package cg.oam.config;

//import java.awt.Dimension;
//import java.util.Properties;
//
//import cg.common.logging.Logger;

/**
 * Proxy to update the Engine Configurer
 */
public class ConfigurerProxy
{

  public static final String engine_image_compressionQuality = "engine.image.compressionQuality";
  public static final String engine_image_renderQuality = "engine.image.renderQuality";
  public static final String engine_thumbnail_compressionQuality = "engine.thumbnail.compressionQuality";
  public static final String engine_thumbnail_renderQuality = "engine.thumbnail.renderQuality";
  public static final String engine_image_areaThreshold = "engine.image.areaThreshold";
  public static final String engine_image_pixelsThreshold = "engine.image.pixelsThreshold";
  public static final String engine_image_dotsPerInch = "engine.image.dotsPerInch";
  public static final String engine_streamingBaseUrl = "engine.streamingBaseUrl";
  public static final String engine_temporaryDirectory = "engine.temporaryDirectory";
  public static final String engine_documentCacheSize = "engine.documentCacheSize";
  public static final String engine_thumbnail_maxSize = "engine.thumbnail.maxSize";
  public static final String engine_process_type = "engine.process.type";
  public static final String service_max_concurent_connections = "service.max.concurent.connections";
  public static final String service_request_timeout = "service.request.timeout";
  public static final String engine_always_use_image_service = "engine.thumbnail.alwaysUseImageService";
  public static final String engine_chunk_size_limit = "engine.chunk.size";
  public static final String engine_image_default_size = "engine.image.defaultSize";

//  private static Logger _log = Logger.getLogger( ConfigurerProxy.class );

//  private Configurer _configurer = null;
//
//  public ConfigurerProxy()
//  {
//    _configurer = EngineConfigurer.getConfigurer();
//  }
//
//  public ConfigurerProxy( Configurer configurer )
//  {
//    _configurer = configurer;
//  }
//
//  public void resetConfigurerToDefaults()
//  {
//    EngineConfigurer.resetConfigurerToDefaults();
//    _configurer = EngineConfigurer.getConfigurer();
//  }

//  public boolean save()
//  {
//    try
//    {
//      PropertiesManager.getPropertiesManager().updateIseedocsProperties( getSettings() );
//      _log.info( "Have saved the current settings to the properties file." );
//      return true;
//    }
//    catch ( Exception e )
//    {
//      _log.error( "Unable to save current settings.", e );
//    }
//
//    return false;
//  }

//  public boolean load()
//  {
//    try
//    {
//      Properties savedValues = PropertiesManager.getPropertiesManager().getIseedocsProperties();
//      applySettings( savedValues );
//      _log.info( "Have load the current settings from properties file." );
//      return true;
//    }
//    catch ( Exception e )
//    {
//      _log.error( "Unable to load current settings.", e );
//    }
//
//    return false;
//  }

//  public Properties getSettings()
//  {
//    Properties settings = new Properties();
//
//    settings.put( engine_image_compressionQuality, getImageCompressionQuality() + "" );
//    // settings.put(engine_image_renderQuality, getImageRenderQuality() + "");
//    settings.put( engine_thumbnail_compressionQuality, getThumbnailCompressionQuality() + "" );
//    // settings.put(engine_thumbnail_renderQuality, getThumbnailRenderQuality() + "");
//    settings.put( engine_image_areaThreshold, getAreaThreshold() + "" );
//    settings.put( engine_image_pixelsThreshold, getPixelsThreshold() + "" );
//    settings.put( engine_image_dotsPerInch, getDefaultDotsPerInch() + "" );
//    settings.put( engine_streamingBaseUrl, getStreamingBaseUrl() + "" );
//    settings.put( engine_temporaryDirectory, getTempDirectory() + "" );
//    settings.put( engine_documentCacheSize, getDocumentCacheSize() + "" );
//    settings.put( engine_thumbnail_maxSize, getPageImageSizeStr() );
//    settings.put( engine_always_use_image_service, getAlwaysUseImageService() + "" );
//    settings.put( engine_process_type, getProcessType() );
//    settings.put( service_max_concurent_connections, getMaxConcurentConnections() + "" );
//    settings.put( service_request_timeout, getRequestTimeout() + "" );
//    settings.put( engine_chunk_size_limit, getChunkLengthLimit() + "" );
//    settings.put( engine_image_default_size, getImageDeafultSize() + "" );
//
//    return settings;
//  }

//  public void applySettings( Properties settings ) throws Exception
//  {
//
//    String value = settings.getProperty( engine_image_compressionQuality );
//    if ( value != null )
//      setImageCompressionQuality( Float.parseFloat( value ) );
//
//    // value = settings.getProperty(engine_image_renderQuality);
//    // if (value != null)
//    // setImageRenderQuality(Float.parseFloat(value));
//
//    value = settings.getProperty( engine_thumbnail_compressionQuality );
//    if ( value != null )
//      setThumbnailCompressionQuality( Float.parseFloat( value ) );
//
//    // value = settings.getProperty(engine_thumbnail_renderQuality);
//    // if (value != null)
//    // setThumbnailRenderQuality(Float.parseFloat(value));
//
//    value = settings.getProperty( engine_image_areaThreshold );
//    if ( value != null )
//      setAreaThreshold( Integer.parseInt( value ) );
//
//    value = settings.getProperty( engine_image_pixelsThreshold );
//    if ( value != null )
//      setPixelsThreshold( Integer.parseInt( value ) );
//
//    value = settings.getProperty( engine_image_dotsPerInch );
//    if ( value != null )
//      setDefaultDotsPerInch( Integer.parseInt( value ) );
//
//    value = settings.getProperty( engine_streamingBaseUrl );
//    if ( value != null )
//      setStreamingBaseUrl( value );
//
//    value = settings.getProperty( engine_temporaryDirectory );
//    if ( value != null )
//      setTempDirectory( value );
//
//    value = settings.getProperty( engine_documentCacheSize );
//    if ( value != null )
//      setDocumentCacheSize( Integer.parseInt( value ) );
//
//    value = settings.getProperty( engine_thumbnail_maxSize );
//    if ( value != null )
//      setPageImageSizeStr( value );
//
//    value = settings.getProperty( engine_always_use_image_service );
//    if ( value != null )
//      setAlwaysUseImageService( Boolean.parseBoolean( value ) );
//
//    value = settings.getProperty( engine_process_type );
//    if ( value != null )
//      setProcessType( value );
//
//    value = settings.getProperty( service_max_concurent_connections );
//    if ( value != null )
//      setMaxConcurentConnections( Integer.parseInt( value ) );
//
//    value = settings.getProperty( service_request_timeout );
//    if ( value != null )
//      setRequestTimeout( Integer.parseInt( value ) );
//
//    value = settings.getProperty( engine_chunk_size_limit );
//    if ( value != null )
//      setChunkLengthLimit( Integer.parseInt( value ) );
//
//    value = settings.getProperty( engine_image_default_size );
//    if ( value != null )
//      setImageDeafultSize( Integer.parseInt( value ) );
//
//  }

//  public float getImageCompressionQuality()
//  {
//    return _configurer.getImageCompressionQuality();
//  }

  // public float getImageRenderQuality() {
  // return _configurer.getImageRenderQuality();
  // }

//  public float getThumbnailCompressionQuality()
//  {
//    return _configurer.getThumbnailCompressionQuality();
//  }

  // public float getThumbnailRenderQuality() {
  // return _configurer.getThumbnailRenderQuality();
  // }

//  public void setImageCompressionQuality( float imageCompressionQuality )
//  {
//    if ( imageCompressionQuality > 0f && imageCompressionQuality <= 1f )
//      _configurer.setImageCompressionQuality( imageCompressionQuality );
//    else
//      throw new IllegalArgumentException( "Invalid compression quality: " + imageCompressionQuality );
//  }

  // public void setImageRenderQuality(float imageRenderQuality){
  // if (imageRenderQuality > 0f && imageRenderQuality <= 1f)
  // _configurer.setImageRenderQuality(imageRenderQuality);
  // else
  // throw new IllegalArgumentException("Invalid compression quality: "+imageRenderQuality);
  // }

//  public void setThumbnailCompressionQuality( float thumbnailCompressionQuality )
//  {
//    if ( thumbnailCompressionQuality > 0f && thumbnailCompressionQuality <= 1f )
//      _configurer.setThumbnailCompressionQuality( thumbnailCompressionQuality );
//    else
//      throw new IllegalArgumentException( "Invalid compression quality: " + thumbnailCompressionQuality );
//  }

  // public void setThumbnailRenderQuality(float thumbnailRenderQuality){
  // if (thumbnailRenderQuality > 0f && thumbnailRenderQuality <= 1f)
  // _configurer.setThumbnailRenderQuality(thumbnailRenderQuality);
  // else
  // throw new IllegalArgumentException("Invalid compression quality: "+thumbnailRenderQuality);
  // }

//  public String getStreamingBaseUrl()
//  {
//    return _configurer.getStreamingBaseUrl();
//  }
//
//  public void setStreamingBaseUrl( String serverName )
//  {
//    _configurer.setStreamingBaseUrl( serverName );
//  }
//
//  public String getTempDirectory()
//  {
//    return _configurer.getTempDirectory();
//  }
//
//  public void setTempDirectory( String serverTempDirectory )
//  {
//    _configurer.setTempDirectory( serverTempDirectory );
//  }
//
//  public int getPixelsThreshold()
//  {
//    return _configurer.getPixelsThreshold();
//  }
//
//  public void setPixelsThreshold( int pixelsThreshold )
//  {
//
//    if ( pixelsThreshold > 0 )
//      _configurer.setPixelsThreshold( pixelsThreshold );
//    else
//      throw new IllegalArgumentException( "Pixels Threshold must be > 0" );
//  }
//
//  public int getAreaThreshold()
//  {
//    return _configurer.getAreaThreshold();
//  }
//
//  public void setAreaThreshold( int areaThreshold )
//  {
//
//    if ( areaThreshold > 0 )
//      _configurer.setAreaThreshold( areaThreshold );
//    else
//      throw new IllegalArgumentException( "Area Threshold must be > 0" );
//  }
//
//  public int getDefaultDotsPerInch()
//  {
//    return _configurer.getDefaultDotsPerInch();
//  }
//
//  public void setDefaultDotsPerInch( int defaultDotsPerInch )
//  {
//
//    if ( defaultDotsPerInch > 0 )
//      this._configurer.setDefaultDotsPerInch( defaultDotsPerInch );
//    else
//      throw new IllegalArgumentException( "Default Dots Per Inch must be > 0" );
//  }
//
//  public Dimension getPageImageSize()
//  {
//    return _configurer.getPageImageSize();
//  }
//
//  public void setPageImageSize( Dimension imageSize )
//  {
//    _configurer.setPageImageSize( imageSize );
//  }
//
//  public String getPageImageSizeStr()
//  {
//    return _configurer.getPageImageSizeStr();
//  }
//
//  public void setPageImageSizeStr( String imageSize )
//  {
//    _configurer.setPageImageSizeStr( imageSize );
//
//  }
//
//  public String getProcessType()
//  {
//    return _configurer.getProcessType();
//  }
//
//  public void setProcessType( String type )
//  {
//    _configurer.setProcessType( type );
//  }
//
//  public int getDocumentCacheSize()
//  {
//    return _configurer.getDocumentCacheSize();
//  }
//
//  public void setDocumentCacheSize( int cacheSize )
//  {
//    _configurer.setDocumentCacheSize( cacheSize );
//  }
//
//  public int getMaxConcurentConnections()
//  {
//    return _configurer.getMaxConcurentConnections();
//  }
//
//  public void setMaxConcurentConnections( int value )
//  {
//    if ( value > 0 )
//      _configurer.setMaxConcurentConnections( value );
//  }
//
//  public int getRequestTimeout()
//  {
//    return _configurer.getRequestTimeout();
//  }
//
//  public void setRequestTimeout( int requestTimeout )
//  {
//    if ( requestTimeout > 0 )
//      _configurer.setRequestTimeout( requestTimeout );
//  }
//
//  public boolean getAlwaysUseImageService()
//  {
//    return _configurer.getAlwaysUseImageService();
//  }
//
//  public void setAlwaysUseImageService( boolean alwaysUseImageService )
//  {
//    _configurer.setAlwaysUseImageService( alwaysUseImageService );
//  }
//
//  public int getChunkLengthLimit()
//  {
//    return _configurer.getChunkLengthLimit();
//  }
//
//  public void setChunkLengthLimit( int value )
//  {
//    _configurer.setChunkLengthLimit( value );
//  }
//
//  public int getImageDeafultSize()
//  {
//    return _configurer.getImageDefaultSize();
//  }
//
//  public void setImageDeafultSize( int value )
//  {
//    _configurer.setImageDefaultSize( value );
//  }
}
