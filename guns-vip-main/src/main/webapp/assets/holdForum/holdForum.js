layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 承办方论坛表管理
     */
    var HoldForum = {
        tableId: "holdForumTable",
        condition: {
            forumName: "",
        }
    };

    /**
     * 初始化表格的列
     */
    HoldForum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'forumId', hide: true, title: '论坛ID'},
            {field: 'forumName', sort: true, title: '论坛名称'},
            //{field: 'forumDesc', sort: true, title: '论坛描述'},
            {field: 'unitName', sort: true, title: '单位名称'},
            //{field: 'unitDesc', sort: true, title: '单位简介'},
            //{field: 'level', sort: true, title: '级别'},
            {field: 'year', sort: true, title: '承办年份'},
            /*{field: 'ability', sort: true, title: '承办能力(会议规模)'},
            {field: 'topic', sort: true, title: '论坛主题'},*/
            {field: 'manager', sort: true, title: '负责人'},
            /*{field: 'manaPhone', sort: true, title: '负责人电话'},
            {field: 'manaEmail', sort: true, title: '负责人邮箱'},
            {field: 'orgSup', sort: true, title: '组织保障'},
            {field: 'fundsSup', sort: true, title: '经费保障'},
            {field: 'staffSup', sort: true, title: '人员保障'},
            {field: 'experience', sort: true, title: '办会经验'},
            {field: 'agreeRule', sort: true, title: '是否同意论坛章程; 0-不同意,1-同意'},
            {field: 'planPath', sort: true, title: '承办方案附件路径'},
            {field: 'commitPath', sort: true, title: '承诺书路径'},
            {field: 'applyTime', sort: true, title: '申报时间'},*/
            {field: 'applyStatus', sort: true, title: '申报状态', templet: function(data){
                    if (data.applyStatus == 1) return '申请中';
                    if (data.applyStatus == 2) return '已通过';
                    if (data.applyStatus == 3) return '未通过';
                    if (data.applyStatus == 0) return '已取消';
                }},//; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
            //{field: 'applyUser', sort: true, title: '申报人ID'},
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
    HoldForum.search = function () {
        var queryData = {};

        queryData['forumName'] = $("#forumName").val();
        table.reload(HoldForum.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    HoldForum.openAddDlg = function () {
        func.open({
            title: '添加承办方论坛表',
            content: Feng.ctxPath + '/holdForum/add',
            tableId: HoldForum.tableId
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      HoldForum.openEditDlg = function (data) {
          func.open({
              title: '详情信息',
              content: Feng.ctxPath + '/holdForum/edit?forumId=' + data.forumId,
              tableId: HoldForum.tableId
          });
      };

    /**
     * 点击审批
     *
     * @param data 点击按钮时候的行数据
     */
    HoldForum.openApprove = function (data) {
        func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/holdForum/approve?forumId=' + data.forumId,
            tableId: HoldForum.tableId
        });
    };
    /**
     * 点击详情
     *
     * @param data 点击按钮时候的行数据
     */
    HoldForum.openDetail = function (data) {
        func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/holdForum/detailAdmin?forumId=' + data.forumId,
            tableId: HoldForum.tableId
        });
    };


    /**
     * 导出excel按钮
     */
    HoldForum.exportExcel = function () {
        var checkRows = table.checkStatus(HoldForum.tableId);
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
    HoldForum.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/holdForum/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(HoldForum.tableId);
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
        elem: '#' + HoldForum.tableId,
        url: Feng.ctxPath + '/holdForum/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: HoldForum.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        HoldForum.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    HoldForum.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        HoldForum.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + HoldForum.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            HoldForum.openEditDlg(data);
        } else if (layEvent === 'delete') {
            HoldForum.onDeleteItem(data);
        } else if (layEvent === 'approve') {
            HoldForum.openApprove(data);
        } else if (layEvent === 'detail') {
            HoldForum.openDetail(data);
        }
    });
});
