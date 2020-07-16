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

    // 点击部门时
    $('#deptName').click(function () {
        var formName = encodeURIComponent("parent.UserInfoDlg.data.deptName");
        var formId = encodeURIComponent("parent.UserInfoDlg.data.deptId");
        var treeUrl = encodeURIComponent("/dept/tree");

        layer.open({
            type: 2,
            title: '部门选择',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#deptId").val(UserInfoDlg.data.deptId);
                $("#deptName").val(UserInfoDlg.data.deptName);
            }
        });
    });

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

    // 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/mgr/add", function (data) {
            console.log(data)
            if (data.message == 'codeError') {
                Feng.error("验证码错误！");
            }else if (data.message == 'phoneError') {
                Feng.error("手机号错误！");
            }else if (data.message == 'overTime') {
                Feng.error("验证码已过期！");
            }else {
                Feng.success("注册成功！");
                setTimeout(function () {
                    window.location.href = Feng.ctxPath + "/login";
                },1000);

            }

        }, function (data) {
            Feng.error("注册失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        //添加 return false 可成功跳转页面
        return false;
    });

    /*//初始化所有的职位列表
    formSelects.config('selPosition', {
        searchUrl: Feng.ctxPath + "/position/listPositions",
        keyName: 'name',
        keyVal: 'positionId'
    });*/







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

    $("#nextSubmit").on("click", function(){
        $.ajax({
            url: Feng.ctxPath +"/mgr/forgetPwdOne",
            async : true,
            type: "post",
            dataType: "text",
            data: "",
            success: function (data) {
                Feng.success("更新成功！");
                window.location.href = Feng.ctxPath + "/mgr/toForgetPwdOne";

            }
        });
    })


    form.on('submit(nextSubmit)', function (data) {
        $.ajax({
            url: Feng.ctxPath +"/mgr/forgetPwdOne",
            async : true,
            type: "post",
            dataType: "text",
            data: "",
            success: function (data) {
                Feng.success("更新成功！");
                window.location.href = Feng.ctxPath + "/mgr/toForgetPwdOne";

            }
        });
    });

    form.on('submit(passSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/mgr/forgetPwdTwo", function (data) {
            window.location.href = Feng.ctxPath + "/mgr/toForgetPwdTwo";
            /*console.log(data)
            if (data.message == 'codeError') {
                Feng.error("验证码错误！");
            }else if (data.message == 'phoneError') {
                Feng.error("手机号错误！");
            }else if (data.message == 'overTime') {
                Feng.error("验证码已过期！");
            }else {
                Feng.success("注册成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);

                //关掉对话框
                admin.closeThisDialog();
            }*/

        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        //添加 return false 可成功跳转页面
        return false;
    });
});