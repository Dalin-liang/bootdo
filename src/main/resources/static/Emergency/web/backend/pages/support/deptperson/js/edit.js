$().ready(function() {
	initSelect();
});
function closeWindow (){
	var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	parent.layer.close(index);
}

function update() {

	$.ajax({
		cache : true,
		type : "POST",
		url : "/deptPerson/updateDuty",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data> 0) {
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
				required : icon + "请输入名字"
			}
		}
	})
}

function initSelect(){
	$.ajax({
		cache : true,
		type : "get",
		url : "/deptPerson/getEmergencyDept",
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
				$("#dept_id").html(html)

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
}