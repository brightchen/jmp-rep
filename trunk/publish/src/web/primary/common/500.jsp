<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head>
	<title>ERROR 500</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/error.css" />
	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="imagetoolbar" content="no" />
</head>

<body>

<div id="wrapper">
<div class="inner">
<div id="heading">
	<h1>Error 500</h1>
</div>

<div id="error">
<div id="msg">

<img src="<%=request.getContextPath()%>/images/stop.png" alt="Opps..." />

<p>Something went wrong with your request.  Please try again later.</p>
<br/>
<p>If you want to get back to the main menu, please click <a href="<%=request.getContextPath()%>/PublisherMainMenu.do">here</a>.</p>

</div>
</div>

</div>
</div>
</body>
</html>

