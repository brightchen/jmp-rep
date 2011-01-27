<%@ page language="java" %>
<%@ page contentType="text/vnd.wap.wml" %>
<%
	long expSeconds = 5;  // 5 seconds to avoid back button refresh
	long expires = new java.util.Date().getTime() + 1000*expSeconds;
	response.addDateHeader("Expires", expires);
	response.addHeader("Cache-Control", "max-age=" + expSeconds);
	long cdate = System.currentTimeMillis();
	response.setDateHeader("Date", cdate);
	response.setDateHeader("Last-Modified", cdate);
%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml"> 
