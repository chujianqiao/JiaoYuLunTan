layui.use(['layer', 'form', 'admin', 'ax','laydate','upload','formSelects'], function () {
    var $ax = layui.ax;
    //座次表基本情况
    var seatId = Feng.getUrlParam("seatId");
    var ajaxSeat = new $ax(Feng.ctxPath + "/seat/detail?seatId=" + Feng.getUrlParam("seatId"));
    var seatMessage = ajaxSeat.start();
    var seatDetail = seatMessage.data;
    var colNum = seatDetail.colNum;
    var rowNum = seatDetail.rowNum;
    var meetId = seatDetail.meetId;
    var middleCol = Math.floor(colNum / 2);
    var middleRow = Math.floor(rowNum / 2);
    //主席台列数
    var platCol = 7;
    //原始座位style
    var orginStyle = 'width:80px; height:50px; margin:10px; border:1px solid #000; float:left; margin:0 0 0 0';
    //红色边框style
    var redBorderStyle = 'width:80px; height:50px; margin:10px; border:1px solid red; float:left; margin:0 0 0 0';
    //绿色背景style
    var greenBackStyle = 'width:80px; height:50px; margin:10px; border:1px solid #000; float:left; margin:0 0 0 0; background:#90EE90;';
    /**
     * 主席台
     */
    for (let i = 0; i < platCol; i++) {
        let one = document.createElement('div');
        let platform = $("#platform");
        platform.css("width",platCol * 80 + "px");
        document.getElementById('platform').appendChild(one);
        one.setAttribute('id', 'platform_'+i);
        one.setAttribute('style','width:80px; height:50px; margin:10px; border:1px solid #000; float:left; margin:0 0 0 0;text-align:center');
        one.innerText = '主席台';

    }

    /**
     * 普通座位
     */
    for(let i = 1;i<=rowNum;i++){
        let first = document.createElement('div');
        document.getElementById('seat').appendChild(first);
        first.innerText = '第' + i + '排';
        if(i > 1){
            first.setAttribute('style','width:80px; height:50px; margin:10px;float:left;clear: both;');
        }else {
            first.setAttribute('style','width:80px; height:50px; margin:10px;float:left;');
        }
        for(let j = 1;j<=colNum;j++) {
            let one = document.createElement('div');
            document.getElementById('seat').appendChild(one);
            one.setAttribute('id', 'seat_'+i+'_'+j);
            one.setAttribute('class', 'orgSeat');
            one.setAttribute('style',orginStyle);
            if(j == middleCol){
                let street = document.createElement('div');
                document.getElementById('seat').appendChild(street);
                street.setAttribute('id', 'street_'+i);
                street.setAttribute('style','width:80px; height:50px; margin:10px; float:left; margin:0 0 0 0; text-align:center');
                if(i == middleRow || i== (middleRow + 2)){
                    street.innerText = '过道';
                }
            }
            one.innerText = ''+j;
            one.onclick = function () {
                let id=$(this).attr("id");
                layer.open({
                    title: '选择参会人员',
                    type: 2,
                    area: ['620px','450px'],
                    content: Feng.ctxPath + '/meetSeat/changeUser?divId=' + id + '&meetId=' + meetId + '&seatId=' + seatId
                });
            }
        }
    }

    //座次列表信息
    var ajaxDetail = new $ax(Feng.ctxPath + "/seatDetail/wrapList?meetId=" + meetId);
    var seatsRes = ajaxDetail.start();
    debugger;
    var seats = seatsRes.data;
    var length = seats.length;
    for(let i=0;i<length;i++){
        let oneSeat = seats[i];
        let seatRow = oneSeat.seatRow;
        let seatCol = oneSeat.seatCol;
        let oneDivId = 'seat_'+ seatRow + '_' + seatCol;
        let oneDiv = $("#"+oneDivId);

        let unitName = oneSeat.unitName;
        if(unitName == '' || unitName == 'undefined' || unitName == undefined){
            let userId = oneSeat.userId;
            let ajaxUser = new $ax(Feng.ctxPath + "/mgr/getUserInfo?userId=" + userId);
            let user = ajaxUser.start();
            let userName = user.data.name;
            oneDiv.css("background","#90EE90");
            oneDiv.text(""+ seatCol + ':' + userName);
        }else{
            oneDiv.css("background","#90EE90");
            oneDiv.text(""+ seatCol + ':' + unitName);
        }

    }

    (function() {
        document.onmousedown = function () {
            var selList = [];
            // var fileNodes = document.getElementsByTagName("div");
            selList = $('.orgSeat');
            var isSelect = true;
            // 获取事件触发后的event对象，做了一个兼容性处理
            var evt = window.event || arguments[0];
            // 存放鼠标点击初始位置
            var startX = (evt.x || evt.clientX);
            var startY = (evt.y || evt.clientY);

            // 创建一个框选元素
            var selDiv = document.createElement("div");
            // 给框选元素添加CSS样式，使用决定定位
            selDiv.style.cssText = "position:absolute; width:0px; height:0px; font-size:0px; margin:0px; padding:0px; border:1px dashed #0099FF; z-index:1000; filter:alpha(opacity:60); opacity:0.6; display:none";
            // 添加ID
            selDiv.id = "selectDiv";
            // appendChild()向节点添加最后一个子节点
            document.body.appendChild(selDiv);
            // 根据起始位置，添加定位
            selDiv.style.left = startX + "px";
            selDiv.style.top = startY + "px";

            // 根据坐标给选框修改样式
            var _x = null;
            var _y = null;
            clearEventBubble(evt);
            // 移动鼠标
            document.onmousemove = function () {
                evt = window.event || arguments[0];
                if (isSelect) {
                    if (selDiv.style.display == "none") {
                        selDiv.style.display = "";
                    }
                    // 获取当前鼠标位置
                    _x = (evt.x || evt.clientX);
                    _y = (evt.y || evt.clientY);
                    selDiv.style.left = Math.min(_x, startX) + 'px';
                    selDiv.style.top = Math.min(_y, startY) + 'px';
                    selDiv.style.width = Math.abs(_x - startX) + 'px';  //Math.abs()返回数的绝对值
                    selDiv.style.height = Math.abs(_y - startY) + 'px';

                    // 获取参数
                    var _l = selDiv.getBoundingClientRect().left;
                    var _t = selDiv.getBoundingClientRect().top;
                    var _w = selDiv.offsetWidth;
                    var _h = selDiv.offsetHeight;
                    for(let i = 0; i < selList.length; i++) {
                        let sl = selList[i].offsetWidth + selList[i].getBoundingClientRect().left;
                        let st = selList[i].offsetHeight + selList[i].getBoundingClientRect().top;
                        let selectDom = selList[i];
                        if (sl > _l && st > _t && selList[i].getBoundingClientRect().left < _l + _w && selList[i].getBoundingClientRect().top < _t + _h) {
                            // 该DOM元素被选中，进行处理
                            // indexOf()可返回某个指定的字符串值在字符串中首次出现的位置
                            if(selList[i].className.indexOf(" selected") == -1){
                                selList[i].className = selList[i].className + " selected";
                            }
                            selectDom.setAttribute('style',redBorderStyle);
                        }else{
                            if (selList[i].className.indexOf(" selected")!= -1) {
                                selList[i].className = "orgSeat";
                            }
                            let text = selectDom.innerText;
                            let numNum = text.indexOf(':');
                            if(numNum == -1){
                                selectDom.setAttribute('style',orginStyle);
                            }else{
                                selectDom.setAttribute('style',greenBackStyle);
                            }

                        }
                    }
                }
                clearEventBubble(evt);
            }

            // 放开鼠标，选框消失
            document.onmouseup = function() {
                debugger;
                isSelect = false;
                if (selDiv) {
                    document.body.removeChild(selDiv);
                }
                selList = null, _x = null, _y = null, selDiv = null, startX = null, startY = null, evt = null;
                let selectList = $('.selected');
                if(selectList.length > 1){
                    let ids = "";
                    for (let k = 0; k < selectList.length; k++) {
                        let selectDom = selectList[k];
                        let domId = selectDom.id;
                        if(k == selectList.length - 1){
                            ids += domId;
                        }else{
                            ids += domId+',';
                        }
                        //重置样式
                        let text = selectDom.innerText;
                        let numNum = text.indexOf(':');
                        if(numNum == -1){
                            selectDom.setAttribute('style',orginStyle);
                        }else{
                            selectDom.setAttribute('style',greenBackStyle);
                        }
                        //使命完成，恢复到原始className
                        selectDom.className = "orgSeat";
                    }
                    layer.open({
                        title: '批量分配',
                        type: 2,
                        area: ['620px','450px'],
                        content: Feng.ctxPath + '/meetSeat/batch?divIds=' + ids + '&meetId=' + meetId + '&seatId=' + seatId
                    });
                }

            }
        }

    })();

    function clearEventBubble(evt) {
        // stopPropagation()不再派发事件。终止事件在传播过程的捕获、目标处理或起跑阶段进一步传播
        if (evt.stopPropagation)
            evt.stopPropagation();
        else
            evt.cancelBubble = true;  // 阻止该事件的进一步冒泡
        if (evt.preventDefault)
            evt.preventDefault();   // 取消事件的默认动作
        else
            evt.returnValue = false;
    }

});
