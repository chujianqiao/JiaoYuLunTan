/**
 * 详情对话框
 */
var MeetInfoDlg = {
    data: {
        meetId: "",
        meetName: "",
        meetDesc: "",
        place: "",
        peopleNum: "",
        thesisNum: "",
        beginTime: "",
        endTime: "",
        regUser: "",
        regTime: ""
    }
};

layui.use(['layer','form', 'admin', 'ax','laydate','upload','formSelects','table','func','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;
    var table = layui.table;
    var func = layui.func;
    var upload = layui.upload;

    getCheckImage();
    getSignImage();

    //实例化编辑器
    var ue = UE.getEditor('container', {
        enableAutoSave: false,
        autoHeightEnabled: true,
        autoFloatEnabled: false,
        scaleEnabled: true,//滚动条
        initialFrameHeight: 400 //默认的编辑区域高度
    });

    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action === 'uploadimage' || action === 'uploadscrawl' || action === 'uploadimage') {
            return Feng.ctxPath + '/ueditor/imgUpdate';
        } else if (action === 'uploadfile') {
            return Feng.ctxPath + '/ueditor/uploadfile';
        } else if (action === 'uploadvideo') {
            return Feng.ctxPath + '/ueditor/uploadvideo';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    };

    //获取详情信息，填充表单
    var meetId = Feng.getUrlParam("meetId");
    if(meetId == null || meetId == "" || meetId == 'undefined'){
        meetId = $("#meetIdParam").val();
    }
    var ajax = new $ax(Feng.ctxPath + "/meet/detail?meetId=" + meetId);
    var result = ajax.start();
    form.val('meetForm', result.data);
    console.log(result.data)
    $("#meetData1").html(result.data.meetName);
    $("#meetData2").html("会议时间：" + result.data.beginTime + "-" + result.data.endTime);
    $("#meetData3").html("会议地点：" + result.data.place);
    $("#meetData4").html(result.data.meetName);
    $("#meetData5").html("会议时间：" + result.data.beginTime + "-" + result.data.endTime);
    $("#meetData6").html("会议地点：" + result.data.place);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/meet/editItem", function (data) {
            Feng.success("更新成功！");
            window.location.href = Feng.ctxPath + '/meet'
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    //表单提交事件
    form.on('submit(btnSubmitAdmin)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/meet/editItem", function (data) {
            Feng.success("更新成功！");
            window.location.href = Feng.ctxPath + '/meet/info'
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    /**
     * 导出Word
     */
    $('#exportWord').click(function(data){
        let content = result.data.content;
        if(content == '' || content == undefined || content == 'undefined'){
            Feng.error("没有内容！");
        }else {
            downLoadMeet();
        }
    });
    $('#getImage').click(function(data){
        var meetId = Feng.getUrlParam("meetId");
        if(meetId == null || meetId == "" || meetId == 'undefined'){
            meetId = $("#meetIdParam").val();
        }
        $.ajax({
            type: "post",
            url: Feng.ctxPath + "/weiXin/getCheckImage?meetId=" + meetId,
            success: function (data) {
                var photo = data.checkName;
                Feng.success("二维码生成成功！");
                $("#checkImg").attr("style","height:300px;background-image: url('data:image/jpeg;base64," + photo + "');background-repeat:no-repeat;background-size: 100% 100%;");
            }
        });
        $.ajax({
            type: "post",
            url: Feng.ctxPath + "/weiXin/getSignImage?meetId=" + meetId,
            success: function (data) {
                var photo = data.signName;
                Feng.success("二维码生成成功！");
                $("#signImg").attr("style","height:300px;background-image: url('data:image/jpeg;base64," + photo + "');background-repeat:no-repeat;background-size: 100% 100%;");
            }
        });
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/meet'
    });

    function downLoadMeet(){
        Feng.info("正在生成文档，请稍等");
        let form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        // form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/meet/exportWord?meetId=" + meetId);    // 此处填写文件下载提交路径
        $("body").append(form);    // 将表单放置在web中
        form.submit();
    }

    //执行一个laydate实例
    laydate.render({
        elem: '#beginTime' //指定元素
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,trigger: 'click'
    });
    laydate.render({
        elem: '#endTime'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,trigger: 'click'
    });
    laydate.render({
        elem: '#joinBegTime'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,trigger: 'click'
    });
    laydate.render({
        elem: '#joinEndTime'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,trigger: 'click'
    });


    function getCheckImage(){
        //获取详情信息，填充表单
        var meetId = Feng.getUrlParam("meetId");
        if(meetId == null || meetId == "" || meetId == 'undefined'){
            meetId = $("#meetIdParam").val();
        }
        $.ajax({
            type: "post",
            url: Feng.ctxPath + "/checkIn/getCheckImage?meetId=" + meetId,
            success: function (data) {
                var photo = data.checkName;
                $("#checkImg").attr("style","height:300px;background-image: url('data:image/jpeg;base64," + photo + "');background-repeat:no-repeat;background-size: 100% 100%;");
            }
        });
    }
    function getSignImage(){
        //获取详情信息，填充表单
        var meetId = Feng.getUrlParam("meetId");
        if(meetId == null || meetId == "" || meetId == 'undefined'){
            meetId = $("#meetIdParam").val();
        }
        $.ajax({
            type: "post",
            url: Feng.ctxPath + "/checkIn/getSignImage?meetId=" + meetId,
            success: function (data) {
                var photo = data.signName;
                $("#signImg").attr("style","height:300px;background-image: url('data:image/jpeg;base64," + photo + "');background-repeat:no-repeat;background-size: 100% 100%;");
            }
        });
    }

});