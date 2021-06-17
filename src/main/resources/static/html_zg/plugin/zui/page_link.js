;
(function($) {
	// 默认设置
	var defaults = {
		// 页面大小定制
		pageSizeArea : [ 5, 10, 20, 30, 40, 50 ],
		// 当前页
		page : 1,
		// 页面大小
		pageSize : 10,
		// 记录总数
		count : 0,
		// 页面总数
		pageCount : 0,
		// 是否禁用分页码选择 默认不禁用
		pageSelect : true,
		// 回调查询方法
		queryFun : null,
		// 首次是否自动调用
		autoFirstCall : true
	};

	// 初始化分页导航条
	$.fn.pager_init = function(options) {
		var id = $(this).attr('id');
		configs[id] = $.extend({}, defaults, configs[id], options);
		var o = configs[id];
		o.id = id;
		count_page(o);
		refresh(o);
		if (o.autoFirstCall) {
			exexQuery(o);
		}
	};

	// 设置记录总条数
	$.fn.pager_set_count = function(n) {
		var id = $(this).attr('id');
		var o = configs[id];
		o.count = n;
		count_page(o);
		refresh(o);
	}

	// 查询方法调用
	$.fn.pager_call = function() {
		var id = $(this).attr('id');
		var o = configs[id];
		o.page = 1;
		exexQuery(o);
	}

	// 移动页数
	$.fn._pager_move = function(id, op) {
		var o = configs[id];
		var page = o.page;
		var pageCount = o.pageCount;
		if (op == 'first') {
			if (page > 1) {
				page = 1;
			}
		} else if (op == 'pre') {
			if (page > 1) {
				page--;
			}
		} else if (op == 'next') {
			if (page < pageCount) {
				page++;
			}
		} else if (op == 'last') {
			if (page < pageCount) {
				page = pageCount;
			}
		}
		o.page = page;
		refresh(o);
		exexQuery(o);
	}

	// 改变每页条数
	$.fn._pager_size_changed = function(id, n) {
		var o = configs[id];
		o.pageSize = o.pageSizeArea[n];
		o.page = 1;
		count_page(o);
		refresh(o);
		exexQuery(o);
	}

	// 计算页面总数
	function count_page(o) {
		o.pageCount = Math.floor(o.count / o.pageSize) + (o.count % o.pageSize == 0 ? 0 : 1);
	}

	// 刷新导航条
	var scount = 0;
	function refresh(o) {
		scount++;
 		var id = o.id;
		var sid = 'table_nav_' + o.id;
		if ($("#" + sid).length == 0) {
			$('#' + id).after('<div id="' + sid + '" style="margin-right: 20px;"></div>');
		}  
		if (o.pageCount == 0) {
			var s = "";
			if(scount > 1){
				 s = '<div style="text-align: center; margin-right: 20px; margin-top: 6px;font-style:bold;">查询结果为空！</div>';
			}else {
				 s = '<div style="text-align: center; margin-right: 20px; margin-top: 6px;font-style:bold;">正在查询...</div>';
			}
			 
			$('#' + sid).html(s);
		} else {
			var c1 = '', c2 = '', c3 = '', c4 = '';
			if (o.pageCount == 0 || o.page == 1) {
				c1 = ' disabled';
			}
			if (o.pageCount == 0 || o.page == 1) {
				c2 = ' disabled';
			}
			if (o.pageCount == 0 || o.page == o.pageCount) {
				c3 = ' disabled';
			}
			if (o.pageCount == 0 || o.page == o.pageCount) {
				c4 = ' disabled';
			}

			var s = '';
			s += '	<ul class="pager" style="float: right; margin: 0;">';
			s += '		<li class="previous' + c1 + '"><a href="javascript:$.fn._pager_move(\'' + id + '\',\'first\');">首页</a></li>';
			s += '		<li class="' + c2 + '"><a href="javascript:$.fn._pager_move(\'' + id + '\',\'pre\');">上一页</a></li>';
			s += '		<li class="disabled"><a style="color: black;">' + o.page + '/' + o.pageCount + '</a></li>';
			s += '		<li class="' + c3 + '"><a href="javascript:$.fn._pager_move(\'' + id + '\',\'next\');">下一页</a></li>';
			s += '		<li class="next' + c4 + '"><a href="javascript:$.fn._pager_move(\'' + id + '\',\'last\');">尾页</a></li>';
			s += '	</ul>';
			
			if(o.pageSelect){
				s += '	<select class="form-control" style="float:right;width:auto;margin-right:20px;" onchange="$.fn._pager_size_changed(\'' + id + '\',this.selectedIndex);">';
				for (var i = 0; i < o.pageSizeArea.length; i++) {
					var n = o.pageSizeArea[i];
					var c = o.pageSize == n ? " selected=\"selected\"" : "";
					s += '		<option' + c + '>' + n + '</option>';
				}
				s += '	</select>';
				s += '	<div style="float: right;  margin-top: 6px;">共' + o.count + '条，每页&nbsp;</div>';
			} 
			$('#' + sid).html(s);
		}

	}

	// 执行查询方法
	function exexQuery(o) {
		if (o.queryFun) {
			o.queryFun(o.page, o.pageSize, o.id);
		}
	}

	var configs = [];

})(jQuery);
