<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<% java.util.Calendar cal = java.util.Calendar.getInstance(); %>

<div id="account">
<div class="inner">
<p>
<% out.print(cal.getTime().toString()); %>
</p>

<div id="login">
<p>

Welcome <s:property value="#session.publisheruser.username" />
&nbsp;&nbsp;<span>&#8226;</span>&nbsp;&nbsp;  <a href="<s:url action="Logout" />">
<s:text name="logout" />
</a></p>	
</div>

</div>
</div>
