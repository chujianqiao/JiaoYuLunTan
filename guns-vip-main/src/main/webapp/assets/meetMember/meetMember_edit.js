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

    debugger;
    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/meetMember/detail?memberId=" + Feng.getUrlParam("memberId"));
    var result = ajax.start();
    form.val('meetMemberForm', result.data);

    /**
     * 加载页面时执行
     */
    $(function(){
        var ajax = new $ax(Feng.ctxPath + "/meetMember/detail?memberId=" + Feng.getUrlParam("memberId"));
        var result = ajax.start();
        var ownForumid = result.data.ownForumid;
        forumSelectOption(ownForumid);

        debugger;
        var thesisId = result.data.thesisId;
        thesisSelectOption(thesisId);

        var userId = result.data.userId;
        var userajax = new $ax(Feng.ctxPath + "/mgr/detail?userId=" + userId);
        var result = userajax.start();
        var title = result.data.title;
        if(title != '教授'){
            $('#professor').remove();
        }
    })

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/meetMember/editItem", function (data) {
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

    /**
     * 构建论坛下拉框候选值
     * @param ownForumid
     */
    function forumSelectOption(ownForumid){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/ownForum/listAll" ,
            success:function(response){
                var data=response.data;
                var forums = [];
                forums = data;

                var options;
                for (i = 0 ;i < forums.length ;i++){
                    var forum = data[i];
                    if(ownForumid == forum.forumId){
                        options += '<option value="'+ forum.forumId+ '" selected="selected">'+ forum.forumName +'</option>';
                    }else{
                        options += '<option value="'+ forum.forumId+ '" >'+ forum.forumName +'</option>';
                    }
                }
                $('#ownForumid').empty();
                $('#ownForumid').append(options);
                form.render('select');
            }
        })
    }

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
        var thesisId = $('#thesisId').val();
        downloadThesis(thesisId);
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