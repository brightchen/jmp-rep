<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<title>ERROR 403</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/error.css" />
	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="imagetoolbar" content="no" />
	<s:head />
</head>

<body>

<div id="wrapper">
<div class="inner">
<div id="heading">
	<h1>Error 403</h1>
</div>

<div id="error">
<div id="msg">

<img src="<%=request.getContextPath()%>/images/keychainicon.png" alt="Opps..." />
<p>Access is denied.  Please check with your Administrator.</p>
<p>If you want to get back to the login page, please click <a href="<%=request.getContextPath()%>/login.jsp">here</a>.</p>


</div>
</div>

</div>
</div>
</body>
</html>

