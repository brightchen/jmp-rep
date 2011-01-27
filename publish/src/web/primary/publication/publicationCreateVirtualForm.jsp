<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="publicationForm.title"/></title>
    <s:head />
    
<SCRIPT LANGUAGE=javascript>
function onfocusField(myField)
{
if (myField.disabled) 
{
myField.blur()
return false
}
return true;
}
</SCRIPT>

</head>

<body onload="document.publicationUpdateForm.UpdatePublication_button_save.disabled=true">
<h3><s:text name="publicationForm.title"/></h3>
<p><s:text name="<form.instruction"/></p>

<s:actionerror />
<s:actionmessage />
	
<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>

<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>
<s:form name="publicationCreateForm" action="CreateVirtualPublication" method="post" validate="true">
	<fieldset>
    		<legend><s:text name="publication.legend"/></legend>
		<s:token />		
		<s:hidden name="task" />	
		<s:textfield key="publication.publicationname" required="true" />
		<s:textfield key="publication.publicationfullname" required="true" />	
		<div class="controlset">
			<span class="label"><s:text name="publication.policy"/><em>*</em></span>
			<s:checkbox key="docNeedReview" cssClass="checkboxLabel" />
		</div>
		<s:textfield key="publication.keyword" required="true"/>
		<!--  
		<s:datetimepicker key="publication.startdate" toggleType="fade" language="en" disabled="true" onblur="true" toggleDuration="1000" value="today" startDate="%{today}"/>
		<s:datetimepicker key="publication.enddate" toggleType="fade" language="en" disabled="true" onblur="true" toggleDuration="1000" value="today" startDate="%{today}"/>
		-->
	</fieldset>
	<div class="buttonrow">
		<s:submit key="button.save" />
		<s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null" />		  
	</div>		
</s:form>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>

</body>

