function weekday(_day){
	var weekDay=["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	return weekDay[_day];
}
function prefixInteger(num, n) {
	return (Array(n).join(0) + num).slice(-n);
}

/*function loadEventP(data){
    $("#planList").empty();
    data.sort((a, b) => new Date(b.handleTime).getTime() - new Date(a.handleTime).getTime());
    for(var i in data){
        if(data[i].eventState!=1){
            if(i==0){
                $("#planList").append("<div class='dialog1 warn_li active' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                    " <a class='btn btn_blue btn-xs pull-right' id='plantbtn'>预案</a></div>"+
                    "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                    "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                )
            }else{
                $("#planList").append("<div class='dialog1 warn_li mt10' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                    " <a class='btn btn_blue btn-xs pull-right' id='plantbtn'>预案</a></div>"+
                    "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                    "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                )
            }
        }else{
            if(i==0){
                $("#planList").append("<div class='dialog1 warn_li active' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                    " </div>"+
                    "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                    "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                )
            }else{
                $("#planList").append("<div class='dialog1 warn_li mt10' dataId='"+data[i].eventId+"' actionId='"+(data[i].actionProgramId?data[i].actionProgramId:'')+"'><div class='warn_li_tl colornum clearfix'>ID："+data[i].eventId+
                    " </div>"+
                    "<div class='warn_li_m pad5 line-clamp mt10'>"+data[i].eventDesc+"</div>"+
                    "<div class='warn_li_b font12 mt10'>报告单位："+data[i].repDept+"   ["+data[i].repTime+"] </div></div>"
                )
            }
        }

    }
    $("b.colornum").text(data.length);
}*/

function layerReload(layerIndex,parent) {
    if(!parent){
        window['layui-layer-iframe' + layerIndex].location.reload();
    }else{
        window.parent['layui-layer-iframe' + layerIndex].location.reload();
    }
}

function closeFrame(name){
    if(!name){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);//关闭当前页
    }else{
        var index = parent.layer.getFrameIndex(name);
        parent.layer.close(index);//关闭当前页
    }
}

var Scroll=function(content){
    this.inte=0
   this._content=content;
   this._scroll=function(){
       var scroll=$(content)[0].scrollHeight;
        var height=$(content).parent().height();
        var sT=$(content).parent().scrollTop();
        if(sT+height>=scroll){
            sT=0;
            $(content).parent().scrollTop(sT);
        }else{
            sT+=2;
            $(content).parent().scrollTop(sT);
        }
   }
   this.start= function(){
       if(this.inte==0){
           inte=setInterval(this._scroll,60);
           parent.inte=inte;
       }else{
           clearInterval(this.inte);
           inte=setInterval(this._scroll,60);
           parent.inte=inte;
       }
   }
   this.stop=function(){
        clearInterval(parent.inte);
    }
};
var Scroll1=function(content1){
    this.inte1=0
   this._content1=content1;
   this._scroll1=function(){
       var scroll1=$(content1)[0].scrollHeight;
        var height1=$(content1).parent().height();
        var sT1=$(content1).parent().scrollTop();
        if(sT1+height1>=scroll1){
            sT1=0;
            $(content1).parent().scrollTop(sT1);
        }else{
            sT1+=2;
            $(content1).parent().scrollTop(sT1);
        }
   }
   this.start1= function(){
       if(this.inte1==0){
           inte1=setInterval(this._scroll1,60);
           parent.inte1=inte1;
       }else{
           clearInterval(this.inte1);
           inte1=setInterval(this._scroll1,60);
           parent.inte1=inte1;
       }
   }
   this.stop1=function(){
        clearInterval(parent.inte1);
    }
};

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