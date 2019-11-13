<%@ page language="java" import="java.util.*,java.sql.*,org.jbit.news.entity.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>管理后台</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css?ver=1"/>
</head>
<body>
<jsp:include page="console_element/top.jsp" />
<div id="main">
  <%@include file="console_element/left.jsp" %>
  <div id="opt_area">
    <ul class="classlist">
	      <li>请选择对应的菜单进行操作</li>
    </ul>
  </div>
</div>
<div id="footer">
  <%@include file="console_element/bottom.html" %>
</div>
</body>
</html>
