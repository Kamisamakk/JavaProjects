<%@page import="java.io.FileOutputStream" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="java.io.*" %>
<%@ page import="org.apache.commons.io.IOUtils" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新闻保存页面</title>
</head>
<body>
<%
    DiskFileItemFactory factory = new DiskFileItemFactory();
    //设置缓冲期大小
    factory.setSizeThreshold(8 * 1024);
    //创建上传组件
    ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
    //设置上传总大小
    servletFileUpload.setSizeMax(1024 * 1024 * 80);
    //设置单文件大小 40M
    servletFileUpload.setFileSizeMax(1024 * 1024 * 40);
    //解析请求数据
    List<FileItem> items = servletFileUpload.parseRequest(request);
    if (items != null && items.size() > 0) {
        for (FileItem item : items) {
            //判断是否是普通输入框
            if (item.isFormField()) {
                System.out.println("isFormField=" + item.getFieldName());
                System.out.println("isFormField=" + item.getString());
            } else {
                System.out.println("获取上传文件的值" + item.getFieldName());
                System.out.println("获取上传文件的值" + item.getName());
                //获取项目真实路径
                String realpath = request.getServletContext().getRealPath("/");
                String filename = realpath + "/" + item.getName();
                OutputStream os = new FileOutputStream(new File(filename));
                IOUtils.copy(item.getInputStream(), os);
            }
        }
    }
%>
</body>
</html>