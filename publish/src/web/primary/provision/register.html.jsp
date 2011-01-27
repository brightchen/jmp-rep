<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>
<%@ include file="headers.html.inc"%>
<html>
<head>
    <title>iseedocs client</title>
</head>

<body>
    <c:choose>
    	<c:when test="{empty failed}">
	    <p align="left">
	      Welcome to iseedocs registration.
	    </p>
	    
	    <p>You first need to give us your details:</p>
    </c:when>
    <c:otherwise>
	    <p align="left">
	      <c:out value="${failed}"/>
	      <br/>Please try again.
	    </p>
    </c:otherwise>
    </c:choose>
    <p>
    <form action="doc.do" method="POST">
	<br/>Name:<br/>
	<input type="text" name="name" size="16"/>
	<br/>Address:<br/>
	<input type="text" name="address" size="16"/>
	<br/>Phone number [at least 8 digits]:<br/>
	<input type="text" name="phone" size="16"/>
	<br/>Do you want to receive notification messages on upcoming published documents?
	<select name="notification">
		<option value="true">Yes</option>
		<option value="false">No</option>
	</select>	
	<br/>
	<input type="submit" value="Continue">
     </form>
      </p>
</body>
</html>