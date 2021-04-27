layui.use(['table', 'admin', 'form', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;

    /**
     * 会议注册成员表管理
     */
    var MeetMember = {
        tableId: "meetMemberTable"
    };
    getUserTranslationAjax();
    function getUserTranslationAjax() {
        $.ajax({
            type: 'post',
            url: Feng.ctxPath + "/translation/getUserTranslation",
            success: function (response) {
                layui.data('system', {
                    key: "lang",
                    value: response.data
                });
            }
        });
    }
    meetSelectOption();
    var langs = layui.data('system').lang;
    /**
     * 初始化表格的列
     */
    MeetMember.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'memberId', hide: true, title: '主键'},
            // {field: 'userId', sort: true, title: '参会人员ID'},
            // {field: 'thesisId', sort: true, title: '参会论文ID'},
            {field: 'meetName', sort: true,minWidth: 250, title: langs.FIELD_ConferenceName},
            {field: 'memberName', sort: true, title: langs.FIELD_NameParticipants},
            //{field: 'unitName', sort: true, title: '所在单位'},
            //{field: 'userPost', sort: true, title: '职务/职称'},
            //{field: 'direct', sort: true, title: '研究方向'},
            {field: 'thesisName', sort: true, title: langs.FIELD_PSTC},
            {field: 'meetTimeStatusStr', sort: true, title: langs.FIELD_ConferenceStatus},
            {field: 'meetStatusStr', sort: true, title: langs.FIELD_ApplicationStatus},
            // {field: 'forumName', sort: true, title: '自设论坛'},

            // {field: 'speak', sort: true, title: '是否申请发言'},
            // {field: 'judge', sort: true, title: '是否同意参加形势研判会'},
            // {field: 'ownForumid', sort: true, title: '自设论坛ID'},
            // {field: 'regTime', sort: true, title: '注册时间'},
            // {align: 'center', toolbar: '#tableBar', title: '操作',minWidth: 180},
            {align: 'center', minWidth: 300, title: langs.FIELD_Operate, templet: function(data){
                if (data.meetTimeStatusStr == "已结束" || data.meetPubStatus == "0"){
                    if (data.meetStatusStr == "已通过") {
                        return "<a class='layui-btn layui-btn-primary layui-btn-xs' title='" + langs.FIELD_ConferenceManual + "' target='_blank' lay-event='meetFile'>" + langs.FIELD_ConferenceManual + "</a><a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='fileDownload' title='" + langs.FIELD_DocumentsDownload + "'>" + langs.FIELD_DocumentsDownload + "</a>";
                    }else {
                        if (data.thesisName == null || data.thesisName == "" || data.thesisName == "无"){
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' target='_blank' lay-event='meetFile' title='" + langs.FIELD_ConferenceManual + "'>" + langs.FIELD_ConferenceManual + "</a><a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='fileDownload' title='" + langs.FIELD_DocumentsDownload + "'>" + langs.FIELD_DocumentsDownload + "</a>   ";
                        }else {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_PapaerDetails + "'>" + langs.FIELD_PapaerDetails + "</a><a class='layui-btn layui-btn-primary layui-btn-xs' target='_blank' lay-event='meetFile' title='" + langs.FIELD_ConferenceManual + "'>" + langs.FIELD_ConferenceManual + "</a><a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='fileDownload' title='" + langs.FIELD_DocumentsDownload + "'>" + langs.FIELD_DocumentsDownload + "</a>   ";
                        }

                    }
                } else {
                    if (data.thesisName == null || data.thesisName == "" || data.thesisName == "无"){
                        if (data.meetStatusStr == "评审中") {
                            return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                        } else if (data.meetStatusStr == "已取消") {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit' title='" + langs.FIELD_Revise + "'>" + langs.FIELD_Revise + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='delete' title='" + langs.FIELD_Delete + "'>" + langs.FIELD_Delete + "</a>";
                        } else if (data.meetStatusStr == "评审通过") {
                            return "<a id='payBtn' class='layui-btn layui-btn-normal layui-btn-xs' lay-event='pay' title='" + langs.FIELD_Pay + "'>" + langs.FIELD_Pay + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";/*<a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='weiXinPay' >微信缴费</a>*/
                        } else if (data.meetStatusStr == "已缴费") {
                            if (data.ownForumid != "" && data.ownForumid != null){
                                return "<a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='addBill' title='" + langs.FIELD_AFAI + "'>" + langs.FIELD_AFAI + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                            } else {
                                return "<a class='layui-btn layui-btn-xs' lay-event='forum' title='" + langs.FIELD_SelectConference + "'>" + langs.FIELD_SelectConference + "</a><a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='addBill' title='" + langs.FIELD_AFAI + "'>" + langs.FIELD_AFAI + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                            }
                        } else if (data.meetStatusStr == "已通过") {
                            return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                        } else if (data.meetStatusStr == "未通过") {
                            return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='delete' title='" + langs.FIELD_Delete + "'>" + langs.FIELD_Delete + "</a>";
                        } else if (data.meetStatusStr == "已申请开票") {
                            if (data.ownForumid != "" && data.ownForumid != null){
                                return "<a class='layui-btn layui-btn-xs' lay-event='forum' title='" + langs.FIELD_SeeConference + "'>" + langs.FIELD_SeeConference + "</a><a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='editBill' title='" + langs.FIELD_SIOI + "'>" + langs.FIELD_SIOI + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                            } else {
                                return "<a class='layui-btn layui-btn-xs' lay-event='forum' title='" + langs.FIELD_SelectConference + "'>" + langs.FIELD_SelectConference + "</a><a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='editBill' title='" + langs.FIELD_SIOI + "'>" + langs.FIELD_SIOI + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                            }
                        }else {
                            return "";
                        }
                    } else {
                        if (data.meetStatusStr == "评审中") {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_PapaerDetails + "'>" + langs.FIELD_PapaerDetails + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                        } else if (data.meetStatusStr == "已取消") {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='edit' title='" + langs.FIELD_Revise + "'>" + langs.FIELD_Revise + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='delete' title='" + langs.FIELD_Delete + "'>" + langs.FIELD_Delete + "</a>";
                        } else if (data.meetStatusStr == "评审通过") {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_PapaerDetails + "'>" + langs.FIELD_PapaerDetails + "</a><a id='payBtn' class='layui-btn layui-btn-normal layui-btn-xs' lay-event='pay' title='" + langs.FIELD_Pay + "'>" + langs.FIELD_Pay + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";/*<a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='weiXinPay' >微信缴费</a>*/
                        } else if (data.meetStatusStr == "已缴费") {
                            if (data.ownForumid != "" && data.ownForumid != null){
                                return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_PapaerDetails + "'>" + langs.FIELD_PapaerDetails + "</a><a class='layui-btn layui-btn-xs' lay-event='forum' title='" + langs.FIELD_SeeConference + "'>" + langs.FIELD_SeeConference + "</a><a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='addBill' title='" + langs.FIELD_AFAI + "'>" + langs.FIELD_AFAI + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                            } else {
                                return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_PapaerDetails + "'>" + langs.FIELD_PapaerDetails + "</a><a class='layui-btn layui-btn-xs' lay-event='forum' title='" + langs.FIELD_SelectConference + "'>" + langs.FIELD_SelectConference + "</a><a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='addBill' title='" + langs.FIELD_AFAI + "'>" + langs.FIELD_AFAI + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                            }
                        } else if (data.meetStatusStr == "已通过") {
                            return "<a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                        } else if (data.meetStatusStr == "未通过") {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_PapaerDetails + "'>" + langs.FIELD_PapaerDetails + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='delete' title='" + langs.FIELD_Delete + "'>" + langs.FIELD_Delete + "</a>";
                        } else if (data.meetStatusStr == "已申请开票") {
                            if (data.ownForumid != "" && data.ownForumid != null){
                                return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_PapaerDetails + "'>" + langs.FIELD_PapaerDetails + "</a><a class='layui-btn layui-btn-xs' lay-event='forum' title='" + langs.FIELD_SeeConference + "'>" + langs.FIELD_SeeConference + "</a><a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='editBill' title='" + langs.FIELD_SIOI + "'>" + langs.FIELD_SIOI + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                            } else {
                                return "<a class='layui-btn layui-btn-primary layui-btn-xs' lay-event='detail' title='" + langs.FIELD_PapaerDetails + "'>" + langs.FIELD_PapaerDetails + "</a><a class='layui-btn layui-btn-xs' lay-event='forum' title='" + langs.FIELD_SelectConference + "'>" + langs.FIELD_SelectConference + "</a><a class='layui-btn layui-btn-normal layui-btn-xs' lay-event='editBill' title='" + langs.FIELD_SIOI + "'>" + langs.FIELD_SIOI + "</a><a class='layui-btn layui-btn-danger layui-btn-xs' lay-event='cancelMeet' title='" + langs.FIELD_CancelRegistration + "'>" + langs.FIELD_CancelRegistration + "</a>";
                            }

                        }else {
                            return "<a class='layui-btn layui-btn-primary layui-btn-xs' title='" + langs.FIELD_PapaerDetails + "' lay-event='detail'>" + langs.FIELD_PapaerDetails + "</a>";
                        }
                    }
                }


                }}
        ]];
    };


    // $(function () {
    //     var payObj = document.getElementById("payBtn");
    //     if(payObj != null){
    //         payObj.href = "http://www.baidu.com";
    //     }
    // })

    /**
     * 点击查询按钮
     */
    MeetMember.search = function () {
        var queryData = {};
        //queryData['userName'] = $('#userName').val();
        queryData['meetId'] = $('#meetId').val();
        table.reload(MeetMember.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    //选择论坛时立刻刷新
    form.on('select(meetId)', function(data){
        MeetMember.search();
    });

    /**
     * 弹出添加对话框
     */
    MeetMember.openAddDlg = function () {
        func.open({
            title: '添加会议注册成员表',
            content: Feng.ctxPath + '/meetMember/add',
            tableId: MeetMember.tableId
        });
    };

     /**
      * 点击编辑
      * @param data 点击按钮时候的行数据
      */
      MeetMember.openEditDlg = function (data) {
          window.location.href = Feng.ctxPath + '/meetMember/edit?memberId=' + data.memberId ;
          // func.open({
          //     title: '修改会议注册信息',
          //     content: Feng.ctxPath + '/meetMember/edit?memberId=' + data.memberId + '&thesisId=' + data.thesisId,
          //     tableId: MeetMember.tableId
          // });
      };

    /**
     * 仅查看详情
     * @param data 点击按钮时候的行数据
     */
    MeetMember.onDisableItem = function (data) {
        window.location.href = Feng.ctxPath + '/meetMember/disable?memberId=' + data.memberId ;
        // func.open({
        //     title: '查看详情',
        //     content: Feng.ctxPath + '/meetMember/disable?memberId=' + data.memberId,
        //     tableId: MeetMember.tableId
        // });
    };

    /**
     * 选择论坛
     * @param data
     */
    MeetMember.onForumItem = function (data) {
        layer.open({
            title: '选择论坛',
            type: 2,
            area: ['620px','600px'],
            content: Feng.ctxPath + '/meetMember/forum?memberId=' + data.memberId,
            tableId: MeetMember.tableId
        });
    };

    /**
     * 申请开票
     * @param data
     */
    MeetMember.onAddBill = function (data) {
        layer.open({
            title: '申请开票',
            type: 2,
            area: ['620px','600px'],
            content: Feng.ctxPath + '/bill/add?memberId=' + data.memberId + "&meetId=" + data.meetId,
            tableId: MeetMember.tableId
        });
    };
    MeetMember.onEditBill = function (data) {
        layer.open({
            title: '查看开票信息',
            type: 2,
            area: ['620px','600px'],
            content: Feng.ctxPath + '/bill/edit?meetMemberId=' + data.memberId,
            tableId: MeetMember.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    MeetMember.exportExcel = function () {
        var checkRows = table.checkStatus(MeetMember.tableId);
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
    MeetMember.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMember/delete?thesisId="+data.thesisId, function (data) {
                Feng.success("删除成功!");
                table.reload(MeetMember.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("memberId", data.memberId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 取消申请
     * @param data
     */
    MeetMember.onCancelItem = function (data){
        if (data.meetStatusStr == '已取消'){
            alert("已经是取消状态！")
            return false;
        }
        // debugger;
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMember/cancelApply", function (data) {
                Feng.success("取消申请成功!");
                table.reload(MeetMember.tableId);
                // window.location.href=window.location.href;
            }, function (data) {
                Feng.error("取消申请失败!" + data.responseJSON.message + "!");
            });
            ajax.set("memberId", data.memberId);
            ajax.start();
        };
        Feng.confirm("是否取消会议申请?", operation);
    };
    MeetMember.onCancelMeet = function (data){
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/meetMember/cancelMeet", function (data) {
                Feng.success("取消成功!");
                table.reload(MeetMember.tableId);
                // window.location.href=window.location.href;
            }, function (data) {
                Feng.error("取消失败!" + data.responseJSON.message + "!");
            });
            ajax.set("memberId", data.memberId);
            ajax.set("meetStatus", data.meetStatus);
            ajax.start();
        };
        Feng.confirm("是否取消参会?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + MeetMember.tableId,
        url: Feng.ctxPath + '/meetMember/wraplist',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: MeetMember.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        MeetMember.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        MeetMember.openAddDlg();
    });

    // 添加按钮点击事件
    $('#btnCreate').click(function () {
        window.location.href = Feng.ctxPath + "/meetMember/add";
    });

    // 导出excel
    $('#btnExp').click(function () {
        MeetMember.exportExcel();
    });

    /**
     * 支付
     * @param data
     */
    MeetMember.onPayItem = function (data) {
        let memberId = data.memberId;
        layer.open({
            title: '扫码支付',
            type: 2,
            area: ['460px','450px'],
            content: Feng.ctxPath + '/alipay/qrcode?memberId=' + memberId,
            tableId: MeetMember.tableId
        });
    };
    /*MeetMember.onWeiXinPayItem = function (data) {
        let memberId = data.memberId;
        layer.open({
            title: '扫码支付',
            type: 2,
            area: ['400px','450px'],
            content: Feng.ctxPath + '/weiXin/toWeiXinPay?memberId=' + memberId,
            tableId: MeetMember.tableId
        });
    };*/

    MeetMember.onMeetFile = function (data) {
        window.open(Feng.ctxPath + "/meet/meetFile?meetId=" + data.meetId);
    };
    MeetMember.onFileDownload = function (data) {
        layer.open({
            title: '材料下载',
            type: 2,
            area: ['620px', '600px'],
            content: Feng.ctxPath + '/meetMaterial/toFileDownload?meetId=' + data.meetId ,
        });
    }

    // 工具条点击事件
    table.on('tool(' + MeetMember.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            MeetMember.openEditDlg(data);
        } else if (layEvent === 'delete') {
            MeetMember.onDeleteItem(data);
        } else if (layEvent === 'detail') {
            MeetMember.onDisableItem(data);
        } else if (layEvent === 'cancel') {
            MeetMember.onCancelItem(data);
        } else if (layEvent === 'cancelMeet') {
            MeetMember.onCancelMeet(data);
        } else if (layEvent === 'forum') {
            MeetMember.onForumItem(data);
        } else if (layEvent === 'pay') {
            MeetMember.onPayItem(data);
        } else if (layEvent === 'addBill') {
            MeetMember.onAddBill(data);
        } else if (layEvent === 'editBill') {
            MeetMember.onEditBill(data);
        } else if (layEvent === 'meetFile') {
            MeetMember.onMeetFile(data);
        }else if (layEvent === 'fileDownload') {
            MeetMember.onFileDownload(data);
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
                $('#meetIdJB').empty();
                $('#meetIdJB').append("<option value='0'>请选择会议</option>");
                $('#meetIdJB').append(options);
                form.render('select');
            }
        })
    }
});
