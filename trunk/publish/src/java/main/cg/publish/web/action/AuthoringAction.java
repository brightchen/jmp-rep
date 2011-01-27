package cg.publish.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cg.common.email.EmailService;
import cg.common.logging.Logger;
import cg.publish.service.UserService;

import com.opensymphony.xwork2.Preparable;

/**
 * Edit Document page Action.
 * 
 *         changed by Bright Chen: 1. add the adInfo() behavior, the request can be as following
 *         "http://localhost:8080/iseepublish/authoring/adInfo.do" or
 *         "http://localhost:8080/iseepublish/authoring/adInfo.do?DocID=<documentName>" The first get cached document,
 *         the second one using the document which name is <documentName>
 * 
 *         It seems there are some potential bugs for the previous behavior for get un-cached document. but in fact
 *         right now we can use cached document, let it as it is.
 */
public class AuthoringAction extends BaseAction implements Preparable
{

  private static final long serialVersionUID = 11176746557L;
  public static final Format layoutPageNameFormat = new MessageFormat( "layout{0,number,0000}" );

  protected static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  protected static final String LINE_DELIMITER = "\r\n";
  // private static final int DEF_XML_CONTENT_BUF_LEN = 40960;

  private static Logger log = Logger.getLogger( AuthoringAction.class );

  private UserService userService;
  private EmailService emailService;

  private String id;
  private String docid;

  private String documentName;
  private String fileName;
  private String fileLink;

  private String defaultPageLayoutFilePath;
  
  public void prepare() throws Exception
  {
    clearErrorsAndMessages();
  }

  public String input() throws Exception
  {
    if ( id == null )
      id = (String) getRequest().getSession().getAttribute( "id" );
    if ( id != null && id.length() > 0 )
    {
      setTask( "layout" );
//      document = getDocumentService().getById( Long.valueOf( id ) );
//      log.info( "\n\n*** Document will be Edited, document.document.getSource() -> " + document.getSource() );
//      docid = document.getSource();
    }
    else
    {
      throw new Exception( "Missing document id." );
    }
    return SUCCESS;
  }

  public String layout() throws Exception
  {
    log.info( "Layout request..." );

    HttpServletRequest request = getRequest();
    HttpServletResponse response = getResponse();
    byte[] xmlData = new byte[0];

    try
    {
      // HttpServletRequest request = getRequest();
      // HttpServletResponse response = getResponse();

      String url = request.getRequestURL().toString();
      String query = request.getQueryString();

      if ( query != null && query.length() > 0 )
        url += "?" + query;

      String userAgent = request.getHeader( "user-agent" );
      if ( userAgent == null )
        userAgent = "unknown";

      String pageParam = request.getParameter( "pageIndex" );
      String action = request.getParameter( "action" );

      log.debug( "\nDocument Layout Request: \n" + request.getRequestURI() + " \npath: " + request.getPathInfo()
                 + " \nquery: " + request.getQueryString() + " \nuser-agent: " + userAgent + " \nIP: "
                 + request.getRemoteAddr() + " \nURL " + url + " \nmethod: " + request.getMethod()
                 + " \npageParam: " + pageParam + " \naction: " + action + "\n" );


      long start = System.currentTimeMillis();

      if ( action == null )
        return null;

      if ( action.equals( "getLayout" ) )
      {
        response.setContentType( "text/xml" );
      }
      else if ( action.equals( "getPageCount" ) )
      {
        response.setContentType( "text/xml" );
      }
      else if ( action.equals( "reset" ) )
      {
        response.setContentType( "text/plain" );
      }
      else if ( action.equals( "getContent" ) )
      {
        response.setContentType( "text/xml" );
      }

      response.setCharacterEncoding( "utf-8" );
      response.setContentLength( xmlData.length );
      response.getOutputStream().write( xmlData );

      log.debug( "returned " + xmlData.length + " bytes. \n" + new String( xmlData ) + "\n["
                 + ( System.currentTimeMillis() - start ) + " millis]\n" );
    }
    catch ( Exception e )
    {
      log.error( "Cannot get layout data, use default one.", e );

      // Below is temporary solution to fix Ajax Editor can't handle empty layout problem. Should fix Ajax Editor itself
      // later.
      response.setCharacterEncoding( "utf-8" );
      response.setContentLength( xmlData.length );
      response.getOutputStream().write( xmlData );
      log.debug( " *** Return default layout -> " + new String( xmlData ) );
    }

    return null;
  }

  public String execute() throws Exception
  {
    log.debug( " *** Entering PublisherUserAction.execute() " );

    String queryString = ServletActionContext.getRequest().getQueryString();
    log.debug( " *** getRequest().getQueryString() -> " + queryString );

    return SUCCESS;
  }

  public String update() throws Exception
  {

    HttpServletRequest request = getRequest();
    HttpServletResponse response = getResponse();

    try
    {
      String url = request.getRequestURL().toString();
      String query = request.getQueryString();

      if ( query != null && query.length() > 0 )
        url += "?" + query;

      String userAgent = request.getHeader( "user-agent" );
      if ( userAgent == null )
        userAgent = "unknown";

      log.debug( "\nUpdate Layout Request: \n" + request.getRequestURI() + " \npath: " + request.getPathInfo()
                 + " \nquery: " + request.getQueryString() + " \nuser-agent: " + userAgent + " \nIP: "
                 + request.getRemoteAddr() + " \nURL " + url + " \nmethod: " + request.getMethod() + "\n" );

      InputStream xmlIn = null;
      String xmlLayout = request.getParameter( "layout" );

      // we'd better share the input stream to save the memory
      if ( xmlLayout != null )
      {
        byte[] xmlLayoutBytes = xmlLayout.getBytes( "UTF-8" );
        xmlIn = new ByteArrayInputStream( xmlLayoutBytes );
      }
      else
      {
        xmlIn = request.getInputStream();
        xmlIn = createResetableInputStream( xmlIn );

      }

      response.setCharacterEncoding( "utf-8" );
      response.setContentType( "text/plain" );

      // write the content on response output stream
      return null;

    }
    catch ( Throwable e )
    {
      String debugToken = "[ " + System.currentTimeMillis() + " ]";
      log.error( debugToken, e );
      response.sendError( HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error. " + debugToken );
    }

    return null;
  }

  /*
   * create a new InputStream which markSupported/reset
   */
  protected static InputStream createResetableInputStream( InputStream in ) throws java.io.IOException
  {
    if ( in.markSupported() )
      return in;

    // read data from this input stream and use ByteArrayInputStream
    // we are using a large enough memory right now,
    // think about better solution later
    final int BUF_LEN = 4096;
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    byte[] data = new byte[BUF_LEN];
    int readLen = 0;
    while ( true )
    {
      readLen = in.read( data, 0, data.length );
      if ( readLen < 0 )
      {
        // end of stream;
        break;
      }
      else
      {
        os.write( data, 0, readLen );
      }
    }

    data = os.toByteArray();
    log.debug( "---- the data length: " + data.length );
    log.debug( "---- the data content:\n " + new String( data, 0, data.length ) );
    return ( new ByteArrayInputStream( data, 0, data.length ) );
  }

  public static void dumpSession( HttpSession session )
  {
    String info = "****** Http Session: " + "\t\nsession = " + session + "\t\nsessionId = " + session.getId();
    Enumeration names = session.getAttributeNames();
    while ( names.hasMoreElements() )
    {
      String name = (String) names.nextElement();
      Object attr = session.getAttribute( name );
      info += "\t\nname=" + name + "; value=" + attr;
    }

    log.debug( info );

  }

  protected String formXmlField( String key, String value )
  {
    if ( key == null || value == null )
      return "";
    return "<" + key + ">" + value + "</" + key + ">";
  }

  protected void sendXmlResponse( HttpServletResponse response, String xmlString )
  {
    if ( response == null || xmlString == null )
    {
      log.error( "sendXmlResponse: input parameter response/xmlString is null." );
      return;
    }

    try
    {
      // the xmlString MUST NOT null.
      byte[] xmlData = xmlString.getBytes( "utf-8" );

      response.setContentType( "text/xml" );
      response.setCharacterEncoding( "utf-8" );

      response.setContentLength( xmlData.length );
      response.getOutputStream().write( xmlData );
      response.getOutputStream().flush();
    }
    catch ( Exception e )
    {
      log.error( e.toString() );
    }
  }

  public String getId()
  {
    return id;
  }

  public void setId( String id )
  {
    this.id = id;
  }

  public String getDocumentName()
  {
    return documentName;
  }

  public void setDocumentName( String documentName )
  {
    this.documentName = documentName;
  }

  public String getFileLink()
  {
    return fileLink;
  }

  public void setFileLink( String fileLink )
  {
    this.fileLink = fileLink;
  }

  public String getFileName()
  {
    return fileName;
  }

  public void setFileName( String fileName )
  {
    this.fileName = fileName;
  }

  public String getDocid()
  {
    return docid;
  }

  public void setDocid( String docid )
  {
    this.docid = docid;
  }

  public String getDefaultPageLayoutFilePath()
  {
    return defaultPageLayoutFilePath;
  }

  public void setDefaultPageLayoutFilePath( String defaultPageLayoutFilePath )
  {
    this.defaultPageLayoutFilePath = defaultPageLayoutFilePath;
  }


  public UserService getUserService()
  {
    return userService;
  }

  public void setUserService( UserService userService )
  {
    this.userService = userService;
  }

  public EmailService getEmailService()
  {
    return emailService;
  }

  public void setEmailService( EmailService emailService )
  {
    this.emailService = emailService;
  }

}
