package cg.usermodule.web.action;

import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import cg.common.logging.Logger;
import cg.usermodule.model.User;
import cg.usermodule.web.util.Constants;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport 
	implements SessionAware, ApplicationAware {

	private static final long serialVersionUID = -5587148523166346642L;
	private static final Logger log = Logger.getLogger( BaseAction.class );
	
	private Map application;
	private Map session;
	
	private String task = null;
	private String token = null;
	
	private String submit = null;

	
	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void setApplication(Map application) {
		this.application = application;
	}

	public Map getApplication() {
		return application;
	}

	public void setSession(Map session) {
		//log.debug( "set session: previous = " + this.session + "; new = " + session );
		this.session = session;
	}
	
	public Map getSession() {
		return session;
	}
	
	public String cancel() {
		return "cancel";
	}

	public User getLoginPublisherUser() {
		log.debug( "getLoginPublisherUser:" );
		dumpSession();
		User loginUser = (User) getSession().get(Constants.PUBLISHER_USER_KEY);
		return loginUser;
		
		//return (PublisherUser) getSession().get(Constants.PUBLISHER_USER_KEY);
	}
	
	@SuppressWarnings("unchecked")
	public void setLoginPublisherUser(User user) {
		getSession().put(Constants.PUBLISHER_USER_KEY, user);
	}
	
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	protected HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
	@SuppressWarnings("unchecked")
	protected void dumpSession()
	{
		//do nothing
	}
	
	protected void removeSessionAttributes( Set< String > keySet )
	{
	    if( keySet == null )
	        return;
	    for( String key : keySet )
	    {
	        if( key == null || key.length() == 0 )
	            continue;
	        getSession().remove( key );
	    }
	}
}
