<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
	<title><s:text name="documentMainPage.title" /></title>
</head>

<s:actionerror />
<s:actionmessage />

<br />

<authz:authorize ifAnyGranted="operator">	
    <s:form name="documentCreateForm" action="CreateDocument" method="post" validate="true" enctype="multipart/form-data">
		<fieldset>
    	<legend><s:text name="documentMainPage.legend" /></legend>
    	<dl>
		<s:token />		
		<s:hidden name="task" />	
		<s:textfield key="document.documentname" required="true" />
		<s:file label="Source" name="upload" />		
		</fieldset>
 		<div class="buttonrow">
		<s:submit key="button.save" cssClass="btn"/>
		<s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null" cssClass="btn"/>		  
		</div>
	</s:form>
</authz:authorize>


