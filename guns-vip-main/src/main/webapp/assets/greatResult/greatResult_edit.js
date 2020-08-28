/**
 * 详情对话框
 */
var GreatResultInfoDlg = {
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
        belongName: "",
        letterName: "",
        commitName: "",
        introducePath: "",
        introduceName: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/greatResult/detail?resultId=" + Feng.getUrlParam("resultId"));
    var result = ajax.start();
    form.val('greatResultForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (result.data.checkStatus == 0) {
            var ajax = new $ax(Feng.ctxPath + "/greatResult/editItem", function (data) {
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
        elem: '#introducefileBtn'
        , url: Feng.ctxPath + '/greatResult/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#introducefileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#introducefileInputHidden").val(res.data.fileId);
            $("#introducePath").val(res.data.path);
            $("#introduceName").val($("#introducefileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#letterfileBtn'
        , url: Feng.ctxPath + '/greatResult/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#letterfileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#letterfileInputHidden").val(res.data.fileId);
            $("#letterPath").val(res.data.path);
            $("#letterName").val($("#letterfileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#commitfileBtn'
        , url: Feng.ctxPath + '/greatResult/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#commitfileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#commitfileInputHidden").val(res.data.fileId);
            $("#commitPath").val(res.data.path);
            $("#commitName").val($("#commitfileNameTip").val());
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
        input1.attr("value","优秀成果简介.docx");
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.submit();    // 表单提交

    });
});