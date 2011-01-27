package cg.publish.service;

import java.util.List;

import cg.publish.model.User;

/**
 * Interface for PublisherUser Service.
 *
 */
public interface UserService {
	
	public List<User> getAll();
	
	public void save(User publisherUser);
	
	public void remove(long id);
	
	public User getById(long id);
	
	public User getByUsername(String username);

}
