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

    // 获取详情信息填充表单
    // var ajax = new $ax(Feng.ctxPath + "/meetMember/detail?memberId=" + Feng.getUrlParam("memberId"));
    // var result = ajax.start();
    // form.val('meetMemberForm', result.data);
    /**
     * 加载页面时执行
     */
    $(function(){
        var ajax = new $ax(Feng.ctxPath + "/meetMember/detail?memberId=" + Feng.getUrlParam("memberId"));
        var result = ajax.start();

        var thesisId = result.data.thesisId;
        // var thAjax = new $ax(Feng.ctxPath + "/thesis/detail?thesisId=" + thesisId);
        // var thResult = thAjax.start();
        // var thesisName = thResult.data.thesisTitle;
        // result.data.thesisName = thesisName;
        //填充表单
        form.val('meetMemberForm', result.data);
        var ownForumid = result.data.ownForumid;


        // thesisSelectOption(thesisId);

        var userId = result.data.userId;
        var userajax = new $ax(Feng.ctxPath + "/mgr/detail?userId=" + userId);
        var result = userajax.start();
        var title = result.data.title;
        if(title != '教授'){
            $('#professor').remove();
        }
    })


    /**
     * 构建论文下拉框候选值
     * @param thesisId
     */
    function thesisSelectOption(thesisId){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/thesis/list" ,
            success:function(response){
                debugger;
                var data=response.data;
                var theses = [];
                theses = data;

                var options;
                for (i = 0 ;i < theses.length ;i++){
                    var thesis = data[i];
                    if(thesisId == thesis.thesisId){
                        options += '<option value="'+ thesis.thesisId+ '" selected="selected">'+ thesis.thesisTitle +'</option>';
                    }else{
                        options += '<option value="'+ thesis.thesisId+ '" >'+ thesis.thesisTitle +'</option>';
                    }
                }
                $('#thesisId').empty();
                $('#thesisId').append(options);
                form.render('select');
            }
        })
    }


    // 下载论文附件
    $('#btnDownload').click(function () {
        debugger;
        var thesisId = $('#thesisId').val();
        downloadThesis(thesisId);
    });

    /**
     * 取消
     */
    $('#cancel').click(function () {
        window.location.href = window.location.href = Feng.ctxPath + '/meetMember';
    });

    function downloadThesis(thesisId){
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/meetMember/downloadThesis?thesisId=" + thesisId);    // 此处填写文件下载提交路径
        $("body").append(form);    // 将表单放置在web中
        form.submit();
    }

});