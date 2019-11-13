<%--
  Created by IntelliJ IDEA.
  User: Minato
  Date: 2019/9/20
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>订单审核</title>
    <link href="${pageContext.request.contextPath}/css/admin.css?ver=3" rel="stylesheet" type="text/css"/>
</head>
<body>
<jsp:include page="console_element/top.jsp"/>
<div id="main">
    <%@include file="console_element/left.jsp" %>
    <div id="opt_menu">
        <form action="${pageContext.request.contextPath}/shoplistServlet?action=check"
              id="updateForm" method="post">
            <table border="1" style="width:600px;">
                <tr>
                    <th colspan="2">审核订单</th>
                </tr>

                <tr>
                    <td>
                        订单号<font color="red">*</font>
                    </td>
                    <td>
                        ${shopList.get("listNo")}
                    </td>
                </tr>
                <tr>
                    <td>
                        状态<font color="red">*</font>
                    </td>
                    <td>
                        <select id="check_states" name="check_states">
                            <c:if test="${shopList.listStates==0}">
                                <option value="${shopList.listStates==0}">未审核
                                </option>
                            </c:if>
                            <option value="1">审核通过</option>
                            <option value="2">审核不通过</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        订单金额
                    </td>
                    <td>
                        ${shopList.listMoney}
                    </td>
                </tr>
                <tr>
                    <td>
                        下单人编号
                    </td>
                    <td>
                        ${shopList.listUserId}
                    </td>
                </tr>
                <tr>
                    <td>
                        订单创建时间
                    </td>
                    <td>
                        ${shopList.listCDate}
                    </td>
                </tr>
                <tr>
                    <td>
                        订单修改时间
                    </td>
                    <td>
                        ${shopList.listMDate}
                    </td>
                </tr>
                <tr>
                    <td>
                        退款金额
                    </td>
                    <td>
                        ${shopList.listReMoney}
                    </td>
                </tr>
                <tr>
                    <td>
                        退款时间
                    </td>
                    <td>
                        ${shopList.listReDate}
                    </td>
                </tr>
                <tr>
                    <td>
                        订单备注
                    </td>
                    <td>
                        ${shopList.listSummary}
                    </td>
                </tr>
                <tr>
                    <td>
                        审核时间
                    </td>
                    <td>
                        ${shopList.listCkDate}
                    </td>
                </tr>
                <tr>
                    <td>
                        审核意见
                    </td>
                    <td>
                        <input type="text" id="checkcomment" name="checkcomment">
                    </td>
                </tr>
                <tr>
                    <td>
                        商品名称
                    </td>
                    <td>
                        ${shopList.listName}
                    </td>
                </tr>
                <tr>
                    <td>
                        商品数量
                    </td>
                    <td>
                        ${shopList.listCount}
                    </td>
                </tr>
                <tr>
                    <td>
                        商品单价
                    </td>
                    <td>
                        ${shopList.listPrice}
                    </td>
                </tr>
                <tr>
                    <td>
                        商品合计
                    </td>
                    <td>
                        ${shopList.listTotal}
                    </td>
                </tr>
                <tr>
                    <td>
                        商品描述
                    </td>
                    <td>
                        ${shopList.listSummary}
                    </td>
                </tr>
                <tr>
                    <td>
                        商品类别
                    </td>
                    <td>
                        ${shopList.listType}
                    </td>
                </tr>
                <tr>
                    <td>
                        商品品牌
                    </td>
                    <td>
                        ${shopList.listBrand}
                    </td>
                </tr>
                <tr>
                    <td>
                        商品图片1
                    </td>
                    <td>
                      <img src="${shopList.listPicPath0}" alt=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品图片2
                    </td>
                    <td>
                        <img style="width: 200px;height: 200px" src="${shopList.listPicPath1}" alt=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品图片3
                    </td>
                    <td>
                        <img style="width: 200px;height: 200px" src="${shopList.listPicPath2}" alt=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品图片4
                    </td>
                    <td>
                        <img style="width: 200px;height: 200px" src="${shopList.listPicPath3}" alt=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品图片5
                    </td>
                    <td>
                        <img style="width: 200px;height: 200px" src="${shopList.listPicPath4}" alt=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品图片6
                    </td>
                    <td>
                        <img style="width: 200px;height: 200px" src="${shopList.listPicPath5}" alt=""/>
                    </td>
                </tr>
                <tr>
                    <td>
                        操作时间
                    </td>
                    <td>
                        ${shopList.listOprDate}
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="button" onclick="submitData()" value="确定"/>
                        <input type="button" onclick="submitBack()" value="取消"/>
                        <input type="hidden" id="nid" name="nid" value="${shopList.listNo}"/>
                        <input type="hidden" id="operator" name="operator" value=" ${userId}"/>
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
        document.getElementById("updateForm").submit();
    }

    function submitBack() {
        history.back();
    }
</script>
</body>
</html>
