<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:directive.page import="cg.iseepublish.model.PhonePublication"/>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<head>

    <title><s:text name="provision.title"/></title>
    <s:head />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

</head>

<body>

<s:actionerror />
<s:actionmessage />


<s:form name="confirmDelProvision" action="deleteProvisionWildRecord" method="post" validate="true" >
		<s:token />	
		<s:hidden name="phoneNumber" />
		<display:table name="phonePublications" class="list" id="phonePublications" export="false" pagesize="10" defaultorder="descending" decorator="cg.iseepublish.web.util.Wrapper" >
   			<display:column sortable="true" titleKey="provision.phoneNumber" >
   			<%=((PhonePublication)pageContext.getAttribute("phonePublications")).getPhoneNumber()%>
   			<input type="hidden" name="selectedMessages" value="<%=((PhonePublication)pageContext.getAttribute("phonePublications")).getId()%>" />
   			</display:column>

   			<display:column property="shortCode"  titleKey="provision.shortCode" />		
  			<display:column property="publicationName"  titleKey="provision.publicationName" /> 
		</display:table>
		<center>Are you sure you want to delete the selected record?</center>		
		<div class="buttonrow">
			<s:submit action="deleteProvisionWildRecord" value="Delete" />
			<s:submit action="WildSearchProvisionDel" key="button.cancel" onclick="form.onsubmit=null" />
		</div>
		
</s:form>



</body>

