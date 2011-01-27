<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%
String path = request.getContextPath();
%>

<head>
    <title><s:text name="NoEditionHanderForm.title"/></title>
    <s:head />
</head>

<h3><s:text name="NoEditionHanderForm.title"/></h3>

<s:actionerror />
<s:actionmessage />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>

<s:text name="NoEditionHanderForm.noEdition" />
<p><a href="<%=path%>/AddPublicationEditionInput.do"><s:text name="AddPublicationEditionInput.addEdition"/></a></p>
