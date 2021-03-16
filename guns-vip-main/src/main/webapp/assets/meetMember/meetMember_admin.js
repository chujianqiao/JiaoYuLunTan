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
            //{field: 'direct', sort: true, title: '研究方向'},
            {field: 'thesisName', sort: true, title: '参会论文'},
            {field: 'forumName', sort: true, title: '参会论坛'},
            {field: 'meetStatusStr', sort: true, title: '会议状态'},
            {field: 'ifMeet', sort: true, title: '是否参加大会'},
            {align: 'center', minWidth: 130, title: '操作', templet: function(data){
                return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail'>查看详情</a>"
                    +  "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='delete'>删除</a>";
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
        queryData['meetId'] = $('#meetId').val();
        $('#userNameExp').val($('#userName').val());
        table.reload(MeetMember.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    //选择论坛时立刻刷新
    form.on('select(forumOption)', function(data){
        MeetMember.search();
    });
    //选择论坛时立刻刷新
    form.on('select(meetId)', function(data){
        MeetMember.search();
        forumSelectOption();
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
            table.exportFile('exportTable', checkRows.data, 'xls');
        }
    };
    /**
     * 导出excel按钮
     */
    MeetMember.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/meetMember/adminList?roleId=2',
            type: 'post',
            data: {
                "userName":$('#userNameExp').val(),
                "meetId":$('#meetId').val(),
                "ownForumid":$('#forumOption').val()
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
        title: '普通人员参会列表全部数据',
        cols: [[ //表头
            {
                field: 'memberName',
                title: '参会人姓名',
            }, {
                field: 'unitName',
                title: '所在单位',
            }, {
                field: 'userPost',
                title: '职务/职称',
            }, {
                field: 'direct',
                title: '研究方向',
            }, {
                field: 'thesisName',
                title: '参会论文',
            }, {
                field: 'forumName',
                title: '参会论坛',
            }, {
                field: 'meetStatusStr',
                title: '会议状态',
            }, {
                field: 'regTime',
                title: '注册时间',
            }
        ]]
    });

    /**
     * 点击删除
     * @param data 点击按钮时候的行数据
     */
    MeetMember.onDeleteItem = function (data) {
        var thesisId = "";
        if (data.thesisId != undefined) {
            thesisId = data.thesisId;
        }
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMember/delete?thesisId="+thesisId, function (data) {
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
    // 导出excel
    $('#btnExpAll').click(function () {
        MeetMember.exportExcelAll();
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
            {field: 'material', sort: true, title: '是否上传材料'},
            {align: 'center', minWidth: 130, title: '操作', templet: function(data){
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detailJB'>查看详情</a>"
                        + "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='deleteJB'>删除</a>";
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
        queryData['meetId'] = $('#meetIdJB').val();
        $('#userNameJBExp').val($('#userNameJB').val());
        table.reload(MeetMemberJB.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    //选择论坛时立刻刷新
    form.on('select(forumOptionJB)', function(data){
        MeetMemberJB.search();
    });
    //选择论坛时立刻刷新
    form.on('select(meetIdJB)', function(data){
        MeetMemberJB.search();
        forumJBSelectOption();

    });

    /**
     * 仅查看详情
     * @param data 点击按钮时候的行数据
     */
    MeetMemberJB.onDisableItem = function (data) {
        window.location.href = Feng.ctxPath + '/meetMember/guestDetail?memberId=' + data.memberId ;
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
        var checkRows = table.checkStatus(MeetMemberJB.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile('exportTableJB', checkRows.data, 'xls');
        }
    };
    /**
     * 导出excel按钮
     */
    MeetMemberJB.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/meetMember/adminList?roleId=5',
            type: 'post',
            data: {
                "userName":$('#userNameJBExp').val(),
                "meetId":$('#meetIdJB').val(),
                "ownForumid":$('#forumOptionJB').val()
            },
            async: false,
            dataType: 'json',
            success: function (res) {
                //使用table.exportFile()导出数据
                //console.log(res.data);
                table.exportFile('exportTableJB', res.data, 'xlsx');
            }
        });
    };
    table.render({
        elem: '#tableExpJBAll',
        id: 'exportTableJB',
        title: '会议嘉宾参会列表全部数据',
        cols: [[ //表头
            {
                field: 'memberName',
                title: '嘉宾姓名',
            }, {
                field: 'unitName',
                title: '所在单位',
            }, {
                field: 'userPost',
                title: '职务/职称',
            }, {
                field: 'direct',
                title: '研究方向',
            }, {
                field: 'forumName',
                title: '参会论坛',
            }, {
                field: 'regTime',
                title: '注册时间',
            }, {
                field: 'introduction',
                title: '嘉宾简介',
            }
        ]]
    });

    /**
     * 点击删除
     * @param data 点击按钮时候的行数据
     */
    MeetMemberJB.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMember/delete?thesisId="+data.thesisId, function (data) {
                Feng.success("删除成功!");
                table.reload(MeetMemberJB.tableId);
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

    // 导出excel
    $('#btnExpJB').click(function () {
        MeetMemberJB.exportExcel();
    });
    // 导出excel
    $('#btnExpJBAll').click(function () {
        MeetMemberJB.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + MeetMemberJB.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MeetMemberJB.openEditDlg(data);
        } else if (layEvent === 'deleteJB') {
            MeetMemberJB.onDeleteItem(data);
        } else if (layEvent === 'detailJB') {
            MeetMemberJB.onDisableItem(data);
        }

    });

    $(function () {
        forumSelectOption();
        forumJBSelectOption();
        meetSelectOption();
    })

    /**
     * 构建论坛下拉框
     */
    function forumSelectOption(){
        var meetId = "";
        if ($("#meetId").val() != null){
            meetId = $("#meetId").val();
        }
        $.ajax({
            type:'post',
            // url:Feng.ctxPath + "/thesisDomain/list" ,
            url:Feng.ctxPath + "/forum/wrapList?meetId=" + meetId,
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
                form.render('select');
            }
        })
    }
    /**
     * 构建论坛下拉框
     */
    function forumJBSelectOption(){
        var meetId = "";
        if ($("#meetId").val() != null){
            meetId = $("#meetId").val();
        }
        $.ajax({
            type:'post',
            // url:Feng.ctxPath + "/thesisDomain/list" ,
            url:Feng.ctxPath + "/forum/wrapList?meetId=" + meetId,
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
                $('#forumOptionJB').empty();
                $('#forumOptionJB').append("<option value=''>请选择论坛</option>");
                $('#forumOptionJB').append(options);
                form.render('select');
            }
        })
    }
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
                $('#meetIdJB').empty();
                $('#meetIdJB').append("<option value='0'>请选择会议</option>");
                $('#meetIdJB').append(options);
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
            {field: 'reviewCount', sort: true, title: '评审数量'},
            {align: 'center', minWidth: 130, title: '操作', templet: function(data){
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='majorDetail'>查看详情</a>"
                        + "<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='deleteMajor'>删除</a>";
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    MeetMemberMajor.search = function () {
        var queryData = {};
        queryData['reviewName'] = $('#userNameMajor').val();
        $('#userNameMajorExp').val($('#userNameMajor').val());
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
            table.exportFile('exportTableMajor', checkRows.data, 'xls');
        }
    };
    /**
     * 导出excel按钮
     */
    MeetMemberMajor.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/reviewMajor/wraplist',
            type: 'post',
            data: {
                "reviewName":$('#userNameMajorExp').val()
            },
            async: false,
            dataType: 'json',
            success: function (res) {
                //使用table.exportFile()导出数据
                //console.log(res.data);
                table.exportFile('exportTableMajor', res.data, 'xlsx');
            }
        });
    };
    table.render({
        elem: '#tableExpMajorAll',
        id: 'exportTableMajor',
        title: '评审专家参会列表全部数据',
        cols: [[ //表头
            {
                field: 'reviewName',
                title: '专家姓名',
            }, {
                field: 'unitName',
                title: '所在单位',
            }, {
                field: 'userPost',
                title: '职务/职称',
            }, {
                field: 'belongDomainStr',
                title: '所属领域',
            }, {
                field: 'direct',
                title: '研究方向',
            }, {
                field: 'idCardType',
                title: '证件类型',
            }, {
                field: 'idCard',
                title: '证件号',
            }, {
                field: 'accountProvince',
                title: '开户省份',
            }, {
                field: 'accountCity',
                title: '开户城市',
            }, {
                field: 'bank',
                title: '银行机构',
            }, {
                field: 'unionNumber',
                title: '联行号',
            }, {
                field: 'accountBank',
                title: '开户行',
            }, {
                field: 'personalAccount',
                title: '个人账号',
            }, {
                field: 'applyTime',
                title: '注册时间',
            }
        ]]
    });

    /**
     * 点击删除
     * @param data 点击按钮时候的行数据
     */
    MeetMemberMajor.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/reviewMajor/adminDelete", function (data) {
                Feng.success("删除成功!");
                table.reload(MeetMemberMajor.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            // ajax.set("memberId", data.memberId);
            ajax.set("reviewId", data.reviewId);
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
    // 导出excel
    $('#btnExpMajorAll').click(function () {
        MeetMemberMajor.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + MeetMemberMajor.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MeetMemberMajor.openEditDlg(data);
        } else if (layEvent === 'deleteMajor') {
            MeetMemberMajor.onDeleteItem(data);
        } else if (layEvent === 'majorDetail') {
            MeetMemberMajor.onDisableItem(data);
        }

    });

});
