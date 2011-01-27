<%-- $Id: ftpUserForm.jsp,v 1.9 2007/10/25 21:08:48 kellyc Exp $ --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
	<title><s:text name="ftpUserForm.title"/></title>
	<s:head />
</head>

<h3><s:text name="ftpUserForm.title"/></h3>

<s:if test="task == 'Create'">
	<p><s:text name="ftpUserForm.newUser"/></p>
</s:if><s:else>
	<p><s:text name="ftpUserForm.editUser"/></p>
</s:else>

<s:actionerror />
<s:actionmessage />

<s:if test="task == 'Create'">
<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>
	<s:form name="ftpUserCreateForm" action="CreateFtpUser" method="post" validate="true" >
		<fieldset>
		<legend><s:text name="ftpUserForm.legend"/></legend>
		<s:token />
		<s:hidden name="task" />
	
		<s:textfield key="publisherUser.username" required="true" />
		<s:password key="publisherUser.password" required="true" />
		<s:password key="password2" required="true" />	
		<s:textfield key="publisherUser.firstname"/>
		<s:textfield key="publisherUser.lastname"/>		
		
		<s:textfield key="publisherUser.email" required="true" />	
		<s:textfield key="publisherUser.phoneNumber" />
		
		<div class="controlset">
			<span class="label"><s:text name="user.status"/><em>*</em></span>
		<s:checkbox key="publisherUser.enabled" cssClass="checkboxLabel" value="true" />
		</div>
		
		</fieldset>
		<div class="buttonrow">
		<s:submit key="button.save"/>
		<s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null" />
		</div>
	</s:form>

</s:if><s:else>

	<s:form name="ftpUserUpdateForm" action="UpdateFtpUser" method="post" validate="true" >
		<fieldset>
		<legend><s:text name="ftpUserForm.legend"/></legend>
		<s:token />
		<s:hidden name="task" />		
		<s:hidden name="id" />
		<s:hidden name="publisherUser.id" />
		<s:hidden name="publisherUser.username" />
		<s:label key="publisherUser.username" />
		<div>
		<label for="UpdatePublisherUser_publisherUser_firstname" class="label"><s:text name="ftpUserForm.password"/></label>
		<p class="text"><a href="<s:url value="ChangeFtpPasswordInput.do"><s:param name="id" value="%{id}"/></s:url>"><s:text name="ftpUserForm.changePword"/></a></p>
		</div>
		<s:textfield key="publisherUser.firstname" />
		<s:textfield key="publisherUser.lastname" />
		
		
		<s:textfield key="publisherUser.email" required="true"/>
		<s:textfield key="publisherUser.phoneNumber" />
		
		 <div class="controlset">
			<span class="label"><s:text name="user.status"/><em>*</em></span>        
		<s:checkbox key="publisherUser.enabled" cssClass="checkboxLabel"/>		
		</div>
		
		</fieldset>
		<div class="buttonrow">
		<s:submit key="button.save" />
		<s:submit action="DeleteFtpUserInput" key="button.delete" onclick="form.onsubmit=null" />
		<s:submit action="ListFtpUsers" key="button.cancel" onclick="form.onsubmit=null" />
		</div>
	</s:form>
</s:else>
