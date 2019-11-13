<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/16
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<style type="text/css">
    .info td{
        width: 100px;
        text-align: center;
    }
</style>
<html>
<head>
    <title>商品添加</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/localization/messages_zh.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/product.js"></script>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
<%@include file="console_element/left.jsp" %>
    <div id="opt_menu" class="info">
    <form action="${pageContext.request.contextPath}/productServlet?action=add"
          id="addForm" method="post" enctype="multipart/form-data">
        <table border="1" style="width:600px;">
            <tr>
                <th colspan="2">添加商品</th>
            </tr>
            <tr>
                <td>
                    商品名称<font color="red">*</font>
                </td>
                <td>
                    <input type="text" id="name1" name="name" maxlength="64"/>
                </td>
            </tr>
            <tr>
                <td>
                    商品类别<font color="red">*</font>
                </td>
                <td>
                    <select id="type_id1" name="type_id" style="width: 170px ">
                        <option value="">请选择</option>
                        <c:forEach var="i" items="${productTypeList}">
                            <option value="${i.productTypeId}">${i.productType}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    商品品牌<font color="red">*</font>
                </td>
                <td>
                    <select id="brand_id1" name="brand_id" style="width: 170px ">
                        <option value="">请选择</option>
                        <c:forEach var="i" items="${productBrandList}">
                            <option value="${i.productBrandId}">${i.productBrand}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    商品价格<font color="red">*</font>
                </td>
                <td>
                    <input type="text" id="price1" name="price" maxlength="16"/>
                </td>
            </tr>
            <tr>
                <td>
                    排序码<font color="red1">*</font>
                </td>
                <td>
                    <input type="text" id="sort_id1" name="sort_id" maxlength="16"/>
                </td>
            </tr>
            <tr>
                <td>
                    商品描述
                </td>
                <td>
                    <input type="text" id="summary1" name="summary" maxlength="64"/>
                </td>
            </tr>
            <tr>
                <td>
                    图片
                </td>
                <td>
                <c:forEach var="i" begin="0" step="1" end="5">
                    <input class="form-control" type="file" id="picpath${i}" name="picpath${i}"/>
                    picpath${i}
                </c:forEach>
                </td>
            </tr>
            <tr>
                <td>
                    操作人
                </td>
                <td>
                    ${userId}
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="hidden" id="validProductName" value="${pageContext.request.contextPath}/productServlet?action=validProductName"/>
                    <input type="hidden" id="operator" name="operator" value="${userId}"/>
                    <input type="submit" value="确定"/>
                </td>
            </tr>
        </table>
    </form>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
</body>
</html>
