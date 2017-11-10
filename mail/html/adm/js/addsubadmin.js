
$(function(){
	$("#name").blur(function(){ nameCheck(); });
	$("#username").blur(function(){ usernameCheck(); });
	$("#pass").blur(function(){ passCheck(); });
	$("#password").blur(function(){ passwordCheck(); });
});

function getUnitMember(src){
	var id = $(src).val();
	var name = $(src).attr("lang");
	if($(src).attr('checked')){
		$(src).parent().parent().next(".clip").find("input").attr("disabled", true);
		$(src).parent().parent().next(".clip").find("input").attr("checked", false);
		var len = $(src).parent().parent().next(".clip").find("input").size();
		if(len > 0){
			for(var j=0; j<len; j++){
				var nexid = $(src).parent().parent().next(".clip").find("input:eq("+j+")").val();
				var length = $("#selelctObj").find("li").size();
				if(length > 0){
					for(var i=0; i<length; i++){
						var selid = $("#selelctObj").find("li:eq("+i+")").attr("lang");
						if(nexid == selid){
							$("#selelctObj").find("li:eq("+i+")").remove();
							break;
						}
					}
				}
			}
		}
		$("#selelctObj").append('<li class="obj" lang="'+id+'">'+name+'</li>');
	}else{
		var length = $("#selelctObj").find("li").size();
		if(length > 0){
			for(var i=0; i<length; i++){
				var selid = $("#selelctObj").find("li:eq("+i+")").attr("lang");
				if(id == selid){
					$("#selelctObj").find("li:eq("+i+")").remove();
					break;
				}
			}
		}
		$(src).parent().parent().next(".clip").find("input").attr("disabled", false);
	}
}

function updateSubAdmin()
{
	if (nameCheck() && usernameCheck() != false && passCheck() && passwordCheck()){
		var str = '';
		$(".boxname").each(function(i){
			if($(this).attr("checked")){
				if(str == ''){
					str = $(this).val();
				}else{
					str += "," + $(this).val();
				}
			}
		});
		if(str == ''){
			alert("请选择操作权限");
			return false;
		}else{
			$("#type").val(str);
			str = '';
			var len = $("#selelctObj").find("li").size();
			if(len == 0){
				alert("请选择管理范围");
				return false;
			}else{
				for(var i=0; i<len; i++){
					if(str == ''){
						str = $("#selelctObj").find("li:eq("+i+")").attr("lang");
					}else{
						str += "," + $("#selelctObj").find("li:eq("+i+")").attr("lang");
					}
				}
			}
			$("#range").val(str);
			$("#addSubAdminForm").submit();
		}
	}
}

function nameCheck()
{
	var val = $("#name").val();
	if(val == ""){
		$("#msg1").html('<label></label>请输入姓名');
		$("#name")[0].focus();
		return false;
	}else if(val.length>40){
		$("#msg1").html('<label></label>姓名太长');
		return false;
	}else{
		$("#msg1").html('<label class="l1"></label>');	
		return true;
	}
}

function usernameCheck()
{
	var val = $("#username").val();
	if(val == ""){
		$("#msg2").html('<label></label>请输入用户名');
		$("#username")[0].focus();
		return false;
	}else if(val.length > 40){
		$("#msg2").html('<label></label>用户名太长');
		return false;
	}else{
		$.ajax({
			type:'post',
			url:'/member/checkUserNameExist.mail',
			data:{username:val},
			dataType: 'html',
			async: false,
			success:function(data){
			    if(data==1){
					$("#msg2").html('<label></label>此管理员已存在');
					$("#username")[0].focus();
					return false;
			    }else{
			    	$("#msg2").html('<label class="l1"></label>');	
					return true;
			    }
			}
		});
	}
}
function passCheck()
{
	var val = $("#pass").val();
	if(val == ""){
		$("#msg3").html('<label></label>请输入密码');
		$("#pass")[0].focus();
		return false;
	}else if(val.length < 6){
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








