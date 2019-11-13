<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/4
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>菜单列表</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <table border="1" style="width:600px;">
            <tr>
                <th colspan="2">
                    菜单查询
                </th>
            </tr>
            <tr>
                <td>
                    菜单名称
                </td>
                <td>
                    <input type="input" id="menuName" name="menuName"
                    /><input type="button" onclick="search()" value="查询"/>
                </td>
            </tr>
        </table>
        <table border="1" style="width:600px;">
            <tr>
                <th colspan="6">
                    菜单列表
                </th>
            </tr>
            <tr align="center">
                <td>
                    菜单编号
                </td>
                <td>
                    菜单名称
                </td>
                <td>
                    上级菜单编号
                </td>
                <td>
                    操作人
                </td>
                <td>
                    操作时间
                </td>
                <td>
                    操作
                </td>
            </tr>
            <c:forEach var="menuBean" items="${menuList }">
                <tr align="center">
                    <td>${menuBean.menuId}</td>
                    <td>${menuBean.menuName}</td>
                    <td>${menuBean.menuParentName}</td>
                    <td>${menuBean.menuOperator}</td>
                    <td>${menuBean.menuOperateTime}</td>
                    <td>
                        <a href="javascript:toUpdate(${menuBean.menuId})">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:menuDel(${menuBean.menuId})">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
            </c:forEach>
            <tr align="right">
                <td colspan="6">
                    总共${totalCount }条记录&nbsp;&nbsp;总共${pageCount}页&nbsp;&nbsp;当前第${currentPage+1 }页&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:pageHome()">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pagePre()">上一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pageNext()">下一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pageEnd()">尾页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                </td>
            </tr>
            <c:if test="${totalCount== 0}">

                <tr>
                    <td colspan="6">暂无数据</td>
                </tr>
            </c:if>
        </table>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
<script language="javascript">
    var currentPage = '${currentPage}';
    var totalCount = '${totalCount}';
    var pageCount = '${pageCount}';

    //首页
    function pageHome() {
        if (currentPage == '0') {
            alert("已经是第一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/menuServlet?action=list&currentPage=0';
        }
    }

    //上一页
    function pagePre() {
        if (currentPage == '0') {
            alert("已经是第一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/menuServlet?action=list&currentPage=' + (parseInt(currentPage) - 1);
        }
    }

    //下一页
    function pageNext() {
        if (parseInt(currentPage) + 1 >= pageCount) {
            alert("已经是最后一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/menuServlet?action=list&currentPage=' + (parseInt(currentPage) + 1);
        }
    }

    //尾页
    function pageEnd() {
        if (parseInt(currentPage) + 1 >= pageCount) {
            alert("已经是最后一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/menuServlet?action=list&currentPage=' + (parseInt(pageCount) - 1);
        }
    }

    //查询
    function search() {
        var menuName = document.getElementById("menuName");
        var url = '${pageContext.request.contextPath}/menuServlet?action=list&menuName=' + menuName.value;
        location.href = url;
    }

    function toUpdate(nid) {

        location.href = '${pageContext.request.contextPath}/menuUpdateServlet?action=updateList&menuId=' + nid;
    }

    //删除
    function menuDel(nid) {
        if (confirm('确定要删除此新闻吗?')) {
            location.href = '${pageContext.request.contextPath}/menuServlet?action=delete&nid=' + nid;
        }
    }
</script>
</body>
</html>
