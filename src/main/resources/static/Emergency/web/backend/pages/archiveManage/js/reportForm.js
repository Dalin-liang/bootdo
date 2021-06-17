
var parentData;
var form;
var $;
var demoListView;
var prefix = "/report/evaluationReport";
$(function() {
    layui.use(['form','laydate','upload'], function () {
        form = layui.form;
        $ = layui.$;
        var upload = layui.upload;

        //多文件列表示例
        var ret_fileList = new Array();
        demoListView = $('#demoList');
        var uploadListIns = upload.render({
            elem: '#testList'
            ,url: prefix+'/uploadFile'
            ,accept: 'file'
            ,multiple: true
            ,auto: false
            ,bindAction: '#testListAction'
            ,choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                });
            }
            ,done: function(res, index, upload){
                if(res.code == 0){ //上传成功
                    var ret_fileItem = new Object();
                    ret_fileItem.url = res.msg;
                    ret_fileItem.type = getFileType(res.msg);
                    ret_fileList.push(JSON.stringify(ret_fileItem));
                    var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(''); //清空操作
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                    ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });

        //回显
        if (parent.editData != null && parent.editData != "") {
            parentData = parent.editData;
            parent.editData = null;

            //form初始化赋值
            form.val('dailyForm',{
                "id" : parentData.id,
                "archiveActionprogramMainId" : parentData.archiveActionprogramMainId,
                "repname" : parentData.repname,
                "repdate" : parentData.repdate,
                "accidentTypeName" : parentData.accidentTypeName,
                "earlywarnTypeName" : parentData.earlywarnTypeName,
                "eventlevelName" : parentData.eventlevelName,
                "reportPerson" : parentData.reportPerson,
                "reportCode" : parentData.reportCode,
                "earlywarnContent" : parentData.earlywarnContent,
                "reportObjecttype" : parentData.reportObjecttype,
                "remarks" : parentData.remarks
            });

            if(parentData.id ==null ||parentData.id ==""){
                $("#reportCode").val(getCode());
            }
            form.render('select', 'dailyForm');
            getFileList(parentData.id);


        }



        form.on('submit(formSubmit)',function(data) {

            data.field.fileList = JSON.stringify(ret_fileList);

            var url;
            if(data.field.id !=null && data.field.id !=""){
                url = prefix+"/update";
            }else{
                url = prefix+"/save";
            }
            $.ajax({
                cache : true,
                type : "POST",
                url : url,
                data : data.field,
                async : false,
                error : function(request) {
                    parent.layer.alert("Connection error");
                },
                success : function(data) {
                    if (data.code == 0) {
                        parent.layer.msg("保存成功");
                        parent.reloadTable();
                        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(index);

                    } else {
                        parent.layer.alert(data.msg)
                    }

                }
            });

        })

    });
});

function getFileType(fileName) {
    var point = fileName.lastIndexOf(".");
    var name = fileName.substr(point);
    var imgType=["gif", "jpeg", "jpg", "bmp", "png"];
    var videoType=["avi","wmv","mkv","mp4","mov","rm","3gp","flv","mpg","rmvb"];
    var txtType=["txt","doc","xls","ppt","docx","xlsx","pptx"];
    if(RegExp("\.(" + imgType.join("|") + ")$", "i").test(name.toLowerCase())) {
        return '0';
    } else if(RegExp("\.(" + videoType.join("|") + ")$", "i").test(name.toLowerCase())) {
        return '2';
    }else if(RegExp("\.(" + txtType.join("|") + ")$", "i").test(name.toLowerCase())) {
        return '1';
    } else {
        return '3';
    }
}

function getFileList(evaluationReportId) {
    $.ajax({
        url : prefix+'/getFileList',
        method : 'post',
        data : {
            evaluationReportId : evaluationReportId
        },
        async : false,
        success : function(data) {
            if(data !=null && data.length>0){
                var html="";
                for (var i=0 ; i<data.length;i++){
                    var fileName = data[i].fileUrl.substr(data[i].fileUrl.lastIndexOf("\\")+1);
                    html+='<tr id="hasFileList-'+ i +'">'+
                        '<td>'+ fileName +'</td>'
                    if(data[i].fileType==0){
                        html+=	'<td> <a href='+data[i].fileUrl+' target="_blank"><img src='+data[i].fileUrl+'></a> </td>'
                    }else{
                        html+=	'<td> </td>'
                    }
                    if(data[i].fileType==2){
                        html+=	'<td style="text-align: center"><a href='+data[i].fileUrl+' class="movie" datasrc='+data[i].fileUrl+' target="_blank"><i class="layui-icon layui-icon-play"></i></a></td>'
                    }else{
                        html+=	'<td> </td>'
                    }
                    html+=	'<td>';
                    if(parentData.dataType =="edit") {
                        html+='<button type="button"  class="layui-btn layui-btn-xs layui-btn-danger delbtn" onclick="delFile(\'' + data[i].id + '\')">删除</button>'
                    }
                    html+=	'<button type="button" class="layui-btn layui-btn-xs layui-btn-danger" onclick="downloadFile(\''+data[i].id+'\')">下载</button>'+
                        '</td>'+
                        '</tr>';

                    $("#fileList").html(html);
                }
            }
        },
        error : function(data) {
            parent.layer.msg("获取附件信息失败");
        }
    });
}



function delFile(id) {
    $.ajax({
        cache : true,
        type : "POST",
        url : prefix+"/delFile",
        data : {id:id},
        async : false,
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("删除成功");
                getFileList(parentData.id);

            } else {
                parent.layer.alert(data.msg)
            }

        }
    });
}
function downloadFile(id) {
    window.location.href=prefix+"/downloadFile?id="+id;

}



function closeWindow(){
    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
    parent.layer.close(index);
}

//生成16位的预案编码: 年月日（8位）+ 随机数（8位）
function getCode(){
    var date = new Date();
    var year = date.getFullYear();
    var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    var code = ""+year+month+day;
    var len = 16-code.length;
    for(var i=0;i<len;i++){
        code += Math.floor(Math.random()*10);
    }
    return code;
}
