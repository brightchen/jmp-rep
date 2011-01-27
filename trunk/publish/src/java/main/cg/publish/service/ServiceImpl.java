package cg.publish.service;

import java.util.List;

import cg.publish.dao.UserDAO;
import cg.publish.model.User;

/**
 * The implementation of PublisherUser Service, used as Business Facade class to access PublisherUser DAO.
 * 
 */
public class ServiceImpl implements UserService
{

  private UserDAO publisherUserDAO;

  public UserDAO getPublisherUserDAO()
  {
    return publisherUserDAO;
  }

  public void setPublisherUserDAO( UserDAO publisherUserDAO )
  {
    this.publisherUserDAO = publisherUserDAO;
  }

  public List< User > getAll()
  {
    return getPublisherUserDAO().findAll();
  }

  public User getById( long id )
  {
    return getPublisherUserDAO().findById( id );
  }

  public User getByUsername( String username )
  {
    return getPublisherUserDAO().findByUsername( username );
  }

  public void remove( long id )
  {
    getPublisherUserDAO().remove( id );
  }

  public void save( User user )
  {
    getPublisherUserDAO().save( user );
  }

}
