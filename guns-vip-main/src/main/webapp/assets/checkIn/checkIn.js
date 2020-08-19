layui.use(['table', 'form', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 报到签到表管理
     */
    var CheckIn = {
        tableId: "checkInTable",
        condition: {
            signStatus: "",
        }
    };

    /**
     * 初始化表格的列
     */
    CheckIn.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'checkId', hide: true, title: '报到签到ID'},
            {field: 'name', sort: true, title: '姓名'},
            //{field: 'post', sort: true, title: '职称'},
            {field: 'roleName', sort: true, title: '参会角色'},
            {field: 'registerStatus', sort: true, title: '报到状态'},
            {field: 'registerTime', sort: true, title: '报到时间'},
            {field: 'signStatus', sort: true, title: '签到状态'},
            {field: 'signTime', sort: true, title: '签到时间'},
            {field: 'signPlace', sort: true, title: '签到地点'},
            {align: 'center', toolbar: '#tableBar', minWidth: 180, title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    CheckIn.search = function () {
        var queryData = {};
        queryData['signStatus'] = $("#signStatus").val();

        table.reload(CheckIn.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    form.on('select(status)', function(data){
        CheckIn.search();
    });



    /**
     * 跳转到添加页面
     */
    CheckIn.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/checkIn/add'
    };

    /**
    * 跳转到编辑页面
    *
    * @param data 点击按钮时候的行数据
    */
    CheckIn.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/checkIn/edit?checkId=' + data.checkId
    };

    /**
     * 跳转到详情页面
     *
     * @param data 点击按钮时候的行数据
     */
    CheckIn.onDetail = function (data) {
        window.location.href = Feng.ctxPath + '/checkIn/toDetail?checkId=' + data.checkId
    };

    /**
     * 导出excel按钮
     */
    CheckIn.exportExcel = function () {
        var checkRows = table.checkStatus(CheckIn.tableId);
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
    CheckIn.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/checkIn/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CheckIn.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("checkId", data.checkId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + CheckIn.tableId,
        url: Feng.ctxPath + '/checkIn/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: CheckIn.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        CheckIn.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    CheckIn.jumpAddPage();

    });

    // 导出excel
    $('#btnExp').click(function () {
        CheckIn.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + CheckIn.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            CheckIn.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            CheckIn.onDeleteItem(data);
        } else if (layEvent === 'detail') {
            CheckIn.onDetail(data);
        }
    });


    //-------------------------------------------------------------------------

    /**
     * 报到签到表管理
     */
    var CheckInForum = {
        tableId: "checkInTableForum",
        condition: {
            signStatus: "",
        }
    };

    /**
     * 初始化表格的列
     */
    CheckInForum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'checkId', hide: true, title: '报到签到ID'},
            {field: 'name', sort: true, title: '姓名'},
            //{field: 'post', sort: true, title: '职称'},
            {field: 'roleName', sort: true, title: '参会角色'},
            {field: 'forumName', sort: true, title: '参会分论坛'},
            {field: 'registerStatus', sort: true, title: '报到状态'},
            {field: 'registerTime', sort: true, title: '报到时间'},
            {field: 'signStatus', sort: true, title: '签到状态'},
            {field: 'signTime', sort: true, title: '签到时间'},
            {field: 'signPlace', sort: true, title: '签到地点'},
            {align: 'center', toolbar: '#tableBarForum', minWidth: 180, title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    CheckInForum.search = function () {
        var queryData = {};
        queryData['signStatus'] = $("#signStatus").val();

        table.reload(CheckInForum.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    form.on('select(statusForum)', function(data){
        CheckInForum.search();
    });



    /**
     * 跳转到添加页面
     */
    CheckInForum.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/checkIn/add'
    };

    /**
     * 跳转到编辑页面
     *
     * @param data 点击按钮时候的行数据
     */
    CheckInForum.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/checkIn/edit?checkId=' + data.checkId
    };

    /**
     * 跳转到详情页面
     *
     * @param data 点击按钮时候的行数据
     */
    CheckInForum.onDetail = function (data) {
        window.location.href = Feng.ctxPath + '/checkIn/toDetail?checkId=' + data.checkId
    };

    /**
     * 导出excel按钮
     */
    CheckInForum.exportExcel = function () {
        var checkRows = table.checkStatus(CheckInForum.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResultForum.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CheckInForum.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/checkIn/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CheckInForum.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("checkId", data.checkId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResultForum = table.render({
        elem: '#' + CheckInForum.tableId,
        url: Feng.ctxPath + '/checkIn/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: CheckInForum.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearchForum').click(function () {
        CheckInForum.search();
    });

    // 添加按钮点击事件
    $('#btnAddForum').click(function () {

        CheckInForum.jumpAddPage();

    });

    // 导出excel
    $('#btnExpForum').click(function () {
        CheckInForum.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + CheckInForum.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'editForum') {
            CheckInForum.jumpEditPage(data);
        } else if (layEvent === 'deleteForum') {
            CheckInForum.onDeleteItem(data);
        } else if (layEvent === 'detailForum') {
            CheckInForum.onDetail(data);
        }
    });
});
