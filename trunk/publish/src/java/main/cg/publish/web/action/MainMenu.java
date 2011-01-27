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

package cg.publish.web.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * Used as an entry point action class for the Welcome and MainManu jsp page.
 */
public class MainMenu extends BaseAction implements Preparable {

	private static final long serialVersionUID = -1663695780209369893L;

	public void prepare() throws Exception {	
		clearErrorsAndMessages();
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", null);
	}
	
	public String execute() {
        return SUCCESS;   
	}
}
