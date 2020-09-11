layui.use(['layer', 'form', 'admin', 'ax','laydate','upload','formSelects'], function () {

});

$(function () {
    var seatId = Feng.getUrlParam("seatId");
    var seatDetail;
    var colNum,rowNum;
    var members = new Array();
    $.ajax({
        url:Feng.ctxPath + '/meetMember/wraplist',
        type:'get',
        dataType:'json',
        success:function(data){
            debugger;
            members = data.data;
            var meetId = seatDetail.meetId;
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
                    one.setAttribute('style','width:80px; height:50px; margin:10px; border:1px solid #000; float:left');
                    // one.css("background","pink");
                    one.innerText = ''+j;
                    one.onclick = function () {
                        var id=$(this).attr("id");
                        layer.open({
                            title: '选择参会人员',
                            type: 2,
                            area: ['620px','400px'],
                            content: Feng.ctxPath + '/meetSeat/changeUser?divId=' + id + '&meetId=' + meetId + '&seatId=' + seatId
                        });
                    }
                }
            }
        }
    })
    $.ajax({
        url:Feng.ctxPath + '/seat/detail?seatId=' + seatId,
        type:'get',
        dataType:'json',
        success:function(data){
            seatDetail = data.data;
            colNum = seatDetail.colNum;
            rowNum = seatDetail.rowNum;
        }
    })

});