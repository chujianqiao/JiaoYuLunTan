/**
 * 角色详情对话框
 */
var DomainInfoDlg = {
    data: {
        pid: "",
        pName: ""
    }
};

/**
 * 详情对话框
 */
var ThesisDomainInfoDlg = {
    data: {
        domainId: "",
        pid: "",
        pids: "",
        domainName: "",
        description: "",
        version: "",
        sort: "",
        createTime: "",
        updateTime: "",
        createUser: "",
        updateUser: ""
    }
};

layui.use(['layer', 'form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/thesisDomain/detail?domainId=" + Feng.getUrlParam("domainId"));
    var result = ajax.start();
    form.val('thesisDomainForm', result.data);

    // 点击上级角色时
    $('#pName').click(function () {
        var formName = encodeURIComponent("parent.DomainInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.DomainInfoDlg.data.pid");
        var treeUrl = encodeURIComponent("/thesisDomain/tree");

        layer.open({
            type: 2,
            title: '论文领域',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#pid").val(DomainInfoDlg.data.pid);
                $("#pName").val(DomainInfoDlg.data.pName);
            }
        });
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/thesisDomain/editItem", function (data) {
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