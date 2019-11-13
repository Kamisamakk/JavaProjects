<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/9
  Time: 9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>菜单修改</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="${pageContext.request.contextPath}/menuUpdateServlet?action=update"
              id="updateForm" method="post">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">菜单修改</th>
                </tr>
                <tr>
                    <td>
                        菜单名称<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="ntitle" name="ntitle" maxlength="32" value="${menuUpdate.ntitle}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        菜单上级编号<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="menuParentName" name="menuParentName" maxlength="10" value="${menuUpdate.nparent_id}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        菜单描述<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="nsummary" name="nsummary" maxlength="32" value="${menuUpdate.nsummary}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        排序码
                    </td>
                    <td>
                        <input type="text" id="sortNo" name="sortNo" maxlength="4" value="${menuUpdate.nsort_id}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        地址<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="path" name="path" maxlength="64" value="${menuUpdate.njsp}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        操作者<font color="red">*</font>
                    </td>
                    <td>
                        ${userId}
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" onclick="submitData()" value="确定"/>
                        <input type="hidden" id="nid" name="nid" value="${nid}"/>
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
            document.getElementById("updateForm").submit();
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
