<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<title><s:text name="publisherUserForm.title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="imagetoolbar" content="no" />
	<s:head />
</head>

<body onload="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=true">

	<h3><s:text name="publisherUserForm.title"/></h3>

	<p><s:text name="publisherUserForm.editUser"/></p>

	<s:actionerror />
	<s:actionmessage />

	<s:form name="publisherUserUpdateForm" action="UpdatePublisherUser" method="post" validate="true" >
		<fieldset>
		<legend><s:text name="publisherUserForm.legend"/></legend>
		
		<s:token />
		<s:hidden name="task" />		
		<s:hidden name="id" />
		<s:hidden name="publisherUser.id" />
		<s:hidden name="publisherUser.username" />
		
		<!--<s:label label="ID" name="publisherUser.id" />-->
		
		<s:label key="publisherUser.username" />
		<!--<p class="text"><s:hidden name="publisherUser.username" /></p> -->
		
		<div>
		<label for="UpdatePublisherUser_publisherUser_firstname" class="label">Password:</label>
		<p class="text"><a href="<s:url value="ChangePasswordInput.do"><s:param name="id" value="%{id}"/><s:param name="sysAdmin" value="%{sysAdmin}"/></s:url>">Change Password</a></p>
		</div>
		
		<s:textfield key="publisherUser.firstname" onclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;" required="true"/>
		<s:textfield key="publisherUser.lastname" onclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;" required="true"/>
	
		<s:textfield key="publisherUser.email" onclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;" required="true"/>
		<s:textfield key="publisherUser.phoneNumber" onclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;"/>
	
	  <s:if test="userDeletable">
  		<div class="controlset">
  			<span class="label"><s:text name="user.status"/></span>
  			<s:checkbox key="publisherUser.enabled" cssClass="checkboxLabel" onclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;"/>
  		</div>
		</s:if>
		<s:else>
		  <s:hidden name="publisherUser.enabled" />
		</s:else>

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
				addToLeftOnclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;" 
				addToRightOnclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;" 
				onclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;" 
				doubleOnclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;"
			/>	
		</div>
	
<authz:authorize ifAnyGranted="sysadmin">
			
			<s:select
				list="availablePublishers" key="selectedPublisher" emptyOption="true" 
				onclick="document.publisherUserUpdateForm.UpdatePublisherUser_button_save.disabled=false;" 
			/>	
		
</authz:authorize>

		</fieldset>	
		<div class="buttonrow">
		<s:submit key="button.save" cssClass="fbtn" disabled="true"/>
		<s:if test="userDeletable">
		  <s:submit action="DeletePublisherUserInput" key="button.delete" onclick="form.onsubmit=null" />
		</s:if>
		<s:submit action="ListPublisherUsers" key="button.cancel" onclick="form.onsubmit=null" />
		</div>
	</s:form>

</body>
</html>

