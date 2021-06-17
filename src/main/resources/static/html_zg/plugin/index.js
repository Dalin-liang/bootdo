var GPS = {

    PI: 3.14159265358979324,
    x_pi: 3.14159265358979324 * 3000.0 / 180.0,
    delta: function (lat, lon)
    {
        // Krasovsky 1940
        //
        // a = 6378245.0, 1/f = 298.3
        // b = a * (1 - f)
        // ee = (a^2 - b^2) / a^2;
        var a = 6378245.0; //  a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
        var ee = 0.00669342162296594323; //  ee: 椭球的偏心率。
        var dLat = this.transformLat(lon - 105.0, lat - 35.0);
        var dLon = this.transformLon(lon - 105.0, lat - 35.0);
        var radLat = lat / 180.0 * this.PI;
        var magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        var sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * this.PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * this.PI);
        return { 'lat': dLat, 'lon': dLon };
    },

    //WGS-84 to GCJ-02
    WGStoGCJ: function (wgsLat, wgsLon)
    {
        if (this.outOfChina(wgsLat, wgsLon))
            return { 'lat': wgsLat, 'lon': wgsLon };

        var d = this.delta(wgsLat, wgsLon);
        return { 'lat':parseFloat(wgsLat)+parseFloat(d.lat), 'lon':parseFloat(wgsLon)+parseFloat(d.lon)};
    },
    //GCJ-02 to WGS-84
    GCJtoWGS: function (gcjLat, gcjLon)
    {
        if (this.outOfChina(gcjLat, gcjLon))
            return { 'lat': gcjLat, 'lon': gcjLon };

        var d = this.delta(gcjLat, gcjLon);
        return { 'lat':parseFloat(gcjLat)-parseFloat(d.lat), 'lon':parseFloat(gcjLon)-parseFloat(d.lon)};
    },
    //GCJ-02 to WGS-84 exactly
    GCJtoWGS_exact: function (gcjLat, gcjLon)
    {
        var initDelta = 0.01;
        var threshold = 0.000000001;
        var dLat = initDelta, dLon = initDelta;
        var mLat = gcjLat - dLat, mLon = gcjLon - dLon;
        var pLat = gcjLat + dLat, pLon = gcjLon + dLon;
        var wgsLat, wgsLon, i = 0;
        while (1)
        {
            wgsLat = (mLat + pLat) / 2;
            wgsLon = (mLon + pLon) / 2;
            var tmp = this.WGStoGCJ(wgsLat, wgsLon)
            dLat = tmp.lat - gcjLat;
            dLon = tmp.lon - gcjLon;
            if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
                break;

            if (dLat > 0) pLat = wgsLat; else mLat = wgsLat;
            if (dLon > 0) pLon = wgsLon; else mLon = wgsLon;

            if (++i > 10000) break;
        }
        //console.log(i);
        return { 'lat':parseFloat(wgsLat), 'lon':parseFloat(wgsLon)};
    },
    //GCJ-02 to BD-09
    GCJtoBD: function (gcjLat, gcjLon)
    {
        var x = gcjLon, y = gcjLat;
        var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * this.x_pi);
        var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * this.x_pi);
        bdLon = z * Math.cos(theta) + 0.0065;
        bdLat = z * Math.sin(theta) + 0.006;
        return { 'lat':parseFloat(bdLat), 'lon':parseFloat(bdLon)};
    },
    //BD-09 to GCJ-02
    BDtoGCJ: function (bdLat, bdLon)
    {
        var x = bdLon - 0.0065, y = bdLat - 0.006;
        var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * this.x_pi);
        var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * this.x_pi);
        var gcjLon = z * Math.cos(theta);
        var gcjLat = z * Math.sin(theta);
        return { 'lat':parseFloat(gcjLat), 'lon':parseFloat(gcjLon)};
    },
    //WGS-84 to Web mercator
    //mercatorLat -> y mercatorLon -> x
    WGStoMercator: function (wgsLat, wgsLon)
    {
        var x = wgsLon * 20037508.34 / 180.;
        var y = Math.log(Math.tan((90. + wgsLat) * this.PI / 360.)) / (this.PI / 180.);
        y = y * 20037508.34 / 180.;
        return { 'lat':parseFloat(y), 'lon':parseFloat(x)};
        /*
        if ((Math.abs(wgsLon) > 180 || Math.abs(wgsLat) > 90))
            return null;
        var x = 6378137.0 * wgsLon * 0.017453292519943295;
        var a = wgsLat * 0.017453292519943295;
        var y = 3189068.5 * Math.log((1.0 + Math.sin(a)) / (1.0 - Math.sin(a)));
        return {'lat' : y, 'lon' : x};
        //*/
    },
    // Web mercator to WGS-84
    // mercatorLat -> y mercatorLon -> x
    MercatortoWGS: function (mercatorLat, mercatorLon)
    {
        var x = mercatorLon / 20037508.34 * 180.;
        var y = mercatorLat / 20037508.34 * 180.;
        y = 180 / this.PI * (2 * Math.atan(Math.exp(y * this.PI / 180.)) - this.PI / 2);
        return { 'lat':parseFloat(y), 'lon':parseFloat(x)};
        /*
        if (Math.abs(mercatorLon) < 180 && Math.abs(mercatorLat) < 90)
            return null;
        if ((Math.abs(mercatorLon) > 20037508.3427892) || (Math.abs(mercatorLat) > 20037508.3427892))
            return null;
        var a = mercatorLon / 6378137.0 * 57.295779513082323;
        var x = a - (Math.floor(((a + 180.0) / 360.0)) * 360.0);
        var y = (1.5707963267948966 - (2.0 * Math.atan(Math.exp((-1.0 * mercatorLat) / 6378137.0)))) * 57.295779513082323;
        return {'lat' : y, 'lon' : x};
        //*/
    },
    // two point's distance
    distance: function (latA, lonA, latB, lonB)
    {
        var earthR = 6371000.;
        var x = Math.cos(latA * this.PI / 180.) * Math.cos(latB * this.PI / 180.) * Math.cos((lonA - lonB) * this.PI / 180);
        var y = Math.sin(latA * this.PI / 180.) * Math.sin(latB * this.PI / 180.);
        var s = x + y;
        if (s > 1) s = 1;
        if (s < -1) s = -1;
        var alpha = Math.acos(s);
        var distance = alpha * earthR;
        return distance;
    },
    outOfChina: function (lat, lon)
    {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    },
    transformLat: function (x, y)
    {
        var ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * this.PI) + 40.0 * Math.sin(y / 3.0 * this.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * this.PI) + 320 * Math.sin(y * this.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    },
    transformLon: function (x, y)
    {
        var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * this.PI) + 20.0 * Math.sin(2.0 * x * this.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * this.PI) + 40.0 * Math.sin(x / 3.0 * this.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * this.PI) + 300.0 * Math.sin(x / 30.0 * this.PI)) * 2.0 / 3.0;
        return ret;
    }
};

var mapComp;
var map;
var allevent;
var infoWindow;
var infoWindow2;
var evevtmarker=[];
var nonlink=false;
var eliminate;
var illegalmarker=[];//两违监测
   	function getEvent(){
   		$.ajax({
            url: '/dispatch/center/getWarnByDevice',
            dataType: 'json',
            type: 'get',
            success: function (result) {
            	if(result.data.length>0){
            		
//          		document.getElementById('audio').load();
//					setInterval(function(){    	
//				   		document.getElementById('audio').play();
//					},2000)
            	}
            	
				$.each(result.data,function(idx,item) {	
					if(item.latLon){
						var latLon=GPS.WGStoGCJ(item.latLon.split(',')[1],item.latLon.split(',')[0]);				
						marker=new AMap.Marker({
							position:new AMap.LngLat(latLon.lat,latLon.lon),
							offset:new AMap.Pixel(-10,-10),
							icon:'img/alert.gif',
							data:item
						})
						map.add(marker);
						evevtmarker.push(marker);
						marker.on('click',getInfo);
					}
			   });
            }
       })   		  		
   	}
   	
   	var setAudio;
   	function getEvent2(allevent){
		$("#event_btn").removeClass("active")
		
		$("#event_btn").parent("a").addClass("nopointer")
		
		if(allevent.length>0){    		
//  		document.getElementById('audio').load();
//			setAudio = setInterval(function(){    	
//		   		document.getElementById('audio').play();
//			},2000)  sourceName
    	}
		evevtmarker=[];

        $.each(allevent,function(idx,item) {
            if(item.latLon){
                var latLon;
                if(item.sourceType=="6"){
                    latLon={'lat':item.latLon.split(',')[1], 'lon':item.latLon.split(',')[0]}
                }else{
                    latLon=GPS.WGStoGCJ(item.latLon.split(',')[1],item.latLon.split(',')[0]);
                }
                marker=new AMap.Marker({
                    position:new AMap.LngLat(latLon.lat,latLon.lon),
                    offset:new AMap.Pixel(-10,-10),
                    icon:'img/alert.gif',
                    data:item
                })
                map.add(marker);
                evevtmarker.push(marker);
                marker.on('click',getInfo);
            }
        })
		$("#event_btn").addClass("active");
		$("#event_btn").parent("a").removeClass("nopointer")
   	}
   	
   	
   	var mapflag;
   	function getInfo(e){
//  	var item=e.target.get("data");
//		var content=["<div class='pop_box'><table></tbody class>"+		
//			"<tr><td style='width:90px'>事件类型：</td><td>"+(item.eventType?item.eventType:"")+"</td></tr>"+		
//			"<tr><td>事件摘要：</td><td>"+(item.eventDesc?item.eventDesc:"")+"</td></tr>"+		
//			"<tr><td>预警类别：</td><td>"+(item.warnType?item.warnType:"")+"</td></tr>"+					
//			"<tr><td>预警级别：</td><td>"+(item.warnLevel?item.warnLevel:"")+"</td></tr>"+
//			"<tr><td>地点：</td><td>"+(item.address?item.address:"")+"</td></tr>"+		
//			"<tr><td>经纬度：</td><td>"+(item.latLon?item.latLon:"")+"</td></tr>"+		
//			"<tr><td>报告单位：</td><td>"+(item.repDept?item.repDept:"")+"</td></tr>"+		
//		"</tbody></table><a class='btn pop_btn btn-success' href='http://172.27.162.39:666/Emergency/web/client/pages/industry.html?eventid="+item.eventId+"' target='_blank'>事件处理</a></div>"];
////		console.log(content)
//		infoWindow = new AMap.InfoWindow({
//			content: content.join("<br>")
//		});
//
//		// 打开信息窗体
//		infoWindow.open(map, [item.location.lon,item.location.lat]);
		
		var item=e.target.get("data");
		mapflag=e.target;
		console.log("item click;");
		console.log(item)
		var source="";
		if(item.sourceType){
			switch(item.sourceType){
		        case "6":
		        	source="消防用电";	 
		        	break;
		        case "7":
		        	source="行为分析";	 
		        	break;
		        case "8":
		        	source="水雨情";	 
		        	break;
		        case "9":
		        	source="特殊人群";	 
		        	break;
		        case "10":
		        	source="客流统计";	 
		        	break;
		        case "11":
		        	source="智慧路灯";	 
		        	break;
		        case "12":
		        	source="水质监测";	 
		        	break;
		        case "14":
		        	source="健康小屋";	 
		        	break;
	        }
		}
		var appImgContent="<div class='pop_box'><table><tbody>";
		var list = getAppReportImgByEventId(item.eventId);
		console.log("imglist==",list);
		var trcon1 = '' ;
		if(list.length>0){
			var trcount = Math.ceil(list.length/3);
			for(var i =0 ; i < trcount; i++){
				var trcon = '<tr>';
				for (var j = 0; j < 3; j++){
					if (i*3+j<list.length) {
						trcon += "<td><img src='"+list[i*3+j].fileUrl+"' width='90' height='90px' /></td>" ;
						//trcon += "<td><img src='http://113.108.176.67:6011/zgappServer/newPic/15691636999497943.jpg' width='90' height='90px' /></td>" ;
					}
				}
				trcon += '</tr>';
				trcon1+=trcon;
				//trcon += "<tr><td><img src='/appFile"+list[i*3].fileUrl+"' width='80' height='90px' /></td>" +
				//	"<td><img src='/appFile"+list[i*3+1].fileUrl+"' width='80' height='90px' /> </td>" +
				//	"<td><img src='/appFile"+list[i*3+2].fileUrl+"' width='80' height='90px' /></td></tr>";
			}
		}
		appImgContent +=trcon1+"</tbody></table></div>";
		
		var content=["<p class='pop_tl'>告警事件</p><div class='pop_box' style='width:350px'><table><tbody class>"+
			"<tr><td style='width:90px'>事件描述：</td><td>"+(item.eventDesc||"")+"</td></tr>"+		
			"<tr><td>上报时间：</td><td>"+(item.repTime||"")+"</td></tr>"+	
			"<tr><td>上报人：</td><td>"+(item.repName||"")+"</td></tr>"+
			"<tr><td>上报人电话：</td><td>"+(item.repPhone||"")+"</td></tr>"+
			"<tr><td>地点：</td><td>"+(item.address||"")+"</td></tr>"+		
			"<tr><td>经纬度：</td><td>"+(item.latLon||"")+"</td></tr>"+		
			"<tr><td>事件来源：</td><td>"+source+"</td></tr>"+		
		"</tbody></table>"+
		appImgContent+
//		"<a class='btn pop_btn btn-success' href='http://172.27.162.39:666/Emergency/web/client/pages/industry.html?eventid="+item.id+"' target='_blank'>事件处理</a>"+
		"<a class='btn btn_modal btn-success' data-toggle='modal' data-target='#eventModal'>推送</a>"+
		"<a class='btn btn_modal btn-primary' data-toggle='modal' data-target='#contModal'>终结</a>"+
		"</div>"];
//		console.log(content)
		infoWindow = new AMap.InfoWindow({
			content: content.join("<br>")
		});

		// 打开信息窗体
		infoWindow.open(map, e.target.get("position"));
		
//		$("#event_addr").val(item.address||"")
//		$("#event_des").val(item.eventDesc||"")
//		$("#event_repdate").val(item.repTime||"")
//		
//		$("#event_re2").val(item.repName||"").trigger("change");
//		
//		$("#event_phone").val(item.repPhone||"");
//		
//		$("#soure_ul a").each(function(){
//			var sourceid=$(this).attr("id")
//			if(sourceid==item.sourceType){
//				$("#soure_drop .txt").text($(this).text())
//				$("#soure_drop .txt").attr("id",sourceid)
//				return false;
//			}else{
//				$("#soure_drop .txt").text($("#soure_ul a").eq(0).text())
//				$("#soure_drop .txt").attr("id",$("#soure_ul a").eq(0).attr("id"))
//			}
//		})
//		$("#event_re").val("")
//		$("#event_sex .txt").text("男")
////		$("#event_dapart .txt").text("").attr("id","")
////		$("#event_dapart .txt").text($("#dapart_ul a").eq(0).text());
//		$("#dapart_ul a").each(function(index){
//			if($(this).attr("id")=="4b4ba08f49044da68ba0531bf70d0ce6"){
//				console.log($(this).attr("id"))
//				$(this).click();
//			}
//		})
//		$("#event_class .txt").text("").attr("id","")
//		$("#event_type .txt").text("").attr("id","")
//		$("#event_level .txt").text("").attr("id","")
////		$("#event_accdata").val("")
//		$("#event_remark").val("")
//		
//		$("#push_btn").attr("flag",item.eventId)
//		$("#end_btn").attr("flag",item.eventId)
//		$("#end_btn").attr("desc",item.eventDesc||"")
//		
//		/* 清空结束告警时 通讯录
//		$("#inputkey").empty()
//		$("#inputtxt").val("")
//		*/
//		$("#end_class").removeClass("active");
//		$("#end_class .btn").removeClass("btn-success");
//		$("#remark_resp").val("")
//		$("#event_operator").val("")
		eventItem=item;
	}
	
   	var eventItem;
//  新推送	
	var newmarker;
	function newEvent(dataStr){
		var data=JSON.parse(dataStr);
		if(data.msgType=="UN_TREATEDEVENT"){			
		    newmarker=new AMap.Marker({
				position:new AMap.LngLat(data.data.latLon.split(',')[1],data.data.latLon.split(',')[0]),
				offset:new AMap.Pixel(-10,-10),
				icon:'img/alert.gif',
				data:data.data
			})
			map.add(newmarker);
			evevtmarker.push(newmarker);
			newmarker.on('click',getInfo);
			eventItem=data.data;
			getNewInfo()
		}else if(data.msgType=="END_EVENT"){
			$.each(evevtmarker,function(idx,item) {
				if(data.data.eventId==item.Ce.data.eventId){
					item.setMap(null);
				}
			});
			//getEvent();
		}
	}

//	切换语音
	function toggleAudio(){
		document.getElementById('audio').src="css/8773.mp3";
        setAudio
	}
    function getNewInfo(){
		var item=eventItem;
		mapflag=newmarker;
		console.log(item)
//		eventdescAudio

		clearInterval(setAudio);
		document.getElementById('audio').src=item.eventdescAudio;
//		document.getElementById('audio').play();
//		document.getElementById('audio').addEventListener('ended', toggleAudio, false);
//		document.getElementById('audio').removeEventListener("ended",toggleAudio,false);
		var source="";
		if(item.sourceType){
			switch(item.sourceType){
		        case "6":
		        	source="消防用电";	 
		        	break;
		        case "7":
		        	source="行为分析";	 
		        	break;
		        case "8":
		        	source="水雨情";	 
		        	break;
		        case "9":
		        	source="特殊人群";	 
		        	break;
		        case "10":
		        	source="客流统计";	 
		        	break;
		        case "11":
		        	source="智慧路灯";	 
		        	break;
		        case "12":
		        	source="水质监测";	 
		        	break;
		        case "14":
		        	source="健康小屋";	 
		        	break;
	        }
		}

		var appImgContent="<div class='pop_box'><table><tbody>";
		var list = getAppReportImgByEventId(item.eventId);
		var trcon1 = '' ;
		if(list.length>0){
			var trcount = Math.ceil(list.length/3);
			for(var i =0 ; i < trcount; i++){
				var trcon = '<tr>';
				for(var j = 0 ; j < 3; j++){
					if (i*3+j < list.length){
						trcon += "<td><img src='"+list[i*3+j].fileUrl+"' width='90' height='90px' /></td>" ;
					}
				}
				trcon +='</tr>';
			}
			trcon1 +=trcon;
		}
		appImgContent +=trcon1+"</tbody></table></div>";
		var content=["<p class='pop_tl'>告警事件</p><div class='pop_box' style='width:350px'><table></tbody class>"+		
			"<tr><td style='width:90px'>事件描述：</td><td>"+(item.eventDesc||"")+"</td></tr>"+		
			"<tr><td>上报时间：</td><td>"+(item.repTime||"")+"</td></tr>"+		
			"<tr><td>上报人电话：</td><td>"+(item.repPhone||"")+"</td></tr>"+
			"<tr><td>地点：</td><td>"+(item.address||"")+"</td></tr>"+		
			"<tr><td>经纬度：</td><td>"+(item.latLon||"")+"</td></tr>"+		
			"<tr><td>事件来源：</td><td>"+source+"</td></tr>"+		
		"</tbody></table>"+
		appImgContent+
		"<a class='btn btn_modal btn-success' data-toggle='modal' data-target='#eventModal'>推送</a>"+
		"<a class='btn btn_modal btn-primary' data-toggle='modal' data-target='#contModal'>终结</a>"+
		"</div>"];
		infoWindow2 = new AMap.InfoWindow({
			content: content.join("<br>")
		});
		
		// 打开信息窗体
		infoWindow2.open(map, [item.latLon.split(',')[1],item.latLon.split(',')[0]]);
		
	}
	 
    
	
$(document).ready(function(){
	function islogin(){
		theurl="http://"+window.location.host+"/index";
		$.ajax({
            url:theurl,
            type: "post",
            async: false,
            success: function (data) {  // 登录情况，返回{msg: "服务器错误，请联系管理员", code: 500}
            	if(data.code!=500){
            		window.location.href=theurl
            	}
            }
        });
	}
	islogin();
    mapComp = new MapComp();
    mapComp.init();
    
    var curWwwPath = window.document.location.hostname;
    console.log(curWwwPath);
    var sock = new SockJS("http://"+curWwwPath+ ":" + window.location.port + "/dispatch");
    //var sock = new SockJS("http://"+curWwwPath+":5555/dispatch");
    var stomp = Stomp.over(sock);
    stomp.connect('guest', 'guest', function(frame) {
    	//订阅个人访问路径(订阅的时候,系统会主动推送初始化数据回来)
        stomp.subscribe('/user/unTreatedEvent/dispatchCenter', function (response) {
            allevent=JSON.parse(response.body).data;
            getEvent2(allevent)

        });

        stomp.subscribe('/topic/dispatchCenter', function (response) {
        	newEvent(response.body)
        });
		
   	});
   	
	$("#event_btn").on("click", function() {
	 	if($(this).hasClass("active")){
			$.each(evevtmarker,function(idx,item){
				item.setMap(null);
			})			
    		if(infoWindow){
    			infoWindow.close();
    		}
			$(this).removeClass("active")
//			clearInterval(setAudio)
//			document.getElementById('audio').src=="";
//			document.getElementById('audio').pause();
		}else{
	 		getEvent2(allevent)
		}
	})
	
/* //非及时推送
   	getEvent()
	$("#event_btn").on("click", function() {
	 	if($(this).hasClass("active")){
			$.each(evevtmarker,function(idx,item){
				item.setMap(null);
			})			
    		if(infoWindow){
    			infoWindow.close();
    		}
			$(this).removeClass("active")
			document.getElementById('audio').pause();
		}else{
	 		getEvent()
			$(this).addClass("active")
		}
	})
	//非及时推送   */
	var initJson = {
   	"9": {
   		"dept": "fb99d18a206c4be7a4d7e8b410655b52",
   		"accident": "4f7166afa66c477ea77cb5642123f5ec",
   		"earlywarn": "3dfb6660ba664aa7b4eaf5241f5d2ac1",
   		"earlylevel": "c26b9c1df860414a9a62936a9a1d922f"
   	},
   	"18": {
   		"dept": "284c6993e4ed4c3692d83058c3809e5b",
   		"accident": "6ca7eb18ff2d4141a59f1090448cf66c",
   		"earlywarn": "fcdb4f543d6a485a96f25a30cf6ee6f6",
   		"earlylevel": "60c8220bbc484a8487f1396dcfd5415d"
   	},
   	"19": {
   		"dept": "284c6993e4ed4c3692d83058c3809e5b",
   		"accident": "6ca7eb18ff2d4141a59f1090448cf66c",
   		"earlywarn": "43ee0dbff5664e8fbe0664c2cf3d06d6",
   		"earlylevel": "0aed3e90c7d441efa1092b00ac6b0c01"
   	},
   	"20": {
   		"dept": "284c6993e4ed4c3692d83058c3809e5b",
   		"accident": "6ca7eb18ff2d4141a59f1090448cf66c",
   		"earlywarn": "46fd2af0ef23414db23dbafbd77a43f0",
   		"earlylevel": "e09b8cf746b64860ac0400468e96dd07"
   	},
   	"8": {
   		"dept": "4048932fda8c40ebbb2cd9954df6ae48",
   		"accident": "4432e338b2d04a86a41efb856385da7a",
   		"earlywarn": "4f51de19a4ba4cedafb130a4b8f99221",
   		"earlylevel": "2c1552db71834234b9a785d93bd116ba"
   	},
   	"aqi": {
   		"dept": "052aa94a5b7548379be87b9ca7f04027",
   		"accident": "cfc83bf63de44633bb4cb59cb0a0561d",
   		"earlywarn": "67caaf5540854da8baba6579caf77387",
   		"earlylevel": "159dbf16121f40828595500bd9329fc4"
   	},
   	"12": {
   		"dept": "052aa94a5b7548379be87b9ca7f04027",
   		"accident": "cfc83bf63de44633bb4cb59cb0a0561d",
   		"earlywarn": "dba5be1ee1e345869bd2114bafc90b3f",
   		"earlylevel": "cc481842d2d248dda03e366ac40660bb"
   	},
   	"11": {
   		"dept": "fdebca551e51476e9c2536bcaababfd5",
   		"accident": "5eb736a3df014c748a2773ea5f1885a7",
   		"earlywarn": "320db896b7154a73aa6a02441ea2115e",
   		"earlylevel": "cbec2b99826840b081bff079ee3fd1a5"
   	},
   	"21": {
   		"dept": "284c6993e4ed4c3692d83058c3809e5b",
   		"accident": "6ca7eb18ff2d4141a59f1090448cf66c",
   		"earlywarn": "e1d8c1e5dcdc4c6eaca9e01500472d02",
   		"earlylevel": "73af9ca1293144cf9dd113d8c9bc5434"
   	}
   };
   
	var personArr;
	$('#eventModal').on('show.bs.modal', function () {
		if(nonlink){
			$("#event_addr").val("");
			$("#event_des").val("");
			$("#event_repdate").val("");
			
			$("#event_re2").val(personArr.user.name);
		    $("#event_re2").trigger("change");
			$("#event_phone").val(personArr.user.mobile||"");
			if(personArr.user.sex==1){
				$("#event_sex .txt").text("女")
			}else if(personArr.user.sex==1){				
				$("#event_sex .txt").text("男")
			}else{
				$("#event_sex .txt").text("其他")
			}
			$("#soure_ul a").each(function(){
				var sourceid=$(this).attr("id")
				$("#soure_drop .txt").text($("#soure_ul a").eq(0).text())
				$("#soure_drop .txt").attr("id",$("#soure_ul a").eq(0).attr("id"))
			})
			
			$("#event_dapart2").val("4b4ba08f49044da68ba0531bf70d0ce6").select2();
			getdapart_ul("4b4ba08f49044da68ba0531bf70d0ce6")
			
			$("#event_remark").val("")
			
//			$("#push_btn").attr("flag",eventItem.eventId)
//			$("#end_btn").attr("flag",eventItem.eventId)
//			$("#end_btn").attr("desc",eventItem.eventDesc||"")
//			
//			$("#end_class").removeClass("active");
//			$("#end_class .btn").removeClass("btn-success");
//			$("#remark_resp").val("")
//			$("#event_operator").val("")
			
		}else{
			$("#event_addr").val(eventItem.address||"")
			$("#event_des").val(eventItem.eventDesc||"")
			$("#event_repdate").val(eventItem.repTime||"")
			console.log(personArr)
			if(eventItem.repName){
				$("#event_re2").val(eventItem.repName).trigger("change");			
				$("#event_phone").val(eventItem.repPhone||"");
				$("#event_sex .txt").text("其他")
				
			}
			var txt=$(".select2-event_re2-container").text().trim();
			if(txt==""){
				$("#event_re2").val(personArr.user.name);
			    $("#event_re2").trigger("change");
				$("#event_phone").val(personArr.user.mobile||"");
				if(personArr.user.sex==1){
					$("#event_sex .txt").text("女")
				}else if(personArr.user.sex==1){				
					$("#event_sex .txt").text("男")
				}else{
					$("#event_sex .txt").text("其他")
				}
			}
			
			$("#soure_ul a").each(function(){
				var sourceid=$(this).attr("id")
				if(sourceid==eventItem.sourceType){
					$("#soure_drop .txt").text($(this).text())
					$("#soure_drop .txt").attr("id",sourceid)
					return false;
				}else{
					$("#soure_drop .txt").text($("#soure_ul a").eq(0).text())
					$("#soure_drop .txt").attr("id",$("#soure_ul a").eq(0).attr("id"))
				}
			})
			var test=""+eventItem.sourceType;
			console.log(initJson[test])
			if(initJson[test]){
				$("#event_dapart2").val(initJson[test].dept).select2();
	//			debugger;
				getdapart_ul(initJson[test].dept,initJson[test].accident)
				getclass_ul(initJson[test].accident,initJson[test].earlywarn)
				gettype_ul(initJson[test].earlywarn,initJson[test].earlylevel)
				
				
			}else{
				$("#event_dapart2").val("4b4ba08f49044da68ba0531bf70d0ce6").select2();
				getdapart_ul("4b4ba08f49044da68ba0531bf70d0ce6")
			}
			
			
			$("#event_remark").val("")
			
			$("#push_btn").attr("flag",eventItem.eventId)
			$("#end_btn").attr("flag",eventItem.eventId)
			$("#end_btn").attr("desc",eventItem.eventDesc||"")
			
			$("#end_class").removeClass("active");
			$("#end_class .btn").removeClass("btn-success");
			$("#remark_resp").val("")
			$("#event_operator").val("")			
		}
	})
	
	
	$('#eventModal').on('hide.bs.modal', function () {
		nonlink=false;
	})
	$("#nonlink_btn").click(function(){
		nonlink=true;
	})
	
	$('#contModal').on('show.bs.modal', function () {
		$("#end_btn").attr("flag",eventItem.eventId)
	})
	$(".dropdown-menu").on("click","li",function(){
		var txt=$(this).text();
		var id=$(this).find("a").attr("id");
		$(this).parents(".com_dropdown").find(".txt").text(txt);
		$(this).parents(".com_dropdown").find(".txt").attr("id",id)
	})
	
    
//  setInterval(function(){
//  	if($("#event_btn").hasClass("active")){
//			$.each(evevtmarker,function(idx,item){
//				item.setMap(null);
//			})    		
//  		getEvent();
//		}else{
//			return;
//		}
//	},10000)
    
    
//  获取水雨情
	var rainmarker=[];
    function getrain(){
    	rainmarker=[];
		$("#rain_btn").removeClass("active")
		$("#rain_btn").parent("a").addClass("nopointer")
    	$.ajax({
            url: '/api/device/stStbprpB/getDeviceByParam',
            dataType: 'json',
            type: 'get',
            success: function (result) {
    			$("#rain_btn").addClass("active")
				$("#rain_btn").parent("a").removeClass("nopointer")
				$.each(result,function(idx,item) {
					var latLon=GPS.WGStoGCJ(item.lon_lat.split(',')[1],item.lon_lat.split(',')[0]);
					marker=new AMap.Marker({
						position:new AMap.LngLat(latLon.lat,latLon.lon),
						offset:new AMap.Pixel(-10,-10),
						icon:'images/2_01.png',
						data:item
					})
					map.add(marker);
					rainmarker.push(marker);
					marker.on('click',getrainInfo);
			   });
//              map.setCenter([result[0].lon_lat.split(',')[1],result[0].lon_lat.split(',')[0]])
            }
        })
    }
//  水雨情杆
	getrain();
    $("#rain_btn").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(rainmarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getrain();
    	}
	})
	var getrainInfo=function(e){
		var data=e.target.get("data");
//	    var lnglat=[data.lon_lat.split(',')[1],data.lon_lat.split(',')[0]];
	    $.ajax({
			url: '/api/device/stStbprpB/getDeviceDetailById',
			dataType: 'json',
			type: 'get',
			data: {
				id: data.id
			},
			success: function(result) {
				console.log(result.data)
				var content=["<p class='pop_tl'>水雨情</p><div class='pop_box'><table></tbody class>"+
					"<tr><td>经纬度：</td><td>"+result.data[0].lgtd+","+result.data[0].lttd+"</td></tr>"+
					"<tr><td style='width:115px;'>至河口距离(km)：</td><td>"+(result.data[0].dstrvm==null?'':result.data[0].dstrvm)+"</td></tr>"+		
					"<tr><td>集水面积(km2)：</td><td>"+(result.data[0].dena==null?'':result.data[0].dena)+"</td></tr>"+					
					"<tr><td>站址：</td><td>"+result.data[0].stlc+"</td></tr>"+
					"<tr><td>测站名称：</td><td>"+result.data[0].stnm+"</td></tr>"+		
					"<tr><td>河流名称：</td><td>"+result.data[0].rvnm+"</td></tr>"+		
					"<tr><td>信息管理单位：</td><td>"+result.data[0].admauth+"</td></tr>"+		
				"</tbody></table></div>"];
				infoWindow = new AMap.InfoWindow({
					content: content.join("<br>")
				});
		
				// 打开信息窗体
				infoWindow.open(map, e.target.get("position"));
			}
		})
	}
//	大气	
	var airmarker=[];
//	测试数据
	var aqidata=[{
		addr:"浪拨村探测点",
		co:"0.6",
		no2:"23",
		o3:"18",
		so2:"5",
		pm10:"54",
		
	},{
		addr:"正果镇探测点",
		co:"0.6",
		no2:"44",
		o3:"52",
		so2:"5",
		pm10:"29",
		
	},{
		addr:"石溪村探测点",
		co:"0.5",
		no2:"46",
		o3:"59",
		so2:"5",
		pm10:"52",
		
	}]
	
    function getair(){
    	airmarker=[];
		$("#air_btn").removeClass("active")
		$("#air_btn").parents("a").addClass("nopointer")
//  	$.ajax({
//          url: '/api/aqi/aqiDevice/getByName',
//          dataType: 'json',
//          type: 'post',
//          success: function (result) {
//				$.each(result,function(idx,item) {
//					$.ajax({
//						url: '/api/aqi/aqiDevice/getDeviceDataById',
//						dataType: 'json',
//						type: 'post',
//						data: {
//							id: item.id
//						},
//						success: function(data) {
//							var co,no2,o3,so2,pm10,img;
//							if(data.deviceData.data[0]){
//			                    co=data.deviceData.data[0].co;
//				                no2=data.deviceData.data[0].no2;
//				                o3=data.deviceData.data[0].o3;
//				                so2=data.deviceData.data[0].so2;
//				                pm10=data.deviceData.data[0].pm10;
//				                img="images/air_1.png";
//				                if(co>10||no2>200||so2>500||pm10>75){
//							    	img="images/air_2.png";
////							    	return;
//							    }
//							}else{
//								co="";
//				                no2="";
//				                o3="";
//				                so2="";
//				                pm10="";
//				                img="images/air_2.png";
//							}
//							
////						    
//						    
//							var marker=new AMap.Marker({
//								position:new AMap.LngLat(item.lon_lat.split(',')[0],item.lon_lat.split(',')[1]),
//								offset:new AMap.Pixel(-10,-10),
//								icon:img,
//								data:data,
//								lnglat:[item.lon_lat.split(',')[0],item.lon_lat.split(',')[1]]
//							})
//							map.add(marker);
//							airmarker.push(marker);
//							marker.on('click',getairInfo);
//							
//						}
//					})
////					var marker=new AMap.Marker({
////						position:new AMap.LngLat(item.lon_lat.split(',')[0],item.lon_lat.split(',')[1]),
////						offset:new AMap.Pixel(-10,-10),
////						icon:"images/air_1.png",
////						data:aqidata[idx],
////						lnglat:[item.lon_lat.split(',')[0],item.lon_lat.split(',')[1]]
////					})
////					map.add(marker);
////					airmarker.push(marker);
////					marker.on('click',getairInfo);
//					
//			   });
////              map.setCenter([result[0].lon_lat.split(',')[0],result[0].lon_lat.split(',')[1]])
//          }
//      })


		
		$.ajax({
            url: '/api/aqi/aqiDevice/getByName',
            dataType: 'json',
            type: 'post',
            success: function (result) {
            	var airid=[];
            	var airlonlan=[];
            	$.each(result,function(idx,item) {
            		if(item.id){
            			airid.push(item.id.toString())
            			airlonlan.push(item.lon_lat)   			
            		}
            		if((idx+1)==result.length){
            			$.ajax({
							url: '/api/aqi/aqiDevice/getDeviceDataByBatchIds',
							dataType: 'json',
							type: 'post',
							contentType:"application/json;charset=utf-8",	
		//					data:{
		//						ids:JSON.stringify(airid),
		//					} ,
							data: JSON.stringify({ "ids":airid}),
							success: function(res) {
//								console.log(res)
    							$("#air_btn").addClass("active")
								$("#air_btn").parent("a").removeClass("nopointer")
								var co,no2,o3,so2,pm10,img;
								var i=0;
								$.each(res,function(index,data) {
									if(data.deviceData[0]){
					                    co=data.deviceData[0].co||"";
						                no2=data.deviceData[0].no2||"";
						                o3=data.deviceData[0].o3||"";
						                so2=data.deviceData[0].so2||"";
						                pm10=data.deviceData[0].pm10||"";
						                img="images/air_1.png";
						                if(co>10||no2>200||so2>500||pm10>75){
									    	img="images/air_2.png";
			//							    	return;
									    }
									}else{
										co="";
						                no2="";
						                o3="";
						                so2="";
						                pm10="";
						                img="images/air_2.png";
									}
									var latLon=GPS.WGStoGCJ(airlonlan[i].split(',')[1],airlonlan[i].split(',')[0]);
									var marker=new AMap.Marker({
										position:new AMap.LngLat(latLon.lat,latLon.lon),
										offset:new AMap.Pixel(-10,-10),
										icon:img,
										data:data
									})
									map.add(marker);
									airmarker.push(marker);
									marker.on('click',getairInfo);
									
									i++;
				            	})
								
							}
						})
            		}
            	})
            	
				
            }
        })
    }
	getair();
    $("#air_btn").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(airmarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getair();
    	}
	})
    
	var getairInfo=function(e){
		var data=e.target.get("data");
//	    var lnglat=e.target.get("lnglat");
	    
	    if(data.deviceData[0]){
            co=data.deviceData[0].co||"";
            no2=data.deviceData[0].no2||"";
            o3=data.deviceData[0].o3||"";
            so2=data.deviceData[0].so2||"";
            pm10=data.deviceData[0].pm10||"";
		}else{
			co="";
            no2="";
            o3="";
            so2="";
            pm10="";
		}
	    
		var content=["<p class='pop_tl'>空气监测</p><div class='pop_box'><table></tbody class>"+
			"<tr><td style='width:115px;'>地址：</td><td>"+data.deviceInfo.positionname+"</td></tr>"+
			"<tr><td>经纬度：</td><td>"+data.deviceInfo.positionxy+"</td></tr>"+
			"<tr><td>CO：</td><td>"+(co?co:'')+"</td></tr>"+		
			"<tr><td>NO2：</td><td>"+(no2?no2:'')+"</td></tr>"+					
			"<tr><td>O3：</td><td>"+(o3?o3:'')+"</td></tr>"+
			"<tr><td>SO2：</td><td>"+(so2?so2:'')+"</td></tr>"+		
			"<tr><td>PM10：</td><td>"+(pm10?pm10:'')+"</td></tr>"+		
		"</tbody></table></div>"];
		
//		var content=["<p class='pop_tl'>空气监测</p><div class='pop_box'><table></tbody class>"+		
//			"<tr><td style='width:115px;'>CO：</td><td>"+data.co+"</td></tr>"+		
//			"<tr><td>NO2：</td><td>"+data.no2+"</td></tr>"+					
//			"<tr><td>O3：</td><td>"+data.o3+"</td></tr>"+
//			"<tr><td>SO2：</td><td>"+data.so2+"</td></tr>"+		
//			"<tr><td>PM10：</td><td>"+data.pm10+"</td></tr>"+		
//		"</tbody></table></div>"];
		
		infoWindow = new AMap.InfoWindow({
			content: content.join("<br>")
		});

		// 打开信息窗体
		infoWindow.open(map, e.target.get("position"));
	}
	
//	水质
	var watermarker=[];
    function getwater(){
    	watermarker=[];
		$("#water_btn").removeClass("active");					
		$("#water_btn").parent("a").addClass("nopointer")
    	$.ajax({
            url: 'http://113.108.176.67:8090/zghb/a/app/riverInterface/getPointInfo',
            dataType: 'json',
            type: 'get',
            success: function (result) {
//          	console.log(result)
    			$("#water_btn").addClass("active")				
				$("#water_btn").parent("a").removeClass("nopointer")
				$.each(result,function(idx,item) {
					var latLon=GPS.WGStoGCJ(item.point.cdLongitude,item.point.cdLatitude);
					 var marker=new AMap.Marker({
						position:new AMap.LngLat(latLon.lat,latLon.lon),
						offset:new AMap.Pixel(-10,-10),
						icon:'images/2_03.png',
						data:item
					})
					map.add(marker);
					watermarker.push(marker);
					marker.on('click',getwaterInfo);
			   });
//              map.setCenter([result[0].point.cdLongitude,result[0].point.cdLatitude])
            }
        })
    }
	getwater();
	$("#water_btn").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(watermarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getwater();
    	}
	})
	function getWaterLevel(o,n){
	    var oLevel;
	    var nLevel;
	    var LStr=['Ⅰ','Ⅱ','Ⅲ','Ⅳ','Ⅴ'];
	    oLevel = (o>=7.5 && 1) || (o>=6 && 2) || (o>=5 && 3) || (o>=3 && 4) ||(o>=2 && 5) || 5;
	    nLevel = (n<=0.15 && 1) || (n<=0.5 && 2) || (n<=1 && 3) || (n<=1.5 && 4) ||(n<=2 && 5) || 5;
	    if(oLevel>nLevel){
	        return [LStr[oLevel-1]+"级","溶解氧"];
        }else if(oLevel<nLevel){
	        return [LStr[nLevel-1]+"级","氨氮"];
        }else{
	        return [LStr[oLevel-1]+"级","溶解氧"];
        }
    }
	
	var getwaterInfo=function(e){
		var data=e.target.get("data");
		var id=data.point.pkid;
//	    var lnglat=[data.point.cdLongitude,data.point.cdLatitude];
	    var codeArr=[];
	    var valueArr=[];
	    $.ajax({
             url: 'http://113.108.176.67:8090/zghb/a/app/riverInterface/getCode',
             dataType: 'json',
             type: 'get',
			 success:function(data){
			 	console.log("---水质")
			 	console.log(data)
			 	$.each(data,function(index,item){
			 		var obj=new Object();
			         obj.name=item.label;
			         obj.code=item.value;
			 		codeArr.push(obj);
			 	})
			 }
         })
	    $.ajax({
			url: 'http://113.108.176.67:8090/zghb/a/app/riverInterface/getData',
			dataType: 'json',
			type: 'get',
			data: {
				pointid: id,
				type:'1'
			},
			success: function(result) {
				console.log(result)
				$.each(result.rows,function(index,item){
					$.each(codeArr, function(idx,itm) {
						if(item.code==itm.code){
							var obj=new Object();
	                        obj.name=itm.name;
	                        obj.value=item.rtd;
	                        valueArr.push(obj);
						}
					});
				})
//				
				var waterL=getWaterLevel(valueArr[0].value,valueArr[7].value);
				
				
				var content=["<p class='pop_tl'>水质监测</p><div class='pop_box'>"+
				"<table></tbody class>"+
					"<tr><td style='width:115px;'>监测站名称：</td><td>"+result.rows[0].cdName+"</td></tr>"+
					"<tr><td>经纬度：</td><td>"+e.target.get("position").lat+","+e.target.get("position").lng+"</td></tr>"+
					"<tr><td>监测时间：</td><td>"+result.rows[0].time+"</td></tr>"+					
					"<tr><td>氨氮(mg/L)：</td><td>"+valueArr[7].value+"</td></tr>"+		
					"<tr><td>电导率：</td><td>"+valueArr[4].value+"</td></tr>"+		
					"<tr><td>悬浮物(mg/L)：</td><td>"+valueArr[6].value+"</td></tr>"+		
					"<tr><td>水温(℃)：</td><td>"+valueArr[1].value+"</td></tr>"+		
					"<tr><td>溶解氧(mg/L)：</td><td>"+valueArr[0].value+"</td></tr>"+		
					"<tr><td>PH值：</td><td>"+valueArr[5].value+"</td></tr>"+		
					"<tr><td>站房湿度(%)：</td><td>"+valueArr[2].value+"</td></tr>"+		
					"<tr><td>站房温度(℃)：</td><td>"+valueArr[3].value+"</td></tr>"+		
					"<tr><td>等级：</td><td>"+waterL[0]+"</td></tr>"+		
					"<tr><td>首要污染物：</td><td>"+waterL[1]+"</td></tr>"+
				"</tbody></table></div>"];
				
				
				infoWindow = new AMap.InfoWindow({
					content: content.join("<br>")
				});
		
				// 打开信息窗体
				infoWindow.open(map, e.target.get("position"));
			}
		})
	}
	
//  路灯
	var lampmarker=[];
    function getlamp(){
    	lampmarker=[];
		$("#lamp_btn").removeClass("active")
		$("#lamp_btn").parent("a").addClass("nopointer")
    	$.ajax({
            url: '/api/lamp/terminalBox/getDeviceByParam',
            dataType: 'json',
            type: 'get',
            success: function (result) {
				$("#lamp_btn").addClass("active")
				$("#lamp_btn").parent("a").removeClass("nopointer")
				$.each(result,function(idx,item) {					
					var latLon=GPS.WGStoGCJ(item.lon_lat.split(',')[1],item.lon_lat.split(',')[0]);
					var marker=new AMap.Marker({
						position:new AMap.LngLat(latLon.lat,latLon.lon),
						offset:new AMap.Pixel(-10,-10),
						icon:'images/2_04.png',
						data:item
					})
					map.add(marker);
					lampmarker.push(marker);
					marker.on('click',getlampInfo);
			   });
//              map.setCenter([result[0].lon_lat.split(',')[1],result[0].lon_lat.split(',')[0]])
            }
        })
    }
	getlamp();
	$("#lamp_btn").on("click", function() {    	
    	if($(this).hasClass("active")){
    		$.each(lampmarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getlamp();
    	}
	})
	var getlampInfo=function(e){
		var data=e.target.get("data");
//	    var lnglat=[data.lon_lat.split(',')[1],data.lon_lat.split(',')[0]];
	    $.ajax({
			url: '/api/lamp/terminalBox/getDeviceDetailById',
			dataType: 'json',
			type: 'get',
			data: {
				id: data.id
			},
			success: function(result) {
				var terminalBoxNo,roadName,address,areaName
				if(result.data[0]){
					terminalBoxNo=result.data[0].terminalBoxNo;
					roadName=result.data[0].roadName;
					address=result.data[0].address;
					areaName=result.data[0].areaName;
					terminalBoxName=result.data[0].terminalBoxName;
					
				}else{
					terminalBoxNo="";
					roadName="";
					address="";
					areaName="";
					terminalBoxName=""
				}
				var content=["<p class='pop_tl'>智慧路灯</p><div class='pop_box'><table></tbody class>"+						
					"<tr><td style='width:90px;'>终端箱编号：</td><td>"+terminalBoxNo+"</td></tr>"+						
					"<tr><td>终端箱名称：</td><td>"+terminalBoxName+"</td></tr>"+
					"<tr><td>经纬度：</td><td>"+e.target.get("position").lat+","+e.target.get("position").lng+"</td></tr>"+
					"<tr><td>道路名称：</td><td>"+roadName+"</td></tr>"+	
					"<tr><td>地址：</td><td>"+address+"</td></tr>"+		
					"<tr><td>片区名称：</td><td>"+areaName+"</td></tr>"+
					"<tr><td>责任人及电话：</td><td></td></tr>"+					
				"</tbody></table></div>"];
				infoWindow = new AMap.InfoWindow({
					content: content.join("<br>")
				});
		
				// 打开信息窗体
				infoWindow.open(map, e.target.get("position"));
			}
		})
	}
    
//  消防
	var firemarker=[];
    function getfire(){    	
    	firemarker=[];
    	$("#fire_btn").removeClass("active")
		$("#fire_btn").parent("a").addClass("nopointer")
    	$.ajax({
            url: '/api/fire/electricDeviceInfo/getDeviceByParam',
            dataType: 'json',
            type: 'get',
            success: function (result) {
            	var fireid=[];
				$.each(result,function(idx,item) {
					if(item.id){
            			fireid.push(item.id.toString())		
            		}
					if((idx+1)==result.length){
						$.ajax({
							url: '/api/fire/electricDeviceInfo/getDeviceDataByBatchIds_simple',
							dataType: 'json',
							type: 'post',
							contentType:"application/json;charset=utf-8",
							data: JSON.stringify({ "ids":fireid}),
							success: function(alldata) {
								$("#fire_btn").addClass("active")								
								$("#fire_btn").parent("a").removeClass("nopointer")
								
								var info={};
								$.each(alldata.data,function(index,data){
									info[data.sn]=data								
								})
								$.each(alldata.detail.FireWaterCycleV22,function(index,data){
							    	var img="images/fire_2.png";
				                   	var batteryvalue=data.batteryvalue||"";
							    	var signal=data.signal||"";
							    	
							    	var channel1type=data.channel1type;
									var channel1=data.channel1||"";
									switch(channel1type){
								        case 0:
								        	if(channel1==""){
								        		break;
								        	}
								        	if(channel1<100||channel1>1500){
								        		img="images/fire_1.png";
								        	}
								        	break;
//							        	case 1:
//								        	if(channel1>4500){
//								        		img="images/fire_1.png";
//								        	}
//								        	break;
									}
									
//									var channel2type=data.channel2type;
//									var channel2=data.channel2||"";
//									switch(channel2type){
//								        case 0:
//								        	if(channel2==""){
//								        		break;
//								        	}
//								        	if(channel2<100||channel2>1500){
//								        		img="images/fire_1.png";
//								        	}
//								        	break;
////							        	case 1:
////							        		if(channel2>4500){
////								        		img="images/fire_1.png";
////								        	}
////								        	break;
//									}
							    	
//							    	if(batteryvalue!=""&&batteryvalue>50000){
//								    	img="images/fire_1.png";
//								    }
//							    	if(signal!=""&&signal<10){
//								    	img="images/fire_1.png";
//								    }
								    
									    var datainfo=info[data.devicecode];
										var latLon=GPS.WGStoGCJ(datainfo.gps.split(',')[1],datainfo.gps.split(',')[0]);
										var marker=new AMap.Marker({
											position:new AMap.LngLat(latLon.lat,latLon.lon),
											offset:new AMap.Pixel(-10,-10),
											icon:img,
											data:data,
											datainfo:datainfo
										})
										map.add(marker);
										firemarker.push(marker);
										marker.on('click',getfireInfo);
								})
							}
						})
					}
			  });
            }
        })
    }
	getfire();
	$("#fire_btn").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(firemarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getfire();
    	}
	})
	var getfireInfo=function(e){
		var data=e.target.get("data");
		var datainfo=e.target.get("datainfo");
//			var pressure=data.pressure||"";
//			var level=data.level;
		var channel1type=data.channel1type;
		var channel1=data.channel1||"";
		switch(channel1type){
	        case 0:
	        	channel1=channel1+"(kPa)";
	        	break;
//      	case 1:
//	        	channel1="液位"+channel1+"(cm)";
//	        	break;
	        default:
	        	channel1="";
		}
		
		var channel2type=data.channel2type;
		var channel2=data.channel2||"";
		switch(channel2type){
	        case 0:
	        	channel2="压力"+channel2+"(kPa)";
	        	break;
//      	case 1:
//	        	channel2="液位"+channel2+"(cm)";
//	        	break;
	        default:
	        	channel1="";
		}
		
        var batteryvalue=data.batteryvalue;
    	var signal=data.signal;
    	var position=e.target.get("position")
		var content=["<p class='pop_tl'>消防栓</p><div class='pop_box'><table></tbody class>"+		
			"<tr><td style='width:80px;'>SN：</td><td>"+datainfo.sn+"</td></tr>"+	
			"<tr><td>地址：</td><td>"+datainfo.address+"</td></tr>"+	
			"<tr><td>经纬度：</td><td>"+position.lat+","+position.lng+"</td></tr>"+	
//			"<tr><td>压力(kPa)：</td><td>"+(pressure?pressure:'')+"</td></tr>"+		
//			"<tr><td>液位(cm)：</td><td>"+(level?level:'')+"</td></tr>"+					
			"<tr><td>通道压力</td><td>"+channel1+"</td></tr>"+
//			"<tr><td>通道2</td><td>"+channel2+"</td></tr>"+
			"<tr><td>电压：</td><td>"+(batteryvalue||'')+"(mV)</td></tr>"+					
			"<tr><td>信号强度：</td><td>"+(signal||'')+"</td></tr>"+		
		"</tbody></table></div>"];
		infoWindow = new AMap.InfoWindow({
			content: content.join("<br>")
		});
//		
//				// 打开信息窗体
		infoWindow.open(map, e.target.get("position"));
	}
	
//	应急指挥车
	var carmarker=[];
    function getemcar(){
    	$("#em_car").removeClass("active")
		$("#em_car").parent("a").addClass("nopointer")
		var latLon=GPS.WGStoGCJ(113.893289,23.417167);						
		var marker=new AMap.Marker({
			position:new AMap.LngLat(latLon.lat,latLon.lon),
			offset:new AMap.Pixel(-10,-10),
			icon:'images/car.png'
		})
		map.add(marker);
		carmarker.push(marker);
		marker.on('click',getcarInfo);	
		$("#em_car").addClass("active")
		$("#em_car").parent("a").removeClass("nopointer")
    }
    var getcarInfo=function(e){
		var content=["<p class='pop_tl'> 应急指挥车：</p><p class='pop_tl'> 地址：正果镇政府</p><div class='pop_box'><table class='table-bordered'>"+
		"<thead class><tr><td>值班人员</td><td> 联系电话</td></tr></thead>"+
		"<tbody class>"+
			"<tr><td style='width:90px;'>钟敏健</td><td>13710285262</td></tr>"+
			"<tr><td style='width:90px;'>邹金宏</td><td>13602736432</td></tr>"+
			"<tr><td style='width:90px;'>张毅锵</td><td>18218637904</td></tr>"+
		"</tbody></table></div>"];
		infoWindow = new AMap.InfoWindow({
			content: content.join("<br>")
		});
		infoWindow.open(map,e.target.get("position"));
	}
    
	getemcar();
	$("#em_car").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(carmarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getemcar();
    	}
	})
//	摄像头
	var cameramarker=[];
    function getcamera(){
    	cameramarker=[];
    	$("#camera").removeClass("active")
		$("#camera").parent("a").addClass("nopointer")
    	$.ajax({
            url: 'plugin/camera.json',
            dataType: 'json',
            type: 'get',
            success: function (result) {
            	
    			$("#camera").addClass("active")
				$("#camera").parent("a").removeClass("nopointer")
            	result=result.monitorInfoListJson;
				$.each(result,function(idx,item) {
					if(item.longitude!=""&&item.latitude!=""){
						
						var latLon=GPS.WGStoGCJ(item.latitude,item.longitude);						
						var marker=new AMap.Marker({
							position:new AMap.LngLat(latLon.lon,latLon.lat),
							offset:new AMap.Pixel(-10,-10),
							icon:'images/video.png',
							data:item
						})
						map.add(marker);
						cameramarker.push(marker);
						marker.on('click',getcameraInfo);						
					}
			 	});
            }
        })
    }
	getcamera();
	$("#camera").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(cameramarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getcamera();
    	}
	})
	var getcameraInfo=function(e){
		var data=e.target.get("data");
		var content=["<p class='pop_tl'>监控视频："+data.id+"</p><div class='pop_box'><table></tbody class>"+		
			"<tr><td style='width:60px;'>地址：</td><td>"+(data.passage_nmae||"")+"</td></tr>"+
			"<tr><td style='width:60px;'>设备类型：</td><td>"+(data.equip_type||"")+"</td></tr>"+
			"<tr><td style='width:60px;'>经纬度：</td><td>"+data.latitude+","+data.longitude+"</td></tr>"+
			"<tr><td style='width:60px;'>ip地址：</td><td>"+(data.ip||"")+"</td></tr>"+	
		"</tbody></table></div>"+
		"<div class=''><iframe style='border:none' src='http://172.27.162.39:10800/play.html?channel="+data.number+"&iframe=yes&aspect=640x360' width='650' height='370' allowfullscreen allow='autoplay' id='videoFrame'></iframe></div"];
		infoWindow = new AMap.InfoWindow({
			content: content.join("<br>")
		});
		infoWindow.open(map,e.target.get("position"));
	}
	
	
//	健康小屋
	var hutmarker=[];
    function gethut(center){
    	hutmarker=[];
    	$("#hut").removeClass("active")
		$("#hut").parent("a").addClass("nopointer")
    	$.ajax({
            url: '/api/healthy/deviceInfo/getByName',
            dataType: 'json',
            type: 'post',
            success: function (result) {
            	
				$("#hut").addClass("active")
				$("#hut").parent("a").removeClass("nopointer")
				$.each(result,function(idx,item) {
                    var latLon=GPS.WGStoGCJ(item.lon_lat.split(',')[1],item.lon_lat.split(',')[0]);
                    var marker=new AMap.Marker({
                        position:new AMap.LngLat(latLon.lat,latLon.lon),
                        offset:new AMap.Pixel(-10,-10),
                        icon:'images/hut.png',
                        data:item
                    })
                    map.add(marker);
                    hutmarker.push(marker);
                    marker.on('click',gethutInfo);

                });
				if(center==1){
					map.setCenter([result[0].lon_lat.split(',')[1],result[0].lon_lat.split(',')[0]])					
				}
            }
        })
    }
	gethut();
	$("#hut").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(hutmarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		gethut(1);
    	}
	})
	var gethutInfo=function(e){
		var data=e.target.get("data");
//		console.log(data)
//	    var lnglat=[data.lon_lat.split(',')[1],data.lon_lat.split(',')[0]];
	    $.ajax({
			url: '/api/healthy/deviceInfo/getDeviceDataById',
			dataType: 'json',
			type: 'POST',
			data: {
				id: data.id
			},
			success: function(result) {
				var chol,highdensity,trig,lowdensity;				
				if(result.deviceData.HealthyBloodFat[0]){
                	chol=result.deviceData.HealthyBloodFat[0].chol;
                	highdensity=result.deviceData.HealthyBloodFat[0].highdensity;
                	trig=result.deviceData.HealthyBloodFat[0].trig;
                	lowdensity=result.deviceData.HealthyBloodFat[0].lowdensity;
                }else{
                	chol=0;
                	highdensity=0;
                	trig=0;
                	lowdensity=0;
                }
                
                var bloodoxygen,pulse;				
				if(result.deviceData.HealthyBloodOxygen[0]){
                	bloodoxygen=result.deviceData.HealthyBloodOxygen[0].bloodoxygen;
                	pulse=result.deviceData.HealthyBloodOxygen[0].pulse;
                }else{
                	bloodoxygen=0;
                	pulse=0;
                }
                
                var systolic,diastolic,pulse;				
				if(result.deviceData.HealthyBloodPressure[0]){
                	systolic=result.deviceData.HealthyBloodPressure[0].systolic;
                	diastolic=result.deviceData.HealthyBloodPressure[0].diastolic;
                	pulse=result.deviceData.HealthyBloodPressure[0].pulse;
                }else{
                	systolic=0;
                	diastolic=0;
                	pulse=0;
                }
                var temperature;				
				if(result.deviceData.HealthyTemperature[0]){
                	temperature=result.deviceData.HealthyTemperature[0].temperature;
                }else{
                	temperature=0;
                }
                
				var content=["<p class='pop_tl'>健康小屋</p><div class='pop_box'><table></tbody class>"+		
					"<tr><td style='width:85px;'>名称：</td><td>"+(result.deviceInfo.name)+"</td></tr>"+		
					"<tr><td>地址：</td><td>"+(result.deviceInfo.address==null?'':result.deviceInfo.address)+"</td></tr>"+					
					"<tr><td>设备状态：</td><td>"+result.deviceInfo.deviceState+"</td></tr>"+
					"<tr><td>设备id：</td><td>"+(result.deviceInfo.deviceId||"")+"</td></tr>"+
					"<tr><td>经纬度：</td><td>"+e.lnglat.lat+","+e.lnglat.lng+"</td></tr>"+
					"<table class='table-bordered'>	"+				
						"</tbody><tr><td style='width:60px;' rowspan='5'>血脂数据</td></tr>"+
							"<tr><td width='120px'>总胆固醇</td>"+	
							"	<td>"+chol+"</td></tr>"+	
							"<tr><td>高密度脂蛋白</td>"+	
							"	<td>"+highdensity+"</td></tr>"+
							"<tr><td>甘油三酯</td>"+	
							"	<td>"+trig+"</td></tr>"+
							"<tr><td>低密度脂蛋白</td>"+	
							"	<td>"+lowdensity+"</td></tr>"+
						"</tbody></table>"+
					"<table class='table-bordered'>	"+				
						"</tbody><tr><td style='width:60px;' rowspan='3'>血氧数据</td></tr>"+
							"<tr><td width='120px'>血氧</td>"+	
							"	<td>"+bloodoxygen+"</td></tr>"+	
							"<tr><td>脉搏</td>"+	
							"	<td>"+pulse+"</td></tr>"+
						"</tbody></table>"+
					"<table class='table-bordered'>	"+				
						"</tbody><tr><td style='width:60px;' rowspan='4'>血压数据</td></tr>"+
							"<tr><td width='120px'>收缩压</td>"+	
							"	<td>"+systolic+"</td></tr>"+	
							"<tr><td>舒张压</td>"+	
							"	<td>"+diastolic+"</td></tr>"+
							"<tr><td>脉搏</td>"+	
							"	<td>"+pulse+"</td></tr>"+
						"</tbody></table>"+
					"<table class='table-bordered'>	"+				
						"</tbody><tr><td style='width:60px;' rowspan='2'>体温数据</td></tr>"+
							"<tr><td width='120px'>体温</td>"+	
							"	<td>"+temperature+"</td></tr>"+	
						"</tbody></table>"+
					"</div>"];
				infoWindow = new AMap.InfoWindow({
					content: content.join("<br>")
				});
		
				// 打开信息窗体
				infoWindow.open(map, e.target.get("position"));
			}
		})
	}
	
	
//	特殊人群
	var groupmarker=[];
    function getgroup(){
    	groupmarker=[];
    	
		$("#sGroup").removeClass("active")
		$("#sGroup").parent("a").addClass("nopointer")
//  	$.ajax({
//          url: '/api/special/populationPosition/getDeviceByParam',
//          dataType: 'json',
//          type: 'Get',
//          data:{
//          	name:"",
//          	status:""
//          },
//          success: function (result) {
//				$.each(result,function(idx,item) {
//					$.ajax({
//						url: '/api/special/populationPosition/getDeviceDetailById',
//						dataType: 'json',
//						type: 'get',
//						data: {
//							id: item.id
//						},
//						success: function(data) {
////							console.log("---特殊人群")
////							console.log(data)
//							var heatRate,thesholdHeartrateH,thesholdHeartrateL,walk,img;							
//							img="images/group_1.png";
//							
//							if(data.detail.Walk[0]){								
//							    walk=data.detail.Walk[data.detail.Walk.length-1].value;
//							}else{								
//								walk=0;
//							}
//							if(data.detail.BloodPressure[0]){								
//							    thesholdHeartrateH=data.detail.BloodPressure[0].dbp?data.detail.BloodPressure[0].dbp:0;
//							    thesholdHeartrateL=data.detail.BloodPressure[0].dbpL?data.detail.BloodPressure[0].dbpL:0;
//							}else{							
//								thesholdHeartrateH=0;
//								thesholdHeartrateL=0;
//							}
//							if(data.detail.Heart[0]){
//								heatRate=data.detail.Heart[0].heartrate;							    
//							}else{
//								heatRate=0;
//							}
//							
//						    if(heatRate<40||heatRate>140||thesholdHeartrateH>140||thesholdHeartrateL<60){
//						    	img="images/group_2.png";
//						    }
//							var latLon=GPS.WGStoGCJ(item.lon_lat.split(',')[1],item.lon_lat.split(',')[0]);
//						    var marker=new AMap.Marker({
//								position:new AMap.LngLat(latLon.lat,latLon.lon),
//								offset:new AMap.Pixel(-10,-10),
//								icon:img,
//								data:data
//							})
//							map.add(marker);
//							groupmarker.push(marker);
//							marker.on('click',getgroupInfo);
//							
//						}
//					})
//									
//				});
//          }
//      })
		$.ajax({
            url: '/api/special/populationPosition/getDeviceByParam',
            dataType: 'json',
            type: 'Get',
            data:{
            	name:"",
            	status:""
            },
            success: function (result) {
            	var groupid=[]
				$.each(result,function(idx,item) {
					if(item.id){
            			groupid.push(item.id.toString())			
            		}
					if(idx==(result.length-1)){
						$.ajax({
							url: '/api/special/populationPosition/getDeviceDataByBatchIds',
							dataType: 'json',
							type: 'post',
                            async: false,
							contentType:"application/json;charset=utf-8",
							data: JSON.stringify({ "ids":groupid}),
							success: function(alldata) {
//								console.log("---特殊人群")
//								console.log(alldata)
    							$("#sGroup").addClass("active")
								$("#sGroup").parent("a").removeClass("nopointer")
								$.each(alldata,function(index,data){
									var heatRate,thesholdHeartrateH,thesholdHeartrateL,walk,img;							
									img="images/group_1.png";
									
									if(data.detail.Walk[0]){								
									    walk=data.detail.Walk[data.detail.Walk.length-1].value;
									}else{								
										walk=0;
									}
									if(data.detail.BloodPressure[0]){								
									    thesholdHeartrateH=data.detail.BloodPressure[0].dbp?data.detail.BloodPressure[0].dbp:0;
									    thesholdHeartrateL=data.detail.BloodPressure[0].dbpL?data.detail.BloodPressure[0].dbpL:0;
									    if(thesholdHeartrateH>140||thesholdHeartrateL<60){
									    	img="images/group_2.png";
									    }
									}else{							
										thesholdHeartrateH=0;
										thesholdHeartrateL=0;
									}
									if(data.detail.Heart[0]){
										heatRate=data.detail.Heart[0].heartrate;
										if(heatRate<40||heatRate>140){
									    	img="images/group_2.png";
									    }
									}else{
										heatRate=0;
									}
									
//								    if(heatRate<40||heatRate>140||thesholdHeartrateH>140||thesholdHeartrateL<60){
//								    	img="images/group_2.png";
//								    }
								    var latLon=GPS.WGStoGCJ(item.lon_lat.split(',')[1],item.lon_lat.split(',')[0]);
								    var latLon=GPS.WGStoGCJ(data.data[0].lon_lat.split(',')[1],data.data[0].lon_lat.split(',')[0]);
								    var marker=new AMap.Marker({
										position:new AMap.LngLat(latLon.lat,latLon.lon),
										offset:new AMap.Pixel(-10,-10),
										icon:img,
										data:data
									})
									map.add(marker);
									groupmarker.push(marker);
									marker.on('click',getgroupInfo);
								})
							}
						})
					}				
				});
            }
        })
    }
	getgroup();
	$("#sGroup").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(groupmarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getgroup();
    	}
	})

	var getgroupInfo=function(e){
		var data=e.target.get("data");
		console.log(data)
//	    var lnglat=[data.data[0].lon_lat.split(',')[1],data.data[0].lon_lat.split(',')[0]];
	    var heatRate,thesholdHeartrateH,thesholdHeartrateL,walk
	    
	    if(data.detail.Walk[0]){								
		    walk=data.detail.Walk[data.detail.Walk.length-1].value;
		}else{								
			walk="";
		}
		if(data.detail.BloodPressure[0]){								
		    thesholdHeartrateH=data.detail.BloodPressure[0].dbp?data.detail.BloodPressure[0].dbp:"";
		    thesholdHeartrateL=data.detail.BloodPressure[0].dbpL?data.detail.BloodPressure[0].dbpL:"";
		}else{							
			thesholdHeartrateH="";
			thesholdHeartrateL="";
		}
		if(data.detail.Heart[0]){
			heatRate=data.detail.Heart[0].heartrate;							    
		}else{
			heatRate="";
		}
		
//	    var walk=data.detail.Walk[data.detail.Walk.length-1].value;	    	
		
		var content=["<p class='pop_tl'>特殊人群</p><div class='pop_box'><table></tbody class>"+
					
			"<tr><td style='width:85px;'>居住地址：</td><td>"+data.data[0].community+"</td></tr>"+
			"<tr><td style='width:85px;'>经纬度：</td><td>"+data.data[0].lon_lat+"</td></tr>"+
			"<tr><td style='width:85px;'>居住状况：</td><td>"+data.data[0].live+"</td></tr>"+		
			"<tr><td style='width:85px;'>姓名：</td><td>"+data.data[0].wearerName+"</td></tr>"+	
			"<tr><td style='width:85px;'>电话：</td><td>"+data.data[0].wearerMobile+"</td></tr>"+
			
			"<tr><td style='width:85px;'>心率：</td><td>"+heatRate+"</td></tr>"+		
			"<tr><td>高血压：</td><td>"+thesholdHeartrateH+"</td></tr>"+					
			"<tr><td>低血压：</td><td>"+thesholdHeartrateL+"</td></tr>"+	
			"<tr><td>步行步数：</td><td>"+walk+"</td></tr>"+
		"</tbody></table></div>"];
		infoWindow = new AMap.InfoWindow({
			content: content.join("<br>")
		});
		infoWindow.open(map, e.target.get("position"));
		
		console.log(data)
	}
	
		
//	行为识别
	var behaviormarker=[];
    function getbehavior(){
    	behaviormarker=[];
		$("#behavior").removeClass("active")
		$("#behavior").parent("a").addClass("nopointer")
    	$.ajax({
            url: '/api/device/netDeviceInfo/getDeviceByParam',
            dataType: 'json',
            type: 'get',
            data:{
            	name:"",
            	status:""
            },
            success: function (result) {
    			$("#behavior").addClass("active")
				$("#behavior").parent("a").removeClass("nopointer")
				$.each(result,function(idx,item) {
					
					var latLon=GPS.WGStoGCJ(item.lon_lat.split(',')[1],item.lon_lat.split(',')[0]);
					 var marker=new AMap.Marker({
						position:new AMap.LngLat(latLon.lat,latLon.lon),
						offset:new AMap.Pixel(-10,-10),
						icon:'images/behavior.png',
						data:item
					})
					map.add(marker);
					behaviormarker.push(marker);
					marker.on('click',getbehaviorInfo);
					
				});
//           	map.setCenter([result[0].lon_lat.split(',')[1],result[0].lon_lat.split(',')[0]])
            }
        })
    }
	getbehavior();
	$("#behavior").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(behaviormarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getbehavior();
    	}
	})
	var getbehaviorInfo=function(e){
		var data=e.target.get("data");
//	    var lnglat=[data.lon_lat.split(',')[1],data.lon_lat.split(',')[0]];
	    $.ajax({
			url: '/api/device/netDeviceInfo/getDeviceDetailById',
			dataType: 'json',
			type: 'get',
			data: {
				id: data.id
			},
			success: function(result) {	
                var cameraname=result.data[0].cameraname;
                var address=result.data[0].address;
                var village=result.data[0].village;
                var Rioternum,Rioterrenum;
                var Crosslinenum,Crosslinerenum;
                var Leftnum,Leftrenum;
                var Parkingnum,Parkingrrenum;
                var Wandernum,Wanderrenum;
                if(result.detail[0].Crossline[0]){
                	Crosslinenum=result.detail[0].Crossline[0].nobjectnum||"";
                	Crosslinerenum=result.detail[0].Crossline[0].ndetectlinenum||"";
                }else{
                	Crosslinenum=0;
                	Crosslinerenum=0;
                }
                if(result.detail[0].Left[0]){
                	Leftnum=result.detail[0].Left[0].nobjectnum||"";
                	Leftrenum=result.detail[0].Left[0].ndetectlinenum||"";
                }else{
                	Leftnum=0;
                	Leftrenum=0;
                }            
                if(result.detail[0].Parkingdete[0]){
                	Parkingnum=result.detail[0].Parkingdete[0].nobjectnum||"";
                	Parkingrrenum=result.detail[0].Parkingdete[0].ndetectlinenum||"";
                }else{
                	Parkingnum=0;
                	Parkingrrenum=0;
                }
                if(result.detail[0].Rioter[0]){
                	Rioternum=result.detail[0].Rioter[0].nobjectnum||"";
                	Rioterrenum=result.detail[0].Rioter[0].ndetectregionnum||"";
                }else{
                	Rioternum=0;
                	Rioterrenum=0;
                }                                  
                if(result.detail[0].Parkingdete[0]){
                	Wandernum=result.detail[0].Parkingdete[0].nobjectnum||"";
                	Wanderrenum=result.detail[0].Parkingdete[0].ndetectregionnum||"";
                }else{
                	Wandernum=0;
                	Wanderrenum=0;
                }
                
				var content=["<p class='pop_tl'>行为识别</p><div class='pop_box'><table></tbody class>"+		
					"<tr><td style='width:85px;'>摄像机名称：</td><td>"+cameraname+"</td></tr>"+		
					"<tr><td>地址：</td><td>"+(address?address:'')+"</td></tr>"+						
					"<tr><td>经纬度：</td><td>"+data.lon_lat+"</td></tr>"+	
					"<tr><td>村庄：</td><td>"+village+"</td></tr>"+	
				"</tbody></table>"+
				"<table class='table-bordered'>	"+				
					"</tbody><tr><td style='width:60px;' rowspan='3'>警戒线</td></tr>"+
						"<tr><td width='145px'>检测到的物体个数</td>"+	
						"	<td>"+Crosslinenum+"</td></tr>"+	
						"<tr><td>规则检测区域顶点数</td>"+	
						"	<td>"+Crosslinerenum+"</td></tr>"+
					"</tbody></table>"+
				"<table class='table-bordered'>	"+				
					"</tbody><tr><td style='width:60px;' rowspan='3'>物品遗失</td></tr>"+
						"<tr><td width='145px'>检测到的物体个数</td>"+	
						"	<td>"+Leftnum+"</td></tr>"+	
						"<tr><td>规则检测区域顶点数</td>"+	
						"	<td>"+Leftrenum+"</td></tr>"+
					"</tbody></table>"+
				"<table class='table-bordered'>	"+				
						"</tbody><tr><td style='width:60px;' rowspan='3'>非法停车</td></tr>"+
							"<tr><td width='145px'>检测到的物体个数</td>"+	
							"	<td>"+Parkingnum+"</td></tr>"+	
							"<tr><td>规则检测区域顶点数</td>"+	
							"	<td>"+Parkingrrenum+"</td></tr>"+
						"</tbody></table>"+
				"<table class='table-bordered'>	"+				
					"</tbody><tr><td style='width:60px;' rowspan='3'>聚众</td></tr>"+
						"<tr><td width='145px'>检测到的物体个数</td>"+	
						"	<td>"+Rioternum+"</td></tr>"+	
						"<tr><td>规则检测区域顶点数</td>"+	
						"	<td>"+Rioterrenum+"</td></tr>"+
					"</tbody></table>"+
				"<table class='table-bordered'>	"+				
					"</tbody><tr><td style='width:60px;' rowspan='3'>徘徊</td></tr>"+
						"<tr><td width='145px'>检测到的物体个数</td>"+	
						"	<td>"+Wandernum+"</td></tr>"+	
						"<tr><td>规则检测区域顶点数</td>"+	
						"	<td>"+Wanderrenum+"</td></tr>"+
					"</tbody></table>"+
					"</div>"];
				infoWindow = new AMap.InfoWindow({
					content: content.join("<br>")
				});
				infoWindow.open(map, e.target.get("position"));
			}
		})
	}
	
//	两违监测
    function getillegal(){
    	illegalmarker=[];
		$("#illegal").removeClass("active")
		$("#illegal").parent("a").addClass("nopointer")
    	$.ajax({
            url: '/api/car/serviceMonitor/getByName',
            dataType: 'json',
            type: 'POST',
            data:{
            	name:"",
            	status:""
            },
            success: function (result) {
    			$("#illegal").addClass("active")
				$("#illegal").parent("a").removeClass("nopointer")
				$.each(result,function(idx,item) {
					var latLon=GPS.WGStoGCJ(item.lng,item.lat);
					var marker=new AMap.Marker({
						position:new AMap.LngLat(latLon.lat,latLon.lon),
						offset:new AMap.Pixel(-10,-10),
						icon:'images/illegal.png',
						data:item
					})
					map.add(marker);
					illegalmarker.push(marker);
					marker.on('click',getillegalInfo);
					
				});
            }
        })
    }
	getillegal();
	$("#illegal").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(illegalmarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getillegal();
    	}
	})
	var getillegalInfo=function(e){
		var data=e.target.get("data");
//	    var lnglat=[data.lng,data.lat];
	    $.ajax({
			url: '/api/car/serviceMonitor/getDeviceDataById',
			dataType: 'json',
			type: 'POST',
			data: {
				id: data.id
			},
			success: function(result) {	
				var deviceInfo="";
				switch (result.deviceInfo.deviceInfo){
					case 0:
						deviceInfo="正常数据";
						break;
					case 1:
						deviceInfo="设备未上线";
						break;
					case 2:
						deviceInfo="设备已过期";
						break;
					case 3:
						deviceInfo="设备离线";
						break;

				}
					
                var status=result.deviceInfo.status=="1"?"正常":'异常';
                var po=(result.deviceInfo.lng?result.deviceInfo.lng:'')+","+(result.deviceInfo.lat?result.deviceInfo.lat:'');
                
				var content=["<p class='pop_tl'>两违监测</p><div class='pop_box'><table></tbody class>"+
					"<tr><td style='width:85px;'>设备信息：</td><td>"+deviceInfo+"</td></tr>"+
					"<tr><td>状态：</td><td>"+(status?status:'')+"</td></tr>"+
					"<tr><td>经纬度：</td><td>"+po+"</td></tr>"+
					
				"</tbody></table></div>"];
				infoWindow = new AMap.InfoWindow({
					content: content.join("<br>")
				});
				infoWindow.open(map, e.target.get("position"));
			}
		})
	}
	
	
//	客流量
	var passmarker=[];
    function getpass(){
    	passmarker=[];
		$("#passenger").removeClass("active")
		$("#passenger").parent("a").addClass("nopointer")
    	$.ajax({
            url: '/api/device/pfDeviceInfo/getDeviceByParam',
            dataType: 'json',
            type: 'Get',
            data:{
            	name:"",
            	status:""
            },
            success: function (result) {
    			$("#passenger").addClass("active")
				$("#passenger").parent("a").removeClass("nopointer")
				$.each(result,function(idx,item) {
					var latLon=GPS.WGStoGCJ(item.lon_lat.split(',')[1],item.lon_lat.split(',')[0]);
					var marker=new AMap.Marker({
						position:new AMap.LngLat(latLon.lat,latLon.lon),
						offset:new AMap.Pixel(-10,-10),
						icon:'images/pass.png',
						data:item
					})
					map.add(marker);
					passmarker.push(marker);
					marker.on('click',getpassInfo);
					
				});
            }
        })
    }
	getpass();
	$("#passenger").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(passmarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getpass();
    	}
	})
	var getpassInfo=function(e){
		var data=e.target.get("data");
//	    var lnglat=[data.lon_lat.split(',')[1],data.lon_lat.split(',')[0]];
	    $.ajax({
			url: '/api/device/pfDeviceInfo/getDeviceDetailById',
			dataType: 'json',
			type: 'Get',
			data: {
				id: data.id
			},
			success: function(result) {	
                var address=result.data[0].address;
                var cameraname=result.data[0].cameraname;
                var peopleNum,totalInNum,totalOutNum;
                if(result.detail[0].DensityStatistics[0]){
                	peopleNum=result.detail[0].DensityStatistics[0].peoplenum!=null?result.detail[0].DensityStatistics[0].peoplenum:"";
                }else{
                	peopleNum="";
                }
                if(result.detail[0].FlowStatistics[0]){
                	totalInNum=result.detail[0].FlowStatistics[0].totalinnum!=null?result.detail[0].FlowStatistics[0].totalinnum:""
                	totalOutNum=result.detail[0].FlowStatistics[0].totaloutnum!=null?result.detail[0].FlowStatistics[0].totaloutnum:""
                }else{
                	totalInNum="";
                	totalOutNum="";
                }
				var content=["<p class='pop_tl'>客流量</p><div class='pop_box'><table></tbody class>"+		
					"<tr><td style='width:85px;'>地址：</td><td>"+address+"</td></tr>"+			
					"<tr><td>村庄：</td><td>"+(result.data[0].village||"")+"</td></tr>"+	
					"<tr><td>摄像头名称：</td><td>"+cameraname+"</td></tr>"+
					"<tr><td>经纬度：</td><td>"+data.lon_lat+"</td></tr>"+					
				"<table class='table-bordered'>	"+				
					"</tbody><tr><td style='width:60px;' rowspan='2'>人群密度</td></tr>"+
						
						"<tr><td width='120px'>区域内人数</td>"+	
						"	<td>"+peopleNum+"</td></tr>"+
					"</tbody></table>"+
				"<table class='table-bordered'>	"+				
					"</tbody><tr><td style='width:60px;' rowspan='3'>通道客流</td></tr>"+
						"<tr><td width='120px'>1分钟内进入总人数</td>"+	
						"	<td>"+totalInNum+"</td></tr>"+
						"<tr><td>1分钟内离开总人数</td>"+	
						"	<td>"+totalOutNum+"</td></tr>"+
					"</tbody></table>"+
					"</div>"];
					
				infoWindow = new AMap.InfoWindow({
					content: content.join("<br>")
				});
				infoWindow.open(map, e.target.get("position"));
			}
		})
	}
	
	
//	人脸识别
	var facemarker=[];
    function getface(){
    	facemarker=[];
		$("#face").removeClass("active")
		$("#face").parent("a").addClass("nopointer")
    	$.ajax({
            url: '/api/face/faceCamera/getByName',
            dataType: 'json',
            type: 'POST',
            data:{
            	name:"",
            	status:""
            },
            success: function (result) {
            	var faceid=[];
            	$.each(result,function(idx,items) {
					if(items.id){
            			faceid.push(items.id.toString())		
            		}
					if((idx+1)==result.length){
						$.ajax({
							url: '/api/face/faceCamera/getDeviceDataByBatchIds2',
							dataType: 'json',
							type: 'post',
							contentType:"application/json;charset=utf-8",
							data: JSON.stringify({ "ids":faceid}),
							success: function(alldata) {
								$("#face").addClass("active")
								$("#face").parent("a").removeClass("nopointer")
								$.each(alldata.faceInfo,function(index,item){
									
									var latLon=GPS.WGStoGCJ(item.longitude,item.latitude);
									var marker=new AMap.Marker({
										position:new AMap.LngLat(item.longitude,item.latitude),
										offset:new AMap.Pixel(-10,-10),
										icon:'images/face.png',
										data:item
									})
									map.add(marker);
									facemarker.push(marker);
									marker.on('click',getfaceInfo);
								})
							}
						})
					}
				})
            }
        })

    }
	getface();
	$("#face").on("click", function() {
    	if($(this).hasClass("active")){
    		$.each(facemarker,function(idx,item){
    			item.setMap(null);
    		})
			
    		if(infoWindow){
    			infoWindow.close();
    		}
    		$(this).removeClass("active")
    	}else{
    		getface();
    	}
	})
	var getfaceInfo=function(e){
		var data=e.target.get("data");
        if(data.personid==0){
			info="<table class='mt10 table-bordered'></tbody><tr><td width='85px'>信息：</td><td>陌生人（非危险名单中人物）</td>"+			
			"</tr></tbody></table>"
        }else{
        	if(data.facePersonDO){
        		info="<table class='mt10 table-bordered'></tbody><tr><td width='85px'>姓名：</td><td>"+(data.facePersonDO.name||"")+"</td></tr>"+
        		"<tr><td>性别：</td><td>"+(data.facePersonDO.gender==0?"男":"女")+"</td></tr>"+
				"<tr><td>电话：</td><td>"+(data.facePersonDO.phone||"")+"</td></tr>"+		
				"<tr><td>地址：</td><td>"+(data.facePersonDO.address||"")+"</td></tr>"+	
				"<tr><td>证件号：</td><td>"+(data.facePersonDO.zjhm||"")+"</td></tr>"+	
				"<tr><td>备注：</td><td>"+(data.facePersonDO.description||"")+"</td></tr>"+	
				"</tbody></table>"
        	}
        }
		var content=["<p class='pop_tl'>人脸识别</p><div class='pop_box'><table></tbody class>"+		
			"<tr><td style='width:85px;'>名称：</td><td>"+(data.cameraname||"")+"</td></tr>"+		
			"<tr><td>经纬度:</td><td>"+(data.latitude||"")+","+(data.longitude||"")+"</td></tr>"+	
			"<tr><td>时间</td><td>"+(data.creattime||"")+"</td></tr>"+
			"<tr><td>图片：</td><td> <img width='100%' src='"+(data.faceurl||"")+"'</td>"+
		"</tbody></table>"+info+
		"</div>"];
		infoWindow = new AMap.InfoWindow({
			content: content.join("<br>")
		});
		infoWindow.open(map, e.target.get("position"));
	}
    $("#eliminate").on("click", function() {//一键清除
        eliminate()
    })
    eliminate = function eliminate(){
        if(infoWindow){
            infoWindow.close();
        }
        if($("#eliminate").hasClass("active")){
            if($("#event_btn").hasClass("active")){//告警信息
                $.each(evevtmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#event_btn").removeClass("active")
            }
            if($("#em_car").hasClass("active")){//应急指挥车
                $.each(carmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#em_car").removeClass("active")
            }
            if($("#camera").hasClass("active")){//摄像头
                $.each(cameramarker,function(idx,item){
                    item.setMap(null);
                })
                $("#camera").removeClass("active")
            }
            if($("#fire_btn").hasClass("active")){//消防栓
                $.each(firemarker,function(idx,item){
                    item.setMap(null);
                })
                $("#fire_btn").removeClass("active")
            }
            if($("#hut").hasClass("active")){//健康小屋
                $.each(hutmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#hut").removeClass("active")
            } if($("#sGroup").hasClass("active")){//特殊人群
                $.each(groupmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#sGroup").removeClass("active")
            } if($("#lamp_btn").hasClass("active")){//智慧路灯
                $.each(lampmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#lamp_btn").removeClass("active")
            } if($("#water_btn").hasClass("active")){//水质监测
                $.each(watermarker,function(idx,item){
                    item.setMap(null);
                })
                $("#water_btn").removeClass("active")
            }
            if($("#rain_btn").hasClass("active")){//水雨情
                $.each(rainmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#rain_btn").removeClass("active")
            }
            if($("#air_btn").hasClass("active")){//AQI
                $.each(airmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#air_btn").removeClass("active")
            }
            if($("#behavior").hasClass("active")){//行为识别
                $.each(behaviormarker,function(idx,item){
                    item.setMap(null);
                })
                $("#behavior").removeClass("active")
            }
            if($("#illegal").hasClass("active")){//两违监测
                $.each(illegalmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#illegal").removeClass("active")
            }
            if($("#passenger").hasClass("active")){//客流量
                $.each(passmarker,function(idx,item){
                    item.setMap(null);
                })
                $("#passenger").removeClass("active")
            }
            if($("#face").hasClass("active")){//人脸识别
                $.each(facemarker,function(idx,item){
                    item.setMap(null);
                })
                $("#face").removeClass("active")
            }
           //  //告警信息
           //      $.each(evevtmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#event_btn").removeClass("active")
		   //
           //  //应急指挥车
           //      $.each(carmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#em_car").removeClass("active")
		   //
           //  //摄像头
           //      $.each(cameramarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#camera").removeClass("active")
		   //
           // //消防栓
           //      $.each(firemarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#fire_btn").removeClass("active")
		   //
           //  //健康小屋
           //      $.each(hutmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#hut").removeClass("active")
           //  //特殊人群
           //      $.each(groupmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#sGroup").removeClass("active")
           //  //智慧路灯
           //      $.each(lampmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#lamp_btn").removeClass("active")
           // //水质监测
           //      $.each(watermarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#water_btn").removeClass("active")
		   //
           //  //水雨情
           //      $.each(rainmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#rain_btn").removeClass("active")
		   //
           //  //AQI
           //      $.each(airmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#air_btn").removeClass("active")
           //  //行为识别
           //      $.each(behaviormarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#behavior").removeClass("active")
		   //
           //  //两违监测
           //      $.each(illegalmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#illegal").removeClass("active")
           //  //客流量
           //      $.each(passmarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#passenger").removeClass("active")
           //  //人脸识别
           //      $.each(facemarker,function(idx,item){
           //          item.setMap(null);
           //      })
           //      $("#face").removeClass("active")

            $("#eliminate").removeClass("active")
		}else{
            $("#eliminate").removeClass("active")
           $("#eliminate").parent("a").addClass("nopointer")

			getEvent2(allevent)//告警信息
			getemcar();//应急指挥车
			getcamera();//摄像头
			getfire();//消防栓
			gethut(1);//健康小屋
			getgroup();//特殊人群
			getlamp();//智慧路灯
			getwater();//水质监测
			getrain();//水雨情
			getair();//AQI
			getbehavior();//行为识别
			getillegal();//两违监测
			getpass();//客流量
			getface();//人脸识别

			setTimeout(function(){
				$("#eliminate").addClass("active")
				$("#eliminate").parent("a").removeClass("nopointer")
			},9000)




    	}

    }






//	室外用电预警
//	var outmarker=[];
//  function getout(){
//  	outmarker=[];
//  	$.ajax({
//          url: 'plugin/outdoor.json',
//          dataType: 'json',
//          type: 'get',
//          success: function (result) {
//          	console.log(result)
//				$.each(result,function(idx,item) {
//					 var marker=new AMap.Marker({
//						position:new AMap.LngLat(item.gpsPosition.split(',')[0],item.gpsPosition.split(',')[1]),
//						offset:new AMap.Pixel(-10,-10),
//						icon:'images/out.png',
//						data:item
//					})
//					map.add(marker);
//					outmarker.push(marker);
//					marker.on('click',getoutInfo);
//					
//				});
//          }
//      })
//  }
//	getout();
//	$("#out_btn").on("click", function() {
//  	if($(this).hasClass("active")){
//  		$.each(outmarker,function(idx,item){
//  			item.setMap(null);
//  		})
//			
//  		if(infoWindow){
//  			infoWindow.close();
//  		}
//  		$(this).removeClass("active")
//  	}else{
//  		getout();
//  		$(this).addClass("active")
//  	}
//	})
//	var getoutInfo=function(e){
//		var data=e.target.get("data");
//	    var lnglat=[data.gpsPosition.split(',')[0],data.gpsPosition.split(',')[1]];
//	    var content=["<p class='pop_tl'>室外用电预警终端</p><div class='pop_box'><table></tbody class>"+		
//						"<tr><td style='width:85px;'>地点：</td><td>"+data.installAddress+"</td></tr>"+		
//						"<tr><td>预警设施：</td><td>"+data.earlyWarnDevice+"</td></tr>"+		
//						"<tr><td>设备型号：</td><td>"+data.deviceNum+"</td></tr>"+		
//					"</tbody></table></div>"];
//		infoWindow = new AMap.InfoWindow({
//			content: content.join("<br>")
//		});
//		infoWindow.open(map, lnglat);
//	}
	
	
//	室内用电预警
//	var inmarker=[];
//  function getin(){
//  	inmarker=[];
//  	$.ajax({
//          url: 'plugin/indoor.json',
//          dataType: 'json',
//          type: 'get',
//          success: function (result) {
//          	console.log(result)
//				$.each(result,function(idx,item) {
//					 var marker=new AMap.Marker({
//						position:new AMap.LngLat(item.gpsPosition.split(',')[0],item.gpsPosition.split(',')[1]),
//						offset:new AMap.Pixel(-10,-10),
//						icon:'images/in.png',
//						data:item
//					})
//					map.add(marker);
//					inmarker.push(marker);
//					marker.on('click',getinInfo);
//					
//				});
//          }
//      })
//  }
//	getin();
//	$("#in_btn").on("click", function() {
//  	if($(this).hasClass("active")){
//  		$.each(inmarker,function(idx,item){
//  			item.setMap(null);
//  		})
//			
//  		if(infoWindow){
//  			infoWindow.close();
//  		}
//  		$(this).removeClass("active")
//  	}else{
//  		getin();
//  		$(this).addClass("active")
//  	}
//	})
//	var getinInfo=function(e){
//		var data=e.target.get("data");
//	    var lnglat=[data.gpsPosition.split(',')[0],data.gpsPosition.split(',')[1]];
//	    var content=["<p class='pop_tl'>室内用电预警终端</p><div class='pop_box'><table></tbody class>"+		
//						"<tr><td style='width:85px;'>地点：</td><td>"+data.installAddress+"</td></tr>"+		
//						"<tr><td>预警设施：</td><td>"+data.earlyWarnDevice+"</td></tr>"+		
//						"<tr><td>设备型号：</td><td>"+data.intellDeviceNum+"</td></tr>"+		
//					"</tbody></table></div>"];
//		infoWindow = new AMap.InfoWindow({
//			content: content.join("<br>")
//		});
//		infoWindow.open(map, lnglat);
//	}
//	
	
	
	
	function getdevice(){
//		aqi
		$.ajax({
            url: '/api/aqi/aqiDevice/getByName',
            dataType: 'json',
            type: 'post',
            success: function (result) {
				$("#aqi_data tbody").empty()
				$.each(result,function(idx,item) {
					$.ajax({
						url: '/api/aqi/aqiDevice/getDeviceDataById',
						dataType: 'json',
						type: 'post',
						data: {
							id: item.id
						},
						success: function(data) {
							var co,no2,o3,so2,pm10;
							if(data.deviceData.data[0]){
			                    co=data.deviceData.data[0].co;
				                no2=data.deviceData.data[0].no2;
				                o3=data.deviceData.data[0].o3;
				                so2=data.deviceData.data[0].so2;
				                pm10=data.deviceData.data[0].pm10;
				                if(co>10){
				                	co="<td class='colorred'>"+co+"</td>"
							    }else{
							    	co="<td class='colorgreen'>"+co+"</td>"
							    }
							    if(no2>200){
				                	no2="<td class='colorred'>"+no2+"</td>"
							    }else{
							    	no2="<td class='colorgreen'>"+no2+"</td>"
							    }
							    if(so2>500){
				                	so2="<td class='colorred'>"+so2+"</td>"
							    }else{
							    	so2="<td class='colorgreen'>"+so2+"</td>"
							    }
							    if(pm10>75){
				                	pm10="<td class='colorred'>"+pm10+"</td>"
							    }else{
							    	pm10="<td class='colorgreen'>"+pm10+"</td>"
							    }
							}else{
								co="<td class='colorred'></td>";
				                no2="<td class='colorred'></td>";
				                so2="<td class='colorred'></td>";
				                pm10="<td class='colorred'></td>";
							}
						    $("#aqi_data").append("<tr><td>"+data.deviceInfo.positionname+"</td>"+co+""+no2+""+so2+""+pm10+"</tr>")
							
						}
					})
					
			  });
            }
        })
//			$("#aqi_data").append("<tr><td>浪拨村探测点</td><td class='colorgreen'>0.6</td><td class='colorgreen'>23</td><td class='colorgreen'>5</td><td class='colorgreen'>54</td></tr>"+
//				"<tr><td>正果镇探测点</td><td class='colorgreen'>0.6</td><td class='colorgreen'>44</td><td class='colorgreen'>5</td><td class='colorgreen'>29</td></tr>"+
//				"<tr><td>石溪村探测点</td><td class='colorgreen'>0.5</td><td class='colorgreen'>46</td><td class='colorgreen'>5</td><td class='colorgreen'>52</td></tr>")

		
//		消防栓
//		$.ajax({
//          url: '/api/fire/electricDeviceInfo/getDeviceByParam',
//          dataType: 'json',
//          type: 'get',
//          success: function (result) {
//				$("#fire_data tbody").empty()
//				$.each(result,function(idx,item) {
//					$.ajax({
//						url: '/api/fire/electricDeviceInfo/getDeviceDetailById',
//						dataType: 'json',
//						type: 'get',
//						data: {
//							id: item.id
//						},
//						success: function(data) {
//							var batteryvalue,level,pressure,signal
//							if(data.detail.waterCycleV2[0]){
//			                    batteryvalue=data.detail.waterCycleV2[0].batteryvalue||"";
//						    	level=data.detail.waterCycleV2[0].level||"";
//						   	 	pressure=data.detail.waterCycleV2[0].pressure||"";
//						    	signal=data.detail.waterCycleV2[0].signal||"";
//						    							    	
//						    	if(pressure>5000){
//				                	pressure="<td class='colorred'>"+pressure+"</td>"
//							    }else{
//							    	pressure="<td class='colorgreen'>"+pressure+"</td>"
//							    }
//							    if(level>4500){
//				                	level="<td class='colorred'>"+level+"</td>"
//							    }else{
//							    	level="<td class='colorgreen'>"+level+"</td>"
//							    }
//							    if(batteryvalue>50000){
//				                	batteryvalue="<td class='colorred'>"+batteryvalue+"</td>"
//							    }else{
//							    	batteryvalue="<td class='colorgreen'>"+batteryvalue+"</td>"
//							    }
//							    if(signal<10){
//				                	signal="<td class='colorred'>"+signal+"</td>"
//							    }else{
//							    	signal="<td class='colorgreen'>"+signal+"</td>"
//							    }
//							}else{
//								batteryvalue="<td class='colorred'></td>";
//								level="<td class='colorred'></td>";
//								pressure="<td class='colorred'></td>";
//								signal="<td class='colorred'></td>";
//							}
//							$("#fire_data").append("<tr><td>"+data.data[0].address+"</td>"+pressure+""+level+""+batteryvalue+""+signal+"</tr>")
//							
//						}
//					})					 
//			  });
//          }
//      })
//		
////		特殊人群
		$.ajax({
            url: '/api/special/populationPosition/getDeviceByParam',
            dataType: 'json',
            type: 'Get',
            data:{
            	name:"",
            	status:""
            },
            success: function (result) {
				$("#group_data tbody").empty()
				$.each(result,function(idx,item) {
					$.ajax({
						url: '/api/special/populationPosition/getDeviceDetailById',
						dataType: 'json',
						type: 'get',
						data: {
							id: item.id
						},
						success: function(data) {
//							console.log("data----------")
//							console.log(data)
							var heatRate,thesholdHeartrateH,thesholdHeartrateL,walk;
							
							if(data.detail.BloodPressure[0]){
								thesholdHeartrateH=data.detail.BloodPressure[0].dbp?data.detail.BloodPressure[0].dbp:0;
							    thesholdHeartrateL=data.detail.BloodPressure[0].dbpL?data.detail.BloodPressure[0].dbpL:0;
							
							    if(thesholdHeartrateH>140){
				                	thesholdHeartrateH="<td class='colorred'>"+thesholdHeartrateH+"</td>"
							    }else{
							    	thesholdHeartrateH="<td class='colorgreen'>"+thesholdHeartrateH+"</td>"
							    }
							    if(thesholdHeartrateL<60){
				                	thesholdHeartrateL="<td class='colorred'>"+thesholdHeartrateL+"</td>"
							    }else{
							    	thesholdHeartrateL="<td class='colorgreen'>"+thesholdHeartrateL+"</td>"
							    }							    
							}else{
								thesholdHeartrateH="<td class='colorred'>0</td>";
								thesholdHeartrateL="<td class='colorred'>0</td>";
							}
							if(data.detail.Heart[0]){
								heatRate=data.detail.Heart[0].heartrate;
							    
							    if(heatRate<40||heatRate>140){
				                	heatRate="<td class='colorred'>"+heatRate+"</td>"
							    }else{
							    	heatRate="<td class='colorgreen'>"+heatRate+"</td>"
							    }						    
							}else{
								heatRate="<td class='colorred'>0</td>";
							}
							if(data.detail.Walk[0]){
								walk=data.detail.Walk[data.detail.Walk.length-1].value;
							    	walk="<td class='colorgreen'>"+walk+"</td>"
//								if(walk<3000){
//				                	walk="<td class='colorred'>"+walk+"</td>"
//							    }else{
//							    	walk="<td class='colorgreen'>"+walk+"</td>"
//							    }
							}else{								
								walk="<td class='colorred'>0</td>";
							}
						    $("#group_data").append("<tr><td>"+data.data[0].wearerName+"</td><td>"+data.data[0].wearerMobile+"</td>"+heatRate+""+thesholdHeartrateH+""+thesholdHeartrateL+""+walk+"</tr>")							
						}
					})									
				});
            }
        })
	}
	// =================================new 设备监测 =================================
	function getDevicenew(){
		$.ajax({
			url: '/api/aqi/aqiDevice/getDataForDeviceMonitor',
			dataType: 'json',
			type: 'get',
			success: function (data) {
				//air aqi
				if(data.air.length>0){
					$("#aqi_data tbody").empty();
					var air = data.air;
					var co,no2,o3,so2,pm10;
					for (var i in air){
						if(air[i].deviceData[0]){
							co=air[i].deviceData[0].co;
							no2=air[i].deviceData[0].no2;
							o3=air[i].deviceData[0].o3;
							so2=air[i].deviceData[0].so2;
							pm10=air[i].deviceData[0].pm10;
							if(co>10){
								co="<td class='colorred'>"+co+"</td>"
							}else{
								co="<td class='colorgreen'>"+co+"</td>";
							}
							if(no2>200){
								no2="<td class='colorred'>"+no2+"</td>";
							}else{
								no2="<td class='colorgreen'>"+no2+"</td>";
							}
							if(so2>500){
								so2="<td class='colorred'>"+so2+"</td>";
							}else{
								so2="<td class='colorgreen'>"+so2+"</td>";
							}
							if(pm10>75){
								pm10="<td class='colorred'>"+pm10+"</td>";
							}else{
								pm10="<td class='colorgreen'>"+pm10+"</td>";
							}
						}else{
							co="<td class='colorred'></td>";
							no2="<td class='colorred'></td>";
							so2="<td class='colorred'></td>";
							pm10="<td class='colorred'></td>";
						}
						$("#aqi_data").append("<tr><td>"+air[i].deviceInfo.positionname+"</td>"+co+""+no2+""+so2+""+pm10+"</tr>");
					}
				}
				//fire
				if(data.fire.length>0){
					$("#fire_data tbody").empty()
					var fire = data.fire;
					var batteryvalue,level1,level2,pressure1,pressure2,signal;
					for(var f in fire){
						//初始化变量值
						batteryvalue="",level1="",level2="",pressure1="",pressure2="",signal="";
						if(fire[f].data){
							if(fire[f].detail){
								batteryvalue=fire[f].detail.batteryvalue!=null?fire[f].detail.batteryvalue : "";//电压
								signal=fire[f].detail.signal != null ? fire[f].detail.signal : "";//信号强度
								if(fire[f].detail.channel1type == 1){//液位1 通道一类型，0表示压力(0~6000kPa)，1表示液位(0~5000cm)
//									level1=fire[f].detail.channel1 != null ? fire[f].detail.channel1 : "";
									level1="";
								}else if(fire[f].detail.channel1type == 0){//压力1
									pressure1=fire[f].detail.channel1 != null ? fire[f].detail.channel1 : "";
								}
								if(fire[f].detail.channel2type == 1){//液位2 通道二类型，0表示压力(0~6000kPa)，1表示液位(0~5000cm)
//									level2=fire[f].detail.channel2 != null ? fire[f].detail.channel2 : "";
									level2="";
								}else if(fire[f].detail.channel2type == 0){//压力2
									pressure2=fire[f].detail.channel2 != null ? fire[f].detail.channel2 : "";
								}
								//通道一压力与液位判断
//								if(pressure1<100||pressure1>1500){
//									pressure1="<td class='colorred'>"+pressure1+"</td>";
//								}else{
//									pressure1="<td class='colorgreen'>"+pressure1+"</td>";
//								}
								pressure1="<td class='colorgreen'>"+pressure1+"</td>";
//								if(level1>4500){
//									level1="<td class='colorred'>"+level1+"</td>";
//								}else{
//									level1="<td class='colorgreen'>"+level1+"</td>";
//								}
								//通道二压力与液位判断
								if(pressure1<100||pressure2>1500){
									pressure2="<td class='colorred'>"+pressure2+"</td>";
								}else{
									pressure2="<td class='colorgreen'>"+pressure2+"</td>";
								}
//								if(level2>4500){
//									level2="<td class='colorred'>"+level2+"</td>";
//								}else{
//									level2="<td class='colorgreen'>"+level2+"</td>";
//								}
								//电压判断
//								if(batteryvalue>50000){
//									batteryvalue="<td class='colorred'>"+batteryvalue+"</td>";
//								}else{
//									batteryvalue="<td class='colorgreen'>"+batteryvalue+"</td>";
//								}
								batteryvalue="<td class='colorgreen'>"+batteryvalue+"</td>";
								//信号强度判断
//								if(signal<10){
//									signal="<td class='colorred'>"+signal+"</td>";
//								}else{
//									signal="<td class='colorgreen'>"+signal+"</td>";
//								}
								signal="<td class='colorgreen'>"+signal+"</td>";
								
							}else{//设备下无数据
								batteryvalue="<td class='colorred'></td>";
//								level1="<td class='colorred'></td>";
//								level2="<td class='colorred'></td>";
								pressure1="<td class='colorred'></td>";
								pressure2="<td class='colorred'></td>";
								signal="<td class='colorred'></td>";
							}
							$("#fire_data").append("" +
									"<tr><td>"+fire[f].data.address+"</td>" +
											""+pressure1+
											""+pressure2+
//											""+level1+
//											""+level2+
											""+batteryvalue+
											""+signal+"</tr>");
						}
						
					}
				}
				//special
				if(data.special.length>0){
					$("#group_data tbody").empty();
					var special = data.special;
					var heatRate,thesholdHeartrateH,thesholdHeartrateL,walk;
					for(var s in special){
						if(special[s].BloodPressure[0]){
							thesholdHeartrateH=special[s].BloodPressure[0].dbp?special[s].BloodPressure[0].dbp:0;
							thesholdHeartrateL=special[s].BloodPressure[0].dbpL?special[s].BloodPressure[0].dbpL:0;
//							console.log("ceshi{}"+thesholdHeartrateH);
//							console.log(typeof thesholdHeartrateH);
							if(parseInt(thesholdHeartrateH)>140){
								thesholdHeartrateH="<td class='colorred'>"+thesholdHeartrateH+"</td>";
							}else{
								thesholdHeartrateH="<td class='colorgreen'>"+thesholdHeartrateH+"</td>";
							}
							if(thesholdHeartrateL<60){
								thesholdHeartrateL="<td class='colorred'>"+thesholdHeartrateL+"</td>";
							}else{
								thesholdHeartrateL="<td class='colorgreen'>"+thesholdHeartrateL+"</td>";
							}							    
						}else{
							thesholdHeartrateH="<td class='colorred'>0</td>";
							thesholdHeartrateL="<td class='colorred'>0</td>";
						}
						if(special[s].Heart[0]){
							heatRate=special[s].Heart[0].heartrate;
							if(heatRate<40||heatRate>140){
								heatRate="<td class='colorred'>"+heatRate+"</td>";
							}else{
								heatRate="<td class='colorgreen'>"+heatRate+"</td>";
							}						    
						}else{
							heatRate="<td class='colorred'>0</td>";
						}
						if(special[s].Walk[0]){
							walk=special[s].Walk[special[s].Walk.length-1].value;
							walk="<td class='colorgreen'>"+walk+"</td>"
//							if(walk<3000){
//			                	walk="<td class='colorred'>"+walk+"</td>"
//						    }else{
//						    	walk="<td class='colorgreen'>"+walk+"</td>"
//						    }
							}else{								
								walk="<td class='colorred'>0</td>";
							}
							$("#group_data").append("<tr><td>"+special[s].data[0].wearerName+"</td><td>"+special[s].data[0].wearerMobile+"</td>"+heatRate+""+thesholdHeartrateH+""+thesholdHeartrateL+""+walk+"</tr>");					
						}
					}
				}
			});
		}
	//getdevice()
	getDevicenew();
	setInterval(function(){
		//getdevice();
		getDevicenew();
//		getcaseNum()
	},30000)
	
	function getcaseNum(){
//		案件
		$.ajax({
			url: '/dispatch/center/getCountByCase',
			dataType: 'json',
			type: 'get',
			success: function(result) {
				$("#case_num").text(result.data.closeData)
				$("#case_ed").text(result.data.uneventData)
			}
		})
//		应急设备
		$.ajax({
			url: '/dispatch/center/getAllEquipment',
			dataType: 'json',
			type: 'get',
			success: function(result) {
//				console.log(result)
				$("#car_num").text(result.data.carData.length)
				$("#other_num").text(result.data.otherData.length)
			}
		})
	}
//	getcaseNum()
	
	
/* 发送结束告警事件
//	获取通讯录		
	var allcontacts=[];
	var contacttel=[];
	
	function getContacts() {
		$.ajax({
			url:'/receiveInfo/getDeptChargeMan',
			dataType:'json',
			type:'get',
			success:function(result){
				$("#treemenu").empty();
				console.log(result)
				$.each(result.deptContact,function(index,item){
					if(item.contact){
						allcontacts.push({"name":item.contact,"tel":item.mobile})
						contacttel.push(item.mobile)
						$("#treemenu").append('<li class="has-children">'+
							'<label>'+item.deptName+'</label>'+
							'<ul><li class="contli" tel="'+item.mobile+'" name="'+item.contact+'"><a>'+item.contact+'（联系电话：'+item.mobile+'）</a></li></ul>'+
						'</li>')
					}
					
//					if(!item.children){
//						$("#treemenu").append('<li class="no-children"><label>'+item.name+'</label></li>')
//					}
//					else{
//						var lihtml="";
//						$.each(item.children,function(idx,val){	
//							allcontacts.push({"name":val.name,"tel":val.mobile})
//							contacttel.push(val.mobile)
//							
//							lihtml+='<li class="contli" tel="'+val.mobile+'" name="'+val.name+'"><a>'+val.name+'（联系电话：'+val.mobile+'）</a></li>'
//							if(idx>=item.children.length-1){
//								$("#treemenu").append('<li class="has-children">'+
////											'<input type="checkbox" name ="group-'+index+'" id="group-'+index+'" />'+
//									'<label>'+item.name+'</label>'+
//									'<ul>'+lihtml+'</ul>'+
//								'</li>')
//							}
//						})
//					}
				});
				
			}
		})
		
	}
	getContacts()
	
	
	$("#treemenu").on('click', 'li label', function() {
		$(this).toggleClass("active")
		$(this).siblings('ul').slideToggle(300);
	});
	$("#treemenu").on('click', '.contli',function(){
		var name=$(this).attr("name");
		var tel=$(this).attr("tel");
    	addcontact(name,tel)
	})
	
	
	
	$("#inputtxt")[0].onkeypress = function(e){
		// 获取事件
		e = e || window.event;
		// 获取按键编码
		var key = e.whick || e.keyCode;
		// 检测是否为回车键
		if(key == 13){
		    var tel=$("#inputtxt").val();
			$("#inputkey").append('<span class="optiontxt" tel="'+tel+'">'+tel+
								'<i class="fa fa-close"></i>'+
							'</span>')
			$("#inputtxt").val("");
		}
	}
	$("#inputtxt").bind("input propertychange",function(event){
	        console.log(event.whick);
    	var key=$(this).val().trim();
    	console.log(key);
    	if(key==""){
    		$("#searchul").slideUp()
    		return;
    	}
    	$("#searchul").empty();
		
    	var arr = [];
		$.each(contacttel, function(index,value){
			if(value.indexOf(key) >= 0){
				arr.push(index);
			}
			if(index==contacttel.length-1){
				if(arr.length<1){
					return;
				}							
				$("#searchul").slideDown()
				$.each(arr, function(index,value){
					$("#searchul").append('<li name="'+allcontacts[value].name+'" tel="'+allcontacts[value].tel+'" ><a>'+allcontacts[value].tel+'（'+allcontacts[value].name+'）</a></li>')
					
              	});
				
			}
		});
	});
	function addcontact(name,tel){
		var len=$("#inputkey .optiontxt").length;
		if(len>0){
			$("#inputkey .optiontxt").each(function(idx,val){
				if($(this).attr("tel")==tel){
					return false;
				}else{
					if(idx==(len-1)){
						$("#inputkey").append('<span class="optiontxt" name="'+name+'" tel="'+tel+'">'+name+									
								'<i class="fa fa-close"></i>'+
							'</span>')
					}
					
				}
			})
		}else{
			$("#inputkey").append('<span class="optiontxt" name="'+name+'" tel="'+tel+'">'+name+
							'<i class="fa fa-close"></i>'+
						'</span>')
		}
	}
	
	$("#searchul").on("click","li",function(){
		var name=$(this).attr("name");
		var tel=$(this).attr("tel");
		addcontact(name,tel);	
	})
	$("#inputkey").on("click",".fa-close",function(){
		$(this).parent(".optiontxt").remove()				
	})
	*/
	
	
	$("#end_class .btn").click(function(){
		$("#end_class").addClass("active");
		$("#end_class .btn").removeClass("btn-success");
		$(this).addClass("btn-success");
	})
	
//	获取上报人列表
	function getAllname(){
		$.ajax({
            url:'/deptPerson/getAll2',
            type:'post',
            dataType:'json',
            async:false,
            success:function (rs) {
                //改用select2加载
                console.log("rs-----")
                console.log(rs)
                personArr = rs;
                var deptArray = new Array();
                deptArray.push({id:"",text:""});
                if(rs.user){
                	var obj = new Object();
                    obj.id = rs.user["name"];
                    obj.text = rs.user["name"];
                    obj.mobile = rs.user["mobile"];
                    obj.sex = rs.user["sex"];
                    deptArray.push(obj);
                }
                if(rs.data.length>0){
                    for (var i in rs.data){
                        var obj = new Object();
                        obj.id = rs.data[i]["name"];
                        obj.text = rs.data[i]["name"];
                        obj.mobile = rs.data[i]["mobile"];
                        obj.sex = rs.data[i]["sex"];
                        deptArray.push(obj);
                    }
                }
                select2PersonArr = deptArray;
                $("#event_re2").select2({
                    data:deptArray,
                    tags: true
                });
                
//              $("#event_re2").val(2).trigger("change");
            	
            	
            	
//              personArr = rs;
//              //改用select2加载
//              var deptArray = new Array();
//              deptArray.push({id:"",text:""});
//              if(rs.length>0){
//                  for (var i in rs){
//                      var obj = new Object();
//                      obj.id = rs[i]["name"];
//                      obj.text = rs[i]["name"];
//                      obj.mobile = rs[i]["mobile"];
//                      obj.sex = rs[i]["sex"];
//                      deptArray.push(obj);
//                  }
//              }
//              $("#event_re2").select2({
//                  data:deptArray,
//                  tags: true
//              });
//              $("#event_re2").val(2).trigger("change");
                
            }
        })
	}
	getAllname();
	$("#event_re2").on("select2:select",function(e){
        $("#event_phone").val(e.params.data.mobile);
        switch(e.params.data.sex){
	        case 1:
	        	$("#event_sex .txt").text("女")	 
	        	break;
	        case 0:
	        	$("#event_sex .txt").text("女")	 
	        	break;
	        case 2:
	        	$("#event_sex .txt").text("其他")	 
	        	break;
	        }
//      $("#event_sex .txt").text(e.params.data.sex==1?"女":"男")
    });
	
	
	$("#event_dapart2").on("select2:select",function(e){
		getdapart_ul($(this).val())
	});
})
	
//	事件详情
	var eventarr={};
	function otherevent(){
//		$.ajax({
//			url: '/dispatch/center/getEventProcess',
//			dataType: 'json',
//			type: 'POST',
//			success: function(result) {
//              $.each(result.data, function(idx,item) {
//              	if(item.feedbackContent!=""){
//              		$("#case_table").append("<tr><td>"+item.accidentTypeName+"</td><td style='text-align: left;'>"+item.feedbackContent+"</td></tr>")                		
//              	}
//              });
//			}
//		})
//		$.ajax({
//			url: '/dispatch/center/getEventByType',
//			dataType: 'json',
//			type: 'get',
//			success: function(result) {
//				console.log("-----事件")
//				console.log(result)
//				 $("#c_table").empty()
//				var thead="<table class='table table-bordered'><thead><tr><td>接报途径</td><td>事件描述</td><td>操作</td></tr></thead><tbody>"
//				var tbody="";
//              $.each(result.data, function(idx,item) {
//              	if(item.length>0){
//              		var source=item[0].sourceName;
//              		$.each(item,function(index,val){
//              			eventarr[val.eventId]=val;
//              			if(index==0){
//              				tbody+="<tr><td rowspan='"+item.length+"'>"+source+"</td><td>"+val.eventDesc+"</td><td><button onclick='getEventinfo(\""+val.eventId+"\")' class='btn btn-xs btn-success' data-toggle='modal' data-target='#eventModal'>推送</button><button class='btn btn-xs btn-primary' data-toggle='modal' data-target='#contModal'>终结</button></td></tr>";                 				
//              			}else{
//              				tbody+="<tr><td>"+val.eventDesc+"</td><td><button onclick='getEventinfo(\""+val.eventId+"\")' class='btn btn-xs btn-success' data-toggle='modal' data-target='#eventModal' >详情</button><button class='btn btn-xs btn-primary' data-toggle='modal' data-target='#contModal'>终结</button></td></tr>";
//              			}
//              		})
//              	}
//              });
//              $("#c_table").append(thead+tbody+"</tbody></table>");
//			}
//		})
		$.ajax({
			url: '/dispatch/center/getEventByTypeAndOrder',
			dataType: 'json',
			type: 'get',
			success: function(result) {
				console.log("-----事件")
				console.log(result)
				 $("#c_table").empty()
				var thead="<table class='table table-bordered'><thead><tr><td>接报途径</td><td>事件描述</td><td>操作</td></tr></thead><tbody>"
				var tbody="";
                $.each(result.data.data, function(idx,item) {
                	if(item.latLon){
                		eventarr[item.eventId]=item;
                		tbody+="<tr><td>"+item.sourceName+"</td><td>"+item.eventDesc+"</td><td><button onclick='getEventinfo(\""+item.eventId+"\")' class='btn btn-xs btn-success' data-toggle='modal' data-target='#eventModal' >推送</button><button onclick='getEventinfo(\""+item.eventId+"\")' class='btn btn-xs btn-primary' data-toggle='modal' data-target='#contModal'>终结</button></td></tr>";                		
                	}
                });
                $("#c_table").append(thead+tbody+"</tbody></table>");
			}
		})
	}
	otherevent();
    function getEventinfo(id){
//		eventinfo=eventarr[id];
//		$("#info_name").val(eventinfo.repname)
//		$("#info_phone").val(eventinfo.repphone)
//		$("#info_sex").val(eventinfo.sex==1?"女":"男")
//		$("#info_addr").val(eventinfo.eventaddr)
//		$("#info_desc").val(eventinfo.eventdesc)
//		$("#info_time").val(eventinfo.create_date)
//		$("#info_way").val(eventinfo.source_name)
////		$("#info_depart").val(eventinfo.repname)
////		$("#info_type").val(eventinfo.repname)
////		$("#info_class").val(eventinfo.repname)
////		$("#info_level").val(eventinfo.repname)
//		$("#info_date").val(eventinfo.repdate)
//		$("#info_remark").val(eventinfo.remarks)
		eventItem=eventarr[id];
		console.log(eventItem)
	}
    
    
//  接报途径和主管部门
	var deptArray;
	function getpart(){
		$.ajax({
            url: '/receiveInfo/getCompushData',
            dataType: 'json',
            type: 'get',
            success: function (result) {
            	console.log(result)
            	$("#soure_ul").empty()
            	
				$.each(result.sourceType,function(idx,item) {	
					$("#soure_ul").append("<li><a id='"+item.id+"'>"+item.name+"</a></li>")				
				});
				
				//改用select2加载
				rs=result.chargeDept;
                deptArray = new Array();
                deptArray.push({id:"",text:""});
                $.each(result.chargeDept,function(idx,item){                	
                    var obj = new Object();
                    obj.id = item.id;
                    obj.text = item.name;
                    deptArray.push(obj);
                    if(result.chargeDept.length>=(idx+1)){
                    	$("#event_dapart2").select2({
		                    data:deptArray
		                });
                    }
                })
                
//              $("#dapart_ul").empty()
//				$.each(result.chargeDept,function(idx,item) {
//					$("#dapart_ul").append("<li><a onclick='getdapart_ul(this)' id='"+item.id+"'>"+item.name+"</a></li>")
////					getdapart_ul()
//				});	
				
				
//				$("#dapart_ul a").eq(0).click();
            }
		})
	}
	getpart();
	
//	获取事故类型
	function getdapart_ul(li,txt){
		if(typeof(li)=="object"){
			var id=$(li).attr("id")
		}else{
			var id=li			
		}
		$.ajax({
			url: '/receiveInfo/getAccidentType',
			dataType: 'json',
			type: 'post',
            async: false,
			data: {
				id: id
			},
			success: function (result) {
				$("#event_class").find(".txt").text("");
				$("#event_class .txt").attr("id","")
            	$("#class_ul").empty()
            	
				$("#event_type").find(".txt").text("");
				$("#event_type .txt").attr("id","")
				$("#type_ul").empty()				
				
				$("#event_level").find(".txt").text("");
				$("#event_level .txt").attr("id","")
            	$("#level_ul").empty()
            	
            	
            	$.each(result,function(idx,item){
            		$("#class_ul").append("<li><a onclick='getclass_ul(this)' id='"+item.id+"'>"+item.name+"</a></li>")
            	})
            	
//				$("#class_ul a").eq(0).click();
//          	$("#event_class .txt").text($("#class_ul a").eq(0).text());
//				$("#class_ul a").eq(0).click();
				if(txt){
					$("#class_ul a").each(function(){
						if($(this).attr("id")==txt){
							$("#event_class .txt").text($(this).text());
							$("#event_class .txt").attr("id",txt)
						}
					})
				}else{
					$("#class_ul a").eq(0).click();
				}
            }
		})
	}
	
//	获取预警类型
	function getclass_ul(li,txt){
		if(typeof(li)=="object"){
			var id=$(li).attr("id")
		}else{
			var id=li			
		}
		$.ajax({
			url: '/receiveInfo/getEarlyWarnTypeByAccidentId',
			dataType: 'json',
			type: 'post',
			async: false,
			data: {
				id: id
			},
			success: function (result) {
				$("#event_type").find(".txt").text("");
				$("#event_type .txt").attr("id","")
				$("#type_ul").empty()
				$("#event_level").find(".txt").text("");
				$("#event_level .txt").attr("id","")
            	$("#level_ul").empty()
            	
//				$("#type_ul").empty()
//				$("#event_type").find(".txt").text("");
				$.each(result,function(idx,item){
            		$("#type_ul").append("<li><a onclick='gettype_ul(this)' id='"+item.id+"'>"+item.name+"</a></li>")
            	})
//				$("#type_ul a").eq(0).click();
//				$("#event_type .txt").text($("#type_ul a").eq(0).text());
				if(txt){
					$("#type_ul a").each(function(){
						if($(this).attr("id")==txt){
							$("#event_type .txt").text($(this).text());
							$("#event_type .txt").attr("id",txt)
						}
					})
				}else{
					$("#type_ul a").eq(0).click();
				}
            }
		})
	}
	
//	获取预警级别
	function gettype_ul(li,txt){
		if(typeof(li)=="object"){
			var id=$(li).attr("id")
		}else{
			var id=li			
		}
		$.ajax({
			url: '/receiveInfo/getEarlyWarnLevelByTypeId',
			dataType: 'json',
			type: 'post',
			async: false,
			data: {
				id: id
			},
			success: function (result) {
				$("#event_level").find(".txt").text("");
				$("#event_level .txt").attr("id","")
				
            	$("#level_ul").empty()
            	$.each(result,function(idx,item){
            		$("#level_ul").append("<li><a id='"+item.id+"'>"+item.name+"</a></li>")
            	})
            	
//				$("#level_ul a").eq(0).click();
//          	$("#event_level .txt").text($("#level_ul a").eq(0).text());
				if(txt){
					$("#level_ul a").each(function(){
						if($(this).attr("id")==txt){
							$("#event_level .txt").text($(this).text());
							$("#event_level .txt").attr("id",txt)
						}
					})
				}else{
					$("#level_ul a").eq(0).click();
				}
            }
		})
	
	}
	
	
	function pushEvent(){
		var obj = new Object();
//		var sex=$("#event_sex .txt").text()=="男"?0:1
		if($("#event_sex .txt").text()=="男"){
			var sex=0;
		}else if($("#event_sex .txt").text()=="女"){
			var sex=1;
		}else{
			var sex=2;
		}
//		obj.repname=$("#event_re2").val()
		if($("#event_re2").val()!=""){
			obj.repname=$("#event_re2").val()
		}else{
			alert("'*'均为必填项，请输入上报人姓名");
			return;
		}
//		obj.repphone=$("#event_phone").val()
		if($("#event_phone").val()!=""){
			obj.repphone=$("#event_phone").val()
		}else{
			alert("'*'均为必填项，请输入上报人电话");
			return;
		}
		obj.sex=sex
//		obj.eventaddr=$("#event_addr").val()
		if($("#event_addr").val()!=""){
			obj.eventaddr=$("#event_addr").val()
			obj.lat_lon=$("#lat_lon").val();//选择地址的经纬度
		}else{
			alert("'*'均为必填项，请输入地址");
			return;
		}
		
//		obj.eventdesc=$("#event_des").val()
		if($("#event_des").val()!=""){
			obj.eventdesc=$("#event_des").val()
		}else{
			alert("'*'均为必填项，请选择事件描述");
			return;
		}
//		obj.repdate=$("#event_repdate").val()
		if($("#event_repdate").val()!=""){
			obj.repdate=$("#event_repdate").val()
		}else{
			alert("'*'均为必填项，请选择上报时间");
			return;
		}
//		obj.source_type=$("#soure_drop .txt").attr("id")
		if($("#soure_drop .txt").attr("id")!=""){
			obj.source_type=$("#soure_drop .txt").attr("id")
		}else{
			alert("'*'均为必填项，请选择接报途径");
			return;
		}
//		obj.dept_id=$("#event_dapart .txt").attr("id")
//		obj.dept_id=$("#event_dapart2").val();
		if($("#event_dapart2").val()!=""){
			obj.dept_id=$("#event_dapart2").val()
		}else{
			alert("'*'均为必填项，请选择主管单位");
			return;
		}
		
//		obj.accident_type_id=$("#event_class .txt").attr("id")
		if($("#event_class .txt").attr("id")!=""){
			obj.accident_type_id=$("#event_class .txt").attr("id")
		}else{
			alert("'*'均为必填项，请选择事故类型");
			return;
		}
//		obj.earlywarn_id=$("#event_type .txt").attr("id")
		if($("#event_type .txt").attr("id")!=""){
			obj.earlywarn_id=$("#event_type .txt").attr("id")
		}else{
			alert("'*'均为必填项，请选择预警类型");
			return;
		}
//		obj.eventlevel=$("#event_level .txt").attr("id")
		if($("#event_level .txt").attr("id")!=""){
			obj.eventlevel=$("#event_level .txt").attr("id")
		}else{
			alert("'*'均为必填项，请选择预警级别");
			return;
		}
//		obj.acceptance_time=$("#event_accdata").val()
		if($("#event_accdata").val()!=""){
			obj.acceptance_time=$("#event_accdata").val()
		}else{
			alert("'*'均为必填项，请选择主受理时间");
			return;
		}
		
//		$.each(obj,function(idx,item){
//			if(item==""){
//				alert("'*'均为必填项，请完善信息再推送！")
//				return;
//			}
//		})
		
		
		obj.remarks=$("#event_remark").val();
		var url = "/receiveInfo/newEventPush";
		if(!nonlink){
			var id=$("#push_btn").attr("flag")
			obj.id=id;	
			url = "/receiveInfo/comEventStart";
		}
		$.ajax({
			url: url,					
			type: 'post',
			dataType:'json',
			contentType:'application/json',
			data: JSON.stringify(obj),
			success: function (result) {
				console.log(result);
				if(result.code == 0){
					alert("推送成功");	
					if(infoWindow){
						map.remove(mapflag);
						infoWindow.close();
					}
					if(infoWindow2){
						map.remove(mapflag);
						infoWindow2.close();
					}
					$('#eventModal').modal('hide');
					if(nonlink){
						window.open("http://"+window.location.host+"/Emergency/web/client/pages/industry.html?eventid="+result.data.eventId,"_blank");
					}else{
						window.open("http://"+window.location.host+"/Emergency/web/client/pages/industry.html?eventid="+id,"_blank");
						otherevent();						
					}
				}
            }
		})
	}
	
	function endEvent(){				
		var id=$("#end_btn").attr("flag");
		var desc=$("#end_btn").attr("desc");
		var mobile="";
		$("#inputkey .optiontxt").each(function(index){
			mobile+=$(this).attr("tel")+",";
		})
		$.ajax({
			url: '/receiveInfo/comFinishedSendMsg',
			dataType: 'json',
			type: 'post',
			data: {
				eventId:id,
				mobiles:mobile,
				content:desc
			},
			success: function (result) {
				console.log(result)
				if(result.msg=="操作成功"){
					alert("发送成功");	
					map.remove(mapflag);
    				infoWindow.close();
					getEvent()
					$('#contModal').modal('hide')
				}else{
					alert("操作失败")
				}
            }
		})
	}
	function endEvent2(){
		
		if($("#end_class").hasClass("active")){
			var remark=$("#remark_resp").val();//说明
			var id=$("#end_btn").attr("flag");//终结事件ID
			var operator=$("#event_operator").val();//操作人
			var finishType = $("#end_class .btn-success").text();//完结类型（结论）
			$.ajax({
				url: '/receiveInfo/comFinishedSendMsg',
				dataType: 'json',
				type: 'post',
				data: {
					eventId:id,
					operator:operator,
					content:remark,
					finishType:finishType
				},
				success: function (result) {
					if(result.msg=="操作成功"){
						alert("发送成功");	
						if(infoWindow){
							map.remove(mapflag);
							infoWindow.close();
						}
						if(infoWindow2){
							map.remove(mapflag);
							infoWindow2.close();
						}
						$.ajax({
							url: '/dispatch/center/event/endWarn',
							type: 'post',
							dataType:'json',
							data:{
								eventId:id
							},
							success: function (result) {
								console.log("删除事件");
								console.log(result);
//								getEvent2(allevent)
							}
						})
						$('#contModal').modal('hide')
						otherevent();
					}else{
						alert("操作失败")
					}
	            }
			})
		}else{
			alert("请选择结论类型")
		}
	}
	
	 function getAppReportImgByEventId(eventId){
		 var list;
		 $.ajax({
				url: '/receiveInfo/getAppReportImgByEventId',
				type: 'post',
				dataType:'json',
			 	async: false,
				data:{
					eventId:eventId
				},
				success: function (data) {
					list = data.data;
				}
			});
		 console.log("ajas list==", list);
		 return list;
	  }

var MapComp = function(){
	var domId="map-cont";
	var _center = [113.892436,23.416955];
	var _map;
	var _def_zoom=12;
	var _curr_zoom = _def_zoom;
	var _max_zoom=20;
	var _min_zoom=8;
	this.init = function(){
        _map = new AMap.Map(domId, {
        	mapStyle: "amap://styles/dark"	,
            zoom:13,//级别 _curr_zoom
            center:[113.889348,23.415269],//中心点坐标  _center
            expandZoomRange:true,
            zooms:[_min_zoom,_max_zoom],
            layers:[
//              new AMap.TileLayer.Satellite(),
//              new AMap.TileLayer.RoadNet()
            ]
        });
        map=_map;
	};
	}


//特殊人群轨迹
var groupmarkergg=[];
var rotationLine = [];
var polyline
function getgroupguiji(id){
    if($("#illegal").hasClass("active")){//与两违有冲突，画线钱先屏蔽两违
        $.each(illegalmarker,function(idx,item){
            item.setMap(null);
        })

        if(infoWindow){
            infoWindow.close();
        }
        $(this).removeClass("active")
    }


    if(polyline){
        map.remove(polyline);
    }
    groupmarkergg=[];
    rotationLine = [];
    $.ajax({
        url:'/baiyi/pushdataLocation/getListSpecialPopulationPosition',// '/api/special/populationPosition/getListSpecialPopulationPosition',
        dataType: 'json',
        type: 'Get',
        data:{
            imei:id
        },
        success: function(result) {
            $.each(result.data,function(idx,item) {
                //var latLon=GPS.WGStoGCJ(item.lon_lat.split(',')[1],item.lon_lat.split(',')[0]);
                //item.push("index",i)


                if (idx == result.data.length - 1){
                    var i = idx+1;
                    item["index"]=i+",最后一次定位";
                }else{
                    item["index"]=idx+1;
                }
                rotationLine.push([item.lon,item.lat]);
                var markerGJ=new AMap.Marker({
                    position:new AMap.LngLat(item.lon,item.lat),
                    offset:new AMap.Pixel(-10, -10),
                    icon:'img/7_33.png',//img/poi-marker-default.png  -13, -30
                    data:item
                })
                map.add(markerGJ);
                groupmarkergg.push(markerGJ);
                markerGJ.on('click',getgroupInfoGuiji);
            });

            //设置轨迹
            // rotationLine.push([116.397428, 39.90923]);
            // rotationLine.push([116.697428, 39.93923]);
            // rotationLine.push([116.777428, 39.98923]);
            // rotationLine.push([113.8230442, 22.6619523]);
            // rotationLine.push([113.823285, 22.6613631]);
            // rotationLine.push([113.8301874, 22.648087]);
            // 绘制轨迹    Polyline是绘制线
            polyline = new AMap.Polyline({
                map: map,
                path: rotationLine,  //设置线的经纬点集合,会以数组中的从第0个到最后个 经纬点连起来
                strokeColor: "#00A",  //线颜色
                strokeWeight: 3,      //线宽
            });
            //map.add(marker);
            polyline.setMap(map)
            //设置地图自适应可见范围
            map.setFitView();
            // //轨迹回放 动画播放
            // marker.moveAlong(rotationLine, 5000);
        }
    })
}
var getgroupInfoGuiji=function(e){
    var data=e.target.get("data");
    var content=["<p class='pop_tl'>特殊人群轨迹</p><div class='pop_box'><table></tbody class>"+
    "<tr><td style='width:85px;'>轨迹顺序：</td><td>"+data.index+"</td></tr>"+
    "<tr><td style='width:85px;'>城市：</td><td>"+data.city+"</td></tr>"+
    "<tr><td style='width:85px;'>地址：</td><td>"+data.address+"</td></tr>"+
    "<tr><td style='width:85px;'>经纬度：</td><td>"+data.lon+","+data.lat+"</td></tr>"+
    "<tr><td style='width:85px;'>时间：</td><td>"+data.timeBegin+"</td></tr>"+

    "</tbody></table></div>"];
    infoWindow = new AMap.InfoWindow({
        content: content.join("<br>")
    });
    infoWindow.open(map, e.target.get("position"));

}