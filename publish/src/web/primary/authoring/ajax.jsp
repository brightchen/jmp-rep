<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>iseepublish</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="cache-control" content="no-cache">
  <link rel="stylesheet" type="text/css" href="css/iseepublish.css" />
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/welcome1.css" />
  <link rel="stylesheet" type="text/css" href="resources/css/ext-all.css" />
  <script type="text/javascript" src="src/ext/ext-base.js"></script>
  <script type="text/javascript" src="src/ext/ext-all.js"></script>
  <script type="text/javascript" src="src/dragresize.js"></script>
  <script type="text/javascript" src="src/http.js"></script>
  <script type="text/javascript" src="src/xml.js"></script>
  <script type="text/javascript" src="src/xml2json.js"></script>
  <script type="text/javascript" src="src/iseepublish.js"></script>
</head>
<body>

<s:include value="/common/auth.jsp" />

<!-- <div id="wrapper">
<div class="inner"> -->
<div style="position:absolute; left:0px; top:100px; width:100%; height:800px;">
<div id="iseepublish_container"></div>
</div>
<div id="grid"></div>

<!-- Drop-down menu to select the content type -->
<select name="contentType" id="contentType" style="display: none;">
  <option value="ad">ad</option>
  <option value="article">article</option>
</select>

<!-- <s:include value="/common/footer.jsp" /> -->

<!-- </div>
</div> -->

</body>
</html>
