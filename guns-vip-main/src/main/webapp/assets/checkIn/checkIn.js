layui.use(['table', 'form', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 报到签到表管理
     */
    var CheckIn = {
        tableId: "checkInTable",
        condition: {
            signStatus: "",
        }
    };

    roleSelectOption();

    /**
     * 初始化表格的列
     */
    CheckIn.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'checkId', hide: true, title: '报到签到ID'},
            {field: 'name', sort: true, title: '姓名'},
            //{field: 'post', sort: true, title: '职称'},
            {field: 'roleName', sort: true, title: '参会角色'},
            {field: 'registerStatus', sort: true, title: '报到状态'},
            {field: 'registerTime', sort: true, title: '报到时间'},
            {field: 'signStatus', sort: true, title: '签到状态'},
            {field: 'signTime', sort: true, title: '签到时间'},
            {field: 'signPlace', sort: true, title: '签到地点'},
            {align: 'center', toolbar: '#tableBar', minWidth: 180, title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    CheckIn.search = function () {
        var queryData = {};
        queryData['signStatus'] = $("#signStatus").val();
        queryData['roleId'] = $("#roleId").val();
        queryData['name'] = $("#name").val();
        $("#nameExp").val($("#name").val());
        table.reload(CheckIn.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    form.on('select(status)', function(data){
        CheckIn.search();
    });
    form.on('select(roleId)', function(data){
        CheckIn.search();
    });


    /**
     * 跳转到添加页面
     */
    CheckIn.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/checkIn/add'
    };

    /**
    * 跳转到编辑页面
    *
    * @param data 点击按钮时候的行数据
    */
    CheckIn.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/checkIn/edit?checkId=' + data.checkId
    };

    /**
     * 跳转到详情页面
     *
     * @param data 点击按钮时候的行数据
     */
    CheckIn.onDetail = function (data) {
        window.location.href = Feng.ctxPath + '/checkIn/toDetail?checkId=' + data.checkId
    };

    /**
     * 导出excel按钮
     */
    CheckIn.exportExcel = function () {
        var checkRows = table.checkStatus(CheckIn.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };
    /**
     * 导出excel按钮
     */
    CheckIn.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/checkIn/wrapList',
            type: 'post',
            data: {
                "name":$('#nameExp').val(),
                "signStatus":$("#signStatus").val(),
                "roleId":$("#roleId").val()
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
        title: '大会报到签到信息全部数据',
        cols: [[ //表头
            {
                field: 'name',
                title: '姓名',
            }, {
                field: 'roleName',
                title: '参会角色',
            }, {
                field: 'registerStatus',
                title: '报到状态',
            }, {
                field: 'registerTime',
                title: '报到时间',
            }, {
                field: 'signStatus',
                title: '签到状态',
            }, {
                field: 'signTime',
                title: '签到时间',
            }, {
                field: 'signPlace',
                title: '签到地点',
            }
        ]]
    });

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CheckIn.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/checkIn/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CheckIn.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("checkId", data.checkId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + CheckIn.tableId,
        url: Feng.ctxPath + '/checkIn/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: CheckIn.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        CheckIn.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    CheckIn.jumpAddPage();

    });

    // 导出excel
    $('#btnExp').click(function () {
        CheckIn.exportExcel();
    });
    // 导出excel
    $('#btnExpAll').click(function () {
        CheckIn.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + CheckIn.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            CheckIn.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            CheckIn.onDeleteItem(data);
        } else if (layEvent === 'detail') {
            CheckIn.onDetail(data);
        }
    });

    function roleSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/role/list" ,
            success:function(response){
                var data=response.data;
                var roles = [];
                roles = data;

                var options;
                for (var i = 0 ;i < roles.length ;i++){
                    options += '<option value="'+ roles[i].roleId+ '" >'+ roles[i].name +'</option>';
                }
                $('#roleId').empty();
                $('#roleId').append("<option value=''>请选择角色</option>");
                $('#roleId').append(options);
                $('#roleIdForum').empty();
                $('#roleIdForum').append("<option value=''>请选择角色</option>");
                $('#roleIdForum').append(options);
                form.render('select');
            }
        })
    }


    //-------------------------------------------------------------------------

    /**
     * 报到签到表管理
     */
    var CheckInForum = {
        tableId: "checkInTableForum",
        condition: {
            signStatus: ""
        }
    };

    forumSelectOption();

    /**
     * 初始化表格的列
     */
    CheckInForum.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'checkId', hide: true, title: '报到签到ID'},
            {field: 'name', sort: true, title: '姓名'},
            //{field: 'post', sort: true, title: '职称'},
            {field: 'roleName', sort: true, title: '参会角色'},
            {field: 'forumName', sort: true, title: '参会分论坛'},
            {field: 'registerStatus', sort: true, title: '报到状态'},
            {field: 'registerTime', sort: true, title: '报到时间'},
            {field: 'signStatus', sort: true, title: '签到状态'},
            {field: 'signTime', sort: true, title: '签到时间'},
            {field: 'signPlace', sort: true, title: '签到地点'},
            {align: 'center', toolbar: '#tableBarForum', minWidth: 180, title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    CheckInForum.search = function () {
        var queryData = {};
        queryData['signStatus'] = $("#signStatusForum").val();
        queryData['forumId'] = $("#forumId").val();
        queryData['name'] = $("#nameForum").val();
        queryData['roleId'] = $("#roleIdForum").val();
        $("#nameForumExp").val($("#nameForum").val());
        table.reload(CheckInForum.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    form.on('select(statusForum)', function(data){
        CheckInForum.search();
    });
    form.on('select(forumId)', function(data){
        CheckInForum.search();
    });
    form.on('select(roleIdForum)', function(data){
        CheckInForum.search();
    });


    /**
     * 跳转到添加页面
     */
    CheckInForum.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/checkIn/add'
    };

    /**
     * 跳转到编辑页面
     *
     * @param data 点击按钮时候的行数据
     */
    CheckInForum.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/checkIn/edit?checkId=' + data.checkId
    };

    /**
     * 跳转到详情页面
     *
     * @param data 点击按钮时候的行数据
     */
    CheckInForum.onDetail = function (data) {
        window.location.href = Feng.ctxPath + '/checkIn/toDetail?checkId=' + data.checkId
    };

    /**
     * 导出excel按钮
     */
    CheckInForum.exportExcel = function () {
        var checkRows = table.checkStatus(CheckInForum.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResultForum.config.id, checkRows.data, 'xls');
        }
    };
    /**
     * 导出excel按钮
     */
    CheckInForum.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/checkIn/wrapListForum',
            type: 'post',
            data: {
                "name":$('#nameForumExp').val(),
                "forumId":$("#forumId").val(),
                "signStatus":$("#signStatusForum").val(),
                "roleId":$("#roleIdForum").val()
            },
            async: false,
            dataType: 'json',
            success: function (res) {
                //使用table.exportFile()导出数据
                //console.log(res.data);
                table.exportFile('exportTableForum', res.data, 'xlsx');
            }
        });
    };
    table.render({
        elem: '#tableExpAllForum',
        id: 'exportTableForum',
        title: '分论坛报到签到全部数据',
        cols: [[ //表头
            {
                field: 'name',
                title: '姓名',
            }, {
                field: 'roleName',
                title: '参会角色',
            }, {
                field: 'forumName',
                title: '参会分论坛',
            }, {
                field: 'registerStatus',
                title: '报到状态',
            }, {
                field: 'registerTime',
                title: '报到时间',
            }, {
                field: 'signStatus',
                title: '签到状态',
            }, {
                field: 'signTime',
                title: '签到时间',
            }, {
                field: 'signPlace',
                title: '签到地点',
            }
        ]]
    });

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CheckInForum.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/checkIn/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CheckInForum.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("checkId", data.checkId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResultForum = table.render({
        elem: '#' + CheckInForum.tableId,
        url: Feng.ctxPath + '/checkIn/wrapListForum',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: CheckInForum.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearchForum').click(function () {
        CheckInForum.search();
    });

    // 添加按钮点击事件
    $('#btnAddForum').click(function () {

        CheckInForum.jumpAddPage();

    });

    // 导出excel
    $('#btnExpForum').click(function () {
        CheckInForum.exportExcel();
    });
    // 导出excel
    $('#btnExpAllForum').click(function () {
        CheckInForum.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + CheckInForum.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'editForum') {
            CheckInForum.jumpEditPage(data);
        } else if (layEvent === 'deleteForum') {
            CheckInForum.onDeleteItem(data);
        } else if (layEvent === 'detailForum') {
            CheckInForum.onDetail(data);
        }
    });

    function forumSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/forum/list" ,
            success:function(response){
                var data=response.data;
                var forums = [];
                forums = data;

                var options;
                for (var i = 0 ;i < forums.length ;i++){
                    if(forums[i].status == "0"){
                        continue;
                    }
                    options += '<option value="'+ forums[i].forumId+ '" >'+ forums[i].forumName +'</option>';
                }
                $('#forumId').empty();
                $('#forumId').append("<option value=''>请选择分论坛</option>");
                $('#forumId').append(options);
                form.render('select');
            }
        })
    }

});
