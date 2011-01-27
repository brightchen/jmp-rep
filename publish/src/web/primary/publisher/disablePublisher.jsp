<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
	<title><s:text name="publisher.disable.legend"/></title>
	<s:head />
</head>


<s:actionerror />
<s:actionmessage />

<s:form name="publisherUpdateForm" action="DisablePublisher" method="post" validate="true">
	<fieldset>
	
    	<legend><s:text name="publisher.disable.legend"/></legend>    
    		<p><s:text name="publisher.disable.confirm" /></p>	
		<s:token />
		<s:hidden key="id" />	
		<s:hidden key="publisher.publishername" />			
		<s:label key="publisher.publishername" />
		
		
		
	</fieldset>
 	<div class="buttonrow">
		<s:submit key="button.disable" />
		<s:submit action="ListPublishers" key="button.cancel" onclick="form.onsubmit=null" />		
	</div>				
</s:form>	


