<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<% java.util.Calendar cal = java.util.Calendar.getInstance(); %>

<div id="account">
<div class="inner">
<p>
<% out.print(cal.getTime().toString()); %>
</p>
  

<div id="login">
<p>Not logged in&nbsp;&nbsp;<span>&#8226;</span>&nbsp;&nbsp; <a
	class="navLink" href="<s:url action="Welcome"/>">Login</a>&nbsp;&nbsp;<span>&#8226;</span>&nbsp;&nbsp;
	<a href="<%=request.getContextPath()%>/PublisherRegistrationInput.do" />
			<s:text name="signup"/>
		</a>
	</p>
</div>
<div id="forget">
<a class="navLink" href="<%=request.getContextPath()%>/ForgotPwd_input.do" />
			<s:text name="login.forgot"/>
		</a>

</div>

</div>
</div>
