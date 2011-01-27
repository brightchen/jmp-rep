<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="cg.iseepublish.model.*" %> 
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="documentManagement.title"/></title>
    <s:head />
</head>

<p><strong>Please download document's source file using the link below:</strong></p>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.document.source != null">
	<li>
        <s:url id="url" action="Download?fileLink=source"/>
        Download source file : <s:a href="%{url}"><%=((Document)session.getAttribute("document")).getSourceFileName()%></s:a> 		
    </li>
</s:if>



<br />

<p><strong>Or update document information using the form below:</strong></p>

<s:form theme="simple" name="documentUpdateForm" action="UpdateDocument" method="post" validate="true"  enctype="multipart/form-data">
		<fieldset>
    	<legend>document manager</legend>
    	<dl>
		<s:token />
		<s:label label="Document Name" name="document.documentname" />
		<s:hidden name="document.documentname" />
		<authz:authorize ifAnyGranted="operator,editor">
		<s:file label="New Source File" name="upload" />
		</authz:authorize>
		<s:textfield key="document.status"/>	
		        <s:submit key="button.save" cssClass="btn"/>
		        <s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null" cssClass="btn"/>
		</dl>
		</fieldset>		
</s:form>
