layui.use(['layer', 'form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var admin = layui.admin;
    var $ax = layui.ax;

    // 让当前iframe弹层高度适应
    admin.iframeAuto();

    // 监听提交
    form.on('submit(submit-psw)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/mgr/changePwd", function (data) {
            Feng.success("修改成功!");
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.setData(data.field);
        ajax.start();

        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });

    // 添加表单验证方法
    form.verify({
        psw: [/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{8}$/, '密码必须由8位大小写字母加数字组合！'],
        repsw: function (t) {
            if (t !== $('#form-psw input[name=newPassword]').val()) {
                return '两次密码输入不一致';
            }
        }
    });
});