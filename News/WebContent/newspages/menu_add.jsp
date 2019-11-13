<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/8
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String operator = session.getAttribute("userId").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>菜单添加</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="${pageContext.request.contextPath}/menuAddServlet?action=add"
              id="addForm" method="post">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">添加菜单</th>
                </tr>
                <tr>
                    <td>
                        菜单名称<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="ntitle" name="ntitle" maxlength="32"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        菜单上级编号<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="menuParentName" name="menuParentName" maxlength="10"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        菜单描述<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="nsummary" name="nsummary" maxlength="32"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        排序码
                    </td>
                    <td>
                        <input type="text" id="sortNo" name="sortNo" maxlength="4"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        菜单选择
                    </td>
                    <td>
                        <select id="isParent" name="isParent">
                            <option >
                                未选择
                            </option>
                            <option  value="1">
                                父菜单
                            </option>
                            <option value="0">
                                子菜单
                            </option>
                        </select>

                    </td>
                </tr>
                <tr>
                    <td>
                        操作者<font color="red">*</font>
                    </td>
                    <td>
                        <%=operator%>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" onclick="submitData()" value="确定"/>
                        <input type="hidden" id="operator" name="operator" value="<%=operator%>"/>
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
        if (validData()) {
            document.getElementById("addForm").submit();
        }
    }

    function validData() {
        var ntitle = document.getElementById("ntitle");
        if (ntitle.value == '') {
            alert("请输入标题!")
            return false;
        }
        return true;
    }
</script>
</body>
</html>
