
var prefix = "/support/knowledgeInfo";
var editDate;
$(document).ready(function(){ 
	$('#knowledgeInfoTable').bootstrapTable({
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
				detailName:$('#name').val()
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
	                    var pageSize=$('#knowledgeInfoTable').bootstrapTable('getOptions').pageSize;
	                    //获取当前是第几页
	                    var pageNumber=$('#knowledgeInfoTable').bootstrapTable('getOptions').pageNumber;
	                    //返回序号，注意index是从0开始的，所以要加上1
	                    return pageSize * (pageNumber - 1) + index + 1;
	                }
				},
				{
					field : 'detailCode', 
					title : '编号',
					width : 120,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'detailName', 
					title : '名称',
					width : 80,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'detailMolecularFormula', 
					title : '分子式' ,
					width : 80,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'dangerTypeName',
					title : '危险类别' ,
					width : 120,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'storagelocation', 
					title : '保管地点' ,
					width : 120,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'dept', 
					title : '使用部门' ,
					width : 100,
					align : "center",
					formatter:paramsMatter,
				},
				{
					field : 'enabled', 
					title : '状态' ,
					width : 60,
					align : "center",
					formatter:paramsMatter,
					formatter : function(value, row, index) {
						if (row.enabled == '0') {
							return "已停用";
						} else if (row.enabled == '1') {
							return "已启用";
						} 
					}
				},
				{
					title : '操作',
					field : 'id',
					align : 'center',
					width : 170,
					//halign : "center",
					formatter : function(value, row, index) {
						var html = "";
						html += '<a class="btn btn-primary btn-sm" style="margin-right: 10px;" onclick="view(\''+row.id+'\')">查看</a>';
						html += '<a class="btn btn-primary btn-sm" style="margin-right: 10px;" onclick="edit(\''+row.id+'\')">修改</a>';
						if(row.enabled ==0 ){
							html += '<a class="btn btn-primary btn-sm" style="margin-right: 10px;" onclick="changeStatus(\''+row.id+'\',\''+row.enabled+'\')">启用</a>';
						}else if(row.enabled ==1){
							html += '<a class="btn btn-danger btn-sm" style="margin-right: 10px;" onclick="changeStatus(\''+row.id+'\',\''+row.enabled+'\')">停用</a>';
						}
						html += '<a class="btn btn-danger btn-sm" style="margin-right: 10px;" onclick="deleteData(\''+row.id+'\')">删除</a>					';
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
	$('#knowledgeInfoTable').bootstrapTable('refresh');
}

function view(id){
	var data = $('#knowledgeInfoTable').bootstrapTable('getRowByUniqueId',id);//行的数据
	data.dataType = "view";
    editDate = data;//传值到子页面
	layer.open({
        type: 2,
        title: '编辑知识库信息',
        shadeClose: true,
        shade: 0.4,
        area: ['95%', '93%'],
        content: 'knowledgeInfoForm.html'
   }); 
}

function edit(id){
	var data = $('#knowledgeInfoTable').bootstrapTable('getRowByUniqueId',id);//行的数据
	data.dataType = "edit";
	editDate = data;//传值到子页面
	layer.open({
		type: 2,
		title: '编辑知识库信息',
		shadeClose: true,
		shade: 0.4,
		area: ['95%', '93%'],
		content: 'knowledgeInfoForm.html'
	}); 
}

function changeStatus(id,enabled){
	editDate = null;
	if (enabled == '0') {
		enabled = "1";
	} else if (enabled == '1') {
		enabled = "0";
	}
	$.ajax({
		url : prefix + '/changeStatus',
		method : 'post',
		data : {
			"id" : id,
			"enabled" : enabled
		},
		async : false,
		success : function(data) {
			if (data.code == '0') {
				reloadTable();
				layer.msg(data.msg);
			} else {
				layer.msg(data.msg);
			}
		},
		error : function(data) {
			layer.msg("保存失败");
		}
	});         
}

function deleteData(id){
	editDate = null;
	layer.confirm('确定要删除该行数据吗？', function(index){
    	$.ajax({   
	    	url: prefix + '/remove',       
	        method:'post',       
	        data:{
		  		"id": id
	  		},
	  		async: false,       
	        success:function(data){      
		  		if(data.code =='0'){       
	  				reloadTable();
		  			layer.msg("删除成功");
	            } else if(data.code =='500'){
		  			layer.alert(data.msg);
	  			}           
	        },              
	        error:function (data) {
	            layer.alert("删除失败，请稍后重试");
	  		}           
	    }) ;         
    });
}



