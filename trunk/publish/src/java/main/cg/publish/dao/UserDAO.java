package cg.publish.dao;

import cg.publish.model.User;

public interface UserDAO extends GenericDAO< User, Long >
{
  public User findByUsername( String username );
}
