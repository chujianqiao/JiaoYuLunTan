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


    // 渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/mgr/phoneLogin", function (data) {
            console.log(data)
            if (data.message == 'codeError') {
                Feng.error("验证码错误！");
            } else if (data.message == 'phoneError') {
                Feng.error("手机号错误！");
            } else if (data.message == 'overTime') {
                Feng.error("验证码已过期！");
            } else if (data.message == 'error') {
                Feng.error("此手机号尚未绑定任何账号！");
            } else if (data.message == 'success'){
                Feng.success("登录成功！");
                // setTimeout(function () {
                //     //window.location.href = Feng.ctxPath + "/login";
                //     window.location.href = Feng.ctxPath + "/login"
                // },1000);
                parent.location.href = Feng.ctxPath + "/";
                admin.closeThisDialog();

            }

        }, function (data) {
            Feng.error("登录失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        //添加 return false 可成功跳转页面
        return false;
    });


    var countdownHandler = function(){
        var $button = $("#sentSmsCode");
        var number = 60;
        var countdown = function(){
            if (number == 0) {
                $button.attr("disabled",false);
                $button.html("发送验证码");
                number = 60;
                return;
            } else {
                $button.attr("disabled",true);
                $button.html(number + "秒后重新发送");
                number--;
            }
            setTimeout(countdown,1000);
        }
        setTimeout(countdown,1000);
    }
    //发送短信验证码
    $("#sentSmsCode").on("click", function(){
        var $phone = $("input[name=phone]");
        var data = {};
        data.phone = $.trim($phone.val());
        if(data.phone == ''){
            Feng.error('请输入手机号码');
            return;
        }
        var reg = /^1\d{10}$/;
        if(!reg.test(data.phone)){
            Feng.error('请输入合法的手机号码');
            return ;
        }
        $.ajax({
            url: Feng.ctxPath +"/check/checkSMS",
            async : true,
            type: "post",
            dataType: "text",
            data: data,
            success: function (data) {
                if(data != 'error'){
                    countdownHandler();
                    return ;
                }

            }
        });
    })


    $("#btnLogin").on("click", function(){
        admin.closeThisDialog();
    })
});