package cg.publish.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.GrantedAuthority;

/**
 * A role in the Publish application.
 */
@SuppressWarnings( "unchecked")
@Entity
@Table( name = "ROLE")
@javax.persistence.SequenceGenerator( name = "ROLE_ID_SEQUENCE" , sequenceName = "ROLE_ID_SEQUENCE" , initialValue = 1 , allocationSize = 1)
public class Role implements Serializable, Comparable, GrantedAuthority
{

  private static final long serialVersionUID = -350108434504282467L;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
//  @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "ROLE_ID_SEQUENCE")
  @Column( name = "ROLE_ID")
  private Long id = null;

  @Column( name = "AUTHORITY" , length = 50 , nullable = false , unique = true , updatable = false)
  private String authority;

  public Role()
  {
  }

  public Role( String authority )
  {
    this.authority = authority;
  }

  public Long getId()
  {
    return id;
  }

  /**
   * public void setId(Long id) { this.id = id; }
   */

  public String getAuthority()
  {
    return authority;
  }

  public void setAuthority( String authority )
  {
    this.authority = authority;
  }

  public boolean equals( Object obj )
  {
    if ( this == obj )
      return true;
    if ( !( obj instanceof Role ) )
      return false;
    final Role role = (Role) obj;
    return getAuthority().equals( role.getAuthority() );
  }

  public int hashCode()
  {
    return getAuthority().hashCode();
  }

  public String toString()
  {
    return "Role id = " + getId();
  }

  public int compareTo( Object obj )
  {
    if ( obj instanceof Role )
      return this.getId().compareTo( ( (Role) obj ).getId() );
    return 0;
  }

}
