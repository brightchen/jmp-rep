<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="publisherProfileForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="publisherProfileForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:form theme="simple" name="publisherProfileForm" action="PublisherMainMenu" method="post" validate="false">
		<fieldset>
    	<legend><s:text name="publisher.profileManagementDone.legend" /></legend>
    	<dl>
		<s:token />
		<s:label key="publisher.publishername" />
		<s:label key="publisher.keyword" />

		<s:submit key="button.ok" cssClass="btn" />
		
		</dl>
		</fieldset>
</s:form>



