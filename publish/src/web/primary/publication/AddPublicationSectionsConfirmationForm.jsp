<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<title><s:text name="AddPublicationSectionsConfirmationForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:form name="AddPublicationSectionsConfirmationForm" method="post" validate="true">
    <fieldset>
    <p><s:text name="AddPublicationSectionsConfirmationForm.textBeforePublication"/></p>
    <s:label key="publication.publicationname" />
    </fieldset>
    <div class="buttonrow">
    	<s:submit action="InputPublicationSections" key="button.Yes" onclick="form.onsubmit=null" />
    	<s:submit action="ListPublications" key="button.No" onclick="form.onsubmit=null" />		  
    </div>		

</s:form>