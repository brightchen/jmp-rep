<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="provision.title"/></title>
    <s:head />


</head>

<body>

<s:actionerror />
<s:actionmessage />



	<s:form name="phoneProvisionCreateForm" action="CreateProvision" method="post" validate="true">
		<fieldset>
    		<legend><s:text name="phoneProvision.legend"/></legend>
		<s:token />		
		<s:hidden name="task" />
		
		<s:textfield key="phoneNumber" required="true" />
		<s:textfield key="shortcode" required="true" />
		<div>
		<s:select key="publicationName" list="publicationNameList" headerKey="@" headerValue="-- Please select --" required="true"/>	
		</div>
		</fieldset>
		<div class="buttonrow">
			<s:submit key="button.save" />
			<s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null" />		  
		</div>
		
	</s:form>
	


</body>

