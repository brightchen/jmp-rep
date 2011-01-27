<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="publisherUserDeleteForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="publisherUserDeleteForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>

<s:form name="publisherUserDeleteForm" action="DeleteVirtualPublisherUser" method="post" validate="false" >
	<fieldset>
		<legend><s:text name="publisherUserForm.legend"/></legend>
		<s:token />		
		<s:hidden name="id" />
		<s:hidden name="publisherUser.id" />
		<s:hidden name="publisherUser.username" />		
		<s:label key="publisherUser.username" />		
	</fieldset>
 	<div class="buttonrow">
		<s:submit key="button.delete" cssClass="fbtn"/>
		<s:submit action="ListVirtualPublisherUsers" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>
	</div>
</s:form>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>


