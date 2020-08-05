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

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects','upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var table = layui.table;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/thesis/detail?thesisId=" + Feng.getUrlParam("thesisId"));
    var result = ajax.start();
    form.val('thesisForm', result.data);
    var fileName = result.data.fileName;
    if(fileName != null && fileName != "" && fileName != 'undefined'){
        $("#fileNameTip").html(fileName);
    }

    //构建评审字典radio选项
    var ajaxDic = new $ax(Feng.ctxPath + "/reviewDictionary/list?dicStatus=1");
    var resultDic = ajaxDic.start();
    for (var i = 0; i < resultDic.count; i++){
        var dicName = resultDic.data[i].dicName;
        var input=$("<input>");
        input.attr("type","checkbox");
        input.attr("name","status");
        input.attr("value",dicName);
        input.attr("title",dicName);
        // input.attr("disabled","");
        input.attr("disabled",false);
        $("#divLink").append(input);
    }
    form.render('checkbox');
    //隐藏是否优秀的选项
    // var isPass = result.data.reviewResult;
    // if(isPass == 1){
    //     //如果论文已通过，禁用是否通过的单选框，不隐藏是否优秀的单选框
    //     document.getElementById("greatDiv").style.display="";
    //     var input = $("#isPass").find("input:radio");
    //     input.attr("disabled","disabled");
    // }else{
    //     document.getElementById("greatDiv").style.display="none";
    // }

    //
    // form.on('radio(reviewResult)', function(data){
    //     debugger;
    //     if(data.value == '0'){
    //         $("input:checkbox[name = status]").each(function(i){
    //             $(this).attr("disabled","");
    //         })
    //     } else {
    //         debugger;
    //         $("input:checkbox[name = status]").each(function(i){
    //             debugger;
    //             var item =  $(this)
    //             var input = item[0];
    //             var disabled = input.disabled;
    //             var dis = item.context.disabled;
    //
    //             input.disabled = false;
    //             item.context.disabled = false;
    //             // $(this).removeAttr("disabled"); //解除禁用
    //         })
    //     }
    // });


    //表单提交事件
    form.on('submit(btnReview)', function (data) {
        debugger;
        var score = data.field.score;
        if(score >= 0 && score <=100){
            debugger;
            var dicts = "";
            $("input:checkbox[name = status]:checked").each(function(i){
                //使用循环遍历迭代的方式得到所有被选中的checkbox复选框
                dicts += $(this).val() + ";";
            })
            data.field.status = dicts;

            var ajax = new $ax(Feng.ctxPath + "/thesis/reviewItem", function (data) {
                Feng.success("提交评审结果成功！");
                window.location.href = Feng.ctxPath + '/thesis'
            }, function (data) {
                Feng.error("提交评审结果失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        }else{
            Feng.error("评分区间 0~100分");
        }
        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/thesis'
    });

    $('#downloadBtn').click(function(){
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        // form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/meetMember/downloadThesis?thesisId=" + Feng.getUrlParam("thesisId"));    // 此处填写文件下载提交路径
        $("body").append(form);    // 将表单放置在web中
        form.submit();

    });

    $('#loadBtn').click(function(){
        /*var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        form.attr("target","_blank");
        form.attr("method","get");
        form.attr("action",Feng.ctxPath + "/assets/pdfview/web/viewer.html");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        input1.attr("name","file");
        input1.attr("value","/meetMember/loadThesisPdf");
        input1.attr("style","display:none");
        form.append(input1);
        var input=$("<input>");
        input.attr("name","thesisId");
        input.attr("value",Feng.getUrlParam("thesisId"));
        input.attr("style","display:none");
        form.append(input);
        $("body").append(form);    // 将表单放置在web中
        form.submit();*/

        window.open("/assets/pdfview/web/viewer.html?file=" + encodeURIComponent("/meetMember/loadThesisPdf?thesisId=" + Feng.getUrlParam("thesisId")));

    });

});