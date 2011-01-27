<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<title><s:text name="subscriptionDetailsForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:form name="subscriptionDetailsForm" method="post" validate="true">

	<s:if test="currentStatus == 'ACTIVATE'">
		   <h3>
		   	<a href="<s:url value="LookupDeviceStatusHistory.do"><s:param name="phoneNumber" value="%{phoneNumber}"/></s:url>" style="text-decoration:none;"><s:label key="phoneNumber" /></a>
		   </h3>
	</s:if>
	<s:else>				
	    <h3><s:label key="phoneNumber" /></h3>
	</s:else>
	
	<h3><s:label key="currentStatus" /></h3>
    
    <display:table name="subscriptionList" class="list" requestURI="" id="subscriptionList" export="false" pagesize="10" sort="list">
        <display:column property="publication.publicationname"  sortable="true"     titleKey="subscriptionDetailsForm.publicationname" />
        <display:column property="created"                      sortable="true"     titleKey="subscriptionDetailsForm.time"/>		
    </display:table>

</s:form>