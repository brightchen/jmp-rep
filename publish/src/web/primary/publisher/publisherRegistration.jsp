<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>


<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<title><s:text name="publisherRegistration.title"/></title>
	<s:head />
	 <SCRIPT type="text/javascript">
        function prepare()
        {
           var hasError = document.getElementById("hasError");
           
           if (hasError==null){
           
	           var textboxes =document.getElementsByTagName("input");
	           if (textboxes!=null){
	           	for (var i=0; i < textboxes.length; i++){
	           		if (textboxes[i].type=="text" ||textboxes[i].type=="password" ){
	           			textboxes[i].value = "";
	           		}
	           	}
	           }
           }
        }
        
    
    </SCRIPT>
</head>




<h3><s:text name="publisherRegistration.request"/></h3>

<p><s:text name="publisherRegistration.form"/></p>

<s:actionerror />
<s:actionmessage />

<s:form name="publisherRegistrationForm" action="PublisherRegistration" method="post" validate="true">
		<fieldset>
    	<legend><s:text name="publisherRegistration.request"/></legend>
    	
		<s:token />
	
		<s:textfield key="publisher.publishername" required="true" />
        <s:password label="Password"            name="password"         required="true"/>
        <s:password label="Confirm Password"    name="confirmPassword"  required="true"/>

		<s:textfield key="publisherUser.username" required="true" />
		<s:textfield key="publisherUser.firstname" required="true" />
		<s:textfield key="publisherUser.lastname" required="true"  />		
		<s:textfield key="publisherUser.email" required="true" />
		<s:textfield key="publisherUser.phoneNumber" required="true" cssStyle="*margin-bottom:35px"/>		
		</fieldset>
 		<div class="buttonrow">
		<s:submit key="button.submit" cssClass="fbtn" cssStyle="*margin-top:10px"/>
		<s:submit action="Welcome" key="button.cancel" onclick="form.onsubmit=null" cssClass="fbtn"/>
		
		</div>
				
</s:form>	


