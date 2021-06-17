
var prefix = "/archive/archiveActionprogramMain";
var editDate;
var form;
layui.use(['form','element','laydate'], function(){
    form = layui.form, $ = layui.$;
    var laydate = layui.laydate;

    getSelectData();
    selectChange();
    laydate.render({
        elem: '#closecaseDateBegin'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#closecaseDateEnd'
        ,type: 'datetime'
    });
});
$(document).ready(function(){
    $('#archiveTable').bootstrapTable({
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
                accidentName : $("#accidentTypeId").find("option:selected").text(),
                warnTypeName : $("#earlywarnTypeId").find("option:selected").text(),
                warnLevelName : $("#earlywarnLevelId").find("option:selected").text(),
                code : $('#code').val(),
                name : $('#name').val(),
                closecaseDateBegin : $('#closecaseDateBegin').val(),
                closecaseDateEnd : $('#closecaseDateEnd').val(),
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
                    var pageSize=$('#archiveTable').bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber=$('#archiveTable').bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {
                field : 'planName',
                title : '预案名称',
                width : 100,
                align : "center",
                formatter:paramsMatter,
            },
            {
                field : 'code',
                title : '结案编码',
                width : 100,
                align : "center",
                formatter:paramsMatter,
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
                width : 80,
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
                field : 'warnDesc',
                title : '事件描述' ,
                width : 120,
                align : "center",
                formatter:paramsMatter,
            },
            {
                title : '操作',
                field : 'id',
                align : 'center',
                width : 80,
                //halign : "center",
                formatter : function(value, row, index) {
                    var html = "";
                    html += '<a class="btn btn-primary btn-sm" style="margin-right: 10px;" onclick="edit(\''+row.id+'\')">生成新预案</a>';
                    return html ;
                }
            }]
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
    $('#archiveTable').bootstrapTable('refresh');
}

function getSelectData(){
    $.ajax({
        url: '/planManage/planMain/getSelectData',
        method:'post',
        data:{
        },
        async: false,
        success:function(data){
            if(data.msg =='true'){
                if(data.accidentTypeList !=null && data.accidentTypeList !=""){
                    accidentType = data.accidentTypeList;
                    var html = '<option value=""></option>';
                    for(var i=0;i<accidentType.length;i++){
                        html += '<option value="'+accidentType[i].id+'">'+accidentType[i].name+'</option>';
                    }
                    $("#accidentTypeId").html(html);
                }
                if(data.earlywarnTypeList !=null && data.earlywarnTypeList !=""){
                    earlywarnType = data.earlywarnTypeList;
                    var html = '<option value=""></option>';
                    for(var i=0;i<earlywarnType.length;i++){
                        html += '<option value="'+earlywarnType[i].id+'">'+earlywarnType[i].name+'</option>';
                    }
                    $("#earlywarnTypeId").html(html);
                    $("#earlywarnTypeId").attr('disabled', 'disabled');
                }
                if(data.earlywarnLevelList !=null && data.earlywarnLevelList !=""){
                    earlywarnLevel = data.earlywarnLevelList;
                    var html = '<option value=""></option>';
                    for(var i=0;i<earlywarnLevel.length;i++){
                        html += '<option value="'+earlywarnLevel[i].id+'">'+earlywarnLevel[i].name+'</option>';
                    }
                    $("#earlywarnLevelId").html(html);
                    $("#earlywarnLevelId").attr('disabled', 'disabled');
                }
                if(data.dpetList !=null && data.dpetList !=""){
                    var dpetList = data.dpetList;
                    var html = '<option value=""></option>';
                    for(var i=0;i<dpetList.length;i++){
                        html += '<option value="'+dpetList[i].id+'">'+dpetList[i].name+'</option>';
                    }
                    $("#reprotDeptId").html(html);
                    $("#startDeptId").html(html);
                }
                form.render('select', 'searchForm');
            } else {
                layer.msg("获取下拉框信息失败");
            }
        },
        error:function (data) {
            layer.msg("获取下拉框信息失败");
        }
    }) ;
}


function selectChange(){
    form.on('select(accidentTypeChange)', function(data){
        $("#earlywarnTypeId").empty();
        $("#earlywarnTypeId").attr('disabled','disabled');
        $("#earlywarnLevelId").empty();
        $("#earlywarnLevelId").attr('disabled','disabled');
        //责任部门
        $("#dutyDeptId").val("");
        $("#dutyDeptName").val("");
        for(var i=0;i<accidentType.length;i++){
            if(data.value == accidentType[i].id){
                $("#dutyDeptId").val(accidentType[i].deptId);
                $("#dutyDeptName").val(accidentType[i].deptName);
            }
        }

        //预警下拉
        var html = '<option value=""></option>';
        var change = false;
        for(var i=0;i<earlywarnType.length;i++){
            if(data.value == earlywarnType[i].accidentTypeId){
                html += '<option value="'+earlywarnType[i].id+'">'+earlywarnType[i].name+'</option>';
                change = true;
            }
        }
        if(change){
            $("#earlywarnTypeId").removeAttr('disabled');
            $("#earlywarnTypeId").html(html);
        }
        form.render('select', 'searchForm');
    });

    form.on('select(earlywarnTypeChange)', function(data){
        $("#earlywarnLevelId").empty();
        $("#earlywarnLevelId").attr('disabled','disabled');
        var change = false;
        var html = '<option value=""></option>';
        for(var i=0;i<earlywarnLevel.length;i++){
            if(data.value == earlywarnLevel[i].earlywarnTypeId){
                change = true;
                html += '<option value="'+earlywarnLevel[i].id+'">'+earlywarnLevel[i].name+'</option>';
            }
        }
        if(change){
            $("#earlywarnLevelId").html(html);
            $("#earlywarnLevelId").removeAttr('disabled');
        }
        form.render('select', 'searchForm');
    });
}

function edit(id){
    var data = $('#archiveTable').bootstrapTable('getRowByUniqueId',id);//行的数据
    editDate = data;//传值到子页面
    layer.open({
        type: 2,
        title: '编辑方案信息',
        shadeClose: true,
        shade: 0.4,
        area: ['95%', '93%'],
        content: 'planModelForm.html'
    });
}

