

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
	$("#domainhost").val($('#checkDomain').val());
	if($('#domainhost').val()==null || $('#domainhost').val().length<1){
		$("#error").html("<font color='red'>尚未设置邮箱域名，无法登录！</font>");
		$("#username")[0].focus();
	}else if($("#username").val().length<1){
		$("#error").html("<font color='red'>请输入邮箱用户名</font>");
		$("#username")[0].focus();
	}else if($("#password").val().length<1){
		$("#error").html("<font color='red'>请输入邮箱密码</font>");
		$("#password")[0].focus();
	}else{
		$.ajax({
			type:'post',
			url:'/user/checkUsernameExist.html',
			data:{username:$("#username").val(),domainhost:$("#domainhost").val()},
			dataType: 'html',
			async: false,
			error:function(){
				alert('数据库连接出错');
			},
			success:function(data){
			    if(data==1){
			    	$.ajax({
						type:'post',
						url:'/user/checkPass.html',
						data:{username:$("#username").val(),password:$("#password").val(),domainhost:$("#domainhost").val()},
						dataType: 'html',
						async: false,
						success:function(data){
				    		if(data==2){
						    	$("#error").html("<font color='red'>用户已冻结</font>");
				    		}else if(data==1){
						    	location.href='/index.html';
						    }else{
						    	$("#error").html("<font color='red'>密码错误</font>");
								$("#password")[0].focus();
						    }
						}
					});
			    }else{
			    	$("#error").html("<font color='red'>用户名不存在</font>");
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