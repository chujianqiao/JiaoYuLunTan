/**
 * 添加或者修改页面
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

























    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/forum/addItem", function (data) {
            Feng.success("添加成功！");
            window.location.href = Feng.ctxPath + '/forum'
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/forum'
    });

});