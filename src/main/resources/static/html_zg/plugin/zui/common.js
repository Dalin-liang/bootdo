

// 同页跳转
function setExTitle(exStr) {
	if (window.parent.currentNav) {
		$("#nav").html(window.parent.currentNav + "&nbsp;&gt;&nbsp;" + exStr);
	}
}

// 获取弹出窗口标题
function getOpenTitle(exStr) {
	if (window.parent.currentNav) {
		return '<i class="icon-location-arrow"></i><span style="margin-left:5px;">' + window.parent.currentNav + "&nbsp;&gt;&nbsp;" + exStr + '</span>';
	} else {
		return '<i class="icon-location-arrow"></i><span style="margin-left:5px;">' + exStr + '</span>';
	}
}

// 获取唯一不重复ID
function getRandId() {
	return "id_" + new Date().getTime() + parseInt(Math.random() * 10000);
}

//处理通用服务端异常json.code逻辑
function doOtherCode(code) {
	var url = baseUrl + "/sys/error?code=" + code;
	if (window.parent.parent.parent.parent.parent) {
		window.parent.parent.parent.parent.parent.location.href = url;
	} else if (window.parent.parent.parent.parent) {
		window.parent.parent.parent.parent.location.href = url;
	} else if (window.parent.parent.parent) {
		window.parent.parent.parent.location.href = url;
	} else if (window.parent.parent) {
		window.parent.parent.location.href = url;
	} else if (window.parent) {
		window.parent.location.href = url;
	} else if (window) {
		window.location.href = url;
	}
}

// =========================================字符串函数
// 拷贝json
function copyJson(json) {
	var s = JSON.stringify(json);
	return JSON.parse(s);
}

// 字符串左边截取
String.prototype.Left = function(len) {
	if (isNaN(len) || len == null) {
		len = this.length;
	} else {
		if (parseInt(len) < 0 || parseInt(len) > this.length) {
			len = this.length;
		}
	}

	return this.substr(0, len);
}

// 字符串右边截取
String.prototype.Right = function(len) {
	if (isNaN(len) || len == null) {
		len = this.length;
	} else {
		if (parseInt(len) < 0 || parseInt(len) > this.length) {
			len = this.length;
		}
	}

	return this.substring(this.length - len, this.length);
}

// 字符串去除左右空格
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

// =========================================日期格式化
// 毫秒日期格式化为yyyy-MM-dd HH:mm:ss
function formatDate(time) {
	var date = new Date(time);
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var H = date.getHours();
	var n = date.getMinutes();
	var s = date.getSeconds();
	return date.getFullYear() + "-" + (m < 10 ? ('0' + m) : m) + "-" + (d < 10 ? ('0' + d) : d) + " " + (H < 10 ? ('0' + H) : H) + ":" + (n < 10 ? ('0' + n) : n) + ":" + (s < 10 ? ('0' + s) : s);
}

// 毫秒格式化日期为yyyy-MM-dd
function formatShortDate(time) {
	var date = new Date(time);
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return date.getFullYear() + "-" + (m < 10 ? ('0' + m) : m) + "-" + (d < 10 ? ('0' + d) : d);
}

// yyyyMMddHHmmss日期格式化为yyyy-MM-dd HH:mm:ss
function formatDate2(s) {
	return s.substr(0, 4) + "-" + s.substr(4, 2) + "-" + s.substr(6, 2) + " " + s.substr(8, 2) + ":" + s.substr(10, 2) + ":" + s.substr(12, 2);
}


// yyyyMMddHHmmss日期格式化为yyyy-MM-dd
function formatShortDate2(s) {
	return s.substr(0, 4) + "-" + s.substr(4, 2) + "-" + s.substr(6, 2);
}

// 毫秒日期格式化为yyyyMMddHHmmss
function formatDate3(time) {
	var date = new Date(time);
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var H = date.getHours();
	var n = date.getMinutes();
	var s = date.getSeconds();
	return date.getFullYear() + "" + (m < 10 ? ('0' + m) : m) + "" + (d < 10 ? ('0' + d) : d) + "" + (H < 10 ? ('0' + H) : H) + "" + (n < 10 ? ('0' + n) : n) + "" + (s < 10 ? ('0' + s) : s);
}
// 毫秒日期格式化为yyyy年MM月dd日HH时mm分ss秒
function formatDate4(time) {
	var date = new Date(time);
	var m = date.getMonth() + 1;
	var d = date.getDate();
	var H = date.getHours();
	var n = date.getMinutes();
	var s = date.getSeconds();
	return date.getFullYear() + "年" + (m < 10 ? ('0' + m) : m) + "月" + (d < 10 ? ('0' + d) : d) + "日" + (H < 10 ? ('0' + H) : H) + "时" + (n < 10 ? ('0' + n) : n) + "分" + (s < 10 ? ('0' + s) : s)+"秒";
} 


// =========================================页面数据缓存
var tmpCache = new Array();
// 添加缓存对象
function putCache(basekey, exKey, obj) {
	removeCache(basekey);
	var o = new Object();
	o.basekey = basekey;
	o.exKey = exKey;
	o.value = obj;
	tmpCache.push(o);
}
// 获取缓存对象
function getCache(basekey, exKey) {
	var v = null;
	for (var i = 0, len = tmpCache.length; i < len; i++) {
		var o = tmpCache[i];
		if (o.basekey == basekey && o.exKey == exKey) {
			v = o.value;
			break;
		}
	}
	return v;
}
// 删除缓存对象
function removeCache(basekey) {
	for (var i = tmpCache.length - 1; i >= 0; i--) {
		var o = tmpCache[i];
		if (o.basekey == basekey) {
			tmpCache.splice(i, 1);
		}
	}
}

// =========================================checkbox函数
// 多选框全选或全取消
function checkAll(flag, name) {
	var cbs = document.getElementsByName(name);
	if (cbs.length) {
		for (var i = 0; i < cbs.length; i++) {
			if (!cbs[i].disabled) {
				cbs[i].checked = flag;
			}

		}
	} else {
		if (!cbs.disabled) {
			cbs.checked = flag;
		}
	}
}

// 设置某值多选框选中
function setCheck(id, name) {
	var cbs = document.getElementsByName(name);
	if (cbs.length) {
		for (var i = 0; i < cbs.length; i++) {
			if (!cbs[i].disabled) {
				cbs[i].checked = cbs[i].value == id;
			}

		}
	} else {
		if (!cbs.disabled) {
			cbs.checked = cbs.value == id;
		}
	}
}

// 判断多选框是否选中
function ifCheck(name) {
	var r = false;
	var cbs = document.getElementsByName(name);
	if (cbs.length) {
		for (var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				r = true;
				break;
			}
		}
	} else {
		r = cbs.checked;
	}
	return r;
}

// 获取多选框数据
function getCheckValues(name) {
	var t = new Array();
	var cbs = document.getElementsByName(name);
	if (cbs.length) {
		for (var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				t[t.length] = cbs[i].value;
			}
		}
	} else {
		if (cbs.checked) {
			t[t.length] = cbs.value;
		}
	}
	return t;
}

// 获取多选框数据
function getCheckValuesForName(name) {
	var t = "";
	var cbs = document.getElementsByName(name);
	if (cbs.length) {
		for (var i = 0; i < cbs.length; i++) {
			if (cbs[i].checked) {
				t = t + cbs[i].value+',';
			} 
		}
	} else {
		if (cbs.checked) {
			t = cbs.value;
		}
	}
	return t;
}


//只允许输入正整数
function outNum(o){
	o.value=o.value.replace(/\D/gi,"");
	if(!o.value||o.value==''){
		o.value = 0;
	}
} 

//错误消息
function showError(msg){
	var options = {type:'danger',time:2000,placement:"bottom-right"};
	new $.zui.Messager(msg, options).show();
}
//成功消息
function showSuccess(msg){
	var options = {type:'success',time:2000,placement:"bottom-right"};
	new $.zui.Messager(msg, options).show();
}
//警告消息
function showWarning(msg){
	var options = {type:'warning',time:2000,placement:"bottom-right"};
	new $.zui.Messager(msg, options).show();
}
//输出页面log
function showLog(log){
  var logger;
  if (window.WEB_SOCKET_LOGGER) {
    logger = WEB_SOCKET_LOGGER;
  } else if (window.console && window.console.log && window.console.error) {
    logger = window.console;
  } else {
    logger = {log: function(){ }, error: function(){ }};
  }
  if(logger){
    logger.log(log);
  }
}
	