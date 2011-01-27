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

package cg.publish.dao.jpa;

import java.util.List;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import cg.publish.dao.GroupDAO;
import cg.publish.model.Group;

/**
 * The Group JPA DAO. The transaction is annotated to method level.
 *
 * @author Charles Deng
 */
@Transactional
public class GroupJpaDAO extends GenericJpaDAO<Group, Long> implements GroupDAO {
	
	@SuppressWarnings("unchecked")
	public Group findByGroupName(String groupName) {
		Query query = getEntityManager().createQuery("FROM Group g WHERE g.groupName = ?1");
		query.setParameter(1, groupName);
		List list = query.getResultList();
		if (list.size() > 0) {
			return (Group)list.get(0);
		} else return null;
	}

	@SuppressWarnings("unchecked")
	public List<Group> findAll() {
		Query query = getEntityManager().createQuery("FROM Group g ORDER BY g.id ASC");
		return query.getResultList();
	}

	public Group findById(Long id) {
		return getEntityManager().find(Group.class, id);
	}

	public void remove(Long id) {
		Group entity = findById(id);
		if (entity != null) getEntityManager().remove(entity);		
	}

	public void save(Group entity) {
		Long id = entity.getId();
		if (id == null || findById(id) == null) 
			getEntityManager().persist(entity);
		else getEntityManager().merge(entity);
	}

}
