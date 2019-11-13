<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/16
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品修改</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/localization/messages_zh.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/product.js"></script>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="${pageContext.request.contextPath}/productServlet?action=update&nid=${productId}"
              id="addForm" method="post" enctype="multipart/form-data">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">修改商品</th>
                </tr>
                <tr>
                    <td>
                        商品名称<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="name1" name="name" value="${product.getName()}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品类别<font color="red">*</font>
                    </td>
                    <td>
                        <select id="type_id1" name="type_id" style="width: 170px ">
                            <option value="">请选择</option>
                            <c:forEach var="i" items="${productTypeList}">
                                <option value="${i.productTypeId}">${i.productType}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品品牌<font color="red">*</font>
                    </td>
                    <td>
                        <select id="brand_id1" name="brand_id" style="width: 170px ">
                            <option value="">请选择</option>
                            <c:forEach var="i" items="${productBrandList}">
                                <option value="${i.productBrandId}">${i.productBrand}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品价格<font color="red">*</font>
                    </td>
                    <td>
                        <input type="text" id="price1" name="price" value="${product.getPrice()}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        排序码<font color="red1">*</font>
                    </td>
                    <td>
                        <input type="text" id="sort_id1" name="sort_id" value="${product.getSort_id()}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品描述
                    </td>
                    <td>
                        <input type="text" id="summary1" name="summary" value="${product.getSummary()}"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <%int j=0;%>
                        <c:forEach  var="picpath"  items="${product.getNpicpath()}">
                            <input type="hidden" id="oldpicpath<%=j%>" name="oldpicpath<%=j%>"
                                   value="${picpath}"/>
                            <%j++;%>
                        </c:forEach>
                    </td>
                </tr>
                <tr >
                    <td colspan="2" align="center">
                        图片
                    </td>
                        <%int i=0;%>
                        <c:forEach var="picpath" items="${product.getNpicpath()}">
                        <tr id="picpathed<%=i%>">
                            <td colspan="2">
                            <c:if test="${picpath==null}">
                                <input type="file" id="picpath<%=i%>" name="picpath"/>
                                picpath<%=i%>
                            </c:if>
                            <c:if test="${picpath!=null}">
                                <img src="${picpath}" width="100px" height="100px"/>
                                <input type="button" value="删除" onclick="showPicInput('picpathed<%=i%>','picpath<%=i%>','oldpicpath<%=i%>')"/>
                                picpath<%=i%>
                            </c:if>
                            <%i++;%>
                            </td>
                        </tr>
                        </c:forEach>
                </tr>

                <tr>
                    <td>
                        操作人
                    </td>
                    <td align="center">
                        ${userId}
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="hidden" id="validProductName" value="${pageContext.request.contextPath}/productServlet?action=validProductName"/>
                        <input type="hidden" id="operator" name="operator" value="${userId}"/>

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
        document.getElementById("addForm").submit();
    }
    //显示图片输入框
    function showPicInput(picpathed,npicpath,opicpath) {
        var picpath = document.getElementById(picpathed);
        var oldpicpath = document.getElementById(opicpath);
        //alert(oldpicpath)
        strHtml = '<input type="file" id="'+npicpath+'" name="'+npicpath+'"/>';
        alert(strHtml)
        oldpicpath.value = "";
        picpath.innerHTML = strHtml;
    }
</script>
</body>
</html>
