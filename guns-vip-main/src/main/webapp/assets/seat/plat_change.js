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
     * 分配座位
     */
    form.on('submit(btnSubmitSeat)', function (data) {
        let divId = $('#divId').val();
        let meetId = $('#meetId').val();
        let seatId = $('#seatId').val();
        // let seatDiv = $('#divId');
        let seatRow = divId.substring(divId.indexOf('_') + 1,divId.lastIndexOf('_'));
        let seatCol = divId.substring(divId.lastIndexOf('_') + 1);
        data.field.seatRow = seatRow;
        data.field.seatCol = seatCol;
        let unitVal = $('#unitName').val();
        if(unitVal == '' || unitVal == 'undefined'|| unitVal == undefined){
            Feng.error('请填写嘉宾名称');
        }else{
            let ajax = new $ax(Feng.ctxPath + "/seatDetail/platItem?seatId=" + seatId, function (data) {
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
        if(text == '主席台'){
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

});