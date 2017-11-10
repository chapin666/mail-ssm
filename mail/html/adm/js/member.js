$(function(){
	$("#name").blur(function(){ nameCheck(); });
	$("#username").blur(function(){ usernameCheck(); });
	$("#pass").blur(function(){ passCheck(); });
	$("#password").blur(function(){ passwordCheck(); });
	$("#phone").blur(function(){ checkPhone(); });
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
		$("#msg1").html('<label></label>请输入成员名称');
		return false;
	}else if(val.length>40){
		$("#msg1").html('<label></label>成员名称太长');
		return false;
	}else{
		$("#msg1").html('<label class="l1"></label>');	
		return true;
	}
}

function usernameCheck()
{
	var val = $("#username").val();
	var domainhost = $("#checkDomain").val();	
	if(val == ""){
		$("#msg2").html('<label></label>请输入用户名');
		return false;
	}else if(val.length>40){
		$("#msg2").html('<label></label>用户名太长');
		return false;
	}else{
		if(val.indexOf('<')>-1||val.indexOf('>')>-1||val.indexOf('@')>-1){
			$("#msg2").html('<label></label>用户名不能包含特殊字符');
			return false;
		}else{
			$.ajax({
				type:'post',
				url:'/user/checkUsernameExist.mail',
				data:{username:val,domainhost:domainhost},
				dataType: 'html',
				async: false,
				success:function(data){
				    if(data==1){
						$("#msg2").html('<label></label>此用户已存在');
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
}

function passCheck()
{
	var val = $("#pass").val();
	var val2 =  val.substr(0,1);//截取第一个字符	
	var n=(val.split(val2)).length-1;//第一个字符出现的次数		
	if(val == ""){
		$("#msg3").html('<label></label>请输入密码');
		return false;
	}else if(val.length < 6){
		$("#msg3").html('<label></label>密码长度必须大于6位');
		return false;
	}else if(val.length > 50){
		$("#msg3").html('<label></label>密码太长');
		return false;
	}else if(val.length == n){
		$("#msg3").html('<label></label>密码太简单');
		return false;
	}else {
		$("#msg3").html('<label class="l1"></label>');
		return true;
	}
}

function passwordCheck()
{
	if(passCheck()){
		var val = $("#password").val();
		var val1 = $("#pass").val();
		if(val == ""){
			$("#msg4").html('<label></label>请确认密码');
			return false;
		}
		else if(val != val1){
			$("#msg4").html('<label></label>两次输入的密码不一至');
			return false;
		}else {
			$("#msg4").html('<label class="l1"></label>');
			return true;
		}
	}
}

function checkPhone(){
	var val = $("#phone").val();
	if(val == ""){
		$("#msg6").html('');
		return true;
	}else if(!isMobile(val)){
		$("#msg6").html('<label></label>电话号码格式不正确');
		return false;
	}else{
		$("#msg6").html('<label class="l1"></label>');
		return true;
	}
}

function addUser(){
	var obj=document.getElementById('checkDomain'); 
	$("#domainhost").val(obj.options[obj.selectedIndex].text);
	
	if(nameCheck()){
		if(usernameCheck()!=false){
			if(passCheck()){
				if(passwordCheck()){
					if(checkPhone()){
						if($('#parent_id').val()!=-1){
							$("#msg5").html('<label class="l1"></label>');
							$.ajax({
								type:'post',
								url:'/user/checkUserSize.mail',
								dataType: 'html',
								async: false,
								success:function(data){
								    if(data==1){
										alert('用户数量已达到2000，无法再继续添加');
										return false;
								    }else{
								    	$('#adduserform').submit();	
										return true;
								    }
								}
							});
						}else{
							$("#msg5").html('<label></label>请选择部门');
						}
					}
				}
			}
		}
	}
}

function updateUser(){
	if(nameCheck()){
		if(passCheck()){
			if(passwordCheck()){
				if(checkPhone()){
					if($('#parent_id').val()!=-1){
						$("#msg5").html('<label class="l1"></label>');
						$.ajax({
							type:'post',
							url:'/user/checkUserSize.mail',
							dataType: 'html',
							async: false,
							success:function(data){
							    if(data==1){
									alert('用户数量已达到2000，无法再继续添加');
									return false;
							    }else{
							    	$('#edituserform').submit();	
									return true;
							    }
							}
						});
					}else{
						$("#msg5").html('<label></label>请选择部门');
					}
				}
			}
		}
	}
}

function showChild() {
	
   var obj=event.srcElement;
   var s1=$(obj).nextAll("select");
   
   s1.each(function(i){
	   $(this).remove();
   });
  
   var value1=$(obj).val();
   	   
   if(value1==-1){
	   if($(obj).prev().val()==undefined){
		   $('#parent_id').val(-1);
	   }else{
		   $('#parent_id').val($(obj).prev().val());
	   }
   }else{
	   $('#parent_id').val(value1);
   }
   
   $.ajax({
	   	url:'/unit_ajax/getUnitByJson.mail',
		cache :false,
		data : {pid :value1},
		type :'Post',
		dataType :'json',
		timeout :20000,
		error : function() {
			alert("出错啦");
		},
		success : function(json) {
			var len = json.length;
			if (len != 0) {
				$("<select onchange='showChild()' class='inputname'></select>").appendTo("#layer");
				$("<option value='-1'>---请选择---</option>").appendTo("#layer>select:last");
				$.each(json, function(i){
					$("<option value="+json[i].id+">&nbsp;"+json[i].name+ "</option>").appendTo("#layer>select:last");
				});
			}
		}
	});
}

function isMobile(s) { 
	var reg = /^[1][0-9][0-9]{9}$/; 
	var re = new RegExp(reg);
	if (re.test(s)) { 
		return true; 
	} else { 
		return false; 
	} 
}