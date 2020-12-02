layui.use(['table', 'form', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 自动回复表管理
     */
    var Answer = {
        tableId: "answerTable"
    };

    /**
     * 初始化表格的列
     */
    Answer.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'answerId', hide: true, title: '自动回复ID'},
            {field: 'answerKey', sort: true, title: '问题'},
            {field: 'answerValue', sort: true, title: '答案'},
            {field: 'status', align: "center", sort: true, templet: '#statusTpl', title: '启用状态'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'sort', sort: true, title: '排序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Answer.search = function () {
        var queryData = {};

        queryData['answerKey'] = $('#answerKey').val();
        $('#answerKeyExp').val($('#answerKey').val());
        table.reload(Answer.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    Answer.openAddDlg = function () {
        func.open({
            title: '添加自动回复表',
            content: Feng.ctxPath + '/answer/add',
            tableId: Answer.tableId
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      Answer.openEditDlg = function (data) {
          func.open({
              title: '修改自动回复表',
              content: Feng.ctxPath + '/answer/edit?answerId=' + data.answerId,
              tableId: Answer.tableId
          });
      };


    /**
     * 导出excel按钮
     */
    Answer.exportExcel = function () {
        var checkRows = table.checkStatus(Answer.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 导出excel按钮
     */
    Answer.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/answer/listAll',
            type: 'post',
            data: {
                "answerKey":$('#answerKeyExp').val()
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
        title: '自动回复全部数据',
        cols: [[ //表头
            {
                field: 'answerKey',
                title: '问题',
            }, {
                field: 'answerValue',
                title: '答案',
            }, {
                field: 'createTime',
                title: '创建时间',
            }
        ]]
    });

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Answer.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/answer/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Answer.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("answerId", data.answerId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Answer.tableId,
        url: Feng.ctxPath + '/answer/listAll',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Answer.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Answer.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    Answer.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        Answer.exportExcel();
    });
    // 导出excel
    $('#btnExpAll').click(function () {
        Answer.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + Answer.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Answer.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Answer.onDeleteItem(data);
        }
    });

    // 修改环节启用状态
    form.on('switch(status)', function (obj) {

        var answerId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        Answer.changeUserStatus(answerId, checked);
    });

    /**
     * 修改启用状态
     *
     * @param linkId
     * @param checked 是否选中（true,false）
     */
    Answer.changeUserStatus = function (answerId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/answer/unfreeze", function (data) {
                Feng.success("解除冻结成功!");
            }, function (data) {
                Feng.error("解除冻结失败!");
                table.reload(Answer.tableId);
            });
            ajax.set("answerId", answerId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/answer/freeze", function (data) {
                Feng.success("冻结成功!");
            }, function (data) {
                Feng.error("冻结失败!" + data.responseJSON.message + "!");
                table.reload(Answer.tableId);
            });
            ajax.set("answerId", answerId);
            ajax.start();
        }
    };
});
