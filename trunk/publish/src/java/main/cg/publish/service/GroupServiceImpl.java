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

import cg.publish.dao.GroupDAO;
import cg.publish.model.Group;

/**
 * The implementation of Group Service, 
 * used as Business Facade class to access Group DAO.
 *
 * @author Charles Deng
 */
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
