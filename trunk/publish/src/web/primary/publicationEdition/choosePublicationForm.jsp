<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="choosePublicationForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="choosePublicationForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>

<s:form name="ChoosePublicationForm" action="AddPublicationEditionInput" method="post" validate="true">
    <fieldset>
        <p><s:text name="choosePublicationForm.message"/></p>
		<s:token />	
	    <s:select key="selectedPublicationName" list="publicationNames" />
	</fieldset>
	
	<s:submit action="ChoosePublication?cachePublication=true" key="button.ok" cssClass="fbtn" />
</s:form>
	

	
