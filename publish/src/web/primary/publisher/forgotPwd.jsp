<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
	<title><s:text name="login.title"/></title>
	<s:head/>
</head>


<h3><s:text name="forgotPwd.heading"/></h3>
<s:actionerror />


<s:form action="ForgotPwd_retrieveByUsername" validate="false" >
	<fieldset>
		  <legend><s:text name="forgotPwd.legend.pwd"/></legend>
		  <s:textfield label="User Name" name="username"  cssStyle="*margin-bottom:35px" />
	</fieldset>
 	<div class="buttonrow">
  	      <s:submit value="Get New Password" cssClass="fbtn" cssStyle="*margin-top:10px;"/>
    </div>
</s:form>

<!--  
<s:form action="ForgotPwd_retrieveByPhone" validate="false" >
	<fieldset>	
		<legend><s:text name="forgotPwd.legend.id"/></legend>	  
		<s:textfield label="Cell phone" name="phonenumber" cssStyle="*margin-bottom:35px">
			<s:param name="after">Format: 4161234567</s:param>
		</s:textfield>
	</fieldset>
 	<div class="buttonrow">
       	  	<s:submit value="Find User ID" cssClass="fbtn" cssStyle="*margin-top:10px;"/>
	</div>
</s:form>
-->
