package cg.oam.config;

//import java.awt.Dimension;
//import java.util.Properties;
//
//import cg.common.logging.Logger;

// import cg.device.profile.Profile;

public class DefaultProfileProxy
{

//  private static final String profile_characterEncoding = "profile.characterEncoding";
//  private static final String profile_textFormatting = "profile.textFormatting";
//  private static final String profile_idxFormatting = "profile.idxFormatting";
//  private static final String profile_tableFormatting = "profile.tableFormatting";
//  private static final String profile_image_encoding = "profile.image.encoding";
//  private static final String profile_thumbnail_encoding = "profile.thumbnail.encoding";
//  private static final String profile_image_bitDepth = "profile.image.bitDepth";
//  private static final String profile_display_size = "profile.display.size";
//  private static final String profile_thumbnail_size = "profile.thumbnail.size";
//  private static final String profile_page_thumbnail_size = "profile.page.thumbnail.size";
//  private static final String profile_page_imageCount = "profile.page.imageCount";
//  private static final String profile_page_size = "profile.page.size";
//  private static final String profile_table_blockSize = "profile.table.blockSize";

//  private static Logger _log = Logger.getLogger( DefaultProfileProxy.class );

//  private Profile _profile = null;

//  private ProfilerService _profilerService = null;

  public DefaultProfileProxy()
  {

  }

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

  // FIXME: move property names to static final variables
//  public Properties getSettings()
//  {
//    Properties settings = new Properties();
//
//    settings.put( profile_characterEncoding, getCharacterEncoding() );
//    settings.put( profile_textFormatting, getTextFormatting() );
//    settings.put( profile_idxFormatting, getIdxFormatting() );
//    settings.put( profile_tableFormatting, getTableFormatting() );
//    settings.put( profile_image_encoding, getImageEncoding() );
//    settings.put( profile_thumbnail_encoding, getThumbnailEncoding() );
//    settings.put( profile_image_bitDepth, getImageBitDepth() + "" );
//    settings.put( profile_display_size, getDisplaySize2() );
//    settings.put( profile_thumbnail_size, getThumbnailSize2() );
//    settings.put( profile_page_thumbnail_size, getPageThumbnailSize2() );
//    settings.put( profile_page_imageCount, getImagesPerPage() + "" );
//    settings.put( profile_page_size, getPageSize() + "" );
//    settings.put( profile_table_blockSize, getTableBlockSize2() + "" );
//
//    return settings;
//  }
//
//  public void applySettings( Properties settings ) throws Exception
//  {
//
//    String value = settings.getProperty( profile_characterEncoding );
//    if ( value != null )
//      setCharacterEncoding( value );
//
//    value = settings.getProperty( profile_textFormatting );
//    if ( value != null )
//      setTextFormatting( value );
//
//    value = settings.getProperty( profile_idxFormatting );
//    if ( value != null )
//      setIdxFormatting( value );
//
//    value = settings.getProperty( profile_tableFormatting );
//    if ( value != null )
//      setTableFormatting( value );
//
//    value = settings.getProperty( profile_image_encoding );
//    if ( value != null )
//      setImageEncoding( value );
//
//    value = settings.getProperty( profile_thumbnail_encoding );
//    if ( value != null )
//      setThumbnailEncoding( value );
//
//    value = settings.getProperty( profile_image_bitDepth );
//    if ( value != null )
//      setImageBitDepth( Integer.parseInt( value ) );
//
//    value = settings.getProperty( profile_display_size );
//    if ( value != null )
//      setDisplaySize2( value );
//
//    value = settings.getProperty( profile_thumbnail_size );
//    if ( value != null )
//      setThumbnailSize2( value );
//
//    value = settings.getProperty( profile_page_thumbnail_size );
//    if ( value != null )
//      setPageThumbnailSize2( value );
//
//    value = settings.getProperty( profile_page_imageCount );
//    if ( value != null )
//      setImagesPerPage( Integer.parseInt( value ) );
//
//    value = settings.getProperty( profile_page_size );
//    if ( value != null )
//      setPageSize( Integer.parseInt( value ) );
//
//    value = settings.getProperty( profile_table_blockSize );
//    if ( value != null )
//      setTableBlockSize2( value );
//
//  }

//  public String getCharacterEncoding()
//  {
//    return _profile.getCharacterEncoding();
//  }
//
//  public String getTextFormatting()
//  {
//    return _profile.getTextFormatting();
//  }
//
//  public String getImageEncoding()
//  {
//    return _profile.getImageEncoding();
//  }
//
//  public int getImageBitDepth()
//  {
//    return _profile.getImageBitDepth();
//  }
//
//  public String getThumbnailSize2()
//  {
//    return (int) _profile.getThumbnailSize().getWidth() + "," + (int) _profile.getThumbnailSize().getHeight();
//  }
//
//  public void setThumbnailSize2( String dimension )
//  {
//    String a[] = dimension.split( "," );
//    Dimension size = new Dimension( Integer.parseInt( a[0].trim() ), Integer.parseInt( a[1].trim() ) );
//    _profile.setThumbnailSize( size );
//    ;
//  }
//
//  public String getPageThumbnailSize2()
//  {
//    return (int) _profile.getPageThumbnailSize().getWidth() + "," + (int) _profile.getPageThumbnailSize().getHeight();
//  }
//
//  public void setPageThumbnailSize2( String dimension )
//  {
//    String a[] = dimension.split( "," );
//    Dimension size = new Dimension( Integer.parseInt( a[0].trim() ), Integer.parseInt( a[1].trim() ) );
//    _profile.setPageThumbnailSize( size );
//  }
//
//  public void setCharacterEncoding( String characterEncoding )
//  {
//    _profile.setCharacterEncoding( characterEncoding );
//  }
//
//  public String getDisplaySize2()
//  {
//    return (int) _profile.getDisplaySize().getWidth() + "," + (int) _profile.getDisplaySize().getHeight();
//  }
//
//  public void setDisplaySize2( String dimension )
//  {
//    String a[] = dimension.split( "," );
//    _profile.setDisplaySize( new Dimension( Integer.parseInt( a[0].trim() ), Integer.parseInt( a[1].trim() ) ) );
//  }
//
//  public void setImageBitDepth( int imageBitDepth )
//  {
//    if ( imageBitDepth <= 0 || imageBitDepth > 32 )
//      throw new IllegalArgumentException( "Invalid image bit depth <= 0" );
//    _profile.setImageBitDepth( imageBitDepth );
//  }
//
//  public void setImageEncoding( String imageEncoding )
//  {
//    if ( !imageEncoding.equals( "jpeg" ) && !imageEncoding.equals( "png" ) )
//      throw new IllegalArgumentException( "Unsupported image encoding: " + imageEncoding );
//    _profile.setImageEncoding( imageEncoding );
//  }
//
//  public void setTextFormatting( String textFormatting )
//  {
//    _profile.setTextFormatting( textFormatting );
//  }
//
//  public String getIdxFormatting()
//  {
//    return _profile.getIdxFormatting();
//  }
//
//  public void setIdxFormatting( String idxFormatting )
//  {
//    _profile.setIdxFormatting( idxFormatting );
//  }
//
//  public String getTableFormatting()
//  {
//    return _profile.getTableFormatting();
//  }
//
//  public void setTableFormatting( String tableFormatting )
//  {
//    _profile.setTableFormatting( tableFormatting );
//  }
//
//  public int getImagesPerPage()
//  {
//    return _profile.getImagesPerPage();
//  }
//
//  public void setImagesPerPage( int imagesPerPage )
//  {
//    if ( imagesPerPage > 0 )
//      _profile.setImagesPerPage( imagesPerPage );
//    else
//      throw new IllegalArgumentException( "Images per Page must be > 0" );
//  }
//
//  public int getPageSize()
//  {
//    return _profile.getPageSize();
//  }
//
//  public Dimension getTableBlockSize()
//  {
//    return _profile.getTableBlockSize();
//  }
//
//  public void setPageSize( int pageSize )
//  {
//    if ( pageSize <= 0 )
//      throw new IllegalArgumentException( "Invalid Page size <= 0" );
//    _profile.setPageSize( pageSize );
//  }
//
//  public String getTableBlockSize2()
//  {
//    return (int) _profile.getTableBlockSize().getWidth() + "," + (int) _profile.getTableBlockSize().getHeight();
//  }
//
//  public void setTableBlockSize2( String dimension ) throws Exception
//  {
//    String a[] = dimension.split( "," );
//    _profile.setTableBlockSize( new Dimension( Integer.parseInt( a[0].trim() ), Integer.parseInt( a[1].trim() ) ) );
//  }
//
//  public String getThumbnailEncoding()
//  {
//    return _profile.getThumbnailEncoding();
//  }
//
//  public void setThumbnailEncoding( String encoding )
//  {
//    if ( !encoding.equals( "jpeg" ) && !encoding.equals( "png" ) )
//      throw new IllegalArgumentException( "Unsupported image encoding: " + encoding );
//    _profile.setThumbnailEncoding( encoding );
//  }
//
//  public Dimension getPageThumbnailSize()
//  {
//    return _profile.getPageThumbnailSize();
//  }
//
//  public void setPageThumbnailSize( Dimension dimension )
//  {
//    _profile.setPageThumbnailSize( dimension );
//  }
//
//  public boolean restoreDefaults()
//  {
//    _profilerService.resetDefaultProfile();
//    // need to save to configuration files
//    _profile = _profilerService.getDefaultProfile();
//    return save();
//  }
//
//  // IOC beans settings
//  public void setProfilerService( ProfilerService profilerService )
//  {
//    _profilerService = profilerService;
//    _profile = _profilerService.getDefaultProfile();
//  }

}
