package cg.oam.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
// import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.core.io.FileSystemResource;

import cg.common.logging.Level;
import cg.common.logging.Logger;

/**
 * Helper class for getting server config file location from master configuration file
 * 
 * @see cg.oam.config.PropertiesManager
 * 
 */
public class SpringPropertyConfigurer extends PropertyPlaceholderConfigurer
{

  static Logger _log = Logger.getLogger( SpringPropertyConfigurer.class );

  // --- attributes
  private PropertiesManager _propertiesManager;

  public SpringPropertyConfigurer() throws Exception
  {
    super();
    initProperties();
  }

  public SpringPropertyConfigurer( PropertiesManager propertiesManager ) throws Exception
  {
    super();
    _propertiesManager = propertiesManager;
    initProperties();
  }

  public void initProperties() throws Exception
  {

    // ignore if properties not set
    setIgnoreResourceNotFound( true );
    setIgnoreUnresolvablePlaceholders( true );

    _log.setLevel( Level.INFO );

    // make sure it has is set a PropertiesManager
    if ( _propertiesManager == null )
      _propertiesManager = PropertiesManager.getPropertiesManager();

    if ( _propertiesManager == null )
      throw new Exception( "Please set the Properties Manager first." );
    try
    {
      setLocation( new FileSystemResource( _propertiesManager.getIseedocsPropertiesLocation() ) );
    }
    catch ( Exception e )
    {
      throw new Exception( "Unable to set properties location. " + e.getMessage() );
    }

  }

  // public void configureLogger(){
  // new LoggerConfigListener().init();
  // }

  public void setPropertiesManager( PropertiesManager propertiesManager ) throws Exception
  {
    _propertiesManager = propertiesManager;
    initProperties();
  }
}