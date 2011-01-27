<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<head>
    <title>Error</title>
</head>

<body>

<br />

<h3>Got error : </h3>

<strong>
	<font color="#FF0000">
		<s:actionerror />
	</font>
</strong>

<p>To continue - 
<a href="Welcome.do">Click here to go back to the welcome page</a>
</p>
<p>or click the "back" button of your browser to bring you back to the previous page.
</p>

<hr />
<p> If this is an unexpected error. Please check with administrator.</p>
Please <a href="http://support.iseemobility.net:8180/index.php">click here</a> if you want to contact our customer service 
department regarding this problem. 
<br /><br />

<hr />

<!-- don't show the technical details
<h3>Technical Details</h3>

<p>
    <s:property value="%{exceptionStack}"/>
</p>
-->

</body>
