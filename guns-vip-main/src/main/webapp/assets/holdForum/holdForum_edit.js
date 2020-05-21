/**
 * 详情对话框
 */
var HoldForumInfoDlg = {
    data: {
        forumId: "",
        forumName: "",
        forumDesc: "",
        applyStatus: "",
        unitName: "",
        unitDesc: "",
        level: "",
        year: "",
        ability: "",
        topic: "",
        manager: "",
        manaPhone: "",
        manaEmail: "",
        orgSup: "",
        fundsSup: "",
        staffSup: "",
        experience: "",
        agreeRule: "",
        planPath: "",
        commitPath: "",
        applyTime: "",
        applyUser: ""
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
    var ajax = new $ax(Feng.ctxPath + "/holdForum/detail?forumId=" + Feng.getUrlParam("forumId"));
    var result = ajax.start();
    form.val('holdForumForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (result.data.applyStatus == 0){
            var ajax = new $ax(Feng.ctxPath + "/holdForum/editItem", function (data) {
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
        console.log(result.data);

        return false;
    });


    //上传文件
    upload.render({
        elem: '#fileBtn'
        , url: Feng.ctxPath + '/holdForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#fileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#fileInputHidden").val(res.data.fileId);
            $("#planPath").val(res.data.path);
            $("#planName").val($("#fileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#commitFileBtn'
        , url: Feng.ctxPath + '/holdForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#commitFileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#commitFileInputHidden").val(res.data.fileId);
            $("#commitPath").val(res.data.path);
            $("#commitName").val($("#commitFileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });


    $('#downloadPlan').click(function () {
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        //form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/system/downloadTemp");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        input1.attr("type","hidden");
        input1.attr("name","name");    // 后台接收参数名
        input1.attr("value","承办方案.docx");
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.submit();    // 表单提交

    });
});