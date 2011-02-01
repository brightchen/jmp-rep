package cg.usermodule.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 * A User belong to multiple groups, and each group has multiple roles
 * 
 */
@SuppressWarnings( "unchecked")
@Entity
@Table( name = "TB_USER")
@javax.persistence.SequenceGenerator( name = "USER_ID_SEQUENCE" , sequenceName = "USER_ID_SEQUENCE" , initialValue = 1 , allocationSize = 1)
public class User implements Serializable, Comparable, UserDetails
{
  private static final long serialVersionUID = -3501084345042453213L;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
//  @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "USER_ID_SEQUENCE")
  @Column( name = "USER_ID")
  private Long id = null;


  @Column( name = "USERNAME" , length = 16 , nullable = false , unique = true , updatable = false)
  private String username;

  @Column( name = "PASSWORD" , length = 50 , nullable = false)
  private String password;

  @Column( name = "FIRSTNAME" , length = 50)
  private String firstname;

  @Column( name = "LASTNAME" , length = 50)
  private String lastname;

  @Column( name = "EMAIL" , length = 50)
  private String email;

  @Column( name = "PHONE_NUMBER" , length = 20)
  private String phoneNumber;

  @ManyToMany( fetch = FetchType.EAGER)
  @JoinTable( name = "USER_GROUP" , joinColumns =
  { @JoinColumn( name = "USER_ID") } , inverseJoinColumns = @JoinColumn( name = "GROUP_ID"))
  private Set< Group > groups = new HashSet< Group >();

  @Column( name = "ACCOUNT_EXPIRED" , nullable = false)
  private boolean accountExpired;

  @Column( name = "ACCOUNT_LOCKED" , nullable = false)
  private boolean accountLocked;

  @Column( name = "CREDENTIALS_EXPIRED" , nullable = false)
  private boolean credentialsExpired;

  @Column( name = "ENABLED" , nullable = false)
  private boolean enabled;

  public User()
  {
  }

  /**
   * Simple constructor.
   */
  public User( String username, String password, boolean accountExpired, boolean accountLocked,
      boolean credentialsExpired, boolean enabled )
  {

    this.username = username;
    this.password = password;
    this.accountExpired = accountExpired;
    this.accountLocked = accountLocked;
    this.credentialsExpired = credentialsExpired;
    this.enabled = enabled;
  }

  public Long getId()
  {
    return id;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername( String username )
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword( String password )
  {
    this.password = password;
  }

  public String getFirstname()
  {
    return firstname;
  }

  public void setFirstname( String firstname )
  {
    this.firstname = firstname;
  }

  public String getLastname()
  {
    return lastname;
  }

  public void setLastname( String lastname )
  {
    this.lastname = lastname;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail( String email )
  {
    this.email = email;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void setPhoneNumber( String phoneNumber )
  {
    this.phoneNumber = phoneNumber;
  }

  public Set< Group > getGroups()
  {
    return groups;
  }

  public void setGroups( Set< Group > groups )
  {
    this.groups = groups;
  }

  public void addGroup( Group group )
  {
    getGroups().add( group );
  }

  @Override
  public GrantedAuthority[] getAuthorities()
  {
    Collection< GrantedAuthority > authorities = new HashSet< GrantedAuthority >();
    Set< Group > groups = getGroups();
    for( Group group : groups )
    {
      authorities.addAll( group.getRoles() );
    }
    if( authorities == null || authorities.size() == 0 )
      return null;
    return authorities.toArray( new GrantedAuthority[ authorities.size() ] );
  }

//  Spring Security 3.x compatible  
//  @Override
//  public Collection< GrantedAuthority > getAuthorities()
//  {
//    Collection< GrantedAuthority > authorities = new HashSet< GrantedAuthority >();
//    Set< Group > groups = getGroups();
//    for( Group group : groups )
//    {
//      authorities.addAll( group.getRoles() );
//    }
//    return authorities;
//  }

  public boolean isAccountNonExpired()
  {
    return !isAccountExpired();
  }

  public boolean isAccountNonLocked()
  {
    return !isAccountLocked();
  }

  public boolean isCredentialsNonExpired()
  {
    return !isCredentialsExpired();
  }

  public boolean isAccountExpired()
  {
    return accountExpired;
  }

  public void setAccountExpired( boolean accountExpired )
  {
    this.accountExpired = accountExpired;
  }

  public boolean isAccountLocked()
  {
    return accountLocked;
  }

  public void setAccountLocked( boolean accountLocked )
  {
    this.accountLocked = accountLocked;
  }

  public boolean isCredentialsExpired()
  {
    return credentialsExpired;
  }

  public void setCredentialsExpired( boolean credentialsExpired )
  {
    this.credentialsExpired = credentialsExpired;
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled( boolean enabled )
  {
    this.enabled = enabled;
  }

  public boolean equals( Object obj )
  {
    if ( this == obj )
      return true;
    if ( !( obj instanceof User ) )
      return false;
    final User user = (User) obj;
    return getUsername().equals( user.getUsername() );
  }

  public int hashCode()
  {
    return getUsername().hashCode();
  }

  public String toString()
  {
    return "User id = " + getId();
  }

  public int compareTo( Object obj )
  {
    if ( obj instanceof User )
      return this.getId().compareTo( ( (User) obj ).getId() );
    return 0;
  }

  public Set< Role > getRoles()
  {
    Set< Group > groups = getGroups();
    if( groups == null || groups.isEmpty() )
      return Collections.EMPTY_SET;
    Set< Role > roles = new HashSet< Role >();
    for( Group group : groups )
    {
      roles.addAll( group.getRoles() );
    }
    return roles;
  }
}
