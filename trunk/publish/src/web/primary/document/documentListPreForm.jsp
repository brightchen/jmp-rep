<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<head>
    <title><s:text name="documentPreListForm.title"/></title>
     <SCRIPT LANGUAGE=javascript>
function goto(form) { 
	var index=form.ListWithPreProcess_publication_publicationname.selectedIndex;
	location='<%=request.getContextPath()%>/ListWithPreProcess.do?publication.publicationname='+form.ListWithPreProcess_publication_publicationname.options[index].value;
}
</SCRIPT>
    <s:head />
</head>

<h3><s:text name="documentPreListForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:form name="ListDocumentPreProcessForm" action="ListWithPreProcess" method="post" validate="true">
		<fieldset>
    	<p><s:text name="documentPreListForm.message"/></p>
		<s:token />	
		
		<s:select key="publication.publicationname" headerValue="-- Please select --"
			headerKey="-1"list="publications" listKey="publicationname" listValue="publicationname" onchange="goto(this.form)" />
		</fieldset>	  
</s:form>
	

