<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
	<title><s:text name="publisherUpdateForm.title"/></title>
	<s:head />
</head>

<p><s:text name="publisherUpdate.form"/></p>

<s:actionerror />
<s:actionmessage />

<s:form name="publisherUpdateForm" action="UpdatePublisher" method="post" validate="true">
	<fieldset>
    	<legend><s:text name="publisherUpdateForm.legend"/></legend>    	
		<s:token />
		<s:hidden key="publisher.publishername" />			
		<s:label key="publisher.publishername" />
		<!--<s:label key="publisher.status" />-->
		<s:textfield key="publisher.contact" />		
		
		<div class="controlset">
			<span class="label"><s:text name="publisher.enable.account"/><em>*</em></span>
			<s:checkbox key="publisher.enabled" cssClass="checkboxLabel"/>
		</div>
	</fieldset>
 	<div class="buttonrow">
		<s:submit key="button.submit" cssClass="fbtn" cssStyle="*margin-top:10px"/>
		<s:submit action="ListPublishers" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>		
	</div>				
</s:form>	


