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



    // 添加表单验证方法
    form.verify({
        psw: [/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{8}$/, '密码必须由8位大小写字母加数字组合！'],
        repsw: function (value) {
            if (value !== $('#userForm input[name=password]').val()) {
                return '两次密码输入不一致';
            }
        }
    });

    // 渲染时间选择框
    laydate.render({
        elem: '#birthday'
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



    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/mgr/forgetPwdOne", function (data) {
            if (data.message == 'userError') {
                Feng.error("此账号尚未注册！");
            }else if (data.message == 'codeError') {
                Feng.error("验证码错误！");
            }else if (data.message == 'phoneError') {
                Feng.error("手机号错误！");
            }else if (data.message == 'userPhoneError') {
                Feng.error("此账号未绑定该手机号！");
            }else if (data.message == 'overTime') {
                Feng.error("验证码已过期！");
            }else {
                Feng.success("验证成功！");
                window.location.href = Feng.ctxPath + "/mgr/toForgetPwdOne?account=" + data.message
            }
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

});