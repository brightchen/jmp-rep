<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="documentForm.title"/></title>
    <s:head />
    
<SCRIPT LANGUAGE=javascript>
function erase(form) { 
	location='<%=request.getContextPath()%>/CreateDocumentInput.do';
}
</SCRIPT>

</head>

<h3><s:text name="documentForm.title"/></h3>

<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>

<s:actionerror />
<s:actionmessage />

<s:form name="documentCreateForm" action="CreateDocument" method="post" validate="true" enctype="multipart/form-data">
	<fieldset>
    	<legend><s:text name="documentForm.legend"/></legend>
		<s:token />		
		<s:hidden name="task" />		
		<!--<s:label key="publication.publicationname" /> -->
		<s:textfield key="document.documentname" required="true" />		
		<s:select key="document.style" list="styles" />
		<div class="controlset">
			<span class="label">Policy<em>*</em></span>
			<s:checkbox key="docNeedReview" cssClass="checkboxLabel"/>
		</div>
		<div>
			<s:file label="Source" name="upload" required="true" accept="application/pdf "/>	
		</div>
	</fieldset>
	<div class="buttonrow">
		<s:submit key="button.save" cssClass="fbtn" />	
		<s:submit action="CreateDocumentInput" key="button.cancel" onclick="erase(this.form); form.onsubmit=null;" cssClass="fbtn" />		  
	</div>
</s:form>
	




