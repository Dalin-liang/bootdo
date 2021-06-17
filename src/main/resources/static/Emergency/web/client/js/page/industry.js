var mapComp;
var map;
var sound;
$(document).ready(function(){
    mapComp = new MapComp();
    mapComp.init();
    var curWwwPath = window.document.location.hostname;
    console.log(curWwwPath);
    var sock = new SockJS("http://"+curWwwPath+ ":" + window.location.port + "/dispatch");
    // var sock = new SockJS("http://"+curWwwPath+":5555/dispatch");
    var stomp = Stomp.over(sock);
    stomp.connect('guest', 'guest', function(frame) {
    	//订阅个人访问路径(订阅的时候,系统会主动推送初始化数据回来)
        stomp.subscribe('/user/queue/dispatchCenter', function (response) {
            var data=JSON.parse(response.body).data;
            var eventData=[];
            var uneventData=[];
            for (var i in data){
                if(!!data[i].actionProgramId||data[i].eventState=="1"){
                    eventData.push(data[i])
                }else{
                    uneventData.push(data[i])
                }
            }
            var o1={
                data:eventData,
                msgType:"EVENT_INIT"
            };
            var o2={
                data:uneventData,
                msgType:"EVENT_INIT"
            }
            o1.data.sort((a, b) => new Date(b.handleTime).getTime() - new Date(a.handleTime).getTime());
            sessionStorage.setItem("data",JSON.stringify(o1));
            o2.data.sort((a, b) => new Date(b.pushTime).getTime() - new Date(a.pushTime).getTime());
            sessionStorage.setItem("uneventData",JSON.stringify(o2));
            console.log(data);
            var unevent=0;
            $("#planList").empty();
            loadEventP(eventData);
            var s=new Scroll($("#planList"));
            s.start();
			getfirst();
			downoption();
			getSearch();
        });
		//订阅公共消息,接收实时数据推送
        stomp.subscribe('/topic/dispatchCenter', function (response) {
            console.log("response:",response.body);
            pushEvent(response.body)
        });
    });
});
function pushEvent(dataStr){
    var data=JSON.parse(dataStr);
    switch(data.msgType){
        case "EVENT_RE_LOCATION":
            var oldData=JSON.parse(sessionStorage.getItem("data"));
            for(var i in oldData.data){
                if(data.data.eventId==oldData.data[i].eventId){
                    oldData.data.splice(i,1,data.data);
                    console.log(oldData);
                    sessionStorage.setItem("data",JSON.stringify(oldData));
                    console.log(oldData.data[i]);
                    loadEventP(JSON.parse(sessionStorage.getItem("data")).data);
                    showEventInfo(oldData.data[i]);
                    getWarnCenter(oldData.data[i]);
                }
            }
            break;
        case "START_PROCESS_EVENT":
            var eventData=JSON.parse(sessionStorage.getItem("data"));
            console.log(eventData.data);
            eventData.data.unshift(data.data);
            /*eventData.data.sort((a, b) => new Date(b.handleTime).getTime() - new Date(a.handleTime).getTime())*/
            sessionStorage.setItem("data",JSON.stringify(eventData));
            loadEventP(JSON.parse(sessionStorage.getItem("data")).data);
            var data1=JSON.parse(sessionStorage.getItem("uneventData"));
            for(var i in data1.data){
                if(data.data.eventId==data1.data[i].eventId){
                    console.log($("#uneventlist #warnList"));
                    data1.data.splice(i,1);
                    sessionStorage.setItem("uneventData",JSON.stringify(data1));
                     if(!!unevenindex){
                        layerReload(unevenindex);
                     }else{

                     }
                }
            }
            break;
        case "END_EVENT":
            var oldData1=JSON.parse(sessionStorage.getItem("data"));
            console.log(data);
            for(var j in oldData1.data){
                if(data.data.eventId==oldData1.data[j].eventId){
                    oldData1.data.splice(j,1);
                    console.log(oldData1);
                    sessionStorage.setItem("data",JSON.stringify(oldData1));
                    loadEventP(oldData1.data);
                    var s=new Scroll($("#planList"));
				    s.start();
                }
            }
            break;
        case "START_ACTION":
             var oldData2=JSON.parse(sessionStorage.getItem("data"));
             localStorage.setItem("action",data.data.actionProgramId);
             sessionStorage.setItem("actionId",data.data.actionProgramId);
            console.log(data);
            for(var j in oldData2.data){
                if(data.data.eventId==oldData2.data[j].eventId){
                    console.log(eventId);
                    oldData2.data[j].actionProgramId=data.data.actionProgramId;
                    oldData2.data[j].eventState=5;
                    sessionStorage.setItem("data",JSON.stringify(oldData2));
                    loadEventP(oldData2.data);
                    loadStep(data.data.actionProgramId);
                }
            }
            clickAfterStart(0);
            break;
        case "NEW_EVENT":
            var uneventData1=JSON.parse(sessionStorage.getItem("uneventData"));
            uneventData1.data.unshift(data.data);
            /*uneventData1.data.sort((a, b) => new Date(b.pushTime).getTime() - new Date(a.pushTime).getTime())*/
            sessionStorage.setItem("uneventData",JSON.stringify(uneventData1));
            if(!!unevenindex){
                layerReload(unevenindex);
            }else{

            }
            var marker=new AMap.Marker({
                position:new AMap.LngLat(data.data.location.lon,data.data.location.lat),
                offset:new AMap.Pixel(-10,-10),
                icon:'../img/mapIcon/alert.gif'
            })
            map.add(marker);
            map.setCenter([data.data.location.lon,data.data.location.lat]);
            sound=setInterval(playSound,10000);
            break;
        case "NEW_LOG":
            loadLog();
            break;
        case "APP_TASK_FEEDBACK":
            loadLog();
            //从session中获取当前点击事件的actionprogramId
            var actionId = sessionStorage.getItem("appactionId");
            console.log("session actionId{}"+actionId);
            var apptrueactiveId = data.data.actionprogramId;
            console.log("app websocket actionId{}"+apptrueactiveId);
            if(!!actionId){//actionId不为空，才加载
                if(apptrueactiveId == actionId){//如果当前点击的预案就是app反馈的预案
                    //刷新任务反馈列表
                    editData=[];
                    loadStep(actionId);
                    console.log("app 任务反馈刷新。。。");
                    sessionStorage.removeItem("appactionId");//删除appactionId
                }
            }
            break;
    }
}

var MapComp = function(){
	var domId="map-cont";
	var _center = [113.892436,23.416955];
	var _map;
	var _def_zoom=15;
	var _curr_zoom = _def_zoom;
	var _max_zoom=20;
	var _min_zoom=9;
	this.init = function(){
        _map = new AMap.Map(domId, {
            zoom:_curr_zoom,//级别
            layers:[
                new AMap.TileLayer.Satellite(),
                new AMap.TileLayer.RoadNet()
            ],
            center: _center,//中心点坐标
            expandZoomRange:true,
            zooms:[_min_zoom,_max_zoom]
        });
        map=_map;
        bindMapZoomBtnEvent();
	};

	var bindMapZoomBtnEvent = function(){
		$("#btn_map_back_center").click(function(){
		    var index=$("#planList .dialog1.active").index();
		    var center=JSON.parse(sessionStorage.getItem("data")).data[index].location;
			_map.setZoomAndCenter(_def_zoom,[center.lon,center.lat]);
		});
        $("#btn_map_zoom_in").click(function(){
			if(_curr_zoom<_max_zoom){
                _curr_zoom++;
                _map.setZoom(_curr_zoom);
			}else{
                //TODO:弹窗提示,等待统一提示框
				alert("已到达最大缩放级别.[等待统一提示框组件]");
			}

        });
        $("#btn_map_zoom_out").click(function(){
            if(_curr_zoom>_min_zoom){
                _curr_zoom--;
                _map.setZoom(_curr_zoom);
			}else{
            	//TODO:弹窗提示,等待统一提示框
                alert("已到达最小缩放级别.[等待统一提示框组件]");
			}
        });
	};
}

function loadEventP(data){
    $("#planList").empty();
    for(var i in data){
        if(data[i].eventState!=1){
            if(!!sessionStorage.getItem("beforeIndex")){
                var beforeIndex=sessionStorage.getItem("beforeIndex");
                if(i==beforeIndex){
                    $("#planList").append("<div class='dialog1 warn_li active' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                        " <span class='pull-right'>指挥调度中</span></div>"+
                        "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                        "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                    )
                }else{
                    $("#planList").append("<div class='dialog1 warn_li mt10' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                        " <span class='pull-right'>指挥调度中</span></div>"+
                        "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                        "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                    )
                }
            }else{
                if(i==0){
                    $("#planList").append("<div class='dialog1 warn_li active' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                        " <span class='pull-right'>指挥调度中</span></div>"+
                        "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                        "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                    )
                }else{
                    $("#planList").append("<div class='dialog1 warn_li mt10' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                        " <span class='pull-right'>指挥调度中</span></div>"+
                        "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                        "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                    )
                }
            }
        }else{
            if(!!sessionStorage.getItem("beforeIndex")) {
                var beforeIndex = sessionStorage.getItem("beforeIndex");
                if(i==beforeIndex){
                    $("#planList").append("<div class='dialog1 warn_li active' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                        " <span class='pull-right colorgreen'>预案启动中</span></div>"+
                        "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                        "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                    )
                }else{
                    $("#planList").append("<div class='dialog1 warn_li mt10' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                        " <span class='pull-right colorgreen'>预案启动中</span></div>"+
                        "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                        "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                    )
                }
            }else{
               if(i==0){
                    $("#planList").append("<div class='dialog1 warn_li active' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                        " <span class='pull-right colorgreen'>预案启动中</span></div>"+
                        "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                        "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                    )
                }else{
                    $("#planList").append("<div class='dialog1 warn_li mt10' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                        " <span class='pull-right colorgreen'>预案启动中</span></div>"+
                        "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                        "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                    )
                }
            }
        }
    }
    sessionStorage.removeItem("beforeIndex");
    $("b.colornum").text(data.length);
}

function loadStep(aId){
    $.ajax({
        url:'/dispatch/center/getTaskStep/'+aId,
        type:'post',
        dataType:'json',
        success:function(data){
            console.log(data);
            $("#list").empty();
            $("#list1").empty();
            editData=[];
            for(var i in data.data){
                editData.push(data.data[i]);
            }
            editData.sort((a, b) => parseInt(a.sortNo) - parseInt(b.sortNo));
            var count=0;
            for(var i in editData){
                if(editData[i].taskType==1){
                    if(i==0){
                        $("#list").append("<div class='dialog1 active' dataId='"+editData[i].id+"' idx='"+i+"'><div class='warn_li_tl clearfix'>"+
                        "<p><span class='badge plansign'>"+prefixInteger((parseInt(i)+1),2)+"</span>"+(editData[i].name?editData[i].name:'')+"</p></div>"+
                        "<div class='pad5 line-clamp'>"+(editData[i].content?editData[i].content:'')+"</div>"+
                        "<div class='pad5 line-clamp colornum'>反馈:"+(editData[i].feedbackContent?editData[i].feedbackContent:'')+"</div></div></div>")
                    }else{
                        $("#list").append("<div class='dialog1 mt10' dataId='" + editData[i].id + "' idx='"+i+"'><div class='warn_li_tl clearfix'>" +
                            "<p><span class='badge plansign'>" + prefixInteger((parseInt(i) + 1), 2) + "</span>" + (editData[i].name ? editData[i].name : '') + "</p></div>" +
                            "<div class='pad5 line-clamp'>" + (editData[i].content ? editData[i].content : '') + "</div>" +
                            "<div class='pad5 line-clamp colornum'>反馈:" + (editData[i].feedbackContent ? editData[i].feedbackContent : '') + "</div></div></div>")
                    }
                }else{
                    if(count==0){
                        $("#list1").append("<div class='dialog1 active' dataId='"+editData[i].id+"' idx='"+i+"'><div class='warn_li_tl clearfix'>"+
                        "<p><span class='badge plansign'>"+prefixInteger((parseInt(i)+1),2)+"</span>"+(editData[i].name?editData[i].name:'')+"</p></div>"+
                        "<div class='pad5 line-clamp'>"+(editData[i].content?editData[i].content:'')+"</div>"+
                        "<div class='pad5 line-clamp colornum'>反馈:"+(editData[i].feedbackContent?editData[i].feedbackContent:'')+"</div></div></div>");
                        count++;
                    }else{
                         $("#list1").append("<div class='dialog1 mt10' dataId='" + editData[i].id + "' idx='"+i+"'><div class='warn_li_tl clearfix'>" +
                            "<p><span class='badge plansign'>" + prefixInteger((parseInt(i) + 1), 2) + "</span>" + (editData[i].name ? editData[i].name : '') + "</p></div>" +
                            "<div class='pad5 line-clamp'>" + (editData[i].content ? editData[i].content : '') + "</div>" +
                            "<div class='pad5 line-clamp colornum'>反馈:" + (editData[i].feedbackContent ? editData[i].feedbackContent : '') + "</div></div></div>")
                    }
                }
            }

        }
    })
}
