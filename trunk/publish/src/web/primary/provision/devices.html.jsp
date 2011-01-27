<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
<head>
    <title><s:property value="#request.client_name" /> client</title>
</head>

<body>

	<p align="left">
	<s:if test="#request.message!=null">
		<s:property value="#request.message"/>
		<br/><br/>
	</s:if>


	  <b><s:property value="#request.devicename"/></b>

	<s:if test="#request.manufacturers!=null">
		Select Brand:
		<s:iterator value="#request.manufacturers" status="stat">
		<br /><a href="<s:property value='#request.manufacturers[#stat.index][1]' />"><s:property value='#request.manufacturers[#stat.index][0]' /></a>
		</s:iterator>
	</s:if>

	<s:if test="#request.models!=null">
		Select Model for <b>"<s:property value="#request.manufacturer"/>"</b>:
		<s:iterator value="#request.models" status="stat">
		<br /><a href="<s:property value='#request.models[#stat.index][1]' />"><s:property value='#request.models[#stat.index][0]' /></a>
		</s:iterator>
	</s:if>

	 
    </p>

</body>
</html>