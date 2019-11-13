<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/18
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品类别管理</title>
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
                    商品类别查询
                </th>
            </tr>
            <tr>
                <td>
                    商品类别
                </td>
                <td>
                    <input type="input" id="type" name="type"
                    /><input type="button"  onclick="search()" value="查询"/>
                </td>
            </tr>
            <tr>
                <th colspan="4">
                    <input type="button" style="width: 50px" value="添加" onclick="ToAdd()"/>
                </th>
            </tr>
        </table>

        <table border="1" style="width:600px;">
            <tr>
                <th colspan="6">
                    商品类别列表
                </th>
            </tr>
            <tr align="center">
                <td>
                    商品类别名称
                </td>
                <td>
                    上级商品类别编号
                </td>
                <td>
                    排序码
                </td>
                <td>
                    操作员
                </td>
                <td>
                    操作时间
                </td>
                <td>
                    操作
                </td>
            </tr>
            <c:forEach var="typeBean" items="${productTypeList}">
                <tr align="center">
                    <td>${typeBean.productType}</td>
                    <td>${typeBean.productTypeParentId}</td>
                    <td>${typeBean.productTypeSortId}</td>
                    <td>${typeBean.productTypeOperator}</td>
                    <td>${typeBean.productTypeOprTime}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/productTypeServlet?action=info&nid=${typeBean.productTypeId}">详情</a>
                        <a href="${pageContext.request.contextPath}/productTypeServlet?action=updatejsp&nid=${typeBean.productTypeId}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:productBrandDel(${brandBean.productBrandId})">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
            location.href = '${pageContext.request.contextPath}/productTypeServlet?action=list&currentPage=0';
        }
    }

    //上一页
    function pagePre() {
        if (currentPage == '0') {
            alert("已经是第一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/productTypeServlet?action=list&currentPage=' + (parseInt(currentPage) - 1);
        }
    }

    //下一页
    function pageNext() {
        if (parseInt(currentPage) + 1 >= pageCount) {
            alert("已经是最后一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/productTypeServlet?action=list&currentPage=' + (parseInt(currentPage) + 1);
        }
    }

    //尾页
    function pageEnd() {
        if (parseInt(currentPage) + 1 >= pageCount) {
            alert("已经是最后一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/productTypeServlet?action=list&currentPage=' + (parseInt(pageCount) - 1);
        }
    }
    //查询
    function search() {
        var type = document.getElementById("type");
        var url = '${pageContext.request.contextPath}/productTypeServlet?action=list&type=' + type.value;
        location.href = url;
    }
    function productBrandDel(nid) {
        var xmlHttp=new XMLHttpRequest();
        var DelUrl="${pageContext.request.contextPath}/productTypeServlet"
        var param="action=delete"
        param+="&nid="+nid
        xmlHttp.open("POST",DelUrl)
        xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded")
        xmlHttp.send(param)
        xmlHttp.onreadystatechange=function(){
            if (xmlHttp.readyState == 4 && xmlHttp.status==200){
                if(xmlHttp.responseText=='1') {
                    if (confirm('确定要删除此类别吗?')) {
                        location.href = '${pageContext.request.contextPath}/productTypeServlet?action=list';
                    }
                }
                else {
                    alert("删除失败")
                }
            }
        }
    }
    function ToAdd() {
        location.href='${pageContext.request.contextPath}/productTypeServlet?action=addjsp'
    }
</script>
</body>
</html>
