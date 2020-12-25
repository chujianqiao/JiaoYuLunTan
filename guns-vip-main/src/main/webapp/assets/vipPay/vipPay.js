layui.use(['table', 'admin','form', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 会员缴费表管理
     */
    var VipPay = {
        tableId: "vipPayTable"
    };

    meetSelectOption();

    /**
     * 初始化表格的列
     */
    VipPay.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'payId', hide: true, title: '缴费记录ID'},
            {field: 'payUserName', sort: true, title: '缴费用户'},
            {field: 'orderNum', sort: true, title: '订单号'},
            {field: 'payMoney', sort: true, title: '缴费金额（元）'},
            // {field: 'payType', sort: true, title: '缴费方式'},
            {field: 'payTypeStr', sort: true, title: '缴费方式'},
            //{field: 'tranNum', sort: true, title: '平台交易单号'},
            {field: 'payTime', sort: true, title: '缴费时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    VipPay.search = function () {
        var queryData = {};

        queryData['userName'] = $('#userName').val();
        $('#userNameExp').val($('#userName').val());
        queryData['meetId'] = $("#meetId").val();
        table.reload(VipPay.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    form.on('select(meetId)', function(data){
        VipPay.search();
    });

    /**
     * 弹出添加对话框
     */
    VipPay.openAddDlg = function () {
        func.open({
            title: '添加会员缴费表',
            content: Feng.ctxPath + '/vipPay/add',
            tableId: VipPay.tableId
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      VipPay.openEditDlg = function (data) {
          /*func.open({
              title: '修改会员缴费表',
              content: Feng.ctxPath + '/vipPay/edit?payId=' + data.payId,
              tableId: VipPay.tableId
          });*/
          window.location.href = Feng.ctxPath + '/vipPay/edit?payId=' + data.payId;
      };


    /**
     * 导出excel按钮
     */
    VipPay.exportExcel = function () {
        var checkRows = table.checkStatus(VipPay.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 导出excel按钮
     */
    VipPay.exportExcelAll = function () {
        //使用ajax请求获取所有数据
        $.ajax({
            url: Feng.ctxPath + '/vipPay/wrapList',
            type: 'post',
            data: {
                "userName":$('#userNameExp').val(),
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
        title: '缴费管理全部数据',
        cols: [[ //表头
            {
                field: 'payUserName',
                title: '缴费用户',
            }, {
                field: 'orderNum',
                title: '订单号',
            }, {
                field: 'payMoney',
                title: '缴费金额（元）',
            }, {
                field: 'payTypeStr',
                title: '缴费方式',
            }, {
                field: 'payTime',
                title: '缴费时间',
            }
        ]]
    });

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    VipPay.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/vipPay/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(VipPay.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("payId", data.payId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + VipPay.tableId,
        url: Feng.ctxPath + '/vipPay/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: VipPay.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        VipPay.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {

    VipPay.openAddDlg();

    });

    // 导出excel
    $('#btnExp').click(function () {
        VipPay.exportExcel();
    });
    // 导出excel
    $('#btnExpAll').click(function () {
        VipPay.exportExcelAll();
    });

    // 工具条点击事件
    table.on('tool(' + VipPay.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            VipPay.openEditDlg(data);
        } else if (layEvent === 'delete') {
            VipPay.onDeleteItem(data);
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
