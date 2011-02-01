package cg.usermodule.web.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

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
