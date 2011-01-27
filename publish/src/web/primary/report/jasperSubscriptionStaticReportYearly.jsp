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
<s:form name="subscriptionReportStaticForm" action="SubscriptionStaticYearlyReport" method="post" validate="true">
	<fieldset>
   	<legend><s:text name="subscriptionYearlyreport.legend"/></legend>
   	
	<s:token />

	<s:hidden key="reportStyle" value="Yearly" />
	
	<s:select key="publicationName" list="publicationNames" required="true" />
	<s:select key="startYear" list="years" label="Start Year" required="true" cssStyle="width:60px"/>
	<s:select key="endYear" list="years" label="End Year" required="true" cssStyle="width:60px"/>
	<s:select key="reportFormat" list="reportFormats" required="true" cssStyle="width:60px"/>
	</fieldset>
 		<div class="buttonrow">
	<s:submit key="button.CreateReport" cssClass="fbtn"/>
	<s:submit action="PublisherMainMenu" key="button.Cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>	  
	</div>
	
</s:form>
	


