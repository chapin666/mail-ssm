function goPage(num){
	$("#pagenow").val(num);
	$("#pageForm").submit();
}

function goPage1(num){
	$("#pagenow1").val(num);
	$("#pageForm1").submit();
}

function goPage2(num){
	$("#pagenow2").val(num);
	$("#pageForm2").submit();
}

function goPage3(num){
	$("#pagenow3").val(num);
	$("#pageForm3").submit();
}

function getCheckedInt(id){
	
	var strIds="";

	$("."+id).each(function(i){
		if($(this).attr("checked") == true){
			strIds += $(this).val()+",";
		}
	});
	
	strIds=strIds.substring(0, strIds.length-1);
	
	return strIds;
}

function getCheckedString(id){
	
	var strIds="";

	$("."+id).each(function(i){
		if($(this).attr("checked") == true){
			strIds += "'"+$(this).val()+"',";
		}
	});
	
	strIds=strIds.substring(0, strIds.length-1);
	
	return strIds;
}

function getCheckedStringLink(id){
	
	var strIds="";

	$("."+id).each(function(i){
		if($(this).attr("checked") == true){
			strIds += $(this).val()+"_1,";
		}
	});
	
	return strIds;
}

function getNoCheckedStringLink(id){
	
	var strIds="";

	$("."+id).each(function(i){
		if($(this).attr("checked") == false){
			strIds += $(this).val()+"_1,";
		}
	});
	
	return strIds;
}

function allsel(flag){
	if(flag){
		$('.box').attr('checked',true);
	}else{
		$('.box').attr('checked',false);
	}
}

function getCheckedInts(id){
	
	var strIds="";

	$("."+id).each(function(i){
		if($(this).attr("checked") == true){
			strIds += $(this).attr('lang')+"_"+$(this).attr('src')+",";
		}
	});
	
	strIds=strIds.substring(0, strIds.length-1);
	
	return strIds;
}
$(function(){
	$("#jumptopage").keydown(function(){
		//alert((event||window.event).keyCode);
		var k = (event||window.event).keyCode;
		if(k==8||k==46) return true;
		if( !((k>=96&&k<=105) || (k>=48&&k<=57) || k==13)){
			return false;
		}
		if(k==13){
			goPage($("#jumptopage").val().trim());
		}
	});
	
	
});