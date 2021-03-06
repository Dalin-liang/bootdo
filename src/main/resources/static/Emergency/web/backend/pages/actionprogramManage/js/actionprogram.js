
var prefix = "/actionprogramManage/dispatchActionprogramMain";
var editDate;
$(document).ready(function(){ 
	$('#actionprogramTable').bootstrapTable({
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
		pageList: [10, 25, 50, 100],
		//search : true, // 是否显示搜索框
		showColumns : false, // 是否显示内容下拉框（选择显示的列）
		sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
		uniqueId: "id",
		queryParams : function(params) {
			return {
				//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
				limit: params.limit,
				offset:params.offset,
				code : $('#code').val(),
				status : $('#status').val(),
				planmainName : $('#planmainName').val(),
				eventCode : $('#eventCode').val(),
				isArchive : $('#isArchive').val()
			};
		},
		// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
		// queryParamsType = 'limit' ,返回参数必须包含
		// limit, offset, search, sort, order 否则, 需要包含:
		// pageSize, pageNumber, searchText, sortName,
		// sortOrder.
		// 返回false将会终止请求
		columns : [{
	                field: 'id',
	                title: 'ID',
	                visible: false
	            },
				{
					field : 'index', 
					title : '序号' ,
					width : 40,
					align : "center",
					formatter: function (value, row, index) {
	                    //获取每页显示的数量
	                    var pageSize=$('#actionprogramTable').bootstrapTable('getOptions').pageSize;
	                    //获取当前是第几页
	                    var pageNumber=$('#actionprogramTable').bootstrapTable('getOptions').pageNumber;
	                    //返回序号，注意index是从0开始的，所以要加上1
	                    return pageSize * (pageNumber - 1) + index + 1;
	                }
				},
				{
					field : 'planmainName',
					title : '预案名称',
					width : 100,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'eventCode',
					title : '事件编码',
					width : 120,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'eventdesc',
					title : '事件描述',
					width : 120,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'code', 
					title : '方案编码',
					width : 100,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'status', 
					title : '方案状态',
					width : 100,
					align : "center",
					formatter : function(value, row, index) {
						if (row.status == '1') {
							return "执行中";
						} else if (row.status == '2') {
							return "已结案";
						}else {
							return "";
						}
					}
				},
				{
					field : 'actionDate', 
					title : '执行时间',
					width : 100,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'closecaseDate', 
					title : '结案时间',
					width : 100,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'isArchive', 
					title : '归档状态' ,
					width : 100,
					align : "center",
					formatter : function(value, row, index) {
						if (row.isArchive == '0') {
							return "未归档";
						} else if (row.isArchive == '1') {
							return "已归档";
						}else {
							return "";
						}
					}
				},
				{
					field : 'archiveDate', 
					title : '归档日期' ,
					width : 100,
					align : "center",
					formatter:paramsMatter,
				},
				{
					title : '操作',
					field : 'id',
					align : 'left',
					halign : 'center',
					width : 60,
					formatter : function(value, row, index) {
						var html = "";
						html += '<a class="btn btn-primary btn-sm" style="margin-right: 10px;" onclick="edit(\''+row.id+'\')">修改</a>';
						return html ;
					}
				} ]
	});
}); 

//表格数据样式
function paramsMatter(value, row, index) {
	if(value !=null && value!=""){
    	var values = value;//获取当前字段的值
        //替换空格，因为字符串拼接的时候如果遇到空格，会自动将后面的部分截掉，所有这里用html的转义符
        //&nbsp;代替
        values = values.replace(/\s+/g,'&nbsp;');
        return "<span title="+values+">"+value+"</span>";
    }
}

function reloadTable(){
	$('#actionprogramTable').bootstrapTable('refresh');
}

function edit(id){
	var data = $('#actionprogramTable').bootstrapTable('getRowByUniqueId',id);//行的数据
    editDate = data;//传值到子页面
    layer.open({
        type: 2,
        title: '编辑方案信息',
        shadeClose: true,
        shade: 0.4,
        area: ['95%', '93%'],
        content: 'actionprogramForm.html'
   }); 
}


