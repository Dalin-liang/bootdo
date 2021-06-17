
var parentData;
var form;
var $;
var demoListView;
var fileLastLen = 0 ;//最后一个文件的排序号
$(function() {
	layui.use(['form','laydate','upload'], function () {
		form = layui.form;
		$ = layui.$;
		var upload = layui.upload;
		var laydate = layui.laydate;

		laydate.render({
			elem: '#time'
			,type: 'time'
			,format: 'HH:mm'
		});

		//多文件列表示例
		var ret_fileList = new Array();
		demoListView = $('#demoList');
		var uploadListIns = upload.render({
			elem: '#testList'
			,url: '/support/twoviolationsdaily/uploadFile'
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
					var fileSort = index;
					if(fileSort.indexOf("-") != -1){
						fileSort =  fileSort.substr(fileSort.lastIndexOf("-")+1);
					}
					fileSort = Number(fileLastLen) + Number(fileSort);
					var ret_fileItem = new Object();
					ret_fileItem.url = res.msg;
					ret_fileItem.type = getFileType(res.msg);
					ret_fileItem.sort = fileSort;
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

			$("#name").attr('readonly',true);

			if(parentData.dataType =="view"){
				getSchedulingDate("/scheduling/getUserViewSchedulingDate");
				$("form input").attr("readonly","readonly").css("cursor", "not-allowed");
				$("form textarea").attr("disabled","disabled").css("cursor", "not-allowed");
				$("#schedulingDate").attr('disabled', 'disabled');
				$("#time").attr('disabled', 'disabled');
				$("#isNotify").attr('disabled', 'disabled');
				$(".ifView").attr('disabled', 'disabled').css('cursor', 'not-allowed');
				$(".delbtn").css('display', 'none');
				$(".submitbtn").css('display', 'none');

			}else if(parentData.dataType =="edit"){
				getSchedulingDate("/scheduling/getUserSchedulingDate");
			}
			//form初始化赋值
			var findTime = null;
			if(parentData.time !=null && parentData.time !=""){
				var date = new Date(parentData.time);
				var hours = date.getHours()<10?"0"+date.getHours():date.getHours();
				var minutes = date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes();
				findTime = hours+":"+minutes;
			}
			form.val('dailyForm',{
				"id" : parentData.id,
				"deptId" : parentData.deptId,
				"deptpersonId" : parentData.deptperson_id,
				"schedulingDate" : parentData.date,
				"name" : parentData.name,
				"time" : findTime,
				"number" : parentData.number,
				"address" : parentData.address,
				"direction" : parentData.direction,
				"goods" : parentData.goods,
				"trackSituation" : parentData.trackSituation,
				"remarks" : parentData.remarks,
				"isNotify" : (parentData.isNotify == '1') ? true: false
			});

			form.render('select', 'dailyForm');
			getFileList(parentData.id);


		}

		form.on('submit(formSubmit)',function(data) {
			data.field.time = data.field.schedulingDate +" "+ data.field.time +":00";
			if (data.field.isNotify == "on") {
				data.field.isNotify = "1";
			} else {
				data.field.isNotify = "0";
			}
			data.field.fileList = JSON.stringify(ret_fileList);

			$.ajax({
				cache : true,
				type : "POST",
				url : "/support/twoviolationsdaily/update",
				data : data.field,
				async : false,
				error : function(request) {
					parent.layer.alert("Connection error");
				},
				success : function(data) {
					if (data.code == 0) {
						parent.layer.msg("操作成功");
						parent.reLoad();
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


function getFileList(twoviolationsdailyId) {
	$.ajax({
		url : '/support/twoviolationsdaily/getFileList',
		method : 'post',
		data : {
			twoviolationsdailyId : twoviolationsdailyId
		},
		async : false,
		success : function(data) {
			if(data !=null && data.length>0){
				fileLastLen = (data[data.length-1].sort > data.length)?data[data.length-1].sort:data.length;
				var html="";
				for (var i=0 ; i<data.length;i++){

					var fileName = data[i].url.substr(data[i].url.lastIndexOf("\\")+1);
					html+='<tr id="hasFileList-'+ i +'">'+
							'<td>'+ fileName +'</td>'
						if(data[i].type==0){
							html+=	'<td> <a href='+data[i].url+' target="_blank"><img src='+data[i].url+'></a> </td>'
						}else{
							html+=	'<td> </td>'
						}
					if(data[i].type==2){
						html+=	'<td style="text-align: center"><a href='+data[i].url+' class="movie" datasrc='+data[i].url+' target="_blank"><i class="layui-icon layui-icon-play"></i></a></td>'
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
					// tr.find('.hasFile-delete').on('click', function(){
					// 	// console.log("==1==tr.remove()======");
					// 	// tr.remove();
					// 	// delFile(data[i].id);
					// 	// console.log("==2==tr.remove()======");
					// });

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
		url : "/support/twoviolationsdaily/delFile",
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
	window.location.href="/support/twoviolationsdaily/downloadFile?id="+id;

}


function getSchedulingDate(url){
	$.ajax({
		url : url,
		method : 'post',
		data : {},
		async : false,
		success : function(data) {
			if(data !=null && data.length>0){
				var html = '<option value=""></option>';
				for(var i=0;i<data.length;i++){
					html += '<option value="'+data[i]+'">'+data[i]+'</option>';
				}
				$("#schedulingDate").html(html);
				form.render('select', 'importantthingForm');
			}else{
				parent.layer.msg("获取值班日期下拉失败");
			}
		},
		error : function(data) {
			parent.layer.msg("获取值班日期下拉失败");
		}
	});
}

function closeWindow(){
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}