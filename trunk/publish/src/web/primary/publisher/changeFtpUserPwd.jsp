<%-- $Id: changeFtpUserPwd.jsp,v 1.10 2008/05/31 21:12:44 waifus Exp $ --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
	<title><s:text name="ftpUserChangePassword.title"/></title>
	<s:head/>
</head>

<h3>Change password</h3>

<s:actionerror />

<s:form action="ChangeFtpPassword" method="post" validate="true">
	<fieldset>
		
		<s:token name="token" />
		<s:hidden name="task"/>
		<s:hidden name="id" />
		
		<s:password label="New Password" name="publisherUser.password"   required="true"/>
		<s:password label="Confirm New Password" name="password2"   required="true" />
    </fieldset>
 		
 	<div class="buttonrow">
		<s:submit value="%{getText('button.save')}" />
		<s:submit action="UpdateFtpUserInput" value="%{getText('button.cancel')}" onclick="form.onsubmit=null"/>
	</div>
	
</s:form>
