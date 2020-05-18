/**
 * 详情对话框
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

























































































    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/educationResult/detail?resultId=" + Feng.getUrlParam("resultId"));
    var result = ajax.start();
    form.val('educationResultForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/educationResult/editItem", function (data) {
            Feng.success("更新成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

});