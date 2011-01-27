<%@ page language="java" %>
<%
	long expSeconds = 5;  // 5 seconds to avoid back button refresh
	long expires = new java.util.Date().getTime() + 1000*expSeconds;
	response.addDateHeader("Expires", expires);
	response.addHeader("Cache-Control", "max-age=" + expSeconds);
	long cdate = System.currentTimeMillis();
	response.setDateHeader("Date", cdate);
	response.setDateHeader("Last-Modified", cdate);
	
	// the contentType might be "application/xhtml+xml" or "text/html"
	response.setContentType("text/html");

%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">

