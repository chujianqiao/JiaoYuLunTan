/**
 * 添加或者修改页面
 */
var MeetMemberInfoDlg = {
    data: {
        memberId: "",
        userId: "",
        thesisId: "",
        speak: "",
        judge: "",
        ownForumid: "",
        regTime: "",
        belongDomain: "",
        pName: ""
    }
};

layui.use(['layer', 'form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var func = layui.func;
    var layer = layui.layer;

    $(function () {
        domainSelectOption();
    })

    var userTitle = $("#userTitle").val();
    if(userTitle != '教授'){
        $('#judge1').remove();
        $('#judge2').remove();
    }

    $(function () {
        forumSelectOption();
    })

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        debugger;
        var ajax = new $ax(Feng.ctxPath + "/thesis/addItem", function (data) {
            Feng.success("添加成功！");
            window.location.href = Feng.ctxPath + '/meetMember';
            //传给上个页面，刷新table用
            // admin.putTempData('formOk', true);
            //关掉对话框
            // admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    //上传文件
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

    upload.render({
        elem: '#imgHead'
        , url: Feng.ctxPath + '/system/upload'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $('#avatarPreview').attr('src', result);
            });
        }
        , done: function (res) {
            var ajax = new $ax(Feng.ctxPath + "/system/updateAvatar", function (data) {
                Feng.success(res.message);
            }, function (data) {
                Feng.error("修改失败!" + data.responseJSON.message + "!");
            });
            ajax.set("fileId", res.data.fileId);
            ajax.start();
        }
        , error: function () {
            Feng.error("上传头像失败！");
        }
    });

    /**
     * 构建论坛下拉框候选值
     * @param ownForumid
     */
    function forumSelectOption(){
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
                    options += '<option value="'+ forum.forumId+ '" >'+ forum.forumName +'</option>';
                }
                $('#ownForumid').empty();
                $('#ownForumid').append(options);
                form.render('select');
            }
        })
    }

    //取消
    $('#btnCancel').click(function () {
        //关闭标签页
        window.opener=null;
        window.open('','_self');
        window.close();
    });


    // 点击上级角色时
    /*$('#pName').click(function () {
        var formName = encodeURIComponent("parent.MeetMemberInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.MeetMemberInfoDlg.data.belongDomain");
        var treeUrl = encodeURIComponent("/thesisDomain/tree");

        layer.open({
            type: 2,
            title: '父级领域',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/thesisDomain/thesisDomainAssign?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#belongDomain").val(MeetMemberInfoDlg.data.belongDomain);
                $("#pName").val(MeetMemberInfoDlg.data.pName);
            }
        });
    });*/

    function domainSelectOption(){
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
                $('#belongDomain').append("<option value=''>请选择论文领域</option>");
                $('#belongDomain').append(options);
                //form.render('select');

                var selectDomain = document.getElementById("belongDomain");
                for (var i = 0; i < selectDomain.options.length; i++) {
                    console.log(selectDomain.options[i].text + "----" + result.data.belongDomain)
                    if (selectDomain.options[i].text == result.data.belongDomain) {
                        selectDomain.options[i].selected = true;
                    }
                }
                form.render('select');
            }
        })
    }

});