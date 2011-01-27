<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="cg.iseepublish.model.*" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
<head>
    <title><s:text name="documentForm.title"/></title>
    <s:head />
</head>


<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>

<s:form name="documentUpdateForm" action="UpdateDocument" method="post" validate="true"  enctype="multipart/form-data">
	<fieldset>
    		<legend><s:text name="doucmentUpdateForm.legend" /></legend>
		<s:token />
		<s:hidden name="task" />		
		<s:hidden name="pubEdid" />

		<s:label key="publication.publicationname" />
		<s:label key="publicationEdition.editionName" />

 		<display:table name="publicationTotalSections" class="list" requestURI="" id="publicationTotalSectionList" export="false" pagesize="10" sort="list">
        		<display:column property="sectionName" sortable="true" title="Section Name" />
        		<display:column property="status" sortable="true" titleKey="viewPublicationEditionsForm.sectionStatus"/>		
        		<display:column property="displayCreateTime" sortable="true" titleKey="viewPublicationEditionsForm.createTime"/> 
        		<display:column property="displayUpdateTime" sortable="true" titleKey="viewPublicationEditionsForm.updateTime"/> 
    		</display:table>
	</fieldset>	
	<%session.setAttribute("publicationEdition", session.getAttribute("publicationEdition"));%>
	<div class="buttonrow">
		<authz:authorize ifAnyGranted="operator,publishermgr">
			
			<s:if test="#session.publicationEdition.status == 'REVIEWED'">
				<s:submit action="PublishEdition" key="button.publish" onclick="form.onsubmit=null" cssClass="fbtn"/>
			</s:if>

			<s:if test="#session.publicationEdition.status == 'PUBLISHED'">
				<s:submit action="RetireEdition" key="button.retire" onclick="form.onsubmit=null" cssClass="fbtn"/>	
			</s:if>
		</authz:authorize>		
	
		<s:submit action="ViewPublicationEditionsInput" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>
	</div>	
		
</s:form>
	