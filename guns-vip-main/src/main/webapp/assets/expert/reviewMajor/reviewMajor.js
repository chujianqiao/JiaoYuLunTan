layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 评审专家表管理
     */
    var ReviewMajor = {
        tableId: "reviewMajorTable",
        condition: {
            reviewName: ""
        }
    };

    /**
     * 初始化表格的列
     */
    ReviewMajor.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'reviewId', hide: true, title: '专家ID'},
            {field: 'reviewName', sort: true, title: '姓名'},
            {field: 'direct', sort: true, title: '研究方向和专长'},
            {field: 'thesisCount', sort: true, title: '论文分配数量'},
            {field: 'reviewCount', sort: true, title: '论文评审数量'},
            {field: 'refuseCount', sort: true, title: '退回数量'},
            {field: 'majorType', sort: true, title: '专家分类'},
            {field: 'applyFrom', sort: true, title: '来源'},
            {field: 'checkStatus', sort: true, title: '申报状态'},
            {field: 'applyTime', sort: true, title: '提交申请时间',minWidth: 180},
            {field: 'agreeTime', hide: true, title: '通过时间'},
            {field: 'refuseTime', hide: true, title: '驳回时间'},
            {field: 'cancelTime', hide: true, title: '取消时间'},
            // {align: 'center', toolbar: '#tableBar', title: '操作',minWidth: 180}

            {align: 'center', minWidth: 180, title: '操作', templet: function(data){
                    if (data.checkStatus == "已取消") {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit'>查看详情</ a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='editNew' id='editNew'>申请</ a>";
                    }else if (data.checkStatus == "申请中") {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit'>查看详情</ a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancel' id='cancel'>取消申请</ a>";
                    }else {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit'>查看详情</ a>"
                    }
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ReviewMajor.search = function () {
        var queryData = {};
        queryData['reviewName'] = $("#reviewName").val();
        table.reload(ReviewMajor.tableId, {
            where: queryData, page: {curr: 1}
        });

    };

    /**
     * 弹出添加对话框
     */
    ReviewMajor.openAddDlg = function () {
        func.open({
            title: '添加评审专家表',
            content: Feng.ctxPath + '/reviewMajor/add',
            tableId: ReviewMajor.tableId
        });
    };

     /**
      * 点击编辑
      * @param data 点击按钮时候的行数据
      */
      ReviewMajor.openEditDlg = function (data) {
          func.open({
              title: '查看详情',
              content: Feng.ctxPath + '/reviewMajor/edit?reviewId=' + data.reviewId,
              tableId: ReviewMajor.tableId
          });
      };

    /**
     * 点击编辑
     * @param data 点击按钮时候的行数据
     */
    ReviewMajor.openAdminEditDlg = function (data) {
        func.open({
            title: '审批',
            content: Feng.ctxPath + '/reviewMajor/approve?reviewId=' + data.reviewId,
            tableId: ReviewMajor.tableId
        });
    };


    /**
     * 导出excel按钮
     */
    ReviewMajor.exportExcel = function () {
        var checkRows = table.checkStatus(ReviewMajor.tableId);
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
    ReviewMajor.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/reviewMajor/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ReviewMajor.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("reviewId", data.reviewId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 点击申请
     * @param data 点击申请按钮时候的行数据
     */
    ReviewMajor.onApplyItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/reviewMajor/reApply", function (data) {
                Feng.success("申请成功!");
                table.reload(ReviewMajor.tableId);
            }, function (data) {
                Feng.error("申请失败!" + data.responseJSON.message + "!");
            });
            ajax.set("reviewId", data.reviewId);
            ajax.start();
        };
        Feng.confirm("确认申请?", operation);
    };

    /**
     * 点击取消申请
     *
     * @param data 点击按钮时候的行数据
     */
    ReviewMajor.onCancelItem = function (data) {
        if (data.checkStatus == '已取消'){
            alert("已经是取消状态！")
            return false;
        }
        debugger;
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/reviewMajor/cancelApply", function (data) {
                Feng.success("取消申请成功!");
                table.reload(ReviewMajor.tableId);
            }, function (data) {
                Feng.error("取消申请失败!" + data.responseJSON.message + "!");
            });
            ajax.set("reviewId", data.reviewId);
            ajax.start();
        };
        Feng.confirm("是否取消申请?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ReviewMajor.tableId,
        // url: Feng.ctxPath + '/reviewMajor/list',
        url: Feng.ctxPath + '/reviewMajor/wraplist',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ReviewMajor.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ReviewMajor.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    ReviewMajor.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        ReviewMajor.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ReviewMajor.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ReviewMajor.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ReviewMajor.onDeleteItem(data);
        }   else if (layEvent === 'cancel') {
            ReviewMajor.onCancelItem(data);
        }   else if (layEvent === 'approve') {
            ReviewMajor.openAdminEditDlg(data);
        }   else if (layEvent === 'editNew') {
            ReviewMajor.onApplyItem(data);
        }
    });
});
