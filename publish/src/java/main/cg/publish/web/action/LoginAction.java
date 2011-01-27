package cg.publish.web.action;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.struts2.ServletActionContext;
import cg.common.logging.Logger;
import cg.publish.model.User;
import cg.publish.service.EmailService;
import cg.publish.service.EmailTemplateService;
import cg.publish.service.UserService;
import cg.publish.web.util.Constants;
import cg.publish.web.util.PublisherUtil;

/**
 * Provide all the functions for a Publisher User login, logout and password retrieve.
 */
public class LoginAction extends BaseAction
{
  private static final long serialVersionUID = -478900937717469567L;
  private static final Logger log = Logger.getLogger( LoginAction.class );

  private UserService userService;
  private EmailTemplateService emailTemplateService;
  private EmailService emailService;
  
  private User user;
  private String username;
  private String phonenumber;

  public String execute()
  {
    log.debug( " *** In PublisherLoginAction.execute() " );
    return SUCCESS;
  }

  @SuppressWarnings( "unchecked")
  public String login() throws Exception
  {
    log.debug( " *** In PublisherLoginAction.login(), getUser().getUsername() -> " + getUser().getUsername() );
    User userFromDB = getUserService().getByUsername( getUser().getUsername() );
    if ( userFromDB != null && userFromDB.getPassword().equals( getUser().getPassword() ) )
    {
      setLoginPublisherUser( userFromDB );
    }
    else
    {
      addActionError( Constants.USER_MISMATCH );
      log.debug( " *** In PublisherLoginAction.login(), error message -> " + Constants.USER_MISMATCH );
      return INPUT;
    }
    return SUCCESS;
  }

  public String logout()
  {
    ServletActionContext.getRequest().getSession().invalidate();
    return SUCCESS;
  }

  public String input()
  {
    log.debug( " *** In PublisherLoginAction.input() " );
    return INPUT;
  }

  public String retrieveByUsername() throws MessagingException, NoSuchAlgorithmException, IOException
  {
    log.debug( " *** In LoginAction.retrieveByUsername() " );
    User user = userService.getByUsername( username );
    if ( user == null )
    {
      addActionError( getText( "error.user.not.exist" ) );
      log.info( " *** In LoginAction.retrieveByUsername(), user does not exist. " );
      return INPUT;
    }
    String emailTo = user.getEmail();
    String passwordNew = PublisherUtil.getRandomNumberAsStr( 6 );
    Map< String, String > properties = new HashMap< String, String >();
    properties.put( "username", username );
    properties.put( "password", passwordNew );
    String emailContent = emailTemplateService.getEmailContent( Constants.ForgotPassword_EmailTemplate, properties );
    emailService.sendMail( emailTo, "Your new password for the account of " + username, emailContent );
    user.setPassword( PublisherUtil.encodePassword( passwordNew, "SHA" ) );
    userService.save( user );
    addActionMessage( " Your new password has been sent to the email of " + emailTo );
    return SUCCESS;
  }

  public String retrieveByPhone()
  {
    log.debug( " *** In LoginAction.retrieveByPhone() " );
    addActionMessage( " Got phone number : " + phonenumber );
    return SUCCESS;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser( User user )
  {
    this.user = user;
  }

  public UserService getUserService()
  {
    return userService;
  }

  public void setUserService( UserService userService )
  {
    this.userService = userService;
  }

  public String getPhonenumber()
  {
    return phonenumber;
  }

  public void setPhonenumber( String phonenumber )
  {
    this.phonenumber = phonenumber;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername( String username )
  {
    this.username = username;
  }

  public EmailService getEmailService()
  {
    return emailService;
  }

  public void setEmailService( EmailService emailService )
  {
    this.emailService = emailService;
  }

  public EmailTemplateService getEmailTemplateService()
  {
    return emailTemplateService;
  }

  public void setEmailTemplateService( EmailTemplateService emailTemplateService )
  {
    this.emailTemplateService = emailTemplateService;
  }

}
