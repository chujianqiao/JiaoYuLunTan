layui.use(['table', 'admin', 'ax', 'func','upload'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var upload = layui.upload;

    /**
     * 会议材料表管理
     */
    var MeetMaterial = {
        tableId: "meetMaterialTable"
    };

    /**
     * 初始化表格的列
     */
    MeetMaterial.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'materialId', hide: true, title: '材料ID'},
            // {field: 'matPath', sort: true, title: '材料路径'},
            {field: 'matName', sort: true, title: '材料名称'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    //上传文件
    upload.render({
        elem: '#btnUpload'
        , url: Feng.ctxPath + '/meetMaterial/upload'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {

            });
        }
        , done: function (res) {
            Feng.success(res.message);
            window.location.href = Feng.ctxPath + '/meetMaterial'
        }
        , error: function () {
            Feng.error("上传文件失败！");
        }
    });

    /**
     * 点击查询按钮
     */
    MeetMaterial.search = function () {
        var queryData = {};
        table.reload(MeetMaterial.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 跳转到添加页面
     */
    MeetMaterial.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/meetMaterial/add'
    };

    /**
    * 跳转到编辑页面
    * @param data 点击按钮时候的行数据
    */
    MeetMaterial.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/meetMaterial/edit?materialId=' + data.materialId
    };

    /**
     * 导出excel按钮
     */
    MeetMaterial.exportExcel = function () {
        var checkRows = table.checkStatus(MeetMaterial.tableId);
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
    MeetMaterial.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMaterial/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(MeetMaterial.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("materialId", data.materialId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MeetMaterial.tableId,
        url: Feng.ctxPath + '/meetMaterial/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: MeetMaterial.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MeetMaterial.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    MeetMaterial.jumpAddPage();

    });

    // 导出excel
    $('#btnExp').click(function () {
        MeetMaterial.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + MeetMaterial.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MeetMaterial.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            MeetMaterial.onDeleteItem(data);
        }
    });
});
