/**
 * 详情对话框
 */
var ReviewMajorInfoDlg = {
    data: {
        reviewId: "",
        direct: "",
        thesisCount: "",
        reviewCount: "",
        refuseCount: "",
        majorType: "",
        applyFrom: "",
        checkStatus: "",
        applyTime: "",
        agreeTime: "",
        refuseTime: "",
        cancelTime: "",
        belongDomain: "",
        pName: ""
    }
};

layui.use(['layer', 'form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var layer = layui.layer;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/reviewMajor/detail?reviewId=" + Feng.getUrlParam("reviewId"));
    var result = ajax.start();

    //转换状态值
    var checkStatus = result.data.checkStatus;
    if(checkStatus == 0){
        result.data.checkStatus = "已取消";
    }else if(checkStatus == 1){
        result.data.checkStatus = "申请中";
    }else if(checkStatus == 2){
        result.data.checkStatus = "已通过";
    }else if(checkStatus == 3){
        result.data.checkStatus = "未通过";
    }else{
        result.data.checkStatus = "-";
    }
    form.val('reviewMajorForm', result.data);

    /**
     * 转换申请状态
     * 字符串→数字
     * @param param
     * @returns {number}
     */
    function tranApplyType(param) {
        var checkStatus = param;
        var status = '';
        if(checkStatus == "已取消"){
            status = 0;
        }else if(checkStatus == "申请中"){
            status = 1;
        }else if(checkStatus == "已通过"){
            status = 2;
        }else if(checkStatus == "未通过"){
            status = 3;
        }else{
            status = 0;
        }
        return status;
    }

    /**
     * 关闭
     */
    $('#closeAdmin').click(function () {
        window.location.href = window.location.href = Feng.ctxPath + '/meetMember';
    });

});