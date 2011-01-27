<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="cg.iseepublish.model.*" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="documentRetireForm.title"/></title>
    <s:head />
</head>
<h3><s:text name="documentRetireForm.title"/></h3>
<p><s:text name="documentRetireForm.message"/></p>

<s:actionerror />
<s:actionmessage />

<s:form name="documentUpdateForm" action="RetireDocument" method="post" validate="true"  enctype="multipart/form-data">
		<fieldset>
    	<legend><s:text name="documentRetireForm.legend"/></legend>
		<s:token />
		<s:hidden name="task" />		
		<s:hidden name="id" />
		<s:hidden name="document.id" />
		<s:hidden name="document.documentname" />
		
		
		<s:label key="document.documentname" />
		<s:if test="#session.document.source != null">
		<div><label class="label"><s:text name="document.sourcefile"/></label>
			<p class="text"><%=((Document)session.getAttribute("document")).getSourceFileName()%></p>
			</div>
		</s:if>
 		</fieldset>
		<div class="buttonrow">	
		<s:submit action="RetireDocument" key="button.retire" onclick="form.onsubmit=null" />		
		<s:submit action="PublisherMainMenu" key="button.cancel" onclick="form.onsubmit=null"/>
		
		</div>
	</s:form>
	