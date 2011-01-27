<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
<title><s:text name="ViewPublicationEditionsForm.title"/></title>
<script language="JavaScript">
<!-- 
setTimeout( "refresh()", 10*1000 ); 
function refresh()
{
  url = "<%=request.getContextPath()%>/ViewPublicationEditionsInput.do?newSeesion=true";
   window.location.href = url;
} 

//-->
</script>

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>

<s:form name="ViewPublicationEditionsForm" action="ViewPublicationEditionsInput" method="post" validate="true">

    <h3>
        <s:select key="selectedPublicationName" list="publicationNames" onchange="document.ViewPublicationEditionsForm.submit()"/>
    </h3>

   <display:table name="publicationTotalSections" class="list" requestURI="" id="publicationTotalSectionList" export="false" pagesize="10" sort="list">
        <display:column property="editionName" href="UpdatePublicationEditions.do" paramId="selectedEditionName" media="html" title="Edition Name" />
        <display:column property="sectionName" href="UpdateDocumentInput.do" media="html" paramId="id" paramProperty="documentId" sortable="true" title="Section Name" />
        <display:column property="status" sortable="true" titleKey="viewPublicationEditionsForm.sectionStatus"/>		
        <display:column property="displayCreateTime" sortable="true" titleKey="viewPublicationEditionsForm.createTime"/> 
        <display:column property="displayUpdateTime" sortable="true" titleKey="viewPublicationEditionsForm.updateTime"/> 
    </display:table>

</s:form>