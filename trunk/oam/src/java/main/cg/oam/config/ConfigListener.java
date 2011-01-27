package cg.oam.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConfigListener implements ServletContextListener
{
  @Override
  public void contextInitialized( ServletContextEvent event )
  {
    ( new SystemConfiger() ).init();
  }

  @Override
  public void contextDestroyed( ServletContextEvent arg0 )
  {
    // TODO Auto-generated method stub
    
  }
}
