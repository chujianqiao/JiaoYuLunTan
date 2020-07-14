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
    debugger;
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

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        debugger;
        var param = data.field.checkStatus;
        var checkStatus = tranApplyType(param);
        data.field.checkStatus = checkStatus;

        if(checkStatus == 0){
            var operation = function () {
                var ajax = new $ax(Feng.ctxPath + "/reviewMajor/editItem", function (data) {
                    Feng.success("修改成功！");
                    //传给上个页面，刷新table用
                    admin.putTempData('formOk', true);
                    //关掉对话框
                    admin.closeThisDialog();
                }, function (data) {
                    Feng.error("修改失败！" + data.responseJSON.message)
                });
                ajax.set(data.field);
                ajax.start();
            };
            Feng.confirm("是否确认修改数据?", operation);
        }else {
            Feng.success("请先取消申请！");
        }
        return false;
    });

    //同意申请
    form.on('submit(btnAgree)', function (data) {
        debugger;
        var param = data.field.checkStatus;
        var checkStatus = tranApplyType(param);
        data.field.checkStatus = checkStatus;
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/reviewMajor/agreeApply", function (data) {
                Feng.success("通过申请成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                Feng.error("通过申请失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        };
        Feng.confirm("是否确认通过申请?", operation);
        return false;
    });

    //驳回申请
    form.on('submit(btnDisagree)', function (data) {
        debugger;
        var param = data.field.checkStatus;
        var checkStatus = tranApplyType(param);
        data.field.checkStatus = checkStatus;
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/reviewMajor/disAgreeApply", function (data) {
                Feng.success("驳回申请成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                Feng.error("驳回申请失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        };
        Feng.confirm("是否确认驳回申请?", operation);
        return false;
    });

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


    // 点击上级角色时
    $('#pName').click(function () {
        var formName = encodeURIComponent("parent.ReviewMajorInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.ReviewMajorInfoDlg.data.belongDomain");
        var treeUrl = encodeURIComponent("/thesisDomain/tree");

        layer.open({
            type: 2,
            title: '父级领域',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/thesisDomain/thesisDomainAssign?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#belongDomain").val(ReviewMajorInfoDlg.data.belongDomain);
                $("#pName").val(ReviewMajorInfoDlg.data.pName);
            }
        });
    });
});