layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 会议表管理
     */
    var Meet = {
        tableId: "meetTable"
    };

    /**
     * 初始化表格的列
     */
    Meet.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'meetId', hide: true, title: '会议ID'},
            {field: 'meetTopic', sort: true, title: '会议主题'},
            {field: 'meetDesc', sort: true, title: '会议描述'},
            {field: 'place', sort: true, title: '会议地点'},
            {field: 'beginTime', sort: true, title: '开始时间',minWidth:160},
            {field: 'endTime', sort: true, title: '结束时间',minWidth:160},
            // {field: 'applyUser', sort: true, title: '申请人ID'},
            // {field: 'registerTime', sort: true, title: '注册时间'},
            // {field: 'subForm', sort: true, title: '分论坛ID'},
            // {field: 'thesisId', sort: true, title: '论文ID'},
            {field: 'userName', sort: true, title: '申请人'},
            {field: 'thesisTitle', sort: true, title: '参会论文题目',minWidth:130},
            // {field: 'engTitle', sort: true, title: '英文题目'},
            // {field: 'cnKeyword', sort: true, title: '中文关键词'},
            // {field: 'engKeyword', sort: true, title: '英文关键词'},
            // {field: 'cnAbstract', sort: true, title: '中英文摘'},
            // {field: 'engAbstract', sort: true, title: '英文摘要'},
            {field: 'thesisDirect', sort: true, title: '论文研究方向'},
            {field: 'speak', sort: true, title: '申请发言'},
            {align: 'center', toolbar: '#tableBar', title: '操作',minWidth:145}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Meet.search = function () {
        var queryData = {};

        queryData['meetTopic'] = $('#meetTopic').val();

        table.reload(Meet.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 跳转到添加页面
     */
    Meet.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/meet/add'
    };

    /**
    * 跳转到编辑页面
    * @param data 点击按钮时候的行数据
    */
    Meet.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/meet/edit?meetId=' + data.meetId
    };

    /**
     * 跳转到详情界面（仅查看）
     * @param data 点击按钮时候的行数据
     */
    Meet.jumpDetailPage = function (data) {
        window.location.href = Feng.ctxPath + '/meet/noEdit?meetId=' + data.meetId
    };

    /**
     * 导出excel按钮
     */
    Meet.exportExcel = function () {
        var checkRows = table.checkStatus(Meet.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除
     * @param data 点击按钮时候的行数据
     */
    Meet.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meet/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Meet.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("meetId", data.meetId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Meet.tableId,
        url: Feng.ctxPath + '/meet/wraplist',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Meet.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Meet.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Meet.jumpAddPage();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Meet.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Meet.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'edit') {
            Meet.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            Meet.onDeleteItem(data);
        } else if (layEvent === 'detail') {
            Meet.jumpDetailPage(data);
        }
    });
});
