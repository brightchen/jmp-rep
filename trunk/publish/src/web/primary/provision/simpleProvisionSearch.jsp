<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="provision.title"/></title>
    <s:head />
</head>

<body>
<h3><s:text name="phoneProvision.legend"/></h3>

<s:actionerror />
<s:actionmessage />


<s:form name="phoneProvisionCreateForm" action="SimpleSearchProvision" method="post" validate="true">
		<fieldset>
    		<legend><s:text name="phoneProvision.legend"/></legend>
			<s:token />		
			<s:hidden name="task" />
		
			<s:textfield key="phoneNumber" required="true" />
		</fieldset>
	
		<div class="buttonrow">
			<s:submit key="button.search" />
			<s:submit action="SimpleWildSearchProvision" key="button.wildSearch" />
			<s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null" />		  
		</div>
		
</s:form>

</body>

