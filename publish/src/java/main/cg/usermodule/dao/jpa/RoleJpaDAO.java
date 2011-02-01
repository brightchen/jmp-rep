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

package cg.usermodule.dao.jpa;

import java.util.List;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import cg.usermodule.dao.RoleDAO;
import cg.usermodule.model.Role;

/**
 * The Role JPA DAO. The transaction is annotated to method level.
 *
 * @author Charles Deng
 */
@Transactional
public class RoleJpaDAO extends GenericJpaDAO<Role, Long> implements RoleDAO {
	
	@SuppressWarnings("unchecked")
	public Role findByAuthority(String authority) {
		Query query = getEntityManager().createQuery("FROM Role r WHERE r.authority = ?1");
		query.setParameter(1, authority);
		List list = query.getResultList();
		if (list.size() > 0) {
			return (Role)list.get(0);
		} else return null;
	}

	@SuppressWarnings("unchecked")
	public List<Role> findAll() {
		Query query = getEntityManager().createQuery("FROM Role r ORDER BY r.id ASC");
		return query.getResultList();
	}

	public Role findById(Long id) {
		return getEntityManager().find(Role.class, id);
	}

	public void remove(Long id) {
		Role entity = findById(id);
		if (entity != null) getEntityManager().remove(entity);		
	}

	public void save(Role entity) {
		Long id = entity.getId();
		if (id == null || findById(id) == null) 
			getEntityManager().persist(entity);
		else getEntityManager().merge(entity);
	}

}
