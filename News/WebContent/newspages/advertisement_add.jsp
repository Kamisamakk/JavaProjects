<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/2
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%
   // String operator = session.getAttribute("userId").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>添加广告</title>
    <link href="../css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="${pageContext.request.contextPath}/newspages/advertisement_add_execute.jsp"
              id="addForm" method="post" enctype="multipart/form-data">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">添加广告</th>
                </tr>
                <tr>
                    <td>
                        标题<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="title" name="title" maxlength="64"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        状态<font color="red">*</font>
                    </td>
                    <td>
                        <select id="states" name="states">
                            <option value="未发布">未发布</option>
                            <option value="发布">发布</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        开始时间<font color="red">*</font>
                    </td>
                    <td>
                        <input type="datetime-local" id="beginTime" name="beginTime" maxlength="16"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        截至时间<font color="red">*</font>
                    </td>
                    <td>
                        <input type="datetime-local" id="endTime" name="endTime" maxlength="16"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        描述
                    </td>
                    <td>
                        <input type="text" id="summary" name="summary" maxlength="64"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        图片
                    </td>
                    <td>
                        <input type="file" id="picpath" name="picpath"
                        />
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
                        <input type="button" onclick="submitData()" value="确定"/>
                        <input type="hidden" id="check_states" name="check_states" value="未审核"/>
                        <input type="hidden" id="operator" name="operator" value="${userId}"/>
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
        var title = document.getElementById("title");
        if (title.value == '') {
            alert("请输入标题!")
            return false;
        }
        if (states.value == '') {
            alert("请选择状态!")
            return false;
        }
        return true;
        return true;
    }
</script>
</body>
</html>

