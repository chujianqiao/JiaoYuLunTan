/**
 * 详情对话框
 */
var SocialForumInfoDlg = {
    data: {
        forumId: "",
        forumName: "",
        forumDesc: "",
        applyStatus: "",
        unitName: "",
        unitPlace: "",
        manager: "",
        manaPhone: "",
        manaEmail: "",
        alreadyMeet: "",
        supPlate: "",
        supMoney: "",
        contractPath: "",
        applyTime: "",
        applyId: "",
        contractName: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var upload = layui.upload;


// 渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });












































    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/socialForum/detail?forumId=" + Feng.getUrlParam("forumId"));
    var result = ajax.start();
    form.val('socialForumForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (result.data.applyStatus == 0) {
            var ajax = new $ax(Feng.ctxPath + "/socialForum/editItem", function (data) {
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
        }else {
            Feng.success("请先取消申请再进行修改！");
        }

        return false;
    });

    //上传文件
    upload.render({
        elem: '#fileBtn'
        , url: Feng.ctxPath + '/socialForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#fileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#fileInputHidden").val(res.data.fileId);
            $("#contractPath").val(res.data.path);
            $("#contractName").val($("#fileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });


});