<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<title><s:text name="AddEditionSectionsConfirmationForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:actionerror />
<s:actionmessage />
<s:form name="editionSectionSuccessfulForm" method="post" validate="true" enctype="multipart/form-data">
<fieldset><legend>Success</legend>
<s:hidden name="newSession" value="false"/>
<p>The section is successfully added to the publication.</p>
</fieldset>
<s:submit action="AddEditionSectionInput" key="button.anotherSection" cssClass="fbtn" />	
<s:submit action="ViewPublicationEditionsInput" key="button.Done" onclick="form.onsubmit=null;" cssClass="fbtn" />	

</s:form>