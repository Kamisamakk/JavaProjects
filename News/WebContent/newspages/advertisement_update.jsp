<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/2
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@page import="org.jbit.news.entity.*" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@page import="java.util.*" %>
<%@ page import="org.jbit.news.dao.AdvertisementDao" %>
<%@ page import="org.jbit.news.dao.impl.AdvertisementDaoImpl" %>
<%
    String nid = request.getParameter("nid");
    AdvertisementDao advertisementDao = new AdvertisementDaoImpl();
    Map<String, String> searchMap = new HashMap<String, String>();
    searchMap.put("nid", nid);
    Advertisement advertisement = advertisementDao.getAdvertisements(searchMap);
    String beginTime = advertisement.getNbegindate();
    String endTime = advertisement.getNenddate();
    String[] beginTimetime = beginTime.split(" ");
    String[] endTimetime = endTime.split(" ");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>修改广告</title>
    <link href="../css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="advertisement_update_execute.jsp"
              id="addForm" method="post" enctype="multipart/form-data">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">修改广告</th>
                </tr>
                <tr>
                    <td>
                        标题<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="title" name="title" maxlength="64"
                               value="<%=advertisement.getNtitle()%>"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        状态<font color="red">*</font>
                    </td>
                    <td>
                        <select id="states" name="states">
                            <option value="<%=advertisement.getNstates()%>"><%=advertisement.getNstates()%>
                            </option>

                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        开始时间<font color="red">*</font>
                    </td>
                    <td>
                        <input type="datetime-local" id="beginTime" name="beginTime" maxlength="16"
                               value="<%=beginTimetime[0]+"T"+beginTimetime[1]%>"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        截至时间<font color="red">*</font>
                    </td>
                    <td>
                        <input type="datetime-local" id="endTime" name="endTime" maxlength="16"
                               value="<%=endTimetime[0]+"T"+endTimetime[1]%>"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        描述
                    </td>
                    <td>
                        <input type="text" id="summary" name="summary" maxlength="64"
                               value="<%=advertisement.getNsummary()%>"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        图片
                    </td>
                    <td id="picpathtd">
                        <%
                            if (advertisement.getNpicpath() != null && !"".equals(advertisement.getNpicpath())) {
                        %>
                        <img src="<%=request.getContextPath()+"/"+advertisement.getNpicpath() %>" width="200px"
                             height="200px"/>
                        <input type="button" value="删除" onclick="showPicInput()"/>
                        <%
                        } else {
                        %>
                        <input type="file" id="picpath" name="picpath"/>
                        <%
                            }
                        %>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" onclick="submitData()" value="确定"/>
                        <input type="button" onclick="submitBack()" value="取消"/>
                        <input type="hidden" id="nid" name="nid" value="<%=advertisement.getNid() %>"/>
                        <input type="hidden" id="oldpicpath" name="oldpicpath"
                               value="<%=advertisement.getNpicpath() %>"/>
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
    //显示图片输入框
    function showPicInput() {
        var picpathtd = document.getElementById("picpathtd");
        var oldpicpath = document.getElementById("oldpicpath");
        var strHtml = '<input type="file" id="picpath" name="picpath"/>';
        oldpicpath.value = "";
        picpathtd.innerHTML = strHtml;
    }

    function submitData() {
        if (validData()) {
            document.getElementById("addForm").submit();
        }
    }

    function submitBack() {
        history.back();
    }

    function validData() {
        var title = document.getElementById("title");
        var states = document.getElementById("states");
        if (title.value == '') {
            alert("请输入标题!")
            return false;
        }
        if (states.value == '') {
            alert("请选择状态!")
            return false;
        }
        return true;
    }
</script>
</body>
</html>

