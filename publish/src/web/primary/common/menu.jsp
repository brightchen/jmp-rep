<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<HTML>
  <HEAD>
    <script type="text/javascript" language="JavaScript" src="<%=path%>/common/menu_script/DHTMLMenuExpanderV2.js"></script>
    <script type="text/javascript" language="JavaScript" src="<%=path%>/common/menu_script/sample2.js"></script>
    <LINK rel="stylesheet" type="text/css" href="<%=path%>/common/menu_script/vertical.css">
  </HEAD>

  <BODY>

    <DIV id="menuSystem">
    <TABLE id="mainTable">
      <!-- non system admin title-->
      <authz:authorize ifAnyGranted="admin,user">
         <TR>  <TH class="title">  <s:text name="menu.user.mobimedia"/>  </TH> </TR>
      </authz:authorize>
      <!-- admin,sysadmin -->
       <!-- sysadmin, only sysadin shows the special title -->
       <authz:authorize ifAnyGranted="sysadmin">
        <TR>  <TH class="title">  <s:text name="menu.system.group"/>  </TH> </TR>
      </authz:authorize>
      <authz:authorize ifAnyGranted="admin, sysadmin">
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.user"/>  </TH></TR>
            <TR><TD>  <a href="<%=path%>/ListPublisherUsers.do"><s:text name="menu.listUsers"/></a>     </TD></TR>
            <TR><TD>  <a href="<%=path%>/CreatePublisherUserInput.do"><s:text name="menu.addUser"/></a> </TD></TR>
          </TABLE>
        </TD></TR>
       </authz:authorize>
      
  

      <!-- operator -->
      <authz:authorize ifAnyGranted="operator">
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.publication"/>   </TH></TR>
            <TR><TD>  
              <a href="<%=path%>/ListPublications.do"><s:text name="menu.managePublications" /></a>
              <input id="UpdatePublicationInput_do" type="hidden" value="" name="UpdatePublicationInput.do"/>
              <input id="UpdatePublication_do" type="hidden" value="" name="UpdatePublication.do"/>
            </TD></TR>
            <TR><TD>  <a href="<%=path%>/CreatePublicationInput.do"><s:text name="menu.addPublication" /></a>   </TD></TR>
          </TABLE>
        </TD></TR>
      </authz:authorize>

      <!-- operator, editor, reviewer -->
      <authz:authorize ifAnyGranted="operator,editor,reviewer">
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.editionManagement"/>   </TH></TR>
            <TR><TD>
              <a href="<%=path%>/ViewPublicationEditionsInput.do?newSession=true"><s:text name="menu.viewPublicationEditions" /></a>
              <input id="ViewPublicationEditionsInput_do" type="hidden" value="" name="ViewPublicationEditionsInput.do?newSession=true"/>
            </TD></TR>
        </authz:authorize>
            <authz:authorize ifAnyGranted="operator">
              <TR><TD>
                <a href="<%=path%>/AddPublicationEditionInput.do?newSession=true"><s:text name="menu.addPublicationEdition" /></a>
                <input id="AddPublicationEditionInput_do" type="hidden" value="" name="AddPublicationEditionInput.do?newSession=true"/>
                <input id="AddPublicationEditionInput_do" type="hidden" value="" name="AddEditionSection.do"/>
              </TD></TR>
      
              <TR><TD>
                <a href="<%=path%>/AddEditionSectionPreInput.do?newSession=true"><s:text name="menu.AddEditionSection" /></a>
                <input id="AddEditionSectionInput_do" type="hidden" value="" name="newSession=true"/>
              </TD></TR>
            </authz:authorize>
             <authz:authorize ifAnyGranted="operator,editor,reviewer">
          </TABLE>
        </TD></TR>
      </authz:authorize>

      <!-- Report for marketmgr -->
      <!-- marketmgr -->
      <authz:authorize ifAllGranted="marketmgr">
      <authz:authorize ifAnyGranted="sysadmin">
      <TR><TH class="title"><s:text name="menu.report"/></TH></TR>
          
          <TR><TD>
           <TABLE>
            <TR><TH>  <s:text name="menu.report"/>  </TH></TR>
            <TR><TD>  <a href="<%=path%>/ClientDownloadCustomReportInput.do"><s:text name="menu.customReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/ClientDownloadStaticMonthlyReportInput.do"><s:text name="menu.staticMonthlyReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/ClientDownloadStaticQuarterlyReportInput.do"><s:text name="menu.staticQuarterlyReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/ClientDownloadStaticYearlyReportInput.do"><s:text name="menu.staticYearlyReport" /></a>  </TD></TR>
           </TABLE>
          </TD></TR>
          
          <TR><TD>
           <TABLE>
            <TR><TH>  <s:text name="menu.subscriptionReport"/>  </TH></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionCustomReportInput.do"><s:text name="menu.customReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionStaticMonthlyReportInput.do"><s:text name="menu.staticMonthlyReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionStaticQuarterlyReportInput.do"><s:text name="menu.staticQuarterlyReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionStaticYearlyReportInput.do"><s:text name="menu.staticYearlyReport" /></a>  </TD></TR>
            </TABLE>
          </TD></TR>
           
          <TR><TD>
           <TABLE>
            <TR><TH>  <s:text name="menu.advertisementReport"/></TH></TR>
            <TR><TD> <a href="<%=path%>/AdvertisementReportInput.do"><s:text name="menu.adReport" /></a>  </TD></TR>
          </TABLE>
        </TD></TR>
            </authz:authorize>
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.report"/>  </TH></TR>
            
            <TR><TD class="subMenu">  <s:text name="menu.downloadReport"/>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/ClientDownloadCustomReportInput.do"><s:text name="menu.customReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/ClientDownloadStaticMonthlyReportInput.do"><s:text name="menu.staticMonthlyReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/ClientDownloadStaticQuarterlyReportInput.do"><s:text name="menu.staticQuarterlyReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/ClientDownloadStaticYearlyReportInput.do"><s:text name="menu.staticYearlyReport" /></a>  </TD></TR>
            
            <TR><TD class="subMenu"><s:text name="menu.subscriptionReport"/>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionCustomReportInput.do"><s:text name="menu.customReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionStaticMonthlyReportInput.do"><s:text name="menu.staticMonthlyReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionStaticQuarterlyReportInput.do"><s:text name="menu.staticQuarterlyReport" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionStaticYearlyReportInput.do"><s:text name="menu.staticYearlyReport" /></a>  </TD></TR>
            
            <TR><TD class="subMenu"><s:text name="menu.advertisementReport"/> </TD></TR>
            <TR><TD>  <a href="<%=path%>/AdvertisementReportInput.do"><s:text name="menu.adReport" /></a>  </TD></TR>
          </TABLE>
        </TD></TR>
      </authz:authorize>
   
  
      <!-- publishermgr -->
      <authz:authorize ifAnyGranted="publishermgr">
        <TR><TH class="title">  <s:text name="menu.publisher.group"/> </TH></TR>
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.publisher.management"/>  </TH></TR>
            <TR><TD>  <a href="<%=path%>/ListPublishers.do"><s:text name="menu.listPublishers"/></a>  </TD></TR>
          </TABLE>
        </TD></TR>
      </authz:authorize>
  
      <!-- Virtual menu -->
      <authz:authorize ifAnyGranted="virtualpublisher">
        <TR><TH class="title">  <s:text name="menu.virtualpublisher.group"/>  </TH></TR>
        
        <!-- publisher -->
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.virtual.publisher.user"/>    </TH></TR>
            <TR><TD>  <a href="<%=path%>/ListVirtualPublisherUsers.do"><s:text name="menu.listUsers"/></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/CreateVirtualPublisherUserInput.do"><s:text name="menu.addUser"/></a>  </TD></TR>
          </TABLE>
        </TD></TR>
      
        <!-- publication -->
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.virtual.publisher.publication"/> </TH></TR>
            <TR><TD>  <a href="<%=path%>/ListPublications.do?virtual=true"><s:text name="menu.managePublications" /></a>  </TD></TR>
            <TR><TD>  <a href="<%=path%>/CreatePublicationInput.do?virtual=true"><s:text name="menu.addPublication" /></a>  </TD></TR>
          </TABLE>
        </TD></TR>
      
        <!-- editionManagement -->
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.virtual.editionManagement"/> </TH></TR>
            <TR><TD>
              <a href="<%=path%>/ViewPublicationEditionsInput.do?virtual=true&newSession=true"><s:text name="menu.viewPublicationEditions" /></a>
              <input id="ViewVirtualPublicationEditionsInput_do" type="hidden" value="" name="ViewPublicationEditionsInput.do?virtual=true&newSession=true"/>
            </TD></TR>
            <TR><TD>
              <a href="<%=path%>/AddPublicationEditionInput.do?virtual=true&newSession=true"><s:text name="menu.addPublicationEdition" /></a>
              <input id="AddVirtualPublicationEditionInput_do" type="hidden" value="" name="AddPublicationEditionInput.do?virtual=true&newSession=true"/></TD>
              <input id="AddEditionSectionsConfirm_do?virtual=true" type="hidden" value="" name="AddEditionSectionsConfirm.do?virtual=true"/>
            </TD></TR>
            <TR><TD>
              <a href="<%=path%>/AddEditionSectionPreInput.do?virtual=true&newSession=true"><s:text name="menu.AddEditionSection" /></a>
              <input id="AddVirtualEditionSectionInput_do" type="hidden" value="" name="AddEditionSectionInput.do?virtual=true&newSession=true"/>
            </TD></TR>
          </TABLE>
        </TD></TR>
  
      </authz:authorize>
  
      <!-- Report -->
      <authz:authorize ifAnyGranted="reportmgr">
       <TR><TH class="title"><s:text name="menu.report"/></TH></TR>
	<TR><TD>
	  <TABLE>
 	    <TR><TH><s:text name="menu.downloadReport"/></TH></TR>
	    <TR><TD><a href="<%=path%>/ClientDownloadCustomReportInput.do"><s:text name="menu.customReport" /></a></TD></TR>
	    <TR><TD><a href="<%=path%>/ClientDownloadStaticMonthlyReportInput.do"><s:text name="menu.staticMonthlyReport" /></a></TD></TR>
	    <TR><TD><a href="<%=path%>/ClientDownloadStaticQuarterlyReportInput.do"><s:text name="menu.staticQuarterlyReport" /></a></TD></TR>
	    <TR><TD><a href="<%=path%>/ClientDownloadStaticYearlyReportInput.do"><s:text name="menu.staticYearlyReport" /></a></TD></TR>
          </TABLE>
        </TD></TR>

	<TR><TD>
	  <TABLE>
	    <TR><TH><s:text name="menu.subscriptionReport"/></TH></TR>
	    <TR><TD><a href="<%=path%>/SubscriptionCustomReportInput.do"><s:text name="menu.customReport" /></a></TD></TR>
	    <TR><TD><a href="<%=path%>/SubscriptionStaticMonthlyReportInput.do"><s:text name="menu.staticMonthlyReport" /></a></TD></TR>
	    <TR><TD><a href="<%=path%>/SubscriptionStaticQuarterlyReportInput.do"><s:text name="menu.staticQuarterlyReport" /></a></TD></TR>
	    <TR><TD><a href="<%=path%>/SubscriptionStaticYearlyReportInput.do"><s:text name="menu.staticYearlyReport" /></a></TD></TR>
	  </TABLE>
	</TD></TR>

	<TR><TD>
	  <TABLE>
	    <TR><TH><s:text name="menu.advertisementReport"/></TH></TR>
	    <TR><TD><a href="<%=path%>/AdvertisementReportInput.do"><s:text name="menu.adReport" /></a></TD></TR>
	  </TABLE>
	</TD></TR>
      </authz:authorize>
  
      <!-- accessmgr -->
      <authz:authorize ifAnyGranted="accessmgr">
        <TR><TH class="title">  <s:text name="menu.accessmgr.group"/> </TH></TR>
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.provision"/> </TH></TR>
            <TR><TD>  <a href="<%=path%>/CreateProvisionInput.do"><s:text name="menu.add" /></a>  </TD></TR>
            <TR><TD>  
              <a href="<%=path%>/SimpleSearchProvisionInput.do"><s:text name="menu.search" /></a>
              <input id="SimpleWildSearchProvision_do" type="hidden" value="" name="SimpleWildSearchProvision.do"/>
              <input id="SimpleSearchProvision_do" type="hidden" value="" name="SimpleSearchProvision.do"/>
            </TD></TR>
            <TR><TD>  
              <a href="<%=path%>/SearchProvisionInputDel.do"><s:text name="menu.delete" /></a>
              <input id="SearchProvisionDel_do" type="hidden" value="" name="SearchProvisionDel.do"/>
              <input id="WildSearchProvisionDel_do" type="hidden" value="" name="WildSearchProvisionDel.do"/>
            </TD></TR>
          </TABLE>
        </TD></TR>
      </authz:authorize>
  
      <!-- subscribermgr -->
      <authz:authorize ifAnyGranted="subscribermgr">
        <TR><TH class="title">  <s:text name="menu.subscriber.group"/>  </TH></TR>
  
        <TR><TD>
          <TABLE>
            <TR><TH>  <s:text name="menu.subscriber.management"/> </TH></TR>
            <TR><TD>  <a href="<%=path%>/SubscriberManagementInput.do"><s:text name="menu.device.status.lookup" /></a>     </TD></TR>
            <TR><TD>  <a href="<%=path%>/SubscriptionManagementInput.do"><s:text name="menu.subscription.lookup" /></a> </TD></TR>
          </TABLE>
        </TD></TR>
      </authz:authorize>
  
       <!-- sysadmin, sysuser -->
      <authz:authorize ifAnyGranted="sysadmin, sysuser">
        <TR><TH class="title"><s:text name="menu.my.account"/></TH></TR>
	      <TR><TD>
	        <TABLE>
      	    <TR><TH><s:text name="menu.manage.my.account"/></TH></TR>
      	    <TR><TD><a href="<%=path%>/ChangePwdInput.do?sysAdmin=true">&nbsp;&nbsp;&nbsp;&nbsp;<s:text name="menu.change.password" /></a></TD></TR>
          </TABLE>
        </TD></TR>
      </authz:authorize>
      
   </TABLE>
  </TD></TR>
 </TABLE>
</DIV>
</BODY>
</HTML>