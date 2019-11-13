<%@ page import="org.jbit.news.dao.AdvertisementDao" %>
<%@ page import="org.jbit.news.dao.impl.AdvertisementDaoImpl" %>
<%@ page import="org.jbit.news.entity.Advertisement" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/8
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String id = request.getParameter("adId");
    AdvertisementDao advertisementDao = new AdvertisementDaoImpl();
    Map<String, String> searchMap = new HashMap<>();
    searchMap.put("adId", id);
    Advertisement advertisement = advertisementDao.getAdvertisements(searchMap);

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>广告详情</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main" align="center">
    <%-- <%@include file="console_element/left.jsp" %>--%>
    <table border="1" style="width:600px;">
        <tr>
            <th colspan="2">广告详情</th>
        </tr>
        <tr>
            <td>
                标题<font color="red">*</font>
            </td>
            <td>
                <%=advertisement.getNtitle()%>
            </td>
        </tr>
        <tr>
            <td>
                描述
            </td>
            <td>
                <%=advertisement.getNsummary()%>
            </td>
        </tr>
        <tr>
            <td>
                图片
            </td>
            <td id="picpathtd">
                <%
                    if (advertisement.getNpicpath() != null && !"".equals(advertisement.getNpicpath())) {
                %>
                <img src="<%=request.getContextPath()+"/"+advertisement.getNpicpath() %>" width="200px" height="200px"/>
                <%
                } else {
                %>
                <input type="file" id="picpath" name="picpath"/>
                <%
                    }
                %>
            </td>
        </tr>
    </table>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>

</body>

</html>
