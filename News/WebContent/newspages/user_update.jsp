<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/15
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>用户修改</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <form action="${pageContext.request.contextPath}/userListServlet?action=update"
          id="updateForm" method="post">
        <table border="1" style="width:600px;">
            <tr>
                <th colspan="2">添加用户</th>
            </tr>
            <tr>
                <td>
                    用户名
                </td>
                <td>
                    <input type="text" id="uid" name="uid" value="${userUpdate.uId}"/>
                </td>
            </tr>
            <tr>
                <td>
                    姓名
                </td>
                <td>
                    <input type="text" id="uname" name="uname" value="${userUpdate.uName}"/>
                </td>
            </tr>
            <tr>
                <td>
                    手机号码
                </td>
                <td>
                    <input type="text" id="unumber" name="unumber" value="${userUpdate.uNumber}"/>
                </td>
            </tr>
            <tr>
                <td>
                    QQ
                </td>
                <td>
                    <input type="text" id="qq" name="qq" value="${userUpdate.QQ}"/>
                </td>
            </tr>
            <tr>
                <td>
                    性别
                </td>
                <td>
                    <select id="usex" name="usex">
                        <c:if test="${userUpdate.uSex==1}">
                            <option >
                                女
                            </option>
                        </c:if>
                        <c:if test="${userUpdate.uSex==0}">
                            <option >
                                男
                            </option>
                        </c:if>
                        <option  value="0">
                            男
                        </option>
                        <option value="1">
                            女
                        </option>
                    </select>

                </td>
            </tr>
            <tr>
                <td>
                    登陆密码
                </td>
                <td>
                    <input type="text" id="upassword" name="upassword" value="${userUpdate.uPassword}"/>
                </td>
            </tr>
            <tr>
                <td>
                    爱好<font color="red">*</font>
                </td>
                <td>
                    <input type="checkbox" id="hobby1" name="hobby" value="蔡"/>&nbsp;蔡
                    <input type="checkbox" id="hobby2" name="hobby" value="徐" checked="true"/>&nbsp;徐
                    <input type="checkbox" id="hobby3" name="hobby" value="坤"/>&nbsp;坤
                </td>
            </tr>
            <tr>
                <td>
                    操作员<font color="red">*</font>
                </td>
                <td>
                    ${userId}
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/>
                    <input type="hidden" id="validUserNameUrl" value="${pageContext.request.contextPath}/userListServlet?action=validUserName"/>
                    <input type="hidden" id="operator" name="operator" value="${userId}"/>
                    <input type="submit"  value="确定"/>
                    <%--                        <input type="button" onclick="submitData()" value="确定"/>--%>

                </td>
            </tr>
        </table>
    </form>
</div>
 <div id="footer">
     <%@include file="console_element/bottom.html" %>
</div>
</body>
</html>
