<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<head>
    <title><s:text name="documentForm.title"/></title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
    <s:head />
    
<SCRIPT LANGUAGE=javascript>
function goto(form) { 
	var index=form.ListVirtualDocumentWithPreProcess_publication_publicationname.selectedIndex;
	location='<%=request.getContextPath()%>/ListVirtualDocumentWithPreProcess.do?publication.publicationname='+form.ListVirtualDocumentWithPreProcess_publication_publicationname.options[index].value;
}
</SCRIPT>
    
</head>

<body>

<title><s:text name="documentList.title"/></title>

<script language="JavaScript">
<!-- 
setTimeout( "refresh()", 100*1000 ); 
function refresh()
{
    window.location.href = document.location;
} 

function PreselectMyItem()
  {    // Get a reference to the drop-down
  
  
    var myDropdownList = document.ListWithPreProcess.ListWithPreProcess_publication_publicationname;
    var itemToSelect = $(publication.publicationname);
    // Loop through all the items
    for (iLoop = 0; iLoop< myDropdownList.options.length; iLoop++)
    {    
      if (myDropdownList.options[iLoop].value == itemToSelect)
      {
        // Item is found. Set its selected property, and exit the loop
        myDropdownList.options[iLoop].selected = true;
        break;
      }
    }
  }
//-->
</script>

<h3><s:text name="documentList.title"/></h3>

<s:form name="ListDocumentPreProcessForm"  action="ListVirtualDocumentWithPreProcess" method="post" validate="true">
	<fieldset>
		<p><s:text name="documentPreListForm.message"/></p>
		<s:token />
		<div style="font-size:1.4em; " ><s:select key="publication.publicationname" list="publications" headerKey="-1" headerValue="-- Please select --" listKey="publicationname" listValue="publicationname" onchange="goto(this.form)"/></div>
	</fieldset>		  	
</s:form>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>

<p><s:text name="documentList.instruction"/></p>
	
<display:table name="documents" class="list" requestURI="" id="documentList" export="false" pagesize="6" defaultsort="3" defaultorder="descending" sort="list">
    <display:column property="documentname" sortable="true" href="UpdateVirtualDocumentInput.do" media="html" paramId="id" paramProperty="id" titleKey="document.documentname" />
    <display:column property="status" sortable="true" titleKey="document.status" />		
    <display:column property="created" sortable="true" titleKey="document.created" format="{0,date,dd MMMM yyyy HH:mm}"/> 
    <display:column property="updated" sortable="true" titleKey="document.updated" format="{0,date,dd MMMM yyyy HH:mm}"/>
</display:table>

</s:if><s:else>				
	<p>No virtual publisher in your session</p>
</s:else>

</body>