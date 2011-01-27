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

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>

<%session.setAttribute("orgStartDate", session.getAttribute("orgStartDate"));%>
<%session.setAttribute("orgEndDate",session.getAttribute("orgEndDate"));%>

<s:form name="publicationUpdateForm" action="UpdateVirtualPublication" method="post" validate="true">
	<fieldset>
    	<legend>Edit publication profile</legend>    	
		<s:token />
		<s:hidden name="task" />		
		<s:hidden name="id" />
		<s:hidden name="publication.id" />
		<s:hidden name="publication.publicationname" />	
		<s:label key="publication.publicationname" />
		<s:textfield key="publication.publicationfullname" onchange="document.publicationUpdateForm.UpdatePublication_button_save.disabled=false"/>
		<s:select key="publication.style" list="#session['stylesnew']" />
		<div class="controlset">
			<span class="label"><s:text name="publication.policy"/><em>*</em></span>
			<s:checkbox key="docNeedReview" cssClass="checkboxLabel" />
		</div>
		<s:textfield key="publication.keyword" required="true"/>
		<s:datetimepicker key="publication.startdate" toggleType="fade" toggleDuration="1000" />
		<s:datetimepicker key="publication.enddate" toggleType="fade" toggleDuration="1000" />		
	</fieldset>
	<div class="buttonrow">
		<s:submit key="button.save" cssClass="fbtn"/>
		<s:submit action="DeleteVirtualPublicationInput" key="button.delete" onclick="form.onsubmit=null" cssClass="fbtn"/>
		<s:submit action="ListVirtualPublications" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>
	</div>		
</s:form>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>

</body>

