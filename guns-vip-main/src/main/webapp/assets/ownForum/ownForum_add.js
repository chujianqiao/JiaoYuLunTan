/**
 * 添加或者修改页面
 */
var OwnForumInfoDlg = {
    data: {
        forumId: "",
        forumName: "",
        forumDesc: "",
        applyType: "",
        applyStatus: "",
        manager: "",
        manaPhone: "",
        manaEmail: "",
        subId: "",
        issubject: "",
        subjectLev: "",
        subjectName: "",
        planPath: "",
        relation: "",
        meaning: "",
        expertName: "",
        staffType: "",
        orgType: "",
        dividePath: "",
        agreeRule: "",
        applyId: "",
        applyTime: "",
        divideName: "",
        planName: "",
        forumTopic: "",
        forumSize: "",
        unitName: "",
        direction: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;



















































































    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/ownForum/addItem", function (data) {
            Feng.success("添加成功！");
            window.location.href = Feng.ctxPath + '/'
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });


});