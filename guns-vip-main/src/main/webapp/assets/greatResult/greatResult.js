layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 优秀成果表管理
     */
    var GreatResult = {
        tableId: "greatResultTable"
    };

    /**
     * 初始化表格的列
     */
    GreatResult.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'resultId', hide: true, title: '成果ID'},
            {field: 'resultName', sort: true, title: '成果名称'},
            {field: 'belongName', sort: true, title: '归属单位/个人'},
            /*{field: 'applyType', sort: true, title: '申请方式; 1-个人, 2-单位'},
            {field: 'manager', sort: true, title: '负责人姓名'},
            {field: 'manaPhone', sort: true, title: '负责人电话'},
            {field: 'manaEmail', sort: true, title: '负责人邮箱'},
            {field: 'manaPost', sort: true, title: '负责人职称/职务'},
            {field: 'manaDirect', sort: true, title: '负责人研究方向'},
            {field: 'resultMean', sort: true, title: '成果意义'},
            {field: 'value', sort: true, title: '应用价值'},
            {field: 'range', sort: true, title: '应用范围'},
            {field: 'object', sort: true, title: '服务对象'},
            {field: 'team', sort: true, title: '攻关团队'},
            {field: 'conclusion', sort: true, title: '研究结论与成果'},
            {field: 'introduce', sort: true, title: '过程简介'},
            {field: 'influence', sort: true, title: '成果影响力'},
            {field: 'slogan', sort: true, title: '宣传口号'},
            {field: 'designImg', sort: true, title: '易拉宝设计图路径'},*/
            {field: 'keyword', sort: true, title: '关键词'},
            /*{field: 'letterPath', sort: true, title: '专家推荐信附件路径'},
            {field: 'commitPath', sort: true, title: '原创承诺书路径'},
            {field: 'form', sort: true, title: '成果形式'},
            {field: 'detail', sort: true, title: '成果内容'},*/
            {field: 'checkStatus', sort: true, title: '申报状态', templet: function(data){
                    if (data.applyStatus == 1) return '申请中';
                    if (data.applyStatus == 2) return '已通过';
                    if (data.applyStatus == 3) return '未通过';
                    if (data.applyStatus == 0) return '已取消';
                }},//; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
            /*{field: 'applyId', sort: true, title: '申请人/单位ID'},
            {field: 'applyTime', sort: true, title: '申请提交时间'},
            {field: 'refuseTime', sort: true, title: '申请驳回时间'},
            {field: 'passTime', sort: true, title: '审核通过时间'},
            {field: 'cancelTime', sort: true, title: '取消申请时间'},*/
            {align: 'center', title: '操作', templet: function(data){
                    if (data.applyStatus == 1) {
                        return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"approve\">审批</a><a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";
                    }else {
                        return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">查看详情</a><a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";
                    }
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    GreatResult.search = function () {
        var queryData = {};


        table.reload(GreatResult.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    GreatResult.openAddDlg = function () {
        func.open({
            title: '添加优秀成果表',
            content: Feng.ctxPath + '/greatResult/add',
            tableId: GreatResult.tableId
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      GreatResult.openEditDlg = function (data) {
          func.open({
              title: '修改优秀成果表',
              content: Feng.ctxPath + '/greatResult/edit?resultId=' + data.resultId,
              tableId: GreatResult.tableId
          });
      };


    /**
     * 导出excel按钮
     */
    GreatResult.exportExcel = function () {
        var checkRows = table.checkStatus(GreatResult.tableId);
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
    GreatResult.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/greatResult/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(GreatResult.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("resultId", data.resultId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + GreatResult.tableId,
        url: Feng.ctxPath + '/greatResult/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: GreatResult.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        GreatResult.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    GreatResult.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        GreatResult.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + GreatResult.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            GreatResult.openEditDlg(data);
        } else if (layEvent === 'delete') {
            GreatResult.onDeleteItem(data);
        }
    });
});
