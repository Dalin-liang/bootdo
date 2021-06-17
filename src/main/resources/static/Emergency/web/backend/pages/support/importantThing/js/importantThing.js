
var prefix = "/support/importantthing";
var editDate;

$(document).ready(function(){
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


    $('#importantThingTable').bootstrapTable({
        method : 'get', // 服务器数据的请求方式 get or post
        url : prefix + "/getUser", // 服务器数据的加载地址
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
                field : 'index',
                title : '序号' ,
                width : 40,
                align : "center",
                formatter: function (value, row, index) {
                    //获取每页显示的数量
                    var pageSize=$('#importantThingTable').bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber=$('#importantThingTable').bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {
                field : 'date',
                width : 80,
                title : '值班日期',
                formatter:paramsMatter,
            },
            {
                field : 'name',
                width : 80,
                title : '姓名',
                formatter:paramsMatter,
            },
            {
                field : 'deptName',
                width : 100,
                title : '部门',
                formatter:paramsMatter,
            },
            {
                field : 'position',
                width : 100,
                title : '职位',
                formatter:paramsMatter,
            },
            {
                field : 'content',
                width : 120,
                title : '情况说明',
                formatter:paramsMatter,
            }
            ,
            {
                field : 'createDate',
                title : '填写时间',
                width : 100,
                formatter:paramsMatter,
            }
            ,
            {
                field : 'updateDate',
                title : '修改时间',
                width : 100,
                formatter:paramsMatter,
            },
            {
                title : '操作',
                field : 'btn',
                align : 'center',
                width : 120,
                formatter :rowOption,
                events: window.operateEvents
            } ]
    });
});

window.operateEvents = {
    'click .edit': function (e, value, row, index) {
        row.dataType = "edit";
        editDate = row;//传值到子页面
        openForm(1);
    },	'click .read': function (e, value, row, index) {
        row.dataType = "view";
        editDate = row;//传值到子页面
        openForm(1);
    },	'click .delete': function (e, value, row, index) {
        layer.confirm('确定要删除选中的记录？', {
            btn : [ '确定', '取消' ]
        }, function() {
            $.ajax({
                url : prefix+"/remove",
                type : "post",
                data : {
                    'id' : row.id
                },
                success : function(r) {
                    if (r.code==0) {
                        layer.msg(r.msg);
                        reloadTable()
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
    if(row.isOwner == "1"){
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


function openForm(type){
    if(type == 0){
        editDate={};
        editDate.dataType = "add";
        $.ajax({
            url : '/scheduling/getUserSchedulingDate',
            method : 'post',
            data : {},
            async : false,
            success : function(data) {
                if(data !=null && data.length>0){
                    layer.open({
                        type: 2,
                        title: '编辑值班要情',
                        shadeClose: true,
                        shade: 0.4,
                        area: ['1000px', '500px'],
                        content: 'importantThingForm.html'
                    });
                }else{
                    layer.confirm('当前用户还没有排班信息，请先排班再添加要情', function(index){
                        layer.close(index);
                    });
                }
            },
            error : function(data) {
                layer.msg("获取当前用户排班信息失败");
            }
        });
    }else{
        layer.open({
            type: 2,
            title: '编辑值班要情',
            shadeClose: true,
            shade: 0.4,
            area: ['1000px', '500px'],
            content: 'importantThingForm.html'
        });
    }
}

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
    $('#importantThingTable').bootstrapTable('refresh');
}




