package cg.usermodule.service;

import java.util.List;

import cg.usermodule.model.Group;

public interface GroupService {
	
	public List<Group> getAll();
	
	public void save(Group group);
	
	public void remove(long id);
	
	public Group getById(long id);
	
	public Group getByGroupName(String groupName);

}
