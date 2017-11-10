var height = $(window).height();
var width = $(window).width();
var right_height = 0;

$(function(){

	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	$("#ypassword").blur(function(){ ypasswordCheck(); });//当id为ypassword的元素失去焦点时，执行function里面的内容
	$("#password").blur(function(){ passwordCheck(); });
	$("#repassword").blur(function(){ repasswordCheck(); });
	
});

$(window).resize(function(){
	height = $(window).height();
	width = $(window).width();
	
	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
});



function ypasswordCheck()
{
	var result=false;
	var val = $("#ypassword").val();//获取id为ypassword的值
	if(val == ""){
		$("#msg1").html('<label></label>请输入旧密码');
		return false;
	}else{
		$.ajax({
			type:'post',
			url:'/user/checkPassword.html',
			data:{password:val},
			dataType: 'html',
			async: false,
			success:function(data){
			    if(data==1){
					$("#msg1").html('<label class="l1"></label>');
					result=true;
			    }else{
			    	$("#msg1").html('<label></label>原密码填写错误');
			    	result=false;
			    }
			}
		});
	}
	
	return result;
}

function passwordCheck()
{
	var val = $("#password").val();
	if(val == ""){
		$("#msg2").html('<label></label>请输入新密码');
		return false;
	}
	else if(val.length < 6){
		$("#msg2").html('<label></label>密码长度必须大于6位');
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
	if (ypasswordCheck() && passwordCheck() && repasswordCheck()){
		$.ajax({
			type:'post',
			url:'/user/editPass.html',
			data:{password:$("#password").val()},
			dataType: 'html',
			async: false,
			success:function(data){
			    if(data==1){
					//alert("密码修改成功，请重新登录");
			    	$(".msgBoxDIV").show();
					setTimeout("($(\".msgBoxDIV\").hide())",4000);
					setTimeout("top.window.location = '/exit.html'",5000);
					//top.window.location = '/exit.html';
			    }else{
			    	alert("操作错误");
					return false;
			    }			    
			}
		});
	}
}










