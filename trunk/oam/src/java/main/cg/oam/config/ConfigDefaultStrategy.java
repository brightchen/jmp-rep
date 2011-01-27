package cg.oam.config;

import java.util.Properties;

public class ConfigDefaultStrategy implements ConfigStrategy
{
  private Properties props;
  
  @Override
  public String getProperty( String name )
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Properties getProperties()
  {
    if( props != null )
      return props;
    props = ConfigUtil.merge( BuildinProperties.getBuildinProperties(), DefaultProperties.getDefaultProperties() );
    
    //merge all system properties
    props = ConfigUtil.merge( props, System.getProperties() );
    
    return props;
  }

}
