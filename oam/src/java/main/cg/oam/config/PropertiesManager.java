package cg.oam.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * Helper class for getting server config file locations by reading the master config location from System.properties
 * The IT Administartor should set the master config file location in the cmd/sh file used to launch the Application
 * Server as for example: -Diseedocs.config.file="C:\config\iseedocs.properties"
 * 
 * There are two sets of configurations ( default and override ). each one located in different directory.
 * The default configuration is included in the deploy package
 * The override configuration should be located outside of deploy package.
 * When startup, system will try to load the default configuration and override configuration. the properties of 
 * override configuration should override the default one.
 * If no default configuration properties, the system will generate the default properties and save to file
 */
public class PropertiesManager
{

  public final static String JVM_CONFIG_PROPRETY_NAME = "iseedocs.config.file";

  public static final String CONFIG_ISEEDOCS_WORK_PROPERTY = "work.properties.location";
  public static final String CONFIG_ISEEDOCS_SYSTEM_PROPERTY = "system.properties.location";
  public static final String CONFIG_ISEEDOCS_SERVICE_PROPERTY = "service.properties.location";

  public static final String CONFIG_LOG4J_WORK_PROPERTY = "log4j.work.properties.location";
  public static final String CONFIG_LOG4J_SYSTEM_PROPERTY = "log4j.system.properties.location";
  public static final String CONFIG_LOG4J_SERVICE_PROPERTY = "log4j.service.properties.location";

  public static final String PROPERTIES_COMMENTS = "local - server instance configurations\n"
                                                   + "#Server generated.\n#The file is flushed when the server is started "
                                                   + "with the values from service.properties and system.properties files";

  // --- attributes

  private static PropertiesManager _instance = null;

  private String _masterFileLocation = null;

  private String _configurationFilePrefix = "iseedocs";

  public static PropertiesManager getPropertiesManager()
  {
    if ( _instance == null )
    {
      try
      {
        _instance = new PropertiesManager( JVM_CONFIG_PROPRETY_NAME );
      }
      catch ( Exception e )
      {
      }
    }
    return _instance;
  }

  public static PropertiesManager initPropertiesManager( String systemPropertyName ) throws Exception
  {

    if ( systemPropertyName == null )
      systemPropertyName = JVM_CONFIG_PROPRETY_NAME;

    // create singleton
    _instance = new PropertiesManager( systemPropertyName );

    return _instance;
  }

  public PropertiesManager( String systemPropertyName ) throws Exception
  {
    _masterFileLocation = System.getProperty( systemPropertyName );

    int j = systemPropertyName.indexOf( "." );
    if ( j != -1 )
      _configurationFilePrefix = systemPropertyName.substring( 0, j );
    else
      _configurationFilePrefix = systemPropertyName;

    // set default Location if not set as JVM "-D" property
    if ( _masterFileLocation == null )
    {
      String path = System.getProperty( "user.dir" );
      if ( path == null )
        path = "";
      _masterFileLocation = path + File.separator + "server" + File.separator + _configurationFilePrefix
                            + File.separator + _configurationFilePrefix + ".properties";
    }

    ensureFileExists( _masterFileLocation );

    log( "Configuration System property '" + systemPropertyName + "' = '" + _masterFileLocation + "'" );

  }

  private void ensureFileExists( String filePath ) throws Exception
  {

    File testLocation = new File( filePath );
    if ( !testLocation.exists() )
    {
      File dir = testLocation.getParentFile();
      if ( !dir.exists() )
        dir.mkdirs();
      System.out.println( "Trying to create file '" + testLocation.getAbsolutePath() + "'" );
      testLocation.createNewFile();
    }
  }

  private String addEntryToMasterProperties( String entryName ) throws Exception
  {

    String fileName = entryName;

    int j = entryName.lastIndexOf( ".location" );
    if ( j != -1 )
      fileName = entryName.substring( 0, j );
    File tmpFile = new File( new File( _masterFileLocation ).getParentFile(), fileName );
    String filePath = tmpFile.getAbsolutePath();
    Properties properties = new Properties();
    properties.put( entryName, replaceFilePath( filePath ) );

    updatePropertiesToFile( properties, _masterFileLocation );

    return filePath;
  }

  private static String replaceFilePath( String path )
  {
    StringBuffer buf = new StringBuffer();

    for ( int i = 0; i < path.length(); i++ )
    {
      char c = path.charAt( i );
      if ( c == '\\' )
        buf.append( '\\' );
      buf.append( c );
    }

    return buf.toString();
  }

  public String getPropertiesLocation( String locationName ) throws Exception
  {
    // we do not cache the master configuration file content, only the location
    return loadLocations().getProperty( locationName );
  }

  public Properties getIseedocsProperties() throws Exception
  {
    Properties iseedocsProperties = loadProperties( getIseedocsPropertiesLocation() );
    return iseedocsProperties;
  }

  public Properties getLogProperties() throws Exception
  {
    Properties properties = loadProperties( getLogPropertiesLocation() );
    return properties;
  }

  public String getIseedocsPropertiesLocation() throws Exception
  {
    // 1. merge system and service configurations to .work. file
    String iseedocsPropertiesLocation = getConfigurationFileLocationAndEnsureExists( CONFIG_ISEEDOCS_WORK_PROPERTY );
    String iseedocsSystemPropertiesLocation = getIseedocsSystemConfigLocation();
    String iseedocsServicePropertiesLocation = getIseedocsServiceConfigLocation();

    Properties iseedocsSystemProperties = loadProperties( iseedocsSystemPropertiesLocation );
    Properties iseedocsServiceProperties = loadProperties( iseedocsServicePropertiesLocation );

    // merge
    Properties iseedocsProperties = new Properties();

    iseedocsProperties.putAll( iseedocsServiceProperties );
    // system properties can overwrite service (cluster level) properties
    iseedocsProperties.putAll( iseedocsSystemProperties );

    // save
    saveProperties( iseedocsProperties, iseedocsPropertiesLocation, PROPERTIES_COMMENTS );

    // 2. return the location of the work properties
    return iseedocsPropertiesLocation;
  }

  public String getIseedocsServiceConfigLocation() throws Exception
  {
    return getConfigurationFileLocationAndEnsureExists( CONFIG_ISEEDOCS_SERVICE_PROPERTY );
  }

  public String getIseedocsSystemConfigLocation() throws Exception
  {
    return getConfigurationFileLocationAndEnsureExists( CONFIG_ISEEDOCS_SYSTEM_PROPERTY );
  }

  private String getConfigurationFileLocationAndEnsureExists( String name ) throws Exception
  {

    String location = loadLocations().getProperty( name );

    if ( location == null )
      location = loadLocations().getProperty( "iseedocs." + name );

    if ( location == null )
    {
      location = addEntryToMasterProperties( name );
    }

    ensureFileExists( location );

    return location;
  }

  public String getLogPropertiesLocation() throws Exception
  {
    // 1. merge system and service configurations to .work. file
    String logPropertiesLocation = getConfigurationFileLocationAndEnsureExists( CONFIG_LOG4J_WORK_PROPERTY );
    String logSystemPropertiesLocation = getLogSystemConfigLocation();
    String logServicePropertiesLocation = getLogServiceConfigLocation();

    Properties logSystemProperties = loadProperties( logSystemPropertiesLocation );
    Properties logServiceProperties = loadProperties( logServicePropertiesLocation );

    // merge
    Properties logProperties = new Properties();

    logProperties.putAll( logServiceProperties );
    // system properties can overwrite service (cluster level) properties
    // if log4j.properties where empty, add the required properties

    if ( logSystemProperties.size() < 3 )
    {
      logSystemProperties.put( "log4j.loggerFactory", "cg.common.logging.IseedocsCategoryFactory" );
      logSystemProperties.put( "log4j.threshold", "DEBUG" );
      logSystemProperties.put( "log4j.rootCategory", ",console" );
      logSystemProperties.put( "log4j.appender.console", "org.apache.log4j.ConsoleAppender" );
      logSystemProperties.put( "log4j.appender.console.layout", "org.apache.log4j.PatternLayout" );
      logSystemProperties.put( "log4j.appender.console.layout.ConversionPattern", "%d %-5p %-17c{2} - %m\n" );
      logSystemProperties.put( "log4j.logger.org", "ERROR" );
    }

    logProperties.putAll( logSystemProperties );

    // save
    saveProperties( logProperties, logPropertiesLocation, PROPERTIES_COMMENTS );

    // 2. return the location of the work properties
    return logPropertiesLocation;
  }

  public String getLogServiceConfigLocation() throws Exception
  {
    // we do not cache the master configuration file content, only the location
    return getConfigurationFileLocationAndEnsureExists( CONFIG_LOG4J_SERVICE_PROPERTY );
  }

  public String getLogSystemConfigLocation() throws Exception
  {
    // we do not cache the master configuration file content, only the location
    return getConfigurationFileLocationAndEnsureExists( CONFIG_LOG4J_SYSTEM_PROPERTY );
  }

  public void updateIseedocsProperties( Properties properties ) throws Exception
  {
    // add timestamp
    properties.put( "configurations.lastUpdated", new Date().toString() );

    // update Iseedocs Service
    updatePropertiesToFile( properties, getIseedocsServiceConfigLocation() );

    // update Iseedocs System, too
    updatePropertiesToFile( properties, getIseedocsSystemConfigLocation() );
  }

  public void updateLogProperties( Properties properties ) throws Exception
  {
    // add timestamp
    properties.put( "configurations.lastUpdated", new Date().toString() );

    // update Log Service
    updatePropertiesToFile( properties, getLogServiceConfigLocation() );

    // update Log System, too
    updatePropertiesToFile( properties, getLogSystemConfigLocation() );
  }

  private Properties loadLocations() throws Exception
  {

    return loadProperties( _masterFileLocation );

  }

  public Properties loadProperties( String fileName ) throws Exception
  {

    Properties properties = new Properties();
    InputStream in = null;
    try
    {
      in = new FileInputStream( fileName );

      properties.load( in );
      return properties;
    }
    catch ( Exception e )
    {
      log( "Cannot read configuration. " + e.getMessage(), e );
      throw e;
    }
    finally
    {
      if ( in != null )
      {
        try
        {
          in.close();
        }
        catch ( Exception e )
        {
        }
      }
    }
  }

  public void saveProperties( Properties properties, String fileName, String comments ) throws Exception
  {

    OutputStream out = null;
    try
    {
      out = new FileOutputStream( fileName );
      properties.store( out, comments );
    }
    catch ( Exception e )
    {
      log( "Cannot save configuration. " + e.getMessage(), e );
      throw e;
    }
    finally
    {
      if ( out != null )
      {
        try
        {
          out.close();
        }
        catch ( Exception e )
        {
        }
      }
    }
  }

  public void updatePropertiesToFile( Properties properties, String fileName ) throws Exception
  {

    OutputStream out = null;
    FileReader in = null;

    // 1. red file and create memory representation
    Vector< PropertyLine > src = new Vector< PropertyLine >( 10 );

    try
    {
      in = new FileReader( fileName );

      BufferedReader reader = new BufferedReader( in );
      String line = null;
      while ( ( line = reader.readLine() ) != null )
      {
        src.add( new PropertyLine( line ) );
      }

    }
    catch ( Exception e )
    {
      log( "Cannot read configuration. " + e.getMessage(), e );
      throw e;
    }
    finally
    {
      if ( in != null )
      {
        try
        {
          in.close();
        }
        catch ( Exception e )
        {
        }
      }
    }

    // 2. update the properties
    StringBuffer buf = new StringBuffer();
    try
    {
      // iterate through the file lines
      for ( PropertyLine line : src )
      {
        if ( line.isComment() )
        {
          // write un-touched line of text
          buf.append( line.getText() );
        }
        else
        {
          String key = line.getName();
          String newValue = properties.getProperty( key );
          if ( newValue != null && newValue.trim().length() > 0 )
          {
            if ( key.toLowerCase().endsWith( ".file" ) )
            {
              String logFileName = line.getValue();
              String delimiter = "/";
              int j = logFileName.lastIndexOf( delimiter );

              if ( j == -1 )
              {
                delimiter = "\\";
                j = logFileName.lastIndexOf( delimiter );
              }

              if ( j != -1 )
              {
                logFileName = newValue + delimiter + logFileName.substring( j + delimiter.length() );
              }
              else
              {
                logFileName = newValue + delimiter + logFileName;
              }
              buf.append( line.getName() + "=" + logFileName );
            }
            else
            {
              buf.append( line.getName() + "=" + newValue );
            }
            // remove updated propery
            properties.remove( line.getName() );
          }
          else
          {
            // file property is not found in the properties to update
            // should we save it?
            buf.append( line.getText() );
          }
        }
        // append end of line
        buf.append( "\r\n" );
      }
      // append remaining properties, if any
      Enumeration e = properties.keys();
      while ( e.hasMoreElements() )
      {
        String key = (String) e.nextElement();
        if ( key.startsWith( "engine." ) || key.startsWith( "profile." ) || key.startsWith( "service." ) )
        {
          String value = properties.getProperty( key );
          if ( value != null )
          {
            buf.append( key + "=" + value );
            // append end of line
            buf.append( "\r\n" );
          }
          else
          {
            log( "Property '" + key + "' was not set." );
          }
        }
      }
    }
    catch ( Exception e )
    {
      log( "Cannot update configuration. " + e.getMessage(), e );
      throw e;
    }

    // 3. flush changes to the file
    try
    {
      out = new FileOutputStream( fileName );
      // FIXME not sure if we have to save as utf-8 or ISO-8859-1 charset
      out.write( buf.toString().getBytes() );
    }
    catch ( Exception e )
    {
      log( "Cannot save configuration. " + e.getMessage(), e );
      throw e;
    }
    finally
    {
      if ( out != null )
      {
        try
        {
          out.close();
        }
        catch ( Exception e )
        {
        }
      }
    }
  }

  public static void log( String message )
  {
    System.out.println( message );
  }

  public static void log( String message, Throwable t )
  {
    System.out.println( message );
    t.printStackTrace();
  }

  static class PropertyLine
  {
    private String _name;
    private String _value;
    private boolean _isComment = false;
    private String _text;

    public PropertyLine( String text )
    {
      _text = text;
      String content = _text.trim();
      if ( content.length() == 0 || content.startsWith( "#" ) || content.startsWith( "!" )
           || content.indexOf( "=" ) < 1 )
      {
        _isComment = true;
      }
      else
      {
        // supposely, it is a valid property line
        int i = content.indexOf( "=" );
        _name = content.substring( 0, i ).trim();
        _value = content.substring( i + 1 ).trim();
      }
    }

    public boolean isComment()
    {
      return _isComment;
    }

    public void setComment( boolean isComment )
    {
      _isComment = isComment;
    }

    public String getName()
    {
      return _name;
    }

    public void setName( String name )
    {
      _name = name;
    }

    public String getValue()
    {
      return _value;
    }

    public void setValue( String value )
    {
      _value = value;
    }

    public String getText()
    {
      return _text;
    }

    public void setText( String text )
    {
      _text = text;
    }
  }
}