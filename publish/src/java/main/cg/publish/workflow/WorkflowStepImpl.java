/*
 * $Id: WorkflowStepImpl.java,v 1.1 2007/05/30 20:30:46 dorelv Exp $
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



/*
 * Class that implements Interface Document WorkflowStep
 * @author dorel vleju
 * $Id: WorkflowStepImpl.java,v 1.1 2007/05/30 20:30:46 dorelv Exp $
 */
public class WorkflowStepImpl implements WorkflowStep{
		
	private static Logger _log = Logger.getLogger(WorkflowStepImpl.class);
	
	// having Action depending of Step, and Step depending of Action static variables
	// will produce a chicken/egg situation
	

	public static final WorkflowStep NEW = new WorkflowStepImpl(10, "NEW", 
			new WorkflowAction[]{WorkflowActionImpl.PREPARE});
	
	public static final WorkflowStep PREPARED = new WorkflowStepImpl(20, "PREPARED", 
			new WorkflowAction[]{WorkflowActionImpl.EDIT, WorkflowActionImpl.DELETE});
	
	public static final WorkflowStep EDITED = new WorkflowStepImpl(30, "EDITED", 
			new WorkflowAction[]{WorkflowActionImpl.REVIEW, WorkflowActionImpl.DELETE});	
	
	public static final WorkflowStep REVIEWED = new WorkflowStepImpl(40, "REVIEWED", 
			new WorkflowAction[]{WorkflowActionImpl.EDIT, WorkflowActionImpl.PUBLISH, WorkflowActionImpl.DELETE});	
	
	public static final WorkflowStep PUBLISHED = new WorkflowStepImpl(50, "PUBLISHED", 
			new WorkflowAction[]{WorkflowActionImpl.RETIRE});		
	
	public static final WorkflowStep RETIRED = new WorkflowStepImpl(60, "RETIRED", 
			new WorkflowAction[]{WorkflowActionImpl.DELETE});
	
	public static final WorkflowStep DELETED = new WorkflowStepImpl(99, "DELETED", null);

	
	WorkflowAction[] _actions = null;
	String _name = null;
	int _id = -1;
	
	
	public WorkflowStepImpl(int id, String name, WorkflowAction[] actions) {
		_id = id;
		_name = name;
		_actions = actions;
		_log.debug("Created new WorkflowStepImpl(" + _id + ", " + _name + ", a[0]=" 
				+ (actions != null && actions.length > 0?actions[0].getName(): "NULL") + ")");
	}
	
	public WorkflowAction[] getStepActions(){
		return _actions;
	}
	
	public String getName(){
		return _name;
	}
	
	public int getId(){
		return _id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof WorkflowStep)
			return ((WorkflowStep)obj).getId() == this.getId();
		return false;
	}
	
}
