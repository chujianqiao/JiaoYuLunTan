/**
 * 详情对话框
 */
var MeetMemberInfoDlg = {
    data: {
        memberId: "",
        userId: "",
        thesisId: "",
        speak: "",
        judge: "",
        ownForumid: "",
        regTime: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;

    /**
     * 加载页面时执行
     */
    $(function(){
        var ajax = new $ax(Feng.ctxPath + "/meetMember/detail?memberId=" + Feng.getUrlParam("memberId"));
        var result = ajax.start();
        //填充表单
        form.val('meetMemberForm', result.data);
        var pptName = result.data.pptName;
        if(pptName != null && pptName != "" && pptName != 'undefined'){
            $("#pptName").html(pptName);
            $("#pptName").val(pptName);
        }
        var wordName = result.data.wordName;
        if(wordName != null && wordName != "" && wordName != 'undefined'){
            $("#wordName").html(wordName);
            $("#wordName").val(wordName);
        }
    })

    /**
     * 下载嘉宾PPT
     */
    $('#downloadPPT').click(function () {
        debugger;
        var memberId = $('#memberId').val();
        var pptName = $('#pptName').val();
        if(pptName != null && pptName != "" && pptName != 'undefined'){
            downloadPPT(memberId);
        }else {
            Feng.error("未发现文件");
        }
    });

    /**
     * 下载嘉宾发言稿
     */
    $('#downloadWord').click(function () {
        var memberId = $('#memberId').val();
        var wordName = $('#wordName').val();
        if(wordName != null && wordName != "" && wordName != 'undefined'){
            downloadWord(memberId);
        }else {
            Feng.error("未发现文件");
        }
    });

    /**
     * 取消
     */
    $('#cancelGuest').click(function () {
        window.location.href = window.location.href = Feng.ctxPath + '/meetMember';
    });

    function downloadPPT(memberId){
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        // form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/meetMember/downloadGuestPDF?memberId=" + memberId);    // 此处填写文件下载提交路径
        $("body").append(form);    // 将表单放置在web中
        form.submit();
    }

    function downloadWord(memberId){
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        // form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/meetMember/downloadGuestWord?memberId=" + memberId);    // 此处填写文件下载提交路径
        $("body").append(form);    // 将表单放置在web中
        form.submit();
    }

});