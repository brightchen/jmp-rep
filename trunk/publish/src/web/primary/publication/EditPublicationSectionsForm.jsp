<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<title><s:text name="PublicationSectionsForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:form name="EditPublicationSectionsForm" action="EditPublicationSections" method="post" validate="true">

    <h3><s:label key="publication.publicationname" /></h3>
    
    <display:table name="publicationSections" class="list" requestURI="" id="publicationSectionList" export="false" pagesize="10" sort="list">
        <display:column property="sectionName" sortable="true" titleKey="sectionName.text" />
        <display:column property="secondLanguage" sortable="true" titleKey="secondLanguage.text"/>		
        <display:column property="order" sortable="true" titleKey="order.text"/> 
    </display:table>
    
    <div class="buttonrow">
    	<s:submit key="button.Submit" />
    </div>		

</s:form>