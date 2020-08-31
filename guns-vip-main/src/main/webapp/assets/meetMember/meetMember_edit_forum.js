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
    var ajax = new $ax(Feng.ctxPath + "/meetMember/checkDetail?memberId=" + Feng.getUrlParam("memberId"));
    var result = ajax.start();
    form.val('meetMemberForm', result.data);


    /**
     * 加载页面时执行
     */
    $(function(){
        var ajax = new $ax(Feng.ctxPath + "/meetMember/checkDetail?memberId=" + Feng.getUrlParam("memberId"));
        var result = ajax.start();
        var ownForumid = result.data.ownForumid;

        //构建论坛候选值
        forumSelectOption(ownForumid);

        var thesisId = result.data.thesisId;

        // thesisSelectOption(thesisId);

        // var userId = result.data.userId;
        // var userajax = new $ax(Feng.ctxPath + "/mgr/detail?userId=" + userId);
        // var result = userajax.start();
        // var title = result.data.title;
        // if(title != '教授'){
        //     $('#professor').remove();
        // }
    })


    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/meetMember/editForum", function (data) {
            Feng.success("更新成功！");
            // window.location.href = Feng.ctxPath + '/meetMember';
            // 传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            // 关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    /**
     * 选择论坛
     */
    form.on('submit(btnSubmitForum)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/meetMember/editForum", function (data) {
            Feng.success("选择论坛成功！");
            // window.location.href = Feng.ctxPath + '/meetMember';
            // 传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            // 关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("选择论坛失败！" + data.responseJSON.message)
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
            // url:Feng.ctxPath + "/ownForum/listAll" ,
            url:Feng.ctxPath + "/forum/wrapList" ,
            success:function(response){
                var data=response.data;
                var forums = [];
                forums = data;
                var options;
                for (i = 0 ;i < forums.length ;i++){
                    var forum = data[i];
                    if(forum.status == "未发布"){
                        continue;
                    }
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


});