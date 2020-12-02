/**
 * 详情对话框
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



    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/bill/detail?meetMemberId=" + Feng.getUrlParam("meetMemberId"));
    var result = ajax.start();
    form.val('billForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/bill/editItem", function (data) {
            Feng.success("更新成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/bill'
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

    // 添加按钮点击事件
    $('#download').click(function () {
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        //form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/system/download");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        input1.attr("type","hidden");
        input1.attr("name","name");    // 后台接收参数名
        input1.attr("value",$("#credentialsName").val());
        var input2=$("<input>");
        input2.attr("type","hidden");
        input2.attr("name","path");    // 后台接收参数名
        input2.attr("value",$("#credentialsPath").val());
        $("body").append(form);    // 将表单放置在web中
        form.append(input1);
        form.append(input2);
        form.submit();    // 表单提交

    });
});