package cg.usermodule.service;

import java.util.List;

import cg.usermodule.model.Role;

public interface RoleService {
	
	public List<Role> getAll();
	
	public void save(Role role);
	
	public void remove(long id);
	
	public Role getById(long id);
	
	public Role getByRolename(String Rolename);

}
