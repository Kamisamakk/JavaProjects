<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/5
  Time: 14:13
  To change this template use File | Settings | File Templates.
--%>
<%@page import="org.jbit.news.entity.*" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@page import="java.util.*" %>
<%@ page import="org.jbit.news.dao.AdvertisementDao" %>
<%@ page import="org.jbit.news.dao.impl.AdvertisementDaoImpl" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String nid = request.getParameter("nid");
    AdvertisementDao advertisementDao = new AdvertisementDaoImpl();
    Map<String, String> searchMap = new HashMap<String, String>();
    searchMap.put("nid", nid);
    Advertisement advertisement = advertisementDao.getAdvertisements(searchMap);


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>审核广告</title>
    <link href="../css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="check_execute.jsp"
              id="updateForm" method="post">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">审核广告</th>
                </tr>
                <tr>
                    <td>
                        标题<font color="red">*</font>
                    </td>
                    <td>
                            <%=advertisement.getNtitle()%>
                </tr>
                <tr>
                    <td>
                        状态<font color="red">*</font>
                    </td>
                    <td>
                        <select id="check_states" name="check_states">
                            <c:if test="<%=advertisement.getCheck_states()==0%>">
                            <option value="<%=advertisement.getCheck_states()%>">未审核
                            </option>
                            </c:if>
                            <option value="1">审核通过</option>
                            <option value="2">审核不通过</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        图片
                    </td>
                    <td>
                        <img src="<%=request.getContextPath()+"/"+advertisement.getNpicpath() %>" width="200px"
                             height="200px"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        描述
                    </td>
                    <td>
                            <%=advertisement.getNsummary()%>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" onclick="submitData()" value="确定"/>
                        <input type="button" onclick="submitBack()" value="取消"/>
                        <input type="hidden" id="nid" name="nid" value="<%=advertisement.getNid() %>"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
<script language="javascript">
    function submitData() {
        document.getElementById("updateForm").submit();
    }

    function submitBack() {
        history.back();
    }
</script>
</html>
