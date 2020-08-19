layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 自设论坛表管理
     */
    var OwnForum = {
        tableId: "ownForumTable",
        condition: {
            forumName: "",
        }
    };

    /**
     * 初始化表格的列
     */
    OwnForum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'forumId', hide: true, title: '论坛ID'},
            {field: 'forumName', sort: true, title: '论坛名称'},
            {field: 'applyType', sort: true, title: '申报人/单位', templet: function(data){
                    if (data.applyType == 1) return data.manager;
                    if (data.applyType == 2) return data.unitName;
                }},
            {field: 'forumTopic', sort: true, title: '论坛主题'},
            {field: 'forumSize', sort: true, title: '论坛规模'},
            /*{field: 'manager', sort: true, title: '负责人'},
            {field: 'manaPhone', sort: true, title: '负责人电话'},
            {field: 'manaEmail', sort: true, title: '负责人邮箱'},*/
            {field: 'issubject', sort: true, title: '是否有课题团队', templet: function(data){
                    if (data.issubject == 0) return '否';
                    if (data.issubject == 1) return '是';
                }},
            /*{field: 'subjectLev', sort: true, title: '课题级别'},
            {field: 'subjectName', sort: true, title: '课题名称'},
            {field: 'planPath', sort: true, title: '论坛申报方案附件路径'},
            {field: 'relation', sort: true, title: '与大会主题的关系'},
            {field: 'meaning', sort: true, title: '选题意义'},
            {field: 'expertName', sort: true, title: '拟邀请专家姓名'},
            {field: 'staffType', sort: true, title: '参会人员类型'},
            {field: 'orgType', sort: true, title: '组织形式'},
            {field: 'dividePath', sort: true, title: '人员分工表附件路径'},
            {field: 'agreeRule', sort: true, title: '是否同意论坛章程;0-不同意,1-同意'},
            {field: 'applyId', sort: true, title: '申报人ID'},
            {field: 'applyTime', sort: true, title: '申报时间'},
            {field: 'divideName', sort: true, title: '人员分工表附件名称'},
            {field: 'planName', sort: true, title: '申报方案附件名称'},
            {field: 'unitName', sort: true, title: '单位名称'},
            {field: 'direction', sort: true, title: '研究方向'},*/
            {field: 'applyStatus', sort: true, title: '申报状态', templet: function(data){
                    if (data.applyStatus == 1) return '申请中';
                    if (data.applyStatus == 2) return '已通过';
                    if (data.applyStatus == 3) return '未通过';
                    if (data.applyStatus == 0) return '已取消';
                }},
            {align: 'center', title: '操作',minWidth: 180, templet: function(data){
                    if (data.applyStatus == 0) {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit'>修改</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='editNew' id='editNew'>申请</a>";
                    }else if(data.applyStatus == 2 || data.applyStatus == 3){
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail'>查看详情</a>";
                    }else {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail'>查看详情</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancel' id='cancel'>取消申请</a>";
                    }
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    OwnForum.search = function () {
        var queryData = {};

        queryData['forumName'] = $("#forumName").val();
        table.reload(OwnForum.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    OwnForum.openAddDlg = function () {
        func.open({
            title: '添加自设论坛表',
            content: Feng.ctxPath + '/ownForum/add',
            tableId: OwnForum.tableId
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      OwnForum.openEditDlg = function (data) {
          func.open({
              title: '修改自设论坛表',
              content: Feng.ctxPath + '/ownForum/edit?forumId=' + data.forumId + '&applyType=' + data.applyType,
              tableId: OwnForum.tableId
          });
      };

    /**
     * 点击详情
     *
     * @param data 点击按钮时候的行数据
     */
    OwnForum.openDetail = function (data) {
        window.location.href = Feng.ctxPath + '/ownForum/detailAdmin?forumId=' + data.forumId + '&applyType=' + data.applyType;
        /*func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/ownForum/detailAdmin?forumId=' + data.forumId + '&applyType=' + data.applyType,
            tableId: OwnForum.tableId
        });*/
    };

    /**
     * 导出excel按钮
     */
    OwnForum.exportExcel = function () {
        var checkRows = table.checkStatus(OwnForum.tableId);
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
    OwnForum.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/ownForum/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(OwnForum.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("forumId", data.forumId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };


    /**
     * 点击取消
     *
     * @param data 点击按钮时候的行数据
     */
    OwnForum.onCancel = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/ownForum/cancel", function (data) {
                Feng.success("取消成功!");
                table.reload(OwnForum.tableId);
            }, function (data) {
                Feng.error("取消失败!" + data.responseJSON.message + "!");
            });
            ajax.set("forumId", data.forumId);
            ajax.start();
        };
        Feng.confirm("是否取消?", operation);
    };

    /**
     * 点击申请
     *
     * @param data 点击按钮时候的行数据
     */
    OwnForum.onEditNew = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/ownForum/editNew", function (data) {
                Feng.success("申请成功!");
                table.reload(OwnForum.tableId);
            }, function (data) {
                Feng.error("申请失败!" + data.responseJSON.message + "!");
            });
            ajax.set("forumId", data.forumId);
            ajax.start();
        };
        Feng.confirm("是否申请?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + OwnForum.tableId,
        url: Feng.ctxPath + '/ownForum/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: OwnForum.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        OwnForum.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    OwnForum.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        OwnForum.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + OwnForum.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            OwnForum.openEditDlg(data);
        } else if (layEvent === 'delete') {
            OwnForum.onDeleteItem(data);
        } else if (layEvent === 'cancel') {
            OwnForum.onCancel(data);
        } else if (layEvent === 'editNew') {
            OwnForum.onEditNew(data);
        } else if (layEvent === 'detail') {
            OwnForum.openDetail(data);
        }
    });
});
