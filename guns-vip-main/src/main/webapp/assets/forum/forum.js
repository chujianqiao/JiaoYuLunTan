layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 分论坛表管理
     */
    var Forum = {
        tableId: "forumTable"
    };

    /**
     * 初始化表格的列
     */
    Forum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'forumId', hide: true, title: '分论坛ID'},
            {field: 'forumName', sort: true, title: '分论坛名称'},
            //{field: 'setNum', sort: true, title: '设置人数'},
            {field: 'existNum', sort: true, title: '已有人数'},
            {field: 'status', sort: true, title: '发布状态'},
            //{field: 'registerStartTime', sort: true, title: '注册开始时间'},
            //{field: 'registerEndTime', sort: true, title: '注册结束时间'},
            {field: 'startTime', sort: true, title: '参会开始时间'},
            {field: 'endTime', sort: true, title: '参会结束时间'},
            {field: 'location', sort: true, title: '论坛地点'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Forum.search = function () {
        var queryData = {};


        table.reload(Forum.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 跳转到添加页面
     */
    Forum.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/forum/add'
    };

    /**
    * 跳转到编辑页面
    *
    * @param data 点击按钮时候的行数据
    */
    Forum.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/forum/edit?forumId=' + data.forumId
    };

    /**
     * 导出excel按钮
     */
    Forum.exportExcel = function () {
        var checkRows = table.checkStatus(Forum.tableId);
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
    Forum.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/forum/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Forum.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("forumId", data.forumId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Forum.tableId,
        url: Feng.ctxPath + '/forum/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Forum.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Forum.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    Forum.jumpAddPage();

    });

    // 导出excel
    $('#btnExp').click(function () {
        Forum.exportExcel();
    });

    // 发布
    $('#btnPub').click(function () {
        Forum.publishForum();
    });

    /**
     * 发布
     */
    Forum.publishForum = function () {
        var checkRows = table.checkStatus(Forum.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要发布的论坛");
        } else {
            var forumIds = "";
            for (var i = 0;i < checkRows.data.length;i++){
                forumIds = forumIds + checkRows.data[i].forumId + ";";
            }
            var operation = function () {
                var ajax = new $ax(Feng.ctxPath + "/forum/publish", function (data) {
                    Feng.success("发布成功!");
                    table.reload(Forum.tableId);
                }, function (data) {
                    Feng.error("发布失败!" + data.responseJSON.message + "!");
                });
                ajax.set("forumIds", forumIds);
                ajax.start();
            };
            Feng.confirm("确认发布?", operation);
        }
    };


    // 工具条点击事件
    table.on('tool(' + Forum.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Forum.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            Forum.onDeleteItem(data);
        }
    });
});
