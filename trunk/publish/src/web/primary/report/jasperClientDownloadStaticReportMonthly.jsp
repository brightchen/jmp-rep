<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title><s:text name="jasperClientDownloadReport.title"/></title>
    <s:head />
</head>

<body>

<h3><s:text name="jasperClientDownloadReport.title"/></h3>

<s:actionerror />
<s:actionmessage />
<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>
<s:form name="clientDownloadReportStaticForm" action="ClientDownloadStaticMonthlyReport" method="post" validate="true">
	<fieldset>
   	<legend><s:text name="monthlyreport.legend"/></legend>
   	<dl>
	<s:token />
	
	<s:hidden key="reportStyle" value="Monthly" />

	<s:select key="startMonth" list="months" label="Start Month" required="true" cssStyle="width:95px"/>
	<s:select key="startYear" list="years" required="true" label="Start Year" cssStyle="width:60px"/>
	<s:select key="endMonth" list="months" label="End Month" required="true" cssStyle="width:95px"/>
	<s:select key="endYear" list="years" label="End Year" required="true" cssStyle="width:60px"/>
	
	<s:select key="reportFormat" list="reportFormats" required="true" cssStyle="width:60px;"/>
	</fieldset>
 		<div class="buttonrow">
	<s:submit key="button.CreateReport" cssClass="fbtn"/>
	<s:submit action="PublisherMainMenu" key="button.Cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>	  
	</div>
</s:form>


