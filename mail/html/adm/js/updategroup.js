var ligroup = 0;
$(function(){
	$("#mlistdiv").hover(
		function(){
			$(this).show();
		},
		function(){
			$(this).hide();
		}
	);
	$("#mailGroupNav ul li").hover(
		function(){
			$(this).find("div.btn_mg").show();
		},
		function(){
			$(this).find("div.btn_mg").hide();
		}
	);
	$(".mailgroup").hover(
		function(){
			$(this).show();
			$("#mailGroupNav ul li:eq("+ligroup+")").find("div").show();
		},
		function(){
			$(this).hide();
			$("#mailGroupNav ul li:eq("+ligroup+")").find("div").hide();
		}
	);
	
	var len = $("#selelctObj").find("li.obj").size();
	if(len > 0){
		for(var j=0; j<len; j++){
			var lilang = $("#selelctObj").find("li.obj:eq("+j+")").attr("lang");
			var ln = $("#typelist").find("input").size();
			for(var m=0; m<ln; m++){
				var iptval = $("#typelist").find("input:eq("+m+")").val();
				if(iptval == lilang){
					$("#typelist").find("input:eq("+m+")").attr("checked", true);
					$("#typelist").find("input:eq("+m+")").parent().parent().next(".clip").find("input").attr("disabled", true);
					break;
				}
			}
		}
	}
});

$(function(){
	$("#name").blur(function(){ nameCheck(); });
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

function getUserMember(src,id1){
	var id = id1;
	var name = $(src).html();
	var length = $("#selelctObj").find("li.obj1").size();
	if(length > 0){
		var isexit = false;
		for(var i=0; i<length; i++){
			var selid = $("#selelctObj").find("li.obj1:eq("+i+")").attr("lang");
			if(id == selid){
				$("#selelctObj").find("li.obj1:eq("+i+")").remove();
				isexit = true;
				break;
			}
		}
	}
	if(!isexit){
		$("#selelctObj").append('<li class="obj1" lang="'+id+'">'+name+'</li>');
	}
}

function updateSubAdmin()
{
	if (nameCheck() != false){
		var str1 = '';
		var str2 = '';
		var len = $("#selelctObj").find("li").size();
		var len1 = $("#selelctObj").find("li.obj").size();
		var len2 = $("#selelctObj").find("li.obj1").size();
		if(len == 0){
			alert("请选择组成员");
			return false;
		}else{
			for(var i=0; i<len1; i++){
				if(str1 == ''){
					str1 = $("#selelctObj").find("li.obj:eq("+i+")").attr("lang");
				}else{
					str1 += "," + $("#selelctObj").find("li.obj:eq("+i+")").attr("lang");
				}
			}
			for(var i=0; i<len2; i++){
				if(str2 == ''){
					str2 = $("#selelctObj").find("li.obj1:eq("+i+")").attr("lang");
				}else{
					str2 += "," + $("#selelctObj").find("li.obj1:eq("+i+")").attr("lang");
				}
			}
		}
		$("#groupunit").val(str1);
		$("#groupuser").val(str2);
		$("#addGroupsForm").submit();
	}
}

function nameCheck()
{
	var val = $("#name").val();
	var val1 = $("#groupsname").val();
	if(val == ""){
		$("#msg1").html('<label></label>请输入组名');
		$("#name")[0].focus();
		return false;
	}else if(val.length>40){
		$("#msg1").html('<label></label>组名太长');
		$("#name")[0].focus();
		return false;
	}else if(val != val1){
		$.ajax({
			type:'post',
			url:'/groups/checkNameExist.mail',
			data:{name:val},
			dataType: 'html',
			async: false,
			success:function(data){
			    if(data==1){
					$("#msg1").html('<label></label>此组名已存在');
					$("#name")[0].focus();
					return false;
			    }else{
			    	$("#msg1").html('<label class="l1"></label>');	
					return true;
			    }
			}
		});
	}else{
		$("#msg1").html('<label class="l1"></label>');	
		return true;
	}
}







