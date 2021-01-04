layui.use(['table', 'admin','form', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 论坛主题征集表管理
     */
    var CollectTopic = {
        tableId: "collectTopicTable"
    };
    meetSelectOption();
    /**
     * 初始化表格的列
     */
    CollectTopic.initColumn = function () {
        return [[
            {type: 'checkbox'},
            // {field: 'topicId', hide: true, title: '主题ID'},
            // {field: 'createUser', sort: true, title: '创建人ID'},
            {field: 'meetName', sort: true, title: '会议名称'},
            {field: 'topicName', sort: true, title: '大会主题'},
            {field: 'topicDesc', sort: true, title: '选题意义'},
            {field: 'subTopic', sort: true, title: '分论坛主题'},
            // {field: 'voteNum', sort: true, title: '票数'},
            {field: 'userName', sort: true, title: '填报人'},
            {field: 'unitName', sort: true, title: '所在单位'},
            {field: 'userPost', sort: true, title: '职称/职务'},
            // {field: 'createTime', sort: true, title: '创建日期'},
            // {field: 'diy1', sort: true, title: '管理员自定义备用字段1'},
            // {field: 'diy2', sort: true, title: '管理员自定义备用字段2'},
            {align: 'center', toolbar: '#tableBar', title: '操作',minWidth: 120}
        ]];
    };

    /**
     * 点击查询按钮
     */
    CollectTopic.search = function () {
        var queryData = {};

        queryData['createUser'] = $('#createUser').val();
        queryData['topicName'] = $('#topicName').val();
        $('#topicNameExp').val($('#topicName').val());
        queryData['meetId'] = $("#meetId").val();
        table.reload(CollectTopic.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    form.on('select(meetId)', function(data){
        CollectTopic.search();
    });

    /**
     * 弹出添加对话框
     */
    CollectTopic.openAddDlg = function () {
        func.open({
            title: '会议主题征集',
            content: Feng.ctxPath + '/collectTopic/add',
            tableId: CollectTopic.tableId
        });
    };

     /**
      * 点击编辑
      * @param data 点击按钮时候的行数据
      */
      CollectTopic.openEditDlg = function (data) {
          window.location.href = Feng.ctxPath + '/collectTopic/edit?topicId=' + data.topicId;
          /*func.open({
              title: '会议主题征集',
              content: Feng.ctxPath + '/collectTopic/edit?topicId=' + data.topicId,
              tableId: CollectTopic.tableId
          });*/
      };


    /**
     * 导出excel按钮
     */
    CollectTopic.exportExcel = function () {
        var checkRows = table.checkStatus(CollectTopic.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };
    /**
     * 全部导出excel按钮
     */
    CollectTopic.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/collectTopic/wraplist',
            type: 'post',
            data: {
                "topicName":$('#topicNameExp').val(),
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
        title: '主题管理全部数据',
        cols: [[ //表头
            {
                field: 'meetName',
                title: '会议名称',
            },{
                field: 'topicName',
                title: '大会主题',
            }, {
                field: 'topicDesc',
                title: '选题意义',
            }, {
                field: 'subTopic',
                title: '分论坛主题',
            }, {
                field: 'userName',
                title: '填报人',
            }, {
                field: 'unitName',
                title: '所在单位',
            }, {
                field: 'userPost',
                title: '职称/职务',
            }
        ]]
    });

    /**
     * 点击删除
     * @param data 点击按钮时候的行数据
     */
    CollectTopic.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/collectTopic/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CollectTopic.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("topicId", data.topicId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + CollectTopic.tableId,
        url: Feng.ctxPath + '/collectTopic/wraplist',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: CollectTopic.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        CollectTopic.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    CollectTopic.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        CollectTopic.exportExcel();
    });
    // 全部导出excel
    $('#btnExpAll').click(function () {
        CollectTopic.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + CollectTopic.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            CollectTopic.openEditDlg(data);
        } else if (layEvent === 'delete') {
            CollectTopic.onDeleteItem(data);
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
