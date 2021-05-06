layui.use(['table', 'admin', 'form', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 论文表管理
     */
    var Thesis = {
        tableId: "thesisTable"
    };
    meetSelectOption();
    var langs = layui.data('system').lang;
    /**
     * 初始化表格的列
     */
    Thesis.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'thesisId', hide: true, title: '论文ID'},
            {field: 'meetName', sort: true, title: langs.FIELD_ConferenceName},
            // {field: 'firstStatus', hide: true, title: '状态'},
            // {field: 'firstStatus', sort: true, title: '状态'},
            {field: 'thesisTitle', sort: true, title: langs.FIELD_TitlePaper},
            {field: 'userName', sort: true, title: langs.FIELD_AuthorPaper},
            {field: 'unitsName', sort: true, title: langs.FIELD_FromOrganization},
            {field: 'belongDomainStr', sort: true, title: langs.FIELD_RAOP},
            {field: 'firstScore', sort: true, title: langs.FIELD_SOIR},
            {field: 'status', sort: true, title: langs.FIELD_OOIR},
            //{field: 'finalResult', sort: true, title: '是否推优'},
            {align: 'center', title: langs.FIELD_Operate,templet: function(data){

                if (data.meetTimeStatusStr == "报名中" || data.meetTimeStatusStr == "报名结束"){
                    if (data.status == "已取消参会"){
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='firstDetail' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a>";
                    }else {
                        if(data.firstStatus == "未评审"){
                            return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='review' title='" + langs.FIELD_Review + "'>" + langs.FIELD_Review + "</a>";
                        }else{
                            if (data.finalResult == 0) {
                                return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='review' title='" + langs.FIELD_RevisionReview + "'>" + langs.FIELD_RevisionReview + "</a>";
                            } else {
                                return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='firstDetail' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a>";
                            }
                        }
                    }
                } else {
                    return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='firstDetail'>" + langs.FIELD_SeeDetails + "</a>";
                }

                    /*if(data.firstStatus == "未评审"){
                        return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='review'>评审</a>";
                    }else{
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='firstDetail'>查看详情</a>";
                    }*/
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Thesis.search = function () {
        var queryData = {};

        queryData['thesisTitle'] = $('#thesisTitle').val();
        queryData['reviewStatus'] = $('#reviewStatus').val();
        queryData['meetId'] = $("#meetId").val();
        table.reload(Thesis.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    form.on('select(meetId)', function(data){
        Thesis.search();
    });
    form.on('select(reviewStatus)', function(data){
        Thesis.search();
    });
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
            {field: 'meetName', sort: true, title: langs.FIELD_ConferenceName},
            {field: 'thesisTitle', sort: true, title: langs.FIELD_TitlePaper},
            {field: 'userName', sort: true, title: langs.FIELD_AuthorPaper},
            {field: 'unitsName', sort: true, title: langs.FIELD_FromOrganization},
            {field: 'belongDomainStr', sort: true, title: langs.FIELD_RAOP},
            {field: 'scoreStr', sort: true, title: langs.FIELD_SOFR},
            {field: 'status', sort: true, title: langs.FIELD_OOIR},
            {field: 'greatStr', sort: true, title: langs.FIELD_RecommendOrNot},
            {align: 'center', title: langs.FIELD_Operate,templet: function(data){
                    if (data.meetTimeStatusStr == "报名中" || data.meetTimeStatusStr == "报名结束"){
                        if(data.secondStatus == "未评审"){
                            return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='reviewAgain' title='" + langs.FIELD_Review + "'>" + langs.FIELD_Review + "</a>";
                        }else{
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='secondDetail' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a>";
                        }
                    }else {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='secondDetail' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a>";
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
        queryData['meetId'] = $("#meetIdAgain").val();
        table.reload(ThesisAgain.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    form.on('select(meetIdAgain)', function(data){
        ThesisAgain.search();
    });

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

    function meetSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/meet/wrapList" ,
            success:function(response){
                var data=response.data;
                var meet = [];
                meet = data;

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
                $('#meetIdAgain').empty();
                $('#meetIdAgain').append("<option value='0'>请选择会议</option>");
                $('#meetIdAgain').append(options);
                $('#meetIdResult').empty();
                $('#meetIdResult').append("<option value='0'>请选择会议</option>");
                $('#meetIdResult').append(options);
                $('#meetIdResult2').empty();
                $('#meetIdResult2').append("<option value='0'>请选择会议</option>");
                $('#meetIdResult2').append(options);
                form.render('select');
            }
        })
    }


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
            {field: 'meetName', sort: true, title: langs.FIELD_ConferenceName},
            {field: 'resultName', sort: true, title: langs.FIELD_TitleDeliverables},
            {field: 'belongName', sort: true, title: langs.FIELD_NameApplicant},
            {field: 'reviewName', sort: true, title: langs.FIELD_ReviewExperts},
            {field: 'reviewResult', sort: true, title: langs.FIELD_Reviewstatus},
            {field: 'score', sort: true, title: langs.FIELD_SOR},
            {align: 'center', title: langs.FIELD_Operate,templet: function(data){
                    if(data.reviewStatus == "未评审"){
                        return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='reviewEdu' title='" + langs.FIELD_Review + "'>" + langs.FIELD_Review + "</a>";
                    }else{
                        if (data.finalResult == 0) {
                            return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='reviewEdu' title='" + langs.FIELD_RevisionReview + "'>" + langs.FIELD_RevisionReview + "</a>";
                        } else {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='eduDetail' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a>";
                        }
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
        queryData['meetId'] = $("#meetIdResult").val();
        queryData['checkStatus'] = $("#reviewStatusResult").val();
        table.reload(EducationResult.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    form.on('select(meetIdResult)', function(data){
        EducationResult.search();
    });
    form.on('select(reviewStatusResult)', function(data){
        EducationResult.search();
    });
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
        if (layEvent === 'eduDetail') {
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
            {field: 'meetName', sort: true, title: langs.FIELD_ConferenceName},
            {field: 'resultName', sort: true, title: langs.FIELD_TitleDeliverables},
            {field: 'belongName', sort: true, title: langs.FIELD_NameApplicant},
            {field: 'reviewName', sort: true, title: langs.FIELD_ReviewExperts},
            {field: 'reviewResult', sort: true, title: langs.FIELD_Reviewstatus},
            {field: 'score', sort: true, title: langs.FIELD_SOR},
            {align: 'center', title: langs.FIELD_Operate,templet: function(data){
                    // return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"reviewDetail\">查看详情</a>\n" +
                    //     "    <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"reviewGreat\">评审</a>\n";
                    if(data.reviewResult == "未评审"){
                        return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='reviewGreat' title='" + langs.FIELD_Review + "'>" + langs.FIELD_Review + "</a>";
                    }else{
                        if (data.finalResult == 0) {
                            return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='reviewGreat' title='" + langs.FIELD_RevisionReview + "'>" + langs.FIELD_RevisionReview + "</a>";
                        } else {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='reviewDetail' title='" + langs.FIELD_SeeDetails + "'>" + langs.FIELD_SeeDetails + "</a>";
                        }
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
        queryData['meetId'] = $("#meetIdResult2").val();
        queryData['checkStatus'] = $("#reviewStatusResult2").val();
        table.reload(GreatResult.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    form.on('select(meetIdResult2)', function(data){
        GreatResult.search();
    });
    form.on('select(reviewStatusResult2)', function(data){
        GreatResult.search();
    });
    //点击li标签事件
    $("#greatRes").click(function(){
        GreatResult.search();
    });

    /**
     * 点击详情
     * @param data 点击按钮时候的行数据
     */
    GreatResult.openDetail = function (data) {
        window.location.href = Feng.ctxPath + '/greatResult/detailAdmin?resultId=' + data.resultId + '&applyType=' + data.applyType;
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
