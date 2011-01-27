<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%@ include file="headers.wml.inc"%> 
<wml>
  <card id="register" title="iseedocs">
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
	<br/>Name:<br/>
	<input name="name" size="16"/>
	<br/>Address:<br/>
	<input name="address" size="16"/>
	<br/>Phone number:<br/>
	<input name="phone" size="16"/>
	<br/>Do you want to receive notification messages on upcoming published documents?
	<select name="notification" value="false">
		<option value="true">Yes</option>
		<option value="false">No</option>
	</select>	
	<br/>
	<a href="<c:url value='doc.do'/>?name=$(name:e)&amp;address=$(address:e)&amp;phone=$(phone:e)&amp;notification=$(notification:e)">Continue</a>
	<br/><br/>
      </p>
  </card>
</wml>
