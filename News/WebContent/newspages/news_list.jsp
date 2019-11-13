<%@page import="org.jbit.news.dao.impl.NewsDaoImpl" %>
<%@page import="org.jbit.news.dao.NewsDao" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String currentPage = request.getParameter("currentPage");
    String pageSize = request.getParameter("pageSize");
    String title = request.getParameter("title");
    if (currentPage == null || "".equals(currentPage.trim())) {
        currentPage = "0";
    }
    if (pageSize == null || "".equals(pageSize.trim())) {
        pageSize = "5";
    }
    NewsDao newsDao = new NewsDaoImpl();
    Map<String, Object> searchMap = new HashMap<String, Object>();
    searchMap.put("currentPage", currentPage);
    searchMap.put("pageSize", pageSize);
    searchMap.put("title", title);
    //System.out.println("title="+title);
    int totalCount = newsDao.findNewsCount(searchMap);//总记录数
    int pageCount = totalCount / Integer.parseInt(pageSize);
    if (totalCount % Integer.parseInt(pageSize) != 0) {
        pageCount += 1;
    }
    List<Map<String, Object>> newsList = newsDao.findNewsList(searchMap);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>新闻管理</title>
    <link href="../css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <table border="1" style="width:600px;">
            <tr>
                <th colspan="2">
                    新闻查询
                </th>
            </tr>
            <tr>
                <td>
                    标题
                </td>
                <td>
                    <input type="input" id="title" name="title"/><input type="button" onclick="search()" value="查询"/>
                </td>
            </tr>
        </table>
        <table border="1" style="width:600px;">
            <tr>
                <th colspan="5">
                    新闻列表
                </th>
            </tr>
            <tr align="center">
                <td>
                    标题
                </td>
                <td>
                    作者
                </td>
                <td>
                    所属主题
                </td>
                <td>
                    操作时间
                </td>
                <td>
                    操作
                </td>
            </tr>
            <%
                if (newsList != null && newsList.size() > 0) {
                    for (int i = 0; i < newsList.size(); i++) {
                        Map<String, Object> map = newsList.get(i);
            %>
            <tr align="center">
                <td><%=map.get("title") %>
                </td>
                <td><%=map.get("author") %>
                </td>
                <td><%=map.get("tname") %>
                </td>
                <td><%=map.get("oprDate") %>
                </td>
                <td>
                    <a>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a>删除</a>
                </td>
            </tr>
            <%
                }
            %>
            <tr align="right">
                <td colspan="5">
                    总共<%=totalCount %>条记录&nbsp;&nbsp;总共<%=pageCount %>
                    页&nbsp;&nbsp;当前第<%=Integer.parseInt(currentPage) + 1 %>页&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:pageHome()">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pagePre()">上一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pageNext()">下一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href="javascript:pageEnd()">尾页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                </td>
            </tr>
            <%
                } else {

                }
            %>
        </table>
    </div>
</div>
<div id="footer">
    <%@include file="console_element/bottom.html" %>
</div>
<script language="javascript">
    var currentPage = '<%=currentPage %>';
    var totalCount = '<%=totalCount %>';
    var pageCount = '<%=pageCount %>';

    //首页
    function pageHome() {
        if (currentPage == '0') {
            alert("已经是第一页!")
        } else {
            location.href = 'news_list.jsp?currentPage=0';
        }
    }

    //上一页
    function pagePre() {
        if (currentPage == '0') {
            alert("已经是第一页!")
        } else {
            location.href = 'news_list.jsp?currentPage=' + (parseInt(currentPage) - 1);
        }
    }

    //下一页
    function pageNext() {
        if (parseInt(currentPage) + 1 == pageCount) {
            alert("已经是最后一页!")
        } else {
            location.href = 'news_list.jsp?currentPage=' + (parseInt(currentPage) + 1);
        }
    }

    //尾页
    function pageEnd() {
        if (parseInt(currentPage) + 1 == pageCount) {
            alert("已经是最后一页!")
        } else {
            location.href = 'news_list.jsp?currentPage=' + (parseInt(pageCount) - 1);
        }
    }

    function search() {
        var title = document.getElementById("title");
        var url = 'news_list.jsp?title=' + title.value;
        location.href = url;
    }
</script>
</body>
</html>