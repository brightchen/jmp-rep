<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<title><s:text name="publicationList.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />


<h3><s:text name="publicationList.title"/></h3>
<p><s:text name="publicationList.instruction"/></p>

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>
	
<display:table name="publications" class="list" requestURI="" id="publicationList" export="false" pagesize="10" sort="list">
    <display:column property="publicationname" sortable="true" href="UpdatePublicationInput.do" media="html" paramId="id" paramProperty="id" titleKey="publication.publicationname" />
    <display:column property="style" sortable="true" titleKey="publication.style" />
    <display:column property="keyword" sortable="true" titleKey="publication.keyword"/>		
    <display:column property="startdate" sortable="true" titleKey="publication.startdate" format="{0,date,dd MMMM yyyy}"/> 
    <display:column property="enddate" sortable="true" titleKey="publication.enddate" format="{0,date,dd MMMM yyyy}"/>   
</display:table>

