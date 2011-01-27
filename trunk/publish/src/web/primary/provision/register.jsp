<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<c:choose>
    <c:when test="${user-agent.startsWith('Mozilla')}">
    	<%-- forward to a html page --%>
	<%@ include file="register.html.jsp.jsp"%> 
    </c:when>
    <c:otherwise>  
	<%@ include file="register.wml.jsp.jsp"%> 
  </c:otherwise>
</c:choose>
