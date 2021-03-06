<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<head>
    <title><s:text name="provision.title"/></title>
    <s:head />
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/common/menu_script/checkbox.js"></script>
</head>

<h3><s:text name="phoneProvision.legend"/></h3>

<s:actionerror />
<s:actionmessage />

<s:form name="phoneProvisionCreateForm" action="deleteProvisionRecordInput" method="post" validate="true" >
		  <s:token />	
		<s:hidden name="phoneNumber" />
		<s:label key="phoneNumber" />
	
		<display:table name="phonePublications" class="list" requestURI="" id="phonePublications" export="false" pagesize="10" defaultsort="1" defaultorder="descending" decorator="cg.iseepublish.web.util.Wrapper" >
   			<display:column property="actionLink"  title="<input type='checkbox' id='checkall' name='selectAllMessages' onclick='selectall()' title='Select all'/>" style=" width: 20px; whitespace: nowrap; vertical-align: middle;" />
   			<display:column property="phoneNumber" sortable="true" titleKey="provision.phoneNumber" />
   			<display:column property="shortCode" sortable="true" titleKey="provision.shortCode" />		
  			<display:column property="publicationName" sortable="true" titleKey="provision.publicationName" /> 
		</display:table>
	
		<s:if test="#session.phonePublications.size()!=0">
		<div class="buttonrow">

			<input type="submit" id="updateMsg__delete"  value="Delete" disabled="true"/>
			<s:submit key="button.cancel" action="SearchProvisionInputDel" onclick="form.onsubmit=null"/>
			
		</div>
		</s:if>
</s:form>