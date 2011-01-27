<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="documentForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="documentForm.title"/></h3>

<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
	
	<s:form name="documentCreateForm" action="CreateVirtualDocument" method="post" validate="true" enctype="multipart/form-data">
		<fieldset>
	    	<legend>create a new document</legend>    	
			<s:token />		
			<s:hidden name="task" />
			<s:textfield key="document.documentname" required="true" />		
			<s:select key="document.style" list="styles" />
			<div class="controlset">
				<span class="label">Policy<em>*</em></span>
			<s:checkbox key="docNeedReview" cssClass="checkboxLabel" />
			</div>
			<div>
			<s:file label="Source" name="upload" required="true"/>	
			</div>
		</fieldset>
		<div class="buttonrow">
			<s:submit key="button.save" cssClass="fbtn" />	
			<s:submit action="CreateVirtualDocumentInput" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn" />		  
		</div>		
	</s:form>
		
</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>



