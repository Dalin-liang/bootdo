$().ready(function() {
	getType();


});



function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/equipment/insert",
		async : false,		data : $('#signupForm').serialize(),// 你的formid

		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			console.log(data)
			if (data > 0) {
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
				required : icon + "请输入姓名"
			}
		}
	})
}

function getType(){
	$.ajax({
		cache : true,
		type : "get",
		url : "/support/equiptype/getAll",
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
				$("#equiptype_id").html(html)

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

	$.ajax({
		cache : true,
		type : "get",
		url : "/support/equipstatus/getAll",
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
				$("#equipstatus_id").html(html)

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});
	$.ajax({
		cache : true,
		type : "get",
		url : "/support/equipstorehouse/getAll",
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
				$("#equipstorehouse_id").html(html)

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

	$.ajax({
		cache : true,
		type : "get",
		url : "/team/query",
		async : false,

		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			console.log(data)
			if (data.total>0) {
				var html="";
				for(var i=0;i<data.rows.length;i++){
					html+="<option value="+data.rows[i].id+">"+data.rows[i].name+" </option>"
				}
				$("#team_id").html(html)
				teanPerson(data.rows[0].id);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}

function teanPerson(teamId){
	$.ajax({
		cache : true,
		type : "get",
		url : "/deptPerson/getDeptPersonByTeam",
		async : false,
		data:{'teamId':teamId},
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			console.log(data)
			if (data.length>0) {
				var html="";
				for(var i=0;i<data.length;i++){
					html+="<option title="+data[i].mobile+" value="+data[i].name+">"+data[i].name+" </option>"
				}
				$("#contact").html(html)
				layui.form.render("select");
				$("#mobile").val(data[0].mobile)
			} else {
				$("#contact").html('')
				$("#mobile").val('')
				layui.form.render("select");


			}

		}
	});
}