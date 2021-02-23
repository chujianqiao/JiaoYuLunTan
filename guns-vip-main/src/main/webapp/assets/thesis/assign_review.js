/**
 * 详情对话框
 */
var ThesisInfoDlg = {
    data: {
        thesisId: "",
        thesisTitle: "",
        engTitle: "",
        thesisUser: "",
        status: "",
        reviewResult: "",
        isgreat: "",
        greatNum: "",
        greatId: "",
        applyTime: "",
        thesisText: "",
        score: "",
        reviewUser: "",
        great: "",
        cnKeyword: "",
        engKeyword: "",
        cnAbstract: "",
        engAbstract: "",
        thesisDirect: "",
        thesisPath: "",
        fileName: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects','upload','selectPlus', 'formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var table = layui.table;
    var selectPlus = layui.selectPlus;
    var formSelects = layui.formSelects;



    //获取详情信息，填充表单
    var thesisId = Feng.getUrlParam("thesisId");
    thesisId = thesisId.toString().split(";");
    var ajax = new $ax(Feng.ctxPath + "/thesis/detail?thesisId=" + thesisId[0]);
    var result = ajax.start();
    form.val('thesisForm', result.data);
    // var ajaxMajor = new $ax(Feng.ctxPath + "/thesis/majorList");
    // var majors = ajaxMajor.start();
    var belongDomain = result.data.belongDomain;
    majorSelectOption();
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var reviewUsers = data.field.reviewUser;
        if (reviewUsers != "" && reviewUsers != null){
            var ajax = new $ax(Feng.ctxPath + "/thesis/assignItem", function (data) {
                Feng.success("分配成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
                // window.location.href = Feng.ctxPath + '/thesis'
            }, function (data) {
                Feng.error("分配失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.set("reviewBatch","1");
            ajax.set("thesisIds",Feng.getUrlParam("thesisId"));
            ajax.start();

            return false;
        } else {
            Feng.error("请选择专家后进行提交。");
            return false;
        }

    });

    form.on('submit(btnSubmitAgain)', function (data) {
        var reviewUsers = data.field.reviewUser;
        var reviewUserArr = reviewUsers.split(",");
        if (reviewUserArr.length == 3){
            var ajax = new $ax(Feng.ctxPath + "/thesis/assignItem", function (data) {
                Feng.success("分配成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
                // window.location.href = Feng.ctxPath + '/thesis'
            }, function (data) {
                Feng.error("分配失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.set("reviewBatch","2");
            ajax.set("thesisIds",Feng.getUrlParam("thesisId"));
            ajax.start();
        } else {
            Feng.error("请选择三个评审专家！")
        }


        return false;
    });

    //初始化评审专家列表
    formSelects.config('selPosition', {
        searchUrl: Feng.ctxPath + "/thesis/majorList",
        searchName: 'belongDomain',      //自定义搜索内容的key值
        searchVal: result.data.belongDomain,
        keyName: 'name',
        keyVal: 'reviewId'
    });

    function majorSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/thesis/majorList?belongDomain=" + belongDomain ,
            success:function(response){
                var data=response.data;
                var majors = [];
                majors = data;

                var options = '<option value="" >请选择专家（可手动输入专家名称筛选）</option>';
                for (var i = 0 ;i < majors.length ;i++){
                    var major = data[i];
                    options += '<option value="'+ major.reviewId+ '" >'+ major.name +'</option>';
                }
                $('#reviewMajor').empty();
                //$('#reviewMajor').append("<option value=''>请选择专家</option>");
                $('#reviewMajor').append(options);

                form.render('select');
            }
        })
    }
});