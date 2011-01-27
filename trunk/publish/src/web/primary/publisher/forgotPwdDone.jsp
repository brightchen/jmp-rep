<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
	<title><s:text name="login.title"/></title>
	<s:head/>
</head>
	<fieldset>
		<legend><s:text name="forgotPwdDone.legend"/></legend>
				
			<p><s:actionmessage/></p>
			<p><a href="<s:url action="PublisherMainMenu"/>"><s:text name="forgotPwdDone.login"/></a></p>
					
	</fieldset>
		
