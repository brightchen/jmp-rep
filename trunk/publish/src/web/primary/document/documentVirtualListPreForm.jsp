<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="documentPreListForm.title"/></title>
    <s:head />
    
<SCRIPT LANGUAGE=javascript>
function goto(form) { 
	var index=form.ListVirtualDocumentWithPreProcess_publication_publicationname.selectedIndex;
	location='<%=request.getContextPath()%>/ListVirtualDocumentWithPreProcess.do?publication.publicationname='+form.ListVirtualDocumentWithPreProcess_publication_publicationname.options[index].value;
}
</SCRIPT>
    
</head>

<h3><s:text name="documentPreListForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>

	<s:form name="ListDocumentPreProcessForm" action="ListVirtualDocumentWithPreProcess" method="post" validate="true">
			<fieldset>
	    	<p><s:text name="documentPreListForm.message"/></p>
			<s:token />			
			<s:select key="publication.publicationname" headerValue="--- Please select ---"
				headerKey="-1"list="publications" listKey="publicationname" listValue="publicationname" onchange="goto(this.form)" />
			</fieldset>	  
	</s:form>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>	

