package cg.usermodule.dao;

import cg.usermodule.model.User;

public interface UserDAO extends GenericDAO< User, Long >
{
  public User findByUsername( String username );
}
