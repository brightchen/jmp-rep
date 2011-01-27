<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<title><s:text name="AddEditionSectionsConfirmationForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>

<s:actionerror />
<s:actionmessage />

<p><strong><s:label name="actionResult" /></strong></p>

<s:form name="AddEditionSectionsConfirmationForm" method="post" validate="true">
 <fieldset>
    <p><s:text name="AddEditionSectionsConfirmationForm.textBeforeEditionName"/></p>
  <p><s:label key="publicationEdition.editionName" /></p>
   </fieldset>
    
    <div class="buttonrow">
    	<s:submit action="AddEditionSectionInput" key="button.Yes" onclick="form.onsubmit=null" />
    	<s:submit action="ViewPublicationEditionsInput" key="button.No" onclick="form.onsubmit=null" />		  
    </div>		

</s:form>