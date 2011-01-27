/*
 * $Id: WorkflowStep.java,v 1.1 2007/05/30 20:30:46 dorelv Exp $
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


/*
 * Interface for the Document WorkflowStep
 * @author dorel vleju
 * $Id: WorkflowStep.java,v 1.1 2007/05/30 20:30:46 dorelv Exp $
 */
public interface WorkflowStep{
		
	public WorkflowAction[] getStepActions();
	public String getName();
	public int getId();
	
}
