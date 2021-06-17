var uploadInst;
$().ready(function() {
	initSelect();


	layui.use('upload', function(){
		var $ = layui.jquery
			,upload = layui.upload

		//选完文件后不自动上传
		 uploadInst =	upload.render({
			elem: '#test8'
			,url: '/support/knowledge/upload'
			,auto: false
			//,multiple: true
			,bindAction: '#savebtn'
			,done: function(res){
				 parent.reLoad();
			} , error: function () {
				//演示失败状态，并实现重传
				layer.alert("上传文件失败")
			},before: function(obj){
				 this.data={'relationId':$("#id").val()};//关键代码
			 }

		 });
	});
});


function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/support/knowledge/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				$("#id").val(data.relationId);
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
			console.log((data))
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
;