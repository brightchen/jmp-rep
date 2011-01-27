package cg.publish.web.action;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cg.common.logging.Logger;
import cg.publish.web.util.Constants;

public class AuthorizationFilter implements Filter
{
  Logger log = Logger.getLogger( AuthorizationFilter.class );

  boolean enableAuthorization = true;
  HashMap< String, String > excludeUrls = null;
  FilterConfig filterConfig = null;
  Map< String, String > sequenceNumbers = new HashMap< String, String >();

  public void destroy()
  {
  }

  public void doFilter( ServletRequest request, ServletResponse response, FilterChain filterChain ) throws IOException,
      ServletException
  {
    HttpServletRequest req = null;

    if ( request instanceof HttpServletRequest )
    {
      req = (HttpServletRequest) request;
      log.info( "getQueryString() = " + req.getQueryString() );
      log.info( "getRequestURL() = " + req.getRequestURL() );

    }

    log.info( "AuthorizationFilter.doFilter()" );

    if ( enableAuthorization && !isURLExcluded( req ) )
    {

      boolean failed = true;

      String phoneId = request.getParameter( "phoneid" );
      String timeStamp = request.getParameter( "timestamp" );
      String number = request.getParameter( "number" );

      String key = request.getParameter( "key" );

      Object publisherUser = req.getSession().getAttribute( (String) Constants.PUBLISHER_USER_KEY );

      log.info( "Authorization is enabled..." );

      if ( publisherUser != null )
      {
        log.info( "Client is portal user : " + publisherUser + ", no need to do device authorization" );
        failed = false;

      }
      else
      {
        log.info( "Authorizing client with following parameters: phoneid=" + phoneId + ",timestamp=" + timeStamp
                   + ",number=" + number + ",key=" + key );
      }

      if ( failed )
      {

        log.info( "Authorization failed for phoneid :" + phoneId );

        if ( req != null )
        {
          log.error( "publisheruser in the session : "
                      + req.getSession().getAttribute( (String) Constants.PUBLISHER_USER_KEY ) );
          Enumeration enumeration = req.getSession().getAttributeNames();

          if ( enumeration != null )
          {
            log.info( "Session Attributes are:" );
            while ( enumeration.hasMoreElements() )
            {
              String attKey = (String) enumeration.nextElement();
              log.info( attKey + " = " + req.getSession().getAttribute( attKey ) );
            }
          }
        }

        if ( response instanceof HttpServletResponse )
        {
          HttpServletResponse res = (HttpServletResponse) response;

          res.sendError( 527, "Authorization failed for this request." );
        }
        else
        {
          response.getOutputStream().write( "527 Authorization failed for this request.".getBytes() );
        }
        return;
      }
      else
      {
        filterChain.doFilter( request, response );
      }
    }
    else
    {
      log.info( "Authorization is disabled..." );
      filterChain.doFilter( request, response );
    }
  }

  public void init( FilterConfig filterConfig ) throws ServletException
  {
    log.info( "^^^^^^^^^^^^init(FilterConfig filterConfig) called ..." );
    String authorize = filterConfig.getInitParameter( "enableAuthorization" );
    if ( authorize != null
         && ( authorize.equalsIgnoreCase( "false" ) || authorize.equalsIgnoreCase( "no" ) || authorize
             .equalsIgnoreCase( "0" ) ) )
    {
      enableAuthorization = false;
    }

    this.filterConfig = filterConfig;
  }

  /*
   * public PhoneService getPhoneService() { return (PhoneService) getService("phoneService"); }
   * 
   * protected Object getService(String serviceName) throws BeansException { return
   * getWebApplicationContext().getBean(serviceName); }
   * 
   * protected WebApplicationContext getWebApplicationContext() {
   * 
   * return WebApplicationContextUtils.getWebApplicationContext(filterConfig .getServletContext());
   * 
   * }
   */

  public static byte[] getKeyedDigest( String buffer, String key )
  {
    return getKeyedDigest( buffer.getBytes(), key.getBytes() );
  }

  public static byte[] getKeyedDigest( byte[] buffer, byte[] key )
  {
    try
    {
      MessageDigest md5 = MessageDigest.getInstance( "MD5" );
      md5.update( buffer );
      return md5.digest();
    }
    catch ( NoSuchAlgorithmException e )
    {
    }
    return null;
  }

  protected boolean authorize( String phoneId, String timeStamp, String randomNumber, String authCode, String key,
                             String lastNum )
  {

    try
    {
      // long lastNumber = getLastLoginSequenceNumber(phoneId);
      long lastNumber = Long.parseLong( lastNum );

      log.info( "lastSequenceNumber for phoneid=" + phoneId + " was :" + lastNumber );

      long currentNumber = -1;

      try
      {
        currentNumber = Long.parseLong( randomNumber );
      }
      catch ( NumberFormatException e )
      {
        log.warn( "the value submitted for 'number' parameter is not a number [" + randomNumber + "]" );
        return false;
      }

      if ( currentNumber <= lastNumber )
      {
        log.warn( "current sequence number has already been used, rejecting the request." );
        return false;
      }
      else
      {
        String text = phoneId + timeStamp + randomNumber + authCode;

        String createdDigest = bin2hex( getKeyedDigest( text, "" ) );
        if ( createdDigest.equalsIgnoreCase( key ) )
        {
          log.info( "authorization successful for phoneid : " + phoneId );
          return true;
        }
        else
        {
          log.info( "authorization failed for phoneid : " + phoneId );
          return false;
        }
      }
    }
    catch ( Exception e )
    {
      e.printStackTrace();
      return false;
    }

  }

  private static String bin2hex( byte[] bytes )
  {
    String temp = "";
    StringBuffer hex = new StringBuffer( bytes.length );

    for ( int i = 0; i < bytes.length; i++ )
    {
      temp = Integer.toHexString( 0x0100 + ( bytes[i] & 0x00FF ) ).substring( 1 ).toUpperCase();
      hex.append( addLeadingZero( temp, 2 ) );
    }

    return hex.toString();
  }

  private static String addLeadingZero( String number, int digits )
  {
    if ( number.length() >= digits )
    {
      return number;
    }
    else
    {
      StringBuffer str = new StringBuffer( digits );

      while ( str.length() < digits - number.length() )
      {
        str.append( "0" );
      }

      str.append( number );

      return str.toString();
    }
  }

  private boolean isURLExcluded( HttpServletRequest req )
  {
    String queryString = req.getQueryString();

    if ( queryString != null )
    {
      queryString = queryString.toLowerCase();
    }
    String uri = req.getRequestURI().toLowerCase();

    String command = req.getParameter( "command" );
    log.info( "**Command: " + req.getParameter( "command" ) );

    if ( uri != null )
    {
      uri = uri.toLowerCase();
    }
    log.info( "Checking the URI : " + uri );
    log.info( "Checking if Url is excluded from authorization : " + req.getRequestURL() );

    if ( excludeUrls != null )
    {
      Set< String > keys = excludeUrls.keySet();
      Iterator< String > keyItr = keys.iterator();
      while ( keyItr != null && keyItr.hasNext() )
      {
        String key = keyItr.next();
        log.info( "Checking the Key to check: " + key );
        if ( uri.indexOf( key.toLowerCase() ) != -1 )
        {
          String values = excludeUrls.get( key );
          log.info( "Checking the Key to values: " + values );
          StringTokenizer tokenizer = new StringTokenizer( values, "," );
          while ( tokenizer.hasMoreElements() )
          {
            String token = tokenizer.nextToken().toLowerCase();
            log.info( "Checking the token: " + token );
            log.info( "Checking the uri: " + uri );
            if ( ( queryString != null && queryString.indexOf( token ) != -1 )
                 || ( uri != null && uri.indexOf( token ) != -1 )
                 || ( command != null && command.equalsIgnoreCase( token ) ) )
            {
              log.info( "Authorization is Enabled but URL is excluded from the authorization list" );
              return true;
            }

          }
        }
      }
    }
    return false;

  }

  public boolean isEnableAuthorization()
  {
    return enableAuthorization;
  }

  public void setEnableAuthorization( boolean enableAuthorization )
  {
    this.enableAuthorization = enableAuthorization;
  }

  public HashMap< String, String > getExcludeUrls()
  {
    return excludeUrls;
  }

  public void setExcludeUrls( HashMap< String, String > excludeUrls )
  {
    this.excludeUrls = excludeUrls;
  }

}
