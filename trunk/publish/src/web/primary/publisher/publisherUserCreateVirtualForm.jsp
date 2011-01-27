<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<title><s:text name="publisherUserForm.title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="imagetoolbar" content="no" />
	<s:head />
</head>

<body>

	<h3><s:text name="publisherUserForm.title"/></h3>

	<p><s:text name="publisherUserForm.newUser"/></p>

<s:actionerror />
<s:actionmessage />
	
<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>

	<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>
<s:form name="publisherUserCreateForm" action="CreateVirtualPublisherUser" method="post" validate="true" >
	<fieldset>
		<legend><s:text name="publisherUserForm.legend"/></legend>		
		<s:token />
		<s:hidden name="task" />	
		<s:textfield key="publisherUser.username" required="true" />
		<s:password key="publisherUser.password" required="true" />
		<s:password key="password2" required="true" />	
		<s:textfield key="publisherUser.firstname" required="true"/>
		<s:textfield key="publisherUser.lastname" required="true"/>
		<s:textfield key="publisherUser.email" required="true"/>
		<s:textfield key="publisherUser.phoneNumber" />
		<div class="controlset">
			<span class="label"><s:text name="user.status"/><em>*</em></span>
			<s:checkbox key="publisherUser.enabled" value="true" cssClass="checkboxLabel" />
		</div>	
		<div class="controlset">
			<span class="label"><s:text name="user.role"/></span>	
			<s:optiontransferselect
			name="availableRolesForDisplay"			
			allowSelectAll="false"
			allowAddAllToLeft="false"
			allowAddAllToRight="false"
			doubleHeaderValue="--- Assign Roles ---"
 			headerValue="--- Available Roles ---"
			doubleHeaderKey="-1"
			headerKey="-1"
			allowUpDownOnLeft="false"
			allowUpDownOnRight="false"
			list="availableRolesForDisplay"
			size="7"
			doubleSize="7"			
			doubleList="currentRolesForDisplay"
			doubleName="currentRolesForDisplay"			
			/>
		</div>		
	</fieldset>					
	<div class="buttonrow">
			<s:submit key="button.save" />
			<s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null"/>
	</div>		
</s:form>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>

</body>
</html>

