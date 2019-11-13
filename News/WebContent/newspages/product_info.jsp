<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/16
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<style type="text/css">
    .info td{
        width: 200px;
        text-align: center;
    }
</style>
<html>
<head>
    <title>商品详情</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main" align="center" class="info">
    <%@include file="console_element/left.jsp" %>
    <table border="1" style="width:800px;" >
        <tr>
            <th colspan="2">商品详情</th>
        </tr>
        <tr>
            <td>
                商品名称
            </td>
            <td>
                ${product.getName()}
            </td>
        </tr>
        <tr>
            <td>
                商品类别
            </td>
            <td>
                ${product.getType()}
            </td>
        </tr>
        <tr>
            <td>
                商品品牌
            </td>
            <td>
                ${product.getBrand()}
            </td>
        </tr>
        <tr>
            <td>
                商品价格
            </td>
            <td>
                ${product.getPrice()}
            </td>
        </tr>
        <tr>
            <td>
                排序码
            </td>
            <td>
                ${product.getSort_id()}
            </td>
        </tr>
        <tr>
            <td>
                商品描述
            </td>
            <td>
                ${product.getSummary()}
            </td>
        </tr>
        <tr>
            <td>
                商品图片
            </td>
            <td>
                <c:forEach var="picpath" items="${product.getNpicpath()}">
                    <c:if test="${picpath==null}">

                    </c:if>
                    <c:if test="${picpath!=null}">
                        <img src="${picpath}" width="100px" height="100px"/>
                    </c:if>
                </c:forEach>
            </td>
        </tr>
    </table>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
</body>
</html>
