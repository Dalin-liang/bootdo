var uploadInst;
$().ready(function() {


	initSelect();
	//选完文件后不自动上传
	layui.use('upload', function() {

		var $ = layui.jquery
			, upload = layui.upload

		//选完文件后不自动上传
		uploadInst=upload.render({
			elem: '#file_select'
			, url: '/support/knowledge/upload?relationId='+paramValue()
			, auto: false
			//,multiple: true
			, done: function (res) {
				parent.reLoad();
				console.log(res)
			}, error: function () {
				//演示失败状态，并实现重传
				layer.alert("上传文件失败")
			}
		});
	})
});

function update() {

	$.ajax({
		cache : true,
		type : "POST",
		url : "/support/knowledge/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {

				uploadInst.upload();
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}

function delFile(){
	$.ajax({
		cache : true,
		type : "POST",
		url : "/support/knowledge/delFile",
		data : {"fileId":$('#fileId').val()},//
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code ==1) {
				$("#file_txt").hide();;
				$("#file_select").show();;
				uploadInst.config.elem.next()[0].value = ''

				parent.reLoad();


			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
}
function closeWindow (){
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}
function initSelect()
{
	$.ajax({
		cache : true,
		type : "get",
		url : "/support/knowledge/getWarnType",
		async : false,

		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.length>0) {
				var html="";
				for(var i=0;i<data.length;i++){
					html+="<option value="+data[i].id+">"+data[i].name+" </option>"
				}
				$("#warnId").html(html)

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
}
function paramValue(){
	var result;
	var url=window.location.search; //获取url中"?"符后的字串
	if(url.indexOf("?")!=-1){
		result = url.substr(url.indexOf("=")+1);
	}
	return result;
}