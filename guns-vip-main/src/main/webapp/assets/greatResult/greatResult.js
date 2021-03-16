layui.use(['table', 'admin','form', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 优秀成果表管理
     */
    var GreatResult = {
        tableId: "greatResultTable",
        condition: {
            resultName: "",
        }
    };
    meetSelectOption();
    /**
     * 初始化表格的列
     */
    GreatResult.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'resultId', hide: true, title: '成果ID'},
            {field: 'meetName', sort: true, title: '会议名称'},
            {field: 'resultName', sort: true, title: '成果名称'},
            {field: 'belongName', sort: true, title: '申请人姓名'},
            {field: 'reviewName', sort: true, title: '评审专家'},
            {field: 'reviewResult', sort: true, title: '评审状态'},
            {field: 'score', sort: true, title: '评审分数'},
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
            //{field: 'keyword', sort: true, title: '关键词'},
            /*{field: 'letterPath', sort: true, title: '专家推荐信附件路径'},
            {field: 'commitPath', sort: true, title: '原创承诺书路径'},
            {field: 'form', sort: true, title: '成果形式'},
            {field: 'detail', sort: true, title: '成果内容'},*/
            /*{field: 'checkStatus', sort: true, title: '申报状态', templet: function(data){
                    if (data.checkStatus == 1) return '申请中';
                    if (data.checkStatus == 2) return '已通过';
                    if (data.checkStatus == 3) return '未通过';
                    if (data.checkStatus == 0) return '已取消';
                }},*///; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
            /*{field: 'applyId', sort: true, title: '申请人/单位ID'},
            {field: 'applyTime', sort: true, title: '申请提交时间'},
            {field: 'refuseTime', sort: true, title: '申请驳回时间'},
            {field: 'passTime', sort: true, title: '审核通过时间'},
            {field: 'cancelTime', sort: true, title: '取消申请时间'},*/
            {align: 'center', title: '操作',minWidth:220, templet: function(data){
                    if (data.reviewResult == "未评审" || data.reviewResult == "未分配") {
                        return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">查看详情</a>\n" +
                            "    <a class=\"layui-btn layui-btn-normal layui-btn-xs\" lay-event=\"assign\">分配专家</a>\n" +
                            "    <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";
                    }else {
                        if (data.finalResult == 1) {
                            return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">评审</a>\n" +
                                "    <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";
                        }else {
                            return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">查看详情</a>\n" +
                                "    <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";
                        }

                    }
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    GreatResult.search = function () {
        var queryData = {};

        queryData['resultName'] = $("#resultName").val();
        $("#resultNameExp").val($("#resultName").val());
        queryData['meetId'] = $("#meetId").val();
        queryData['checkStatus'] = $("#checkStatus").val();
        table.reload(GreatResult.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    form.on('select(meetId)', function(data){
        GreatResult.search();
    });
    form.on('select(checkStatus)', function(data){
        GreatResult.search();
    });
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
              content: Feng.ctxPath + '/greatResult/edit?resultId=' + data.resultId + '&applyType=' + data.applyType,
              tableId: GreatResult.tableId
          });
      };

    /**
     * 点击审批
     *
     * @param data 点击按钮时候的行数据
     */
    GreatResult.openApprove = function (data) {
        func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/greatResult/approve?resultId=' + data.resultId + '&applyType=' + data.applyType,
            tableId: GreatResult.tableId
        });
    };
    /**
     * 点击详情
     *
     * @param data 点击按钮时候的行数据
     */
    GreatResult.openDetail = function (data) {
        window.location.href = Feng.ctxPath + '/greatResult/detailAdmin?resultId=' + data.resultId + '&applyType=' + data.applyType;
        /*func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/greatResult/detailAdmin?resultId=' + data.resultId + '&applyType=' + data.applyType,
            tableId: GreatResult.tableId
        });*/
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
     * 导出excel按钮
     */
    GreatResult.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/greatResult/wrapList',
            type: 'post',
            data: {
                "resultName":$('#resultNameExp').val(),
                "meetId":$('#meetId').val(),
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
        title: '著作申报全部数据',
        cols: [[ //表头
            {
                field: 'meetName',
                title: '会议名称',
            },{
                field: 'resultName',
                title: '成果名称',
            }, {
                field: 'belongName',
                title: '申请人姓名',
            }, {
                field: 'reviewName',
                title: '评审专家',
            }, {
                field: 'reviewResult',
                title: '评审状态',
            }, {
                field: 'score',
                title: '评审分数',
            }, {
                field: 'description',
                title: '评审备注',
            }
        ]]
    });

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

    GreatResult.jumpAssignPage = function (data) {
        func.open({
            title: '分配评审人',
            area: ['350px', '300px'],
            content: Feng.ctxPath + '/greatResult/assign?resultId=' + data.resultId,
            tableId: GreatResult.tableId
        });
    };

    GreatResult.jumpAssignPageBatch = function (data) {
        var checkRows = table.checkStatus(GreatResult.tableId);
        var belongDomain = $("#belongDomain").val();
        if (checkRows.data.length === 0) {
            Feng.error("请选择被分配的数据");
        } else if (belongDomain == ""){
            Feng.error("请先根据领域筛选，再进行批量分配。");
        } else {
            var resultIds = "";
            for (var i = 0;i < checkRows.data.length;i++){
                resultIds = resultIds + checkRows.data[i].resultId + ";";
            }
            func.open({
                title: '分配评审人',
                area: ['350px', '300px'],
                content: Feng.ctxPath + '/greatResult/assign?resultId=' + resultIds,
                tableId: GreatResult.tableId
            });
        }
    };
    GreatResult.jumpReviewPageBatch = function (data) {
        var checkRows = table.checkStatus(GreatResult.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择被评审的数据");
        } else {
            var resultIds = "";
            for (var i = 0;i < checkRows.data.length;i++){
                resultIds = resultIds + checkRows.data[i].resultId + ";";
            }
            func.open({
                title: '批量评审',
                area: ['350px', '300px'],
                content: Feng.ctxPath + '/greatResult/reviewBatch?resultId=' + resultIds,
                tableId: GreatResult.tableId
            });
        }
    };
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + GreatResult.tableId,
        url: Feng.ctxPath + '/greatResult/wrapList',
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
    // 导出excel
    $('#btnExpAll').click(function () {
        GreatResult.exportExcelAll();
    });

    //批量分配
    $('#assignBatch').click(function () {
        GreatResult.jumpAssignPageBatch();
    });
    $('#reviewBatch').click(function () {
        GreatResult.jumpReviewPageBatch();
    });

    // 工具条点击事件
    table.on('tool(' + GreatResult.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            GreatResult.openEditDlg(data);
        } else if (layEvent === 'delete') {
            GreatResult.onDeleteItem(data);
        } else if (layEvent === 'approve') {
            GreatResult.openApprove(data);
        } else if (layEvent === 'detail') {
            GreatResult.openDetail(data);
        } else if (layEvent === 'assign') {
            GreatResult.jumpAssignPage(data);
        }
    });

    function meetSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/meet/wrapList" ,
            success:function(response){
                var data=response.data;
                var meet = [];
                meet = data;
                console.log(meet)

                var options;
                for (var i = 0 ;i < meet.length ;i++){
                    if (meet[i].meetStatus == 1){
                        options += '<option value="'+ meet[i].meetId+ '" selected>'+ meet[i].meetName +'</option>';
                    } else {
                        options += '<option value="'+ meet[i].meetId+ '" >'+ meet[i].meetName +'</option>';
                    }

                }
                $('#meetId').empty();
                $('#meetId').append("<option value='0'>请选择会议</option>");
                $('#meetId').append(options);
                form.render('select');
            }
        })
    }
});
