$(document).ready(function () {
    //alert($("#validProductName").val());
    $("#addForm").validate({
        rules: {
            name: {
                "required": true,
                "maxlength": 18,
                "remote": {
                    url: $("#validProductName").val(),
                    type: 'get',
                    dataType: 'json',//{"productName":1234}
                    data: {
                        'product': function () {
                            return $("#name1").val();
                        }
                    }
                }
            }
        },
        messages: {
            name:{
                "required": "请输入商品名称",
                "remote": "商品已经存在"
            }
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
});