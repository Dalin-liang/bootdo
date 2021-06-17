
var prefix = "/receiveInfo"
$(function() {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix + "/getUnTreatedEventList", // 服务器数据的加载地址
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
                sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                uniqueId: "id",
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset:params.offset
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
                        field : 'eventDesc',
                        title : '事件描述' ,
                        width : '300'
                    },
                    {
                        field : 'sourceType',
                        title : '信息来源类别' ,
                        width : '30',
                        formatter :function (value) {
                            if(value == "10"){
                                return "物联网（客流统计）";
                            }else if(value == "11"){
                                return "物联网（智慧路灯）";
                            }else if(value == "12"){
                                return "物联网（水质）";
                            }else if(value == "14"){
                                return "微物联网（健康小屋）";
                            }else if(value == "15"){
                                return "物联网（AQI空气质量）";
                            }else if(value == "16"){
                                return "消防栓水压报警";
                            }else if(value == "17"){
                                return "电气报警";
                            }else if(value == "18"){
                                return "无线烟感报警";
                            }else if(value == "19"){
                                return "室外用电终端报警";
                            }else if(value == "20"){
                                return "室内用电终端报警";
                            }else if(value == "21"){
                                return "消防栓压力传感器报警";
                            }else if(value == "6"){
                                return "消防用电";
                            }
                            else if(value == "7"){
                                return "物联网（行为分析）";
                            }
                            else if(value == "9"){
                                return "物联网（特殊人群）";
                            }
                        }
                    },
                    {
                        field : 'address',
                        title : '地址',
                        width : '250'

                    },
                    {
                        field : 'repName',
                        title : '上报人',
                        width : '50'

                    } ]
            });
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
        area : [ '800px', '520px' ],
        content : 'add.html' // iframe的url
    });
}
function edit(id) {
    var row=$("#exampleTable").bootstrapTable("getRowByUniqueId",id);
    console.log(id);
    console.log(row);
    sessionStorage.setItem("edit","true");
    sessionStorage.setItem("row",JSON.stringify(row));
    layer.open({
        type : 2,
        title : '编辑',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '200px' ],
        content : 'edit.html' // iframe的url
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

function  endWarn(){
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要终结的数据");
        return;
    }

    var ids = new Array();
    // 遍历所有选择的行数据，取每条数据对应的ID
    $.each(rows, function(i, row) {
        ids[i] = row['eventId'];
    });
    $.ajax({
        type : 'POST',
        data : {
            "ids" : ids
        },
        url : prefix + '/batchEndCase',
        success : function(r) {
            if (r.code == 0) {
                layer.msg(r.msg);
                reLoad();
            } else {
                layer.msg(r.msg);
            }
        }
    });

}