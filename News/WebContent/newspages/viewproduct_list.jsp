<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/19
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css?version=2"/>
<div>
    <tr>
        <c:forEach var="product" items="${productList }">
            <td>
                <img src="${product.productPicPath0}" alt="" width="200px" height="200px"/>
            </td>
            <td>
                    ${product.productName}
            </td>
        </c:forEach>
    </tr>
</div>
<div>
    <tr align="right">
        <td colspan="6">
            总共${totalCount }条记录&nbsp;&nbsp;总共${pageCount}页&nbsp;&nbsp;当前第${currentPage+1 }页&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:pageHome()">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="javascript:pagePre()">上一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="javascript:pageNext()">下一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
            <a href="javascript:pageEnd()">尾页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
        </td>
    </tr>
</div>