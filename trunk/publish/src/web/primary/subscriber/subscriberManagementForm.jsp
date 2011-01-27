<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="subscriberManagementForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="subscriberManagementForm.title"/></h3>

<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>

<s:actionerror />
<s:actionmessage />

<s:form name="subscriberManagementForm" method="post" validate="true" enctype="multipart/form-data">
	<fieldset>
    	<legend><s:text name="subscriberManagementForm.legend"/></legend>
		<s:token />		

		<s:textfield key="phoneNumber" required="true" />	
    </fieldset>			
    
  	<div class="buttonrow">
		<s:submit action="LookupDeviceStatusHistory" key="button.LookupDeviceStatusHistory" cssClass="fbtn" />
		<s:submit action="LookupSubscriptionDetails" key="button.LookupSubscriptionDetails" cssClass="fbtn" />
		<s:submit action="LookupSubscriptionHistory" key="button.LookupSubscriptionHistory" cssClass="fbtn" />
		<s:submit action="TerminateAccountConfirm"   key="button.TerminateAccount"          cssClass="fbtn" />
		<s:submit action="QuerySubscription"         key="button.ManageSubscription"        cssClass="fbtn" />
	</div>

</s:form>
	




