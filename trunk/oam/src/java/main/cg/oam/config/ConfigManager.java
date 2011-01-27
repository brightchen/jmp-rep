package cg.oam.config;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ConfigManager
{
  public void init()
  {
    ConfigStrategy configStrategy = new ConfigDefaultStrategy();
    Properties props = configStrategy.getProperties();
    
    //config log4j
    PropertyConfigurator.configure( props );
    
    //
    PropertyPlaceholderConfigurer cfg = new PropertyPlaceholderConfigurer();
    cfg.setProperties( props );
  }
}
