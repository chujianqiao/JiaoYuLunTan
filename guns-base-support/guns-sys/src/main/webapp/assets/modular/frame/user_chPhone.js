layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var admin = layui.admin;
    var $ax = layui.ax;

    var isPhoneOk = false;
    // 让当前iframe弹层高度适应
    admin.iframeAuto();

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
        var reg = /^1[34578]\d{9}$/;
        if(!reg.test(data.phone)){
            Feng.error('请输入合法的手机号码');
            return ;
        }
        if (isPhoneOk) {
            $.ajax({
                url: Feng.ctxPath + "/check/checkSMS",
                async: true,
                type: "post",
                dataType: "text",
                data: data,
                success: function (data) {
                    if (data != 'error') {
                        countdownHandler();
                        return;
                    }

                }
            });
        }
    })

    $("#phone").blur(function() {
        var phone = this.value.trim();
        var phoneTest = /^1[34578]\d{9}$/;
        if (phoneTest.test(phone)) {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'success';
            span.innerHTML = '√';
            // 设置成功的标志
            isPhoneOk = true;

            var ajax = new $ax(Feng.ctxPath + "/mgr/addCheck", function (data) {
                console.log(data)
                if (data.message == 'existPhone') {
                    // 设置错误信息
                    span.className = 'error';
                    span.innerHTML = '此手机号已被绑定,请更换一个';
                    // 设置用户名标记为false
                    isPhoneOk = false;
                    // 终止程序
                    return;
                }else {
                    // 设置成功信息
                    span.className = 'success';
                    span.innerHTML = '√';
                    // 设置成功的标志
                    isPhoneOk = true;
                }

            }, function (data) {
            });
            ajax.set("phone",this.value);
            ajax.start();
        }else {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '手机号码格式不正确';
            // 设置成功的标志
            isPhoneOk = false;
        }
    })

    // 监听提交
    form.on('submit(submit-psw)', function (data) {
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        var ajax = new $ax(Feng.ctxPath + "/mgr/changePhone", function (data) {
            if (data.message == 'codeError') {
                Feng.error("验证码错误！");
            }else if (data.message == 'phoneError') {
                Feng.error("手机号错误！");
            }else if (data.message == 'userError') {
                Feng.error("此手机号已被绑定，请更换手机号！");
            }else if (data.message == 'overTime') {
                Feng.error("验证码已过期！");
            }else {
                Feng.success("修改成功！");
                admin.closeThisDialog();
                parent.location.reload();
            }
        }, function (data) {
            Feng.error("修改失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    // 添加表单验证方法
    form.verify({
        psw: [/(?!.*[\u4E00-\u9FA5\s])(?!^[a-zA-Z]+$)(?!^[\d]+$)(?!^[^a-zA-Z\d]+$)^.{8,14}$/, '8~14位字母/数字以及标点符号至少包含2种，不允许有空格、中文！'],
        repsw: function (t) {
            if (t !== $('#form-psw input[name=newPassword]').val()) {
                return '两次密码输入不一致';
            }
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
});