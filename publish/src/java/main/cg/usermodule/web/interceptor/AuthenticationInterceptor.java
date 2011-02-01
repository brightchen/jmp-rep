package cg.usermodule.web.interceptor;

// import org.springframework.security.context.SecurityContextHolder;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import cg.common.logging.Logger;

/**
 * The Authentication Interceptor for the Publisher User.
 * 
 */
public class AuthenticationInterceptor implements Interceptor
{
  private static final long serialVersionUID = 90420319545986431L;
  private static final Logger log = Logger.getLogger( AuthenticationInterceptor.class );

  public void destroy()
  {
  }

  public void init()
  {
  }

  @SuppressWarnings( "unchecked")
  public String intercept( ActionInvocation actionInvocation ) throws Exception
  {
//    Map session = actionInvocation.getInvocationContext().getSession();
//    Object PublisherUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    if ( PublisherUser instanceof PublisherUser )
//    {
//      if ( session.get( Constants.PUBLISHER_USER_KEY ) == null )
//      {
//        log.info( " *** Principal is instance of Publisher User, put in session now. " );
//        session.put( Constants.PUBLISHER_USER_KEY, PublisherUser );
//      }
//      else
//      {
//        log.info( " *** Principal is instance of Publisher User and already in session. " );
//      }
//      return actionInvocation.invoke();
//    }
//    else
    {
      log.info( " *** Principal is not instance of Publisher User, redirect to login " );
      return Action.LOGIN;
    }
  }

}
