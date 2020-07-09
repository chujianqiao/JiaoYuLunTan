/**
 * 用户详情对话框
 */
var UserInfoDlg = {
    data: {
        deptId: "",
        deptName: "",
        belongDomain: "",
        pName: ""

    }
};

layui.use(['layer', 'form', 'admin', 'laydate', 'ax', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;
    var layer = layui.layer;
    var formSelects = layui.formSelects;

    $(function () {
        forumSelectOption();
    })

    // 点击部门时
    $('#deptName').click(function () {
        var formName = encodeURIComponent("parent.UserInfoDlg.data.deptName");
        var formId = encodeURIComponent("parent.UserInfoDlg.data.deptId");
        var treeUrl = encodeURIComponent("/dept/tree");

        layer.open({
            type: 2,
            title: '部门选择',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#deptId").val(UserInfoDlg.data.deptId);
                $("#deptName").val(UserInfoDlg.data.deptName);
            }
        });
    });

    // 添加表单验证方法
    form.verify({
        psw: [/^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{8}$/, '密码必须由8位大小写字母加数字组合！'],
        repsw: function (value) {
            if (value !== $('#userForm input[name=password]').val()) {
                return '两次密码输入不一致';
            }
        }
    });

    // 渲染时间选择框
    laydate.render({
        elem: '#birthday'
    });

    /*// 表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/mgr/add", function (data) {
            console.log(data)
            if (data.message == 'codeError') {
                Feng.error("验证码错误！");
            }else if (data.message == 'phoneError') {
                Feng.error("手机号错误！");
            }else if (data.message == 'overTime') {
                Feng.error("验证码已过期！");
            }else {
                Feng.success("注册成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);

                //关掉对话框
                admin.closeThisDialog();
            }

        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        //添加 return false 可成功跳转页面
        return false;
    });*/

    /*//初始化所有的职位列表
    formSelects.config('selPosition', {
        searchUrl: Feng.ctxPath + "/position/listPositions",
        keyName: 'name',
        keyVal: 'positionId'
    });*/







    var countdownHandler = function(){
        var $button = $("#sentSmsCode");
        var number = 60;
        var countdown = function(){
            if (number == 0) {
                $button.attr("disabled",false);
                $button.html("发送验证码");
                number = 60;
                return;
            } else {
                $button.attr("disabled",true);
                $button.html(number + "秒后重新发送");
                number--;
            }
            setTimeout(countdown,1000);
        }
        setTimeout(countdown,1000);
    }
    //发送短信验证码
    $("#sentSmsCode").on("click", function(){
        var $phone = $("input[name=phone]");
        var data = {};
        data.phone = $.trim($phone.val());
        if(data.phone == ''){
            Feng.error('请输入手机号码');
            return;
        }
        var reg = /^1\d{10}$/;
        if(!reg.test(data.phone)){
            Feng.error('请输入合法的手机号码');
            return ;
        }
        $.ajax({
            url: Feng.ctxPath +"/check/checkSMS",
            async : true,
            type: "post",
            dataType: "text",
            data: data,
            success: function (data) {
                if(data != 'error'){
                    countdownHandler();
                    return ;
                }

            }
        });
    })



    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/reviewMajor/addAccount", function (data) {
            console.log(data)
            if (data.message == 'error') {
                Feng.error("生成失败！");
            }else {
                Feng.success("生成成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);

                //关掉对话框
                admin.closeThisDialog();
            }

        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();

        //添加 return false 可成功跳转页面
        return false;
    });


    /**
     * 构建论坛下拉框候选值
     * @param ownForumid
     */
    function forumSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/ownForum/listAll" ,
            success:function(response){
                var data=response.data;
                var forums = [];
                forums = data;

                var options;
                for (i = 0 ;i < forums.length ;i++){
                    var forum = data[i];
                    options += '<option value="'+ forum.forumId+ '" >'+ forum.forumName +'</option>';
                }
                $('#ownForumId').empty();
                $('#ownForumId').append(options);
                form.render('select');
            }
        })
    }


    form.on('radio(joinType)', function(data){

        if (data.value == 0){
            $("#ownForumDiv").attr("style","display:none")
        } else {
            $("#ownForumDiv").attr("style","display:block")
        }
    });


    // 点击上级角色时
    $('#pName').click(function () {
        var formName = encodeURIComponent("parent.UserInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.UserInfoDlg.data.belongDomain");
        var treeUrl = encodeURIComponent("/thesisDomain/tree");

        layer.open({
            type: 2,
            title: '父级领域',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/thesisDomain/thesisDomainAssign?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#belongDomain").val(UserInfoDlg.data.belongDomain);
                $("#pName").val(UserInfoDlg.data.pName);
            }
        });
    });

});