layui.use(['form', 'upload', 'element', 'laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
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


    if ($("#roleId").val().indexOf("4") > -1 || $("#roleId").val().indexOf("5") > -1 || $("#roleId").val().indexOf("1") > -1){
        $("#thesisDataDiv").attr("style","display:none");
        $("#thesisDiv").attr("style","display:none");
    }

    function meetDetail() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + "/meet/detailPub",
            success: function (response) {
                var data = response.data;
                console.log(data);
                $("#meetData1").html("<h2 style='font-weight: bold;'>" + data.detail.meetName + "</h2>");
                $("#meetData2").html("会议描述：" + data.detail.meetDesc);
                $("#meetData3").html("会议地点：" + data.detail.place);
                $("#meetData4").html("会议时间：" + data.beginTime + " 至 " + data.endTime);
                $("#meetData5").html("报名时间：" + data.joinBegTime + " 至 " + data.joinEndTime);
                $("#meetName").html("" + data.detail.meetName);
                $("#seat").html("座位：" + data.seat.seatRow + "排" + data.seat.seatCol + "号");
                //绑定点击事件
                let seatId = data.seatId;
                ownSeat(seatId);
            }
        });
    }

    function thesisDetail() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + "/thesis/detailPub",
            success: function (response) {
                var data = response.data;
                if (data != "empty"){
                    $("#thesisData1").html("<h2 style='font-weight: bold;'>" + data.thesisTitle + "</h2>");
                    $("#thesisData2").html(data.engTitle);
                    $("#thesisData3").html("作者：" + data.thesisUser);
                    $("#thesisData4").html("摘要：" + data.cnAbstract);
                    $("#thesisResult").attr("href","javascript:thesisResult('" + data.thesisId + "')")
                }else {
                    $("#thesisData1").html("无");
                }

            }
        });
    }

    function meetMemberDetail() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + '/meetMember/wraplist',
            success: function (response) {
                var data = response.data;


                for (var i = 0;i < data.length;i++){
                    console.log(data[i]);
                    if (data[i].meetStatus == 4 || data[i].meetStatus == 6){
                        if (data[i].ownForumid != null && data[i].ownFourmid != ""){
                            $("#forum").attr("href","javascript:forumAdd('exist')")
                        } else {
                            $("#forum").attr("href","javascript:forumAdd('" + data[i].memberId + "')")
                            $("#iconEdit").attr("style","font-size: 60px");
                        }
                        $("#pay").attr("href","javascript:toPay('yes')")
                        forumSelectOption(data[i].ownForumid);
                        break;
                    }else if (data[i].meetStatus == 2){
                        $("#forum").attr("href","javascript:forumAdd('toPay')")
                        $("#pay").attr("href","javascript:toPay('" + data[i].memberId + "')")
                        $("#iconNotice").attr("style","font-size: 60px");
                        break;
                    }else {
                        $("#forum").attr("href","javascript:forumAdd('')")
                        $("#pay").attr("href","javascript:toPay('')")
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
                        $("#forumData1").html("<h2 style='font-weight: bold;'>" + forum.forumName + "</h2>");
                        $("#forumData2").html("论坛地点：" + forum.location);
                        $("#forumData3").html("论坛时间：" + forum.startTime + " 至 " + forum.endTime);
                        $("#forumData4").html("报名时间：" + forum.registerStartTime + " 至 " + forum.registerEndTime);
                        $("#forumName").html("论坛：" + forum.forumName);
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

    function ownSeat(seatId){
        $("#seat").each(function(){
            let seatText = document.getElementById("seat").innerText;
            let index = seatText.indexOf('无');
            if(index < 0){
                $(this).click(function(){
                    layer.open({
                        title: '我的座位',
                        type: 2,
                        area: ['1200px','580px'],
                        // resize:false,
                        content: Feng.ctxPath + '/meetSeat?seatId='+ seatId
                    });
                });
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