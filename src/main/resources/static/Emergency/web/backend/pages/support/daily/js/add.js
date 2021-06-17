$(function() {
	layui.use(['form','laydate','upload'], function () {
		var form = layui.form;
		var upload = layui.upload;

		$.ajax({
			url : '/deptPerson/getCurrentDeptPersonInfo',
			method : 'get',
			data : {
			},
			async : false,
			success : function(data) {
				if(data !=null && data !=""){
					$("#deptId").val(data.deptId);
					$("#deptpersonId").val(data.deptpersonId);
					$("#position").val(data.position);
					$("#deptName").val(data.deptName);
					$("#name").val(data.name);
					$("#position").attr('readonly',true);
					$("#deptName").attr('readonly',true);
					$("#name").attr('readonly',true);
				}else{
					layer.msg("获取当前用户信息失败");
				}
			},
			error : function(data) {
				layer.msg("获取信息失败，请联系管理员");
			}
		});
		
		$.ajax({
			url : "/scheduling/getUserSchedulingDate",
			method : 'post',
			data : {},
			async : false,
			success : function(data) {
				if(data !=null && data.length>0){
					var myDate = new Date;
					var year = myDate.getFullYear(); //获取当前年
					var mon = myDate.getMonth() + 1; //获取当前月
					var date = myDate.getDate();
					if(mon<=9){//格式化
						mon="0"+mon;
					}
					if(date<=9){
						date="0"+date;
					}
					var today = year+"-"+mon+"-"+date;
					var tempTime =data[0];

					var html = '';
					for(var i=0;i<data.length;i++){
						if(today == data[i]){
							tempTime =  data[i];
						}
						html += '<option value="'+data[i]+'">'+data[i]+'</option>';
					}
					$("#schedulingDate").html(html);
					$("#schedulingDate").val(tempTime);
					form.render('select', 'dailyForm');




				}else{
					parent.layer.msg("获取值班日期下拉失败");
				}
			},
			error : function(data) {
				parent.layer.msg("获取值班日期下拉失败");
			}
		});

		//多文件列表示例
		var ret_fileList = new Array();
		var demoListView = $('#demoList')
			,uploadListIns = upload.render({
			elem: '#testList'
			,url: '/support/daily/uploadFile'
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
					ret_fileItem.sort = index;
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


		form.on('submit(formSubmit)',function(data) {
			if (data.field.isException == "on") {
				data.field.isException = "1";
			} else {
				data.field.isException = "0";
			}
			if (data.field.isPersonInCharge == "on") {
				data.field.isPersonInCharge = "1";
			} else {
				data.field.isPersonInCharge = "0";
			}
			data.field.fileList = JSON.stringify(ret_fileList);
			$.ajax({
				cache : true,
				type : "POST",
				url : "/support/daily/save",
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
						parent.reLoad();
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

function save() {
	var isEx=	$('input[name="isException"]:checked ').val()//获取
	if(typeof (isEx)=="undefined"){

		$("#isException").val(0);
	}else{
		$("#isException").val(1);
	}

	var incharge=	$('input[name="isPersonInCharge"]:checked ').val()//获取
	if(typeof (incharge)=="undefined"){

		$("#isPersonInCharge").val(0);
	}else{
		$("#isPersonInCharge").val(1);
	}

	$.ajax({
		cache : true,
		type : "POST",
		url : "/support/daily/save",
		data : $('#signupForm').serialize(),// 你的formid
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
				parent.reLoad();
				parent.layer.alert(data.msg)
			}

		}
	});

}
function closeWindow(){
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}