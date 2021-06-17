
$(function(){
    getCurUserInfo();
});


function getCurUserInfo(){
    $.ajax({
        url : '/EmyGetUserInfo',
        method : 'post',
        async : false,
        success : function(data) {
            if (data.code == '0') {
                $("#curUserName").html(data.name?data.name:data.username);
            } else {
                curUserAlertMsg("当前未登录，请先登录后台系统");
            }
        },
        error : function(data) {
            curUserAlertMsg("获取当前用户信息失败，请联系管理员");
        }
    });
}

function curUserAlertMsg(msg){
    layer.open({
        type: 1
        ,offset: 'auto'
        ,content: '<div style="padding: 20px 10px;text-align: center;">'+msg+'</div>'
        ,btn: '确认'
        ,btnAlign: 'c' //按钮居中
        ,shade: 0 //不显示遮罩
        ,area:["500px","200px"]
        ,yes: function(){
            window.location.href = "/login";
        }
    });
}