
var prefix = "/support/twoviolationsdaily";
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
                        address:$('#address').val(),
                        start_schedulingDate:$('#start_schedulingDate').val(),
                        end_schedulingDate:$('#end_schedulingDate').val(),
                        number:$('#number').val(),
                        direction:$('#direction').val()
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
                        title : '值班人员'
                    },
                    {
                        field : 'time',
                        title : '发现时间',
                        formatter:function(value,row,index){
                            var value="";
                            if(row.time !=null && row.time !=""){
                                var date = new Date(row.time);
                                var hours = date.getHours()<10?"0"+date.getHours():date.getHours();
                                var minutes = date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes();
                                value = hours+":"+minutes;
                            }
                            return value;
                        }
                    },
                    {
                        field : 'number',
                        title : '车牌号码'
                    },
                    {
                        field : 'address',
                        title : '发现地点'
                    },
                    {
                        field : 'direction',
                        title : '行车方向'
                    },
                    {
                        field : 'goods',
                        title : '货物'
                    },
                    {
                        field : 'isNotify',
                        title : '是否通知执法队',
                        formatter:function(value,row,index){
                            var value="";
                            if(row.isNotify=="1"){
                                value = "已通知";
                            }else if(row.isNotify=="0"){
                                value = "未通知";
                            }
                            return value;
                        }
                    },
                    {
                        field : 'trackSituation',
                        title : '执法队追踪情况'
                    },
                    {
                        field : 'remarks',
                        title : '备注'
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
            title : '两违日志编辑',
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
            title : '两违日志查看',
            maxmin : true,
            shadeClose : false, // 点击遮罩关闭层
            area : [ '70%', '85%' ],
            content : 'edit.html'// iframe的url
        });
    }

}


function rowOption(value, row, index){
    var html="";
    if(row.isOwner !=null && row.isOwner !="" && row.isOwner =="1"){
        html= '<button class="layui-btn layui-btn-sm read" >查看</button>'+
            '<button class="layui-btn layui-btn-sm edit"  >修改</button>';
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
                    title : '两违日志编辑',
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

function exp() {
    window.location.href=prefix+"/expExcel";
}