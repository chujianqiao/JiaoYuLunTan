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
    var ajax = new $ax(Feng.ctxPath + "/thesisReviewMiddle/thesisResult?thesisId=" + Feng.getUrlParam("thesisId"));
    var result = ajax.start();
    console.log(result.data);
    form.val('thesisForm', result.data);
    var fileName = result.data.fileName;
    if(fileName != null && fileName != "" && fileName != 'undefined'){
        $("#fileNameTip").html(fileName);
    }


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
    form.on('radio(reviewResult)', function(data){
        if(data.value == '1'){
            debugger;
            var ajaxDic = new $ax(Feng.ctxPath + "/reviewDictionary/list?dicStatus=1");
            var resultDic = ajaxDic.start();
            for (var i = 0; i < resultDic.count; i++){
                var objId = resultDic.data[i].dicId;
                document.getElementById(objId).disabled=false;
            }
            form.render('checkbox');
        }
        else {
            debugger;
            var ajaxDic = new $ax(Feng.ctxPath + "/reviewDictionary/list?dicStatus=1");
            var resultDic = ajaxDic.start();
            for (var i = 0; i < resultDic.count; i++){
                var objId = resultDic.data[i].dicId;
                // var obj = document.getElementById(objId);
                document.getElementById(objId).disabled=true;
            }
            form.render('checkbox');
        }
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