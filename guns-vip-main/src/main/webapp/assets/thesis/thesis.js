layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 论文表管理
     */
    var Thesis = {
        tableId: "thesisTable"
    };

    /**
     * 初始化表格的列
     */
    Thesis.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'thesisId', hide: true, title: '论文ID'},
            {field: 'thesisTitle', sort: true, title: '论文题目'},
            // {field: 'engTitle', sort: true, title: '英文题目'},
            // {field: 'thesisUser', sort: true, title: '论文作者ID'},
            {field: 'userName', sort: true, title: '论文作者'},
            {field: 'unitsName', sort: true, title: '所在单位'},
            // {field: 'isgreat', sort: true, title: '是否推荐优秀'},
            // {field: 'greatNum', sort: true, title: '推荐专家人数'},
            // {field: 'greatId', sort: true, title: '推优专家ID'},
            // {field: 'applyTime', sort: true, title: '论文提交时间'},
            // {field: 'thesisText', sort: true, title: '正文'},
            // {field: 'score', sort: true, title: '分数'},
            // {field: 'reviewUser', sort: true, title: '评审人ID'},
            // {field: 'great', sort: true, title: '是否为优秀论文'},
            // {field: 'cnKeyword', sort: true, title: '中文关键词'},
            // {field: 'engKeyword', sort: true, title: '英文关键词'},
            {field: 'cnAbstract', sort: true, title: '中文摘要'},
            // {field: 'engAbstract', sort: true, title: '英文摘要'},
            // {field: 'status', sort: true, title: '评审状态'},
            // {field: 'reviewResult', sort: true, title: '评审结果'},
            {field: 'reviewStr', sort: true, title: '评审结果'},
            {field: 'greatStr', sort: true, title: '是否优秀'},
            // {field: 'thesisDirect', sort: true, title: '参会论文研究方向'},
            // {field: 'thesisPath', sort: true, title: '论文附件路径'},
            // {field: 'fileName', sort: true, title: '论文附件文件名'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Thesis.search = function () {
        var queryData = {};

        queryData['thesisTitle'] = $('#thesisTitle').val();

        table.reload(Thesis.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 跳转到添加页面
     */
    Thesis.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/thesis/add'
    };

    /**
    * 跳转到编辑页面
    * @param data 点击按钮时候的行数据
    */
    Thesis.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/edit?thesisId=' + data.thesisId
    };

    /**
     * 查看详情页面
     * @param data 点击按钮时候的行数据
     */
    Thesis.jumpDisablePage = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/disable?thesisId=' + data.thesisId
    };

    /**
     * 跳转到评审页面
     * @param data 点击按钮时候的行数据
     */
    Thesis.onReviewItem = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/review?thesisId=' + data.thesisId
    };

    /**
     * 导出excel按钮
     */
    Thesis.exportExcel = function () {
        var checkRows = table.checkStatus(Thesis.tableId);
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
    Thesis.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/thesis/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Thesis.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("thesisId", data.thesisId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Thesis.tableId,
        url: Feng.ctxPath + '/thesis/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Thesis.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Thesis.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    Thesis.jumpAddPage();

    });

    // 导出excel
    $('#btnExp').click(function () {
        Thesis.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Thesis.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Thesis.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            Thesis.onDeleteItem(data);
        } else if (layEvent === 'review') {
            Thesis.onReviewItem(data);
        } else if (layEvent === 'disable') {
            Thesis.jumpDisablePage(data);
        }
    });
});
