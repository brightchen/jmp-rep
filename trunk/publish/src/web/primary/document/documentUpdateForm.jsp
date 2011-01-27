<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="cg.iseepublish.model.*" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

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
		<s:hidden name="id" />
		<s:hidden name="url" />
		<s:hidden name="document.id" />
		<s:hidden name="document.documentname" />
		<s:hidden name="publication.publicationname"/>
		<s:label key="document.documentname" />
		<s:label key="document.status" />
		<s:label key="document.style" />
<s:if test="#session.document.source != null">
		<div><label class="label"><s:text name="document.sourcefile"/></label>
       <p class="text"> <s:url id="url" action="Download?fileLink=source"/>
        <s:a href="%{url}"><%=((Document)session.getAttribute("document")).getSourceFileName()%></s:a></p>
     </div>
</s:if>
</fieldset>
<div class="buttonrow">
<s:if test="#session.document.status == 'PREPARED'">
	<authz:authorize ifAnyGranted="editor,publishermgr">
		<s:submit action="EditDocument" key="button.edit" onclick="form.onsubmit=null" cssClass="fbtn"/>
	</authz:authorize>
</s:if>

<s:if test="#session.document.status == 'EDITED'">
	<authz:authorize ifAnyGranted="editor,publishermgr">
		<s:submit action="EditDocument" key="button.edit" onclick="form.onsubmit=null" cssClass="fbtn"/>
	</authz:authorize>
	<authz:authorize ifAnyGranted="reviewer,publishermgr">
		<s:if test="#session.document.policy == 'NeedReview'">
			<s:submit action="ReviewDocument" key="button.review" onclick="form.onsubmit=null" cssClass="fbtn"/>
		</s:if>
		

	</authz:authorize>
</s:if>
	
<authz:authorize ifAnyGranted="operator,publishermgr">
<s:if test="#session.document.status == 'REVIEWED'">
		<s:submit action="EditDocument" key="button.edit" onclick="form.onsubmit=null" cssClass="fbtn"/>
	<!--	<s:if test="#session.document.policy == 'NeedReview'">
			<s:submit action="ReviewDocument" key="button.review" onclick="form.onsubmit=null" cssClass="fbtn"/>
		</s:if> -->
		
</s:if>
<!--
<s:if test="#session.document.status == 'PUBLISHED'">
		<s:submit action="RetireDocumentInput" key="button.retire" onclick="form.onsubmit=null" cssClass="fbtn"/>	
</s:if> -->
<s:if test="#session.document.status != 'NEW'">
<s:if test="#session.document.status != 'LOCKED'">
<s:if test="#session.document.status != 'EDITED'">
<s:if test="#session.document.status != 'REVIEWED'">
<s:if test="#session.document.status != 'PUBLISHED'">
    <s:submit action="DeleteDocumentInput" key="button.delete" onclick="form.onsubmit=null" cssClass="fbtn"/>
</s:if>
</s:if>
</s:if>
</s:if>
</s:if>

</authz:authorize>		

<s:if test="#session.document.status == 'EDITED'">
    <authz:authorize ifAnyGranted="admin,sysadmin,publishermgr">
        <s:submit action="DeleteDocumentInput" key="button.delete" onclick="form.onsubmit=null" cssClass="fbtn"/>
    </authz:authorize>
</s:if>


		<s:submit action="ViewPublicationEditionsInput" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>
		
		
	</div>	
		
	</s:form>
	