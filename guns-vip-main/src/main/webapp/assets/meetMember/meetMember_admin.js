layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 会议注册成员表管理
     */
    var MeetMember = {
        tableId: "meetMemberTable"
    };

    /**
     * 初始化表格的列
     */
    MeetMember.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'memberId', hide: true, title: '主键'},
            {field: 'memberName', sort: true, title: '参会人姓名'},
            {field: 'unitName', sort: true, title: '所在单位'},
            {field: 'userPost', sort: true, title: '职务/职称'},
            {field: 'direct', sort: true, title: '研究方向'},
            {field: 'thesisName', sort: true, title: '参会论文'},
            {field: 'forumName', sort: true, title: '参会论坛'},
            {field: 'meetStatusStr', sort: true, title: '会议状态'},
            {align: 'center', minWidth: 130, title: '操作', templet: function(data){
                return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail'>查看详情</a>";
                }}
        ]];
    };
    
    /**
     * 点击查询按钮
     */
    MeetMember.search = function () {
        var queryData = {};
        queryData['userName'] = $('#userName').val();
        queryData['ownForumid'] = $('#forumOption').val();
        table.reload(MeetMember.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    //选择论坛时立刻刷新
    form.on('select(forumOption)', function(data){
        MeetMember.search();
    });

    /**
     * 仅查看详情
     * @param data 点击按钮时候的行数据
     */
    MeetMember.onDisableItem = function (data) {
        window.location.href = Feng.ctxPath + '/meetMember/disable?memberId=' + data.memberId ;
    };

    /**
     * 选择论坛
     * @param data
     */
    MeetMember.onForumItem = function (data) {
        func.open({
            title: '选择论坛',
            content: Feng.ctxPath + '/meetMember/forum?memberId=' + data.memberId,
            tableId: MeetMember.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    MeetMember.exportExcel = function () {
        var checkRows = table.checkStatus(MeetMember.tableId);
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
    MeetMember.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMember/delete?thesisId="+data.thesisId, function (data) {
                Feng.success("删除成功!");
                table.reload(MeetMember.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("memberId", data.memberId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MeetMember.tableId,
        url: Feng.ctxPath + '/meetMember/adminList?roleId=2',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: MeetMember.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MeetMember.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MeetMember.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        MeetMember.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MeetMember.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MeetMember.openEditDlg(data);
        } else if (layEvent === 'delete') {
            MeetMember.onDeleteItem(data);
        } else if (layEvent === 'detail') {
            MeetMember.onDisableItem(data);
        }

    });


    /**----------------------------------------------------------------------------------------------------------------------
     * 嘉宾
     */
    var MeetMemberJB = {
        tableId: "meetMemberTableJB"
    };

    /**
     * 初始化表格的列
     */
    MeetMemberJB.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'memberId', hide: true, title: '主键'},
            {field: 'memberName', sort: true, title: '嘉宾姓名'},
            {field: 'unitName', sort: true, title: '所在单位'},
            {field: 'userPost', sort: true, title: '职务/职称'},
            {field: 'direct', sort: true, title: '研究方向'},
            // {field: 'thesisName', sort: true, title: '参会论文'},
            {field: 'forumName', sort: true, title: '参会论坛'},
            {align: 'center', minWidth: 130, title: '操作', templet: function(data){
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail'>查看详情</a>";
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    MeetMemberJB.search = function () {
        var queryData = {};
        queryData['userName'] = $('#userNameJB').val();
        queryData['ownForumid'] = $('#forumOptionJB').val();
        table.reload(MeetMemberJB.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    //选择论坛时立刻刷新
    form.on('select(forumOptionJB)', function(data){
        MeetMemberJB.search();
    });

    /**
     * 仅查看详情
     * @param data 点击按钮时候的行数据
     */
    MeetMemberJB.onDisableItem = function (data) {
        window.location.href = Feng.ctxPath + '/meetMember/disable?memberId=' + data.memberId ;
    };

    /**
     * 选择论坛
     * @param data
     */
    MeetMemberJB.onForumItem = function (data) {
        func.open({
            title: '选择论坛',
            content: Feng.ctxPath + '/meetMember/forum?memberId=' + data.memberId,
            tableId: MeetMember.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    MeetMemberJB.exportExcel = function () {
        var checkRows = table.checkStatus(MeetMember.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResultJB.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除
     * @param data 点击按钮时候的行数据
     */
    MeetMemberJB.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMember/delete?thesisId="+data.thesisId, function (data) {
                Feng.success("删除成功!");
                table.reload(MeetMember.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("memberId", data.memberId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };


    // 渲染表格
    var tableResultJB = table.render({
        elem: '#' + MeetMemberJB.tableId,
        url: Feng.ctxPath + '/meetMember/adminList?roleId=5',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: MeetMemberJB.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearchJB').click(function () {
        MeetMemberJB.search();
    });

    // 添加按钮点击事件
    // $('#btnAdd').click(function () {
    //     MeetMemberJB.openAddDlg();
    // });

    // 导出excel
    $('#btnExpJB').click(function () {
        MeetMemberJB.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MeetMemberJB.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MeetMemberJB.openEditDlg(data);
        } else if (layEvent === 'delete') {
            MeetMemberJB.onDeleteItem(data);
        } else if (layEvent === 'detail') {
            MeetMemberJB.onDisableItem(data);
        }

    });

    $(function () {
        forumSelectOption();
    })

    /**
     * 构建论坛下拉框
     */
    function forumSelectOption(){
        $.ajax({
            type:'post',
            // url:Feng.ctxPath + "/thesisDomain/list" ,
            url:Feng.ctxPath + "/forum/wrapList" ,
            success:function(response){
                var data=response.data;
                var forums = [];
                forums = data;
                var options;
                for (i = 0 ;i < forums.length ;i++){
                    var forum = data[i];
                    if(forum.status == "未发布"){
                        continue;
                    }
                    options += '<option value="'+ forum.forumId+ '" >'+ forum.forumName +'</option>';
                }
                $('#forumOption').empty();
                $('#forumOption').append("<option value=''>请选择论坛</option>");
                $('#forumOption').append(options);
                $('#forumOptionJB').empty();
                $('#forumOptionJB').append("<option value=''>请选择论坛</option>");
                $('#forumOptionJB').append(options);
                form.render('select');
            }
        })
    }



    /**----------------------------------------------------------------------------------------------------------------------
     * 专家
     */
    var MeetMemberMajor = {
        tableId: "meetMemberTableMajor"
    };

    /**
     * 初始化表格的列
     */
    MeetMemberMajor.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'reviewId', hide: true, title: '专家ID'},
            {field: 'reviewName', sort: true, title: '专家姓名'},
            {field: 'unitName', sort: true, title: '所在单位'},
            {field: 'userPost', sort: true, title: '职务/职称'},
            {field: 'belongDomainStr', sort: true, title: '所属领域'},
            {field: 'direct', sort: true, title: '研究方向'},
            {align: 'center', minWidth: 130, title: '操作', templet: function(data){
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='majorDetail'>查看详情</a>";
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    MeetMemberMajor.search = function () {
        var queryData = {};
        queryData['reviewName'] = $('#userNameMajor').val();
        table.reload(MeetMemberMajor.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 仅查看详情
     * @param data 点击按钮时候的行数据
     */
    MeetMemberMajor.onDisableItem = function (data) {
        window.location.href = Feng.ctxPath + Feng.ctxPath + '/reviewMajor/disable?reviewId=' + data.reviewId;
    };

    /**
     * 导出excel按钮
     */
    MeetMemberMajor.exportExcel = function () {
        var checkRows = table.checkStatus(MeetMemberMajor.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResultMajor.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除
     * @param data 点击按钮时候的行数据
     */
    MeetMemberMajor.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMember/delete?thesisId="+data.thesisId, function (data) {
                Feng.success("删除成功!");
                table.reload(MeetMember.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("memberId", data.memberId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };


    // 渲染表格
    var tableResultMajor = table.render({
        elem: '#' + MeetMemberMajor.tableId,
        url: Feng.ctxPath + '/reviewMajor/wraplist',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: MeetMemberMajor.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearchMajor').click(function () {
        MeetMemberMajor.search();
    });

    // 导出excel
    $('#btnExpMajor').click(function () {
        MeetMemberMajor.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MeetMemberMajor.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MeetMemberMajor.openEditDlg(data);
        } else if (layEvent === 'delete') {
            MeetMemberMajor.onDeleteItem(data);
        } else if (layEvent === 'majorDetail') {
            MeetMemberMajor.onDisableItem(data);
        }

    });

});
