<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<title><s:text name="subscriptionManagementForm.title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="imagetoolbar" content="no" />
	<s:head />
</head>

<body>

	<h3><s:text name="menu.subscription.lookup"/></h3>

	<s:actionerror />
	<s:actionmessage />

	<p><strong><s:text name="note"/></strong><s:text name="require.field"/>(<em>*</em>)</p>

	<s:form name="subscriptionManagementForm" action="ChangeSubscription" method="post" validate="true" >
	    
    <s:if test="subscriptionOperationStatus == 'NULL'">
      <fieldset>
        <s:textfield key="phoneNumber" required="true"/>
      </fieldset>
      <div class="buttonrow">
        <s:submit action="QuerySubscription" key="button.QuerySubscription" onclick="form.onsubmit=null"/>
      </div>
    </s:if>
    
    <s:else>
      <fieldset>
        <legend><s:text name="subscriptionManagementForm.legend"/></legend>
        
        <s:token />
        <s:hidden name="subscriptionOperationStatus" />
        
        <s:textfield key="phoneNumber" required="true"/>

        <div class="controlset">
          <span class="label"><s:text name="subscription.label"/></span>	
          <s:optiontransferselect
            name="availableSubscriptions"			
            allowSelectAll="false"
            allowAddAllToLeft="false"
            allowAddAllToRight="false"
            doubleHeaderValue="-- Assigned subscriptions --"
            headerValue="-- Available subscriptions --"
            doubleHeaderKey="-1"
            headerKey="-1"
            allowUpDownOnLeft="false"
            allowUpDownOnRight="false"
            list="availableSubscriptions"
            size="10"
            doubleSize="10"			
            cssClass="bttn"
			emptyOption="false"
			buttonCssClass="btn"
            doubleList="assignedSubscriptions"
            doubleName="assignedSubscriptions"			
          />
 
        </div>

      </fieldset>
      
      <div class="buttonrow">
        <s:submit action="QuerySubscription" key="button.QuerySubscription" onclick="form.onsubmit=null"/>
        <s:if test="subscriptionOperationStatus == 'QUERIED'">
          <s:submit key="button.ChangeSubscription"/>
        </s:if>
      </div>	
    </s:else>	
	
  </s:form>

</body>
</html>

