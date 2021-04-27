layui.use(['table', 'admin','form', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;
    /**
     * 管理
     */
    var Seat = {
        tableId: "seatTable"
    };
    meetSelectOption();
    /**
     * 初始化表格的列
     */
    Seat.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'seatId', hide: true, title: '主键'},
            {field: 'meetName', sort: true, title: '会议名称'},
            {field: 'meetTypeStr', sort: true, title: '类型'},
            // {field: 'colNum', sort: true, title: '列数'},
            // {field: 'rowNum', sort: true, title: '行数'},
            {field: 'seatTypeStr', sort: true, title: '排列方式'},
            {align: 'center', toolbar: '#tableBar', title: '操作',minWidth:400}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Seat.search = function () {
        debugger;
        var queryData = {};
        var a = $('#seatName').val();
        queryData['seatName'] = $('#seatName').val();
        queryData['meetId'] = $('#meetId').val();
        // queryData['meetId'] = $('#meetId').val();
        // queryData['meetType'] = $('#meetType').val();
        // queryData['colNum'] = $('#colNum').val();
        // queryData['rowNum'] = $('#rowNum').val();
        // queryData['seatType'] = $('#seatType').val();

        table.reload(Seat.tableId, {
            where: queryData, page: {curr: 1}
        });
    };
    form.on('select(meetId)', function(data){
        Seat.search();
    });
    /**
     * 跳转到添加页面
     */
    Seat.jumpAddPage = function () {
        window.location.href = Feng.ctxPath + '/seat/add'
    };

    /**
    * 跳转到编辑页面
    * @param data 点击按钮时候的行数据
    */
    Seat.jumpEditPage = function (data) {
        window.location.href = Feng.ctxPath + '/seat/edit?seatId=' + data.seatId
    };

    /**
     * 跳转到座次页面
     */
    Seat.jumpSeatPage = function (data) {
        layer.open({
            title: '分配座位',
            type: 2,
            area: ['1100px','480px'],
            // resize:false,
            content: Feng.ctxPath + '/meetSeat?seatId=' + data.seatId
        });
    };

    /**
     * 导出excel按钮
     */
    Seat.exportExcel = function () {
        var checkRows = table.checkStatus(Seat.tableId);
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
    Seat.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/seat/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Seat.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("seatId", data.seatId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 重置座次
     * @param data
     */
    Seat.resetSeat = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/seat/reset", function (data) {
                Feng.success("重置成功!");
                table.reload(Seat.tableId);
            }, function (data) {
                Feng.error("重置失败!" + data.responseJSON.message + "!");
            });
            ajax.set("seatId", data.seatId);
            ajax.start();
        };
        Feng.confirm("是否要重置该会议的座次表?", operation);
    }

    /**
     * 自动分配
     * @param data
     */
    Seat.autoAssignSeat = function (data) {
        let operation = function () {
            let ajax = new $ax(Feng.ctxPath + "/meetSeat/autoAssign?seatId=" + data.seatId, function (data) {
                Feng.success("自动分配成功!");
                table.reload(Seat.tableId);
            }, function (data) {
                Feng.error("自动分配失败!" + data.responseJSON.message + "!");
            });
            ajax.set("seatId", data.seatId);
            ajax.start();
        };
        Feng.confirm("自动分配之前会重置该会议的座次表，是否分配?", operation);
    }

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Seat.tableId,
        url: Feng.ctxPath + '/seat/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: Seat.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Seat.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Seat.jumpAddPage();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Seat.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Seat.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Seat.jumpEditPage(data);
        } else if (layEvent === 'delete') {
            Seat.onDeleteItem(data);
        } else if (layEvent === 'seat') {
            Seat.jumpSeatPage(data);
        } else if (layEvent === 'reset') {
            Seat.resetSeat(data);
        } else if (layEvent === 'autoAssign') {
            Seat.autoAssignSeat(data);
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
                //console.log(meet)

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
