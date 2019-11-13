<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/18
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品类别修改</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="${pageContext.request.contextPath}/productTypeServlet?action=update"
              id="updateForm" method="post">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">商品类别修改</th>
                </tr>
                <tr>
                    <td>
                        商品类别名称<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="type" name="type" maxlength="18" value="${productType.getType()}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        上级商品类别编号
                    </td>
                    <td>
                        <select id="typeparent_id" name="typeparent_id" style="width: 170px ">
                            <option value="">请选择</option>
                            <c:forEach var="i" items="${productTypeList}">
                                <option value="${i.productTypeId}">${i.productTypeId}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        排序码<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="sort_id" name="sort_id" maxlength="4" value="${productType.getSort_id()}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品类别描述
                    </td>
                    <td>
                        <input type="text" id="summary" name="summary" maxlength="32" value="${productType.getSummary()}"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" onclick="submitData()" value="确定"/>
                        <input type="hidden" id="nid" name="nid" value="${productType.getNid()}"/>
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
        var type = document.getElementById("type");
        if (brand.value == '') {
            alert("请输入类别名称!")
            return false;
        }
        return true;
    }
</script>
</body>
</html>
