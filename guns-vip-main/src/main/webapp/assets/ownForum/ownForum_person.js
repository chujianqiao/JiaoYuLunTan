layui.use(['table', 'admin','form', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 自设论坛表管理
     */
    var OwnForum = {
        tableId: "ownForumTable",
        condition: {
            forumName: "",
        }
    };
    meetSelectOption();
    var langs = layui.data('system').lang;
    /**
     * 初始化表格的列
     */
    OwnForum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'forumId', hide: true, title: '论坛ID'},
            {field: 'meetName', sort: true, title: langs.FIELD_ConferenceName},
            {field: 'forumName', sort: true, title: langs.FIELD_TitleSubforum},
            {field: 'manager', sort: true, title: langs.FIELD_InformantS},
            //{field: 'forumTopic', sort: true, title: '论坛主题'},
            {field: 'forumSize', sort: true, title: langs.FIELD_ScaleSubforum},
            /*{field: 'manager', sort: true, title: '负责人'},
            {field: 'manaPhone', sort: true, title: '负责人电话'},
            {field: 'manaEmail', sort: true, title: '负责人邮箱'},*/
            {field: 'issubject', sort: true, title: langs.FIELD_DYAOBOARPT, templet: function(data){
                    if (data.issubject == 0) return langs.FIELD_No;
                    if (data.issubject == 1) return langs.FIELD_Yes;
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
            {field: 'applyStatus', sort: true, title: langs.FIELD_ApplicationOfStatus, templet: function(data){
                    if (data.applyStatus == 1) return '申请中';
                    if (data.applyStatus == 2) return '已通过';
                    if (data.applyStatus == 3) return '未通过';
                    if (data.applyStatus == 0) return '已取消';
                }},
            {align: 'center', title: langs.FIELD_Operate,minWidth: 180, templet: function(data){
                    if (data.applyStatus == 0) {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit' title='" + langs.FIELD_Revise + "'>" + langs.FIELD_Revise + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='editNew' id='editNew' title='" + langs.FIELD_Apply + "'>" + langs.FIELD_Apply + "</a>";
                    }else if(data.applyStatus == 2 || data.applyStatus == 3){
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a>";
                    }else {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancel' id='cancel' title='" + langs.FIELD_ApplicationCancelled + "'>" + langs.FIELD_ApplicationCancelled + "</a>";
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
        queryData['meetId'] = $("#meetId").val();
        table.reload(OwnForum.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    form.on('select(meetId)', function(data){
        OwnForum.search();
    });
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
        url: Feng.ctxPath + '/ownForum/wrapList',
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
    $('#btnCreate').click(function () {
        window.location.href = Feng.ctxPath + "/holdForum/add";
    });
    $('#btnCreate2').click(function () {
        window.location.href = Feng.ctxPath + "/holdForum/add";
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
