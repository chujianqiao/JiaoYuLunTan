/**
 * 详情对话框
 */
var ThesisInfoDlg = {
    data: {
        thesisId: "",
        thesisTitle: "",
        engTitle: "",
        thesisUser: "",
        status: "",
        reviewResult: "",
        isgreat: "",
        greatNum: "",
        greatId: "",
        applyTime: "",
        thesisText: "",
        score: "",
        reviewUser: "",
        great: "",
        cnKeyword: "",
        engKeyword: "",
        cnAbstract: "",
        engAbstract: "",
        thesisDirect: "",
        thesisPath: "",
        fileName: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var table = layui.table;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/thesis/detail?thesisId=" + Feng.getUrlParam("thesisId"));
    var result = ajax.start();
    form.val('thesisForm', result.data);

    //表单提交事件
    /**
     * 复评
     */
    form.on('submit(btnReviewAgain)', function (data) {
        debugger;
        var score = data.field.score;
        if(score >= 0 && score <=100){
            var ajax = new $ax(Feng.ctxPath + "/thesis/reviewItemAgain", function (data) {
                Feng.success("提交评审结果成功！");
                window.location.href = Feng.ctxPath + '/thesis'
            }, function (data) {
                Feng.error("提交评审结果失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        }else{
            Feng.error("评分区间 0~100分");
        }
        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/thesis'
    });

    $('#downloadBtn').click(function(){
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        // form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/meetMember/downloadThesis?thesisId=" + Feng.getUrlParam("thesisId"));    // 此处填写文件下载提交路径
        $("body").append(form);    // 将表单放置在web中
        form.submit();

    });

    $('#loadBtn').click(function(){
        window.open("/assets/pdfview/web/viewer.html?file=" + encodeURIComponent("/meetMember/loadThesisPdf?thesisId=" + Feng.getUrlParam("thesisId")));

    });

});