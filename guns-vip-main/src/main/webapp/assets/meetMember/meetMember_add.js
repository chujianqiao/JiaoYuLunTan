/**
 * 添加或者修改页面
 */
var MeetMemberInfoDlg = {
    data: {
        memberId: "",
        userId: "",
        thesisId: "",
        speak: "",
        judge: "",
        ownForumid: "",
        regTime: "",
        belongDomain: "",
        pName: ""
    }
};

layui.use(['layer', 'form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var upload = layui.upload;
    var func = layui.func;
    var layer = layui.layer;

    //定义数组，存储省份信息
    var province = ["","北京", "上海", "天津", "重庆", "浙江", "江苏", "广东", "福建", "湖南", "湖北", "辽宁",
        "吉林", "黑龙江", "河北", "河南", "山东", "陕西", "甘肃", "新疆", "青海", "山西", "四川",
        "贵州", "安徽", "江西", "云南", "内蒙古", "西藏", "广西", "宁夏", "海南", "香港", "澳门", "台湾"];
    //定义数组,存储城市信息
    var beijing = ["东城区", "西城区", "海淀区", "朝阳区", "丰台区", "石景山区", "通州区", "顺义区", "房山区", "大兴区", "昌平区", "怀柔区", "平谷区", "门头沟区", "延庆县", "密云县"];
    var shanghai = ["浦东新区", "徐汇区", "长宁区", "普陀区", "闸北区", "虹口区", "杨浦区", "黄浦区", "卢湾区", "静安区", "宝山区", "闵行区", "嘉定区", "金山区", "松江区", "青浦区", "南汇区", "奉贤区", "崇明县"];
    var tianjing = ["河东", "南开", "河西", "河北", "和平", "红桥", "东丽", "津南", "西青", "北辰", "塘沽", "汉沽", "大港", "蓟县", "宝坻", "宁河", "静海", "武清"];
    var chongqing = ["渝中区", "大渡口区", "江北区", "沙坪坝区", "九龙坡区", "南岸区", "北碚区", "万盛区", "双桥区", "渝北区", "巴南区", "万州区", "涪陵区", "黔江区", "长寿区", "江津区", "合川区", "永川区", "南川区"];
    var jiangsu = ["南京", "无锡", "常州", "徐州", "苏州", "南通", "连云港", "淮安", "扬州", "盐城", "镇江", "泰州", "宿迁"];
    var zhejiang = ["杭州", "宁波", "温州", "嘉兴", "湖州", "绍兴", "金华", "衢州", "舟山", "台州", "利水"];
    var guangdong = ["广州", "韶关", "深圳", "珠海", "汕头", "佛山", "江门", "湛江", "茂名", "肇庆", "惠州", "梅州", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳"];
    var fujiang = ["福州", "厦门", "莆田", "三明", "泉州", "漳州", "南平", "龙岩", "宁德"];
    var hunan = ["长沙", "株洲", "湘潭", "衡阳", "邵阳", "岳阳", "常德", "张家界", "益阳", "郴州", "永州", "怀化", "娄底", "湘西土家苗族自治区"];
    var hubei = ["武汉", "襄阳", "黄石", "十堰", "宜昌", "鄂州", "荆门", "孝感", "荆州", "黄冈", "咸宁", "随州", "恩施土家族苗族自治州"];
    var liaoning = ["沈阳", "大连", "鞍山", "抚顺", "本溪", "丹东", "锦州", "营口", "阜新", "辽阳", "盘锦", "铁岭", "朝阳", "葫芦岛"];
    var jilin = ["长春", "吉林", "四平", "辽源", "通化", "白山", "松原", "白城", "延边朝鲜族自治区"];
    var heilongjiang = ["哈尔滨", "齐齐哈尔", "鸡西", "牡丹江", "佳木斯", "大庆", "伊春", "黑河", "大兴安岭"];
    var hebei = ["石家庄", "保定", "唐山", "邯郸", "承德", "廊坊", "衡水", "秦皇岛", "张家口"];
    var henan = ["郑州", "洛阳", "商丘", "安阳", "南阳", "开封", "平顶山", "焦作", "新乡", "鹤壁", "许昌", "漯河", "三门峡", "信阳", "周口", "驻马店", "济源"];
    var shandong = ["济南", "青岛", "菏泽", "淄博", "枣庄", "东营", "烟台", "潍坊", "济宁", "泰安", "威海", "日照", "滨州", "德州", "聊城", "临沂"];
    var shangxi = ["西安", "宝鸡", "咸阳", "渭南", "铜川", "延安", "榆林", "汉中", "安康", "商洛"];
    var gansu = ["兰州", "嘉峪关", "金昌", "金川", "白银", "天水", "武威", "张掖", "酒泉", "平凉", "庆阳", "定西", "陇南", "临夏", "合作"];
    var qinghai = ["西宁", "海东地区", "海北藏族自治州", "黄南藏族自治州", "海南藏族自治州", "果洛藏族自治州", "玉树藏族自治州", "海西蒙古族藏族自治州"];
    var xinjiang = ["乌鲁木齐", "奎屯", "石河子", "昌吉", "吐鲁番", "库尔勒", "阿克苏", "喀什", "伊宁", "克拉玛依", "塔城", "哈密", "和田", "阿勒泰", "阿图什", "博乐"];
    var shanxi = ["太原", "大同", "阳泉", "长治", "晋城", "朔州", "晋中", "运城", "忻州", "临汾", "吕梁"];
    var sichuan = ["成都", "自贡", "攀枝花", "泸州", "德阳", "绵阳", "广元", "遂宁", "内江", "乐山", "南充", "眉山", "宜宾", "广安", "达州", "雅安", "巴中", "资阳", "阿坝藏族羌族自治州", "甘孜藏族自治州", "凉山彝族自治州"];
    var guizhou = ["贵阳", "六盘水", "遵义", "安顺", "黔南布依族苗族自治州", "黔西南布依族苗族自治州", "黔东南苗族侗族自治州", "铜仁", "毕节"];
    var anhui = ["合肥", "芜湖", "安庆", "马鞍山", "阜阳", "六安", "滁州", "宿州", "蚌埠", "巢湖", "淮南", "宣城", "亳州", "淮北", "铜陵", "黄山", "池州"];
    var jiangxi = ["南昌", "九江", "景德镇", "萍乡", "新余", "鹰潭", "赣州", "宜春", "上饶", "吉安", "抚州"];
    var yunnan = ["昆明", "曲靖", "玉溪", "保山", "昭通", "丽江", "普洱", "临沧", "楚雄彝族自治州", "大理白族自治州", "红河哈尼族彝族自治州", "文山壮族苗族自治州", "西双版纳傣族自治州", "德宏傣族景颇族自治州", "怒江傈僳族自治州", "迪庆藏族自治州"];
    var neimenggu = ["呼和浩特", "包头", "乌海", "赤峰", "通辽", "鄂尔多斯", "呼伦贝尔", "巴彦淖尔", "乌兰察布"];
    var guangxi = ["南宁", "柳州", "桂林", "梧州", "北海", "防城港", "钦州", "贵港", "玉林", "百色", "贺州", "河池", "崇左"];
    var xizang = ["拉萨", "昌都地区", "林芝地区", "山南地区", "日喀则地区", "那曲地区", "阿里地区"];
    var ningxia = ["银川", "石嘴山", "吴忠", "固原", "中卫"];
    var hainan = ["海口", "三亚"];
    var xianggang = ["中西区", "湾仔区", "东区", "南区", "九龙城区", "油尖旺区", "观塘区", "黄大仙区", "深水埗区", "北区", "大埔区", "沙田区", "西贡区", "元朗区", "屯门区", "荃湾区", "葵青区", "离岛区"];
    var taiwan = ["台北", "高雄", "基隆", "台中", "台南", "新竹", "嘉义"];
    var aomeng = ["澳门半岛", "氹仔岛", "路环岛"];
    //页面加载方法
    $(function () {
        layui.use('form', function () {
            form = layui.form;
            //各种基于事件的操作，下面会有进一步介绍
            //设置省份数据
            setProvince();
            //监听下拉事件
            form.on('select(Province)', function (data) {
                setCity(data.value);
            });
        });

    });
    //设置省份数据
    function setProvince() {
        //给省份下拉列表赋值
        var $sel = $("#selProvince");
        $sel.html("");
        //获取对应省份城市
        for (var i = 0, len = province.length; i < len; i++) {
            modelVal = province[i];
            var option = $("<option value='" + province[i] + "'>" + province[i] + "</option>");
            //添加到 select 元素中
            $sel.append(option);
        }
        form.render('select');
        setCity($($sel.get(0)).val());
    }
    form.on('select(country)', function (data) {
        if (data.value.indexOf("中国") > -1){
            $("#province").css("display","block");
            $("#city").css("display","block");
        }else {
            setProvince();
            $("#province").css("display","none");
            $("#city").css("display","none");
        }
    });
    //根据选中的省份获取对应的城市
    function setCity(provinec) {
        var $city = $("#selCity");
        var proCity, option, modelVal;
        //通过省份名称，获取省份对应城市的数组名
        switch (provinec) {
            case "北京":
                proCity = beijing;
                break;
            case "上海":
                proCity = shanghai;
                break;
            case "天津":
                proCity = tianjing;
                break;
            case "重庆":
                proCity = chongqing;
                break;
            case "浙江":
                proCity = zhejiang;
                break;
            case "江苏":
                proCity = jiangsu;
                break;
            case "广东":
                proCity = guangdong;
                break;
            case "福建":
                proCity = fujiang;
                break;
            case "湖南":
                proCity = hunan;
                break;
            case "湖北":
                proCity = hubei;
                break;
            case "辽宁":
                proCity = liaoning;
                break;
            case "吉林":
                proCity = jilin;
                break;
            case "黑龙江":
                proCity = heilongjiang;
                break;
            case "河北":
                proCity = hebei;
                break;
            case "河南":
                proCity = henan;
                break;
            case "山东":
                proCity = shandong;
                break;
            case "陕西":
                proCity = shangxi;
                break;
            case "甘肃":
                proCity = gansu;
                break;
            case "新疆":
                proCity = xinjiang;
                break;
            case "青海":
                proCity = qinghai;
                break;
            case "山西":
                proCity = shanxi;
                break;
            case "四川":
                proCity = sichuan;
                break;
            case "贵州":
                proCity = guizhou;
                break;
            case "安徽":
                proCity = anhui;
                break;
            case "江西":
                proCity = jiangxi;
                break;
            case "云南":
                proCity = yunnan;
                break;
            case "内蒙古":
                proCity = neimenggu;
                break;
            case "西藏":
                proCity = xizang;
                break;
            case "广西":
                proCity = guangxi;
                break;
            case "宁夏":
                proCity = ningxia;
                break;
            case "海南":
                proCity = hainan;
                break;
            case "香港":
                proCity = xianggang;
                break;
            case "澳门":
                proCity = aomeng;
                break;
            case "台湾":
                proCity = taiwan;
                break;
        }
        //先清空之前绑定的值
        $city.empty();
        //设置对应省份的城市
        if (proCity != undefined){
            for (var i = 0, len = proCity.length; i < len; i++) {
                modelVal = proCity[i];
                option = "<option value='" + modelVal + "'>" + modelVal + "</option>";
                //添加
                $city.append(option);
            }
        }

        form.render('select');
    }


    //获取详情信息，填充表单
    let userId = $("#userId").val()
    var ajax = new $ax(Feng.ctxPath + "/mgr/getUserInfo?userId=" + userId);
    var result = ajax.start();
    setCity(result.data.province);
    form.val('meetRegForm', result.data);
    //判断大小会
    let ajaxPubMeet = new $ax(Feng.ctxPath + "/meet/detailPub");
    let pubMeet = ajaxPubMeet.start().data.detail;
    var size = pubMeet.size;
    if(size == 'small' || size == "small"){
        //小会，移除所有论文的元素
        $(".thesisItem").remove();
        $("#isSubmit").remove();
        $("#btnSubmit").css("display","")
        $("#btnNext").css("display","none")

    }else if(size == 'big' || size == "big") {
        //是否必须投稿
        let isSub = pubMeet.mustSub;
        if(isSub == 1 || isSub == '1'){
            $("#isSubmit").remove();
        }
    }

    if (result.data.country.indexOf("中国") > -1){
        $("#province").css("display","block");
        $("#city").css("display","block");
    } else {
        $("#province").css("display","none");
        $("#city").css("display","none");
    }

    var userTitle = $("#userTitle").val();
    if(userTitle != '教授'){
        $('#judge1').remove();
        $('#judge2').remove();
    }

    $(function () {
        forumSelectOption();
    })

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var flag = 0;
        $("input:checkbox[name = ifAgreeMeet]:checked").each(function(i){
            flag = 1;
        })
        if (flag == 1){
            $("#divSubmit").css("pointer-events","none");
            var ajax = new $ax(Feng.ctxPath + "/thesis/addItem", function (data) {
                Feng.success("提交成功！");
                window.location.href = Feng.ctxPath + '/meetMember';
            }, function (data) {
                Feng.error("提交失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
        } else {
            Feng.error("请先阅读并同意《论坛章程》！");
        }

        return false;
    });
    //表单提交事件
    form.on('submit(btnNext)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/thesis/addUserItem", function (data) {
            Feng.success("提交成功！");
            window.location.href = Feng.ctxPath + "/thesis/toAddItem?ownForumid=" + $("#ownForumid").val() + "&ifMeet=" + $("input[name='ifMeet']:checked").val();;
        }, function (data) {
            Feng.error("提交失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });

    //上传PDF文件
    upload.render({
        elem: '#fileBtn'
        , url: Feng.ctxPath + '/holdForum/uploadThesisPdf'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                var fileName = file.name;
                var fileType = fileName.substr(fileName.lastIndexOf("."));
                if(fileType.compare(".pdf")){
                    $("#fileNameTip").val(file.name);
                    $("#fileNameTip").html(file.name);
                }

            });
        }
        , done: function (res) {
            var type = res.data.type;
            var sizeInfo = res.data.sizeInfo;
            if(type != ".pdf"){
                Feng.error("上传失败，文件格式不匹配");
            }else {
                if (sizeInfo != "yes"){
                    $("#fileNameTip").val("");
                    $("#fileNameTip").html("");
                    Feng.error("上传失败，文件大小超过限制，请上传"+sizeInfo+"以内的文件。");
                }else {
                    $("#fileInputHidden").val(res.data.fileId);
                    $("#thesisPath").val(res.data.path);
                    $("#fileName").val($("#fileNameTip").val());
                    Feng.success(res.message);
                }

            }
        }
        , error: function () {
            Feng.error("上传文件失败！");
        }
    });

    //上传Word文件
    upload.render({
        elem: '#wordBtn'
        , url: Feng.ctxPath + '/thesis/uploadWord'
        , accept: 'file'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                var fileName = file.name;
                var fileType = fileName.substr(fileName.lastIndexOf("."));
                if(fileType.compare(".doc") || fileType.compare(".docx")){
                    $("#wordNameTip").val(file.name);
                    $("#wordNameTip").html(file.name);
                }
            });
        }
        , done: function (res) {
            var status = res.data.status;
            if(status == "格式问题" || status === "格式问题"){
                Feng.error(res.message);
            }else if (status == "大小问题" || status === "大小问题"){
                $("#wordNameTip").val("");
                $("#wordNameTip").html("");
                Feng.error(res.message);
            } else {
                $("#wordInputHidden").val(res.data.fileId);
                $("#wordPath").val(res.data.path);
                $("#wordName").val($("#wordNameTip").val());
                Feng.success(res.message);
            }
        }
        , error: function (res) {
            Feng.error("上传文件失败");
        }
    });

    upload.render({
        elem: '#imgHead'
        , url: Feng.ctxPath + '/system/upload'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $('#avatarPreview').attr('src', result);
            });
        }
        , done: function (res) {
            var ajax = new $ax(Feng.ctxPath + "/system/updateAvatar", function (data) {
                Feng.success(res.message);
            }, function (data) {
                Feng.error("修改失败!" + data.responseJSON.message + "!");
            });
            ajax.set("fileId", res.data.fileId);
            ajax.start();
        }
        , error: function () {
            Feng.error("上传头像失败！");
        }
    });

    /**
     * 构建论坛下拉框候选值
     * @param ownForumid
     */
    function forumSelectOption(){
        $.ajax({
            type:'post',
            url:Feng.ctxPath + "/forum/wrapList" ,
            success:function(response){
                var data=response.data;
                var forums = [];
                forums = data;

                var options;
                for (i = 0 ;i < forums.length ;i++){
                    var forum = data[i];
                    options += '<option value="'+ forum.forumId+ '" >'+ forum.forumName +'</option>';
                }
                $('#ownForumid').empty();
                $('#ownForumid').append("<option value=''>请选择</option>");
                $('#ownForumid').append(options);
                form.render('select');
            }
        })
    }

    //取消
    $('#btnCancel').click(function () {
        //关闭标签页
        window.opener=null;
        window.open('','_self');
        window.close();
    });

    var thesisItems=$(".thesisItem");
    form.on('radio(subRadio)', function(data){
        if(data.value == 0){
            $(".thesisItem").remove();
            $("#btnSubmit").css("display","")
            $("#btnNext").css("display","none")
        }else if(data.value == 1){
            $("#thesisDiv").append(thesisItems);
            $("#btnSubmit").css("display","none")
            $("#btnNext").css("display","")
            //append后需要重新绑定upload
            layui.use(['form','upload'],function(){
                upload.render({
                    elem: '#fileBtn'
                    , url: Feng.ctxPath + '/holdForum/upload'
                    , accept: 'file'
                    , before: function (obj) {
                        obj.preview(function (index, file, result) {
                            var fileName = file.name;
                            var fileType = fileName.substr(fileName.lastIndexOf("."));
                            if(fileType.compare(".pdf")){
                                $("#fileNameTip").val(file.name);
                                $("#fileNameTip").html(file.name);
                            }

                        });
                    }
                    , done: function (res) {
                        var type = res.data.type;
                        var sizeInfo = res.data.sizeInfo;
                        if(type != ".pdf"){
                            Feng.error("上传失败，文件格式不匹配");
                        }else {
                            if (sizeInfo != "yes"){
                                Feng.error("上传失败，文件大小超过限制，请上传"+sizeInfo+"以内的文件。");
                            }else {
                                $("#fileInputHidden").val(res.data.fileId);
                                $("#thesisPath").val(res.data.path);
                                $("#fileName").val($("#fileNameTip").val());
                                Feng.success(res.message);
                            }

                        }
                    }
                    , error: function () {
                        Feng.error("上传文件失败！");
                    }
                });

                upload.render({
                    elem: '#wordBtn'
                    , url: Feng.ctxPath + '/thesis/uploadWord'
                    , accept: 'file'
                    , before: function (obj) {
                        obj.preview(function (index, file, result) {
                            var fileName = file.name;
                            var fileType = fileName.substr(fileName.lastIndexOf("."));
                            if(fileType.compare(".doc") || fileType.compare(".docx")){
                                $("#wordNameTip").val(file.name);
                                $("#wordNameTip").html(file.name);
                            }
                        });
                    }
                    , done: function (res) {
                        var status = res.data.status;
                        if(status == "格式问题" || status === "格式问题"){
                            Feng.error(res.message);
                        }else if (status == "大小问题" || status === "大小问题"){
                            Feng.error(res.message);
                        } else {
                            $("#wordInputHidden").val(res.data.fileId);
                            $("#wordPath").val(res.data.path);
                            $("#wordName").val($("#wordNameTip").val());
                            Feng.success(res.message);
                        }
                    }
                    , error: function (res) {
                        Feng.error("上传文件失败");
                    }
                });
            });

        }
    });

    /**
     * remove掉元素之后再append， $("#pName").click(function (){}失效，改用该方法
     */
    $(document).on('click',"#pName",function(){
        var formName = encodeURIComponent("parent.MeetMemberInfoDlg.data.pName");
        var formId = encodeURIComponent("parent.MeetMemberInfoDlg.data.belongDomain");
        var treeUrl = encodeURIComponent("/thesisDomain/tree");
        layer.open({
            type: 2,
            title: '论文领域',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#belongDomain").val(MeetMemberInfoDlg.data.belongDomain);
                $("#pName").val(MeetMemberInfoDlg.data.pName);
            }
        });
    });

    // 点击上级角色时
    // $("#pName").click(function () {
    //     var formName = encodeURIComponent("parent.MeetMemberInfoDlg.data.pName");
    //     var formId = encodeURIComponent("parent.MeetMemberInfoDlg.data.belongDomain");
    //     var treeUrl = encodeURIComponent("/thesisDomain/tree");
    //     layer.open({
    //         type: 2,
    //         title: '论文领域',
    //         area: ['300px', '400px'],
    //         //content: Feng.ctxPath + '/thesisDomain/thesisDomainAssign?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
    //         content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
    //         end: function () {
    //             $("#belongDomain").val(MeetMemberInfoDlg.data.belongDomain);
    //             $("#pName").val(MeetMemberInfoDlg.data.pName);
    //         }
    //     });
    // });


    /*function domainSelectOption(){
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
                $('#belongDomain').append("<option value=''>请选择论文领域</option>");
                $('#belongDomain').append(options);
                form.render('select');
            }
        })
    }*/

    /**
     * 不区分大小写比较字符串
     * @param str
     * @returns {boolean}
     */
    String.prototype.compare = function(str) {
        //不区分大小写
        if(this.toLowerCase() == str.toLowerCase()) {
            return true;
        } else {
            return false;
        }
    }

});