<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/10
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>用户列表</title>
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
                    用户查询
                </th>
            </tr>
            <tr>
                <td>
                    用户名
                </td>
                <td>
                    <input type="input" id="uid" name="uid"
                    /><input type="button" onclick="search()" value="查询"/>
                </td>
                <td>
                    姓名
                </td>
                <td>
                    <input type="input" id="uName" name="uName"
                    /><input type="button" onclick="" value="查询"/>
                </td>
            </tr>
        </table>
        <table border="1" style="width:600px;">
            <tr>
                <th colspan="7">
                    用户列表
                </th>
            </tr>
            <tr align="center">
                <td>
                    用户名
                </td>
                <td>
                    用户状态
                </td>
                <td>
                    手机号码
                </td>
                <td>
                    QQ
                </td>
                <td>
                    操作时间
                </td>
                <td>
                    操作
                </td>
            </tr>
            <c:forEach var="userBean" items="${userList }">
                <tr align="center">
                    <td>${userBean.uid}</td>
                    <c:if test="${userBean.ustates==1}">
                    <td>正常</td>
                    </c:if>
                    <c:if test="${userBean.ustates==0}">
                        <td>冻结</td>
                    </c:if>
                    <td>${userBean.unumber}</td>
                    <td>${userBean.qq}</td>
                    <td>${userBean.oprDate}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/userListServlet?action=info&userId=${userBean.uid}">详情</a>
                        <a href="${pageContext.request.contextPath}/userListServlet?action=updatejsp&userId=${userBean.uid}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="javascript:userDel(${userBean.uid})">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        <c:if test="${userBean.ustates==1}">
                            <a href="${pageContext.request.contextPath}/userListServlet?action=lock&userId=${userBean.uid}">冻结</a>
                        </c:if>
                        <c:if test="${userBean.ustates==0}">
                            <a href="${pageContext.request.contextPath}/userListServlet?action=unlock&userId=${userBean.uid}">解冻</a>
                        </c:if>

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
            location.href = '${pageContext.request.contextPath}/userListServlet?action=list&currentPage=0';
        }
    }

    //上一页
    function pagePre() {
        if (currentPage == '0') {
            alert("已经是第一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/userListServlet?action=list&currentPage=' + (parseInt(currentPage) - 1);
        }
    }

    //下一页
    function pageNext() {
        if (parseInt(currentPage) + 1 >= pageCount) {
            alert("已经是最后一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/userListServlet?action=list&currentPage=' + (parseInt(currentPage) + 1);
        }
    }

    //尾页
    function pageEnd() {
        if (parseInt(currentPage) + 1 >= pageCount) {
            alert("已经是最后一页!")
        } else {
            location.href = '${pageContext.request.contextPath}/userListServlet?action=list&currentPage=' + (parseInt(pageCount) - 1);
        }
    }

    //查询
    function search() {
        var uName = document.getElementById("uName");
        var url = '${pageContext.request.contextPath}/userListServlet?action=list&username=' + uName.value;
        location.href = url;
    }

    function toUpdate(nid) {

        location.href = '${pageContext.request.contextPath}/menuUpdateServlet?action=updateList&menuId=' + nid;
    }
    function userInfo(uid) {
        var xmlHttp=new XMLHttpRequest();
        var DelUrl="${pageContext.request.contextPath}/userListServlet"
        var param="action=info"
        param+="&uid="+uid
        xmlHttp.open("POST",DelUrl)
        xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded")
        xmlHttp.send(param)
        xmlHttp.onreadystatechange=function(){
            if (xmlHttp.readyState == 4 && xmlHttp.status==200){
                if(xmlHttp.responseText=='1') {
                        location.href = '${pageContext.request.contextPath}/newspages/user_info.jsp';
                }
            }
        }
    }
    //删除
    function userDel(uid) {
        var xmlHttp=new XMLHttpRequest();
        var DelUrl="${pageContext.request.contextPath}/userListServlet"
        var param="action=delete"
        param+="&uid="+uid
        xmlHttp.open("POST",DelUrl)
        xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded")
        xmlHttp.send(param)
        xmlHttp.onreadystatechange=function(){
            if (xmlHttp.readyState == 4 && xmlHttp.status==200){
                if(xmlHttp.responseText=='1') {
                    if (confirm('确定要删除此用户吗?')) {
                        location.href = '${pageContext.request.contextPath}/userListServlet?action=list';
                    }
                }
                else {
                   alert("删除失败")
                }
            }
        }

    }

</script>
</body>
</html>
