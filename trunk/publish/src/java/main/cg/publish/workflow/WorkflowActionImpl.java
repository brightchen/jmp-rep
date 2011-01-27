/*
 * $Id: WorkflowActionImpl.java,v 1.1 2007/05/30 20:30:45 dorelv Exp $
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

import cg.common.logging.Logger;
import cg.publish.model.Role;


/*
 * Class that implements Interface Document WorkflowAction
 * @author dorel vleju
 * $Id: WorkflowActionImpl.java,v 1.1 2007/05/30 20:30:45 dorelv Exp $
 */
public class WorkflowActionImpl implements WorkflowAction{
		
	private static Logger _log = Logger.getLogger(WorkflowActionImpl.class);
	
	
	
	public static WorkflowActionImpl PREPARE = new WorkflowActionImpl(100, "Prepare", null, 
			new Role[]{new Role("content manager"), new Role("editor")});
	
	public static WorkflowActionImpl EDIT = new WorkflowActionImpl(200, "Edit", null, 
			new Role[]{new Role("reviewer"), new Role("content manager"), new Role("editor")});
	
	public static WorkflowActionImpl REVIEW = new WorkflowActionImpl(300, "Review", null,  
			new Role[]{new Role("reviewer"), new Role("content manager"), new Role("editor")});
	
	public static WorkflowActionImpl PUBLISH = new WorkflowActionImpl(400, "Publish", null,  
			new Role[]{new Role("content manager")});
	
	public static WorkflowActionImpl DELETE = new WorkflowActionImpl(500, "Delete", null,  
			new Role[]{new Role("content manager")});
	
	public static WorkflowActionImpl RETIRE = new WorkflowActionImpl(600, "Retire", null,  
			new Role[]{new Role("content manager")});
	
	private static boolean _isInitialized = false;
	
	// having Action depending of Step, and Step depending of Action static variables
	// will produce a chicken/egg situation
	public static void setActionTargets(){
		
		if(!_isInitialized){
			PREPARE.setTargetStep(WorkflowStepImpl.PREPARED);
			EDIT.setTargetStep(WorkflowStepImpl.EDITED);
			REVIEW.setTargetStep(WorkflowStepImpl.REVIEWED);
			PUBLISH.setTargetStep(WorkflowStepImpl.PUBLISHED);
			DELETE.setTargetStep(WorkflowStepImpl.DELETED);
			RETIRE.setTargetStep(WorkflowStepImpl.RETIRED);
			_isInitialized = true;
		}
	}
	
	Role[] _roles = null;
	WorkflowStep _targetStep = null;
	String _name = null;
	int _id = -1;
	
	public WorkflowActionImpl(int id, String name, WorkflowStep targetStep, Role[] roles) {
		_id = id;
		_name = name;
		_targetStep = targetStep;
		_roles = roles;	
		
	}

	public WorkflowStep getTargetStep(){
		return _targetStep;
	}
	
	public Role[] getEnablingRoles(){
		return _roles;
	}
    
	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof WorkflowAction)
			return ((WorkflowAction)obj).getId() == this.getId();
		return false;
	}

	public void setTargetStep(WorkflowStep targetStep) {
		_targetStep = targetStep;
		
		_log.debug("Created new WorkflowActionImpl(" + _id + ", " + _name 
				+ ", " + (_targetStep != null ? _targetStep.getName() : "NULL")
				+ ", r[0]=" + (_roles != null && _roles.length > 0?_roles[0].getAuthority(): "NULL") + ")");
	}
	
}
