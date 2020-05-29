/**
 * 添加或者修改页面
 */
var ReviewMajorInfoDlg = {
    data: {
        reviewId: "",
        direct: "",
        thesisCount: "",
        reviewCount: "",
        refuseCount: "",
        majorType: "",
        applyFrom: "",
        checkStatus: "",
        applyTime: "",
        agreeTime: "",
        refuseTime: "",
        cancelTime: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;



































    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var flag = 0;
        $("input:checkbox[name = ifAgree]:checked").each(function(i){
            flag = 1;
        })
        if (flag == 1) {
            var ajax = new $ax(Feng.ctxPath + "/reviewMajor/addItem", function (data) {
                Feng.success("添加成功！");
                window.location.href = Feng.ctxPath + '/'
            }, function (data) {
                Feng.error("添加失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        }else {
            Feng.error("请先阅读并同意《论坛章程》！");
        }

        return false;
    });


});