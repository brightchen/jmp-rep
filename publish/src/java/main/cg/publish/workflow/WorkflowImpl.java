/*
 * $Id: WorkflowImpl.java,v 1.1 2007/05/30 20:30:46 dorelv Exp $
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
 * Class that implements Interface Document Workflow
 * @author dorel vleju
 * $Id: WorkflowImpl.java,v 1.1 2007/05/30 20:30:46 dorelv Exp $
 */
public class WorkflowImpl implements Workflow{
		
	static{
		WorkflowActionImpl.setActionTargets();
	}
	
	public WorkflowStep getInitialStep() throws Exception{
		return WorkflowStepImpl.NEW;
	}
	
	public WorkflowAction[] getAvailableActions(WorkflowStep currentStep, Role[] userRoles) throws Exception{
		
		// FIXME: filter out roles
		if (currentStep != null)
			return currentStep.getStepActions();
		
		// FIXME: should we return an empty WorkflowAction[0] ??
		return null;
	}
	
	public WorkflowStep executeAction(WorkflowStep currentStep, WorkflowAction workflowAction, Role[] userRoles) throws Exception{
		
		// 0. validate parameters
		
		if (userRoles == null)
			throw new IllegalArgumentException("User Roles cannot be null.");
		
		if (currentStep == null)
			throw new IllegalArgumentException("Current Step cannot be null.");
		
		if (currentStep.getStepActions() == null)
			throw new IllegalArgumentException("Current Step Actions cannot be null.");
		
		if (workflowAction == null)
			throw new IllegalArgumentException("Requested Action cannot be null.");
		
		if (workflowAction.getTargetStep() == null)
			throw new IllegalArgumentException("Requested Action has null Target Step.");
		
		if (workflowAction.getEnablingRoles() == null)
			throw new IllegalArgumentException("Requested Action has null Enabling Roles.");
		
		// 1. check if current step contains the requested action
		boolean hasAction = false;
		for (WorkflowAction action: currentStep.getStepActions()){
			if (action.equals(workflowAction)){
				hasAction = true;
				break;
			}
		}
		
		if (!hasAction)
			throw new IllegalArgumentException("Current Step does not have requested action.");
		
		// 2. check if user is in role
		boolean hasRole = false;
		for (Role role: workflowAction.getEnablingRoles()){
			for (Role userRole: userRoles){
				if (role.equals(userRole)){
					hasRole = true;
					break;
				}
			}
		}
		
		if (!hasRole)
			throw new IllegalArgumentException("User does not have Enabling role to execute requested action.");
		
		// 3. return the new step (of the requested action)
		
		// It is caller's job to save the new Step!!!
		return workflowAction.getTargetStep();
		
	}
	
}
