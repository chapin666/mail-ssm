
$(function(){
	$("#ypassword").blur(function(){ ypasswordCheck(); });
	
	$("#password").blur(function(){ passwordCheck(); });
	
	$("#repassword").blur(function(){ repasswordCheck(); });
});



function ypasswordCheck()
{
	var val = $("#ypassword").val();
	if(val == ""){
		$("#msg1").html('<label></label>请输入旧密码');
		return false;
	}else{
		$.ajax({
			type:'post',
			url:'/member/checkPassword.mail',
			data:{password:val},
			dataType: 'html',
			async: false,
			success:function(data){
			    if(data==1){
					$("#msg1").html('<label class="l1"></label>');	
					return true;
			    }else{
			    	$("#msg1").html('<label></label>原密码填写错误');
					return false;
			    }
			}
		});
	}
}

function passwordCheck()
{
	var val = $("#password").val();
	if(val == ""){
		$("#msg2").html('<label></label>请输入新密码');
		return false;
	}else if(val.length < 6){
		$("#msg2").html('<label></label>密码长度必须大于6位');
		return false;
	}else if(val.length > 50){
		$("#msg2").html('<label></label>密码太长');
		return false;
	}else {
		$("#msg2").html('<label class="l1"></label>');
		return true;
	}
}


function repasswordCheck()
{
	var val = $("#repassword").val();
	var val1 = $("#password").val();
	if(val == ""){
		$("#msg3").html('<label></label>请确认密码');
		return false;
	}
	else if(val != val1){
		$("#msg3").html('<label></label>两次输入的密码不一至');
		return false;
	}else {
		$("#msg3").html('<label class="l1"></label>');
		return true;
	}
}

function updatePassword()
{
	if (ypasswordCheck() != false && passwordCheck() && repasswordCheck()){
		$.ajax({
			type:'post',
			url:'/member/updatePassword.mail',
			data:{password:$("#password").val()},
			dataType: 'html',
			async: false,
			success:function(data){
			    if(data==1){
					alert("密码修改成功，请重新登录");
					top.window.location = '/adm/exit.mail';
			    }else{
			    	alert("操作错误");
					return false;
			    }
			}
		});
	}
}










