<%@ page language="java" import="java.util.*,java.sql.*,org.jbit.news.entity.*" pageEncoding="utf-8" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="org.jbit.news.dao.TopicsDao" %>
<%@page import="org.jbit.news.dao.impl.TopicsDaoImpl" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <title>修改主题 </title>
    <link href="../css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.html" %>
    <div id="opt_menu">
        <form action="topic_update_execute.jsp" method="post">
            <table align="center" border="1">
                <tr align="center">
                    <td colspan="2" style="width:300px;">主题名称</td>
                    <td colspan="2" style="width:300px;">
                        <input type="text" name="tname" id="tname" value="<%=request.getParameter("tname") %>"/>
                        <input type="hidden" name="tid" id="tid" value="<%=request.getParameter("tid") %>"/>
                    </td>
                    <td colspan="2" style="width:300px;">
                        <input type="submit" name="确定"/>
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
    function delTopic(id) {
        if (confirm("您确定要删除此主题吗？")) {
            location.href = 'topic_del.jsp?tid=' + id;
        }
    }

</script>
</body>
</html>
