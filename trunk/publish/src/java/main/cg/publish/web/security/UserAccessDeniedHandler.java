package cg.publish.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.util.PortResolver;
import org.springframework.security.util.PortResolverImpl;
import org.springframework.security.ui.AccessDeniedHandler;
import org.springframework.security.util.RedirectUrlBuilder;
// import org.springframework.security.ui.AccessDeniedHandler;
// import org.springframework.security.util.PortResolver;
// import org.springframework.security.util.PortResolverImpl;
// import org.springframework.security.util.RedirectUrlBuilder;

import cg.common.logging.Logger;

public class UserAccessDeniedHandler implements AccessDeniedHandler
{
  public static final String SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY = "SPRING_SECURITY_403_EXCEPTION";
  private static final Logger log = Logger.getLogger( UserAccessDeniedHandler.class );

  protected String loginPage = "/login.jsp";
  protected PortResolver portResolver = new PortResolverImpl();

// spring security 3.x interface  
//  public void handle( HttpServletRequest request, HttpServletResponse response,
//                      AccessDeniedException accessDeniedException ) throws IOException, ServletException

  public void handle( ServletRequest request, ServletResponse response,
                      AccessDeniedException accessDeniedException ) throws IOException, ServletException
  {
    log.debug( "PublishAccessDeniedHandler::handle()" );
    log.debug( "accessDeniedException message: " + accessDeniedException.getMessage() );
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // we don't want to delegate to the error page
    httpRequest.getSession().setAttribute( SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY, accessDeniedException );

    // forward to the login page
    String redirectUrl = buildRedirectUrlToLoginPage( httpRequest, httpResponse );
    log.debug( "redirectUrl = " + redirectUrl );
    httpResponse.sendRedirect( httpResponse.encodeRedirectURL( redirectUrl ) );
  }

  protected String buildRedirectUrlToLoginPage( HttpServletRequest request, HttpServletResponse response )
  {
    String loginForm = loginPage;
    int serverPort = portResolver.getServerPort( request );
    String scheme = request.getScheme();

    RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();

    urlBuilder.setScheme( scheme );
    urlBuilder.setServerName( request.getServerName() );
    urlBuilder.setPort( serverPort );
    urlBuilder.setContextPath( request.getContextPath() );
    urlBuilder.setPathInfo( loginForm );
    return urlBuilder.getUrl();
  }

  public String getLoginPage()
  {
    return loginPage;
  }

  public void setLoginPage( String loginPage )
  {
    this.loginPage = loginPage;
  }
}