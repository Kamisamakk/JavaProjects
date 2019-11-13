<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/12
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>用户添加</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/localization/messages_zh.js"></script>
    <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/js/user.js" ></script>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="${pageContext.request.contextPath}/userAddServlet?action=add"
              id="addForm" method="post">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">添加用户</th>
                </tr>
                <tr>
                    <td>
                        用户名<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="uid1" name="uid"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        姓名
                    </td>
                    <td>
                        <input type="text" id="uname" name="uname" />
                    </td>
                </tr>
                <tr>
                    <td>
                        手机号码
                    </td>
                    <td>
                        <input type="text" id="unumber" name="unumber" maxlength="11"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        QQ
                    </td>
                    <td>
                        <input type="text" id="qq" name="qq" maxlength="12"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        性别
                    </td>
                    <td>
                        <select id="usex" name="usex">
                            <option >
                                未选择
                            </option>
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
                        登陆密码<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="upassword" name="upassword" />
                    </td>
                </tr>
                <tr>
                    <td>
                        爱好<font color="red">*</font>
                    </td>
                    <td>
                        <input type="checkbox" id="hobby1" name="checkbox" value="蔡"/>&nbsp;蔡
                        <input type="checkbox" id="hobby2" name="checkbox" value="徐"/>&nbsp;徐
                        <input type="checkbox" id="hobby3" name="checkbox" value="坤"/>&nbsp;坤
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
                        <input type="hidden" id="hobby" name="hobby" />
                        <input type="hidden" id="validUserNameUrl" value="${pageContext.request.contextPath}/userAddServlet?action=validUserName"/>
                        <input type="hidden" id="operator" name="operator" value="${userId}"/>
                        <input type="submit"  value="确定"/>
<%--                        <input type="button" onclick="submitData()" value="确定"/>--%>

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
    function submitData(){
        if (validData()){
            check_all();
            document.getElementById("addForm").submit();
        }
    }

    function validData(){
        var uid = document.getElementById("uid");
        var upassword = document.getElementById("upassword");
        if(uid.value==''){
            alert("请输入用户名!")
            return false;
        }
        if(upassword.value==''){
            alert("请输入密码!")
            return false;
        }
        return true;
    }
</script>
</body>
</html>
