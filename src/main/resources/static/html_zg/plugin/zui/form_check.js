
;
(function($) {
	// 默认设置
	var defaults = {
		// 消息
		formId : '',
		// 需校验的表单元素
		arr : []
	};

	// 初始化绑定表单验证
	$.fn.form_bind_check = function() {
		var id = $(this).attr('id');
		configs[id] = $.extend({}, defaults, {
			formId : id,
			arr : []
		});
		scanForm(configs[id]);
		var arr = configs[id].arr;
		for (var i = 0; i < arr.length; i++) {
			bindRequiredSign(arr[i]);
			bindBlur(arr[i]);
		}
	};

	// 表单提交验证
	$.fn.form_submit_check = function() {
		var flag = true;
		var id = $(this).attr('id');
		var arr = configs[id].arr;
		for (var i = 0; i < arr.length; i++) {
			var flag2 = check(arr[i]);
			flag = flag2 && flag;
		}
		return flag;
	}

	// 重置form后清空所有错误提示
	$.fn.form_clear_all_err = function() {
		var id = $(this).attr('id');
		var arr = configs[id].arr;
		for (var i = 0; i < arr.length; i++) {
			clearError(arr[i]);
		}
	}

	// 表单提交验证
	$.fn.form_custom_error = function(id, info) {
		var e = {
			id : id,
			rs : '',
			me : $("#" + id)
		};
		findParent($("#" + id), e);
		showError(e, info);
	}

	// 重置form后清空所有错误提示
	$.fn.form_clear_one_err = function(id) {
		var e = {
			id : id,
			rs : '',
			me : $("#" + id)
		};
		findParent($("#" + id), e);
		clearError(e);
	}

	// 表单扫描有效校验元素
	function scanForm(c) {
		var es = [ "input", "select", "textarea" ];
		for (var i = 0; i < es.length; i++) {
			$("#" + c.formId).find(es[i]).each(function() {
				if ($(this).attr('type') == 'hidden') {
					return;
				}
				var o = {
					id : $(this).attr('id'),
					rs : [],
					me : $(this)
				};
				var ck = $(this).attr('ck');
				if (ck) {
					o.rs = ck.toUpperCase().split('|');
				}
				findParent($(this), o);
				c.arr.push(o);
			});
		}

	}

	// 查找顶级元素节点form-group
	function findParent(e, o) {
		var p = e.parent();
		if (p&&p.attr('class')) {
			if (p.attr('class').indexOf('form-group') != -1) {
				o.group = p;
			} else {
				findParent(p, o);
			}
		}
	}

	// 元素标记必填*
	function bindRequiredSign(e) {
		var label = e.me.parent().prev();
		var s = label.attr("class");
		for (var i = 0; i < e.rs.length; i++) {
			if ("N" == e.rs[i]||"NS" == e.rs[i]) {
				if (-1 == s.indexOf("required")) {
					label.attr("class", s + " required");
				}
				flag = true;
				break;
			}
		}

		// 动态取消必选后，重新绑定需要取消必填*
		if (!flag) {
			if (-1 != s.indexOf("required")) {
				label.attr("class", s.replace("required", "").trim());
			}
		}

	}

	// 添加焦点事件
	function bindBlur(e) {
		//chosen事件
		if(e.rs[0]&&e.rs[0]=="NS"){
			//var sid = e.id+"_chosen";
			$('#'+e.id).on('change', function(o){
				window.MySetTimeout(check, 200, e);
			});
		}else{
			if(e.me[0].tagName=="SELECT"){ 
				$('#'+e.id).on('change', function(o){
					clearError(e);
				});
				
			}else{
				e.me.blur(e, function(param) {
					window.MySetTimeout(check, 200, param.data);
				});
			}
		}
				
	}

	// 元素校验
	function check(e) {
		var flag = true;
		var v = e.me.val();
		for (var i = 0; i < e.rs.length; i++) {
			var k = e.rs[i];
			if ("N" == k && !check_null(v)) {
				showError(e, '不能为空！');
				flag = false;
				break;
			} else if ("NS" == k) {//特殊控件（chosen控件）
				var sid = e.id+"_chosen";
				var names = '';
				$("#"+sid).find("span").each(function(){
					names = names + $(this).text()+",";
				});
				if(names==''){
					showError(e, '不能为空！');
					flag = false;
				}
				break;
			} else if ("S" == k && !check_security(v)) {
				showError(e, '限制只能输入字母、数字或下划线！');
				flag = false;
				break;
			} else if ("I" == k && !check_int(v)) {
				showError(e, '必须为正整数！');
				flag = false;
				break;
			} else if ("F" == k && !check_float(v)) {
				showError(e, '必须为浮点数！');
				flag = false;
				break;
			} else if ("E" == k && !check_email(v)) {
				showError(e, '不符合邮件地址规则！');
				flag = false;
				break;
			} else if ("P" == k && !check_phone(v)) {
				showError(e, '不符合手机号码规则！');
				flag = false;
				break;
			} else if ("U" == k && !check_url(v)) {
				showError(e, '不符合网络地址规则！');
				flag = false;
				break;
			} else if ("L" == k.Left(1)) {
				var s = k.substr(1).replace("[", "").replace("]", "");
				var ss = s.split("-");
				if (ss[0] == '' && ss[1] != '') {
					if (!check_len(v, -1, ss[1])) {
						showError(e, '输入不能超过' + ss[1] + '个字符！');
						flag = false;
						break;
					}
				} else if (ss[0] != '' && ss[1] == '') {
					if (!check_len(v, ss[0], -1)) {
						showError(e, '输入不能少于' + ss[0] + '个字符！');
						flag = false;
						break;
					}
				} else if (!check_len(v, ss[0], ss[1])) {
					showError(e, '输入字符长度要求在' + ss[0] + '-' + ss[1] + '之间！');
					flag = false;
					break;
				}
			}
		}

		if (flag) {
			clearError(e);
		}
		return flag;
	}

	// 显示错误信息
	function showError(e, info) {
		var div = e.me.parent(); 
		var s = div.attr("class");
		if (-1 == s.indexOf("has-error")) {
			div.attr("class", s + " has-error");
		}
 
		var label = e.group.find("label").first();
		var errInfo = label.html() + info;
		 
		var errDiv = div.find(".text-danger").first();
		if (errDiv.length == 0) {
			div.append('<i class="icon icon-times text-danger" ' +
					'data-toggle="tooltip" title="'+errInfo+'" ' +
					'data-tip-class="tooltip-danger" ' +
					'data-placement="top"></i>'); 
			$('[data-toggle="tooltip"]').tooltip(); 
		} else {
			errDiv.attr("title",errInfo);
			errDiv.attr("data-original-title",errInfo);
		}
		$('[data-toggle="tooltip"]').tooltip(); 

	}

	// 清除错误
	function clearError(e) {
		var div = e.me.parent(); 
		var s = div.attr("class");
		if(!s){
			return;
		}
		if (-1 != s.indexOf("has-error")) {
			s = s.replace("has-error", "").trim();
			div.attr("class", s);
		}
   
		var errDiv = div.find(".text-danger").first();
		if (errDiv.length != 0) {
			errDiv.remove();
		}
	}

	var configs = [];
})(jQuery);

// ----------------------------------各验证函数
function check_null(v) {// 验证非空
	return v != null && v.trim() != '';
}

function check_security(v) {// 字母、数字、下划线
	var re = /^[a-zA-Z0-9_]{1,}$/;
	return v == '' || re.test(v);
}

function check_int(v) {// 验证正整数
	var re = /^[0-9]{1,}$/;
	return v == '' || re.test(v);
}

function check_float(v) {// 验证正浮点数
	var re = /^[0-9\.]{1,}$/;
	return v == '' || re.test(v) && !isNaN(v);
}

function check_email(v) {// 验证邮箱
	var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
	return v == '' || re.test(v);
}

function check_phone(v) {// 验证电话号码
	var re = /^(1[3-8]+\d{9})$/;
	return v == '' || re.test(v);
}

function check_url(v) {// 验证网络地址
	return v == '' || v.indexOf('http://') == 0;
}

function check_len(v, begin, end) {// 验证长度
	var flag1 = true, flag2 = true;
	if (v != '') {
		var n = v.length;
		if (begin != -1) {
			if (n < begin) {
				flag1 = false;
			}
		}
		if (end != -1) {
			if (n > end) {
				flag2 = false;
			}
		}
	} else {
		return true;
	}
	return flag1 && flag2;
}

// 重定义setTimeout函数
var ____sto = setTimeout;
window.MySetTimeout = function(callback, timeout, param) {
	var args = Array.prototype.slice.call(arguments, 2);
	var _cb = function() {
		callback.apply(null, args);
	}
	____sto(_cb, timeout);
}