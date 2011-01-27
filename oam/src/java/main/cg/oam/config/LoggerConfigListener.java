package cg.oam.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * Bootstrap listener for custom Logger initialization in a web environment.
 * 
 * <p>
 * This listener should be registered before ContextLoaderListener in web.xml, when using custom Log4J initialization.
 * 
 * <p>
 * For Servlet 2.2 containers and Servlet 2.3 ones that do not initalize listeners before servlets, use
 * Log4jConfigServlet. See the ContextLoaderServlet javadoc for details.
 * 
 */

public class LoggerConfigListener implements ServletContextListener
{

  public static final String CONFIG_SYSTEM_PROPERTY_PARAM = "configSystemPropertyName";
  public static final String CONFIG_SYSTEM_PROPERTY_DEFAULT = "default.config.file";

  // We suppose this method is called right at the begging of web application loading
  // It will set the System Property Name for the Properties Manager to look for master configuration file
  public void contextInitialized( ServletContextEvent event )
  {
    // Perform actual Log4J initialization; else rely on Log4J's default initialization.
    try
    {
      String systemPropertyName = null;
      if ( event != null )
        systemPropertyName = event.getServletContext().getInitParameter( CONFIG_SYSTEM_PROPERTY_PARAM );

      if ( systemPropertyName == null )
        systemPropertyName = CONFIG_SYSTEM_PROPERTY_DEFAULT;

      String location = PropertiesManager.initPropertiesManager( systemPropertyName ).getLogPropertiesLocation();

      // Only perform custom Log4J initialization in case of a config file.
      if ( location != null )
      {
        // Write a log message to servlet log.
        if ( event != null )
          event.getServletContext().log( "Initializing custom Logger from '" + location + "'" );
        else
          System.out.println( "Initializing custom Logger from '" + location + "'" );

        PropertyConfigurator.configure( location );
      }
    }
    catch ( Exception e )
    {
      throw new IllegalArgumentException( "Unable to configure Logger. " + e.getMessage() );
    }
  }

  public void contextDestroyed( ServletContextEvent event )
  {
    /**
     * Shut down Log4J, properly releasing all file locks.
     * <p>
     * This isn't strictly necessary, but recommended for shutting down Log4J in a scenario where the host VM stays
     * alive (for example, when shutting down an application in a J2EE environment).
     */
    LogManager.shutdown();
  }

}
