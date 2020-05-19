/**
 * 详情对话框
 */
var ReviewUnitInfoDlg = {
    data: {
        reviewId: "",
        location: "",
        year: "",
        repName: "",
        post: "",
        education: "",
        createTime: ""
    }
};

layui.use(['form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //渲染时间选择框
    var laydate = layui.laydate;
    laydate.render({
         elem: '#year' //指定元素
        ,type: 'year'
    });

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/reviewUnit/detail?reviewId=" + Feng.getUrlParam("reviewId"));
    var result = ajax.start();
    form.val('reviewUnitForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        debugger;
        var ajax = new $ax(Feng.ctxPath + "/reviewUnit/editItem", function (data) {
            Feng.success("更新成功！");
            window.location.href = Feng.ctxPath + '/reviewUnit'
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    $('#cancel').click(function(){
        window.location.href = Feng.ctxPath + '/reviewUnit'
    });

    // $(function(){
    //     createSelectOption();
    // });

    function createSelectOption(){
        debugger;
        var _html="";
        for(var _i=0;_i<5;_i++){
            _html=_html+"<option value='"+"2020"+"'>"+"2020"+"</option>";
        }
        var html=$("#year");
        // html = html.appendChild(_html);
        // $("#year").append(_html);
        $("#year").append(_html);

        //console.log(html);
    }



});