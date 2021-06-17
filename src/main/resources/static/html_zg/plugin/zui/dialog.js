;
(function($) {
	// 默认设置
	var defaults = {
		// 消息
		msg : '',
		// 参数
		param : null,
		// 后续回调方法
		back : null,
		// 输入类型
		input_type : "test"
	};

	// 弹出警告对话框
	$.fn.showAlert = function(options) {
		var id = getRandId();
		configs[id] = $.extend({}, defaults, options, {
			tn : "_myshowAlert",
			id : id,
			type : 1,
			bt_ok : true,
			bt_cancel : false,
			head_msg : "提示信息"
		});
		baseShow(configs[id]);
	};

	// 弹出确认对话框
	$.fn.showConfirm = function(options) {
		var id = getRandId();
		configs[id] = $.extend({}, defaults, options, {
			tn : "_myshowConfirm",
			id : id,
			type : 2,
			bt_ok : true,
			bt_cancel : true,
			head_msg : "确认信息"
		});
		baseShow(configs[id]);
	};

	// 弹出确认对话框
	$.fn.showInput = function(options) {
		var id = getRandId();
		configs[id] = $.extend({}, defaults, options, {
			tn : "_myshowInput",
			id : id,
			type : 3,
			bt_ok : true,
			bt_cancel : true,
			head_msg : "输入框"
		});
		configs[id].head_msg = configs[id].msg;
		configs[id].msg = "<input type='" + configs[id].input_type + "' class='form-control' maxlength='20' id='" + id + "'/>";
		baseShow(configs[id]);
	};
	
	// 弹出审核对话框
	$.fn.showAudit = function(options) {
		var id = getRandId();
		configs[id] = $.extend({}, defaults, options, {
			tn : "_myshowAudit",
			id : id,
			bt_audit_pass : true,
			bt_audit_not_pass : true,
			head_msg : "灾前专报审核"
		});
		baseShow(configs[id]);
	};
	 
	// 点通过后回调函数  type="通过" type="不通过" 
	$.fn._dialog_click_audit = function(id,type) {
		var o = configs[id];
		o.handler.close();
		if(o.back){
			if(type=="通过"){
				o.back(configs[id].param,"1");
			}else if(type="不通过"){
				o.back(configs[id].param,"2");
			}
		}
	}

	// 点确定后回调函数
	$.fn._dialog_click_ok = function(id) {
		var o = configs[id];
		o.handler.close();
		if (o.back) {
			if (o.type == 1 || o.type == 2) {
				o.back(configs[id].param);
			} else if (o.type == 3) {
				var v = $('#' + id).val();
				o.back(configs[id].param, v);
			}

		}
	}

	// 获取内容
	function getShowContent(o) {
		var s = '<table width="100%">';
		s += "<tr><td height='40px'>";
		s += o.msg;
		s += "</td></tr>";
		s += "<tr><td style='text-align:right;padding-top:10px;'>"
		if (o.bt_cancel) {
			s += '<button type="button" class="btn btn-default"	data-dismiss="modal">取消</button>&nbsp;&nbsp;&nbsp;&nbsp;';
		}
		if (o.bt_ok) {
			s += '<button type="button" class="btn btn-primary"	onclick="$.fn._dialog_click_ok(\'' + o.id + '\');" id="_dialog_ok">确定</button>';
		} 
		if (o.bt_audit_not_pass) {
			s += '<button type="button" class="btn btn-primary"	onclick="$.fn._dialog_click_audit(\'' + o.id + '\',\'不通过\');" id="_dialog_ok">不通过</button>&nbsp;&nbsp;&nbsp;&nbsp;';
		}
		if (o.bt_audit_pass) {
			s += '<button type="button" class="btn btn-primary"	onclick="$.fn._dialog_click_audit(\'' + o.id + '\',\'通过\');" id="_dialog_ok">通过</button>';
		}
		
 		
		s += "</td></tr>";
		s += "</table>";
		return s;
	}

	// 显示基础对话框
	function baseShow(o) {
		o.handler = new $.zui.ModalTrigger({
			toggle : "modal",
			backdrop : false,
			title : o.head_msg,
			custom : getShowContent(o),
			height : "170px",
			width : "260px",
			waittime : true,
			pisition : "center"
		});
		o.handler.show({
			shown : function() {
				var _t = this;
				_t.lastChild.style.marginLeft = (_t.clientWidth - _t.lastChild.clientWidth) / 2 + "px";
			}
		});
		//用户体验更好，可以相应回车
		$('#_dialog_ok').focus();
	}

	var configs = [];
})(jQuery);
