<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="cg.iseepublish.model.*" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="documentForm.title"/></title>
    <s:head />
</head>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>

<s:form name="documentUpdateForm" action="UpdateVirtualDocument" method="post" validate="true"  enctype="multipart/form-data">
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
		<s:submit action="EditVirtualDocument" key="button.edit" onclick="form.onsubmit=null" cssClass="fbtn"/>
</s:if>

<s:if test="#session.document.status == 'EDITED'">
		<s:submit action="EditVirtualDocument" key="button.edit" onclick="form.onsubmit=null" cssClass="fbtn"/>
		<s:if test="#session.document.policy == 'NeedReview'">
			<s:submit action="ReviewVirtualDocument" key="button.review" onclick="form.onsubmit=null" cssClass="fbtn"/>
		</s:if>
		<!--
		<s:else>
 			<s:submit action="PublishVirtualDocument" key="button.publish" onclick="form.onsubmit=null" cssClass="fbtn"/>
 		</s:else>
 		-->
</s:if>

<s:if test="#session.document.status == 'REVIEWED'">
		<s:submit action="EditVirtualDocument" key="button.edit" onclick="form.onsubmit=null" cssClass="fbtn"/>
		<s:if test="#session.document.policy == 'NeedReview'">
			<s:submit action="ReviewVirtualDocument" key="button.review" onclick="form.onsubmit=null" cssClass="fbtn"/>
		</s:if>
	<!--	<s:submit action="PublishVirtualDocument" key="button.publish" onclick="form.onsubmit=null" cssClass="fbtn"/> -->
</s:if>
<!--
<s:if test="#session.document.status == 'PUBLISHED'">
		<s:submit action="RetireVirtualDocumentInput" key="button.retire" onclick="form.onsubmit=null" cssClass="fbtn"/>	
</s:if>

-->
<s:if test="#session.document.status != 'NEW'">
<s:if test="#session.document.status != 'LOCKED'">
<s:if test="#session.document.status != 'EDITED'">
<s:if test="#session.document.status != 'REVIEWED'">
<s:if test="#session.document.status != 'PUBLISHED'">
		<s:submit action="DeleteVirtualDocumentInput" key="button.delete" onclick="form.onsubmit=null" cssClass="fbtn"/>
</s:if>
</s:if>
</s:if>
</s:if>
</s:if>		
		<s:submit action="ListVirtualDocumentWithPreProcess" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>		
</div>		
</s:form>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>
	