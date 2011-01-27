<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<title><s:text name="addPublicationSectionsForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:form name="AddPublicationSectionsConfirmationForm" action="AddPublicationSections" method="post" validate="true">

    <h3><s:text name="publication.publicationname" /></h3>
    <h3><s:label name="publication.publicationname" /></h3>
    
    <display:table name="publicationSections" class="list" requestURI="" id="orders" export="false" pagesize="10" sort="list">
        <fieldset>
            <display:column titleKey="sectionName.text">
                <s:textfield key="sectionNameList" required="true" />
            </display:column>
    
            <display:column titleKey="secondLanguage.text">
                <s:textfield key="secondLanguageList" required="false" />
            </display:column>
    
            <display:column titleKey="order.text">
                <s:select key="orderList" list="orders" required="true" />
            </display:column>
        </fieldset>            
    </display:table>

    
    <div class="buttonrow">
    	<s:submit key="button.Submit" />
    </div>		

</s:form>