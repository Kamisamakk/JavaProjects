<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/22
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>购物商城</title>
    <link href="${pageContext.request.contextPath}/css/zui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/zui.min.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/shopping.css?ver=3" type="text/css" rel="stylesheet" />
</head>
<body>
<div align="center">
<table>
    <tr height="50">
        <td colspan="3" align="center" ><h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;购物车</h1></td>
    </tr>
    <tr align="center" height="30" bgcolor="lightgrey">
        <td>名称</td>
        <td>价格</td>
        <td>数量</td>
        <td>清除</td>
    </tr>
    <c:if test="${productList!=null}">
        <%int i=0;%>
        <c:forEach var="product" items="${productList}">
            <tr align="center" height="50">
                <input type="hidden" id="price<%=i%>" style="width: 50px;text-align: center" type="text" value=" ${product.productPrice}"/>
                <td>${product.productName}</td>
                <td>${product.productPrice}</td>
                <td>
                    <a class="btn btn-mini" href="javascript:reduce('${product.productName}',<%=i%>)" >
                        减
                    </a>
                    <input id="productCount<%=i%>" style="width: 50px;text-align: center" type="text" value=" ${product.productCount}"/>
                    <a class="btn btn-mini" href="javascript:add('${product.productName}',<%=i%>)">
                        加
                    </a>
                </td>
                <td><a class="btn" href="">移除</a></td>
            </tr>
            <tr height="50">
                <td colspan="3" align="left" ><h4 id="view<%=i%>">应付金额：${product.productCount*product.productPrice}</h4></td>
                <br>
            </tr>
            <%i++;%>
        </c:forEach>
        <tr align="center" height="50">
            <td align="left" colspan="3"><a class="btn btn-primary" href="${pageContext.request.contextPath}/shoppingCartServlet?action=payjsp&userId=${userId}">结算</a></td>
            <td align="right" colspan="3"><a class="btn btn-primary" href="">清空购物车</a></td>
        </tr>
    </c:if>
    <c:if test="${productList==null}">
        <tr height="100">
            <td colspan="3" align="center">没有商品可以显示！</td>
        </tr>
    </c:if>

</table>
</div>
<script type="text/javascript">
    function add(productName,i) {
        var productCount="productCount"+i
        var price="price"+i
        var view="view"+i
        //请求地址和参数
        var loginUrl = contextPath+"/shoppingCartServlet";
        var param = "action=add";
        //验证输入框
        //拼接请求参数
        param += "&productName="+productName;
        //alert(param);
        $.ajax({
            url:loginUrl,
            type:"post",
            data:param,//请求参数
            dataType:"text",//请求参数格式
            success:function(data){
                if(data>=1) {
                    document.getElementById(productCount).value=parseInt(document.getElementById(productCount).value)+1;
                    //document.getElementById(total).value=parseInt(document.getElementById(total).value)+parseInt(document.getElementById(price).value);
                   var money=parseInt(document.getElementById(productCount).value)*parseInt(document.getElementById(price).value)
                    document.getElementById(view).innerText="应付金额："+money


                }
            },
            error:function(e){
                alert(e);
                console.log(e);
            }
        })
    }

    function reduce(productName,i) {
        var productCount="productCount"+i
        var price="price"+i
        var view="view"+i
        //请求地址和参数
        var loginUrl = contextPath+"/shoppingCartServlet";
        var param = "action=reduce";
        //验证输入框
        //拼接请求参数
        param += "&productName="+productName;
        if(parseInt(document.getElementById(productCount).value)>1){
            //alert(param);
            $.ajax({
                url:loginUrl,
                type:"post",
                data:param,//请求参数
                dataType:"text",//请求参数格式
                success:function(data){
                    if(data>=1) {
                        document.getElementById(productCount).value=parseInt(document.getElementById(productCount).value)-1;
                        var money=parseInt(document.getElementById(productCount).value)*parseInt(document.getElementById(price).value)
                       // alert(money)
                        document.getElementById(view).innerText="应付金额："+money
                    }
                },
                error:function(e){
                    alert(e);
                    console.log(e);
                }
            })
        }else {
            alert("错误")
        }

    }
</script>
</body>
</html>