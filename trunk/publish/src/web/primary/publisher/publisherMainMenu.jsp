<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
	<title><s:text name="index.title" /></title>
</head>


	<h3>
		<s:text name="mainMenu.heading">
		<s:param value="#session.publisheruser.username"/>
		</s:text>
	</h3>
	<s:actionerror />
	<s:actionmessage />
	
<br />


