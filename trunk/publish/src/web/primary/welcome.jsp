<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>

  <head>
    <title><s:text name="index.title"/></title>
  </head>

  <div class="three_up searches">
  	<div class="node first">
  		<h3></h3>		
  	</div>
  	<div class="node">		
  		<h3>Main page - Welcome</h3>
  		<s:if test="#session.user.username != null"></s:if>
  		<s:else>
  			<ul>
  				<authz:authorize ifAllGranted="ROLE_ANONYMOUS">    
  					<li><a href="<s:url action="MainMenu"/>">User Login</a></li>
  				</authz:authorize> 
  			</ul>  
  		</s:else>
  	</div>
  
  	<div class="node last">
  		<div class="signup_promo">
  			<h3>Signup</h3>
  			<p>You can <a href="<s:url action="RegistrationInput"/>">signup today</a>.</p>
  			<div style="width:100px;height:90px;"></div>								
  		</div>		
  	</div>		
  </div>
  
  <div class="three_up searches">
  	<div class="node first">
  		<h3></h3>		
  	</div>
  
  	<div class="node">
  		<h3></h3>		
  	</div>
  	<div class="node last">
  		<h3></h3>
  	</div>
  </div>


