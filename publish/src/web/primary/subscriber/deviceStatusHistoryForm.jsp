<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<title><s:text name="deviceStatusHistoryForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:form name="deviceStatusHistoryForm" method="post" validate="true">

	<s:if test="currentStatus == 'ACTIVATE'">
		   <h3>
		   	<a href="<s:url value="LookupSubscriptionDetails.do"><s:param name="phoneNumber" value="%{phoneNumber}"/></s:url>" style="text-decoration:none;"><s:label key="phoneNumber" /></a>
		   </h3>
	</s:if>
	<s:else>				
	    <h3><s:label key="phoneNumber" /></h3>
	</s:else>
    
    <h3><s:label key="currentStatus" /></h3>
    
    <display:table name="deviceStatusHistoryList" class="list" requestURI="" id="deviceStatusHistoryList" export="false" defaultsort="2" defaultorder="descending" pagesize="10" sort="list">
        <display:column property="status"           sortable="true"     titleKey="deviceStatusHistoryForm.status" />
        <display:column property="effectiveTime"    sortable="true"     titleKey="deviceStatusHistoryForm.effectiveTime" format="{0,date,dd MMMM yyyy HH:mm:ss}"/>		
        <display:column property="actionBy"         sortable="true"     titleKey="deviceStatusHistoryForm.actionBy"/> 
        <display:column property="reason"           sortable="true"     titleKey="deviceStatusHistoryForm.reason"/> 
    </display:table>

</s:form>