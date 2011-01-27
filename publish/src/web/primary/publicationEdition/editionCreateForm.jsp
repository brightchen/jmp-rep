<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="editionCreateForm.title"/></title>
    <s:head />
    
<SCRIPT LANGUAGE=javascript>
function erase(form) 
{ 
	location='<%=request.getContextPath()%>/AddPublicationEdition.do';
}
</SCRIPT>

</head>

<h3><s:text name="editionCreateForm.title"/></h3>

<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>

<p><strong><s:label name="actionResult" /></strong></p>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>

<s:form name="editionCreateForm" action="AddPublicationEditionInput" method="post" validate="true" enctype="multipart/form-data">
	
	<fieldset>
    	<legend><s:text name="editionCreateForm.legend"/></legend>
		<s:token />		
		<s:hidden name="task" />	
	
	    <h3>
            <s:select key="selectedPublicationName" list="publicationNames" onchange="document.editionCreateForm.submit()"/>
        </h3>
		
		<s:datetimepicker key="editionDate" toggleType="fade" disabled="true" toggleDuration="1000" required="true" value="today"/>	
	</fieldset>
	
	<div class="buttonrow">
	    <s:submit action="AddPublicationEdition" key="button.Submit" cssClass="fbtn" />
	    <s:submit key="button.cancel" action="PublisherMainMenu" onclick="form.onsubmit=null"/>
	</div>
	
</s:form>
	




