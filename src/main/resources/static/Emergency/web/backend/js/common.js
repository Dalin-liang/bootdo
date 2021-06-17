function closeFrame(name){
    if(!name){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);//关闭当前页
    }else{
        var index = parent.layer.getFrameIndex(name);
        parent.layer.close(index);//关闭当前页
    }
}

function prefixInteger(num, n) {
	return (Array(n).join(0) + num).slice(-n);
}

//公用方法，时间戳转date字符串
function stampToDateStr(stamp,type){
    stamp=parseInt(stamp);
    switch(type){
        case "date":
            return new Date(stamp).Format("yyyy-MM-dd");
            break;
        case "datetime":
            return new Date(stamp).Format("yyyy-MM-dd hh:mm:ss");
            break;
        default:
            return new Date(stamp).Format(type);
            break;
    }
}

//公用方法，时间字符串转换
function strToStr(str,type){
    switch(type){
        case "date":
            return new Date(str).Format("yyyy-MM-dd");
            break;
        case "datetime":
            return new Date(str).Format("yyyy-MM-dd hh:mm:ss");
            break;
        default:
            return new Date(str).Format(type);
            break;
    }
}