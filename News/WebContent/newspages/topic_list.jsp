<%@ page language="java" import="java.util.*,java.sql.*,org.jbit.news.entity.*" pageEncoding="utf-8" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="org.jbit.news.dao.TopicsDao" %>
<%@page import="org.jbit.news.dao.impl.TopicsDaoImpl" %>

<%
    String pageSize = request.getParameter("pageSize");
    String currentPage = request.getParameter("currentPage");
    if (pageSize == null || "".equals(pageSize)) {
        pageSize = "5";
    }
    if (currentPage == null || "".equals(currentPage)) {
        currentPage = "0";
    }
    TopicsDao topicsDao = new TopicsDaoImpl();
    int count = topicsDao.findTopicCount();
    int totalPage = count / Integer.parseInt(pageSize);//总页数
    if (count % Integer.parseInt(pageSize) != 0) {
        totalPage += 1;
    }
    System.out.println("count=" + count);
    Map<String, String> searchMap = new HashMap<String, String>();
    searchMap.put("pageSize", pageSize);
    searchMap.put("currentPage", currentPage);
    List<Topic> topicList = topicsDao.getTopicsPage(searchMap);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <title>主题 管理</title>
    <link href="<%=request.getContextPath() %>/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <table align="center" border="1">
            <tr align="center">
                <th colspan="4">主题列表</th>
            </tr>
            <tr align="center">
                <th colspan="2" style="width:300px;">主题名称</th>
                <th colspan="2" style="width:300px;">操作</th>
            </tr>
            <%
                if (topicList != null && topicList.size() > 0) {
                    for (int i = 0; i < topicList.size(); i++) {
                        Topic topic = topicList.get(i);
            %>
            <tr align="center">
                <td colspan="2" style="width:300px;"><%=topic.getTname() %>
                </td>
                <td colspan="2" style="width:300px;">
                    <a href="topic_update.jsp?tid=<%=topic.getTid() %>&tname=<%=topic.getTname() %>">修改</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="javascript:delTopic('<%=topic.getTid() %>')">删除</a>
                </td>
            </tr>
            <%

                }
            %>
            <tr align="right">
                <td colspan="4" style="width:600px;">
                    共<%=count %>条记录共<%=totalPage %>页 当前第<%=Integer.parseInt(currentPage) + 1 %>页 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href='javascript:pageHome();'>首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href='javascript:pagePre();'>上一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href='javascript:pageNext();'>下一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                    <a href='javascript:pageEnd();'>尾页</a>
                </td>
            </tr>
            <%
            } else {
            %>
            <tr>
                <td colspan="2">暂无数据</td>
            </tr>
            <%
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
    var totalPage = '<%=totalPage %>';

    //首页
    function pageHome() {
        if (currentPage == '0') {
            alert("已经是首页了");
        } else {
            location.href = 'topic_list.jsp?currentPage=0';
        }
    }

    //上一页
    function pagePre() {
        if (currentPage == '0') {
            alert("已经是第一页了");
        } else {
            location.href = 'topic_list.jsp?currentPage=' + (parseInt(currentPage) - 1);
        }
    }

    //下一页
    function pageNext() {
        if ((parseInt(currentPage) + 1) == totalPage) {
            alert("已经是最后一页了");
        } else {
            location.href = 'topic_list.jsp?currentPage=' + (parseInt(currentPage) + 1);
        }
    }

    //下一页
    function pageEnd() {
        if ((parseInt(currentPage) + 1) == totalPage) {
            alert("已经是最后一页了");
        } else {
            location.href = 'topic_list.jsp?currentPage=' + (parseInt(totalPage) - 1);
        }
    }
</script>
</body>
</html>
