package cg.usermodule.service;

import java.util.List;

import cg.usermodule.dao.RoleDAO;
import cg.usermodule.model.Role;

public class RoleServiceImpl implements RoleService {
	
	private RoleDAO roleDAO;

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public List<Role> getAll() {
		return getRoleDAO().findAll();
	}

	public Role getById(long id) {
		 return getRoleDAO().findById(id);
	}

	public Role getByRolename(String rolename) {
		return getRoleDAO().findByAuthority(rolename);
	}

	public void remove(long id) {
		getRoleDAO().remove(id);		
	}

	public void save(Role role) {
		getRoleDAO().save(role);		
	}

}
