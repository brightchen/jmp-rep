package cg.oam.config;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

// the default properties file should be within the classpath if not specified
public class DefaultProperties extends Properties
{
  private static final long serialVersionUID = -1496935754867075190L;
  protected enum PropertyFileSuffix
  {
    properties,
    xml
  }

  protected static String key_defaultPropertyFile = "defaultPropertyFile";
  protected static String defaultPropertiesFileName = "defaultProperties";

  public static Properties getDefaultProperties()
  {
    String filePath = getDefaultPropertyFilePath();
    if( filePath == null )
      return null;
    
    Properties props = new Properties();
    
    //check property file existance
    File propertyFile = new File( filePath );
    if( !propertyFile.exists() )
    {
      return props;
    }
    
    PropertyFileSuffix suffix = getSuffix( filePath );
    try
    {
      FileInputStream propertyFileIs = new FileInputStream( propertyFile );
      if( PropertyFileSuffix.properties.equals( suffix ) )
      {
        props.load( propertyFileIs );
      }
      if( PropertyFileSuffix.xml.equals( suffix ) )
      {
        props.loadFromXML( propertyFileIs );
      }
      propertyFileIs.close();
    }
    catch( Exception e )
    {
    }
    return props;
  }
  

  protected static String getDefaultPropertyFilePath()
  {
    //get from property first
    String propertyFileName = null;
    propertyFileName = System.getProperty( key_defaultPropertyFile );
    if( propertyFileName != null && !propertyFileName.isEmpty() )
      return propertyFileName;
    
    //get from classpath
    for( PropertyFileSuffix suffix : PropertyFileSuffix.values() )
    {
      propertyFileName = defaultPropertiesFileName + "." + suffix.name();
      URL propertyUrl = ClassLoader.getSystemResource( propertyFileName );
      if( propertyUrl == null )
        continue;
      return propertyUrl.getPath();
    }
    return null;
  }
  
  protected static PropertyFileSuffix getSuffix( String filePath )
  {
    for( PropertyFileSuffix suffix : PropertyFileSuffix.values() )
    {
      if( filePath.endsWith( suffix.name() ) )
        return suffix;
    }
    throw new RuntimeException( "Invlid filePath: " + filePath );
  }
  
  public static void main( String argv[] )
  {
    System.out.println( getDefaultPropertyFilePath() );
  }
}
