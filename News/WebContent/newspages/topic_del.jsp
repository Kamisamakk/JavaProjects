<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.jbit.news.dao.TopicsDao" %>
<%@ page import="org.jbit.news.dao.impl.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主题删除</title>
</head>
<body>
<%
    String tid = request.getParameter("tid");
    String result = null;
    if (tid != null && !"".equals(tid.trim())) {
        TopicsDao topicsDao = new TopicsDaoImpl();
        topicsDao.deleteTopic(tid);
        result = "<script>alert('删除成功!');location.href='topic_list.jsp'</script>";
    } else {
        result = "<script>alert('请上传参数tid!');history.back();</script>";
    }
    out.print(result);
%>
</body>
</html>