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
        applyUser: "",
        belongDomain: "",
        pName: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var upload = layui.upload;

    $(function () {
        //domainSelectOption();
    })

    // 渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });


    form.on('radio(applyType)', function(data){

        if (data.value == 1){
            $("#manaEmailDiv").attr("style","display:block")
            $("#manaEmailDiv").children('div').children('input').attr("lay-verify","required|email");
            $("#manaPostDiv").attr("style","display:block")
            $("#manaPostDiv").children('div').children('input').attr("lay-verify","required");
            $("#manaDirectDiv").attr("style","display:block")
            $("#manaDirectDiv").children('div').children('input').attr("lay-verify","required");
            $("#belongNameDiv").children('label').html("申请人姓名：<span style='color: red;'>*</span>");
            $("#belongNameDiv").children('div').children('input').attr("placeholder","请输入申请人姓名");
            $("#manaPhoneDiv").children('label').html("申请人电话：<span style='color: red;'>*</span>");
            $("#manaPhoneDiv").children('div').children('input').attr("placeholder","请输入申请人电话");
            $("#teamDiv").children('label').html("所在单位：<span style='color: red;'>*</span>");
            $("#teamDiv").children('div').children('input').attr("placeholder","请输入所在单位");
        } else {
            $("#manaEmailDiv").attr("style","display:none")
            $("#manaEmailDiv").children('div').children('input').attr("lay-verify","");
            $("#manaPostDiv").attr("style","display:none")
            $("#manaPostDiv").children('div').children('input').attr("lay-verify","");
            $("#manaDirectDiv").attr("style","display:none")
            $("#manaDirectDiv").children('div').children('input').attr("lay-verify","");
            $("#belongNameDiv").children('label').html("负责人：<span style='color: red;'>*</span>");
            $("#belongNameDiv").children('div').children('input').attr("placeholder","请输入负责人");
            $("#manaPhoneDiv").children('label').html("联系电话：<span style='color: red;'>*</span>");
            $("#manaPhoneDiv").children('div').children('input').attr("placeholder","请输入联系电话");
            $("#teamDiv").children('label').html("单位名称：<span style='color: red;'>*</span>");
            $("#teamDiv").children('div').children('input').attr("placeholder","请输入单位名称");
        }
    });



    //表单提交事件
    form.on('submit(personSubmit)', function (data) {
        var flag = 0;
        $("input:checkbox[name = ifAgree]:checked").each(function(i){
            flag = 1;
        })
        if (flag == 1) {
            if (data.field.resultType == 1) {
                var ajax = new $ax(Feng.ctxPath + "/greatResult/addItem", function (data) {
                    Feng.success("申报成功！");
                    window.location.href = Feng.ctxPath + '/greatResult';
                    //传给上个页面，刷新table用
                    admin.putTempData('formOk', true);
                    //关掉对话框
                    admin.closeThisDialog();
                }, function (data) {
                    Feng.error("申报失败！" + data.responseJSON.message)
                });
                ajax.set(data.field);
                ajax.start();
            } else {
                var ajax = new $ax(Feng.ctxPath + "/educationResult/addItem", function (data) {
                    Feng.success("申报成功！");
                    window.location.href = Feng.ctxPath + '/greatResult';
                    //传给上个页面，刷新table用
                    admin.putTempData('formOk', true);
                    //关掉对话框
                    admin.closeThisDialog();
                }, function (data) {
                    Feng.error("申报失败！" + data.responseJSON.message)
                });
                ajax.set(data.field);
                ajax.start();
            }
        }else {
            Feng.error("请先阅读并同意《论坛章程》！");
        }
        return false;
    });

    //表单提交事件
    /*form.on('submit(unitSubmit)', function (data) {
        var flag = 0;
        $("input:checkbox[name = ifAgree]:checked").each(function(i){
            flag = 1;
        })
        if (flag == 1) {
            if (data.field.resultType == 1) {
                var ajax = new $ax(Feng.ctxPath + "/greatResult/addItem", function (data) {
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
            } else {
                var ajax = new $ax(Feng.ctxPath + "/educationResult/addItem", function (data) {
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
            }
        }else {
            Feng.error("请先阅读并同意《论坛章程》！");
        }
        return false;
    });*/




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


    //上传文件
    upload.render({
        elem: '#unitintroducefileBtn'
        , url: Feng.ctxPath + '/educationResult/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#unitintroducefileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#unitintroducefileInputHidden").val(res.data.fileId);
            $("#unitintroducePath").val(res.data.path);
            $("#unitintroduceName").val($("#unitintroducefileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#unitletterfileBtn'
        , url: Feng.ctxPath + '/educationResult/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#unitletterfileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#unitletterfileInputHidden").val(res.data.fileId);
            $("#unitletterPath").val(res.data.path);
            $("#unitletterName").val($("#unitletterfileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#unitcommitfileBtn'
        , url: Feng.ctxPath + '/educationResult/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#unitcommitfileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#unitcommitfileInputHidden").val(res.data.fileId);
            $("#unitcommitPath").val(res.data.path);
            $("#unitcommitName").val($("#unitcommitfileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
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
        input1.attr("value","优秀成果简介.docx");
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.submit();    // 表单提交

    });

    /*function domainSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/thesisDomain/list" ,
            success:function(response){
                var data=response.data;
                var domains = [];
                domains = data;

                var options;
                for (var i = 0 ;i < domains.length ;i++){
                    var domain = data[i];
                    options += '<option value="'+ domain.domainId+ '" >'+ domain.domainName +'</option>';
                }
                $('#belongDomain').empty();
                $('#belongDomain').append("<option value=''>请选择成果领域</option>");
                $('#belongDomain').append(options);
                form.render('select');
            }
        })
    }*/

    // 点击上级角色时
    $('#pName').click(function () {
        var formName = encodeURIComponent("parent.HoldForumInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.HoldForumInfoDlg.data.belongDomain");
        var treeUrl = encodeURIComponent("/thesisDomain/tree");

        layer.open({
            type: 2,
            title: '父级领域',
            area: ['300px', '400px'],
            //content: Feng.ctxPath + '/thesisDomain/thesisDomainAssign?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#belongDomain").val(HoldForumInfoDlg.data.belongDomain);
                $("#pName").val(HoldForumInfoDlg.data.pName);
            }
        });
    });
});