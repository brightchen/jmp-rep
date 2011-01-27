<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="documentPreForm.title"/></title>
    <s:head />
    <SCRIPT LANGUAGE=javascript>
function goto(form) { 
	var index=form.CreateDocumentPreProcess_publication_publicationname.selectedIndex;
<!--	alert(form.CreateDocumentPreProcess_publication_publicationname.options[index].value); -->
	location='<%=request.getContextPath()%>/CreateDocumentPreProcess.do?publication.publicationname='+form.CreateDocumentPreProcess_publication_publicationname.options[index].value;
}
</SCRIPT>
</head>

<h3><s:text name="documentPreForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:form name="CreateDocumentPreProcessForm" action="CreateDocumentPreProcess" method="post" validate="true">
    		<fieldset>
    		 	<p><s:text name="documentPreForm.message"/></p>
		<s:token />	
		<s:select key="publication.publicationname" list="publications" headerValue="-- Please select --"
			headerKey="-1" listKey="publicationname" listValue="publicationname" onchange="goto(this.form)" />
		</fieldset>
</s:form>
	

	
