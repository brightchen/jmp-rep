<%@ taglib prefix="c" uri="/WEB-INF/c.tld" %>

<%@ include file="headers.wml.inc"%> 
<wml>
  <card id="application" title="<c:out value='${client_name}'/>"> 
    <p align="left">
      An error has occurred processing your request.
      <c:if test="${!empty errormsg}">
        <br /><c:out value='${errormsg}'/>
      </c:if>
    </p>
  </card>
</wml>
