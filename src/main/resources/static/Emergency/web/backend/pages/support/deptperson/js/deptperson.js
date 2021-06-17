
var prefix = "/deptPerson"
$(function() {
	load();
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
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								pageSize: params.limit,
								pageNumber:params.offset,
					            name:$('#name').val(),
								deptName:$('#deptName').val(),
								position:$('#position').val(),
								is_duty:1
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
									field : 'xuhao',
									title : '序号',
									formatter: function (value, row, index) {
										//获取每页显示的数量
										var pageSize2=$('#exampleTable').bootstrapTable('getOptions').pageSize;
										//获取当前是第几页
										var pageNumber2=$('#exampleTable').bootstrapTable('getOptions').pageNumber;
										//返回序号，注意index是从0开始的，所以要加上1
										return pageSize2 * (pageNumber2 - 1) + index + 1;
									}
								},
																{
									field : 'dept',
									title : '部门'
								},
																{
									field : 'name', 
									title : '姓名' 
								},
																{
								field : 'position',
								title : '职位'
								},							{
									field : 'mobile',
									title : '手机号码'
								},

																{
									field : 'remarks', 
									title : '备注' 
								},
								{
									title : '操作',
									field: 'btn',
									align : 'center',
									formatter :rowOption,
									events: window.operateEvents

								} ]
					});
}

window.operateEvents = {

	'click .edit': function (e, value, row, index) {
		sessionStorage.setItem("edit","true");
		sessionStorage.setItem("row",JSON.stringify(row));
		layer.open({
			type : 2,
			title : '编辑',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '800px', '400px' ],
			content : 'edit.html', // iframe的url
			success:function (layero,index){
				var body=layer.getChildFrame('body',index);
				body.find("#id").val(row.id);
				body.find("#name").val(row.name);
				body.find("#dept").val(row.dept_id);
				body.find("#sysUser").val(row.user_id);
				body.find("#mobile").val(row.mobile);
				body.find("#sex").val(row.sex);
				body.find("#position").val(row.position);
				body.find("#remarks").val(row.remarks);
				var iframeWin = window[layero.find('iframe')[0]['name']];
				iframeWin.renderForm();
			}

		});
	},
	'click .read': function (e, value, row, index) {
		sessionStorage.setItem("edit","true");
		sessionStorage.setItem("row",JSON.stringify(row));
		layer.open({
			type : 2,
			title : '查看',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '800px', '400px' ],
			content : 'edit.html?state=read', // iframe的url
			success:function (layero,index){
				var body=layer.getChildFrame('body',index);
				body.find("#id").val(row.id);
				body.find("#name").val(row.name);
				body.find("#dept").val(row.dept_id);
				body.find("#sysUser").val(row.user_id);
				body.find("#mobile").val(row.mobile);
				body.find("#sex").val(row.sex);
				body.find("#position").val(row.position);
				body.find("#remarks").val(row.remarks);
				body.find("#btn").css('display',"none");
				var iframeWin = window[layero.find('iframe')[0]['name']];
				iframeWin.renderForm();
				body.find("input,select,textarea").attr('disabled','disabled');

			}
		});
	},
	'click .like': function (e, value, row, index) {
		$table.bootstrapTable('remove', {
			field: 'id',
			values: [row.id]
		})
	}
}


function rowOption(value, row, index){
	return [

		'<button class="layui-btn layui-btn-primary layui-btn-sm edit">修改</button>' +
		'<button class="layui-btn layui-btn-sm read" )">查看</button>'+
		'<button class="layui-btn layui-btn-danger layui-btn-sm" class="delete" onclick="remove(\''+row.id+'\')">删除</button>'
	]
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '400px' ],
		content : 'add.html' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content :  'edit.html' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : "/deptPerson/delete",
			type : "post",
			data : {
				'id' : id
			},
			success : function(r) {
				if (r>0) {
					layer.msg("删除成功");
					reLoad();
				}else{
					layer.msg("删除失败");
				}
			}
		});
	})
}

function resetPwd(id) {
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
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}