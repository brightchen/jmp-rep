/*
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

import cg.publish.model.Group;

/**
 * Interface for Group Service.
 *
 * @author Charles Deng
 */
public interface GroupService {
	
	public List<Group> getAll();
	
	public void save(Group group);
	
	public void remove(long id);
	
	public Group getById(long id);
	
	public Group getByGroupName(String groupName);

}
