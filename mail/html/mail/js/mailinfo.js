var height = $(window).height();
var width = $(window).width();
var right_height = 0;
var relation_width = 0;
var span_width = 0;
var mail_width = 0;

$(function(){
	
	 $(".reportmail").easydrag();
	 $(".DoRecall").easydrag();
	 
	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	//设置主页面的宽度
	relation_width = width - 30;
	if(relation_width < 650){
		relation_width = 650
	}
	$(".mail").css("width", relation_width);
	$(".button").css("width", relation_width);
	
	$(document).bind("click",function(e){
  		var target  = $(e.target);
  		if(target.closest(".tiaozhuan").length == 0 && target.closest(".tiaozhuanBtn").length == 0){
       		$(".tiaozhuan").hide();
  		}
 	});
	
});

$(window).resize(function(){
	height = $(window).height();
	width = $(window).width();
	
	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	//设置主页面的宽度
	//设置主页面的宽度
	relation_width = width - 30;
	if(relation_width < 650){
		relation_width = 650
	}
	$(".mail").css("width", relation_width);
	$(".button").css("width", relation_width);
	
});


function tiaozhuanShow(n)
{
	if(n == 1){
		$(".tiaozhuan").css("top","80px");
		$(".tiaozhuan").css("right","30px");
		$(".tiaozhuan").css("bottom","auto");
	}else{
		$(".tiaozhuan").css("top","auto");
		$(".tiaozhuan").css("right","30px");
		$(".tiaozhuan").css("bottom","50px");
	}
	$(".tiaozhuan").show();
}

function selall(tag){
	if(tag){
		$('.box').attr('checked',true);
	}else{
		$('.box').attr('checked',false);
	}
}

function selread(tag){
	$('.box').attr('checked',false);
	$('.box').each(function(i){
		if(tag){
			if($(this).attr('lang')==1){
				$(this).attr('checked',true);
			}else{
				$(this).attr('checked',false);
			}
		}else{
			if($(this).attr('lang')==1){
				$(this).attr('checked',false);
			}else{
				$(this).attr('checked',true);
			}
		}
	});
}

function moveinfo(tag){	
	if(tag==2 || tag == 6){
		$(".cddelete").show();				
		if(tag == 6){
			tag = 2;
			$('#edittype').val(tag);
			$('#editinfoform').attr('action','/email/EditInfoMail.html');
			$('#editinfoform').submit();
		}		
	}else{
		$('#edittype').val(tag);
		$('#editinfoform').attr('action','/email/EditInfoMail.html');
		$('#editinfoform').submit();
	}
}

function addtag(){
	
	if($('#tagname').val().length>0){
		$.ajax({
			type:'post',
			url:'/tag/checkNameExist.html',
			data:{name:$("#tagname").val(),userid:$("#userid").val()},
			dataType: 'html',
			async: false,
			success:function(data){
	    		if(data==1){
	    			//alert('标签名称已存在');
	    			$("#tagsp1").html('<label></label>标签名称已存在');
			    	return false;
			    }else{
			    	$('#addtagform').submit();
	    			return true;
			    }
			}
		});
		return false;
	}else{
		alert('请输入标签名称');
		return false;
	}
}

function addetag(src){
	var tag = $(src).val();
	if($(src).val()==-2 || $(src).val()==0){
		$('.createlabel').show();
		if($(src).val()==0){
			$('.createlabel').hide();
			$('#addtotag').val('-1');
			$('#addtotag1').val('-1');
		}
	}else if(tag == -3){
		$('#edittype').val(tag);
		$('#editinfoform').attr('action','/email/EditInfoMail.html');		
		$('#editinfoform').submit();
	}else if(tag == -4){
		$('#edittype').val(tag);
		$('#editinfoform').attr('action','/email/EditInfoMail.html');
		$('#editinfoform').submit();
	}else if(tag != -1&& tag != -2 && $(src).val()!=0){
		var ids = getCheckedInt("box");
		if(ids.length<1){
			alert("请选择邮件");
			$('#addtotag').val('-1');
			$('#addtotag1').val('-1');
		}else{
			$('#tagid').val(tag);
			alert('标记成功');
			$('#editinfoform').attr('action','/tag/doAddETag6.html');
			$('#editinfoform').submit();
		}
	}
}

function deleteetag(id){
	if(id!=null&&id!=''&&id!=undefined){
		$('#tagid').val(id);
		$('#editinfoform').attr('action','/tag/deleteETag6.html');
		$('#editinfoform').submit();
	}
}

function getback(){
	$.ajax({
		type:'post',
		url:'/email/getBackMail.html',
		data:{id:$('#emailids').val()},
		dataType: 'html',
		async: false,
		success:function(data){
    		if(data==1){
    			alert('邮件已撤回');
    			//location.href='/email/MailInfo.html?id='+$('#emailid').val();
    			$('#emailtoname').hide();
    			$('#emailcopyto').hide();
		    	return true;
		    }else{
		    	alert('邮件撤回失败');
    			return false;
		    }
		}
	});
	return false;
}

function rewrite(tag){
	$('#typeid').val(tag);
	$('#rewriteform').submit();
}

function reportmail()
{
	$("#report").submit();
}

function report(){
	$(".reportmail").show();
}

function DoRecall(){
	
	$(".DoRecall").show();
}

function recall()
{
	var valemailid = $("#emailid").val();
	var valtomail=$("#tomail").val();
	$.ajax({
		type:'post',
		url:'/email/RecallMail.html',
		data:{emailid:valemailid,
			tomail:valtomail},
		dataType: 'html',
		async: false,
		success:function(data){
			$("#recalltext_div_1").hide();
	    	$("#recalltext_div_2").show();
	    	
	    	$("#btn_blue").hide();
	    	$("#btn_gray").hide();
	    	$("#btn_ok").show();
	    	
		    if(data==0){
		    	$("#recallresult").html("撤回成功");
				return true;
		    }else if(data == 1){
		    	$("#recallresult").html("撤回失败，邮件已经不存在");
				return false;
		    }else if(data == 2){
		    	$("#recallresult").html("撤回失败，邮件已经被查看");
				return false;
		    }else if(data == 3)
	    	{
		    	$("#recallresult").html("只能撤销本服务器邮件");
				return false;
	    	}
		}
	});
}

function RecallClose() {
	$("#recalltext_div_1").show();
	$("#recalltext_div_2").hide();
	
	$("#btn_blue").show();
	$("#btn_gray").show();
	$("#btn_ok").hide();
	
	$("#recallresult").html("");
	
	$(".DoRecall").hide();
}