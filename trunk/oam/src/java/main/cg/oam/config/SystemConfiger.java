package cg.oam.config;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SystemConfiger extends PropertyPlaceholderConfigurer
{
  public SystemConfiger()
  {
    super();
    init();
  }
  
  public void init()
  {
    ConfigStrategy configStrategy = new ConfigDefaultStrategy();
    Properties props = configStrategy.getProperties();
    
    //config log4j
    PropertyConfigurator.configure( props );
    
    setProperties( props );
  }
}
