<%@page import="org.jbit.news.entity.Admin" %>
<%@page import="org.jbit.news.dao.impl.AdminDaoImpl" %>
<%@page import="org.jbit.news.dao.AdminDao" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String uname = request.getParameter("uname");
    String password = request.getParameter("upwd");
    AdminDao adminDao = new AdminDaoImpl();
    Admin admin = adminDao.findAdmin(uname, password);
    if (admin == null) {
%>
<script type="text/javascript">
    alert("用户名密码错误，请重新登录");
    open("../index.jsp", "_self");
</script>
<%
    } else {
        session.setAttribute("userId", uname);
        //session.setAttribute("admin", admin.getUid());
        //session.setAttribute("userId",admin.getUid());
        response.sendRedirect("../newspages/admin.jsp");
    }
%>
