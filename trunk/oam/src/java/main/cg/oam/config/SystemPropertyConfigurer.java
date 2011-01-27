package cg.oam.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import cg.common.logging.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * Helper class for getting server config file location from System.properties The IT Administartor should set config
 * file location in the batch file used to launch the Application Server as for example:
 * -Diseedocs.config.file="C:\config\iseedocs.properties"
 */
public class SystemPropertyConfigurer extends PropertyPlaceholderConfigurer
{

  static Logger log = Logger.getLogger( SystemPropertyConfigurer.class );

  // FIXEME: merge with Log4jSystemConfig

  // --- attributes
  private final static String DEFAULT_CONFIG_FILE_LOCATION = "\\config\\iseedocs.properties";
  public static final String CONFIG_ISEEDOCS_PROPERTY = "iseedocs.work.properties.location";

  public SystemPropertyConfigurer( String systemPropertyName ) throws Exception
  {
    super();
    String configFile = System.getProperty( systemPropertyName );
    log.debug( "Configuration System property '" + systemPropertyName + "' = '" + configFile + "'" );
    // set default Location if not set as JVM "-D" property
    if ( configFile == null )
      configFile = DEFAULT_CONFIG_FILE_LOCATION;
    // //
    String location = null;
    InputStream in = null;
    try
    {
      in = new FileInputStream( configFile );

      Properties properties = new Properties();
      properties.load( in );
      location = properties.getProperty( CONFIG_ISEEDOCS_PROPERTY );

      if ( location == null )
      {
        log.info( "Cannot find property " + CONFIG_ISEEDOCS_PROPERTY + " in '" + configFile + "'" );
      }
    }
    catch ( Exception e )
    {
      log.error( "Cannot read custom " + CONFIG_ISEEDOCS_PROPERTY + " from '" + configFile + "'", e );
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
    // //

    if ( location != null )
    {
      setLocation( new FileSystemResource( location ) );
      log.info( "Have set config properties file to '" + location + "'" );
    }

  }
}