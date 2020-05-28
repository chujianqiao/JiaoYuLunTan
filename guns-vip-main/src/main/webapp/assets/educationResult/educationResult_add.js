/**
 * 添加或者修改页面
 */
var EducationResultInfoDlg = {
    data: {
        resultId: "",
        resultName: "",
        applyType: "",
        manager: "",
        manaPhone: "",
        manaEmail: "",
        manaPost: "",
        manaDirect: "",
        resultMean: "",
        value: "",
        range: "",
        object: "",
        team: "",
        conclusion: "",
        introduce: "",
        influence: "",
        slogan: "",
        designImg: "",
        keyword: "",
        letterPath: "",
        commitPath: "",
        form: "",
        detail: "",
        checkStatus: "",
        applyId: "",
        applyTime: "",
        refuseTime: "",
        passTime: "",
        cancelTime: "",
        belongName: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

























































































    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/educationResult/addItem", function (data) {
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