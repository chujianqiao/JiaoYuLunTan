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

    /**
     * 加载页面时执行
     */
    $(function(){
        var divId = $('#divId').val();
        var seatRow = divId.substring(divId.indexOf('_') + 1,divId.lastIndexOf('_'));
        var seatCol = divId.substring(divId.lastIndexOf('_') + 1);
        var seatStr = '第' + seatRow + '排，' + '第' + seatCol + '列';
        $("#seatStr").val(seatStr);
        //构建参会人员候选值
        userSelectOption();
        $("#writeUnit").hide();
    })

    /**
     * radio监听
     */
    form.on('radio', function (data) {
        if(data.value == "people") {
            $("#writeUnit").hide();
            $("#changeUser").show();
        }
        if(data.value == "unit") {
            $("#changeUser").hide();
            $("#writeUnit").show();
        }
    });

    /**
     * 分配座位
     */
    form.on('submit(btnSubmitSeat)', function (data) {
        let type = data.field.type;
        let divId = $('#divId').val();
        let meetId = $('#meetId').val();
        let seatId = $('#seatId').val();
        // let seatDiv = $('#divId');
        let seatRow = divId.substring(divId.indexOf('_') + 1,divId.lastIndexOf('_'));
        let seatCol = divId.substring(divId.lastIndexOf('_') + 1);
        data.field.seatRow = seatRow;
        data.field.seatCol = seatCol;
        if(type == 'people'){
            //个人
            let changVal = $('#userId').val();
            if(changVal == '' || changVal == 'undefined'|| changVal == undefined){
                Feng.error('请选择用户');
            }else{
                let ajax = new $ax(Feng.ctxPath + "/seatDetail/editItem?seatId=" + seatId, function (data) {
                    Feng.success("分配座位成功！");
                    let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    window.parent.location.reload();   //刷新父界面
                    parent.layer.close(index);    //关闭弹出层
                }, function (data) {
                    Feng.error("分配座位失败！" + data.responseJSON.message)
                });
                ajax.set(data.field);
                ajax.start();
            }
        }else if (type == 'unit'){
            //单位
            let unitVal = $('#unitName').val();
            if(unitVal == '' || unitVal == 'undefined'|| unitVal == undefined){
                Feng.error('请填写单位名称');
            }else{
                let ajax = new $ax(Feng.ctxPath + "/seatDetail/batchItem?divIds=" + divId + "&seatId" + seatId, function (data) {
                    Feng.success("分配座位成功！");
                    let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    window.parent.location.reload();   //刷新父界面
                    parent.layer.close(index);    //关闭弹出层
                }, function (data) {
                    Feng.error("分配座位失败！" + data.responseJSON.message)
                });
                ajax.set(data.field);
                ajax.start();
            }
        }
        return false;
    });

    /**
     * 重置座位
     */
    form.on('submit(btnResetSeat)', function (data) {
        let divId = $('#divId').val();
        let seatEle = $("#" + divId,window.parent.document);
        let text = seatEle[0].innerText;
        //判断座位是否分配
        let numNum = text.indexOf(':');
        if(numNum == -1){
            Feng.error('此座位未分配');
        }else{
            let operation = function () {
                let seatId = $('#seatId').val();
                let seatRow = divId.substring(divId.indexOf('_') + 1,divId.lastIndexOf('_'));
                let seatCol = divId.substring(divId.lastIndexOf('_') + 1);
                data.field.seatRow = seatRow;
                data.field.seatCol = seatCol;
                let ajax = new $ax(Feng.ctxPath + "/seatDetail/resetSeat?seatId=" + seatId, function (data) {
                    Feng.success("重置座位成功！");
                    let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    window.parent.location.reload();   //刷新父界面
                    parent.layer.close(index);    //关闭弹出层
                }, function (data) {
                    Feng.error("重置座位失败！" + data.responseJSON.message)
                });
                ajax.set(data.field);
                ajax.start();
            };
            Feng.confirm("是否要重置此座位?", operation);
        }
        return false;
    });

    /**
     * 构建用户下拉框候选值
     */
    function userSelectOption(){
        $.ajax({
            type:'post',
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