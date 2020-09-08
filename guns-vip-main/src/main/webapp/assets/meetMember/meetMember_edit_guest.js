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
        var ajax = new $ax(Feng.ctxPath + "/meetMember/adminDetail?memberId=" + Feng.getUrlParam("memberId"));
        var result = ajax.start();
        var ownForumid = result.data.ownForumid;
        forumSelectOption(ownForumid);
        //填充表单
        form.val('meetMemberForm', result.data);
        var pptName = result.data.pptName;
        if(pptName != null && pptName != "" && pptName != 'undefined'){
            $("#pptName").html(pptName);
            $("#pptName").val(pptName);
            $("#pptNameTip").html(pptName);
            $("#pptNameTip").val(pptName);
        }
        var wordName = result.data.wordName;
        if(wordName != null && wordName != "" && wordName != 'undefined'){
            $("#wordName").html(wordName);
            $("#wordName").val(wordName);
            $("#wordNameTip").html(wordName);
            $("#wordNameTip").val(wordName);
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

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        debugger;
        var ajax = new $ax(Feng.ctxPath + "/meetMember/adminEditItem", function (data) {
            Feng.success("更新成功！");
            window.location.href = Feng.ctxPath + '/meetMember';
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

    //上传Word文件
    upload.render({
        elem: '#uploadWord'
        , url: Feng.ctxPath + '/thesis/uploadWord'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                var fileName = file.name;
                var fileType = fileName.substr(fileName.lastIndexOf("."));
                if(fileType.compare(".doc") || fileType.compare(".docx")){
                    $("#wordNameTip").html(fileName);
                    $("#wordNameTip").val(fileName);
                }
            });
        }
        , done: function (res) {
            debugger;
            var status = res.data.status;
            if(status == "格式问题" || status === "格式问题"){
                Feng.error(res.message);
            }else {
                $("#wordPath").val(res.data.path);
                $("#wordName").val($("#wordNameTip").val());
                Feng.success(res.message);
            }
        }
        , error: function (res) {
            Feng.error("上传文件失败");
        }
    });

    //上传PPT文件
    upload.render({
        elem: '#uploadPPT'
        , url: Feng.ctxPath + '/thesis/uploadPPT'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                var fileName = file.name;
                var fileType = fileName.substr(fileName.lastIndexOf("."));
                if(fileType.compare(".ppt") || fileType.compare(".pptx")){
                    $("#pptNameTip").html(fileName);
                    $("#pptNameTip").val(fileName);
                }
            });
        }
        , done: function (res) {
            debugger;
            var status = res.data.status;
            if(status == "格式问题" || status === "格式问题"){
                Feng.error(res.message);
            }else {
                $("#pptPath").val(res.data.path);
                $("#pptName").val($("#pptNameTip").val());
                Feng.success(res.message);
            }
        }
        , error: function (res) {
            Feng.error("上传文件失败");
        }
    });

    /**
     * 不区分大小写比较字符串
     * @param str
     * @returns {boolean}
     */
    String.prototype.compare = function(str) {
        //不区分大小写
        if(this.toLowerCase() == str.toLowerCase()) {
            return true;
        } else {
            return false;
        }
    }

});