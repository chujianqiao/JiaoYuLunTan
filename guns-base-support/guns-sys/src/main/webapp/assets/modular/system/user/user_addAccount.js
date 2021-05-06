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
        meetSelectOption();
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
        psw: [/(?!.*[\u4E00-\u9FA5\s])(?!^[a-zA-Z]+$)(?!^[\d]+$)(?!^[^a-zA-Z\d]+$)^.{8,14}$/, '8~14位字母/数字以及标点符号至少包含2种，不允许有空格、中文！'],
        /*repsw: function (value) {
            if (value !== $('#userForm input[name=password]').val()) {
                return '两次密码输入不一致';
            }
        }*/
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
            //console.log(data)
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
    /*function forumSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/ownForum/listAll" ,
            success:function(response){
                var data=response.data;
                var forums = [];
                forums = data;

                var options;
                for (var i = 0 ;i < forums.length ;i++){
                    var forum = data[i];
                    options += '<option value="'+ forum.forumId+ '" >'+ forum.forumName +'</option>';
                }
                $('#ownForumId').empty();
                $('#ownForumId').append(options);
                form.render('select');
            }
        })
    }*/
    //选择论坛时立刻刷新
    form.on('select(meetId)', function(data){
        forumSelectOption();
    });
    function forumSelectOption(){
        var meetId = "";
        if ($("#meetId").val() != null){
            meetId = $("#meetId").val();
        }
        $.ajax({
            type:'post',
            // url:Feng.ctxPath + "/thesisDomain/list" ,
            url:Feng.ctxPath + "/forum/wrapList?meetId=" + meetId,
            success:function(response){
                var data=response.data;
                var forums = [];
                forums = data;
                var options;
                for (i = 0 ;i < forums.length ;i++){
                    var forum = data[i];
                    if(forum.status == "未发布"){
                        continue;
                    }
                    options += '<option value="'+ forum.forumId+ '" >'+ forum.forumName +'</option>';
                }
                $('#ownForumid').empty();
                $('#ownForumid').append("<option value=''>请选择论坛</option>");
                $('#ownForumid').append(options);
                form.render('select');
            }
        })
    }
    function meetSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/meet/wrapList" ,
            success:function(response){
                var data=response.data;
                var meet = [];
                meet = data;
                //console.log(meet)

                var options;
                for (var i = 0 ;i < meet.length ;i++){
                    if (meet[i].meetStatus == 1){
                        options += '<option value="'+ meet[i].meetId+ '" selected>'+ meet[i].meetName +'</option>';
                    } else {
                        options += '<option value="'+ meet[i].meetId+ '" >'+ meet[i].meetName +'</option>';
                    }

                }
                $('#meetId').empty();
                $('#meetId').append("<option value=''>请选择会议</option>");
                $('#meetId').append(options);
                form.render('select');
            }
        })
    }

    /*function domainSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/thesisDomain/list" ,
            success:function(response){
                var data=response.data;
                var domains = [];
                domains = data;

                var options;
                for (var i = 0 ;i < domains.length ;i++){
                    var domain = data[i];
                    options += '<option value="'+ domain.domainId+ '" >'+ domain.domainName +'</option>';
                }
                $('#belongDomain').empty();
                $('#belongDomain').append("<option value=''>请选择专家领域</option>");
                $('#belongDomain').append(options);
                form.render('select');
            }
        })
    }*/


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
            title: '专家领域',
            area: ['300px', '400px'],
            //content: Feng.ctxPath + '/thesisDomain/thesisDomainAssign?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#belongDomain").val(UserInfoDlg.data.belongDomain);
                $("#pName").val(UserInfoDlg.data.pName);
            }
        });
    });

    var isAccountOk = false;
    var isPasswordOk = false;
    var isPhoneOk = false;
    var isAccountOkGuest = false;
    var isPasswordOkGuest = false;
    var isPhoneOkGuest = false;
    var accounttips;
    $("#account").focus(function() {
        accounttips = layer.tips('设置后不可更改，中英文均可，最长14个英文或7个汉字', '#account',{tips:[1,'#000'],time: 30000});
    })
    $("#account").blur(function() {
        layer.close(accounttips);

        // 去除多余的空白字符
        var uname = this.value.trim();
        // 判断是否为空
        if (uname == '') {
            // 设置错误信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '用户名不允许为空';
            // 设置用户名标记为false
            isAccountOk = false;
            ifCanSub();
            // 终止程序
            return;
        }
        // 判断是否有非法字符(除了中英文,数字,下划线以外的字符)
        var charReg = /[^\u4E00-\u9FA5\w]/;
        var res = charReg.test(uname);
        // 如果res为真,表示有非法字符
        if (res) {
            // 设置错误信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '用户名仅支持中英文,数字和下划线,且不能为纯数字';
            // 设置用户名标记为false
            isAccountOk = false;
            ifCanSub();
            // 终止程序
            return;
        }
        // 经过上述判断后,说明都是合法的字符(中英文,下划线,数字),接下来判断是否为纯数字
        var numReg = /\D/;
        var res = numReg.test(uname);
        // 如果res为false,则表示出了数字以外无其他字符
        if (!res) {
            // 设置错误信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '用户名仅支持中英文,数字和下划线,且不能为纯数字';
            // 设置用户名标记为false
            isAccountOk = false;
            ifCanSub();
            // 终止程序
            return;
        }
        // 满足以上条件后,判断用户名字符串的长度
        var len = 0; // 表示用户名的长度
        for (var i = 0; i < uname.length; i++) {
            // 如果是中文,就+2；否则+1
            if (/[\u4e00-\u9fa5]/.test(uname[i])) {
                len += 2;
            } else {
                len += 1;
            }
            // 尽量避免执行过多的次数,一旦len超过14就不满足条件了
            if (len > 14) {
                break;
            }
        }
        // 判断是否满足条件
        if (len > 14) {
            // 设置错误信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '用户名不能超过7个汉字或14个字符';
            // 设置用户名标记为false
            isAccountOk = false;
            ifCanSub();
            // 终止程序
            return;
        } else {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'success';
            span.innerHTML = '√';
            // 设置成功的标志
            isAccountOk = true;
            ifCanSub();

            var ajax = new $ax(Feng.ctxPath + "/mgr/addCheck", function (data) {
                //console.log(data)
                if (data.message == 'existAccount') {
                    // 设置错误信息
                    span.className = 'error';
                    span.innerHTML = '此用户名已存在,请更换一个';
                    // 设置用户名标记为false
                    isAccountOk = false;
                    ifCanSub();
                    // 终止程序
                    return;
                }else {
                    // 设置成功信息
                    span.className = 'success';
                    span.innerHTML = '√';
                    // 设置成功的标志
                    isAccountOk = true;
                    ifCanSub();
                }

            }, function (data) {
            });
            ajax.set("account",this.value);
            ajax.start();
        }
        /*var pattern = /^[\u4e00-\u9fa5]{1,7}$|^[\A-Za-z]{1,14}$/,
            str = $("#account").val();
        alert(pattern.test(str));*/
    })
    $("#accountGuest").focus(function() {
        accounttips = layer.tips('设置后不可更改，中英文均可，最长14个英文或7个汉字', '#accountGuest',{tips:[1,'#000'],time: 30000});
    })
    $("#accountGuest").blur(function() {
        layer.close(accounttips);

        // 去除多余的空白字符
        var uname = this.value.trim();
        // 判断是否为空
        if (uname == '') {
            // 设置错误信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '用户名不允许为空';
            // 设置用户名标记为false
            isAccountOkGuest = false;
            ifCanSub();
            // 终止程序
            return;
        }
        // 判断是否有非法字符(除了中英文,数字,下划线以外的字符)
        var charReg = /[^\u4E00-\u9FA5\w]/;
        var res = charReg.test(uname);
        // 如果res为真,表示有非法字符
        if (res) {
            // 设置错误信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '用户名仅支持中英文,数字和下划线,且不能为纯数字';
            // 设置用户名标记为false
            isAccountOkGuest = false;
            ifCanSub();
            // 终止程序
            return;
        }
        // 经过上述判断后,说明都是合法的字符(中英文,下划线,数字),接下来判断是否为纯数字
        var numReg = /\D/;
        var res = numReg.test(uname);
        // 如果res为false,则表示出了数字以外无其他字符
        if (!res) {
            // 设置错误信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '用户名仅支持中英文,数字和下划线,且不能为纯数字';
            // 设置用户名标记为false
            isAccountOkGuest = false;
            ifCanSub();
            // 终止程序
            return;
        }
        // 满足以上条件后,判断用户名字符串的长度
        var len = 0; // 表示用户名的长度
        for (var i = 0; i < uname.length; i++) {
            // 如果是中文,就+2；否则+1
            if (/[\u4e00-\u9fa5]/.test(uname[i])) {
                len += 2;
            } else {
                len += 1;
            }
            // 尽量避免执行过多的次数,一旦len超过14就不满足条件了
            if (len > 14) {
                break;
            }
        }
        // 判断是否满足条件
        if (len > 14) {
            // 设置错误信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '用户名不能超过7个汉字或14个字符';
            // 设置用户名标记为false
            isAccountOkGuest = false;
            ifCanSub();
            // 终止程序
            return;
        } else {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'success';
            span.innerHTML = '√';
            // 设置成功的标志
            isAccountOkGuest = true;
            ifCanSub();

            var ajax = new $ax(Feng.ctxPath + "/mgr/addCheck", function (data) {
                //console.log(data)
                if (data.message == 'existAccount') {
                    // 设置错误信息
                    span.className = 'error';
                    span.innerHTML = '此用户名已存在,请更换一个';
                    // 设置用户名标记为false
                    isAccountOkGuest = false;
                    ifCanSub();
                    // 终止程序
                    return;
                }else {
                    // 设置成功信息
                    span.className = 'success';
                    span.innerHTML = '√';
                    // 设置成功的标志
                    isAccountOkGuest = true;
                    ifCanSub();
                }

            }, function (data) {
            });
            ajax.set("account",this.value);
            ajax.start();
        }
        /*var pattern = /^[\u4e00-\u9fa5]{1,7}$|^[\A-Za-z]{1,14}$/,
            str = $("#account").val();
        alert(pattern.test(str));*/
    })



    var passwordtips;
    $("#password").focus(function() {
        passwordtips = layer.tips('8~14位字母/数字以及标点符号至少包含2种，不允许有空格、中文', '#password',{tips:[1,'#000'],time: 30000});
    })
    $("#password").blur(function() {
        layer.close(passwordtips);
        var pass = this.value;
        var passTest = /(?!.*[\u4E00-\u9FA5\s])(?!^[a-zA-Z]+$)(?!^[\d]+$)(?!^[^a-zA-Z\d]+$)^.{8,14}$/;
        if (passTest.test(pass)) {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'success';
            span.innerHTML = '√';
            // 设置成功的标志
            isPasswordOk = true;
            ifCanSub();
        }else {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '密码设置不符合要求';
            // 设置成功的标志
            isPasswordOk = false;
            ifCanSub();
        }
    })
    $("#passwordGuest").focus(function() {
        passwordtips = layer.tips('8~14位字母/数字以及标点符号至少包含2种，不允许有空格、中文', '#passwordGuest',{tips:[1,'#000'],time: 30000});
    })
    $("#passwordGuest").blur(function() {
        layer.close(passwordtips);
        var pass = this.value;
        var passTest = /(?!.*[\u4E00-\u9FA5\s])(?!^[a-zA-Z]+$)(?!^[\d]+$)(?!^[^a-zA-Z\d]+$)^.{8,14}$/;
        if (passTest.test(pass)) {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'success';
            span.innerHTML = '√';
            // 设置成功的标志
            isPasswordOkGuest = true;
            ifCanSub();
        }else {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '密码设置不符合要求';
            // 设置成功的标志
            isPasswordOkGuest = false;
            ifCanSub();
        }
    })

    $("#phone").blur(function() {
        var phone = this.value.trim();
        var phoneTest = /^1(3[0-9]|4[5,7,8,9]|5[0,1,2,3,5,6,7,8,9]|6[2,5,6,7]|7[0,1,2,3,5,6,7,8]|8[0-9]|9[1,3,5,6,7,8,9])\d{8}$/;
        if (phoneTest.test(phone)) {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'success';
            span.innerHTML = '√';
            // 设置成功的标志
            isPhoneOk = true;
            ifCanSub();

            var ajax = new $ax(Feng.ctxPath + "/mgr/addCheck", function (data) {
                //console.log(data)
                if (data.message == 'existPhone') {
                    // 设置错误信息
                    span.className = 'error';
                    span.innerHTML = '此手机号已被绑定,请更换一个';
                    // 设置用户名标记为false
                    isPhoneOk = false;
                    ifCanSub();
                    // 终止程序
                    return;
                }else {
                    // 设置成功信息
                    span.className = 'success';
                    span.innerHTML = '√';
                    // 设置成功的标志
                    isPhoneOk = true;
                    ifCanSub();
                }

            }, function (data) {
            });
            ajax.set("phone",this.value);
            ajax.start();
        }else {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '手机号码格式不正确';
            // 设置成功的标志
            isPhoneOk = false;
            ifCanSub();
        }
    })
    $("#phoneGuest").blur(function() {
        var phone = this.value.trim();
        var phoneTest = /^1(3[0-9]|4[5,7,8,9]|5[0,1,2,3,5,6,7,8,9]|6[2,5,6,7]|7[0,1,2,3,5,6,7,8]|8[0-9]|9[1,3,5,6,7,8,9])\d{8}$/;
        if (phoneTest.test(phone)) {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'success';
            span.innerHTML = '√';
            // 设置成功的标志
            isPhoneOkGuest = true;
            ifCanSub();

            var ajax = new $ax(Feng.ctxPath + "/mgr/addCheck", function (data) {
                //console.log(data)
                if (data.message == 'existPhone') {
                    // 设置错误信息
                    span.className = 'error';
                    span.innerHTML = '此手机号已被绑定,请更换一个';
                    // 设置用户名标记为false
                    isPhoneOk = false;
                    ifCanSub();
                    // 终止程序
                    return;
                }else {
                    // 设置成功信息
                    span.className = 'success';
                    span.innerHTML = '√';
                    // 设置成功的标志
                    isPhoneOk = true;
                    ifCanSub();
                }

            }, function (data) {
            });
            ajax.set("phone",this.value);
            ajax.start();
        }else {
            // 设置成功信息
            var span = this.nextElementSibling;
            span.className = 'error';
            span.innerHTML = '手机号码格式不正确';
            // 设置成功的标志
            isPhoneOkGuest = false;
            ifCanSub();
        }
    })

    function ifCanSub() {
        if (isAccountOk && isPasswordOk && isPhoneOk){
            $("#registerSub").attr('disabled',false);
            $("#registerSub").removeClass("layui-bg-gray");
        }else {
            $("#registerSub").attr('disabled',true);
            $("#registerSub").addClass("layui-bg-gray");
        }
        if (isAccountOkGuest && isPasswordOkGuest && isPhoneOkGuest){
            $("#registerSubGuest").attr('disabled',false);
            $("#registerSubGuest").removeClass("layui-bg-gray");
        }else {
            $("#registerSubGuest").attr('disabled',true);
            $("#registerSubGuest").addClass("layui-bg-gray");
        }
    }
});