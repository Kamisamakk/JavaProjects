<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/15
  Time: 12:47
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
    <title>用户详情</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main" align="center" >
    <%@include file="console_element/left.jsp" %>
    <table border="1" style="width:200px;" >
        <tr>
            <th colspan="2">用户详情</th>
        </tr>
        <tr>
            <td>
                用户名
            </td>
            <td>
                ${userinfo.uId}
            </td>
        </tr>
        <tr>
            <td>
                姓名
            </td>
            <td>
                ${userinfo.uName}
            </td>
        </tr>
        <tr>
            <td>
                手机号码
            </td>
            <td>
                ${userinfo.uNumber}
            </td>
        </tr>
        <tr>
            <td>
                QQ
            </td>
            <td>
                ${userinfo.QQ}
            </td>
        </tr>
        <tr>
            <td>
                性别
            </td>
                <c:if test="${userinfo.uSex==0}">
                    <td>男</td>
                </c:if>
                <c:if test="${userinfo.uSex==1}">
                    <td>女</td>
                </c:if>
        </tr>
        <tr>
            <td>
                登陆密码
            </td>
            <td>
                ${userinfo.uPassword}
            </td>
        </tr>
        <tr>
            <td>
                爱好
            </td>
            <td>
                ${userinfo.hobby}
            </td>
        </tr>
    </table>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
</body>
</html>
