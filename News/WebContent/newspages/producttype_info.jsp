<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/18
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品类别详情</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main" align="center" class="info">
    <%@include file="console_element/left.jsp" %>
    <table border="1" style="width:500px;">
        <tr>
            <th colspan="2">商品类别详情</th>
        </tr>
        <tr>
            <td>
                商品类别编号
            </td>
            <td>
                ${productType.getNid()}
            </td>
        </tr>
        <tr>
            <td>
                商品类别名称
            </td>
            <td>
                ${productType.getType()}
            </td>
        </tr>
        <tr>
            <td>
                上级商品类别编号
            </td>
            <td>
                ${productType.getTypeparent_id()}
            </td>
        </tr>
        <tr>
            <td>
                排序码
            </td>
            <td>
                ${productType.getSort_id()}
            </td>
        </tr>
        <tr>
            <td>
                商品类别描述
            </td>
            <td>
                ${productType.getSummary()}
            </td>
        </tr>
    </table>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
</body>
</html>
