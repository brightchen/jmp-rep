/*
 * $Id: WorkflowTest.java,v 1.1 2007/05/30 20:30:46 dorelv Exp $
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

package cg.iseepublish.workflow.test;

import junit.framework.TestCase;

import cg.iseepublish.model.Role;
import cg.iseepublish.workflow.Workflow;
import cg.iseepublish.workflow.WorkflowAction;
import cg.iseepublish.workflow.WorkflowActionImpl;
import cg.iseepublish.workflow.WorkflowImpl;
import cg.iseepublish.workflow.WorkflowStep;
import cg.iseepublish.workflow.WorkflowStepImpl;


/**
 * @author dorel vleju
 */
public class WorkflowTest extends TestCase{
	
	
	
	public void testListActions(){
		try{
			Workflow workflow = new WorkflowImpl();
			
			System.out.println("Get the current State from database yourself!!!!");
			WorkflowStep currentStep = WorkflowStepImpl.EDITED;
			Role [] roles = {new Role("editor")};
			
			WorkflowAction [] availableActions = workflow.getAvailableActions(currentStep, roles);
			
			System.out.println("Found available actions for step: " + currentStep.getName() 
					+ ", and role: " + roles[0].getAuthority() + ":\n-----------");
			
			for (WorkflowAction action: availableActions){
				System.out.println(action.getName());
			}
			
			System.out.println("-----------");
			
		}catch(Exception e){
		    System.out.println("Cannot list Actions.");
			e.printStackTrace(System.err);
		}
	}
	
	public void testExecuteAction(){
		try{
			Workflow workflow = new WorkflowImpl();
			
			WorkflowStep currentStep = WorkflowStepImpl.EDITED;
			Role [] roles = {new Role("editor")};
			
			WorkflowAction action = WorkflowActionImpl.REVIEW;
			
			WorkflowStep nextStep = workflow.executeAction(currentStep, action, roles);
			
			System.out.println("Executed action: " + action.getName() 
					+ ", with user role: " + roles[0].getAuthority() + ", and moved Entity on next step: " + nextStep.getName());

			System.out.println("Implement Save Entity new State to database yourself!!!!");
			
		}catch(Exception e){
		    System.out.println("Cannot execute Action.");
			e.printStackTrace(System.err);
		}
	}
	
	
}
