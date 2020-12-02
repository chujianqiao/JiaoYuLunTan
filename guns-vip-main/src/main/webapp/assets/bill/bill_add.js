/**
 * 添加或者修改页面
 */
var BillInfoDlg = {
    data: {
        billId: "",
        userId: "",
        meetId: "",
        enterprise: "",
        taxpayerNumber: "",
        credentialsName: "",
        credentialsPath: "",
        food: "",
        hotel: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;

    $("#meetMemberId").val(Feng.getUrlParam("memberId"));

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/bill/addItem", function (data) {
            Feng.success("申请成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            parent.location.reload();
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("申请失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    //上传文件
    upload.render({
        elem: '#fileBtn'
        , url: Feng.ctxPath + '/holdForum/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                //$("#fileNameTip").val(file.name);
                var fileName = file.name;
                var fileType = fileName.substr(fileName.lastIndexOf("."));
                if(fileType.compare(".png") || fileType.compare(".jpg") || fileType.compare(".jpeg")){
                    $("#fileNameTip").val(file.name);
                    $("#fileNameTip").html(file.name);
                }
            });
        }
        , done: function (res) {
            var type = res.data.type;
            if(type != ".png" && type != ".jpg" && type != ".jpeg"){
                Feng.error("上传失败，文件格式不匹配");
            }else {
                $("#fileInputHidden").val(res.data.fileId);
                $("#credentialsPath").val(res.data.path);
                $("#credentialsName").val($("#fileNameTip").val());
                Feng.success(res.message);
            }
        }
        , error: function () {
            Feng.error("上传图片失败！");
        }
    });

    /**
     * 不区分大小写比较字符串
     * @param str
     * @returns {boolean}
     */
    String.prototype.compare = function(str) {
        //不区分大小写
        if(this.toLowerCase() == str.toLowerCase()) {
            return true;
        } else {
            return false;
        }
    }
});