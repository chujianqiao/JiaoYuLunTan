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
    getUserTranslationAjax();
    function getUserTranslationAjax() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + "/translation/getUserTranslation",
            success: function (response) {
                layui.data('system', {
                    key: "lang",
                    value: response.data
                });
            }
        });
    }

// 加载当前语言字典并缓存
    /*var getUserTranslationAjax = new $ax(Feng.ctxPath + "/translation/getUserTranslation", function (data) {
        layui.data('system', {
            key: "lang",
            value: data.data
        });
    }, function (data) {
        layer.msg("加载语言字典失败！" + data.responseJSON.message, {icon: 5, anim: 6});
    });
    getUserTranslationAjax.start();*/


    if ($("#roleId").val().indexOf("4") > -1 || $("#roleId").val().indexOf("5") > -1 || $("#roleId").val().indexOf("1") > -1){
        $("#thesisDataDiv").attr("style","display:none");
        $("#thesisDiv").attr("style","display:none");
    }
    if ($("#roleId").val().indexOf("5") > -1){
        $("#pay").attr("style","display:none");
        $("#payMo").attr("style","display:none");
        $("#guest").attr("href","javascript:editGuest('" + $("#userId").val() + "')");
        $("#guestMo").attr("href","javascript:editGuest('" + $("#userId").val() + "')");
    }else {
        $("#guest").attr("style","display:none");
        $("#guestMo").attr("style","display:none");
    }

    function meetDetail() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + "/meet/detailPub",
            success: function (response) {
                var langs = layui.data('system').lang;
                var data = response.data;
                if (data.meetTimeStatusStr == "无会议"){
                    $("#meetData1").html("<h2 style='font-weight: bold;'>当前无正在进行的会议。</h2>");
                    $("#thesisData1").html("<h2 style='font-weight: bold;'>当前无正在进行的会议。</h2>");
                    $("#forumData1").html("<h2 style='font-weight: bold;'>当前无正在进行的会议。</h2>");

                } else {
                    $("#meetFile").attr("href",Feng.ctxPath + "/meet/meetFile");
                    $("#meetFileMo").attr("href",Feng.ctxPath + "/meet/meetFile");
                    $("#fileDownload").attr("href","javascript:fileDownload();");
                    $("#fileDownloadMo").attr("href","javascript:fileDownloadMo();");
                    $("#iconFile").attr("style","font-size: 60px");
                    $("#iconFileMo").attr("style","font-size: 60px");
                    $("#iconDownload").attr("style","font-size: 60px");
                    $("#iconDownloadMo").attr("style","font-size: 60px");
                    $("#meetData1").html("<h2 style='font-weight: bold;'>" + data.detail.meetName + "</h2>");
                    $("#meetData2").html(langs.FIELD_DescriptionConference + "：" + data.detail.meetDesc);
                    $("#meetData3").html(langs.FIELD_ConferencePlace + "：" + data.detail.place);
                    $("#meetData4").html(langs.FIELD_ConferenceDate + "：" + data.beginTime + " ~ " + data.endTime);
                    $("#meetData5").html(langs.FIELD_RegistrationDeadline + "：" + data.joinBegTime + " ~ " + data.joinEndTime);
                    $("#meetName").html("" + data.detail.meetName);
                    $("#meetNameMo").html("" + data.detail.meetName);
                    if (data.seat != undefined){
                        $("#seat").html(langs.FIELD_Seat + "：" + data.seat.seatRow + langs.FIELD_SeatRow + data.seat.seatCol + langs.FIELD_SeatCol);
                        $("#seatMo").html(langs.FIELD_Seat + "：" + data.seat.seatRow + langs.FIELD_SeatRow + data.seat.seatCol + langs.FIELD_SeatCol);
                    }
                    //绑定点击事件
                    let seatId = data.seatId;
                    ownSeat(seatId);
                    if (data.detail.size == "big"){
                        thesisDetail();
                    } else {
                        $("#thesisData1").html(langs.FIELD_None);
                    }

                    meetMemberDetail();
                }

            }
        });
    }

    function thesisDetail() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + "/thesis/detailPub",
            success: function (response) {
                var langs = layui.data('system').lang;
                var data = response.data;
                if (data != "empty"){
                    $("#thesisData1").html("<h2 style='font-weight: bold;'>" + data.thesisTitle + "</h2>");
                    $("#thesisData2").html(data.engTitle);
                    $("#thesisData3").html(langs.FIELD_Author + "：" + data.thesisUser);
                    $("#thesisData4").html(langs.FIELD_Abstract + "：" + data.cnAbstract);
                    $("#thesisData5").html(langs.FIELD_ReviewStatus + "：" + data.reviewStr);
                    $("#thesisResult").attr("href","javascript:thesisResult('" + data.thesisId + "')")
                    $("#iconThesisResult").attr("style","font-size: 60px");
                }else {
                    $("#thesisData1").html(langs.FIELD_None);
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
                var langs = layui.data('system').lang;
                for (var i = 0;i < data.length;i++){
                    console.log(data[i]);
                    if (data[i].finalResult == 2 || data[i].thesisName == "无"){
                        if (data[i].meetStatus == 4 || data[i].meetStatus == 6){
                            if (data[i].ownForumid != null && data[i].ownFourmid != ""){
                                $("#forum").attr("href","javascript:forumAdd('exist')");
                                $("#forumMo").attr("href","javascript:forumAdd('exist')");
                            } else {
                                $("#forum").attr("href","javascript:forumAdd('" + data[i].memberId + "')");
                                $("#forumMo").attr("href","javascript:forumAdd('" + data[i].memberId + "')");
                                $("#iconEdit").attr("style","font-size: 60px");
                                $("#iconEditMo").attr("style","font-size: 60px");
                            }
                            $("#pay").attr("href","javascript:toPay('yes')");
                            $("#payMo").attr("href","javascript:toPay('yes')");
                            forumSelectOption(data[i].ownForumid);
                            break;
                        }else if (data[i].meetStatus == 2){
                            $("#forum").attr("href","javascript:forumAdd('toPay')");
                            $("#forumMo").attr("href","javascript:forumAdd('toPay')");
                            $("#pay").attr("href","javascript:toPay('" + data[i].memberId + "')");
                            $("#payMo").attr("href","javascript:toPay('" + data[i].memberId + "')");
                            $("#iconNotice").attr("style","font-size: 60px");
                            $("#iconNoticeMo").attr("style","font-size: 60px");
                            break;
                        }else {
                            $("#forum").attr("href","javascript:forumAdd('')");
                            $("#forumMo").attr("href","javascript:forumAdd('')");
                            $("#pay").attr("href","javascript:toPay('')");
                            $("#payMo").attr("href","javascript:toPay('')");
                            $("#forumData1").html(langs.FIELD_None);
                            $("#forumData2").empty();
                            $("#forumData3").empty();
                            $("#forumData4").empty();
                        }
                    }else {
                        $("#forum").attr("href","javascript:forumAdd('')");
                        $("#forumMo").attr("href","javascript:forumAdd('')");
                        $("#pay").attr("href","javascript:toPay('')");
                        $("#payMo").attr("href","javascript:toPay('')");
                        $("#forumData1").html(langs.FIELD_None);
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
                var langs = layui.data('system').lang;
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
                        $("#forumData2").html(langs.FIELD_VenueForum + "：" + forum.location);
                        $("#forumData3").html(langs.FIELD_DateForum + "：" + forum.startTime + " ~ " + forum.endTime);
                        //$("#forumData4").html("报名时间：" + forum.registerStartTime + " 至 " + forum.registerEndTime);
                        $("#forumName").html(langs.FIELD_Forum + "：" + forum.forumName);
                        $("#forumNameMo").html(langs.FIELD_Forum + "：" + forum.forumName);
                        break;
                    }else{
                        $("#forumData1").html(langs.FIELD_None);
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
            var langs = layui.data('system').lang;
            let seatText = document.getElementById("seat").innerText;
            let index = seatText.indexOf('无');
            if(index < 0){
                $(this).click(function(){
                    layer.open({
                        title: langs.FIELD_MySeat,
                        type: 2,
                        area: ['1200px','580px'],
                        // resize:false,
                        content: Feng.ctxPath + '/meetSeat?seatId='+ seatId
                    });
                });
            }
        })
        $("#seatMo").each(function(){
            var langs = layui.data('system').lang;
            let seatText = document.getElementById("seatMo").innerText;
            let index = seatText.indexOf('无');
            if(index < 0){
                $(this).click(function(){
                    layer.open({
                        title: langs.FIELD_MySeat,
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
        var ajax = new $ax(Feng.ctxPath + "/meetMember/adminEditItem", function (data) {
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