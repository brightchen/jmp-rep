<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="publisherUserDeleteForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="publisherUserDeleteForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:form name="publisherUserDeleteForm" action="DeletePublisherUser" method="post" validate="false" >
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
		<s:submit action="ListPublisherUsers" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>
		</div>
</s:form>



