/**
 * 添加或者修改页面
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
    });



    //渲染时间选择框
    var laydate = layui.laydate;
    laydate.render({
        elem: '#endTime' //指定元素
    });






































    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/meet/addItem", function (data) {
            Feng.success("添加成功！");
            window.location.href = Feng.ctxPath + '/meet'
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/meet'
    });

});