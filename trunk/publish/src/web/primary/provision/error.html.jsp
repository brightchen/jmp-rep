<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ include file="headers.html.inc"%>
<html>
<head>
    <title><c:out value='${client_name}'/> client</title>
</head>

<body>
    <p align="left">
      An error has occurred processing your request.
      <c:if test="${!empty errormsg}">
        <br /><c:out value='${errormsg}'/>
      </c:if>
    </p>
</body>
</html>