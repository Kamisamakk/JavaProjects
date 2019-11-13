<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/3
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@page import="org.apache.commons.io.IOUtils" %>
<%@page import="java.io.FileOutputStream" %>
<%@page import="java.io.*" %>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.*" %>
<%@page import="org.apache.commons.fileupload.*" %>
<%@page import="org.jbit.news.entity.*" %>
<%@ page import="org.jbit.news.dao.AdvertisementDao" %>
<%@ page import="org.jbit.news.dao.impl.AdvertisementDaoImpl" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改广告</title>
</head>
<body>
<%
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload fileUpload = new ServletFileUpload(factory);
    List<FileItem> fileList = fileUpload.parseRequest(request);
    Advertisement advertisement = new Advertisement();
    //advertisement.setNid(Integer.parseInt(nid));
    for (FileItem fileItem : fileList) {
        if (fileItem.isFormField()) {
            if ("title".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                advertisement.setNtitle(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            } else if ("nid".equals(fileItem.getFieldName())) {
                advertisement.setNid(Integer.parseInt(fileItem.getString()));
            } else if ("states".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                advertisement.setNstates(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            } else if ("summary".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                advertisement.setNsummary(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            } else if ("oldpicpath".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                advertisement.setNpicpath(new String(fileItem.getString().getBytes("ISO8859-1"), "UTF-8"));
                //System.out.println("advertisement = " + advertisement.getNpicpath());
            } else if ("beginTime".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                advertisement.setNbegindate(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            } else if ("endTime".equals(fileItem.getFieldName())
                    && fileItem.getString() != null && !"".equals(fileItem.getString().trim())) {
                advertisement.setNenddate(new String(fileItem.getString().getBytes("ISO8859-1"), "utf-8"));
            }
        } else {
            if (fileItem.getName() != null && !"".equals(fileItem.getName().trim())) {
                String realPath = request.getServletContext().getRealPath("/");
                String extendName = fileItem.getName().substring(fileItem.getName().lastIndexOf(".") + 1);
                String filename = System.currentTimeMillis() + "." + extendName;
                advertisement.setNpicpath(filename);
                OutputStream os = new FileOutputStream(new File(realPath + "/" + filename));
                IOUtils.copy(fileItem.getInputStream(), os);
            }
        }
    }
    AdvertisementDao advertisementDao = new AdvertisementDaoImpl();
    int result = advertisementDao.updateAdvertisements(advertisement);
    if (result > 0) {
        out.println("<script>alert('保存成功');location.href='advertisement_list.jsp'</script>");
    } else {
        out.println("<script>alert('保存失败');history.back();");
    }
%>
</body>
</html>
