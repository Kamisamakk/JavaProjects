$(document).ready(function () {
    jQuery.validator.addMethod("isMobile", function (value, element) {
        var length = value.length;
        var mobile = /^1[34578]\d{9}$/;/*/^1(3|4|5|7|8)\d{9}$/*/
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请输入正确的手机号码");
    alert($("#validUserNameUrl").val());
    $("#addForm").validate({
        rules: {
            uid: {
                "required": true,
                "maxlength": 4,
                "remote": {
                    url: $("#validUserNameUrl").val(),
                    type: 'get',
                    dataType: 'json',//{"userId":1234}
                    data: {
                        'userId': function () {
                            return $("#uid1").val();
                        }
                    }
                }
            },
            uname: "required",
            qq: "required",
            upassword: "required",
            usex: "required",
            unumber: {
                "required": true,
                "isMobile": true
            }
        },
        messages: {
            uid: {
                "required": "请输入用户名",
                "maxlength": "长度仅限于4位字符",
                "remote": "用户名已经存在"
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