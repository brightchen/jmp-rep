/*
 * $Id: WorkflowAction.java,v 1.1 2007/05/30 20:30:45 dorelv Exp $
 *
 * This unpublished source code contains trade secrets and copyrighted
 * materials that are the proprietary property of iseemedia, Inc.
 * Unauthorized use, copying or distribution of this source code or the
 * ideas contained herein is a violation of U.S. and international laws
 * and is strictly prohibited.
 *
 * Copyright (c) 2005 iseemedia, Inc. All Rights Reserved.
 *
 */

package cg.publish.workflow;

import cg.publish.model.Role;


/*
 * Interface for the Document WorkflowAction
 * @author dorel vleju
 * $Id: WorkflowAction.java,v 1.1 2007/05/30 20:30:45 dorelv Exp $
 */
public interface WorkflowAction{
		
	public WorkflowStep getTargetStep();
	public Role[] getEnablingRoles();
	public String getName();
	public int getId();
}
