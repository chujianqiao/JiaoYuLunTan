layui.use(['table', 'admin','form', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 社会资助论坛表管理
     */
    var SocialForum = {
        tableId: "socialForumTable",
        condition: {
            unitName: "",
        }
    };
    meetSelectOption();
    var langs = layui.data('system').lang;
    /**
     * 初始化表格的列
     */
    SocialForum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            //{field: 'forumId', hide: true, title: '论坛ID'},
            //{field: 'forumName', sort: true, title: '论坛名称'},
            //{field: 'forumDesc', sort: true, title: '论坛描述'},
            {field: 'meetName', sort: true, title: langs.FIELD_ConferenceName},
            {field: 'unitName', sort: true, title: langs.FIELD_NOAEO},
            {field: 'creditCode', sort: true, title: langs.FIELD_USCC},
            {field: 'manager', sort: true, title: langs.FIELD_ContactPerson},
            {field: 'manaPhone', sort: true, title: langs.FIELD_ContactNumber},
            {field: 'manaEmail', sort: true, title: langs.FIELD_ContactEmail},
            /*{field: 'unitPlace', sort: true, title: '企业/单位所在地'},
            {field: 'alreadyMeet', sort: true, title: '已资助的会议'},*/
            {field: 'supPlate', sort: true, title: langs.FIELD_SFC},
            //{field: 'supMoney', sort: true, title: '资助金额'},
            //{field: 'contractPath', sort: true, title: '合同条件附件路径'},
            /*{field: 'applyStatus', sort: true, title: '申报状态', templet: function(data){
                    if (data.applyStatus == 1) return '申请中';
                    if (data.applyStatus == 2) return '已通过';
                    if (data.applyStatus == 3) return '未通过';
                    if (data.applyStatus == 0) return '已取消';
                }},*///; 1-申请中, 2-已通过 , 3-未通过 , 0-取消申请
            /*{field: 'applyTime', sort: true, title: '申报时间'},
            {field: 'applyId', sort: true, title: '申报单位ID'},
            {field: 'contractName', sort: true, title: '合同条件附件名称'},*/
            {align: 'center', title: langs.FIELD_Operate,minWidth: 180, templet: function(data){
                    /*if (data.applyStatus == 0) {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit'>修改</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='editNew' id='editNew'>申请</a>";
                    }else if(data.applyStatus == 2 || data.applyStatus == 3){
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail'>查看详情</a><a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";
                    }else {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail'>查看详情</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancel' id='cancel'>取消申请</a>";
                    }*/
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='delete' title='" + langs.FIELD_Delete + "'>" + langs.FIELD_Delete + "</a>";
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    SocialForum.search = function () {
        var queryData = {};

        queryData['unitName'] = $("#unitName").val();
        queryData['meetId'] = $("#meetId").val();
        table.reload(SocialForum.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    form.on('select(meetId)', function(data){
        SocialForum.search();
    });
    /**
     * 弹出添加对话框
     */
    SocialForum.openAddDlg = function () {
        func.open({
            title: '添加承办方论坛表',
            content: Feng.ctxPath + '/socialForum/add',
            tableId: SocialForum.tableId
        });
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    SocialForum.openEditDlg = function (data) {
        func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/socialForum/edit?forumId=' + data.forumId,
            tableId: SocialForum.tableId
        });
    };

    /**
     * 点击详情
     *
     * @param data 点击按钮时候的行数据
     */
    SocialForum.openDetail = function (data) {
        func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/socialForum/detailAdmin?forumId=' + data.forumId,
            tableId: SocialForum.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    SocialForum.exportExcel = function () {
        var checkRows = table.checkStatus(SocialForum.tableId);
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
    SocialForum.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/socialForum/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(SocialForum.tableId);
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
    SocialForum.onCancel = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/socialForum/cancel", function (data) {
                Feng.success("取消成功!");
                table.reload(SocialForum.tableId);
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
    SocialForum.onEditNew = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/socialForum/editNew", function (data) {
                Feng.success("申请成功!");
                table.reload(SocialForum.tableId);
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
        elem: '#' + SocialForum.tableId,
        url: Feng.ctxPath + '/socialForum/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: SocialForum.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        SocialForum.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

        SocialForum.openAddDlg();

    });
    $('#btnCreate').click(function () {
        window.location.href = Feng.ctxPath + "/socialForum/add";
    });
    // 导出excel
    $('#btnExp').click(function () {
        SocialForum.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + SocialForum.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            SocialForum.openEditDlg(data);
        } else if (layEvent === 'delete') {
            SocialForum.onDeleteItem(data);
        } else if (layEvent === 'cancel') {
            SocialForum.onCancel(data);
        } else if (layEvent === 'editNew') {
            SocialForum.onEditNew(data);
        } else if (layEvent === 'detail') {
            SocialForum.openDetail(data);
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
