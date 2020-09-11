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

    // 获取详情信息填充表单
    // var ajax = new $ax(Feng.ctxPath + "/meetMember/checkDetail?memberId=" + Feng.getUrlParam("memberId"));
    // var result = ajax.start();
    // form.val('meetMemberForm', result.data);


    /**
     * 加载页面时执行
     */
    $(function(){
        // var ajax = new $ax(Feng.ctxPath + "/meetMember/checkDetail?memberId=" + Feng.getUrlParam("memberId"));
        // var result = ajax.start();
        // var ownForumid = result.data.ownForumid;
        //构建参会人员候选值
        userSelectOption();
    })

    /**
     * 分配座位
     */
    form.on('submit(btnSubmitSeat)', function (data) {
        debugger;
        var divId = $('#divId').val();
        var meetId = $('#meetId').val();
        var seatId = $('#seatId').val();
        var seatDiv = $('#divId');
        var seatRow = divId.substring(divId.indexOf('_') + 1,divId.lastIndexOf('_'));
        var seatCol = divId.substring(divId.lastIndexOf('_') + 1);
        data.field.seatRow = seatRow;
        data.field.seatCol = seatCol;
        var ajax = new $ax(Feng.ctxPath + "/seatDetail/editItem?seatId=" + seatId, function (data) {
            Feng.success("分配座位成功！");
            // window.location.href = Feng.ctxPath + '/meetMember';
            // 传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            // 关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("分配座位失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    /**
     * 构建论坛下拉框候选值
     * @param ownForumid
     */
    function userSelectOption(){
        $.ajax({
            type:'post',
            // url:Feng.ctxPath + "/ownForum/listAll" ,
            url:Feng.ctxPath + "/meetMember/wraplist" ,
            success:function(response){
                var divId = $('#divId').val();
                var seatDiv = $('#' + divId);

                var data=response.data;
                var members = [];
                members = data;
                var options;
                options = '<option value="" >请选择用户</option>';
                for (i = 0 ;i < members.length ;i++){
                    var member = data[i];
                    if(false){
                        options += '<option value="'+ member.userId+ '" selected="selected">'+ member.memberName +'</option>';
                    }
                    else{
                        options += '<option value="'+ member.userId+ '" >'+ member.memberName +'</option>';
                    }
                }
                $('#userId').empty();
                $('#userId').append(options);
                form.render('select');
            }
        })
    }


});