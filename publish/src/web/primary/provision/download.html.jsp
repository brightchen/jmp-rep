<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ include file="headers.html.inc"%>
<html>
<head>
    <title><s:property value="#request.client_name" /> client</title>
</head>

<body>

	<p align="left">
      Welcome to <s:property value="#request.client_name"/> client download, to download for 
	  <b><s:property value="#request.devicename"/></b>

	<s:if test="#request.jadlink!=null">
	please click <a href="<s:property value='#request.jadlink' />">here</a>
	</s:if>

	<s:if test="#request.jadlinks!=null && #request.jadlink==null">
		please choose one of the links below:
		<s:iterator value="#request.jadlinks" status="stat">
		<br /><a href="<s:property value='#request.jadlinks[#stat.index][1]' />"><s:property value='#request.jadlinks[#stat.index][0]' /></a>
		</s:iterator>
	</s:if>

	 <s:if test="#request.browseLink!=null">
		<br/><br/>If your device is not <b><s:property value="#request.devicename"/></b>, please click <a href="<s:property value='#request.browseLink'/>">here</a> to select your device.
	</s:if>
    </p>

</body>
</html>