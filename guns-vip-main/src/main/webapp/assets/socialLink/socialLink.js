layui.use(['table', 'form', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 合作方式表管理
     */
    var SocialLink = {
        tableId: "socialLinkTable"
    };

    /**
     * 初始化表格的列
     */
    SocialLink.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'linkId', hide: true, title: '环节ID'},
            {field: 'linkName', sort: true, title: '环节名称'},
            {field: 'description', sort: true, title: '备注'},
            {field: 'status', align: "center", sort: true, templet: '#statusTpl', title: '启用状态'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'sort', sort: true, title: '排序'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    SocialLink.search = function () {
        var queryData = {};

        queryData['linkName'] = $('#linkName').val();
        $('#linkNameExp').val($('#linkName').val());
        table.reload(SocialLink.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    SocialLink.openAddDlg = function () {
        func.open({
            title: '添加合作方式表',
            content: Feng.ctxPath + '/socialLink/add',
            tableId: SocialLink.tableId
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      SocialLink.openEditDlg = function (data) {
          func.open({
              title: '修改合作方式表',
              content: Feng.ctxPath + '/socialLink/edit?linkId=' + data.linkId,
              tableId: SocialLink.tableId
          });
      };


    /**
     * 导出excel按钮
     */
    SocialLink.exportExcel = function () {
        var checkRows = table.checkStatus(SocialLink.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };
    /**
     * 导出excel按钮
     */
    SocialLink.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/socialLink/listAll',
            type: 'post',
            data: {
                "linkName":$('#linkNameExp').val()
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
        title: '合作字典全部数据',
        cols: [[ //表头
            {
                field: 'linkName',
                title: '方式名称',
            }, {
                field: 'description',
                title: '备注',
            }, {
                field: 'createTime',
                title: '创建时间',
            }
        ]]
    });

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    SocialLink.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/socialLink/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(SocialLink.tableId);
            }, function (data) {
                var rulemodeid = $("#message").val();
                console.log(rulemodeid);
                Feng.error("删除失败!" + /*data.responseJSON.message*/ + "!");
            });
            ajax.set("linkId", data.linkId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + SocialLink.tableId,
        url: Feng.ctxPath + '/socialLink/listAll',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: SocialLink.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        SocialLink.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    SocialLink.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        SocialLink.exportExcel();
    });
    // 导出excel
    $('#btnExpAll').click(function () {
        SocialLink.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + SocialLink.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            SocialLink.openEditDlg(data);
        } else if (layEvent === 'delete') {
            SocialLink.onDeleteItem(data);
        }
    });


    // 修改环节启用状态
    form.on('switch(status)', function (obj) {

        var reviewId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        SocialLink.changeUserStatus(reviewId, checked);
    });

    /**
     * 修改环节启用状态
     *
     * @param linkId
     * @param checked 是否选中（true,false），选中就是解锁用户，未选中就是锁定用户
     */
    SocialLink.changeUserStatus = function (linkId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/socialLink/unfreeze", function (data) {
                Feng.success("解除冻结成功!");
            }, function (data) {
                Feng.error("解除冻结失败!");
                table.reload(SocialLink.tableId);
            });
            ajax.set("linkId", linkId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/socialLink/freeze", function (data) {
                Feng.success("冻结成功!");
            }, function (data) {
                Feng.error("冻结失败!" + data.responseJSON.message + "!");
                table.reload(SocialLink.tableId);
            });
            ajax.set("linkId", linkId);
            ajax.start();
        }
    };
});
