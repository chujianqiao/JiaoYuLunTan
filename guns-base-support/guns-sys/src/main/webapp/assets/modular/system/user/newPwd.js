/**
 * 用户详情对话框
 */
var UserInfoDlg = {
    data: {
        deptId: "",
        deptName: ""
    }
};

layui.use(['layer', 'form', 'admin', 'laydate', 'ax', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;
    var formSelects = layui.formSelects;
    var isPasswordOk = false;
    var isRePasswordOk = false;

    // 添加表单验证方法
    form.verify({
        psw: [/(?!.*[\u4E00-\u9FA5\s])(?!^[a-zA-Z]+$)(?!^[\d]+$)(?!^[^a-zA-Z\d]+$)^.{8,14}$/, '8~14位字母/数字以及标点符号至少包含2种，不允许有空格、中文！'],
        repsw: function (value) {
            if (value !== $('#userForm input[name=password]').val()) {
                return '两次密码输入不一致';
            }
        }
    });



    form.on('submit(btnSubmit)', function (data) {
        if (isPasswordOk && isRePasswordOk){
            var ajax = new $ax(Feng.ctxPath + "/mgr/forgetPwdTwo", function (data) {
                if (data.message != 'error') {
                    sentWeiXinMessage(data.message);
                    Feng.success("设置成功！");
                    window.location.href = Feng.ctxPath + "/mgr/toForgetPwdTwo";
                }else {
                    Feng.error("设置失败！");
                }
            }, function (data) {
                Feng.error("更新失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();

            return false;
        }
    });

    function sentWeiXinMessage(message) {
        var ajax = new $ax(Feng.ctxPath + "/weiXin/sentWeiXinMessage", function (data) {
        }, function (data) {
        });
        ajax.set("message",message);
        ajax.start();

        return false;
    }

    var passwordtips;
    $("#password").focus(function() {
        passwordtips = layer.tips('8~14位字母/数字以及标点符号至少包含2种，不允许有空格、中文', '#password',{tips:[1,'#000'],time: 30000});
    })
    $("#password").blur(function() {
        layer.close(passwordtips);
        var pass = this.value;
        var passTest = /(?!.*[\u4E00-\u9FA5\s])(?!^[a-zA-Z]+$)(?!^[\d]+$)(?!^[^a-zA-Z\d]+$)^.{8,14}$/;
        /*if ($("#rePassword").val() != $("#password").val() && $("#rePassword").val() != "" && $("#password").val() != "") {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '两次密码不一致';
            // 设置成功的标志
            isPasswordOk = false;
            ifCanSub();
        }else {*/
            if (passTest.test(pass)) {
                // 设置成功信息
                var span = this.nextElementSibling;
                span.className = 'success';
                span.innerHTML = '√';
                // 设置成功的标志
                isPasswordOk = true;
                ifCanSub();
            }else {
                // 设置成功信息
                var span = this.nextElementSibling;
                span.className = 'error';
                span.innerHTML = '密码设置不符合要求';
                // 设置成功的标志
                isPasswordOk = false;
                ifCanSub();
            }
        /*}*/

    })

    $("#rePassword").blur(function() {
        var pass = this.value;
        var passTest = /(?!.*[\u4E00-\u9FA5\s])(?!^[a-zA-Z]+$)(?!^[\d]+$)(?!^[^a-zA-Z\d]+$)^.{8,14}$/;

        if ($("#password").val() == ""){
        } else if ($("#password").val() != "" && $("#rePassword").val() == ""){
        } else if ($("#password").val() != "" && $("#rePassword").val() != ""){
            if ($("#rePassword").val() != $("#password").val()) {
                // 设置成功信息
                var span = this.nextElementSibling;
                span.className = 'error';
                span.innerHTML = '两次密码不一致';
                // 设置成功的标志
                isRePasswordOk = false;
                ifCanSub();
            }else {
                if (passTest.test(pass)) {
                    // 设置成功信息
                    var span = this.nextElementSibling;
                    span.className = 'success';
                    span.innerHTML = '√';
                    // 设置成功的标志
                    isRePasswordOk = true;
                    ifCanSub();
                }else {
                    // 设置成功信息
                    var span = this.nextElementSibling;
                    span.className = 'error';
                    span.innerHTML = '密码设置不符合要求';
                    // 设置成功的标志
                    isRePasswordOk = false;
                    ifCanSub();
                }
            }
        }

    })

    function ifCanSub() {
        if (isPasswordOk && isRePasswordOk){
            $("#btnSubmit").attr('disabled',false);
            $("#btnSubmit").removeClass("layui-bg-gray");
        }else {
            $("#btnSubmit").attr('disabled',true);
            $("#btnSubmit").addClass("layui-bg-gray");
        }
    }
});