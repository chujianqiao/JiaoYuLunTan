/**
 * 详情对话框
 */
var MeetInfoDlg = {
    data: {
        meetId: "",
        meetTopic: "",
        meetDesc: "",
        place: "",
        beginTime: "",
        endTime: "",
        applyUser: "",
        registerTime: "",
        subForm: "",
        thesisId: "",
        thesisTitle: "",
        engTitle: "",
        cnKeyword: "",
        engKeyword: "",
        cnAbstract: "",
        engAbstract: "",
        thesisDirect: "",
        speak: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //渲染时间选择框
    var laydate = layui.laydate;
    laydate.render({
        elem: '#beginTime' //指定元素
        ,type: 'datetime'
    });

    //渲染时间选择框
    var laydate = layui.laydate;
    laydate.render({
        elem: '#endTime' //指定元素
        ,type: 'datetime'
    });

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/meet/detail?meetId=" + Feng.getUrlParam("meetId"));
    debugger;
    var result = ajax.start();
    var speak = result.data.speak;
    if(speak == 0){
        result.data.speak = "否";
    }else if(speak == 1){
        result.data.speak = "是";
    }
    form.val('meetForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/meet/editItem", function (data) {
            Feng.success("更新成功！");
            window.location.href = Feng.ctxPath + '/meet'
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/meet'
    });
});