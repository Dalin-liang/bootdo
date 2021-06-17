
var prefix = "/sms/smsSendConfig";
var editDate;
var $;
var form;
var SourceList;
var planminList;
var actionprogramMainList ;
layui.use(['form','element'], function(){
    form = layui.form;
    $ = layui.$;

    form.on('select(typeChange)', function(data){
        getTargetByType(data.value);
    });
    form.on('select(SourceChange)', function(data){
        SourceChange(data.value);
    });


});
$(document).ready(function(){
    $('#smsSendConfigTable').bootstrapTable({
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
                offset: params.offset,
                type: $('#type').val(),
                targetid: $('#targetid').val(),
                code: $('#code').val(),
                issend: $('#issend').val(),
                Sourceid: $('#Sourceid').val()
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
                    var pageSize=$('#smsSendConfigTable').bootstrapTable('getOptions').pageSize;
                    //获取当前是第几页
                    var pageNumber=$('#smsSendConfigTable').bootstrapTable('getOptions').pageNumber;
                    //返回序号，注意index是从0开始的，所以要加上1
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {
                field : 'type',
                title : '配置类型' ,
                width : 120,
                align : "center",
                formatter:paramsMatter,
                formatter : function(value, row, index) {
                    if (value == '0') {
                        return "接报来源类目";
                    } else if (value == '1') {
                        return "预案";
                    }else if (value == '2') {
                        return "方案";
                    }else if (value == '3') {
                        return "接报来源";
                    }else {
                        return "";
                    }
                }
            },
            {
                field : 'targetName',
                title : '配置对象',
                width : 120,
                align : "center",
                formatter:paramsMatter,
            },
            {
                field : 'issend',
                title : '短信状态' ,
                width : 80,
                align : "center",
                formatter:paramsMatter,
                formatter : function(value, row, index) {
                    if (row.issend == '0') {
                        return "短信已关闭";
                    } else if (row.issend == '1') {
                        return "短信已开启";
                    }else {
                        return "";
                    }
                }
            },
            // {
            //     field : 'remarks',
            //     title : '备注' ,
            //     width : 100,
            //     align : "center",
            //     formatter:paramsMatter,
            // },
            {
                title : '操作',
                field : 'id',
                align : 'center',
                width : 120,
                //halign : "center",
                formatter : function(value, row, index) {
                    var html = "";
                    html += '<a class="btn btn-primary btn-sm" style="margin-right: 10px;" onclick="edit(\''+row.id+'\')">修改</a>';
                    if(row.issend ==0){
                        html += '<a class="btn btn-primary btn-sm" style="margin-right: 10px;" onclick="changeStatus(\''+row.id+'\',\''+row.type+'\',\''+row.targetid+'\',\''+row.issend+'\')">开启短信</a>';
                    }else if(row.issend ==1){
                        html += '<a class="btn btn-danger btn-sm" style="margin-right: 10px;" onclick="changeStatus(\''+row.id+'\',\''+row.type+'\',\''+row.targetid+'\',\''+row.issend+'\')">关闭短信</a>';
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
    $('#smsSendConfigTable').bootstrapTable('refresh');
}

function edit(id){
    var data = $('#smsSendConfigTable').bootstrapTable('getRowByUniqueId',id);//行的数据
    data.dataType = "edit";
    editDate = data;//传值到子页面
    layer.open({
        type: 2,
        title: '编辑短信配置',
        shadeClose: true,
        shade: 0.4,
        area: ['800px', '400px'],
        content: 'smsSendConfigForm.html'
    });
}

function changeStatus(id,type,targetid,issend){
    editDate = null;
    if (issend == '0') {
        issend = "1";
    } else if (issend == '1') {
        issend = "0";
    }
    $.ajax({
        url : prefix + '/changeStatus',
        method : 'post',
        data : {
            "id" : id,
            "type" : type,
            "targetid" : targetid,
            "issend" : issend
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

function openAllType(){
    var type = $("#type").val();
    if(type == null || type == "" || type.length <= 0){
        layer.confirm('请先选择配置类型',  function(index){
            layer.close(index);
        });
        return ;
    }else{
        layer.confirm('确定开启该配置类型的所有短信吗？', function(index){
            changeStatusByParam(type,1);
        });
    }

}

function closeAllType(){
    var type = $("#type").val()
    if(type == null || type == "" || type.length <= 0){
        layer.confirm('请先选择配置类型', function(index){
            layer.close(index);
        });
        return ;
    }else{
        layer.confirm('确定关闭该配置类型的所有短信吗？', function(index){
            changeStatusByParam(type,0);
        });
    }
}

function changeStatusByParam(type,issend){
    var targetid = $("#targetid").val();
    var Sourceid = null;
    if(type == 0){
        Sourceid = $("#Sourceid").val();
    }
    $.ajax({
        url: prefix + '/changeStatusByParam',
        method:'post',
        data:{
            "type": type,
            "targetid": targetid,
            "Sourceid": Sourceid,
            "issend": issend
        },
        async: false,
        success:function(data){
            if(data.code =='0'){
                reloadTable();
                layer.msg("修改成功");
                form.render('select', 'smsSendConfig');
            } else if(data.code =='500'){
                layer.alert(data.msg);
            }
        },
        error:function (data) {
            layer.alert("修改失败，请稍后重试");
        }
    }) ;
}

function getTargetByType(type){
    $("#SourceItem").css("display","none");
    $("#codeItem").css("display","none");
    $("#targetid").empty();
    $("#code").val("");
    $("#Sourceid").val("");
    SourceList = null;
    planminList = null;
    actionprogramMainList = null;
    if(type !=null && type!=""){
        $.ajax({
            url : prefix + '/getTargetByType',
            method : 'post',
            data : {
                type: type
            },
            async : false,
            success : function(data) {
                if (data.msg == 'true') {
                    if(type == 0){
                        if(data.SourceList !=null && data.SourceList !=""){
                            $("#SourceItem").css("display","block");
                            SourceList = data.SourceList;
                            var html = '<option value=""></option>';
                            for(var i=0;i<SourceList.length;i++){
                                html += '<option value="'+SourceList[i].id+'">'+SourceList[i].name+'</option>';
                            }
                            $("#Sourceid").html(html);

                        }
                    }else if(type == 1){
                        if(data.planminList !=null && data.planminList !=""){
                            $("#codeItem").css("display","block");
                            planminList = data.planminList;
                            var html = '<option value=""></option>';
                            for(var i=0;i<planminList.length;i++){
                                html += '<option value="'+planminList[i].id+'">'+planminList[i].name+'</option>';
                            }
                            $("#targetid").html(html);
                        }
                    }else if(type == 2){
                        if(data.actionprogramMainList !=null && data.actionprogramMainList !=""){
                            $("#codeItem").css("display","block");
                            actionprogramMainList = data.actionprogramMainList;
                            var html = '<option value=""></option>';
                            for(var i=0;i<actionprogramMainList.length;i++){
                                html += '<option value="'+actionprogramMainList[i].id+'">'+actionprogramMainList[i].name+'</option>';
                            }
                            $("#targetid").html(html);
                        }
                    }else if(type == 3){
                        if(data.SourceList !=null && data.SourceList !=""){
                            SourceList = data.SourceList;
                            var html = '<option value=""></option>';
                            for(var i=0;i<SourceList.length;i++){
                                html += '<option value="'+SourceList[i].id+'">'+SourceList[i].name+'</option>';
                            }
                            $("#targetid").html(html);

                        }
                    }

                } else {
                    layer.msg("获取下拉失败");
                }
            },
            error : function(data) {
                layer.msg("获取下拉失败");
            }
        });
    }
    form.render('select', 'smsSendConfig');
}

function SourceChange(SourceId){
    $("#targetid").empty();
    $("#code").val("");
    $("#targetid").val("");
    if(SourceId !=null && SourceId !=""){
        for(var i=0;i<SourceList.length;i++){
            if(SourceId == SourceList[i].id){
                if(SourceList[i].sourceMenuList !=null && SourceList[i].sourceMenuList !=""){
                    var sourceMenuList = SourceList[i].sourceMenuList;
                    var html = '<option value=""></option>';
                    for (var j=0;j<sourceMenuList.length;j++){
                        html += '<option value="'+sourceMenuList[j].id+'">'+sourceMenuList[j].name+'</option>';
                    }
                    $("#targetid").html(html);
                }
            }
        }
    }
    form.render('select', 'smsSendConfig');
}


