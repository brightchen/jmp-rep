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

import cg.publish.model.Role;

/**
 * Interface for Role Service.
 *
 * @author Charles Deng
 */
public interface RoleService {
	
	public List<Role> getAll();
	
	public void save(Role role);
	
	public void remove(long id);
	
	public Role getById(long id);
	
	public Role getByRolename(String Rolename);

}
