<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<title><s:text name="iseepublish.title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/welcome.css" />
	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="imagetoolbar" content="no" />
	<s:head />
</head>

<body>

<s:if test="#session.user.username != null">
	<s:include value="/common/logout.jsp" />
</s:if><s:else>				
	<s:include value="/common/header.jsp" />
</s:else>

<div id="wrapper">
<div class="inner">
<div id="heading">
	<h1>Login here</h1>
</div>

<div id="login_layout">
<p><s:text name="login.description1" /><sup><s:text name="login.description2" /></sup> <s:text name="login.description3" /></p>
<br />
<div id="columns">	
    <s:if test="#session.SPRING_SECURITY_403_EXCEPTION != null" >
        <font color="#FF0000">Log in failed - 
            <s:text name="login.accessDenied" />
        </font><br /><br />
    </s:if>
    <s:else>
		<s:if test="#session.SPRING_SECURITY_LAST_EXCEPTION != null">
			<font color="#FF0000">Log in failed - 
				<s:if test="#session.SPRING_SECURITY_LAST_EXCEPTION.message == 'Bad credentials'">
					<s:text name="login.wrongPwd" />
				</s:if><s:else>
					<s:if test="#session.SPRING_SECURITY_LAST_EXCEPTION.message == 'User is disabled'">
						<s:text name="login.userDisabled" />
					</s:if><s:else>
						<s:text name="login.userNotExist" />
					</s:else> 
				</s:else>
			</font><br /><br />
		</s:if>
	</s:else>
<div class="login">


    <form action="j_security_check" method="post">
		<fieldset>			
			
			<legend><s:text name="login.legend" /></legend>
				<label for="publisherUser.username">user name</label>
				<input type="text" id="publisherUser.username" name="j_username" />
				<label for="publisherUser.password">password</label>
				<input type="password" id="publisherUser.password" name="j_password" />
				<p ><input type="image" height="30" width="100" src="<%=request.getContextPath()%>/images/btn_login.gif" alt="Login" />  
				<a class="navLink" href="<s:url action="ForgotPwd_input"/>"><s:text name="login.forgot" /></a></p>							
		</fieldset>
    </form>
</div>
<div class="signup_promo">
	<h3><s:text name="login.request" /></h3>
	<p> <s:text name="login.signup.msg1" /><a href="<s:url action="PublisherRegistrationInput"/>"><s:text name="login.signup.here" /></a> <s:text name="login.signup.msg2" /></p>
	<div style="width:100px;height:90px;"></div>								
</div>
</div>


<div id="ext"></div>
</div>

<s:if test="#request.docid == null">
	<s:include value="/common/footer.jsp" />
</s:if>
</div>
</body>
</html>