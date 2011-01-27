<%-- $Id: ftpUserDeleteForm.jsp,v 1.3 2007/11/05 20:22:06 charlesd Exp $ --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="ftpUserDeleteForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="ftpUserDeleteForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:form name="ftpUserDeleteForm" action="DeleteFtpUser" method="post" validate="false" >
	<fieldset>
		<legend><s:text name="ftpUserDeleteForm.legend"/></legend>
		<s:token />		
		<s:hidden name="id" />
		<s:hidden name="publisherUser.id" />
		<s:hidden name="publisherUser.username" />		
		<s:label key="publisherUser.username" />		
	</fieldset>
 		<div class="buttonrow">
		<s:submit key="button.delete" cssClass="fbtn"/>
		<s:submit action="ListFtpUsers" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>
		</div>
</s:form>



