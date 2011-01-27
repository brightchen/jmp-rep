<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>document review</title>
	<style type="text/css">
	    body { margin:0; padding:0; }
	</style>
	<script language="javascript" type="text/javascript">

function popitup() {
	myRef = window.open('<%=request.getContextPath()%>/preview/applet.jsp','mywin',
'left=700,top=250,width=240,height=519,toolbar=0,resizable=0');
myRef.focus();
}


</script> 

</head>
<body onload="javascript:popitup()">

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
<s:form theme="simple" name="approveDocumentForm" action="UpdateDocument" method="post" validate="true">

	
    	<s:token />		
		<s:hidden name="id" />
		<s:hidden name="document.id" />
		<s:hidden name="document.documentname" />
		<s:submit action="ApproveDocument" key="button.approve" onclick="form.onsubmit=null" cssClass="fbtn"/>
		
</s:form>
	<center>
		<applet code="org.microemu.applet.Main"
			width="292" height="618"
			archive="<%=request.getContextPath()%>/scripts/microemu-javase-applet.jar,<%=request.getContextPath()%>/scripts/microemu-device-large.jar,<%=request.getContextPath()%>/scripts/iseepublish.jar">
			<param name="midlet" value="com.iseemobility.client.PublishController">
			<param name="device" value="org/microemu/device/large/device.xml">
			<param name="StartupDocument" value="<%=doc_rep_id%>">
			<param name="Catalogue-URL" value="<%=startupDocument%>">
	        <p>You need to have Java installed and enabled in your web browser to be able to view this demo. Download at <a href="http://java.sun.com/">http://java.sun.com/</a>.</p>
		</applet>
	</center>  
<% } else {%>
	<p>Document URL undefined! Missing identificator.</p>
<% }%>



</body>
</html>

