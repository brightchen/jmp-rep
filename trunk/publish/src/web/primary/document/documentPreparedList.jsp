<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>


    <title><s:text name="documentForm.title"/></title>
    <s:head />
     <SCRIPT type="text/javascript">
        function prepare()
        {
     
           var docList =document.getElementById("documentList");
           if (docList==null){
           	 var ins =document.getElementById("instruction");
           	 ins.style.display="none";
           }
        }
    </SCRIPT>

	<h3>
		<s:text name="mainMenu.heading">
		<s:param value="#session.publisheruser.username"/>
		</s:text>
	</h3>
	<div id="instruction">
		<p><s:text name="publicationList.instruction"/></p>
        </div>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
	<authz:authorize ifAnyGranted="editor">
	
		<display:table name="preparedDocuments" class="list" requestURI="" id="documentList" export="false" pagesize="12" defaultsort="4" defaultorder="descending" sort="list">
   	
   		   <display:column property="documentname" sortable="true" href="UpdateDocumentInput.do" media="html" paramId="id" paramProperty="id" titleKey="document.documentname" />
 		   <display:column property="publication.publicationname" sortable="true" titleKey="publication.publicationname"  />	
 		   <display:column property="status" sortable="true" titleKey="document.status" />		
 		   <display:column property="created" sortable="true" titleKey="document.created" format="{0,date,dd MMMM yyyy HH:mm}"/> 
		</display:table>
	</authz:authorize>
