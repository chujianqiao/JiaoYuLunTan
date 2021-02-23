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



    var ajax = new $ax(Feng.ctxPath + "/socialLink/list");
    var result = ajax.start();
    console.log(result);
    for (var i = 0; i < result.count; i++){
        if (i!=0 && i%7==0){
            $("#divLink").append("<br><br>");
        }
        var input=$("<input>");
        input.attr("type","checkbox");
        input.attr("name","supPlates");
        input.attr("value",result.data[i].linkName);
        input.attr("title",result.data[i].linkName);
        $("#divLink").append(input);
    }
    form.render('checkbox');








































    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/socialForum/detail?forumId=" + Feng.getUrlParam("forumId"));
    var result = ajax.start();
    form.val('socialForumForm', result.data);
    var supPlate = result.data.supPlate;
    $("input:checkbox[name = supPlates]").each(function(i){
        //使用循环遍历迭代的方式得到所有被选中的checkbox复选框
        console.log($(this).val());
        console.log(supPlate);
        if (supPlate.indexOf($(this).val()) > -1) {
            $(this).attr("checked",true);
        }
    })
    form.render('checkbox');

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {

        var supPlate = "";
        $("input:checkbox[name = supPlates]:checked").each(function(i){
            //使用循环遍历迭代的方式得到所有被选中的checkbox复选框
            console.log($(this).val());
            supPlate = supPlate + $(this).val() + ";";
        })
        data.field.supPlate = supPlate;

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

        return false;
    });

    //上传文件
    upload.render({
        elem: '#fileBtn'
        , url: Feng.ctxPath + '/socialForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#fileNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#fileInputHidden").val(res.data.fileId);
            $("#contractPath").val(res.data.path);
            $("#contractName").val($("#fileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });


});