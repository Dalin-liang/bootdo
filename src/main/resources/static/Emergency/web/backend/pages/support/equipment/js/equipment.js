
var prefix = "/equipment"
$(function() {
	load();

    var onSampleResized = function(e){
        var table = $(e.currentTarget); //reference to the resized table
    };


    $("#exampleTable").colResizable({

        liveDrag:true,

        gripInnerHtml:"<div class='grip'></div>",

        draggingClass:"dragging",

        onResize:onSampleResized

    });
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/query", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams: function (params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: this.pageSize,
                        offset: this.pageNumber,
                         name:$('#name').val(),
                        // username:$('#searchName').val()
                    };
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [

                    {

                        title: '序号'	,
                        formatter: function (value, row, index) {

                            return index + 1;
                        }
                    },
                    {
                        field: 'name',
                        title: '装备名称'
                    },

                    {
                        field: 'code',
                        title: '装备编号'
                    },

                    {
                        field: 'contact',
                        title: '联系人'
                    },
                    {
                        field: 'mobile',
                        title: '联系电话'
                    },
                    {
                        field: 'teamName',
                        title: '  所属应急队伍'
                    },
                    {
                        field: 'houseName',
                        title: '存放位置'
                    },

                    {
                        field: 'remarks',
                        title: '备注'
                    }, {
                        title: '操作',
                        field: 'btn',
                        align: 'center',
                        events: window.operateEvents,
                        formatter: rowOption
                    }]
            });
}

window.operateEvents = {

    'click .edit': function (e, value, row, index) {
        layer.open({
            type : 2,
            title : '编辑',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '800px', '520px' ],
            content : 'edit.html', // iframe的url
            success:function (layero,index){
                var body=layer.getChildFrame('body',index);
                body.find("#id").val(row.id);
                body.find("#name").val(row.name);
                body.find("#equiptype_id").val(row.equiptype_id);
                body.find("#equipstatus_id").val(row.equipstatus_id);
                body.find("#code").val(row.code);
                body.find("#team_id").val(row.team_id);
                body.find("#contact").val(row.contact);
                body.find("#equipstorehouse_id").val(row.equipstorehouse_id);

                body.find("#remarks").val(row.remarks);

                body.find("#update_by").val(row.update_by);
                body.find("#update_date").val(row.update_date);
                var iframeWin = window[layero.find('iframe')[0]['name']];
                iframeWin.teanPerson(row.team_id)
                body.find("#contact").val(row.contact);
                body.find("#mobile").val(row.mobile);
                iframeWin.renderForm();

            }

        });
    },
    'click .read': function (e, value, row, index) {
        layer.open({
            type : 2,
            title : '查看',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '800px', '520px' ],
            content : 'edit.html?state=read', // iframe的url
            success:function (layero,index){
                var body=layer.getChildFrame('body',index);
                body.find("#id").val(row.id);
                body.find("#name").val(row.name);
                body.find("#equiptype_id").val(row.equiptype_id);
                body.find("#equipstatus_id").val(row.equipstatus_id);
                body.find("#code").val(row.code);
                body.find("#team_id").val(row.team_id);
                body.find("#equipstorehouse_id").val(row.equipstorehouse_id);

                body.find("#remarks").val(row.remarks);
                body.find("#update_by").val(row.update_by);
                body.find("#update_date").val(row.update_date);
                body.find("input,select,textarea").attr('disabled','disabled');
                body.find("#btn").css('display',"none");
                var iframeWin = window[layero.find('iframe')[0]['name']];
                iframeWin.teanPerson(row.team_id)
                body.find("#contact").val(row.contact);
                body.find("#mobile").val(row.mobile);
                iframeWin.renderForm();



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
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function rowOption(value, row, index){
    return [

        '<button class="layui-btn layui-btn-primary layui-btn-sm edit">修改</button>' +
        '<button class="layui-btn layui-btn-sm read" )">查看</button>'+
        '<button class="layui-btn layui-btn-danger layui-btn-sm" class="delete" onclick="remove(\''+row.id+'\')">删除</button>'
    ]
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '850px', '550px' ],
		content : 'add.html' // iframe的url
	});
}
function edit(id) {
   var s= $("#exampleTable").bootstrapTable('getSelections')

    var rows = $('#exampleTable').bootstrapTable('getRowByUniqueId',id);//行的数据
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/delete",
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
