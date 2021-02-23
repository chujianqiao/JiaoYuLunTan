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


    form.on('radio(applyType)', function(data){

        if (data.value == 1){
            $("#manaEmailDiv").attr("style","display:block");
            $("#manaEmailDiv").children('div').children('input').attr("lay-verify","required|email");
            $("#postDiv").attr("style","display:block");
            $("#postDiv").children('div').children('input').attr("lay-verify","required");
            $("#managerDiv").children('label').html("姓名：<span style='color: red;'>*</span>");
            $("#managerDiv").children('div').children('input').attr("placeholder","请输入姓名");
        } else {
            $("#manaEmailDiv").attr("style","display:none");
            $("#manaEmailDiv").children('div').children('input').attr("lay-verify","");
            $("#postDiv").attr("style","display:none");
            $("#postDiv").children('div').children('input').attr("lay-verify","");
            $("#managerDiv").children('label').html("负责人：<span style='color: red;'>*</span>");
            $("#managerDiv").children('div').children('input').attr("placeholder","请输入负责人");
        }
    });


//表单提交事件
    form.on('submit(holdSubmit)', function (data) {
        var flag = 0;
        $("input:checkbox[name = ifAgreeHold]:checked").each(function(i){
            flag = 1;
        })
        if (flag == 1) {
            var ajax = new $ax(Feng.ctxPath + "/holdForum/addItem", function (data) {
                Feng.success("申报成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                Feng.error("申报失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        }else {
            Feng.error("请先阅读并同意《论坛章程》！");
        }

        return false;
    });

    //表单提交事件
    form.on('submit(ownSubmit)', function (data) {
        var flag = 0;
        $("input:checkbox[name = ifAgreeOwn]:checked").each(function(i){
            flag = 1;
        })
        if (flag == 1) {
            var ajax = new $ax(Feng.ctxPath + "/ownForum/addItem", function (data) {
                Feng.success("申报成功！");
                window.location.href = Feng.ctxPath + '/ownForum';
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                Feng.error("申报失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        }else {
            Feng.error("请先阅读并同意《论坛章程》！");
        }

        return false;
    });

    //表单提交事件
    form.on('submit(unitSubmit)', function (data) {
        var flag = 0;
        $("input:checkbox[name = ifAgreeOwnUnit]:checked").each(function(i){
            flag = 1;
        })
        if (flag == 1) {
            var ajax = new $ax(Feng.ctxPath + "/ownForum/addItem", function (data) {
                Feng.success("申报成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                Feng.error("申报失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        }else {
            Feng.error("请先阅读并同意《论坛章程》！");
        }

        return false;
    });

    //表单提交事件
    form.on('submit(socialSubmit)', function (data) {
        var supPlate = $("#supPlate").val();

        data.field.supPlate = supPlate;

        var flag = 0;
        $("input:checkbox[name = ifAgreeSocial]:checked").each(function(i){
            flag = 1;
        })
        if (flag == 1) {
            var ajax = new $ax(Feng.ctxPath + "/socialForum/addItem", function (data) {
                Feng.success("衷心感谢您携手中国教育科学论坛，</br>共谋中国教育改革与发展！");
                //传给上个页面，刷新table用
                setTimeout(function (){

                    window.location.href = Feng.ctxPath + "/socialForum"
                }, 3000);

            }, function (data) {
                Feng.error("申报失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        }else {
            Feng.error("请先阅读并同意《论坛章程》！");
        }

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
        elem: '#ownfileBtn'
        , url: Feng.ctxPath + '/ownForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#ownfileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#ownfileInputHidden").val(res.data.fileId);
            $("#ownplanPath").val(res.data.path);
            $("#ownplanName").val($("#ownfileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#unitfileBtn'
        , url: Feng.ctxPath + '/ownForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#unitfileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#unitfileInputHidden").val(res.data.fileId);
            $("#unitplanPath").val(res.data.path);
            $("#unitplanName").val($("#unitfileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#socialfileBtn'
        , url: Feng.ctxPath + '/socialForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#socialfileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#socialfileInputHidden").val(res.data.fileId);
            $("#contractPath").val(res.data.path);
            $("#contractName").val($("#socialfileNameTip").val());
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

    $('#owndownloadPlan').click(function () {
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        //form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/system/downloadTemp");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        input1.attr("type","hidden");
        input1.attr("name","name");    // 后台接收参数名
        input1.attr("value","论坛申报方案.docx");
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.submit();    // 表单提交

    });

    $('#unitdownloadPlan').click(function () {
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        //form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/system/downloadTemp");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        input1.attr("type","hidden");
        input1.attr("name","name");    // 后台接收参数名
        input1.attr("value","论坛申报方案.docx");
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.submit();    // 表单提交

    });

});