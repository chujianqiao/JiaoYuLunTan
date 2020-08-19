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









































    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/socialForum'
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

    // 添加按钮点击事件
    $('#downloadContract').click(function () {
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        //form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/system/download");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        input1.attr("type","hidden");
        input1.attr("name","name");    // 后台接收参数名
        input1.attr("value",$("#contractName").val());
        var input2=$("<input>");
        input2.attr("type","hidden");
        input2.attr("name","path");    // 后台接收参数名
        input2.attr("value",$("#contractPath").val());
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.append(input2);
        form.submit();    // 表单提交

    });


});