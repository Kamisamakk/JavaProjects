<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/3
  Time: 23:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.jbit.news.dao.*" %>
<%@ page import="org.jbit.news.dao.impl.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>广告删除</title>
</head>
<body>
<%
    String nid = request.getParameter("nid");
    if (nid != null && !"".equals(nid.trim())) {
        AdvertisementDao advertisementDao = new AdvertisementDaoImpl();
        advertisementDao.deleteAdvertisements(nid);
        out.print("<script>alert('删除成功');location.href='advertisement_list.jsp';</script>");
    } else {
        out.print("<script>alert('请上传参数nid');history.back();</script>");
    }
%>
</body>
</html>