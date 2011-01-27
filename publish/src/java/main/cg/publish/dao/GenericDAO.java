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

package cg.publish.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for the Generic methods of all DAO
 *
 * @author Charles Deng
 */
public interface GenericDAO<T, ID extends Serializable> {
	
	public T findById(ID id);

	public List<T> findAll();
    
	public void save(T entity);
	
	public void remove(ID id);

}
