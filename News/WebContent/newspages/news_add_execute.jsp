<%@page import="org.jbit.news.dao.impl.NewsDaoImpl" %>
<%@page import="org.jbit.news.dao.NewsDao" %>
<%@page import="org.apache.commons.io.IOUtils" %>
<%@page import="java.io.FileOutputStream" %>
<%@page import="java.io.*" %>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.*" %>
<%@page import="org.apache.commons.fileupload.*" %>
<%@page import="org.jbit.news.entity.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新闻保存</title>
</head>
<body>
<%
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload fileUpload = new ServletFileUpload(factory);
    List<FileItem> fileList = fileUpload.parseRequest(request);
    News news = new News();
    for (FileItem fileItem : fileList) {
        if (fileItem.isFormField()) {
            if ("title".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                news.setNtitle(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            } else if ("author".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                news.setNauthor(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            } else if ("tid".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                news.setNtid(Integer.parseInt(fileItem.getString()));
            } else if ("summary".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                news.setNsummary(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            } else if ("content".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                news.setNcontent(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            }
            System.out.println(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
        } else {
            System.out.println(fileItem.getFieldName() + "," + fileItem.getName());
            String realPath = request.getServletContext().getRealPath("/");
            String fileName = fileItem.getName();
            news.setNpicpath(fileName);
            OutputStream os = new FileOutputStream(new File(realPath + "/" + fileName));
            IOUtils.copy(fileItem.getInputStream(), os);
        }
    }
    NewsDao newsDao = new NewsDaoImpl();
    int result = newsDao.saveNews(news);
    if (result > 0) {
        out.println("<script>alert('保存成功');location.href='news_list.jsp'</script>");
    } else {
        out.println("<script>alert('保存失败');history.back();");
    }
%>
</body>
</html>