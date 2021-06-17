; 
(function($) {
	// checkbox选择器-键值型数据
	$.fn.checkbox = {
		bind : function(targetId, code, paraArr, ename, defs) {
			var p = "code=" + code;
			if (paraArr) {
				for (var i = 0; i < paraArr.length; i++) {
					p += "&paras=" + paraArr[i];
				}
			}
			var url = baseUrl + "/com/getKeyValueList";
			$.post(url, p, function callback(txt) {
				var json = $.parseJSON(txt);
				if (json.code == 0) {
					var arr = json.info;
					var s = '';
					for (var i = 0; i < arr.length; i++) {
						var o = arr[i];
						var c = '';
						if (defs) {
							for (var j = 0; j < defs.length; j++) {
								if (o.key == defs[j]) {
									c = 'checked = "checked"';
									break;
								}
							}
						}

						s += '<label class="checkbox-inline"><input type="checkbox" name="' + ename + '" value="' + o.key + '" ' + c + ' /> ' + o.value + '</label>&nbsp;&nbsp;&nbsp;';
					}
					$('#' + targetId).html(s);
				} else {
					doOtherCode(json.code);
				}
			});
		}
	}

	// 下拉选择器-键值型数据
	$.fn.select = {
		bind : function(targetId, code, paraArr, def, f) {
			var p = "code=" + code;
			if (paraArr) {
				for (var i = 0; i < paraArr.length; i++) {
					p += "&paras=" + paraArr[i];
				}
			}
			var url = baseUrl + "/com/getKeyValueList";
			(function(nurl,np,nf){
				$.post(nurl, np, function callback(txt) {
					var json = $.parseJSON(txt);
					if (json.code == 0) {
						var arr = json.info;
						var s = '<option value="">-请选择-</option>';
						for (var i = 0; i < arr.length; i++) {
							var o = arr[i];
							var c = def == o.key ? ' selected="selected"' : '';
							s += '<option value="' + o.key + '"' + c + '>' + o.value + '</option>';
						}
						$('#' + targetId).html(s);
					} else {
						doOtherCode(json.code);
					}
					
					if(nf){
						nf(json);
					}
				});
			})(url,p,f);
		}
	}

	// 自定义弹出框
	$.fn.win = {
		// 默认设置
		default_win : {
			targetId : '',// 实例ID
			url : '',// 地址
			title : '自定义弹出框',
			width : 600,
			height : 400,
			openHandler : null,
			backFun : null
		},

		// 绑定并初始化
		bind : function(triggerId, url, title, width, height, f) {
			var o = $.fn.win._mixConfig(url, title, width, height, f);

			// 绑定事件
			$('#' + triggerId).bind("click", o, function(e) {
				$.fn.win._open(e.data);
			});

		},

		// 直接开启窗口
		open : function(url, title, width, height, f) { 
			var o = $.fn.win._mixConfig(url, title, width, height, f);
			$.fn.win._open(o);
		},

		// 回调函数
		back : function() {
			var n = arguments.length;
			var o = configs[arguments[0]];
			// 回调
			if (o.backFun) {
				var arr = [];
				for (var i = 1; i < arguments.length; i++) {
					arr[i - 1] = arguments[i];
				}
				o.backFun(arr);
			}
			// 关闭
			if (o.openHandler) {
				o.openHandler.close();
			}
		},

		// 合并参数
		_mixConfig : function(url, title, width, height, f) {
			var o = $.extend({}, $.fn.win.default_win);
			o.targetId = getRandId();
			o.url = url;
			o.title = title ? title : o.title;
			o.width = width ? width : o.width;
			o.height = height ? height : o.height;
			o.backFun = f;

			// 再加工处理
			if (o.url.indexOf("?") == -1) {
				o.url = o.url + "?targetId=" + o.targetId;
			} else {
				o.url = o.url + "&targetId=" + o.targetId;
			}
			o.title = getOpenTitle(o.title);
			configs[o.targetId] = o;
			return o;
		},

		// 打开窗体
		_open : function(o) {
			// 绑定事件
			o.openHandler = new $.zui.ModalTrigger({
				toggle : "modal",
				backdrop : false,
				title : o.title,
				iframe : o.url,
				height : o.height + "px",
				width : o.width + "px",
				moveable : true
			});
			o.openHandler.show();
		}

	}
	var configs = [];
})(jQuery);