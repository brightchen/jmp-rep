<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<%@taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% java.util.Calendar cal = java.util.Calendar.getInstance(); %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

  <head>
  	<title><decorator:title default="iseepublish" /></title>
  	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css" />
  	<meta http-equiv="content-type" content="text/xhtml; charset=UTF-8" />
  	<meta http-equiv="imagetoolbar" content="no" />
  	<meta http-equiv="pragma" content="no-cache" />
  	<meta http-equiv="cache-control" content="no-cache" />
  	<meta http-equiv="expires" content="0" />
  	<decorator:head />
  </head>
	
  <body onload="prepare()">
    <div id="maincontainer">
      <div id="topsection"><div class="innertube">
        <h1><img src="<%=request.getContextPath()%>/images/logo-etisalat.gif" alt="main page" /></h1>
      </div>
    
      <!-- header menu bar-->
      <div id="nav">
      	<p><% out.print(cal.getTime().toString()); %></p>
      </div>
      <!-- end of header menu -->
    </div>

    <!-- start of main column-->
    <div id="contentwrapper">
      <div id="contentcolumn">
        <div class="innertube">
          <decorator:body />
        </div>
      </div>
    </div>
    
    <!-- end of main column-->
    
    <div id="leftcolumn">
      <div class="innertube">
      </div>
    </div>
    
    <div id="footer"><a href="<%=request.getContextPath()%>/common/footer.jsp">Copyright</div>
    </div>
  </body>
</html>