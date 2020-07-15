layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 赞助环节表管理
     */
    var SocialLink = {
        tableId: "socialLinkTable"
    };

    /**
     * 初始化表格的列
     */
    SocialLink.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'linkId', hide: true, title: '环节ID'},
            {field: 'linkName', sort: true, title: '环节名称'},
            {field: 'description', sort: true, title: '备注'},
            {field: 'status', sort: true, title: '状态'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'sort', sort: true, title: '排序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    SocialLink.search = function () {
        var queryData = {};


        table.reload(SocialLink.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    SocialLink.openAddDlg = function () {
        func.open({
            title: '添加赞助环节表',
            content: Feng.ctxPath + '/socialLink/add',
            tableId: SocialLink.tableId
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      SocialLink.openEditDlg = function (data) {
          func.open({
              title: '修改赞助环节表',
              content: Feng.ctxPath + '/socialLink/edit?linkId=' + data.linkId,
              tableId: SocialLink.tableId
          });
      };


    /**
     * 导出excel按钮
     */
    SocialLink.exportExcel = function () {
        var checkRows = table.checkStatus(SocialLink.tableId);
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
    SocialLink.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/socialLink/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(SocialLink.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("linkId", data.linkId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + SocialLink.tableId,
        url: Feng.ctxPath + '/socialLink/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: SocialLink.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        SocialLink.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    SocialLink.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        SocialLink.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + SocialLink.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            SocialLink.openEditDlg(data);
        } else if (layEvent === 'delete') {
            SocialLink.onDeleteItem(data);
        }
    });
});
