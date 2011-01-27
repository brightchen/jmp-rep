<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<title><s:text name="AddEditionSectionsConfirmationForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:if test="#session.virtualPublisher != null">
	<p>Working on virtual publisher - <s:property value="#session.virtualPublisher.publishername"/></p>
</s:if>


<h3><s:text name="editionSectionForm.title"/></h3>


<p>The options you selected is already existed!  <br/>
Please click <a href="<%=request.getContextPath()%>/AddEditionSectionInput.do">here to the Add Section Form</a> and choose a different option. <p>