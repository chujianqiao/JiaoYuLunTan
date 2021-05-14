layui.use(['layer', 'table', 'form', 'ax', 'laydate'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    var form = layui.form;

    /**
     * 系统管理--操作日志
     */
    var LoginLog = {
        tableId: "logTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    LoginLog.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'operationLogId', hide: true, sort: true, title: 'id'},
            {field: 'logType', align: "center", sort: true, title: '日志类型'},
            {field: 'logName', align: "center", sort: true, title: '日志名称'},
            {field: 'userName', align: "center", sort: true, title: '用户名称'},
            {field: 'className', align: "center", sort: true, title: '类名'},
            {field: 'method', align: "center", sort: true, title: '方法名'},
            {field: 'createTime', align: "center", sort: true, title: '时间'},
            {field: 'regularMessage', align: "center", sort: true, title: '具体消息'},
            {align: 'center', toolbar: '#tableBar', title: '操作', minWidth: 100}
        ]];
    };

    /**
     * 点击查询按钮
     */
    LoginLog.search = function () {
        var queryData = {};
        queryData['beginTime'] = $("#beginTime").val();
        queryData['endTime'] = $("#endTime").val();
        queryData['logName'] = $("#logName").val();
        queryData['logType'] = $("#logType").val();
        $("#beginTimeExp").val($("#beginTime").val());
        $("#endTimeExp").val($("#endTime").val());
        $("#logNameExp").val($("#logName").val());
        table.reload(LoginLog.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    form.on('select(logType)', function(data){
        LoginLog.search();
    });

    /**
     * 导出excel按钮
     */
    LoginLog.exportExcel = function () {
        var checkRows = table.checkStatus(LoginLog.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 日志详情
     */
    LoginLog.logDetail = function (param) {
        var ajax = new $ax(Feng.ctxPath + "/log/detail/" + param.operationLogId, function (data) {
            Feng.infoDetail("日志详情", data.regularMessage);
        }, function (data) {
            Feng.error("获取详情失败!");
        });
        ajax.start();
    };



    /**
     * 全部导出excel按钮
     */
    LoginLog.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/log/list',
            type: 'post',
            data: {
                "logType":$("#logType").val(),
                "beginTime":$("#beginTimeExp").val(),
                "endTime":$("#endTimeExp").val(),
                "logName":$("#logNameExp").val()
            },
            async: false,
            dataType: 'json',
            success: function (res) {
                //使用table.exportFile()导出数据
                //console.log(res.data);
                table.exportFile('exportTable', res.data, 'xlsx');
            }
        });
    };
    table.render({
        elem: '#tableExpAll',
        id: 'exportTable',
        title: '业务日志全部数据',
        cols: [[ //表头
            {
                field: 'logType',
                title: '日志类型',
            }, {
                field: 'logName',
                title: '日志名称',
            }, {
                field: 'userName',
                title: '用户名称',
            }, {
                field: 'className',
                title: '类名',
            }, {
                field: 'method',
                title: '方法名',
            }, {
                field: 'createTime',
                title: '时间',
            }, {
                field: 'regularMessage',
                title: '具体消息',
            }
        ]]
    });


    //渲染时间选择框
    laydate.render({
        elem: '#beginTime'
    });

    //渲染时间选择框
    laydate.render({
        elem: '#endTime'
    });

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + LoginLog.tableId,
        url: Feng.ctxPath + '/log/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: LoginLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        LoginLog.search();
    });



    // 全部导出excel
    $('#btnExpAll').click(function () {
        LoginLog.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + LoginLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'detail') {
            LoginLog.logDetail(data);
        }
    });
});
