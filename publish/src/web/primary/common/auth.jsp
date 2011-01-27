<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<% java.util.Calendar cal = java.util.Calendar.getInstance(); %>

<div id="account">
<div id="heading">
	<h1>iseemedia</h1>
</div>
<div class="inner">
<h4>
<% out.print(cal.getTime().toString()); %>
</h4>

<div id="login">
<p>
   <a href="<%=request.getContextPath()%>/Welcome.do"><s:text name="main"/></a>
</p>
</div>

<div id="first">
 <p>
   <a href="<%=request.getContextPath()%>/ListDocuments.do"><s:text name="list.all.doc"/></a>
 </p>	
</div>

<div id="document">
<p>
Welcome <s:property value="#session.publisheruser.username" />
&nbsp;&nbsp;<span>&#8226;</span>&nbsp;&nbsp;  <a href="<%=request.getContextPath()%>/Logout.do" />
<s:text name="logout" />
</a></p>		
</div>

</div>
</div>