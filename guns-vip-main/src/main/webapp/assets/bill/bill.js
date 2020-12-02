layui.use(['table', 'form', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 管理
     */
    var Bill = {
        tableId: "billTable"
    };

    /**
     * 初始化表格的列
     */
    Bill.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'billId', hide: true, title: '发票ID'},
            {field: 'userName', sort: true, title: '申请人'},
            {field: 'meetName', sort: true, title: '会议'},
            {field: 'enterprise', sort: true, title: '企业名称'},
            {field: 'taxpayerNumber', sort: true, title: '纳税人识别号'},
            {field: 'food', sort: true, title: '饮食禁忌'},
            {field: 'hotel', sort: true, title: '酒店预订'},
            {align: 'center', minWidth: 130, title: '操作', templet: function(data){
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail'>查看详情</a>"
                        +  "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='delete'>删除</a>";
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Bill.search = function () {
        var queryData = {};

        queryData['userName'] = $('#userName').val();
        $('#userNameExp').val($('#userName').val());
        table.reload(Bill.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 仅查看详情
     * @param data 点击按钮时候的行数据
     */
    Bill.onDisableItem = function (data) {
        window.location.href = Feng.ctxPath + '/bill/toDetail?meetMemberId=' + data.meetMemberId + '&userId=' + data.userId;
    };

    /**
     * 弹出添加对话框
     */
    Bill.openAddDlg = function () {
        func.open({
            title: '添加',
            content: Feng.ctxPath + '/bill/add',
            tableId: Bill.tableId
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      Bill.openEditDlg = function (data) {
          func.open({
              title: '修改',
              content: Feng.ctxPath + '/bill/edit?billId=' + data.billId,
              tableId: Bill.tableId
          });
      };


    /**
     * 导出excel按钮
     */
    Bill.exportExcel = function () {
        var checkRows = table.checkStatus(Bill.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 导出excel按钮
     */
    Bill.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/bill/listAll',
            type: 'post',
            data: {
                "userName":$('#userNameExp').val(),
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
        title: '发票管理全部数据',
        cols: [[ //表头
            {
                field: 'userName',
                title: '申请人姓名',
            }, {
                field: 'meetName',
                title: '会议名称',
            }, {
                field: 'enterprise',
                title: '企业名称',
            }, {
                field: 'taxpayerNumber',
                title: '纳税人识别号',
            }, {
                field: 'food',
                title: '饮食禁忌',
            }, {
                field: 'hotel',
                title: '酒店预订',
            }
        ]]
    });

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Bill.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/bill/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Bill.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("billId", data.billId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Bill.tableId,
        url: Feng.ctxPath + '/bill/listAll',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Bill.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Bill.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    Bill.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        Bill.exportExcel();
    });
    // 导出excel
    $('#btnExpAll').click(function () {
        Bill.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + Bill.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Bill.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Bill.onDeleteItem(data);
        } else if (layEvent === 'detail') {
            Bill.onDisableItem(data);
        }
    });
});
