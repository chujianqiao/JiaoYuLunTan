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

    //获取详情信息，填充表单
    // var ajax = new $ax(Feng.ctxPath + "/meetMember/detail?memberId=" + Feng.getUrlParam("memberId"));
    // var result = ajax.start();
    // form.val('meetMemberForm', result.data);

    /**
     * 加载页面时执行
     */
    $(function(){
        // domainSelectOption();
    })
        var ajax = new $ax(Feng.ctxPath + "/meetMember/detail?memberId=" + Feng.getUrlParam("memberId"));
        var result = ajax.start();
        var ownForumid = result.data.ownForumid;
        // forumSelectOption(ownForumid);

        var thesisId = result.data.thesisId;
        var ajaxThesis = new $ax(Feng.ctxPath + "/thesis/detail?thesisId=" + thesisId);
        var resultThesis = ajaxThesis.start();

        //PDF
        var fileName = resultThesis.data.fileName;
        $("#fileNameTip").html(fileName);
        //Word
        var wordName = resultThesis.data.wordName;
        $("#wordNameTip").html(wordName);
        //批量赋值
        form.val('meetMemberForm', resultThesis.data);
        form.val('meetMemberForm', result.data);

        //领域
        var domiansId = resultThesis.data.belongDomain;
        var idList = domiansId.split(",");
        var dNameStr = "";
        var h = idList.length;
        for (i = 0;i < idList.length;i++){
            var ajaxDomain = new $ax(Feng.ctxPath + "/thesisDomain/detail?domainId=" + idList[i]);
            var resultDomain = ajaxDomain.start();
            var name = resultDomain.data.domainName;
            if(name == undefined || name ===  undefined){
                continue;
            }
            if(i == idList.length - 1){
                dNameStr += name;
            }else{
                dNameStr += (name + ",");
            }
        }
        $("#belongDomain").val(domiansId);
        $("#pName").val(dNameStr);

        // thesisSelectOption(thesisId);

        // var userId = result.data.userId;
        // var userajax = new $ax(Feng.ctxPath + "/mgr/detail?userId=" + userId);
        // var result = userajax.start();
        // var title = result.data.title;
        // if(title != '教授'){
        //     $('#professor').remove();
        // }


    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        debugger;
        var ajax = new $ax(Feng.ctxPath + "/meetMember/editItem", function (data) {
            Feng.success("更新成功！");
            window.location.href = Feng.ctxPath + '/meetMember';
            //传给上个页面，刷新table用
            // admin.putTempData('formOk', true);
            //关掉对话框
            // admin.closeThisDialog();
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    // 点击上级角色时
    $('#pName').click(function () {
        var formName = encodeURIComponent("parent.MeetMemberInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.MeetMemberInfoDlg.data.belongDomain");
        var treeUrl = encodeURIComponent("/thesisDomain/tree");

        layer.open({
            type: 2,
            title: '父级领域',
            area: ['300px', '400px'],
            //content: Feng.ctxPath + '/thesisDomain/thesisDomainAssign?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#belongDomain").val(MeetMemberInfoDlg.data.belongDomain);
                $("#pName").val(MeetMemberInfoDlg.data.pName);
            }
        });
    });

    //上传PDF文件
    upload.render({
        elem: '#fileBtn'
        , url: Feng.ctxPath + '/holdForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#fileNameTip").val(file.name);
                $("#fileNameTip").html(file.name);
            });
        }
        , done: function (res) {
            $("#fileInputHidden").val(res.data.fileId);
            $("#thesisPath").val(res.data.path);
            $("#fileName").val($("#fileNameTip").val());
            Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传文件失败！");
        }
    });

    //上传Word文件
    upload.render({
        elem: '#wordBtn'
        , url: Feng.ctxPath + '/thesis/uploadWord'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                var fileName = file.name;
                var fileType = fileName.substr(fileName.lastIndexOf("."));
                if(fileType.compare(".doc") || fileType.compare(".docx")){
                    debugger;
                    $("#wordNameTip").val(file.name);
                    $("#wordNameTip").html(file.name);
                }
            });
        }
        , done: function (res) {
            var status = res.data.status;
            if(status == "格式问题" || status === "格式问题"){
                Feng.error(res.message);
            }else {
                $("#wordInputHidden").val(res.data.fileId);
                $("#wordPath").val(res.data.path);
                $("#wordName").val($("#wordNameTip").val());
                Feng.success(res.message);
            }
        }
        , error: function (res) {
            Feng.error("上传文件失败");
        }
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

    //取消
    $('#btnCancel').click(function () {
        window.location.href = Feng.ctxPath + '/meetMember';
    });

    // 下载论文PDF附件
    $('#btnDownload').click(function () {
        var thesisId = $('#thesisId').val();
        downloadThesis(thesisId);
    });

    // 下载论文Word附件
    $('#btnDownloadWord').click(function () {
        var thesisId = $('#thesisId').val();
        downloadThesisWord(thesisId);
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

    function downloadThesisWord(thesisId){
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/meetMember/downloadThesisWord?thesisId=" + thesisId);    // 此处填写文件下载提交路径
        $("body").append(form);    // 将表单放置在web中
        form.submit();
    }

    /*function domainSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/thesisDomain/list" ,
            success:function(response){
                var data=response.data;
                var domains = [];
                domains = data;

                var options;
                for (var i = 0 ;i < domains.length ;i++){
                    var domain = data[i];
                    options += '<option value="'+ domain.domainId+ '" >'+ domain.domainName +'</option>';
                }
                $('#belongDomain').empty();
                //$('#belongDomain').append("<option value=''>请选择论文领域</option>");
                $('#belongDomain').append(options);
                //form.render('select');

                var selectDomain = document.getElementById("belongDomain");
                for (var i = 0; i < selectDomain.options.length; i++) {
                    console.log(selectDomain.options[i].value + "----" + domiansId)
                    if (selectDomain.options[i].value == domiansId) {
                        selectDomain.options[i].selected = true;
                    }
                }
                form.render('select');
            }
        })
    }*/

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