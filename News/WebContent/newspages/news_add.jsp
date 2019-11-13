<%@page import="org.jbit.news.dao.impl.TopicsDaoImpl" %>
<%@page import="org.jbit.news.dao.TopicsDao" %>
<%@page import="org.jbit.news.entity.*" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@page import="java.util.*" %>
<%
    TopicsDao topicDao = new TopicsDaoImpl();
    List<Topic> topicList = topicDao.getAllTopics();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>添加新闻</title>
    <link href="../css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="news_add_execute.jsp"
              id="addForm" method="post" enctype="multipart/form-data">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">添加新闻</th>
                </tr>
                <tr>
                    <td>
                        标题<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="title" name="title" maxlength="64"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        作者<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="author" name="author" maxlength="16"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        所属主题<font color="red">*</font>
                    </td>
                    <td>
                        <select id="tid" name="tid">
                            <option value="">请选择</option>
                            <%
                                if (topicList != null && topicList.size() > 0) {
                                    for (int i = 0; i < topicList.size(); i++) {
                                        Topic topic = topicList.get(i);
                            %>
                            <option value="<%=topic.getTid() %>"><%=topic.getTname() %>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        摘要
                    </td>
                    <td>
                        <input type="text" id="summary" name="summary" maxlength="64"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        图片
                    </td>
                    <td>
                        <input type="file" id="picpath" name="picpath"
                        />
                    </td>
                </tr>
                <tr>
                    <td>
                        内容<font color="red">*</font>
                    </td>
                    <td>
                        <textarea id="content" name="content" rows="6" cols="60"></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" onclick="submitData()" value="确定"/>
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
    function submitData() {
        if (validData()) {
            document.getElementById("addForm").submit();
        }
    }

    function validData() {
        var title = document.getElementById("title");
        if (title.value == '') {
            alert("请输入标题!")
            return false;
        }
        return true;
    }
</script>
</body>
</html>
