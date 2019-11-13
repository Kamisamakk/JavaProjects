<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/20
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>订单管理</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <table border="1" style="width:600px;">
            <tr>
                <th colspan="4">
                    订单查询
                </th>
            </tr>
            <tr>
                <td>
                    订单号
                </td>
                <td>
                    <input type="input" id="list_no" name="list_no"
                    /><input type="button" onclick="search1()" value="查询"/>
                </td>
                <td>
                   订单状态
                </td>
                <td>
                    <input type="input" id="list_states" name="list_states"
                    /><input type="button" onclick="search2()" value="查询"/>
                </td>
            </tr>
        </table>

        <table border="1" style="width:600px;">
            <tr>
                <th colspan="7">
                    订单列表
                </th>
            </tr>
            <tr align="center">
                <td>
                    订单号
                </td>
                <td>
                    下单人名称
                </td>
                <td>
                    联系电话
                </td>
                <td>
                    订单金额
                </td>
                <td>
                    订单状态
                </td>
                <td>
                    手机号码
                </td>
                <td>
                    操作时间
                </td>
            </tr>
            <c:forEach var="listBean" items="${shopList }">
                <tr align="center">
                    <td>${listBean.listNo}</td>
                    <td>${listBean.userName}</td>
                    <td>${listBean.userNumber}</td>
                    <td>${listBean.listMoney}</td>
                    <td>
                        <c:if test="${listBean.listStates==0}">
                            未审核
                        </c:if>
                        <c:if test="${listBean.listStates==1}">
                            审核通过
                        </c:if>
                        <c:if test="${listBean.listStates==2}">
                            审核未通过
                        </c:if>
                    </td>
                    <td>${listBean.operateTime}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/shoplistServlet?action=info&list_no=${listBean.listNo}">详情</a>
                        <c:if test="${listBean.listStates==0}">
                            <a href="${pageContext.request.contextPath}/shoplistServlet?action=checkjsp&list_no=${listBean.listNo}">审核</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                        <a href="javascript:productDel(${productBean.productId})">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
            </c:forEach>
            <tr align="right">
                <td colspan="7">
                    总共${totalCount }条记录&nbsp;&nbsp;总共${pageCount}页&nbsp;&nbsp;当前第${currentPage+1 }页&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:pageHome()">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pagePre()">上一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pageNext()">下一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pageEnd()">尾页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                </td>
            </tr>
            <c:if test="${totalCount== 0}">
                <tr>
                    <td colspan="7">暂无数据</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
<script type="text/javascript">
    //精确查询
    function search1() {
        var list_no = document.getElementById("list_no");
        var url = '${pageContext.request.contextPath}/shoplistServlet?action=list&list_no=' + list_no.value;
        location.href = url;
    }
</script>
</body>
</html>
