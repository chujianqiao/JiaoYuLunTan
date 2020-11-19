/**
 * 详情对话框
 */
var SeatInfoDlg = {
    data: {
        seatId: "",
        meetId: "",
        meetType: "",
        colNum: "",
        rowNum: "",
        seatType: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/seat/detail?seatId=" + Feng.getUrlParam("seatId"));
    var result = ajax.start();
    form.val('seatForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        let checkRow = isInteger(data.field.rowNum);
        let checkCol = isInteger(data.field.colNum);
        let checkPlat = isInteger(data.field.platNum);
        if(!checkRow){
            Feng.error("行数必须为整数!");
            return false;
        }
        if(!checkCol){
            Feng.error("列数必须为整数！");
            return false;
        }
        if(!checkPlat){
            Feng.error("主席台列数必须为整数！");
            return false;
        }
        let ajax = new $ax(Feng.ctxPath + "/seat/editItem", function (data) {
            Feng.success("更新成功！");
            window.location.href = Feng.ctxPath + '/seat'
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/seat'
    });

    function isInteger(obj) {
        return obj%1 === 0
    }
});