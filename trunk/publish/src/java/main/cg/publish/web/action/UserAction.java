package cg.publish.web.action;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import cg.publish.model.Role;
import cg.publish.model.User;
import cg.publish.service.RoleService;
import cg.publish.service.UserService;
import cg.publish.web.util.Constants;
import cg.publish.web.util.PublisherUtil;
import cg.publish.web.util.Validation;

import com.opensymphony.xwork2.Preparable;
import cg.common.email.EmailService;
import cg.common.logging.Logger;

/**
 * Provide CRUD and list functions for publisher user management.
 * 
 */
public class UserAction extends BaseAction implements Preparable
{

  private static final long serialVersionUID = 10987446557L;

  private static final Logger log = Logger.getLogger( UserAction.class );

  private List< User > userList;
  private User user;
  private String id;
  private String password2;
  private String inputOldPassword = "";
  private List< String > availableRoles;
  private List< String > availableRolesForDisplay;
  private List< String > currentRoles;
  private List< String > currentRolesForDisplay;
//  private List< String > availablePublishers;
//  private List< String > currentPublishers;
//  private String selectedPublisher;

  private EmailService emailService;
  private RoleService roleService;
  private UserService userService;

  private boolean userDeletable = true;

  private boolean sysAdmin;

  public void prepare() throws Exception
  {
    clearErrorsAndMessages();
  }

  public String input()
  {

    availableRoles = getAllRoleStringList( null );
    availableRoles = filtAvailableRoles( availableRoles );
    availableRolesForDisplay = parseRoleNameForDisplay( availableRoles );

    currentRoles = filtCurrentRoles( null );
    currentRolesForDisplay = parseRoleNameForDisplay( currentRoles );

    if ( id != null && id.length() > 0 )
    {
      setTask( Constants.UPDATE );
      if ( id.equals( "0" ) )
      {
        user = getLoginPublisherUser();
        setWorkedPublisherUser( user );
        sysAdmin = isAdminUser( getWorkedPublisherUser() );
      }
      else
      {
        user = getPublisherUserService().getById( Long.valueOf( id ) );
        setWorkedPublisherUser( user );
        sysAdmin = isAdminUser( getWorkedPublisherUser() );

        availableRoles = getAllRoleStringList( user );
        currentRoles = getCurrentUserRoleStringList( user );
        checkUserDeletable( currentRoles );
        for ( int i = 0; i < currentRoles.size(); i++ )
        {
          availableRoles.remove( currentRoles.get( i ) );
        }

        availableRolesForDisplay = parseRoleNameForDisplay( availableRoles );
        currentRolesForDisplay = parseRoleNameForDisplay( currentRoles );

      }
      log.debug( " *** Old publisherUser will be updated, publisherUser.getId() -> " + user.getId() );
    }
    else
    {
      setTask( Constants.CREATE );
      user = new User();
      log.debug( " *** New publisherUser is created -> " + user );
    }
    return SUCCESS;
  }

  private List< String > filtAvailableRoles( List< String > availableRoles )
  {
    if ( availableRoles == null || availableRoles.size() == 0 )
      return availableRoles;

    // right now, we only filt the user role
    String filtedRoleString = ( isSystemUser( null ) ? Constants.ROLE_SYSUSER : Constants.ROLE_USER );
    for ( int index = 0; index < availableRoles.size(); ++index )
    {
      String roleString = availableRoles.get( index );
      if ( roleString.equalsIgnoreCase( filtedRoleString ) )
      {
        availableRoles.remove( index );
        break;
      }
    }

    return availableRoles;
  }

  private List< String > filtCurrentRoles( List< String > currentRoles )
  {
    if ( currentRoles == null )
    {
      currentRoles = new ArrayList< String >();
    }
    String addRoleString = ( isSystemUser( null ) ? Constants.ROLE_SYSUSER : Constants.ROLE_USER );
    if ( currentRoles.size() == 0 )
    {
      currentRoles.add( addRoleString );
      return currentRoles;
    }

    // check if the user role already added
    for ( String roleString : currentRoles )
    {
      if ( roleString.equalsIgnoreCase( addRoleString ) )
      {
        return currentRoles;
      }
    }

    currentRoles.add( addRoleString );
    return currentRoles;
  }

  private void checkUserDeletable( List< String > roles )
  {
    if ( roles == null || roles.size() == 0 )
      return;

    for ( String roleString : roles )
    {
      if ( roleString != null
           && ( roleString.equalsIgnoreCase( Constants.ROLE_ADMIN ) || roleString
               .equalsIgnoreCase( Constants.ROLE_SYSADMIN ) ) )
      {
        setUserDeletable( false );
        return;
      }
    }
  }

//  private List< String > getAllRoleStringListVirtual()
//  {
//    List< String > list = new ArrayList< String >();
//    list.add( Constants.ROLE_ADMIN );
//    list.add( Constants.ROLE_USER );
//    list.add( Constants.ROLE_OPERATOR );
//    list.add( Constants.ROLE_EDITOR );
//    list.add( Constants.ROLE_REVIEWER );
//    list.add( Constants.ROLE_MARKETMGR );
//    return list;
//  }

  private List< String > parseRoleNameForDisplay( List< String > roles )
  {
    List< String > list = new ArrayList< String >();
    for ( int i = 0; i < roles.size(); i++ )
    {
      if ( roles.get( i ).equals( Constants.ROLE_OPERATOR ) )
        list.add( "content manager" );
      else if ( roles.get( i ).equals( Constants.ROLE_MARKETMGR ) )
        list.add( "market manager" );
      else if ( roles.get( i ).equals( Constants.ROLE_PUBLSHERMGR ) )
        list.add( "publisher manager" );
      else if ( roles.get( i ).equals( Constants.ROLE_VIRTUAL_PUBLSHER ) )
        list.add( "virtual publisher" );
      else if ( roles.get( i ).equals( Constants.ROLE_REPORTMGR ) )
        list.add( "report manager" );
      else if ( roles.get( i ).equals( Constants.ROLE_ACCESSMGR ) )
        list.add( "access manager" );
      else if ( roles.get( i ).equals( Constants.ROLE_SUBSCRIBERMGR ) )
        list.add( "subscriber manager" );
      else if ( roles.get( i ).equals( Constants.ROLE_FTPMGR ) )
        ;
      else
        list.add( roles.get( i ) );
    }
    return list;
  }

  private List< String > parseRoleNameForDB( List< String > roles )
  {
    List< String > list = new ArrayList< String >();
    for ( int i = 0; i < roles.size(); i++ )
    {
      if ( roles.get( i ).equals( "content manager" ) )
        list.add( Constants.ROLE_OPERATOR );
      else if ( roles.get( i ).equals( "market manager" ) )
        list.add( Constants.ROLE_MARKETMGR );
      else if ( roles.get( i ).equals( "publisher manager" ) )
        list.add( Constants.ROLE_PUBLSHERMGR );
      else if ( roles.get( i ).equals( "virtual publisher" ) )
        list.add( Constants.ROLE_VIRTUAL_PUBLSHER );
      else if ( roles.get( i ).equals( "report manager" ) )
        list.add( Constants.ROLE_REPORTMGR );
      else if ( roles.get( i ).equals( "access manager" ) )
        list.add( Constants.ROLE_ACCESSMGR );
      else if ( roles.get( i ).equals( "subscriber manager" ) )
        list.add( Constants.ROLE_SUBSCRIBERMGR );
      else
        list.add( roles.get( i ) );
    }
    return list;
  }

  private List< String > getCurrentUserRoleStringList( User user )
  {
    List< String > list = new ArrayList< String >();
    Set< Role > roleSet = user.getRoles();
    log.debug( " *** PublisherUserAction.getCurrentUserRoleStringList(),  roleSet.size() -> " + roleSet.size() );
    Role[] roleArray = roleSet.toArray( new Role[0] );
    log.debug( " *** PublisherUserAction.getCurrentUserRoleStringList(),  roleArray.length -> " + roleArray.length );
    for ( int i = 0; i < roleArray.length; i++ )
    {
      list.add( roleArray[i].getAuthority() );
    }
    return list;
  }

  private boolean isSystemUser( User user )
  {
    return Constants.PUBLISHER_SYSTEM.equals( user.getUsername() );
  }

  private List< String > getAllRoleStringList( User user )
  {
    List< String > list = new ArrayList< String >();
//    if ( publisherUser == null
//         && getLoginPublisherUser().getPublisher().getPublishername().equals( Constants.PUBLISHER_SYSTEM )
//         || publisherUser != null
//         && publisherUser.getPublisher().getPublishername().equals( Constants.PUBLISHER_SYSTEM ) )
    {
      list.add( Constants.ROLE_ACCESSMGR );
      list.add( Constants.ROLE_PUBLSHERMGR );
      list.add( Constants.ROLE_REPORTMGR );
      list.add( Constants.ROLE_SUBSCRIBERMGR );
      list.add( Constants.ROLE_SYSADMIN );
      list.add( Constants.ROLE_SYSUSER );
      list.add( Constants.ROLE_VIRTUAL_PUBLSHER );
    }
//    else
//    {
//      list.add( Constants.ROLE_ADMIN );
//      list.add( Constants.ROLE_OPERATOR );
//      list.add( Constants.ROLE_EDITOR );
//      list.add( Constants.ROLE_MARKETMGR );
//      list.add( Constants.ROLE_REVIEWER );
//      list.add( Constants.ROLE_USER );
//    }
    return list;
  }

  @SuppressWarnings( "unchecked")
  public void setWorkedPublisherUser( User publisherUser )
  {
    log.debug( "setWorkedPublisherUser:" );
    dumpSession();
    getSession().put( Constants.WORKED_PUBLISHER_USER_KEY, publisherUser );
  }

  public User getWorkedPublisherUser()
  {
    User user = (User) getSession().get( Constants.WORKED_PUBLISHER_USER_KEY );
    return user;
  }

  public String create() throws Exception
  {
    log.debug( " *** Entering PublisherUserAction.create() " );
    if ( !password2.equals( user.getPassword() ) )
    {
      addActionError( Constants.PASSWORD_MISMATCH );
      User cachedPublisherUser = user; // don't want to lost previously entered value
      List< String > cachedAvailableRoles = availableRolesForDisplay;
      List< String > cachedcurrentRoles = currentRolesForDisplay;
      input(); // since input() will clean up and regenerated list of

      user = cachedPublisherUser;
      availableRolesForDisplay = cachedAvailableRoles;
      currentRolesForDisplay = cachedcurrentRoles;
      return INPUT;
    }

    if ( !Validation.isEmpty( user.getFirstname() ) )
    {
      if ( Validation.isNotAlpha( user.getFirstname() ) )
      {
        addActionError( Constants.FIRSTNAME_HAS_NON_ALPHABET );
        User cachedPublisherUser = user;
        List< String > cachedAvailableRoles = availableRolesForDisplay;
        List< String > cachedcurrentRoles = currentRolesForDisplay;
        input();

        user = cachedPublisherUser;
        availableRolesForDisplay = cachedAvailableRoles;
        currentRolesForDisplay = cachedcurrentRoles;
        return INPUT;
      }
    }

    if ( !Validation.isEmpty( user.getLastname() ) )
    {
      if ( Validation.isNotAlpha( user.getLastname() ) )
      {
        addActionError( Constants.LASTNAME_HAS_NON_ALPHABET );
        User cachedPublisherUser = user;
        List< String > cachedAvailableRoles = availableRolesForDisplay;
        List< String > cachedcurrentRoles = currentRolesForDisplay;
        input();

        user = cachedPublisherUser;
        availableRolesForDisplay = cachedAvailableRoles;
        currentRolesForDisplay = cachedcurrentRoles;
        return INPUT;
      }
    }

    User u = getPublisherUserService().getByUsername( user.getUsername().trim() );
    if ( u == null )
    {
      if ( currentRolesForDisplay != null && currentRolesForDisplay.size() > 0 )
      {
        currentRoles = parseRoleNameForDB( currentRolesForDisplay );
        for ( int i = 0; i < currentRoles.size(); i++ )
        {
          log.debug( " *** currentRoles.get(i) -> " + currentRoles.get( i ) );
//          user.addRole( getRoleService().getByRolename( currentRoles.get( i ) ) );
        }
      }
      else
      {
        addActionError( "Please select at least one role." );
        input();
        return INPUT;
      }

      user.setPassword( PublisherUtil.encodePassword( password2, "SHA" ) );
      getPublisherUserService().save( user );
    }
    else
    {
      addActionError( Constants.USERNAME_EXIST );
      input();
      return INPUT;
    }

    // send email notification to the new user
    sendEmailNotice( user.getEmail(), user.getUsername(), password2,
                     user.getFirstname() + " " + user.getLastname(), "",
                     "New account notification", "user.email" );
    return SUCCESS;
  }

  public String createVirtual() throws Exception
  {
    log.debug( " *** Entering PublisherUserAction.createVirtual() " );
    if ( !password2.equals( user.getPassword() ) )
    {
      addActionError( Constants.PASSWORD_MISMATCH );
      return INPUT;
    }
    User u = getPublisherUserService().getByUsername( user.getUsername().trim() );
    if ( u == null )
    {
      if ( currentRolesForDisplay != null && currentRolesForDisplay.size() > 0 )
      {
        currentRoles = parseRoleNameForDB( currentRolesForDisplay );
        for ( int i = 0; i < currentRoles.size(); i++ )
        {
          log.debug( " *** currentRoles.get(i) -> " + currentRoles.get( i ) );
//          user.addRole( getRoleService().getByRolename( currentRoles.get( i ) ) );
        }
      }
      else
      {
        addActionError( "Please select at least one role." );
        return INPUT;
      }
      user.setPassword( PublisherUtil.encodePassword( password2, "SHA" ) );
      getPublisherUserService().save( user );
    }
    else
    {
      addActionError( Constants.USERNAME_EXIST );
      return INPUT;
    }
    return SUCCESS;
  }

  public String createFtpUser() throws Exception
  {
    log.debug( " *** Entering PublisherUserAction.createFtpUser() " );
    if ( !password2.equals( user.getPassword() ) )
    {
      addActionError( Constants.PASSWORD_MISMATCH );
      return INPUT;
    }
    User u = getPublisherUserService().getByUsername( user.getUsername().trim() );
    if ( u == null )
    {
//      user.addRole( getRoleService().getByRolename( Constants.ROLE_FTPMGR ) );
      user.setPassword( password2 );
      getPublisherUserService().save( user );
    }
    else
    {
      addActionError( Constants.USERNAME_EXIST );
      return INPUT;
    }
    return SUCCESS;
  }

  public String update() throws Exception
  {
    log.debug( " *** Entering PublisherUserAction.update() " );
    log.debug( " *** publisherUser.getId() -> " + user.getId() );
    log.debug( " *** publisherUser.getUsername() -> " + user.getUsername() );

    String username = user.getUsername();
    if ( username != null && username.length() > 0 )
    {
      User userNew = getPublisherUserService().getByUsername( username );
      if ( userNew != null )
      {
        String[] ignoredProperties = new String[]
        { "id", "username" };
        if ( user.getPassword() == null || user.getPassword().length() < 1 )
        {
          ignoredProperties = new String[]
          { "id", "username", "password" };
        }
        BeanUtils.copyProperties( user, userNew, ignoredProperties );
        if ( currentRolesForDisplay != null && currentRolesForDisplay.size() > 0 )
        {
          currentRoles = parseRoleNameForDB( currentRolesForDisplay );
//          for ( int i = 0; i < currentRoles.size(); i++ )
//          {
//            userNew.addRole( getRoleService().getByRolename( currentRoles.get( i ) ) );
//          }
        }
        /*
         * if (currentPublishers != null && currentPublishers.size() > 0) { if (currentPublishers.size() == 1) { for
         * (String publisherStr : currentPublishers) {
         * publisherUser.addPublisher(publisherService.getByPublishername(publisherStr)); } } else if
         * (currentPublishers.size() > 1) { addActionError("Can not select more than one linked publisher."); return
         * INPUT; } }
         */

        if ( !Validation.isEmpty( user.getFirstname() ) )
        {
          if ( Validation.isNotAlpha( user.getFirstname() ) )
          {
            addActionError( Constants.FIRSTNAME_HAS_NON_ALPHABET );
            return INPUT;
          }
        }

        if ( !Validation.isEmpty( user.getLastname() ) )
        {
          if ( Validation.isNotAlpha( user.getLastname() ) )
          {
            addActionError( Constants.LASTNAME_HAS_NON_ALPHABET );
            return INPUT;
          }
        }

        getPublisherUserService().save( userNew );
      }
      else
      {
        log.error( " *** Can't update publisher user with name -> " + username );
        log.error( " *** The above publisher user does not exist in the database. " );
      }
    }
    else
    {
      log.error( " *** Can't update publisher user with name -> " + username );
    }
    return SUCCESS;
  }

  public String updateFtpUser() throws Exception
  {
    log.debug( " *** Entering PublisherUserAction.updateFtpUser() " );
    String username = user.getUsername();
    if ( username != null && username.length() > 0 )
    {
      User userNew = getPublisherUserService().getByUsername( username );
      if ( userNew != null )
      {
        String[] ignoredProperties = new String[]
        { "id", "username" };
        if ( user.getPassword() == null || user.getPassword().length() < 1 )
        {
          ignoredProperties = new String[]
          { "id", "username", "password" };
        }
        BeanUtils.copyProperties( user, userNew, ignoredProperties );
//        userNew.addRole( getRoleService().getByRolename( Constants.ROLE_FTPMGR ) );

        getPublisherUserService().save( userNew );
      }
      else
      {
        log.error( " *** Can't update FTP user with name -> " + username );
        log.error( " *** The above FTP user does not exist in the database. " );
      }
    }
    else
    {
      log.error( " *** Can't update FTP user with name -> " + username );
    }
    return SUCCESS;
  }

  public String execute()
  {
    return SUCCESS;
  }

  public String delete() throws Exception
  {
    if ( id != null )
    {
      getPublisherUserService().remove( Long.valueOf( id ) );
    }
    else
    {
      log.info( " *** In PublisherUserAction.delete(), can't delete publisherUser with id = null . " );
    }
    return SUCCESS;
  }

  public String list()
  {
    log.debug( " *** in PublisherUserAction.list() " );
//    userList = getPublisherUserService().getAllByPublisher( getLoginPublisherUser().getPublisher() );
    userList = splitFtpUserFromList( userList, false );
    ServletActionContext.getRequest().setAttribute( "publisherUsers", userList );
    return SUCCESS;
  }

//  public String listVirtual()
//  {
//    log.debug( " *** in PublisherUserAction.listVirtual() " );
//    if ( publisherSet.size() == 0 )
//    {
//      addActionError( getText( "no.access.virtual.publisher" ) );
//      return INPUT;
//    }
//    Publisher virtualPublisher = publisherSet.iterator().next();
//    ServletActionContext.getRequest().getSession().setAttribute( "virtualPublisher", virtualPublisher );
//    userList = getPublisherUserService().getAllByPublisher( virtualPublisher );
//    userList = splitFtpUserFromList( userList, false );
//    ServletActionContext.getRequest().setAttribute( "publisherUsers", userList );
//    // addActionMessage("Working on virtual publisher - " + virtualPublisher.getPublishername());
//    return SUCCESS;
//  }

  private List< User > splitFtpUserFromList( List< User > publisherUserList, boolean ftp )
  {
    List< User > ftpUsers = new ArrayList< User >();
    List< User > noFtpUsers = new ArrayList< User >();
    for ( User u : publisherUserList )
    {
      for ( Role r : u.getRoles() )
      {
        if ( r.getAuthority().equals( Constants.FtpmgrRoleName ) )
        {
          if ( !ftpUsers.contains( u ) )
            ftpUsers.add( u );
        }
      }
      if ( !ftpUsers.contains( u ) )
        noFtpUsers.add( u );
    }
    return ftp ? ftpUsers : noFtpUsers;
  }

  public String listFtpUsers()
  {
    log.debug( " *** in PublisherUserAction.list() " );
//    userList = getPublisherUserService().getAllByPublisher( getLoginPublisherUser().getPublisher() );
    userList = splitFtpUserFromList( userList, true );
    ServletActionContext.getRequest().setAttribute( "publisherUsers", userList );
    // ServletActionContext.getRequest().getSession().setAttribute("userList", userList);
    return SUCCESS;
  }

  // public String changePasswordInput() {
  // log.debug(" *** Entering PublisherUserAction.changePasswordInput() ");
  // return SUCCESS;
  // }

  private boolean isAdminUser( User u )
  {
    List< String > currentRoles = getCurrentUserRoleStringList( u );
    for ( int i = 0; i < currentRoles.size(); i++ )
    {
      if ( currentRoles.get( i ).equals( Constants.ROLE_SYSUSER )
           || currentRoles.get( i ).equals( Constants.ROLE_SYSADMIN ) )
      {
        return true;
      }
    }
    return false;
  }

  public String changePassword() throws NoSuchAlgorithmException
  {
    log.debug( " *** Entering PublisherUserAction.changePassword() " );
    String name = getWorkedPublisherUser().getUsername();

    try
    {
      User u = getPublisherUserService().getByUsername( name );

      if ( isAdminUser( u ) )
      {
        if ( inputOldPassword == null || inputOldPassword.length() == 0 )
        {
          addActionError( "Old password is required." );
          return INPUT;
        }

        // check old password
        String encodedOldPassword = PublisherUtil.encodePassword( inputOldPassword, "SHA" );
        if ( !u.getPassword().equals( encodedOldPassword ) )
        {
          addActionError( "Old password incorrect." );
          return INPUT;
        }
      }

      if ( !password2.equals( user.getPassword() ) )
      {
        addActionError( Constants.PASSWORD_MISMATCH );
        return INPUT;
      }

      String encodedPassword = PublisherUtil.encodePassword( password2, "SHA" );
      u.setPassword( encodedPassword );
      getPublisherUserService().save( u );
      setPublisherUser( u );
      availableRoles = getAllRoleStringList( u );
      currentRoles = getCurrentUserRoleStringList( u );
      for ( int i = 0; i < currentRoles.size(); i++ )
      {
        availableRoles.remove( currentRoles.get( i ) );
      }
      availableRolesForDisplay = parseRoleNameForDisplay( availableRoles );
      currentRolesForDisplay = parseRoleNameForDisplay( currentRoles );
      addActionMessage( getText( "publisherUserAction.password.changed" ) );
      setTask( Constants.UPDATE );

      sendEmailNotice( user.getEmail(), user.getUsername(), password2,
                       user.getFirstname() + " " + user.getLastname(), "", 
                       "Password Change notification", "userpassword.email" );
      input();
      return SUCCESS;
    }
    catch ( Exception ex )
    {
      addActionError( "Failed to change password: " + ex.getMessage() );
      return INPUT;
    }
  }

  public String changePwd() throws NoSuchAlgorithmException
  {
    log.debug( " *** Entering PublisherUserAction.changePwd() " );

    User u = getLoginPublisherUser();

    try
    {
      if ( isAdminUser( u ) )
      {
        if ( inputOldPassword == null || inputOldPassword.length() == 0 )
        {
          addActionError( "Old password is required." );
          return INPUT;
        }

        // check old password
        String encodedOldPassword = PublisherUtil.encodePassword( inputOldPassword, "SHA" );
        if ( !u.getPassword().equals( encodedOldPassword ) )
        {
          addActionError( "Old password incorrect." );
          return INPUT;
        }
      }

      if ( !password2.equals( user.getPassword() ) )
      {
        addActionError( Constants.PASSWORD_MISMATCH );
        return INPUT;
      }

      String encodedPassword = PublisherUtil.encodePassword( password2, "SHA" );
      u.setPassword( encodedPassword );
      getPublisherUserService().save( u );

      addActionMessage( getText( "publisherUserAction.password.changed" ) );
      return SUCCESS;
    }
    catch ( Exception ex )
    {
      addActionError( "Failed to change password: " + ex.getMessage() );
      return INPUT;
    }
  }

  public String changeFtpPassword()
  {
    log.debug( " *** Entering PublisherUserAction.changePassword() " );
    String name = getWorkedPublisherUser().getUsername();

    try
    {
      if ( !password2.equals( user.getPassword() ) )
      {
        addActionError( Constants.PASSWORD_MISMATCH );
        return INPUT;
      }

      User u = getPublisherUserService().getByUsername( name );
      u.setPassword( password2 );
      getPublisherUserService().save( u );
      setPublisherUser( u );
      addActionMessage( getText( "publisherUserAction.password.changed" ) );
      setTask( Constants.UPDATE );
      sendEmailNotice( user.getEmail(), user.getUsername(), password2,
                       user.getFirstname() + " " + user.getLastname(), "", 
                       "Password Change notification", "userpassword.email" );
      return SUCCESS;
    }
    catch ( Exception ex )
    {
      addActionError( "Failed to change password: " + ex.getMessage() );
      return INPUT;
    }
  }

  public User getPublisherUser()
  {
    return user;
  }

  public void setPublisherUser( User publisherUser )
  {
    this.user = publisherUser;
  }

  public String getId()
  {
    return id;
  }

  public void setId( String id )
  {
    this.id = id;
  }

  public UserService getPublisherUserService()
  {
    return userService;
  }

  public void setPublisherUserService( UserService userService )
  {
    this.userService = userService;
  }

  public List< User > getPublisherUserList()
  {
    return userList;
  }

  public void setPublisherUserList( List< User > userList )
  {
    this.userList = userList;
  }

  public String getPassword2()
  {
    return password2;
  }

  public void setPassword2( String password2 )
  {
    this.password2 = password2;
  }

  public List< String > getAvailableRoles()
  {
    return availableRoles;
  }

  public void setAvailableRoles( List< String > availableRoles )
  {
    this.availableRoles = availableRoles;
  }

  public List< String > getCurrentRoles()
  {
    return currentRoles;
  }

  public void setCurrentRoles( List< String > currentRoles )
  {
    this.currentRoles = currentRoles;
  }

  public RoleService getRoleService()
  {
    return roleService;
  }

  public void setRoleService( RoleService roleService )
  {
    this.roleService = roleService;
  }

  public List< String > getAvailableRolesForDisplay()
  {
    return ( availableRolesForDisplay == null ) ? ( new ArrayList< String >( 0 ) ) : availableRolesForDisplay;
  }

  public void setAvailableRolesForDisplay( List< String > availableRolesForDisplay )
  {
    this.availableRolesForDisplay = availableRolesForDisplay;
  }

  public List< String > getCurrentRolesForDisplay()
  {
    return currentRolesForDisplay;
  }

  public void setCurrentRolesForDisplay( List< String > currentRolesForDisplay )
  {
    this.currentRolesForDisplay = currentRolesForDisplay;
  }


  public void setInputOldPassword( String inputOldPassword )
  {
    this.inputOldPassword = inputOldPassword;
  }

  public EmailService getEmailService()
  {
    return emailService;
  }

  public void setEmailService( EmailService emailService )
  {
    this.emailService = emailService;
  }


  protected void sendEmailNotice( String email, String username, String userPassword, String userFullName,
                                String publisherName, String subject, String templateName )
  {
    try
    {
      emailService = getEmailService();
      if ( email != null && emailService.isEmailAddressValid( email ) )
      {

        Map< String, String > tokens = new HashMap< String, String >();
        tokens.put( "user.username", username );
        tokens.put( "user.password", userPassword );
        tokens.put( "user.fullname", userFullName );
        tokens.put( "publisher.name", publisherName );

        getEmailService().sendEmail( email, subject, templateName, tokens, "text/html" );
        log.debug( "Sent email To: " + email );
      } /*
         * else { log.error("Invalid email address: " + emailAddress); }
         */

    }
    catch ( Exception e )
    {
      log.error( "Cannot sent document status update by email.", e );
    }
  }

  public boolean isUserDeletable()
  {
    log.debug( "isUserDeletable(): " + userDeletable );
    return userDeletable;
  }

  public void setUserDeletable( boolean userDeletable )
  {
    this.userDeletable = userDeletable;
  }


  public boolean isSysAdmin()
  {
    return sysAdmin;
  }

  public void setSysAdmin( boolean sysAdmin )
  {
    this.sysAdmin = sysAdmin;
  }
}
