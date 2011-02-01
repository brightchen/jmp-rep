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

package cg.usermodule.dao;

import cg.usermodule.model.Role;

/**
 * Interface for Role DAO
 *
 * @author Charles Deng
 */
public interface RoleDAO extends GenericDAO<Role, Long> {
	
	public Role findByAuthority(String authority);

}
