
var prefix = "/support/daily";
var editData;
$(function() {
    load2();
    layui.use(['laydate'], function () {

        laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#start_schedulingDate'
        });
        laydate.render({
            elem: '#end_schedulingDate'
        });

    });
})
function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/list", // 服务器数据的加载地址
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
						smartDisplay:false,
						pageList:[10,20,30],
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset
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
									field : 'id', 
									title : '' 
								},
																{
									field : 'deptpersonId', 
									title : '值班人员 ：单位人员表ID' 
								},
																{
									field : 'schedulingDate', 
									title : '值班日期' 
								},
																{
									field : 'isException', 
									title : '是否异常' 
								},
																{
									field : 'content', 
									title : '日报/要情' 
								},
																{
									field : 'createBy', 
									title : '记录录入人' 
								},
																{
									field : 'createDate', 
									title : '记录录入时间' 
								},
																{
									field : 'updateBy', 
									title : '记录更新人' 
								},
																{
									field : 'updateDate', 
									title : '记录更新时间' 
								},
																{
									title : '操作',
									field : 'id',
									align : 'center',
									formatter : function(value, row, index) {
										var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
												+ row.id
												+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
												+ row.id
												+ '\')"><i class="fa fa-remove"></i></a> ';
										var f = '<a class="btn btn-success btn-sm" href="#" title="备用"  mce_href="#" onclick="resetPwd(\''
												+ row.id
												+ '\')"><i class="fa fa-key"></i></a> ';
										return e + d ;
									}
								} ]
					});
}

function load2() {
	$('#exampleTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url :  prefix+"/getUser", // 服务器数据的加载地址
				method:'get',
				striped: true,
				cache: false,
				pagination: true,
				sortable: false,
				sortOrder: "asc",

				sidePagination: "server",
				pageNumber: 1,
				pageSize: 10,
				pageList: [10, 25, 50, 100],
				smartDisplay: false,
				clickToSelect: true,
				uniqueId: "id",
				queryParams : function(params) {
					return {
						//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit: params.limit,
						offset:params.offset,
						deptPersonName:$('#deptPersonName').val(),
						deptName:$('#deptName').val(),
                        start_schedulingDate:$('#start_schedulingDate').val(),
                        end_schedulingDate:$('#end_schedulingDate').val()
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
						field : 'rownum',
						title: '序号',//标题  可不加
						formatter :function(value, row, index){
							//获取每页显示的数量
							var pageSize=$('#exampleTable').bootstrapTable('getOptions').pageSize;
							//获取当前是第几页
							var pageNumber=$('#exampleTable').bootstrapTable('getOptions').pageNumber;
							//返回序号，注意index是从0开始的，所以要加上1
							return pageSize * (pageNumber - 1) + index + 1;
						}

					},
					{
						field : 'date',
						title : '值班日期'
					},
					{
						field : 'name',
						title : '姓名'
					},
					{
						field : 'deptName',
						title : '部门'
					},
					{
						field : 'position',
						title : '职位'
					},
					{
						field : 'content',
						title : '日报内容'
					}
					,
					{
						field : 'createDate',
						title : '填写时间'
					}
					,
					{
						field : 'updateDate',
						title : '修改时间'
					},{
						title : '操作',
						field: 'btn',
						align : 'center',
						formatter :rowOption,
						events: window.operateEvents

					}


				]
			});
}
window.operateEvents = {

	'click .edit': function (e, value, row, index) {
		editData = row;
		editData.dataType = "edit";
		layer.open({
			type : 2,
			title : '值班日报编辑',
			maxmin : true,
			shadeClose : false, // 点击遮罩关闭层
			area : [ '70%', '85%' ],
			content : 'edit.html' // iframe的url

		});
	},	'click .read': function (e, value, row, index) {
		editData = row;
		editData.dataType = "view";
		layer.open({
            type : 2,
            title : '值班日报查看',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
			area : [ '70%', '85%' ],
            content : 'edit.html'// iframe的url
        });
    },	'click .delete': function (e, value, row, index) {
		layer.confirm('确定要删除选中的记录？', {
			btn : [ '确定', '取消' ]
		}, function() {
			$.ajax({
				url : prefix+"/removeUser",
				type : "post",
				data : {
					'id' : row.id
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

}


function rowOption(value, row, index){
    var html="";
    if(row.isOwner !=null && row.isOwner !="" && row.isOwner =="1"){
		html= '<button class="layui-btn layui-btn-sm read" >查看</button>'+
			'<button class="layui-btn layui-btn-sm edit"  >修改</button>'+
			'<button class="layui-btn layui-btn-sm layui-btn-danger delete"  >删除</button>';
	}else{
		html= '<button class="layui-btn layui-btn-sm read" >查看</button>';
	}
	return [
        html
	]
}



function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	$.ajax({
		url : "/scheduling/getUserSchedulingDate",
		method : 'post',
		data : {},
		async : false,
		success : function(data) {
			if(data !=null && data.length>0){
				layer.open({
					type : 2,
					title : '值班日报编辑',
					maxmin : true,
					shadeClose : false, // 点击遮罩关闭层
					area : [ '70%', '85%' ],
					content : 'add.html' // iframe的url

				});
			}else{
				layer.confirm('当前用户还没有排班信息，请先排班再添加日记', function(index){
					layer.close(index);
				});
			}
		},
		error : function(data) {
			layer.msg("获取当前用户排班信息失败");
		}
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