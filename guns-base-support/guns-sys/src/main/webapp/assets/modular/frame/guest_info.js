layui.use(['form', 'upload', 'admin', 'element', 'ax', 'laydate'], function () {
    var $ = layui.jquery;
    var form = layui.form;
    var upload = layui.upload;
    var element = layui.element;
    var $ax = layui.ax;
    var admin = layui.admin;
    var laydate = layui.laydate;

    //获取用户详情
    var ajax = new $ax(Feng.ctxPath + "/system/currentUserInfo");
    var result = ajax.start();
    //用这个方法必须用在class有layui-form的元素上
    form.val('userInfoForm', result.data);
    $("#userId").val(result.data.userId);
    $("#pptNameTip").val(result.data.pptName);
    $("#wordNameTip").val(result.data.wordName);
    $("#introduction").val(result.data.introduction);
    //表单提交事件
    form.on('submit(guestInfoSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
            Feng.success("保存成功!");
            // 关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("保存失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", $("#userId").val());
        ajax.set("pptPath", $("#pptPath").val());
        ajax.set("pptName", $("#pptNameTip").val());
        ajax.set("wordPath", $("#wordPath").val());
        ajax.set("wordName", $("#wordNameTip").val());
        ajax.set("introduction", $("#introduction").val());
        ajax.start();
    });

    //上传文件
    upload.render({
        elem: '#pptBtn'
        , url: Feng.ctxPath + '/meetMember/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#pptNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#pptInputHidden").val(res.data.fileId);
            $("#pptPath").val(res.data.path);
            $("#pptName").val($("#pptNameTip").val());
            /*var ajax = new $ax(Feng.ctxPath + "/meetMember/updateFile", function (data) {
                if (data.message == "sizeError"){
                    Feng.error("上传失败，无参会信息！");
                } else if (data.message == "success") {
                    Feng.success("上传成功!");
                }else {
                    Feng.error("上传失败!");
                }

            }, function (data) {
                Feng.error("上传失败!" + data.responseJSON.message + "!");
            });
            ajax.set("pptPath",res.data.path);
            ajax.set("pptName",$("#pptNameTip").val());
            ajax.start();*/
            //Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传失败！");
        }
    });

    //上传文件
    upload.render({
        elem: '#wordBtn'
        , url: Feng.ctxPath + '/meetMember/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $("#wordNameTip").val(file.name);
            });
        }
        , done: function (res) {
            $("#wordInputHidden").val(res.data.fileId);
            $("#wordPath").val(res.data.path);
            $("#wordName").val($("#wordNameTip").val());

            /*var ajax = new $ax(Feng.ctxPath + "/meetMember/updateFile", function (data) {
                if (data.message == "sizeError"){
                    Feng.error("上传失败，无参会信息！");
                } else if (data.message == "success") {
                    Feng.success("上传成功!");
                }else {
                    Feng.error("上传失败!");
                }

            }, function (data) {
                Feng.error("上传失败!" + data.responseJSON.message + "!");
            });
            ajax.set("wordPath",res.data.path);
            ajax.set("wordName",$("#wordNameTip").val());
            ajax.start();*/

            //Feng.success(res.message);
        }
        , error: function () {
            Feng.error("上传失败！");
        }
    });
});