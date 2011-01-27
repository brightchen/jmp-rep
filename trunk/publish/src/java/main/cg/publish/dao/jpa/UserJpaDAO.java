package cg.publish.dao.jpa;

import java.util.List;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import cg.publish.dao.UserDAO;
import cg.publish.model.User;

/**
 * The Publisher User JPA DAO. The transaction is annotated to method level.
 */
@Transactional
public class UserJpaDAO extends GenericJpaDAO< User, Long > implements UserDAO, UserDetailsService
{
  @SuppressWarnings( "unchecked")
  public User findByUsername( String username )
  {
    Query query = getEntityManager().createQuery( "FROM PublisherUser u WHERE u.username = ?1" );
    query.setParameter( 1, username );
    List list = query.getResultList();
    if ( list.size() > 0 )
    {
      return (User) list.get( 0 );
    }
    else
      return null;
  }

  @SuppressWarnings( "unchecked")
  public List< User > findAll()
  {
    Query query = getEntityManager().createQuery( "FROM PublisherUser u ORDER BY u.username ASC" );

    return query.getResultList();
  }

  public User findById( Long id )
  {
    return getEntityManager().find( User.class, id );
  }

  public void remove( Long id )
  {
    User user = findById( id );
    if ( user != null )
      getEntityManager().remove( user );
  }

  public void save( User entity )
  {
    Long id = entity.getId();
    if ( id == null || findById( id ) == null )
      getEntityManager().persist( entity );
    else
      getEntityManager().merge( entity );
  }

  public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException, DataAccessException
  {
    User user = findByUsername( username );
    if ( user == null )
    {
      throw new UsernameNotFoundException( "Publisher User '" + username + "' not found..." );
    }
    else
    {
      // ServletActionContext.getRequest().getSession().setAttribute( Constants.PUBLISHER_USER_KEY, user );
      return (UserDetails) user;
    }
  }

}
