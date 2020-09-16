layui.use(['form', 'upload', 'element', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var upload = layui.upload;
    var element = layui.element;
    var laydate = layui.laydate;

    //渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });
    meetDetail();
    thesisDetail();
    meetMemberDetail();


    function meetDetail() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + "/meet/detailPub",
            success: function (response) {
                var data = response.data;
                //console.log(data);

                $("#meetData1").html(data.meetName);
                $("#meetData2").html("会议描述：" + data.meetDesc);
                $("#meetData3").html("会议地点：" + data.place);
                $("#meetData4").html("会议时间：" + data.beginTime + "-" + data.endTime);
                $("#meetData5").html("报名时间：" + data.joinBegTime + "-" + data.joinEndTime);

            }
        });
    }

    function thesisDetail() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + "/thesis/detailPub",
            success: function (response) {
                var data = response.data;
                //console.log(data);
                $("#thesisData1").html(data.thesisTitle);
                $("#thesisData2").html(data.engTitle);
                $("#thesisData3").html("作者：" + data.thesisUser);
                $("#thesisData4").html("摘要：" + data.cnAbstract);

            }
        });
    }

    function meetMemberDetail() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + '/meetMember/wraplist',
            success: function (response) {
                var data = response.data;
                //console.log(data);

                for (var i = 0;i < data.length;i++){
                    console.log(data[i].meetStatus);
                    if (data[i].meetStatus == 4){
                        $("#forum").attr("href","javascript:forumAdd('" + data[i].memberId + "')")
                        forumSelectOption(data[i].ownForumid);
                        break;
                    }else {
                        $("#forum").attr("href","javascript:forumAdd('')")
                        $("#forumData1").html("无");
                        $("#forumData2").empty();
                        $("#forumData3").empty();
                        $("#forumData4").empty();
                    }

                }
            }
        });
    }

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
                        $("#forumData1").html(forum.forumName);
                        $("#forumData2").html("论坛地点：" + forum.location);
                        $("#forumData3").html("论坛时间：" + forum.startTime + "-" + forum.endTime);
                        $("#forumData4").html("报名时间：" + forum.registerStartTime + "-" + forum.registerEndTime);
                        break;
                    }else{
                        $("#forumData1").html("无");
                        $("#forumData2").empty();
                        $("#forumData3").empty();
                        $("#forumData4").empty();
                    }
                }
            }
        })
    }



    //获取用户详情
    /*var ajax = new $ax(Feng.ctxPath + "/meet/detailPub");
    var result = ajax.start();
    console.log(result.data);*/

    //用这个方法必须用在class有layui-form的元素上
    /*form.val('userInfoForm', result.data);
    $("#pptNameTip").val(result.data.pptName);
    $("#wordNameTip").val(result.data.wordName);
    $("#introduction").val(result.data.introduction);*/
    /*//获取嘉宾附件信息
    var ajax = new $ax(Feng.ctxPath + "/meetMember/wraplist");
    var result = ajax.start();
    //console.log(result)
    if (result.data != null){
        $("#pptNameTip").val(result.data[0].pptName);
        $("#wordNameTip").val(result.data[0].wordName);
        $("#introduction").val(result.data[0].introduction);
    }*/



    //表单提交事件
    form.on('submit(userInfoSubmit)', function (data) {
        console.log(data)
        var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
            if (data.message == "phoneError"){
                Feng.error("修改失败,该手机号已被绑定！");
            } else {
                Feng.success("修改成功!");
            }
        }, function (data) {
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set(data.field);
        ajax.start();
    });
    //表单提交事件
    form.on('submit(guestInfoSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
            Feng.success("修改成功!");
        }, function (data) {
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId",$("#userId").val());
        ajax.set("pptPath",$("#pptPath").val());
        ajax.set("pptName",$("#pptNameTip").val());
        ajax.set("wordPath",$("#wordPath").val());
        ajax.set("wordName",$("#wordNameTip").val());
        ajax.set("introduction",$("#introduction").val());
        ajax.start();
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


    //上传文件
    upload.render({
        elem: '#pptBtn'
        , url: Feng.ctxPath + '/meetMember/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#pptNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#pptInputHidden").val(res.data.fileId);
            $("#pptPath").val(res.data.path);
            $("#pptName").val($("#pptNameTip").val());
            /*var ajax = new $ax(Feng.ctxPath + "/meetMember/updateFile", function (data) {
                if (data.message == "sizeError"){
                    Feng.error("上传失败，无参会信息！");
                } else if (data.message == "success") {
                    Feng.success("上传成功!");
                }else {
                    Feng.error("上传失败!");
                }

            }, function (data) {
                Feng.error("上传失败!" + data.responseJSON.message + "!");
            });
            ajax.set("pptPath",res.data.path);
            ajax.set("pptName",$("#pptNameTip").val());
            ajax.start();*/
            //Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#wordBtn'
        , url: Feng.ctxPath + '/meetMember/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#wordNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#wordInputHidden").val(res.data.fileId);
            $("#wordPath").val(res.data.path);
            $("#wordName").val($("#wordNameTip").val());

            /*var ajax = new $ax(Feng.ctxPath + "/meetMember/updateFile", function (data) {
                if (data.message == "sizeError"){
                    Feng.error("上传失败，无参会信息！");
                } else if (data.message == "success") {
                    Feng.success("上传成功!");
                }else {
                    Feng.error("上传失败!");
                }

            }, function (data) {
                Feng.error("上传失败!" + data.responseJSON.message + "!");
            });
            ajax.set("wordPath",res.data.path);
            ajax.set("wordName",$("#wordNameTip").val());
            ajax.start();*/

            //Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传失败！");
        }
    });
});