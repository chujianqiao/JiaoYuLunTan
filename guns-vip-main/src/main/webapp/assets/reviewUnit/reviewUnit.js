layui.use(['table', 'admin', 'ax', 'func','upload'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var upload = layui.upload;

    /**
     * 理事单位表管理
     */
    var ReviewUnit = {
        tableId: "reviewUnitTable"
    };

    /**
     * 初始化表格的列
     */
    ReviewUnit.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'reviewId', hide: true, title: '单位ID'},
            {field: 'reviewName', sort: true, title: '单位名称'},
            {field: 'location', sort: true, title: '单位所在地'},
            {field: 'year', sort: true, title: '担任理事单位年份'},
            {field: 'repName', sort: true, title: '代表姓名'},
            {field: 'post', sort: true, title: '代表职称/职务'},
            {field: 'education', sort: true, title: '学历'},
            {field: 'createTime', sort: true, title: '导入时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作',minWidth: 180}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ReviewUnit.search = function () {
        debugger;
        var queryData = {};
        queryData['reviewName'] = $("#reviewName").val();
        table.reload(ReviewUnit.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 跳转到添加页面
     */
    ReviewUnit.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/reviewUnit/add'
    };

    /**
    * 跳转到编辑页面
    *
    * @param data 点击按钮时候的行数据
    */
    ReviewUnit.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/reviewUnit/edit?reviewId=' + data.reviewId
    };

    /**
     * 下载模板
     * @param data 点击按钮时候的行数据
     */
    ReviewUnit.download = function (data) {
        debugger;
        // var ajax = new $ax(Feng.ctxPath + "/reviewUnit/download", function (data) {
        //     Feng.success("下载成功!");
        //     table.reload(ReviewUnit.tableId);
        // }, function (data) {
        //     Feng.error("下载失败!" + data.responseJSON.message + "!");
        // });
        // ajax.start();
        var form=$("<form>");    // 定义一个form表单
        form.attr("style","display:none");
        form.attr("target","_blank");
        form.attr("method","post");
        form.attr("action",Feng.ctxPath + "/reviewUnit/download");    // 此处填写文件下载提交路径
        var input1=$("<input>");
        // input1.attr("type","hidden");
        // input1.attr("name","id");    // 后台接收参数名
        // input1.attr("value","");
        $("body").append(form);    // 将表单放置在web中
        // form.append(input1);
        form.submit();
    };

    /**
     * 导出excel按钮
     */
    ReviewUnit.exportExcel = function () {
        var checkRows = table.checkStatus(ReviewUnit.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    ReviewUnit.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/reviewUnit/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ReviewUnit.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("reviewId", data.reviewId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ReviewUnit.tableId,
        url: Feng.ctxPath + '/reviewUnit/wraplist',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ReviewUnit.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ReviewUnit.search();
    });

    // 下载模板按钮点击事件
    $('#btnDown').click(function () {
        ReviewUnit.download();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    ReviewUnit.jumpAddPage();

    });

    // 导出excel
    $('#btnExp').click(function () {
        ReviewUnit.exportExcel();
    });

    // 导入excel
    var uploadInst = upload.render({
        elem: '#btnImp'
        , url: '/reviewUnit/uploadExcel'
        ,accept: 'file'
        , done: function (res) {
            debugger;
            table.reload(ReviewUnit.tableId, {url: Feng.ctxPath + "/reviewUnit/getUploadData"});

            Feng.success("导入成功!");
            // window.location.href = Feng.ctxPath + "/reviewUnit/list";
            table.reload(ReviewUnit.tableId, {url: Feng.ctxPath + "/reviewUnit/list"});
            window.location.href = Feng.ctxPath + "/reviewUnit";
        }
        , error: function () {
            //请求异常回调
        }
    });


    // 工具条点击事件
    table.on('tool(' + ReviewUnit.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ReviewUnit.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            ReviewUnit.onDeleteItem(data);
        }
    });
});
