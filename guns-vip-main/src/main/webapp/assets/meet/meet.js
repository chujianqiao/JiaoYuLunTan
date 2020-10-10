layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 会议表管理
     */
    var Meet = {
        tableId: "meetTable"
    };

    /**
     * 初始化表格的列
     */
    Meet.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'meetId', hide: true, title: '会议ID'},
            {field: 'meetName', sort: true, title: '会议名称'},
            // {field: 'meetDesc', sort: true, title: '会议描述'},
            {field: 'place', sort: true, title: '会议地点'},
            {field: 'peopleNum', sort: true, title: '参会人数限制'},
            {field: 'thesisNum', sort: true, title: '投稿人数限制'},
            {field: 'beginTime', sort: true, title: '开始时间'},
            {field: 'endTime', sort: true, title: '结束时间'},
            // {field: 'regUser', sort: true, title: '注册人'},
            {field: 'regName', sort: true, title: '注册人'},
            {field: 'regTime', sort: true, title: '注册时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作',minWidth:230}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Meet.search = function () {
        var queryData = {};
        table.reload(Meet.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 跳转到添加页面
     */
    Meet.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/meet/add'
    };

    /**
    * 跳转到编辑页面
    * @param data 点击按钮时候的行数据
    */
    Meet.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/meet/edit?meetId=' + data.meetId
    };

    /**
     * 导出excel按钮
     */
    Meet.exportExcel = function () {
        var checkRows = table.checkStatus(Meet.tableId);
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
    Meet.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meet/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Meet.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("meetId", data.meetId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 点击发布
     * @param data 点击按钮时候发布会议
     */
    Meet.onPublishItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meet/pubMeet", function (data) {
                Feng.success("发布成功!");
                table.reload(Meet.tableId);
            }, function (data) {
                Feng.error("发布失败!" + data.responseJSON.message + "!");
            });
            ajax.set("meetId", data.meetId);
            ajax.start();
        };
        Feng.confirm("只能有一个状态为“发布”的会议，是否发布?", operation);
    };

    /**
     * 下载会议手册
     * @param data
     */
    Meet.downloadWord = function (data) {
        let content = data.content;
        if(content == '' || content == undefined || content == 'undefined'){
            Feng.error("没有内容！");
        }else{
            Feng.info("正在生成文档，请稍等");
            let meetId = data.meetId;
            let form=$("<form>");    // 定义一个form表单
            form.attr("style","display:none");
            // form.attr("target","_blank");
            form.attr("method","post");
            form.attr("action",Feng.ctxPath + "/meet/exportWord?meetId=" + meetId);    // 此处填写文件下载提交路径
            $("body").append(form);    // 将表单放置在web中
            form.submit();
        }
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Meet.tableId,
        url: Feng.ctxPath + '/meet/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Meet.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Meet.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Meet.jumpAddPage();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Meet.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Meet.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Meet.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            Meet.onDeleteItem(data);
        } else if (layEvent === 'publish') {
            Meet.onPublishItem(data);
        } else if (layEvent === 'meetWord') {
            Meet.downloadWord(data);
        }
    });
});
