<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="publicationDeleteForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="publicationDeleteForm.title"/></h3>

<p><s:text name="publicationDeleteForm.message"/></p>


<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>

<s:form name="deletePublicationForm" action="DeletePublication" method="post" validate="true">
		<fieldset>
    	<legend><s:text name="publicationDeleteForm.legend"/></legend>
    	
		<s:token />		
		<s:hidden name="id" />
		<s:hidden name="publication.id" />
		<s:hidden name="publication.publicationname" />
		<s:label key="publication.publicationname" />
	</fieldset>	
	<div class="buttonrow">
<authz:authorize ifAnyGranted="operator,publishermgr">	
		<s:submit key="button.delete" cssClass="fbtn"/>
</authz:authorize>		
		<s:submit action="ListPublications" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>
		</div>
</s:form>

