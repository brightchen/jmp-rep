package cg.oam.config;

import cg.common.logging.Logger;
import javax.servlet.ServletContextEvent;

public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener
{
  static final Logger log = Logger.getLogger( SpringPropertyConfigurer.class );

  public void contextInitialized( ServletContextEvent event )
  {
    log.debug( "----ContextLoaderListener begin." );
    super.contextInitialized( event );
    log.debug( "----ContextLoaderListener end." );
  }
}
