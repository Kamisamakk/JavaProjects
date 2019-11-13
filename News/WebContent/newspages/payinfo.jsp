<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/22
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>付款信息</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
</head>
<body>
<div id="main" align="center" class="info">
    <table border="1" style="width:800px;" >
        <tr>
            <th colspan="2">商品详情</th>
        </tr>
        <c:forEach var="product" items="${productList}">
        <tr>
            <td>
                商品名称
            </td>
            <td>
                ${product.productName}
            </td>
        </tr>
        <tr>
            <td>
                商品类别
            </td>
            <td>
                ${product.productType}
            </td>
        </tr>
        <tr>
            <td>
                商品品牌
            </td>
            <td>
                ${product.productBrand}
            </td>
        </tr>
        <tr>
            <td>
                商品价格
            </td>
            <td>
                ${product.productPrice}*${product.productCount}=${product.productPrice*product.productCount}
            </td>
        </tr>
        <tr>
            <td>
                商品图片
            </td>
            <td>
                    <c:if test="${product.productPicPath0==null}">

                    </c:if>
                    <c:if test="${product.productPicPath0!=null}">
                        <img src="${product.productPicPath0}" width="100px" height="100px"/>
                    </c:if>
            </td>
        </tr>
        </c:forEach>
    </table>
    <div align="center">
        <input type="button" onclick="pay()" value="付款"/>
        <input type="button" onclick="back()" value="返回"/>
    </div>
</div>
<script type="text/javascript">
    function back() {
        history.back();
    }
    function pay() {
        //请求地址和参数
        var loginUrl = "${pageContext.request.contextPath}/shoplistServlet";
        var param = "action=pay"
        param+="&userId=${userId}"
        $.ajax({
            url:loginUrl,
            type:"post",
            data:param,//请求参数
            dataType:"text",//请求参数格式
            success:function(data){
                if(data=='1') {
                    alert("支付成功!");
                    history.back()
                }
                else {
                    alert("支付失败!");
                }
            },
            error:function(e){
                alert(e);
                console.log(e);
            }
        })
    }
</script>
</body>
</html>
