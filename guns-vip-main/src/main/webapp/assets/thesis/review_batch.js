/**
 * 详情对话框
 */
var ThesisInfoDlg = {
    data: {
        thesisId: "",
        thesisTitle: "",
        engTitle: "",
        thesisUser: "",
        status: "",
        reviewResult: "",
        isgreat: "",
        greatNum: "",
        greatId: "",
        applyTime: "",
        thesisText: "",
        score: "",
        reviewUser: "",
        great: "",
        cnKeyword: "",
        engKeyword: "",
        cnAbstract: "",
        engAbstract: "",
        thesisDirect: "",
        thesisPath: "",
        fileName: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects','upload','selectPlus', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var table = layui.table;
    var selectPlus = layui.selectPlus;
    var formSelects = layui.formSelects;



    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/thesis/reviewAdmin", function (data) {
            Feng.success("评审成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            // window.location.href = Feng.ctxPath + '/thesis'
        }, function (data) {
            Feng.error("评审失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.set("thesisIds",Feng.getUrlParam("thesisId"));
        ajax.set("finalResult",2);
        ajax.start();
        return false;

    });

});