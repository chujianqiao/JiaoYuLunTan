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
            // {field: 'firstStatus', hide: true, title: '状态'},
            // {field: 'firstStatus', sort: true, title: '状态'},
            {field: 'thesisTitle', sort: true, title: '论文题目'},
            {field: 'userName', sort: true, title: '论文作者'},
            {field: 'unitsName', sort: true, title: '所在单位'},
            {field: 'belongDomainStr', sort: true, title: '论文领域'},
            {field: 'firstScore', sort: true, title: '初评分数'},
            {field: 'status', sort: true, title: '初评结果'},
            {field: 'greatStr', sort: true, title: '是否优秀'},
            {align: 'center', title: '操作',templet: function(data){
                    if(data.firstStatus == "未评审"){
                        return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='review'>评审</a>";
                    }else{
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='firstDetail'>查看详情</a>";
                    }
                }}
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
     * 初评后查看详情页面
     * @param data 点击按钮时候的行数据
     */
    Thesis.jumpFirstDetailPage = function (data) {
        debugger;
        window.location.href = Feng.ctxPath + '/thesis/firstDetail?thesisId=' + data.thesisId
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
        url: Feng.ctxPath + '/thesis/reviewList?reviewSort=1',
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
        } else if(layEvent === 'firstDetail'){
            Thesis.jumpFirstDetailPage(data);
        }
    });

    /**--------复评-------**/

    /**
     * 论文表管理
     */
    var ThesisAgain = {
        tableId: "thesisTableAgain"
    };

    /**
     * 初始化表格的列
     */
    ThesisAgain.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'thesisId', hide: true, title: '论文ID'},
            {field: 'thesisTitle', sort: true, title: '论文题目'},
            {field: 'userName', sort: true, title: '论文作者'},
            {field: 'unitsName', sort: true, title: '所在单位'},
            {field: 'belongDomainStr', sort: true, title: '论文领域'},
            {field: 'scoreStr', sort: true, title: '论文分数'},
            {field: 'status', sort: true, title: '初评结果'},
            {field: 'aaa', sort: true, title: '复评结果'},
            {align: 'center', title: '操作',templet: function(data){
                if(data.secondStatus == "未评审"){
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='reviewAgain'>评审</a>";
                }else{
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='secondDetail'>查看详情</a>";
                }
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ThesisAgain.search = function () {
        var queryData = {};
        queryData['thesisTitle'] = $('#thesisTitleAgain').val();
        table.reload(ThesisAgain.tableId, {
            where: queryData, page: {curr: 1}
        });
    };


    /**
     * 跳转到编辑页面
     * @param data 点击按钮时候的行数据
     */
    ThesisAgain.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/edit?thesisId=' + data.thesisId
    };

    /**
     * 查看详情页面
     * @param data 点击按钮时候的行数据
     */
    ThesisAgain.jumpDisablePage = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/disable?thesisId=' + data.thesisId
    };

    /**
     * 跳转到复评页面
     * @param data 点击按钮时候的行数据
     */
    ThesisAgain.jumpReviewAgainPage = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/reviewAgain?thesisId=' + data.thesisId
    };

    /**
     * 复评后查看详情
     * @param data 点击按钮时候的行数据
     */
    ThesisAgain.jumpSecondDetailPage = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/secondDetail?thesisId=' + data.thesisId
    };

    /**
     * 导出excel按钮
     */
    ThesisAgain.exportExcel = function () {
        var checkRows = table.checkStatus(ThesisAgain.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResultAgain.config.id, checkRows.data, 'xls');
        }
    };

    // 渲染表格
    var tableResultAgain = table.render({
        elem: '#' + ThesisAgain.tableId,
        url: Feng.ctxPath + '/thesis/reviewList?reviewSort=2',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ThesisAgain.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearchAgain').click(function () {
        ThesisAgain.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ThesisAgain.jumpAddPage();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ThesisAgain.exportExcel();
    });

    //批量分配
    $('#assignBatchAgain').click(function () {
        ThesisAgain.jumpAssignPageBatchAgain();
    });

    // 工具条点击事件
    table.on('tool(' + ThesisAgain.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ThesisAgain.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            ThesisAgain.onDeleteItem(data);
        } else if (layEvent === 'review') {
            ThesisAgain.onReviewItem(data);
        } else if (layEvent === 'disable') {
            ThesisAgain.jumpDisablePage(data);
        } else if (layEvent === 'reviewAgain') {
            ThesisAgain.jumpReviewAgainPage(data);
        } else if (layEvent === 'secondDetail') {
            ThesisAgain.jumpSecondDetailPage(data);
        }
    });


    /**--------------------------------------------------教改实验---------------------------------------------------------------------**/
    /**
     * 优秀成果表管理
     */
    var EducationResult = {
        tableId: "educationResultTable",
        condition: {
            resultName: "",
        }
    };

    /**
     * 初始化表格的列
     */
    EducationResult.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'resultId', hide: true, title: '成果ID'},
            {field: 'resultName', sort: true, title: '成果名称'},
            {field: 'belongName', sort: true, title: '申请人姓名'},
            {field: 'reviewName', sort: true, title: '评审专家'},
            {field: 'reviewResult', sort: true, title: '评审状态'},
            {field: 'score', sort: true, title: '评审分数'},
            {align: 'center', title: '操作',templet: function(data){
                    if(data.reviewStatus == "未评审"){
                        return "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"reviewEdu\">评审</a>";
                    }else{
                        return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"detail\">查看详情</a>";
                    }
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    EducationResult.search = function () {
        var queryData = {};
        queryData['resultName'] = $("#resultName").val();
        table.reload(EducationResult.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 点击详情
     * @param data 点击按钮时候的行数据
     */
    EducationResult.openDetail = function (data) {
        window.location.href = Feng.ctxPath + '/educationResult/detailAdmin?resultId=' + data.resultId + '&applyType=' + data.applyType;
    };

    /**
     * 评审
     * @param data
     */
    EducationResult.reviewEduPage = function (data) {
        window.location.href = Feng.ctxPath + '/educationResult/reviewPage?resultId=' + data.resultId;
    };

    //点击li标签事件
    $("#eduRes").click(function(){
        EducationResult.search();
    });

    // 渲染表格
    var tableResultEdu = table.render({
        elem: '#' + EducationResult.tableId,
        url: Feng.ctxPath + '/educationResult/wrapListReview',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: EducationResult.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearchEdu').click(function () {
        EducationResult.search();
    });

    // 工具条点击事件
    table.on('tool(' + EducationResult.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'detail') {
            EducationResult.openDetail(data);
        } else if (layEvent === 'reviewEdu'){
            EducationResult.reviewEduPage(data);
        }
    });


    /**-------------------------------------------------优秀论著---------------------------------------------------**/
    /**
     * 优秀成果表管理
     */
    var GreatResult = {
        tableId: "greatResultTable",
        condition: {
            resultName: "",
        }
    };

    /**
     * 初始化表格的列
     */
    GreatResult.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'resultId', hide: true, title: '成果ID'},
            {field: 'resultName', sort: true, title: '成果名称'},
            {field: 'belongName', sort: true, title: '申请人姓名'},
            {field: 'reviewName', sort: true, title: '评审专家'},
            {field: 'reviewResult', sort: true, title: '评审状态'},
            {field: 'score', sort: true, title: '评审分数'},
            {align: 'center', title: '操作',templet: function(data){
                    // return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"reviewDetail\">查看详情</a>\n" +
                    //     "    <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"reviewGreat\">评审</a>\n";
                    if(data.reviewResult == "未评审"){
                        return "<a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"reviewGreat\">评审</a>";
                    }else{
                        return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"reviewDetail\">查看详情</a>";
                    }
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    GreatResult.search = function () {
        var queryData = {};
        queryData['resultName'] = $("#greatResultName").val();
        table.reload(GreatResult.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    //点击li标签事件
    $("#greatRes").click(function(){
        GreatResult.search();
    });

    /**
     * 点击详情
     * @param data 点击按钮时候的行数据
     */
    GreatResult.openDetail = function (data) {
        window.location.href = Feng.ctxPath + '/greatResult/reviewDetail?resultId=' + data.resultId;
    };

    /**
     * 评审页面
     * @param data
     */
    GreatResult.reviewGreatPage = function (data) {
        window.location.href = Feng.ctxPath + '/greatResult/reviewPage?resultId=' + data.resultId;
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + GreatResult.tableId,
        url: Feng.ctxPath + '/greatResult/wrapListReview',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: GreatResult.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearchGreat').click(function () {
        GreatResult.search();
    });

    // 工具条点击事件
    table.on('tool(' + GreatResult.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'reviewDetail') {
            GreatResult.openDetail(data);
        } else if (layEvent === 'reviewGreat'){
            GreatResult.reviewGreatPage(data);
        }
    });
});
