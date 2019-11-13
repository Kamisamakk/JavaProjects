<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/5
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@page import="org.jbit.news.entity.*" %>
<%@ page import="org.jbit.news.dao.AdvertisementDao" %>
<%@ page import="org.jbit.news.dao.impl.AdvertisementDaoImpl" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>审核广告</title>
</head>
<body>
<%
    Advertisement advertisement = new Advertisement();
    advertisement.setCheck_states(Integer.valueOf(request.getParameter("check_states")));
    advertisement.setNid(Integer.parseInt(new String(request.getParameter("nid").getBytes("ISO8859-1"), "utf-8")));
    //advertisement.setNid(Integer.parseInt(nid));
    AdvertisementDao advertisementDao = new AdvertisementDaoImpl();
    int result = advertisementDao.updateStates(advertisement);
    if (result > 0) {
        out.println("<script>alert('修改成功');location.href='advertisement_list.jsp'</script>");
    } else {
        out.println("<script>alert('修改失败');history.back();");
    }
%>
</body>
</html>
