<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="editionSectionForm.title"/></title>
    <s:head />
    
<SCRIPT LANGUAGE=javascript>
function erase(form) 
{ 
	location='<%=request.getContextPath()%>/InputEditionSection.do';
}
</SCRIPT>

</head>
<h3><s:text name="editionSectionForm.title"/></h3>

<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>

<p><strong><s:label name="actionResult" /></strong></p>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>

<s:form name="editionSectionForm" action="AddEditionSectionInput" method="post" validate="true" enctype="multipart/form-data">
	<fieldset>
    	<legend><s:text name="editionSectionForm.legend"/></legend>
		
		<s:token />		
		<s:hidden name="task" />
		
		<p>
            <s:select key="selectedPublicationName" list="publicationNames" onchange="document.editionSectionForm.submit()" headerValue="Please select" headerKey="Please select" required="true"/>
        </p>

        <s:select key="selectedEditionName" list="publicationEditionNames" required="true" disabled="true"/>
		<!-- s:textfield key="document.documentname" required="true" / -->		
		<s:select key="documentStyle" label="Style" list="styles" required="true" disabled="true"/>

		<div class="controlset">
			<span class="label">Policy<em>*</em></span>
			<s:checkbox key="docNeedReview" cssClass="checkboxLabel"/>
		</div>

		<s:select key="selectedSectionName" list="publicationSectionNames" required="true" disabled="true"/>
		
		<div>
			<s:file label="Source" name="upload" required="true" accept="application/pdf " disabled="true"/>	
		</div>
		
	</fieldset>
	<div class="buttonrow">
		<s:submit action="AddEditionSection" key="button.save" cssClass="fbtn" disabled="true"/>	
		<s:submit action="ViewPublicationEditionsInput" key="button.Cancel" onclick="form.onsubmit=null;" cssClass="fbtn" />		  
	</div>

</s:form>
	




