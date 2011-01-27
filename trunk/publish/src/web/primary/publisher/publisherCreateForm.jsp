<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
	<title><s:text name="publisherForm.title"/></title>
	<s:head />
</head>

<p><s:text name="publisherUpdate.form"/></p>

<s:actionerror />
<s:actionmessage />

<s:form name="publisherUpdateForm" action="UpdatePublisher" method="post" validate="true">
	<fieldset>
    	<legend><s:text name="publisherUpdateForm.legend"/></legend>    	
		<s:token />	
		<s:label key="publisher.publishername" />
		<s:hidden key="publisher.publishername" />
		<s:textfield key="publisher.contact" />
		<s:textfield key="publisher.status" required="true" />
	</fieldset>
 	<div class="buttonrow">
		<s:submit key="button.submit" cssClass="fbtn" cssStyle="*margin-top:10px"/>
		<s:submit action="Welcome" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>		
	</div>				
</s:form>	


