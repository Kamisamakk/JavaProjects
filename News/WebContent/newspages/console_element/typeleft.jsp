<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/19
  Time: 9:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.core.js"></script>
<script type="text/javascript">
    var setting = {
        async: {
            enable: true,//开启异步
            url:"${pageContext.request.contextPath}/productTypeServlet?action=queryLeftMenuList",
            autoParam:["id"],
            dataFilter: filter
        }
    }

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].url = "${pageContext.request.contextPath}/"+childNodes[i].url;
            childNodes[i].target = "_self";
        }
        return childNodes;
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting);
    });
</script>
<div id="opt_list">
    <ul id="treeDemo" class="ztree"></ul>
</div>