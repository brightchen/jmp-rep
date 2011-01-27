package cg.publish.model;

import java.io.Serializable;
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

@SuppressWarnings( "unchecked")
@Entity
@Table( name = "TB_GROUP")
@javax.persistence.SequenceGenerator( name = "GROUP_ID_SEQUENCE" , sequenceName = "GROUP_ID_SEQUENCE" , initialValue = 1 , allocationSize = 1)
public class Group implements Serializable, Comparable
{

  private static final long serialVersionUID = -350108434504282121L;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
//  @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "GROUP_ID_SEQUENCE")    //error when use derby
  @Column( name = "GROUP_ID")
  private Long id = null;

  @Column( name = "GROUP_NAME" , length = 50 , nullable = false , unique = true , updatable = false)
  private String groupName;

  @ManyToMany( fetch = FetchType.EAGER)
  @JoinTable( name = "TB_ROLE_GROUP" , joinColumns =
  { @JoinColumn( name = "GROUP_ID") } , inverseJoinColumns = @JoinColumn( name = "ROLE_ID"))
  private Set< Role > roles = new HashSet< Role >();

  public Group()
  {
  }

  public Group( String groupName )
  {
    this.groupName = groupName;
  }

  public Long getId()
  {
    return id;
  }

  public String getGroupName()
  {
    return groupName;
  }

  public void setGroupName( String groupName )
  {
    this.groupName = groupName;
  }

  public Set< Role > getRoles()
  {
    return roles;
  }

  public void setRoles( Set< Role > roles )
  {
    this.roles = roles;
  }

  public void addRole( Role role )
  {
    getRoles().add( role );
  }

  public boolean equals( Object obj )
  {
    if ( this == obj )
      return true;
    if ( !( obj instanceof Group ) )
      return false;
    final Group g = (Group) obj;
    return getGroupName().equals( g.getGroupName() );
  }

  public int hashCode()
  {
    return getGroupName().hashCode();
  }

  public String toString()
  {
    return "Group id = " + getId();
  }

  public int compareTo( Object obj )
  {
    if ( obj instanceof Group )
      return this.getId().compareTo( ( (Group) obj ).getId() );
    return 0;
  }

}
