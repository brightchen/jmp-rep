<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	
	<style type="text/css">
	    body { margin:0; padding:0; }
	</style>


</head>
<body>
<%
	String doc_rep_id = request.getParameter("documentRId");	// get id from request parameters
	if (doc_rep_id == null){
		Object id = request.getAttribute("documentRId"); // get id from request attributes
		if (id != null){
			doc_rep_id = (String)id;
		}else{
			id = session.getAttribute("documentRId"); // get id from session
			if (id != null)
				doc_rep_id = (String)id;
		}
    }
    if (doc_rep_id != null){
    	String server = request.getServerName();
	    String port = ":" + request.getServerPort();
	    if (port.equals(":80") || port.equals(":443"))
			port = "";
	    server = request.getScheme() + "://" + server + port + request.getContextPath();
    	String startupDocument = server + "/";
%>

	<!-- <p>Document URL: <%=startupDocument%></p>  -->
		<applet code="org.microemu.applet.Main" width="240" height="519" archive="<%=request.getContextPath()%>/scripts/applet-simulator.jar,<%=request.getContextPath()%>/scripts/iseepublish.jar">
			<param name="midlet" value="com.iseemobility.client.PublishController">
			<param name="device" value="cg.iseepublish.simulator.device.blueray.Device">
			<param name="StartupDocument" value="<%=doc_rep_id%>">
			<param name="Catalogue-URL" value="<%=startupDocument%>">
	        <p>You need to have Java installed and enabled in your web browser to be able to view this demo. Download at <a href="http://java.sun.com/">http://java.sun.com/</a>.</p>
		</applet>  
<% } else {%>
	<p>Document URL undefined! Missing identificator.</p>
<% }%>
</body>
</html>

