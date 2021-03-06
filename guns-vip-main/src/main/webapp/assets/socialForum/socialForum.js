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
    /**
     * 初始化表格的列
     */
    SocialForum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            //{field: 'forumId', hide: true, title: '论坛ID'},
            //{field: 'forumName', sort: true, title: '论坛名称'},
            //{field: 'forumDesc', sort: true, title: '论坛描述'},
            {field: 'meetName', sort: true, title: '会议名称'},
            {field: 'unitName', sort: true, title: '企业/单位名称'},
            {field: 'creditCode', sort: true, title: '统一社会信用代码'},
            {field: 'manager', sort: true, title: '联系人'},
            {field: 'manaPhone', sort: true, title: '联系电话'},
            {field: 'manaEmail', sort: true, title: '联系邮箱'},
            //{field: 'unitPlace', sort: true, title: '企业/单位所在地'},
            /*
            {field: 'alreadyMeet', sort: true, title: '已资助的会议'},*/
            {field: 'supPlate', sort: true, title: '合作方式'},
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
            {align: 'center', title: '操作',minWidth: 180, templet: function(data){

                        return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">查看详情</a><a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";

                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    SocialForum.search = function () {
        var queryData = {};

        queryData['unitName'] = $("#unitName").val();
        $("#unitNameExp").val($("#unitName").val());
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
     * 点击审批
     *
     * @param data 点击按钮时候的行数据
     */
    SocialForum.openApprove = function (data) {
        func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/socialForum/approve?forumId=' + data.forumId,
            tableId: SocialForum.tableId
        });
    };
    /**
     * 点击详情
     *
     * @param data 点击按钮时候的行数据
     */
    SocialForum.openDetail = function (data) {
        window.location.href = Feng.ctxPath + '/socialForum/detailAdmin?forumId=' + data.forumId;
        /*func.open({
            title: '详情信息',
            content: Feng.ctxPath + '/socialForum/detailAdmin?forumId=' + data.forumId,
            tableId: SocialForum.tableId
        });*/
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
     * 导出excel按钮
     */
    SocialForum.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/socialForum/list',
            type: 'post',
            data: {
                "unitName":$('#unitNameExp').val(),
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
        title: '合作列表全部数据',
        cols: [[ //表头
            {
                field: 'meetName',
                title: '会议名称',
            },{
                field: 'unitName',
                title: '企业/单位名称',
            }, {
                field: 'creditCode',
                title: '统一社会信用代码',
            }, {
                field: 'manager',
                title: '联系人',
            }, {
                field: 'manaPhone',
                title: '联系电话',
            }, {
                field: 'manaEmail',
                title: '联系邮箱',
            }, {
                field: 'supPlate',
                title: '合作方式',
            }/*, {
                field: 'supMoney',
                title: '资助金额',
            }*/
        ]]
    });

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

    // 导出excel
    $('#btnExp').click(function () {
        SocialForum.exportExcel();
    });
    // 全部导出excel
    $('#btnExpAll').click(function () {
        SocialForum.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + SocialForum.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            SocialForum.openEditDlg(data);
        } else if (layEvent === 'delete') {
            SocialForum.onDeleteItem(data);
        } else if (layEvent === 'approve') {
            SocialForum.openApprove(data);
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
