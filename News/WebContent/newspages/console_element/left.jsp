<%@ page import="org.jbit.news.dao.MenuDao" %>
<%@ page import="org.jbit.news.dao.impl.MenuDaoImpl" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    MenuDao menuDao=new MenuDaoImpl();
    List<Map<String, Object>> menulist=menuDao.findMenuList(null);
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css">
<div id="opt_list" class="tree">
   <%-- <ul>
        <% for (Map<String, Object> menu:menulist) {
        %>
        <li><a href="${pageContext.request.contextPath}/<%=menu.get("njsp")%>"><%=menu.get("menuName")%></a></li>
        <%
            }
        %>
        <%--
        <li><a href="${pageContext.request.contextPath}/newspages/topic_add.jsp">添加主题</a></li>
        <li><a href="${pageContext.request.contextPath}/newspages/topic_list.jsp">主题管理</a></li>
        <li><a href="${pageContext.request.contextPath}/newspages/news_add.jsp">添加新闻</a></li>
        <li><a href="${pageContext.request.contextPath}/newspages/news_list.jsp">新闻管理</a></li>
        <li><a href="${pageContext.request.contextPath}/newspages/advertisement_add.jsp">添加广告</a></li>
        <li><a href="${pageContext.request.contextPath}/newspages/advertisement_list.jsp">广告管理</a></li>
        <li><a href="${pageContext.request.contextPath}/menuServlet?action=initAdd">添加菜单</a></li>
        <li><a href="${pageContext.request.contextPath}/menuServlet?action=list">菜单管理</a></li>

    </ul>--%>
    <ol>
        <%
            String sonName =null;
            for (Map<String, Object> menu:menulist) {
                //System.out.println(menu.get("menuName"));
                if(menu.get("menuParentName")==null||menu.get("menuParentName").equals("")) {
        %>
        <li>
            <label for="<%=menu.get("menuId")%>" class="folderOne">
                <a style="text-decoration:none;"
                        <%
                            if(menu.get("njsp")!=null)
                        {
                        %>
                   href="${pageContext.request.contextPath}/<%=menu.get("njsp")%>"
                        <%
                            }
                        %>
                ><%=menu.get("menuName")%>
                </a>
            </label>
            <input type="checkbox" id="<%=menu.get("menuId")%>" />
            <ol>
        <%
            for (Map<String, Object> menu1:menulist) {
                //System.out.println(menu.get("menuId")+"  "+menu1.get("menuParentName"));
                if(menu.get("menuId").toString().equals(menu1.get("menuParentName"))) {
                    sonName =(String)menu1.get("menuName");
        %>
                <li class="file folderThree"><a href="${pageContext.request.contextPath}/<%=menu1.get("njsp")%>"><%=sonName%></a></li>
        <%
                }
            }
        %>
            </ol>
        </li>
        <%
                }
            }
        %>
    </ol>
</div>
