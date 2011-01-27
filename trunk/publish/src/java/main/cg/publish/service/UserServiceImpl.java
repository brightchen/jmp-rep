package cg.publish.service;

import java.util.List;

import cg.publish.dao.UserDAO;
import cg.publish.model.User;

/**
 * The implementation of User Service, 
 * used as Business Facade class to access User DAO.
 */
public class UserServiceImpl implements UserService {
	
	private UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO( UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public List<User> getAll() {
		return getUserDAO().findAll();
	}
	
	public User getById(long id) {
		 return getUserDAO().findById(id);
	}

	public User getByUsername(String username) {
		return getUserDAO().findByUsername(username);
	}

	public void remove(long id) {
		getUserDAO().remove(id);		
	}

	public void save(User user) {
		getUserDAO().save(user);		
	}

}
