package cg.usermodule.service;

import java.util.List;

import cg.usermodule.dao.GroupDAO;
import cg.usermodule.model.Group;

public class GroupServiceImpl implements GroupService {

	private GroupDAO groupDAO;
	
	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}	
	
	public List<Group> getAll() {
		return groupDAO.findAll();
	}

	public Group getByGroupName(String groupName) {
		return groupDAO.findByGroupName(groupName);
	}

	public Group getById(long id) {
		return groupDAO.findById(id);
	}

	public void remove(long id) {
		groupDAO.remove(id);
	}

	public void save(Group group) {
		groupDAO.save(group);
	}	
	
}
