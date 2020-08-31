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



    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/ownForum'
    });


    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/ownForum/detail?forumId=" + Feng.getUrlParam("forumId"));
    var result = ajax.start();
    form.val('ownForumForm', result.data);
    if (result.data.applyType == 1){
        $("#manaEmailDiv").attr("style","display:block")
        $("#postDiv").attr("style","display:block")
        $("#managerDiv").children('label').html("姓名：");
        $("#managerDiv").children('div').children('input').attr("placeholder","请输入姓名");
    } else {
        $("#manaEmailDiv").attr("style","display:none")
        $("#postDiv").attr("style","display:none")
        $("#managerDiv").children('label').html("负责人：");
        $("#managerDiv").children('div').children('input').attr("placeholder","请输入负责人");
    }

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/ownForum/approveForum", function (data) {
            Feng.success("通过申请成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("通过申请失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    //表单提交事件
    form.on('submit(rejSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/ownForum/rejectForum", function (data) {
            Feng.success("驳回申请成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("驳回申请失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    // 添加按钮点击事件
    $('#downloadPlan').click(function () {
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        //form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/system/download");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        input1.attr("type","hidden");
        input1.attr("name","name");    // 后台接收参数名
        input1.attr("value",$("#planName").val());
        var input2=$("<input>");
        input2.attr("type","hidden");
        input2.attr("name","path");    // 后台接收参数名
        input2.attr("value",$("#planPath").val());
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.append(input2);
        form.submit();    // 表单提交

    });

});