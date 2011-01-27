<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<head>
  <title><s:text name="publisherList.title" /></title>
    <s:head />
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/common/menu_script/enable.js"></script>
</head>
	

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<h1><s:text name="publisherList.list" /></h1>
<h4><s:text name="publisherList.instruction" /></h4>
<s:form name="phoneProvisionCreateForm" action="EnablePublisherInput" method="post" validate="true" >
	<s:token />	
	<display:table name="publishers" class="list" requestURI="" id="publisherList" export="false" pagesize="10" defaultsort="2" defaultorder="descending" decorator="cg.iseepublish.web.util.PublisherWrapper" >
		<display:column property="actionLink"  title="" style=" width: 20px; whitespace: nowrap; vertical-align: middle;"/>
		<display:column property="publishername" sortable="true" href="UpdatePublisherInput.do" media="html" paramId="id" paramProperty="id" titleKey="publisher.publishername" />
		<display:column property="contact" sortable="true" titleKey="publisher.contact" />
		<display:column property="status" sortable="true" titleKey="publisher.status" />
		<display:column property="enabled" sortable="true" titleKey="publisher.enabled" />
	</display:table>
	
	<div class="buttonrow">
	<s:submit action="EnablePublisherInput" key="button.enable" disabled="true" onclick="form.onsubmit=null" id="button.enable" />
	<s:submit action="DisablePublisherInput" key="button.disable" disabled="true" onclick="form.onsubmit=null" id="button.disable" />
	
	</div>
</s:form>