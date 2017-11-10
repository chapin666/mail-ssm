$(function(){
	$("#loginBtn").hover(
		function(){
			$(this).removeClass("loginBtn").addClass("loginBtnOn");
		},
		function(){
			$(this).removeClass("loginBtnOn").addClass("loginBtn");
		}
	);
	$("#resetBtn").hover(
		function(){
			$(this).removeClass("resetBtn").addClass("resetBtnOn");
		},
		function(){
			$(this).removeClass("resetBtnOn").addClass("resetBtn");
		}
	);
});

function login(){
	if($("#username").val().length<1){
		$("#error").html("<font color='red'>请输入管理员帐号</font>");
		$("#username")[0].focus();
	}else if($("#password").val().length<1){
		$("#error").html("<font color='red'>请输入管理员密码</font>");
		$("#password")[0].focus();
	}else{
		$.ajax({
			type:'post',
			url:'/member/checkUserNameExist.mail',
			data:{username:$("#username").val()},
			dataType: 'html',
			async: false,
			error:function(){
				alert('数据库连接出错');
			},
			success:function(data){
			    if(data==1){
			    	$.ajax({
						type:'post',
						url:'/member/checkPass.mail',
						data:{username:$("#username").val(),password:$("#password").val()},
						dataType: 'html',
						async: false,
						success:function(data){
				    		if(data==1){
						    	location.href='/adm/index.mail';
						    }else{
						    	$("#error").html("<font color='red'>密码错误</font>");
								$("#password")[0].focus();
						    }
						}
					});
			    }else{
			    	$("#error").html("<font color='red'>管理员帐号不存在</font>");
					$("#username")[0].focus();
			    }
			}
		});
		return false;
	}
}

function resets(){

	$('#username').val('');
	$('#password').val('');
}