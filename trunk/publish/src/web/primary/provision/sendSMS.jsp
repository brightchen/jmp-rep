<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<title>Send SMS Notification</title>
<script language="JavaScript">
   function sendSMS(){
	document.forms[0].text.value = document.forms[0].action_f.value + "=" + document.forms[0].parameter_f.value;
	if (confirm("Are you sure you wish to Notify this phone? \n" + document.forms[0].to.value + "\n" + document.forms[0].text.value))
		return true;
	return false;
  }
	function window_onload() {
		document.forms[0].si_url.disabled=true
		document.forms[0].si_text.disabled=true
		document.forms[0].text.disabled=false
		
	}
</script>
</head>

<body LANGUAGE="javascript" onload="return window_onload()">

<p><b>Send SMS</b></p>
<form method="GET" action="/iseepublish/ota/sendsms">
  <table border="0" cellpadding="0" style="border-collapse: collapse" bordercolor="#111111" bgcolor="#C0C0C0" cellspacing="5">
	<tr>
      <td>Phone Number:</td>
      <td>
      <input type="text" name="to" size="20" value="14168239748"/></td>
    </tr>
	<tr>
      <td>Port: (optional)</td>
      <td>
      <input type="text" name="udh" size="20"></td>
    </tr>
    <tr>
      <td>SMS Message:</td>
      <td>
      <textarea name="text" rows="4" cols="40" >your message here:</textarea>
	  </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td align="center"><input type="reset" onClick="document.forms[0].si_url.disabled=true;document.forms[0].si_text.disabled=true;document.forms[0].text.disabled=false"> <input type="submit" value="Send Message" name="B1"></td>
    </tr>
  </table>
</form>

</body>

</html>
