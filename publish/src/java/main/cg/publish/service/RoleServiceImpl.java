/**
 * This unpublished source code contains trade secrets and copyrighted
 * materials that are the proprietary property of iseemedia, Inc.
 * Unauthorized use, copying or distribution of this source code or the
 * ideas contained herein is a violation of U.S. and international laws
 * and is strictly prohibited.
 *
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 *
 */

package cg.publish.service;

import java.util.List;

import cg.publish.dao.RoleDAO;
import cg.publish.model.Role;

/**
 * The implementation of Role Service, 
 * used as Business Facade class to access Role DAO.
 *
 * @author Charles Deng
 */
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
