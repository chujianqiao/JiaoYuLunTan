// /**
//  * 详情对话框
//  */
// var MeetInfoDlg = {
//     data: {
//         meetId: "",
//         meetName: "",
//         meetDesc: "",
//         place: "",
//         peopleNum: "",
//         thesisNum: "",
//         beginTime: "",
//         endTime: "",
//         regUser: "",
//         regTime: ""
//     }
// };

layui.use(['layer','form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;
    var laydate = layui.laydate;
    var nowTime = new Date().valueOf();

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
    // var ajax = new $ax(Feng.ctxPath + "/meet/detail?meetId=" + Feng.getUrlParam("meetId"));
    // var result = ajax.start();
    // form.val('meetForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/meet/addItem", function (data) {
            Feng.success("添加成功！");
            window.location.href = Feng.ctxPath + '/meet'
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/meet'
    });

    //执行一个laydate实例
    var beginTime = laydate.render({
        elem: '#beginTime' //指定元素
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,trigger: 'click'
        ,min:nowTime,
        done:function(value,date){
            endMax = endTime.config.max;
            endTime.config.min = date;
            endTime.config.min.month = date.month -1;
        }
    });
    var endTime = laydate.render({
        elem: '#endTime'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,trigger: 'click'
        ,min : nowTime,
        done:function(value,date){
            if($.trim(value) == ''){
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
            }
            beginTime.config.max = date;
            beginTime.config.max.month = date.month -1;
        }
    });
    var joinBegTime = laydate.render({
        elem: '#joinBegTime'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,trigger: 'click'
        ,min:nowTime,
        done:function(value,date){
            endMax = joinEndTime.config.max;
            joinEndTime.config.min = date;
            joinEndTime.config.min.month = date.month -1;
        }
    });
    var joinEndTime = laydate.render({
        elem: '#joinEndTime'
        ,type: 'datetime'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,trigger: 'click'
        ,min : nowTime,
        done:function(value,date){
            if($.trim(value) == ''){
                var curDate = new Date();
                date = {'date': curDate.getDate(), 'month': curDate.getMonth()+1, 'year': curDate.getFullYear()};
            }
            joinBegTime.config.max = date;
            joinBegTime.config.max.month = date.month -1;
        }
    });
});