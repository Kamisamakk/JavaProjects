<%@ page import="org.jbit.news.entity.AdminUserList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    int onlineUserCount= AdminUserList.GetUser();
%>
<div id="header">
    <div id="welcome">欢迎使用新闻管理系统！</div>
    <div id="nav">
        <div id="logo"><img src="<%=request.getContextPath() %>/images/logo.jpg" alt="新闻中国"/></div>
        <div id="a_b01"><img src="<%=request.getContextPath() %>/images/a_b01.gif" alt=""/></div>
    </div>
</div>
<div id="admin_bar">
    <div id="status">欢迎您,管理员${userId}! 当前在线用户人数：<%=onlineUserCount %> &#160;&#160;&#160;&#160;
        <a href="${pageContext.request.contextPath}/userListServlet?action=logout">退出</a></div>
    <div id="channel"></div>

</div>
