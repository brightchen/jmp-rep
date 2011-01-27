<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
	<title><s:text name="publisherUserChangePassword.title"/></title>
	<s:head/>
	
</head>

<h3>Change password</h3>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>


<s:form action="ChangePasswordVirtual" method="post" validate="true">
	<fieldset>
	<s:token name="token" />
	<s:hidden name="task"/>
	<s:hidden name="id" />
	<s:hidden name="sysAdmin" />
	
	<s:if test="sysAdmin">
		<s:password label="Old Password" name="inputOldPassword"/>
	</s:if>
	<s:password label="New Password" name="publisherUser.password" required="true"/>
	<s:password label="Re-enter Password" name="password2" required="true"/>
    </fieldset>
    
 	<div class="buttonrow">
	<s:submit value="%{getText('button.save')}" />
	<s:submit action="ListVirtualPublisherUsers" value="%{getText('button.cancel')}" onclick="form.onsubmit=null"/>
</div>
</s:form>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>
