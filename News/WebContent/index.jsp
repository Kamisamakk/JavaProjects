<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.jbit.news.dao.TopicsDao"%>
<%@page import="org.jbit.news.entity.Topic"%>
<%@page import="org.jbit.news.dao.impl.TopicsDaoImpl"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>新闻中国</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css?ver=2"/>
    <link href="${pageContext.request.contextPath}/css/zui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/css/zui.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/zui.js"></script>
    <script type="text/javascript">
        function check() {
            var login_username = document.getElementById("uname");
            var login_password = document.getElementById("upwd");
            if (login_username.value == "") {
                alert("用户名不能为空！请重新填入！");
                login_username.focus();
                return false;
            } else if (login_password.value == "") {
                alert("密码不能为空！请重新填入！");
                login_password.focus();
                return false;
            }
            return true;
        }

        function focusOnLogin() {
            var login_username = document.getElementById("uname");
            login_username.focus();
        }
        function enterCheck(){
            if(event.keyCode == 13){
                check();
            }
        }
        function register(){
            location.href="${pageContext.request.contextPath}/user_register.jsp";
        }
        //登录
        function login2(){
            alert("管理员登录")
            //创建AJAX引擎
            var xmlHttp = new XMLHttpRequest();
            //请求地址和参数
            var loginUrl = contextPath+"/userListServlet";
            var param = "action=login";
            //验证输入框
            //拼接请求参数
            param += "&username="+$("#uname").val();
            param += "&upwd="+$("#upwd").val();
            //创建请求
            xmlHttp.open("POST", loginUrl, true);
            //设置请求头，告诉POST普通表单
            xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
            //发送请求
            xmlHttp.send(param);
            //接收响应
            xmlHttp.onreadystatechange=function(){
                if (xmlHttp.readyState == 4 && xmlHttp.status==200){
                    if(xmlHttp.responseText=='1') {
                        location.href = "${pageContext.request.contextPath}/newspages/admin.jsp";
                    }
                    else{
                        alert("用户名或者密码错!请检查账号是否冻结");
                    }
                }
            }
        }
        function login3(){
            //请求地址和参数
            var loginUrl = contextPath+"/userListServlet";
            var param = "action=login";
            //验证输入框
            //拼接请求参数
            param += "&username="+$("#uname").val();
            param += "&upwd="+$("#upwd").val();
            //alert(param);
            $.ajax({
                url:loginUrl,
                type:"post",
                data:param,//请求参数
                dataType:"text",//请求参数格式
                success:function(data){
                    if(data=='1') {
                        location.href = "${pageContext.request.contextPath}/newspages/admin.jsp";
                    }
                    else {
                        alert("用户名或者密码错!");
                    }
                },
                error:function(e){
                    alert(e);
                    console.log(e);
                }
            })
        }

        function login(){
            //请求地址和参数
            var loginUrl = contextPath+"/api/userListServlet";
            var param = "action=login&username="+$("#uname").val()+"&upwd="+$("#upwd").val();
            //alert(loginUrl)
            console.log('loginUrl='+loginUrl);
            console.log('param='+param);
            $.ajax({
                url:loginUrl,
                type:"get",
                data:param,
                dataType:"text",
                success:function(result){
                    if (result=='1') {
                        userResult='<div id="top_login">欢迎您,用户'+$("#uname").val();
                        //userResult+='<a href="${pageContext.request.contextPath}/userListServlet?action=info&userId='+$("#uname").val()+'">个人中心</a>&nbsp;';
                        userResult+='&nbsp;&nbsp;&nbsp;<div class="btn-group">'
                        userResult+='<button type="button" class="btn btn-mini" data-toggle="dropdown">'
                        userResult+='个人中心 <span class="caret"></span></button>'
                        userResult+='<ul class="dropdown-menu" role="menu">'
                        //userResult+='<li><a class="label label-primary label-outline" href="${pageContext.request.contextPath}/shoppingCartServlet?action=cart">购物车</a></li>'
                        userResult+='<li><a class="label label-primary label-outline" data-type="ajax" data-url="${pageContext.request.contextPath}/shoppingCartServlet?action=cart&userId='+$("#uname").val()+'" data-toggle="modal">购物车</a></li>'
                        userResult+='<li><a class="label label-primary label-outline" href="###">订单</a></li></ul>'
                        userResult+='</div>'
                        userResult+='<a href="${pageContext.request.contextPath}/userListServlet?action=logout">退出</a>';
                        userResult+='<input type="hidden" id="user" value="'+$("#uname").val()+'">'
                        userResult+='</div>'
                        userResult+='<input type="hidden" id="rename" name="rename" value="${userId}" />'
                        userResult+='<input type="hidden" id="repwd" name="repwd" value="${userPassword} "/>'
                        $("#top_login").html(userResult);
                        //var productListUrl = contextPath + "/productServlet?action=productview";
                        Refresh(productListUrl,pagesListUrl);
                    }
                    else {
                        alert("用户名或者密码错");
                    }
                },
                error:function(e){
                    alert(e);
                }
            });
        }

    </script>
</head>
<body onload="">
<div id="header">
    <div id="top_login">
        <form action="" method="post">
            <label> 登录名${userId} </label>
            <c:if test="${userId!=null}">
                <script type="text/javascript">
                    userResult='<div id="top_login">欢迎您,用户${userId}';
                    userResult+='&nbsp;&nbsp;&nbsp;<div class="btn-group">'
                    userResult+='<button type="button" class="btn btn-mini" data-toggle="dropdown">'
                    userResult+='个人中心 <span class="caret"></span></button>'
                    userResult+='<ul class="dropdown-menu" role="menu">'
                    userResult+='<li><a class="label label-primary label-outline" data-type="ajax" data-url="${pageContext.request.contextPath}/shoppingCartServlet?action=cart&userId='+${userId}+'" data-toggle="modal">购物车</a></li>'
                    userResult+='<li><a class="label label-primary label-outline" href="###">订单</a></li></ul>'
                    userResult+='</div>'
                    userResult+='<a href="${pageContext.request.contextPath}/userListServlet?action=logout">退出</a>';
                    userResult+='<input type="hidden" id="user" value="'+${userId}+'">'
                    userResult+='</div>'
                    $("#top_login").html(userResult);
                </script>
            </c:if>
            <input type="text" id="uname" name="uname" value="" class="login_input" />
            <label> 密&#160;&#160;码 </label>
            <input type="password" id="upwd" name="upwd" value="" class="login_input"/>
            <input type="button" class="btn btn-mini" value="用户登录" onclick="login()" />
            <input type="button" class="btn btn-mini" value="管理员登录" onclick="login2()" />
            <label id="error">
            </label>
            <img src="images/friend_logo.gif" alt="Google" id="friend_logo" />
        </form>
    </div>
    <div id="nav">
        <div id="myNiceCarousel" class="carousel slide" data-ride="carousel">
            <!-- 圆点指示器 -->
            <ol class="carousel-indicators">
                <li data-target="#myNiceCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myNiceCarousel" data-slide-to="1"></li>
                <li data-target="#myNiceCarousel" data-slide-to="2"></li>
            </ol>

            <!-- 轮播项目 -->
            <div class="carousel-inner">
                <div class="item active">
                    <img alt="First slide" src="http://zui.sexy/docs/img/slide1.jpg">
                    <div class="carousel-caption">
                        <h3>我是第一张幻灯片</h3>
                        <p>:)</p>
                    </div>
                </div>
                <div class="item">
                    <img alt="Second slide" src="http://zui.sexy/docs/img/slide2.jpg">
                    <div class="carousel-caption">
                        <h3>我是第二张幻灯片</h3>
                        <p>0.0</p>
                    </div>
                </div>
                <div class="item">
                    <img alt="Third slide" src="http://zui.sexy/docs/img/slide3.jpg">
                    <div class="carousel-caption">
                        <h3>我是第三张幻灯片</h3>
                        <p>最后一张咯~</p>
                    </div>
                </div>
            </div>

            <!-- 项目切换按钮 -->
            <a class="left carousel-control" href="#myNiceCarousel" data-slide="prev">
                <span class="icon icon-chevron-left"></span>
            </a>
            <a class="right carousel-control" href="#myNiceCarousel" data-slide="next">
                <span class="icon icon-chevron-right"></span>
            </a>
        </div>
    </div>
</div>
<div id="container">
    <div class="sidebar">
        <div class="panel" id="">
            <div class="panel-heading">
                商品分类
            </div>
                <ul id="treeDemo" class="ztree"></ul>
                <input id="type" type="hidden" value=""/>
        </div>
    </div>
    <div class="main">
        <div class="class_type">
            <img src="images/class_type.gif" alt="新闻中心" />
        </div>
        <div class="content" id="productdiv">

        </div>

        <jsp:include page="index-elements/index_rightbar.jsp"></jsp:include>

    </div>

</div>
<div align="center" style="width: 100%"  id="pages">
</div>
<jsp:include page="index-elements/index_bottom.html"></jsp:include>
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js?ver=1"></script>
<script type="text/javascript">
    var setting = {
        async: {
            enable: true,//开启异步
            url:"${pageContext.request.contextPath}/productTypeServlet?action=queryLeftMenuList",
            autoParam:["id"],
            dataFilter: filter
        },
        callback:{
            onClick: zTreeOnClick
        }
    }
    function zTreeOnClick(event, treeId, treeNode) {
        if (treeNode.isParent) {
            return;
        }
        document.getElementById("type").value=treeNode.id
        //location.reload();
        //alert("我是"+treeNode.name+"  type_id是"+$("#type").val());
        //refush();
        var productListUrl = contextPath + '/productServlet?action=productview&type_id=' + $("#type").val();
        var pagesListUrl = contextPath + '/productServlet?action=productlist&type_id=' + $("#type").val();
        Refresh(productListUrl,pagesListUrl);
    }

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            if(!childNodes)
                childNodes[i].url= "${pageContext.request.contextPath}/"+childNodes[i].url;
            childNodes[i].target = "_self";
        }
        return childNodes;
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting);
    });


</script>
</body>
</html>