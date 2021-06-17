
var prefix = "/scheduling"
$(function () {
    userload();
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
function userload() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix + "/countByUser", // 服务器数据的加载地址
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
                        limit: params.limit,
                        offset:params.offset,
                        name:$('#name').val(),
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
                        title: '序号',//标题  可不加

                        formatter: function (value, row, index) {

                            return index + 1;
                        }
                    },
                    {
                        field : 'name',
                        title : '值班人员'
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
                        field : 'is_personincharge',
                        title : '是否值班负责人',
                        formatter: function (value, row, index) {
                            return (value =="1")?"是":"否"
                        }
                    },
                    {
                        field : 'count',
                        title : '值班次数'
                    }


                ]
            });
}
function reLoad(opt){
    if(opt==1){
        userload();
    }else{
        deptload();
    }
}

function changeTable(opt) {
    if (opt==1) {
        $("#nameItem").css("display","block");
        $("#deptItem").css("display","none");
        $('#exampleTable').bootstrapTable(
            "refreshOptions",
            {
                url : prefix + "/countByUser", // 服务器数据的加载地址
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset:params.offset,
                        name:$('#name').val(),
                        start_schedulingDate:$('#start_schedulingDate').val(),
                        end_schedulingDate:$('#end_schedulingDate').val()
                    };
                },
                columns : [
                    {
                        title: '序号',//标题  可不加

                        formatter: function (value, row, index) {

                            return index + 1;
                        }
                    },
                    {
                        field : 'name',
                        title : '值班人员'
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
                        field : 'is_personincharge',
                        title : '是否值班负责人',
                        formatter: function (value, row, index) {
                            return value =="1"?"是":"否"
                        }
                    },
                    {
                        field : 'count',
                        title : '值班次数'
                    }


                ]
            }
        );

    }else {
        $("#nameItem").css("display","none");
        $("#deptItem").css("display","block");
        $('#exampleTable').bootstrapTable(
            "refreshOptions",
            {
                url: prefix + "/countByDept", // 服务器数据的加载地址
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset:params.offset,
                        deptName:$('#deptName').val(),
                        start_schedulingDate:$('#start_schedulingDate').val(),
                        end_schedulingDate:$('#end_schedulingDate').val()
                    };
                },
                columns: [
                    {
                        title: '序号',//标题  可不加

                        formatter: function (value, row, index) {

                            return index + 1;
                        }
                    },
                    {
                        field: 'deptName',
                        title: '部门'
                    },
                    {
                        field: 'count',
                        title: '值班次数'
                    }
                    ,
                    {
                        field: 'exceptionCount',
                        title: '异常次数'
                    }
                ]
            }
        );
    }
}




