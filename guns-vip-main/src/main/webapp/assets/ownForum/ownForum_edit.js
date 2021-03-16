/**
 * 详情对话框
 */
var OwnForumInfoDlg = {
    data: {
        forumId: "",
        forumName: "",
        forumDesc: "",
        applyType: "",
        applyStatus: "",
        manager: "",
        manaPhone: "",
        manaEmail: "",
        subId: "",
        issubject: "",
        subjectLev: "",
        subjectName: "",
        planPath: "",
        relation: "",
        meaning: "",
        expertName: "",
        staffType: "",
        orgType: "",
        dividePath: "",
        agreeRule: "",
        applyId: "",
        applyTime: "",
        divideName: "",
        planName: "",
        forumTopic: "",
        forumSize: "",
        unitName: "",
        direction: "",
        title: "",
        post: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var laydate = layui.laydate;
    var nowTime = new Date().valueOf();



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



    // 渲染时间选择框
    var start = laydate.render({
        elem: '#startTime'
        ,type: 'datetime'
        ,min:nowTime,
        done:function(value,date){
            endMax = end.config.max;
            end.config.min = date;
            end.config.min.month = date.month -1;
        }
    });

    // 渲染时间选择框
    var end = laydate.render({
        elem: '#endTime'
        ,type: 'datetime'
        ,min : nowTime,
        done:function(value,date){
            if($.trim(value) == ''){
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
            }
            start.config.max = date;
            start.config.max.month = date.month -1;
        }
    });



    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/ownForum/detail?forumId=" + Feng.getUrlParam("forumId"));
    var result = ajax.start();
    form.val('ownForumForm', result.data);
    if (result.data.applyType == 1){
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

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (result.data.applyStatus == 0){
            var ajax = new $ax(Feng.ctxPath + "/ownForum/editItem", function (data) {
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

    //表单提交事件
    form.on('submit(setSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/ownForum/editItem", function (data) {
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

    //上传文件
    upload.render({
        elem: '#fileBtn'
        , url: Feng.ctxPath + '/ownForum/upload'
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


    $('#downloadPlan').click(function () {
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        //form.attr("target","_blank");
        form.attr("method","post");
        //form.attr("action",Feng.ctxPath + "/system/downloadTemp");    // 此处填写文件下载提交路径
        form.attr("action",Feng.ctxPath + "/system/download");
        var input1=$("<input>");
        input1.attr("type","hidden");
        input1.attr("name","name");    // 后台接收参数名
        input1.attr("value","论坛申报方案.docx");
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.submit();    // 表单提交

    });

});