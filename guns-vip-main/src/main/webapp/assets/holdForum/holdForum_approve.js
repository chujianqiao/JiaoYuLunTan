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
        var ajax = new $ax(Feng.ctxPath + "/holdForum/approveForum", function (data) {
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
        var ajax = new $ax(Feng.ctxPath + "/holdForum/rejectForum", function (data) {
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
    $('#downloadCommit').click(function () {
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        //form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/system/download");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        input1.attr("type","hidden");
        input1.attr("name","name");    // 后台接收参数名
        input1.attr("value",$("#commitName").val());
        var input2=$("<input>");
        input2.attr("type","hidden");
        input2.attr("name","path");    // 后台接收参数名
        input2.attr("value",$("#commitPath").val());
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.append(input2);
        form.submit();    // 表单提交

    });

});