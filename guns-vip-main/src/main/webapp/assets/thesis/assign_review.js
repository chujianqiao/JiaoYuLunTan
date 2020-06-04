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

    //获取详情信息，填充表单
    debugger;
    var ajax = new $ax(Feng.ctxPath + "/thesis/detail?thesisId=" + Feng.getUrlParam("thesisId"));
    var result = ajax.start();
    form.val('thesisForm', result.data);
    // var ajaxMajor = new $ax(Feng.ctxPath + "/thesis/majorList");
    // var majors = ajaxMajor.start();

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/thesis/assignItem", function (data) {
            Feng.success("分配成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            // window.location.href = Feng.ctxPath + '/thesis'
        }, function (data) {
            Feng.error("分配失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    //初始化评审专家列表
    formSelects.config('selPosition', {
        searchUrl: Feng.ctxPath + "/thesis/majorList",
        keyName: 'name',
        keyVal: 'userId'
    });

});