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

package cg.usermodule.dao.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cg.usermodule.dao.GenericDAO;

/**
 * The Generic JPA DAO. The EntityManager is injected by Spring.
 *
 * @author Charles Deng
 */
public abstract class GenericJpaDAO<T,ID extends Serializable> implements GenericDAO<T, ID> {
	

	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    EntityManager getEntityManager() {
        if (em == null)
            throw new IllegalStateException("EntityManager has not been set on DAO before usage");
        return em;
    }

}
