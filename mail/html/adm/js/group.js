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
	
	$("#memberids").click(function(){
		if($(this).attr("checked")){
			$(".unitscla").attr("checked", true);
			$(".usersla").attr("checked", true);
		}else{
			$(".unitscla").attr("checked", false);
			$(".usersla").attr("checked", false);
		}
	});
	
});

function ycGroupsFun()
{
	var isunit = false;
	var isuser = false;
	$(".unitscla").each(function(i){
		if($(this).attr("checked")){
			isunit = true;
			return false;
		}
	});
	$(".usersla").each(function(i){
		if($(this).attr("checked")){
			isuser = true;
			return false;
		}
	});
	
	if(!isunit && !isuser){
		alert("请至少选择一项移除");
		return false;
	}else{
		if(window.confirm('确定移除吗？')){
			$("#ycGroups").submit();
		}
	}
}

