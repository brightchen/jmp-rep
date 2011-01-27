<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<HTML>
<HEAD>
<!-- Author: Michael Falatine 4thorder@4thorder.us - http://www.4thorder.us/Scripts/ -->
<!-- Courtesy of SimplytheBest.net - http://simplythebest.net/scripts/ -->


<BODY>

<!-- Menu start -->
<DIV id="menuSystem">
<TABLE id="mainTable">

<authz:authorize ifAllGranted="admin">
<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.user"/></TH>
</TR>
<TR>
<TD><a href="<%=path%>/ListPublisherUsers.do"><s:text name="menu.listUsers"/></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/CreatePublisherUserInput.do"><s:text name="menu.addUser"/></a></TD>
</TR>
</TABLE>
</TD>
</TR>
</authz:authorize>

<authz:authorize ifAllGranted="operator">
<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.ftpUser"/></TH>
</TR>
<TR>
<TD><a href="<%=path%>/ListFtpUsers.do"><s:text name="menu.listFtpUsers" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/CreateFtpUserInput.do"><s:text name="menu.addFtpUser" /></a></TD>
</TR>
</TABLE>
</TD>
</TR>
</authz:authorize>

<authz:authorize ifAnyGranted="operator">
<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.publication"/></TH>
</TR>
<TR>
<TD><a href="<%=path%>/ListPublications.do"><s:text name="menu.listPublications" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/CreatePublicationInput.do"><s:text name="menu.addPublication" /></a></TD>
</TR>
</TABLE>
</TD>
</TR>
</authz:authorize>

<authz:authorize ifAnyGranted="operator,editor,reviewer">
<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.document"/></TH>
</TR>
<TR>
<TD><a href="<%=path%>/ListPreProcess.do"><s:text name="menu.listDocumentsFromPublication" /></a>
<input id="ListDocuments_do" type="hidden" value="" name="ListDocuments.do"/></TD>
</TR>
<authz:authorize ifAnyGranted="operator">
<TR>
<TD><a href="<%=path%>/CreateDocumentInput.do"><s:text name="menu.addDocument" /></a>
<input id="CreateDocumentPreProcess_do" type="hidden" value="" name="CreateDocumentPreProcess.do"/></TD>
</TR>
</authz:authorize>
</TABLE>
</TD>
</TR>
</authz:authorize>

<authz:authorize ifAllGranted="marketmgr">
<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.report"/></TH>
</TR>
<TR>
<TD class="subMenu"><s:text name="menu.downloadReport"/></TD>
</TR>
<TR>
<TD><a href="<%=path%>/ClientDownloadCustomReportInput.do"><s:text name="menu.customReport" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/ClientDownloadStaticMonthlyReportInput.do"><s:text name="menu.staticMonthlyReport" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/ClientDownloadStaticQuarterlyReportInput.do"><s:text name="menu.staticQuarterlyReport" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/ClientDownloadStaticYearlyReportInput.do"><s:text name="menu.staticYearlyReport" /></a></TD>
</TR>
</TABLE>
</TD>
</TR>
</authz:authorize>


<authz:authorize ifAnyGranted="sysadmin">

<TR>
<TH><s:text name="menu.system.group"/></TH>
</TR>

<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.user"/></TH>
</TR>
<TR>
<TD><a href="<%=path%>/ListPublisherUsers.do"><s:text name="menu.listUsers"/></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/CreatePublisherUserInput.do"><s:text name="menu.addUser"/></a></TD>
</TR>
</TABLE>
</TD>
</TR>

</authz:authorize>

<authz:authorize ifAnyGranted="publishermgr">

<TR>
<TH><s:text name="menu.publisher.group"/></TH>
</TR>

<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.virtual.publisher.user"/></TH>
</TR>

<TR>
<TD><a href="<%=path%>/ListVirtualPublisherUsers.do"><s:text name="menu.listUsers"/></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/CreateVirtualPublisherUserInput.do"><s:text name="menu.addUser"/></a></TD>
</TR>

</TABLE>
</TD>
</TR>

<!--  
<TR>
<TD>
<TABLE>

<TR>
<TH><s:text name="menu.virtual.publisher.ftpUser"/></TH>
</TR>

<TR>
<TD><a href="<%=path%>/ListFtpUsers.do"><s:text name="menu.listFtpUsers" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/CreateFtpUserInput.do"><s:text name="menu.addFtpUser" /></a></TD>
</TR>

</TABLE>
</TD>
</TR>
-->

<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.virtual.publisher.publication"/></TH>
</TR>

<TR>
<TD><a href="<%=path%>/ListVirtualPublications.do"><s:text name="menu.listPublications" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/CreateVirtualPublicationInput.do"><s:text name="menu.addPublication" /></a></TD>
</TR>

</TABLE>
</TD>
</TR>

<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.virtual.publisher.document"/></TH>
</TR>

<TR>
<TD><a href="<%=path%>/ListVirtualDocumentPreProcess.do"><s:text name="menu.listDocumentsFromPublication" /></a>
<input id="ListDocuments_do" type="hidden" value="" name="ListDocuments.do"/></TD>
</TR>
<TR>
<TD><a href="<%=path%>/CreateVirtualDocumentInput.do"><s:text name="menu.addDocument" /></a>
<input id="CreateDocumentPreProcess_do" type="hidden" value="" name="CreateDocumentPreProcess.do"/></TD>
</TR>

</TABLE>
</TD>
</TR>


<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.publisher.management"/></TH>
</TR>
<TR>
<TD><a href="<%=path%>/ListPublishers.do"><s:text name="menu.listPublishers"/></a></TD>
</TR>

<!--  
<TR>
<TD><a href="<%=path%>/CreatePublisherInput.do"><s:text name="menu.addPublisher"/></a></TD>
</TR>
-->

</TABLE>
</TD>
</TR>

<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.report"/></TH>
</TR>
<TR>
<TD class="subMenu"><s:text name="menu.downloadReport"/></TD>
</TR>
<TR>
<TD><a href="<%=path%>/ClientDownloadCustomReportInput.do"><s:text name="menu.customReport" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/ClientDownloadStaticMonthlyReportInput.do"><s:text name="menu.staticMonthlyReport" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/ClientDownloadStaticQuarterlyReportInput.do"><s:text name="menu.staticQuarterlyReport" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/ClientDownloadStaticYearlyReportInput.do"><s:text name="menu.staticYearlyReport" /></a></TD>
</TR>
</TABLE>
</TD>
</TR>

<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.provision"/></TH>
</TR>
<TR>
<TD><a href="<%=path%>/CreateProvisionInput.do"><s:text name="menu.add" /></a></TD>
</TR>
<TR>
<TD> <a href="<%=path%>/SimpleSearchProvisionInput.do"><s:text name="menu.search" /></a>
<input id="SimpleWildSearchProvision_do" type="hidden" value="" name="SimpleWildSearchProvision.do"/>
<input id="SimpleSearchProvision_do" type="hidden" value="" name="SimpleSearchProvision.do"/></TD>
</TR>
<TR>
<TD> <a href="<%=path%>/SearchProvisionInputDel.do"><s:text name="menu.delete" /></a>
<input id="SearchProvisionDel_do" type="hidden" value="" name="SearchProvisionDel.do"/>
<input id="WildSearchProvisionDel_do" type="hidden" value="" name="WildSearchProvisionDel.do"/></TD>
</TR>
</TABLE>
</TD>
</TR>

</authz:authorize>

<authz:authorize ifAnyGranted="subscribermgr">

<TR>
<TH><s:text name="menu.subscriber.group"/></TH>
</TR>

<TR>
<TD>
<TABLE>
<TR>
<TH><s:text name="menu.subscriber.management"/></TH>
</TR>
<TR>
<TD class="subMenu"><s:text name="menu.submenu"/></TD>
</TR>
<TR>
<TD><a href="<%=path%>/DeviceStatusLookupInput.do"><s:text name="menu.device.status.lookup" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/SubscriptionDetailsLookupInput.do"><s:text name="menu.subscription.details.lookup" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/AccessUserDataInput.do"><s:text name="menu.access.user.data" /></a></TD>
</TR>
<TR>
<TD><a href="<%=path%>/MoreActionInput.do"><s:text name="menu.more.action" /></a></TD>
</TR>
</TABLE>
</TD>
</TR>

</authz:authorize>

<authz:authorize ifAnyGranted="sysadmin, sysuser">

<TR>
<TH><s:text name="menu.my.account"/></TH>
</TR>

<TR>
<TD><a href="<%=path%>/ChangePwdInput.do">&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="menu.change.password" /></a></TD>
</TR>

</authz:authorize>


</TABLE>
</DIV>
<!-- Menu end -->

</BODY>
</HTML>