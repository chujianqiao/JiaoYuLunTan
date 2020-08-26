layui.use(['table', 'form', 'admin', 'ax', 'func'], function () {
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
        tableId: "thesisTable",
        condition: {
            belongDomain: "",
        }
    };

    $(function () {
        domainSelectOption();
    })

    /**
     * 初始化表格的列
     */
    Thesis.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'thesisId', hide: true, title: '论文ID'},
            {field: 'thesisTitle', sort: true, title: '论文名称（中文）'},
            {field: 'engTitle', sort: true, title: '论文名称（英文）'},
            {field: 'userName', sort: true, title: '作者'},
            {field: 'firstName', sort: true, title: '评审专家'},
            {field: 'status', sort: true, title: '评审状态'},
            {field: 'reviewTime', sort: true, title: '评审时间'},
            {align: 'center', title: '操作',minWidth:220,templet: function(data){
                        return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"disable\">查看详情</a>\n" +
                            "    <a class=\"layui-btn layui-btn-normal layui-btn-xs\" lay-event=\"assign\">分配专家</a>\n" +
                            "    <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Thesis.search = function () {
        var queryData = {};

        queryData['thesisTitle'] = $('#thesisTitle').val();
        queryData['belongDomain'] = $('#belongDomain').val();
        $('#thesisTitleExp').val($('#thesisTitle').val());
        table.reload(Thesis.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    form.on('select(belongDomain)', function(data){
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
     * 跳转到评审页面
     * @param data 点击按钮时候的行数据
     */
    Thesis.onReviewItem = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/review?thesisId=' + data.thesisId
    };

    Thesis.jumpAssignPage = function (data) {
        func.open({
            title: '分配评审人',
            area: ['350px', '300px'],
            content: Feng.ctxPath + '/thesis/assign?thesisId=' + data.thesisId,
            tableId: Thesis.tableId
        });
    };

    Thesis.jumpAssignPageBatch = function (data) {
        var checkRows = table.checkStatus(Thesis.tableId);
        var belongDomain = $("#belongDomain").val();
        if (checkRows.data.length === 0) {
            Feng.error("请选择被分配的数据");
        } else if (belongDomain == ""){
            Feng.error("请先根据领域筛选，再进行批量分配。");
        } else {
            var thesisIds = "";
            for (var i = 0;i < checkRows.data.length;i++){
                thesisIds = thesisIds + checkRows.data[i].thesisId + ";";
            }
            func.open({
                title: '分配评审人',
                area: ['350px', '300px'],
                content: Feng.ctxPath + '/thesis/assign?thesisId=' + thesisIds,
                tableId: Thesis.tableId
            });
        }
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
     * 导出excel按钮
     */
    Thesis.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/thesis/wrapListFirst',
            type: 'post',
            data: {
                "thesisTitle":$('#thesisTitleExp').val(),
                "belongDomain":$('#belongDomain').val()
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
        title: '论文初评全部数据',
        cols: [[ //表头
            {
                field: 'thesisTitle',
                title: '论文名称（中文）',
            }, {
                field: 'engTitle',
                title: '论文名称（英文）',
            }, {
                field: 'userName',
                title: '作者',
            }, {
                field: 'firstName',
                title: '评审专家',
            }, {
                field: 'status',
                title: '评审状态',
            }, {
                field: 'reviewTime',
                title: '评审时间',
            }
        ]]
    });

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
        url: Feng.ctxPath + '/thesis/wrapListFirst',
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
    // 导出excel
    $('#btnExpAll').click(function () {
        Thesis.exportExcelAll();
    });

    //批量分配
    $('#assignBatch').click(function () {
        Thesis.jumpAssignPageBatch();
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
        } else if (layEvent === 'assign') {
            Thesis.jumpAssignPage(data);
        }
    });


    //复评---------------------------------------------------

    /**
     * 论文表管理
     */
    var ThesisAgain = {
        tableId: "thesisTableAgain",
        condition: {
            belongDomain: "",
        }
    };

    /**
     * 初始化表格的列
     */
    ThesisAgain.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'thesisId', hide: true, title: '论文ID'},
            {field: 'thesisTitle', sort: true, title: '论文名称（中文）'},
            {field: 'engTitle', sort: true, title: '论文名称（英文）'},
            // {field: 'engTitle', sort: true, title: '英文题目'},
            // {field: 'thesisUser', sort: true, title: '论文作者ID'},
            {field: 'userName', sort: true, title: '作者'},
            {field: 'againName', sort: true, title: '评审专家'},
            //{field: 'unitsName', sort: true, title: '所在单位'},
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
            //{field: 'cnAbstract', sort: true, title: '中文摘要'},
            // {field: 'engAbstract', sort: true, title: '英文摘要'},
            {field: 'status', sort: true, title: '评审状态'},
            {field: 'reviewTime', sort: true, title: '评审时间'},
            // {field: 'reviewResult', sort: true, title: '评审结果'},
            //{field: 'belongDomainStr', sort: true, title: '论文领域'},
            //{field: 'reviewStr', sort: true, title: '评审结果'},
            // {field: 'thesisDirect', sort: true, title: '参会论文研究方向'},
            // {field: 'thesisPath', sort: true, title: '论文附件路径'},
            // {field: 'fileName', sort: true, title: '论文附件文件名'},
            {align: 'center', title: '操作',minWidth:220,templet: function(data){
                    return "<a class=\"layui-btn layui-btn-primary layui-btn-xs\" lay-event=\"disable\">查看详情</a>\n" +
                        "    <a class=\"layui-btn layui-btn-normal layui-btn-xs\" lay-event=\"assignAgain\">分配专家</a>\n" +
                        "    <a class=\"layui-btn layui-btn-danger layui-btn-xs\" lay-event=\"delete\">删除</a>";
                }}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ThesisAgain.search = function () {
        var queryData = {};

        queryData['thesisTitle'] = $('#thesisTitleAgain').val();
        queryData['belongDomain'] = $("#belongDomainAgain").val();
        $('#thesisTitleExpAgain').val($('#thesisTitleAgain').val());
        table.reload(ThesisAgain.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    form.on('select(belongDomainAgain)', function(data){
        ThesisAgain.search();
    });

    /**
     * 跳转到添加页面
     */
    ThesisAgain.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/thesis/add'
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
        debugger;
        window.location.href = Feng.ctxPath + '/thesis/disable?thesisId=' + data.thesisId
    };

    /**
     * 跳转到评审页面
     * @param data 点击按钮时候的行数据
     */
    ThesisAgain.onReviewItem = function (data) {
        window.location.href = Feng.ctxPath + '/thesis/review?thesisId=' + data.thesisId
    };

    ThesisAgain.jumpAssignPageAgain = function (data) {
        func.open({
            title: '分配评审人',
            area: ['350px', '300px'],
            content: Feng.ctxPath + '/thesis/assignAgain?thesisId=' + data.thesisId,
            tableId: ThesisAgain.tableId
        });
    };

    ThesisAgain.jumpAssignPageBatchAgain = function (data) {
        var checkRows = table.checkStatus(ThesisAgain.tableId);
        var belongDomainAgain = $("#belongDomainAgain").val();
        if (checkRows.data.length === 0) {
            Feng.error("请选择被分配的数据");
        } else if (belongDomainAgain == ""){
            Feng.error("请先根据领域筛选，再进行批量分配。");
        } else {
            var thesisIds = "";
            for (var i = 0;i < checkRows.data.length;i++){
                thesisIds = thesisIds + checkRows.data[i].thesisId + ";";
            }
            func.open({
                title: '分配评审人',
                area: ['350px', '300px'],
                content: Feng.ctxPath + '/thesis/assignAgain?thesisId=' + thesisIds,
                tableId: ThesisAgain.tableId
            });
        }
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
    /**
     * 导出excel按钮
     */
    ThesisAgain.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/thesis/wrapListAgain',
            type: 'post',
            data: {
                "thesisTitle":$('#thesisTitleExpAgain').val(),
                "belongDomain":$('#belongDomain').val()
            },
            async: false,
            dataType: 'json',
            success: function (res) {
                //使用table.exportFile()导出数据
                //console.log(res.data);
                table.exportFile('exportTableAgain', res.data, 'xlsx');
            }
        });
    };
    table.render({
        elem: '#tableExpAllAgain',
        id: 'exportTableAgain',
        title: '论文复评全部数据',
        cols: [[ //表头
            {
                field: 'thesisTitle',
                title: '论文名称（中文）',
            }, {
                field: 'engTitle',
                title: '论文名称（英文）',
            }, {
                field: 'userName',
                title: '作者',
            }, {
                field: 'againName',
                title: '评审专家',
            }, {
                field: 'status',
                title: '评审状态',
            }, {
                field: 'reviewTime',
                title: '评审时间',
            }
        ]]
    });


    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    ThesisAgain.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/thesis/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ThesisAgain.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("thesisId", data.thesisId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResultAgain = table.render({
        elem: '#' + ThesisAgain.tableId,
        url: Feng.ctxPath + '/thesis/wrapListAgain',
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
    $('#btnAddAgain').click(function () {

        ThesisAgain.jumpAddPage();

    });

    // 导出excel
    $('#btnExpAgain').click(function () {
        ThesisAgain.exportExcel();
    });
    // 导出excel
    $('#btnExpAllAgain').click(function () {
        ThesisAgain.exportExcelAll();
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
        } else if (layEvent === 'assignAgain') {
            ThesisAgain.jumpAssignPageAgain(data);
        }
    });

    function domainSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/thesisDomain/list" ,
            success:function(response){
                var data=response.data;
                var domains = [];
                domains = data;

                var options;
                for (var i = 0 ;i < domains.length ;i++){
                    var domain = data[i];
                    options += '<option value="'+ domain.domainId+ '" >'+ domain.domainName +'</option>';
                }
                $('#belongDomain').empty();
                $('#belongDomain').append("<option value=''>请选择领域</option>");
                $('#belongDomain').append(options);
                $('#belongDomainAgain').empty();
                $('#belongDomainAgain').append("<option value=''>请选择领域</option>");
                $('#belongDomainAgain').append(options);
                form.render('select');
            }
        })
    }
});
