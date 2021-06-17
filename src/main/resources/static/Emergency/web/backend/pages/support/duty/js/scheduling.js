Date.prototype.Format = function(fmt)
{ //author: meizz
	var o = {
		"M+" : this.getMonth()+1,                 //月份
		"d+" : this.getDate(),                    //日
		"h+" : this.getHours(),                   //小时
		"m+" : this.getMinutes(),                 //分
		"s+" : this.getSeconds(),                 //秒
		"q+" : Math.floor((this.getMonth()+3)/3), //季度
		"S"  : this.getMilliseconds()             //毫秒
	};
	if(/(y+)/.test(fmt))
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("("+ k +")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	return fmt;
}

var prefix = "/scheduling"
var f_date=new Date().Format('yyyy-MM-dd');

$(function() {
	load();
	initCalendar();


});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/query", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						pageList: [10, 25, 50, 100],
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						uniqueId: "id",
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: this.pageSize,   //页面大小
								offset:this.pageNumber, //页码
								scheduling_date:f_date
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
								{
									checkbox : true
								},
								{
									field : 'username',
									title : '姓名'
								},
								{
									field : 'deptname',
									title : '部门'
								},
								{
									field : 'position',
									title : '职位'
								},
								{
									field : 'mobile',
									title : '联系电话'
								}
								,
								{
									field : 'work',
									title : '工作事项'
								}
								,
								{
									title : '操作',
									field : 'id',
									align : 'center',
									width : 80,
									formatter : function(value, row, index) {
										var html = "";
										html += '<a class="btn btn-primary btn-sm" style="margin-right: 10px;" onclick="edit(\''+row.id+'\')">修改</a>';
										return html ;
									}
								}


								 ]
					});
}
function reLoad() {
	initCalendar();
	$('#exampleTable').bootstrapTable('refresh');
}

function reLoadBootstrapTable() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '1200px', '520px' ],
		content :  'add.html' // iframe的url
		,btn: ['确定', '关闭']
		,btnAlign: 'c'
		,yes: function(index){
			//当点击‘确定’按钮的时候，获取弹出层返回的值
			var res = window["layui-layer-iframe" + index].callbackdata();
			//打印返回的值，看是否有我们想返回的值。
			addScheduling(res)
			//最后关闭弹出层
			layer.close(index);
		},
		cancel: function(){
			layer.closeAll();
			//右上角关闭回调
		}
	});
}
function edit(id) {
	var data = $('#exampleTable').bootstrapTable('getRowByUniqueId',id);//行的数据
	editDate = data;//传值到子页面
	layer.open({
		type: 2,
		title: '编辑事项信息',
		shadeClose: true,
		shade: 0.4,
		area: ['50%', '50%'],
		content: 'workForm.html'
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
		// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/deleteBatch',
			success : function(r) {
				if (r>0) {
					layer.msg("删除成功");
					reLoad();
				} else {
					layer.msg("删除失败");
				}
			}
		});
	}, function() {

	});
}


function initCalendar() {
	$.ajax({
		type : 'get',
		url : prefix + '/query',
		data:{'offset':1,'limit':10000},
		success : function(data) {
			if (data.rows.length > 0) {
				$(".fc-day .fc-day-content div ").html('');
			for (var i = 0; i < data.rows.length; i++) {
				$(".fc-day[data-date=" + data.rows[i].scheduling_date + "] .fc-day-content div ").html('<img src="img/calendar.svg" style="margin-left: 75px" ></div>');
			}
		}

		}
	});
}


function addScheduling(data){
	$.ajax({
		type : 'POST',
		data : {
			"data" : data,
			"f_date":f_date
		},
		url : prefix + '/insert',
		success : function(r) {

			if (r> 0) {
				//$(".fc-day[data-date="+data+"] .fc-day-content div ").html('<img src="img/calendar.svg" style="margin-left: 75px" ></div>');
				$(".fc-day[data-date="+data+"]").addClass('fc-cell-overlay');

				reLoad();

			} else {
				layer.msg("保存失败");
			}
		}
	});
}

