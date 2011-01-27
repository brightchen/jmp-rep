<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="jasperSubscriptionReport.title"/></title>
    <s:head />  
</head>

<body>

<h3><s:text name="jasperSubscriptionReport.title"/></h3>

<s:actionerror />
<s:actionmessage />
<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>
<s:form name="subscriptionReportForm" action="SubscriptionReport" method="post" validate="true">
	<fieldset>
   	<legend><s:text name="subscriptionDailyreport.legend"/></legend>
   	
	<s:token />
	<s:select key="publicationName" list="publicationNames" required="true" />
	<s:select key="reportStyle" list="reportStyles" required="true" />
	<s:select key="reportFormat" list="reportFormats" required="true" />
	<s:datetimepicker key="startDate" toggleType="fade" disabled="true" toggleDuration="1000" required="true" value="today"/>
	<s:datetimepicker key="endDate" toggleType="fade" disabled="true" toggleDuration="1000" required="true" value="today"/>
	</fieldset>
 		<div class="buttonrow">
	<s:submit key="button.CreateReport" cssClass="fbtn"/>
	<s:submit action="PublisherMainMenu" key="button.Cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>	  
	</div>
</s:form>
	
</body>

