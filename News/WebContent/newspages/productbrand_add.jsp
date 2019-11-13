<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/18
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>品牌添加</title>
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
        <form action="${pageContext.request.contextPath}/productBrandServlet?action=add"
              id="addForm" method="post">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">添加品牌</th>
                </tr>
                <tr>
                    <td>
                        品牌名称<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="brand1" name="brand" maxlength="64"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        上级品牌编号
                    </td>
                    <td>
                        <select id="brandparent_id" name="brandparent_id" style="width: 170px ">
                            <option value="">请选择</option>
                        <c:forEach var="i" items="${productBrandList}">
                            <option value="${i.productBrandId}">${i.productBrandId}</option>
                        </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        排序码<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="sort_id1" name="sort_id" maxlength="16"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        品牌描述
                    </td>
                    <td>
                        <input type="text" id="summary1" name="summary" maxlength="64"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        操作员
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
