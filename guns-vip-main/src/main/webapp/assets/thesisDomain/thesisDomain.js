layui.use(['table', 'admin', 'ax', 'ztree', 'func', 'tree'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var $ZTree = layui.ztree;
    var func = layui.func;
    var tree = layui.tree;

    /**
     * 论文领域表管理
     */
    var ThesisDomain = {
        tableId: "thesisDomainTable",
        condition: {
            domainId: "",
            domainName: "",
        }
    };

    /**
     * 初始化表格的列
     */
    ThesisDomain.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'domainId', hide: true, title: '领域ID'},
            //{field: 'pid', sort: true, title: '父领域ID'},
            //{field: 'pids', sort: true, title: '父级ids'},
            {field: 'domainName', sort: true, title: '领域名称'},
            {field: 'belongMajor', sort: true, title: '所属专家'},
            {field: 'description', sort: true, title: '备注'},
            //{field: 'version', sort: true, title: '版本'},
            //{field: 'sort', sort: true, title: '排序'},
            {field: 'createTime', sort: true, title: '创建时间'},
            //{field: 'updateTime', sort: true, title: '修改时间'},
            //{field: 'createUser', sort: true, title: '创建人'},
            //{field: 'updateUser', sort: true, title: '修改人'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ThesisDomain.search = function () {
        var queryData = {};
        queryData['domainName'] = $("#name").val();
        queryData['domainId'] = ThesisDomain.condition.domainId;
        table.reload(ThesisDomain.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 选择领域时
     */
    ThesisDomain.onClickDomain = function (obj) {
        ThesisDomain.condition.domainId = obj.data.id;
        ThesisDomain.search();
    };

    /**
     * 弹出添加对话框
     */
    ThesisDomain.openAddDlg = function () {
        func.open({
            title: '添加论文领域',
            content: Feng.ctxPath + '/thesisDomain/add',
            tableId: ThesisDomain.tableId,
            endCallback: function () {
                ThesisDomain.loadDomainTree();
            }
        });
    };

     /**
      * 点击编辑
      *
      * @param data 点击按钮时候的行数据
      */
      ThesisDomain.openEditDlg = function (data) {
          func.open({
              title: '修改论文领域表',
              content: Feng.ctxPath + '/thesisDomain/edit?domainId=' + data.domainId,
              tableId: ThesisDomain.tableId,
              endCallback: function () {
                  //ThesisDomain.loadDomainTree();
              }
          });
      };


    /**
     * 导出excel按钮
     */
    ThesisDomain.exportExcel = function () {
        var checkRows = table.checkStatus(ThesisDomain.tableId);
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
    ThesisDomain.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/thesisDomain/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ThesisDomain.tableId);

                //左侧树加载
                ThesisDomain.loadDomainTree();

            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("domainId", data.domainId);
            ajax.start();
        };
        Feng.confirm("是否删除领域"+ data.domainName +"?", operation);
    };

    /**
     * 左侧树加载
     */
    /*ThesisDomain.loadDomainTree = function () {
        var ajax = new $ax(Feng.ctxPath + "/thesisDomain/layuiTree", function (data) {
            tree.render({
                elem: '#domainTree',
                data: data,
                click: ThesisDomain.onClickDomain,
                onlyIconControl: true
            });
        }, function (data) {
        });
        ajax.start();
    };*/

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ThesisDomain.tableId,
        url: Feng.ctxPath + '/thesisDomain/wrapList',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ThesisDomain.initColumn()
    });

    //初始化左侧部门树
    //ThesisDomain.loadDomainTree();

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ThesisDomain.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ThesisDomain.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ThesisDomain.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ThesisDomain.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ThesisDomain.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ThesisDomain.onDeleteItem(data);
        }
    });
});

$(function () {
    var panehHidden = false;
    if ($(this).width() < 769) {
        panehHidden = true;
    }
    $('#myContiner').layout({initClosed: panehHidden, west__size: 260});
});