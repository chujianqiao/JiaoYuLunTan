layui.use(['table', 'form', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 评审字典表管理
     */
    var ReviewDictionary = {
        tableId: "reviewDictionaryTable"
    };

    /**
     * 初始化表格的列
     */
    ReviewDictionary.initColumn = function () {
        //获取多语言
        var langs = layui.data('system').lang;
        return [[
            {type: 'checkbox'},
            {field: 'dicId', hide: true, title: '主键ID'},
            {field: 'dicName', sort: true, title: '评审字典项名称'},
            // {field: 'dicStatus', sort: true, title: '状态'},
            {field: 'dicStatus', align: "center", sort: true, templet: '#statusTpl', title: "状态"},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ReviewDictionary.search = function () {
        var queryData = {};
        table.reload(ReviewDictionary.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    ReviewDictionary.openAddDlg = function () {
        func.open({
            title: '添加评审字典项',
            content: Feng.ctxPath + '/reviewDictionary/add',
            tableId: ReviewDictionary.tableId
        });
    };

     /**
      * 点击编辑
      * @param data 点击按钮时候的行数据
      */
      ReviewDictionary.openEditDlg = function (data) {
          func.open({
              title: '修改评审字典项',
              content: Feng.ctxPath + '/reviewDictionary/edit?dicId=' + data.dicId,
              tableId: ReviewDictionary.tableId
          });
      };

    /**
     * 导出excel按钮
     */
    ReviewDictionary.exportExcel = function () {
        var checkRows = table.checkStatus(ReviewDictionary.tableId);
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
    ReviewDictionary.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/reviewDictionary/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ReviewDictionary.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dicId", data.dicId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ReviewDictionary.tableId,
        url: Feng.ctxPath + '/reviewDictionary/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ReviewDictionary.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ReviewDictionary.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    ReviewDictionary.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        ReviewDictionary.exportExcel();
    });

    // 修改字典状态
    form.on('switch(status)', function (obj) {
        debugger;
        var dicId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;
        ReviewDictionary.changeDictStatus(dicId, checked);
    });

    ReviewDictionary.changeDictStatus = function (dicId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/reviewDictionary/freeItem", function (data) {
                Feng.success("解除冻结成功!");
            }, function (data) {
                Feng.error("解除冻结失败!");
                table.reload(ReviewDictionary.tableId);
            });
            ajax.set("dicId", dicId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/reviewDictionary/unFreeItem", function (data) {
                Feng.success("冻结成功!");
            }, function (data) {
                Feng.error("冻结失败!" + data.responseJSON.message + "!");
                table.reload(ReviewDictionary.tableId);
            });
            ajax.set("dicId", dicId);
            ajax.start();
        }
    }

    // 工具条点击事件
    table.on('tool(' + ReviewDictionary.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ReviewDictionary.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ReviewDictionary.onDeleteItem(data);
        }
    });
});
