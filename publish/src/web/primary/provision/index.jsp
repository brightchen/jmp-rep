<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<% if (request.getHeader("user-agent").startsWith("Mozilla")){
	 
%>
<%@ include file="headers.html.inc"%>
<html>
<head>
    <title>iseemedia J2ME clients</title>
</head>

<body> 
    <p align="left">
    	<br />Welcome to iseemedia client download.<br />
        <br /><a href="doc.do">iseemobility</a>
        <br /><a href="/iseedocs/ota-mail/prv/">iseedocs</a>
        <br /><a href="/iseemobility/iseedocs.cab">Windows Mobile 5</a>
    </p>
</body>
</html>

<% }else{ %>

<%@ include file="headers.wml.inc"%> 
<wml>
  <card id="application" title="iseemedia"> 
    <p align="left">
    	<br />Welcome to iseemedia client download.<br />
        <br /><a href="doc.do">iseemobility</a>
        <br /><a href="/iseedocs/ota-mail/prv/">iseedocs</a>
    </p>
  </card>
</wml>
<% } %>