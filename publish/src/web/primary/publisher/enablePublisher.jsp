<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
	<title><s:text name="publisher.enable.legend"/></title>
	<s:head />
</head>


<s:actionerror />
<s:actionmessage />

<s:form name="publisherUpdateForm" action="EnablePublisher" method="post" validate="false">
	<fieldset>
	<p><s:text name="publisher.enable.confirm" /></p>	
    	<legend><s:text name="publisher.enable.legend"/></legend>    
		<s:token />
		<s:hidden key="publisher.publishername" />	
		<s:hidden key="id" />			
		<s:label key="publisher.publishername" />
	
			
		
	</fieldset>
 	<div class="buttonrow">
		<s:submit key="button.enable" />
		<s:submit action="ListPublishers" key="button.cancel" onclick="form.onsubmit=null" />		
	</div>				
</s:form>	


