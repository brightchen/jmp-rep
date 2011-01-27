package cg.oam.config;

import java.util.Properties;

public interface ConfigStrategy
{
  public String getProperty( String name );
  public Properties getProperties();
}
