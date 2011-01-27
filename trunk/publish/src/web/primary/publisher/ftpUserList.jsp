<%-- $Id: ftpUserList.jsp,v 1.2 2007/07/13 15:46:28 kellyc Exp $ --%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<title><s:text name="ftpUserList.title" /></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<h1><s:text name="ftpUserList.userlist" /></h1>
<p><s:text name="ftpUserList.instruction" /></p>

<display:table name="publisherUsers" class="list" requestURI="" id="publisherUserList" export="false" pagesize="10" sort="list">
    <display:column property="username" sortable="true" href="UpdateFtpUserInput.do" media="html" paramId="id" paramProperty="id" titleKey="publisherUser.username" />
    <display:column property="firstname" sortable="true" titleKey="publisherUser.firstname" />
    <display:column property="lastname" sortable="true" titleKey="publisherUser.lastname" />
    <display:column property="email" sortable="true" titleKey="publisherUser.email" />
    <display:column property="phoneNumber" sortable="true" titleKey="publisherUser.phoneNumber" />
</display:table>

