<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<title><s:text name="subscriptionHistoryForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:form name="subscriptionHistoryForm" method="post" validate="true">

  	<s:if test="currentStatus == 'ACTIVATE'">
		   <h3>
		   	<a href="<s:url value="LookupDeviceStatusHistory.do"><s:param name="phoneNumber" value="%{phoneNumber}"/></s:url>" style="text-decoration:none;"><s:label key="phoneNumber" /></a>
		   </h3>
	</s:if>
	<s:else>				
	    <h3><s:label key="phoneNumber" /></h3>
	</s:else>
	
    <h3><s:label key="currentStatus" /></h3>
    
    <display:table name="subscriptionHistoryList" class="list" requestURI="" id="subscriptionHistoryList" defaultsort="3" defaultorder="descending" export="false" pagesize="10" sort="list">
        <display:column property="publication"      sortable="true"     titleKey="subscriptionHistoryForm.publication" />
        <display:column property="action"           sortable="true"     titleKey="subscriptionHistoryForm.action"/>		
        <display:column property="effectiveTime"    sortable="true"     titleKey="subscriptionHistoryForm.effectiveTime" format="{0,date,dd MMMM yyyy HH:mm:ss}"/> 
        <display:column property="actionBy"         sortable="true"     titleKey="subscriptionHistoryForm.actionBy"/> 
    </display:table>

</s:form>