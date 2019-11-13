var contextPath = $("#contextPath").val();
//请求地址
var productListUrl = contextPath + "/productServlet?action=productview";
var pagesListUrl = contextPath + "/productServlet?action=productlist";

Refresh(productListUrl,pagesListUrl);

function Refresh(productListUrl,pagesListUrl) {
    $.ajaxSettings.async = false;
    $.getJSON(productListUrl, function (data, status) {
        if (data != null && data.length > 0) {
            var productResult = '';
            for (var i = 0; i < data.length; i++) {
                productResult += '<a class="card" href="javascript:buy(' + $("#user").val() +','+i+')">';
				productResult += '<input type="hidden" id="productId'+i+'" value="'+data[i].productId+'"/>';
				productResult += '<input type="hidden" id="productName'+i+'" value="'+data[i].productName+'"/>';
				productResult += '<div class="media-wrapper">';
                productResult += '<img src="' + contextPath + '/' + data[i].productPicPath0 + '">';
                productResult += '</div>';
                productResult += '<div class="caption">' + data[i].productName + '</div>';
                productResult += '<div class="card-heading"><strong>' + data[i].productName + '</div>';
                productResult += '<div class="card-content text-muted">评价&#160;1000&#160;&#160;&#160;&#160;收藏&#160;44444</div>';
                productResult += '<div class="pull-left">￥' + data[i].productPrice + '</div>';
                productResult += '<div class="pull-right">月销55555笔</div>';
                productResult += '</a>';
            }
            //输出Html
            $("#productdiv").html(productResult);
        }
        else {
			var productResult = '';
			$("#productdiv").html(productResult);
		}
    });

	$.getJSON(pagesListUrl, function (data, status) {
		if (data != null && data.length > 0) {
			var pagesResult = '';
			pagesResult += '<tr> <td colspan="7">';
			pagesResult += '<input type="hidden" id="totalCount" value="'+data[0].totalCount+'"/>';
			pagesResult += '<input type="hidden" id="pageCount" value="'+data[0].pageCount+'"/>';
			pagesResult += '<input type="hidden" id="currentPage" value="'+data[0].currentPage+'"/>';
			pagesResult += '总共' + data[0].totalCount + '条记录&nbsp;&nbsp;总共' + data[0].pageCount + '页&nbsp;&nbsp;当前第' + (parseInt(data[0].currentPage) + 1) + '页&nbsp;&nbsp;&nbsp;&nbsp;'
			pagesResult += '<a href="javascript:pageHome()">首页</a>&nbsp;&nbsp;|&nbsp;&nbsp;'
			pagesResult += '<a href="javascript:pagePre()">上一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;'
			pagesResult += ' <a href="javascript:pageNext()">下一页</a>&nbsp;&nbsp;|&nbsp;&nbsp;'
			pagesResult += ' <a href="javascript:pageEnd()">尾页</a>&nbsp;&nbsp;|&nbsp;&nbsp;'
			pagesResult += '</td></tr>'
			$("#pages").html(pagesResult);
		}
	});
    $.ajaxSettings.async = true;
}

// $("#buy").click(function(){
// 	if (confirm('确定要购买此商品吗?')) {
//
// 	}
// });
function buy(uid,i) {
	var productId="productId"+i
	var productName="productName"+i
	if(uid==null)alert("请登陆后购买")
	else if (confirm('确定要添加此商品到购物车吗?')) {
		//请求地址和参数
		var loginUrl = contextPath+ "/shoppingCartServlet"
		var param = "action=newadd";
		//验证输入框
		//拼接请求参数
		param += "&productId=" + document.getElementById(productId).value;
		param += "&productName="+document.getElementById(productName).value;
		param += "&userId="+document.getElementById("user").value
		$.ajax({
			url:loginUrl,
			type:"post",
			data:param,//请求参数
			dataType:"text",//请求参数格式
			success:function(data){
				if(data=='1') {
					alert("添加成功");
				}
			},
			error:function(e){
				alert(e);
				console.log(e);
			}
		});
	}
}

//首页
function pageHome() {
	if ($("#currentPage").val() == '0') {
		document.getElementById("type").value=''
        Refresh(productListUrl,pagesListUrl);
	}
}

//上一页
function pagePre() {
	if ($("#currentPage").val() == '0') {
		alert("已经是第一页!")
	} else {
		//location.href = contextPath +'/productServlet?action=productview&currentPage=' + (parseInt($("#currentPage").val()) - 1);
		var productListUrl = contextPath + '/productServlet?action=productview&currentPage=' + parseInt($("#currentPage").val()-1)+'&type_id='+$("#type").val();
		var pagesListUrl = contextPath + '/productServlet?action=productlist&currentPage=' + parseInt($("#currentPage").val()-1)+'&type_id='+$("#type").val();
        Refresh(productListUrl,pagesListUrl);
	}
}

//下一页
function pageNext() {
	if (parseInt($("#currentPage").val())+1 >= parseInt($("#pageCount").val())) {
		alert("已经是最后一页!")
	} else {
		//location.href = contextPath +'/productServlet?action=productview&currentPage=' + parseInt($("#currentPage").val());
		var productListUrl = contextPath + '/productServlet?action=productview&currentPage=' + parseInt($("#currentPage").val()+1)+'&type_id='+$("#type").val();
		var pagesListUrl = contextPath + '/productServlet?action=productlist&currentPage=' + parseInt($("#currentPage").val()+1)+'&type_id='+$("#type").val();;
        Refresh(productListUrl,pagesListUrl);
	}
}

//尾页
function pageEnd() {
	if (parseInt($("#currentPage").val())+1 >= parseInt($("#pageCount").val())) {
		alert("已经是最后一页!")
	} else {
		//location.href = contextPath +'/productServlet?action=productview&currentPage=' + parseInt($("#currentPage").val()) ;
		var productListUrl = contextPath + '/productServlet?action=productview&currentPage=' + parseInt($("#pageCount").val()-1)+'&type_id='+$("#type").val();
		var pagesListUrl = contextPath + '/productServlet?action=productlist&currentPage=' + parseInt($("#pageCount").val()-1)+'&type_id='+$("#type").val();
        Refresh(productListUrl,pagesListUrl);
	}
}
