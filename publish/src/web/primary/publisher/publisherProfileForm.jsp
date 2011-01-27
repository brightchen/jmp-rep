<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="publisherProfileForm.title"/></title>
</head>

<h3><s:text name="publisherProfileForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:form name="publisherProfileForm" action="PublisherProfile" method="post" validate="false">
		<fieldset>
    	<legend><s:text name="publisher.profileManagement.legend" /></legend>
    
		<s:token />
		<s:hidden name="publisher.publishername" />
		<s:label key="publisher.publishername" />
		<s:textfield key="publisher.keyword" />
		
		</fieldset>
 		<div class="buttonrow">
<authz:authorize ifAnyGranted="admin">
		<s:submit key="button.save" cssClass="btn" />
</authz:authorize>
		
		<s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null" cssClass="btn"/>
		
		</div>
</s:form>



