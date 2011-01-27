<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags"%>

<title><s:text name="report.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<h1><s:text name="report.h1"/></h1>

<display:table name="dataList" class="list" requestURI="" id="ClientDownloadJasperData" export="false" pagesize="20" sort="list">
    <display:column property="dateStr" sortable="true" title="Date" />
    <display:column property="downloaded" sortable="true" title="Client Downloaded" />
    <display:column property="activated" sortable="true" title="Activated client" />
    <display:column property="suspended" sortable="true" title="Suspended client" />
</display:table>
<!--
<s:form name="clientDownloadReportForm" action="ClientDownloadReport" method="post" validate="true">

<%session.setAttribute("startDate", session.getAttribute("startDate"));%>
<%session.setAttribute("endDate",session.getAttribute("endDate"));%>
<%session.setAttribute("reportStyles", session.getAttribute("reportStyles"));%>

<p>Export to: 
<s:submit action="JasperClientDownloadReportCSV" key="button.CSV" onclick="form.onsubmit=null" cssClass="bttn"/>
<s:submit action="JasperClientDownloadReportPDF" key="button.PDF" onclick="form.onsubmit=null" cssClass="bttn"/>
<s:submit action="JasperClientDownloadReportRTF" key="button.RTF" onclick="form.onsubmit=null" cssClass="bttn"/>
<s:submit action="JasperClientDownloadReportXLS" key="button.XLS" onclick="form.onsubmit=null" cssClass="bttn"/>	</p>
</s:form> -->