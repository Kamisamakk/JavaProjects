<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.jbit.news.dao.TopicsDao" %>
<%@ page import="org.jbit.news.dao.impl.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>主题修改</title>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");
    String tid = request.getParameter("tid");
    String tname = request.getParameter("tname");
    System.out.println(tname);
    String result = null;
    if (tid != null && !"".equals(tid.trim())
            && tname != null && !"".equals(tname.trim())) {
        TopicsDao topicsDao = new TopicsDaoImpl();
        Map<String, String> topicMap = new HashMap<String, String>();
        topicMap.put("tname", tname);
        topicMap.put("tid", tid);
        topicsDao.updateTopic(topicMap);
        result = "<script>alert('修改成功!');location.href='topic_list.jsp'</script>";
    } else {
        result = "<script>alert('请上传参数!');history.back();</script>";
    }
    out.print(result);
%>
</body>
</html>