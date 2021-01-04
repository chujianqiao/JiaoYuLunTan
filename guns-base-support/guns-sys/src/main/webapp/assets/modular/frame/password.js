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
            sentWeiXinMessage("");
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