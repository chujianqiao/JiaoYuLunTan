/**
 * 详情对话框
 */
var ForumInfoDlg = {
    data: {
        forumId: "",
        forumName: "",
        setNum: "",
        existNum: "",
        status: "",
        registerStartTime: "",
        registerEndTime: "",
        startTime: "",
        endTime: "",
        location: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var nowTime = new Date().valueOf();


// 渲染时间选择框
    var start = laydate.render({
        elem: '#startTime'
        ,type: 'datetime'
        ,trigger: 'click'
        ,min:nowTime,
        done:function(value,date){
            endMax = end.config.max;
            end.config.min = date;
            end.config.min.month = date.month -1;
        }
    });

    // 渲染时间选择框
    var end = laydate.render({
        elem: '#endTime'
        ,type: 'datetime'
        ,trigger: 'click'
        ,min : nowTime,
        done:function(value,date){
            if($.trim(value) == ''){
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
            }
            start.config.max = date;
            start.config.max.month = date.month -1;
        }
    });



// 渲染时间选择框
    var registerStart = laydate.render({
        elem: '#registerStartTime'
        ,type: 'datetime'
        ,trigger: 'click'
        ,min:nowTime,
        done:function(value,date){
            endMax = end.config.max;
            registerEnd.config.min = date;
            registerEnd.config.min.month = date.month -1;
        }
    });

    // 渲染时间选择框
    var registerEnd = laydate.render({
        elem: '#registerEndTime'
        ,type: 'datetime'
        ,trigger: 'click'
        ,min : nowTime,
        done:function(value,date){
            if($.trim(value) == ''){
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
            }
            registerStart.config.max = date;
            registerStart.config.max.month = date.month -1;
        }
    });
























    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/forum/detail?forumId=" + Feng.getUrlParam("forumId"));
    var result = ajax.start();
    form.val('forumForm', result.data);
    console.log(result.data);
    $("#forumData1").html(result.data.forumName);
    $("#forumData2").html("分论坛时间：" + result.data.startTime + "-" + result.data.endTime);
    $("#forumData3").html("分论坛地点：" + result.data.location);
    $("#forumData4").html(result.data.forumName);
    $("#forumData5").html("分论坛时间：" + result.data.startTime + "-" + result.data.endTime);
    $("#forumData6").html("分论坛地点：" + result.data.location);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/forum/editItem", function (data) {
            Feng.success("更新成功！");
            window.location.href = Feng.ctxPath + '/forum'
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/forum'
    });


    $('#getImage').click(function(data){
        var forumId = Feng.getUrlParam("forumId");
        if(forumId == null || forumId == "" || forumId == 'undefined'){
            forumId = result.data.forumId;
        }
        $.ajax({
            type: "post",
            url: Feng.ctxPath + "/weiXin/getForumCheckImage?forumId=" + forumId,
            success: function (data) {
                var photo = data.checkName;
                Feng.success("二维码生成成功！");
                $("#checkImg").attr("style","height:300px;background-image: url('data:image/jpeg;base64," + photo + "');background-repeat:no-repeat;background-size: 100% 100%;");
            }
        });
        $.ajax({
            type: "post",
            url: Feng.ctxPath + "/weiXin/getForumSignImage?forumId=" + forumId,
            success: function (data) {
                var photo = data.signName;
                Feng.success("二维码生成成功！");
                $("#signImg").attr("style","height:300px;background-image: url('data:image/jpeg;base64," + photo + "');background-repeat:no-repeat;background-size: 100% 100%;");
            }
        });
    });
});