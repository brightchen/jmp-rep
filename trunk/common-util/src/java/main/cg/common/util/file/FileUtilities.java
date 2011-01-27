package cg.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.TreeMap;

import cg.common.logging.Logger;

/**
 * TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code
 * Templates
 */
public final class FileUtilities
{

  public static final String FORMAT_HTML = "HTML";
  public static final String FORMAT_BMP = "BMP";
  public static final String FORMAT_FPX = "FPX";
  public static final String FORMAT_IVU = "IVU";
  public static final String FORMAT_JPEG = "JPEG";
  public static final String FORMAT_PNG = "PNG";
  public static final String FORMAT_TIFF = "TIFF";

  public static final String EXTENSION_HTML = "html";
  public static final String EXTENSION_HTM = "htm";
  public static final String EXTENSION_DOC = "doc";
  public static final String EXTENSION_RTF = "rtf";
  public static final String EXTENSION_DOT = "dot";
  public static final String EXTENSION_XLW = "xlw";
  public static final String EXTENSION_XLA = "xla";
  public static final String EXTENSION_XLS = "xls";
  public static final String EXTENSION_XLT = "xlt";
  public static final String EXTENSION_XLC = "xlc";
  public static final String EXTENSION_XLM = "xlm";
  public static final String EXTENSION_PPT = "ppt";
  public static final String EXTENSION_PPS = "pps";
  public static final String EXTENSION_POT = "pot";
  public static final String EXTENSION_BMP = "bmp";
  public static final String EXTENSION_FPX = "fpx";
  public static final String EXTENSION_IVU = "ivu";
  public static final String EXTENSION_JPEG = "jpeg";
  public static final String EXTENSION_JPG = "jpg";
  public static final String EXTENSION_PDF = "pdf";
  public static final String EXTENSION_PNG = "png";
  public static final String EXTENSION_TIF = "tif";
  public static final String EXTENSION_TIFF = "tiff";
  public static final String EXTENSION_TXT = "txt";

  public static final String EXTENSION_STW = "stw";
  public static final String EXTENSION_SXW = "sxw";
  public static final String EXTENSION_SXC = "sxc";
  public static final String EXTENSION_STC = "stc";
  public static final String EXTENSION_SXD = "sxd";
  public static final String EXTENSION_STD = "std";
  public static final String EXTENSION_SXI = "sxi";
  public static final String EXTENSION_STI = "sti";
  public static final String EXTENSION_SXG = "sxg";
  public static final String EXTENSION_SXM = "sxm";
  public static final String EXTENSION_ODT = "odt";
  public static final String EXTENSION_OTT = "ott";
  public static final String EXTENSION_ODP = "odp";
  public static final String EXTENSION_OTP = "otp";
  public static final String EXTENSION_ODS = "ods";
  public static final String EXTENSION_OTS = "ots";
  public static final String EXTENSION_XML = "xml";
  public static final String EXTENSION_XHTML = "html";
  public static final String EXTENSION_XSL_FO = "fo";
  public static final String EXTENSION_XSL_FO_FOP = "fop";
  public static final String EXTENSION_XSL_FO_CSS = "css.fo";
  public static final String EXTENSION_XMSE = "xmse";
  public static final String EXTENSION_XMSP = "xmsp";
  public static final String EXTENSION_XMSW = "xmsw";

  // OpenOffice.org1.0 / StarOffice6.0
  public static final String MIME_TYPE_SXW = "application/vnd.sun.xml.writer";
  public static final String MIME_TYPE_STW = "application/vnd.sun.xml.writer.template";
  public static final String MIME_TYPE_SXC = "application/vnd.sun.xml.calc";
  public static final String MIME_TYPE_STC = "application/vnd.sun.xml.calc.template";
  public static final String MIME_TYPE_SXD = "application/vnd.sun.xml.draw";
  public static final String MIME_TYPE_STD = "application/vnd.sun.xml.draw.template";
  public static final String MIME_TYPE_SXI = "application/vnd.sun.xml.impress";
  public static final String MIME_TYPE_STI = "application/vnd.sun.xml.impress.template";
  public static final String MIME_TYPE_SXG = "application/vnd.sun.xml.writer.global";
  public static final String MIME_TYPE_SXM = "application/vnd.sun.xml.math";

  // OpenOffice.org2.0 / StarOffice 8
  public static final String MIME_TYPE_ODT = "application/vnd.oasis.opendocument.text";
  public static final String MIME_TYPE_OTT = "application/vnd.oasis.opendocument.text-template";
  public static final String MIME_TYPE_ODP = "application/vnd.oasis.opendocument.presentation";
  public static final String MIME_TYPE_OTP = "application/vnd.oasis.opendocument.presentation-template";
  public static final String MIME_TYPE_ODS = "application/vnd.oasis.opendocument.spreadsheet";
  public static final String MIME_TYPE_OTS = "application/vnd.oasis.opendocument.spreadsheet-template";

  // MicroSoft
  public static final String MIME_TYPE_XLS = "application/vnd.ms-excel";
  public static final String MIME_TYPE_DOC = "application/msword";
  public static final String MIME_TYPE_RTF = "application/rtf";
  public static final String MIME_TYPE_PPT = "application/vnd.ms-powerpoint";
  public static final String MIME_TYPE_PDF = "application/pdf";

  // Image and mis.
  public static final String MIME_TYPE_BMP = "image/bmp";
  public static final String MIME_TYPE_TXT = "text/plain";
  public static final String MIME_TYPE_JPEG = "image/jpeg";
  public static final String MIME_TYPE_PNG = "image/png";
  public static final String MIME_TYPE_TIFF = "image/tiff";
  public static final String MIME_TYPE_IVU = "image/ivu";
  public static final String MIME_TYPE_FPX = "image/fpx";
  public static final String MIME_TYPE_OCTET_STREAM = "application/octet-stream";
  public static final String MIME_TYPE_TEXT = "text/plain";
  public static final String MIME_TYPE_HTML = "text/html";
  public static final String MIME_TYPE_ALBUM = "application/album";
  public static final String MIME_TYPE_ATTHM = "application/multi-attachment";

  // Davisor
  public static final String MIME_TYPE_XML = "text/xml";
  public static final String MIME_TYPE_XSL_FO = "text/xsl+xml";
  public static final String MIME_TYPE_XSL_FO_FOP = "text/davisor.xsl+xml+fop";
  public static final String MIME_TYPE_XSL_FO_CSS = "text/davisor.xsl+xml";
  public static final String MIME_TYPE_XMSE = "application/vnd.davisor.xmse+xml";
  public static final String MIME_TYPE_XMSP = "application/vnd.davisor.xmsp+xml";
  public static final String MIME_TYPE_XMSW = "application/vnd.davisor.xmsw+xml";
  public static final String MIME_TYPE_XHTML = "text/xhtml+xml";

  public static final String MIME_TYPE_NEWSSTAND = "application/newsstand";
  public static final String MIME_TYPE_PDFPUBLISHING = "application/pdfpublishing";
  public static final String MIME_TYPE_MULTISECTION = "application/multisection";

  public static final String DEFAULT_FILE_EXTENSION = "dat";
  public static final String DEFAULT_DOC_INFO_FILE = "docinfo";
  public static final String DEFAULT_CONVERSION_STATUS_FILE = "status";

  /*
   * FPX can also be described by the following mime types application/vnd.fpx application/vnd.netfpx image/x-fpx
   * image/fpx image/vnd.fpx
   */
  // image array
  public static final String[] IMAGES =
  { EXTENSION_JPEG, EXTENSION_JPG, EXTENSION_TIFF, EXTENSION_TIF, EXTENSION_BMP, EXTENSION_PNG };

  private static TreeMap< String, String > mimeTypeTable = new TreeMap< String, String >();

  private static TreeMap< String, String > extensionTable = new TreeMap< String, String >();

  private static Logger _log = Logger.getLogger( FileUtilities.class );

  static
  {
    final String[][] MIMETYPE_LOOKUP;

    MIMETYPE_LOOKUP = new String[][]
    { new String[]
    { EXTENSION_BMP, MIME_TYPE_BMP }, new String[]
    { EXTENSION_FPX, MIME_TYPE_FPX }, new String[]
    { EXTENSION_IVU, MIME_TYPE_IVU }, new String[]
    { EXTENSION_JPG, MIME_TYPE_JPEG }, new String[]
    { EXTENSION_JPEG, MIME_TYPE_JPEG }, new String[]
    { EXTENSION_PNG, MIME_TYPE_PNG }, new String[]
    { EXTENSION_TIF, MIME_TYPE_TIFF }, new String[]
    { EXTENSION_TIFF, MIME_TYPE_TIFF }, new String[]
    { EXTENSION_HTML, MIME_TYPE_HTML }, new String[]
    { EXTENSION_HTM, MIME_TYPE_HTML }, new String[]
    { EXTENSION_TXT, MIME_TYPE_TXT }, new String[]
    { EXTENSION_PDF, MIME_TYPE_PDF },

     // MicroSoft
     new String[]
     { EXTENSION_DOC, MIME_TYPE_DOC }, new String[]
     { EXTENSION_DOT, MIME_TYPE_DOC }, new String[]
     { EXTENSION_RTF, MIME_TYPE_RTF }, new String[]
     { EXTENSION_XLW, MIME_TYPE_XLS }, new String[]
     { EXTENSION_XLA, MIME_TYPE_XLS }, new String[]
     { EXTENSION_XLS, MIME_TYPE_XLS }, new String[]
     { EXTENSION_XLT, MIME_TYPE_XLS }, new String[]
     { EXTENSION_XLC, MIME_TYPE_XLS }, new String[]
     { EXTENSION_XLM, MIME_TYPE_XLS }, new String[]
     { EXTENSION_XML, MIME_TYPE_XML }, new String[]
     { EXTENSION_PPT, MIME_TYPE_PPT }, new String[]
     { EXTENSION_PPS, MIME_TYPE_PPT }, new String[]
     { EXTENSION_POT, MIME_TYPE_PPT },

     // davisor
     new String[]
     { EXTENSION_XSL_FO, MIME_TYPE_XSL_FO }, new String[]
     { EXTENSION_XSL_FO_FOP, MIME_TYPE_XSL_FO_FOP }, new String[]
     { EXTENSION_XSL_FO_CSS, MIME_TYPE_XSL_FO_CSS }, new String[]
     { EXTENSION_XHTML, MIME_TYPE_XHTML }, new String[]
     { EXTENSION_XMSE, MIME_TYPE_XMSE }, new String[]
     { EXTENSION_XMSP, MIME_TYPE_XMSP }, new String[]
     { EXTENSION_XMSW, MIME_TYPE_XMSW },

     // OpenOffice.org2.0 / StarOffice 8
     new String[]
     { EXTENSION_ODT, MIME_TYPE_ODT }, new String[]
     { EXTENSION_OTT, MIME_TYPE_OTT }, new String[]
     { EXTENSION_ODP, MIME_TYPE_ODP }, new String[]
     { EXTENSION_OTP, MIME_TYPE_OTP }, new String[]
     { EXTENSION_ODS, MIME_TYPE_ODS }, new String[]
     { EXTENSION_OTS, MIME_TYPE_OTS },

     // OpenOffice.org1.0 / StarOffice6.0
     new String[]
     { EXTENSION_STW, MIME_TYPE_STW }, new String[]
     { EXTENSION_SXW, MIME_TYPE_SXW }, new String[]
     { EXTENSION_SXC, MIME_TYPE_SXC }, new String[]
     { EXTENSION_STC, MIME_TYPE_STC }, new String[]
     { EXTENSION_SXD, MIME_TYPE_SXD }, new String[]
     { EXTENSION_STD, MIME_TYPE_STD }, new String[]
     { EXTENSION_SXI, MIME_TYPE_SXI }, new String[]
     { EXTENSION_STI, MIME_TYPE_STI }, new String[]
     { EXTENSION_SXG, MIME_TYPE_SXG }, new String[]
     { EXTENSION_SXM, MIME_TYPE_SXM },

    };

    for ( int i = 0; i < MIMETYPE_LOOKUP.length; i++ )
      mimeTypeTable.put( MIMETYPE_LOOKUP[i][0], MIMETYPE_LOOKUP[i][1] );

    for ( int i = 0; i < MIMETYPE_LOOKUP.length; i++ )
    {
      extensionTable.put( MIMETYPE_LOOKUP[i][1], MIMETYPE_LOOKUP[i][0] );
      // _log.debug("extensionTable : " + MIMETYPE_LOOKUP[i][1] + "=" + MIMETYPE_LOOKUP[i][0]);
    }
  }

  public static String getContentType( String ext )
  {
    String contentType = null;
    contentType = mimeTypeTable.get( ext.toLowerCase() );
    if ( contentType == null )
      contentType = "unknown";
    return contentType;
  }

  public static String getFileExtension( String mimeType )
  {
    String fileExtension = DEFAULT_FILE_EXTENSION;
    fileExtension = extensionTable.get( mimeType.toLowerCase() );

    // _log.debug("MimeType '" + mimeType.toLowerCase() + "' extension = " + fileExtension);
    if ( fileExtension == null )
      fileExtension = DEFAULT_FILE_EXTENSION;
    return fileExtension;
  }

  public static String getFileContentType( String fileName )
  {
    if ( fileName == null )
      return null;
    String contentType = null;

    String extension = getExtension( fileName );

    contentType = (String) mimeTypeTable.get( extension.toLowerCase() );
    if ( contentType == null )
      contentType = "unknown";
    return contentType;
  }

  /**
   * 
   * @param fileName
   * @return true if fileName is supported by IS conversion engine
   */
  public static boolean isImage( String fileName )
  {

    if ( fileName == null )
      return false;
    boolean isImage = false;
    for ( int i = 0; i < IMAGES.length; i++ )
    {
      if ( getExtension( fileName ).equalsIgnoreCase( IMAGES[i] ) )
        isImage = true;
    }
    return isImage;
  }

  /**
   * 
   * @param inString
   * @return true is @param inString is numeric
   */
  public static boolean isNumeric( String inString )
  {
    CharacterIterator theIterator = new StringCharacterIterator( inString );
    boolean used = false, isFirst = true;

    for ( char ch = theIterator.first(); ch != CharacterIterator.DONE; ch = theIterator.next() )
    {
      if ( !Character.isDigit( ch ) )
      {
        if ( String.valueOf( ch ).equalsIgnoreCase( "." ) && !used )
          used = true;
        else if ( String.valueOf( ch ).equalsIgnoreCase( "-" ) && isFirst )
          ;
        else
          return false;

        isFirst = false;
      }
    }

    return true;
  }

  /**
   * 
   * @param inputString
   * @return number of upper case characters within the input string
   */
  public static int upperCaseCount( String inputString )
  {
    int count = 0;
    CharacterIterator theIterator = new StringCharacterIterator( inputString );
    for ( char ch = theIterator.first(); ch != CharacterIterator.DONE; ch = theIterator.next() )
    {
      if ( Character.isUpperCase( ch ) )
        count++;
    }

    return count;
  }

  // Copies src file to dst file.
  // If the dst file does not exist, it will be created
  public static void copyFile( File src, File dst ) throws IOException
  {

    InputStream in = new FileInputStream( src );
    OutputStream out = new FileOutputStream( dst );
    copyStream( in, out );
  }

  public static void copyStream( InputStream in, OutputStream out ) throws IOException
  {

    byte[] buf = new byte[1024];
    int len;
    while ( ( len = in.read( buf ) ) > 0 )
    {
      out.write( buf, 0, len );
    }
    in.close();
    out.close();

  }

  // Deletes directory dir plus all subdirectories and files
  public static boolean deleteDirectory( File dir ) throws Exception
  {
    if ( dir.exists() && dir.isDirectory() )
    {
      File[] files = dir.listFiles();
      for ( File file : files )
      {
        // _log.debug("removing directory '" + file.getAbsolutePath());
        boolean success = deleteDirectory( file );
        if ( !success )
        {
          // give up?
          return false;
        }
      }
    }
    _log.debug( "removing file or directory '" + dir.getAbsolutePath() );
    return dir.delete();
  }

  // @return true if the filename has the extension ext
  public static boolean checkExtension( String filename, String ext )
  {
    return ext.equalsIgnoreCase( getExtension( filename ) );
  }

  public static String getExtension( String filename )
  {
    if ( filename == null )
      return null;
    int dotpos = -1;
    dotpos = filename.lastIndexOf( "." );
    if ( dotpos == -1 )
      return "";
    else
      return filename.substring( dotpos + 1, filename.length() );
  }

  public static String dropExtension( String filename )
  {
    int dotpos = -1;
    dotpos = filename.lastIndexOf( "." );
    if ( dotpos == -1 )
      return filename;
    else
      return filename.substring( 0, dotpos );
  }

  // filter file of folders with name "filter" from f2.
  public static boolean fileComparer( File f1, File f2 )
  {
    if ( f1.equals( f2 ) )
    {
      return true;
    }
    else if ( !f1.exists() )
    {
      _log.debug( "The first input file does not exist." );
      return false;
    }
    else if ( !f2.exists() )
    {
      _log.debug( "The second input file does not exist." );
      return false;
    }
    else if ( !f1.isFile() || !f2.isFile() )
    {
      _log.debug( "One of the input is not a file" );
      return false;
      /*
       * } else if (!(f1.getName().equalsIgnoreCase(f2.getName()))){ _log.debug("Incompatable file name"); return false;
       */
    }
    else if ( !( f1.length() == f2.length() ) )
    {
      _log.debug( "Incompatable file length" );
      return false;
    }
    else
    {
      FileInputStream in1 = null;
      FileInputStream in2 = null;
      try
      {
        int len = (int) f1.length();
        byte[] buf1 = new byte[len];
        byte[] buf2 = new byte[len];

        in1 = new FileInputStream( f1 );
        in2 = new FileInputStream( f2 );
        if ( in1.read( buf1 ) != len )
          return false;
        if ( in2.read( buf2 ) != len )
          return false;
        if ( !Arrays.equals( buf1, buf2 ) )
        {
          return false;
        }

      }
      catch ( Exception e )
      {

      }
      finally
      {
        if ( !( in1 == null ) )
        {
          try
          {
            in1.close();
          }
          catch ( Exception e )
          {
            in1 = null;
          }
        }
        if ( !( in2 == null ) )
        {
          try
          {
            in2.close();
          }
          catch ( Exception e )
          {
            in2 = null;
          }
        }
      }
      return true;
    }
  }

}
