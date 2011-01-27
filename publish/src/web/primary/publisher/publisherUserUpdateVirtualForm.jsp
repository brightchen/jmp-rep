<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<head>
	<title><s:text name="publisherUserForm.title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="imagetoolbar" content="no" />
	
	<SCRIPT LANGUAGE=javascript>
	function enable() 
{ 
	var saveButton = document.getElementById("UpdateVirtualPublisherUser_button_save");
	if (saveButton!=null){
		saveButton.disabled=false;
	}
}
</SCRIPT>
<s:head />
</head>

	<h3><s:text name="publisherUserForm.title"/></h3>

	<p><s:text name="publisherUserForm.editUser"/></p>

<s:actionerror />
<s:actionmessage />
	
<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>

<s:form name="publisherUserUpdateForm" action="UpdateVirtualPublisherUser" method="post" validate="true" >
	<fieldset>
		<legend><s:text name="publisherUserForm.legend"/></legend>		
		<s:token />
		<s:hidden name="task" />		
		<s:hidden name="id" />
		<s:hidden name="publisherUser.id" />
		<s:hidden name="publisherUser.username" />		
		<s:label key="publisherUser.username" />
		<!--<p class="text"><s:hidden name="publisherUser.username" /></p> -->
		
		<div>
		<label for="UpdatePublisherUser_publisherUser_firstname" class="label">Password:</label>
		<p class="text"><a href="<s:url value="ChangePasswordVirtualInput.do"><s:param name="id" value="%{id}"/></s:url>">Change Password</a></p>
		</div>
		
		<s:textfield key="publisherUser.firstname" onclick="enable();" required="true"/>
		<s:textfield key="publisherUser.lastname" onclick="enable();" required="true"/>
		<s:textfield key="publisherUser.email" onclick="enable();" required="true"/>
		<s:textfield key="publisherUser.phoneNumber" onclick="enable();"/>
	
		<div class="controlset">
			<span class="label"><s:text name="user.status"/></span>
			<s:checkbox key="publisherUser.enabled" cssClass="checkboxLabel" onclick="enable();"/>
		</div>
		<div class="controlset">
			<span class="label"><s:text name="user.role"/></span>
			<s:optiontransferselect
				name="availableRolesForDisplay"
				allowSelectAll="false"
				allowAddAllToLeft="false"
				allowAddAllToRight="false"
				allowUpDownOnLeft="false"
				allowUpDownOnRight="false"
				headerValue="--- Available Roles ---"
				doubleHeaderValue="--- Current Roles ---"
				doubleHeaderKey="-1"
				headerKey="-1"
				list="availableRolesForDisplay"
				size="7"
				doubleSize="7"
				doubleList="currentRolesForDisplay"
				doubleName="currentRolesForDisplay"
				cssClass="bttn"
				emptyOption="false"
				buttonCssClass="btn"
				onclick="enable();" 
				doubleOnclick="enable();"
			/>	
		</div>		

	</fieldset>	
	<div class="buttonrow">
		<s:submit key="button.save" cssClass="fbtn" disabled="true"/>
		<s:submit action="DeleteVirtualPublisherUserInput" key="button.delete" onclick="form.onsubmit=null" />
		<s:submit action="ListVirtualPublisherUsers" key="button.cancel" onclick="form.onsubmit=null" />
	</div>
</s:form>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>

