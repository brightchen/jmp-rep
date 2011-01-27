package cg.oam.config;

import java.util.Properties;

public class ConfigUtil
{
  public static final char[] classPathItemsPossibleSeperators = { ';', ':' };
  
  public static char getClassPathItemsSeperator( String classpath )
  {
    for( char seperator : classPathItemsPossibleSeperators )
    {
      if( classpath.indexOf( seperator ) >= 0 )
        return seperator;
    }
    return ':';
  }
  public static String[] getClasspathItems()
  {
    String classpathValue = System.getProperty( "java.class.path" );
    if( classpathValue == null || classpathValue.isEmpty() )
      return null;
    char seperator = getClassPathItemsSeperator( classpathValue );
    return classpathValue.split( String.valueOf( seperator ) );
  }
  
  
  public static Properties merge( Properties defaultProps, Properties overrideProps )
  {
    if( defaultProps == null )
      return overrideProps;
    if( overrideProps == null )
      return defaultProps;
    
    Properties props = new Properties();
    
    props.putAll( defaultProps );
    props.putAll( overrideProps );
    return props;
  }
}
