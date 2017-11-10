
$(function(){
	$("#pass").blur(function(){ passCheck(); });
	$("#password").blur(function(){ passwordCheck(); });
});

function updateSubAdmin()
{
	if (passCheck() && passwordCheck()){
		$("#addSubAdminForm").submit();
	}
}

function passCheck()
{
	var val = $("#pass").val();
	if(val == ""){
		$("#msg3").html('<label></label>请输入密码');
		$("#pass")[0].focus();
		return false;
	}
	else if(val.length < 6){
		$("#msg3").html('<label></label>密码长度必须大于6位');
		$("#pass")[0].focus();
		return false;
	}else {
		$("#msg3").html('<label class="l1"></label>');
		return true;
	}
}
function passwordCheck()
{
	var val = $("#password").val();
	var val1 = $("#pass").val();
	if(val == ""){
		$("#msg4").html('<label></label>请确认密码');
		$("#password")[0].focus();
		return false;
	}
	else if(val != val1){
		$("#msg4").html('<label></label>两次输入的密码不一至');
		$("#password")[0].focus();
		return false;
	}else {
		$("#msg4").html('<label class="l1"></label>');
		return true;
	}
}








