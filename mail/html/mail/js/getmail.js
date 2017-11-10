var height = $(window).height();
var width = $(window).width();
var right_height = 0;
var relation_width = 0;
var span_width = 0;
var mail_width = 0;

$(function(){	
	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	//设置主页面的宽度
	relation_width = width - 30;
	if(relation_width < 780){
		relation_width = 780
	}
	$(".relation").css("width", relation_width);
	span_width = relation_width - 444;
	$(".relation li span.span6").css("width", span_width);
	$(".relation li span.span9").css("width", span_width);
	$(".mail").css("width", relation_width);
	$(".mail").css("height", $(".relation").height());
	$(".button").css("width", relation_width);
	
	
	$(document).bind("click",function(e){
  		var target  = $(e.target);
  		if(target.closest(".tiaozhuan").length == 0 && target.closest(".tiaozhuanBtn").length == 0){
       		$(".tiaozhuan").hide();
  		}
 	});
	$(".cddelete").easydrag();	
});

$(window).resize(function(){
	height = $(window).height();
	width = $(window).width();
	
	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	//设置主页面的宽度
	relation_width = width - 30;
	if(relation_width < 780){
		relation_width = 780
	}
	$(".relation").css("width", relation_width);
	span_width = relation_width - 444;
	$(".relation li span.span6").css("width", span_width);
	$(".relation li span.span9").css("width", span_width);
	$(".mail").css("width", relation_width);
	$(".mail").css("height", $(".relation").height());
	$(".button").css("width", relation_width);
	
});

function tiaozhuanShow(event)
{
	if(event == 1){
		$(".tiaozhuan").css("top","80px");
		$(".tiaozhuan").css("right","30px");
		$(".tiaozhuan").css("bottom","auto");
	}else{
		x=event.clientX;
		y=event.clientY;		
		$(".tiaozhuan").css("top",y-60+"px");
		$(".tiaozhuan").css("right","30px");
		$(".tiaozhuan").css("bottom","auto");		
	}
	$(".tiaozhuan").show();
}

//跳转
function jump(){	
	var jumpnum = $('#jumpnum').val();//获取jumpnum 输入框的值
	$('#pagenow').val(jumpnum);	
	if(!isNaN(jumpnum)){//判断是否是数字
		$('#pageForm').attr('action','/email/getMail.html');
		$('#pageForm').submit();//提交表单
	}else{		
		document.getElementById("jumpnum").focus();//jumpnum获得焦点
		return false;
	}
}


//全选、    无     复选框
function selall(tag){	
	if(tag){
		$('#cbs').attr('checked',true);
		$('.box').attr('checked',true);		
	}else{
		$('#cbs').attr('checked',false);
		$('.box').attr('checked',false);
	}
}

//已读、未读    复选框
function selread(tag){
	//$('.box').attr('checked',false);//取消所有复选框 选中 状态
	$('.box').each(function(i){
		if(tag){
			if($(this).attr('state')==1){
				$('#cbs').attr('checked',false);
				$(this).attr('checked',true);
			}else{
				$('#cbs').attr('checked',false);
				$(this).attr('checked',false);
			}
		}else{
			if($(this).attr('state')==1){
				$('#cbs').attr('checked',false);
				$(this).attr('checked',false);
			}else{
				$('#cbs').attr('checked',false);
				$(this).attr('checked',true);
			}
		}
	});
}


function moverecive(tag){
	var ids = getCheckedInt('box');
	if(tag == 6){//判读确认删除
		ids[0] = 1;
	}
	if(ids.length>0){
		$('.box').each(function(i){
			if($(this).attr('checked')){
				$('.box1:eq('+i+')').attr('checked',true);
				$('.box2:eq('+i+')').attr('checked',true);
				$('.box3:eq('+i+')').attr('checked',true);
				$('.box4:eq('+i+')').attr('checked',true);
			}
		});
		if(tag==5 || tag == 6){			
			//if(window.confirm('彻底删除后邮件将不可恢复，确定要删除？')){
				$(".cddelete").show();				
				if(tag == 6){
					tag = 5;
					$('#edittype').val(tag);
					$('#editreciveform').submit();
				}
			//}
		}else{
			$('#edittype').val(tag);
			$('#editreciveform').submit();
		}
	}else if(tag==1){
		if(window.confirm('确定要将该页邮件全部标记为已读？')){
			$('#edittype').val(tag);
			$('#editreciveform').submit();
		}
	}else{
		$(".msgBoxDIV").show();
		setTimeout("($(\".msgBoxDIV\").hide())",4000);
		return false;
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
			    	parent.window.frames["left"].location.reload();//从当前页面刷新框架中另一页面
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
	var ids = getCheckedInt("box");	
	if($(src).val()==-2 || $(src).val()==0){
		$('.createlabel').show();
		if($(src).val()==0){
			$('.createlabel').hide();
			$('#addtotag').val('-1');
			$('#addtotag1').val('-1');
		}		
	}else if($(src).val()==-3 && (ids.length>0)){
		$('#tagid').val($(src).val());
		//$('#editRubMail').attr('action','/email/EditReciveMail.html');
		$("#editreciveform").submit();
	}else if($(src).val()==-4 && (ids.length>0)){
		$('#tagid').val($(src).val());
		//$('#editRubMail').attr('action','/email/EditReciveMail.html');
		$("#editreciveform").submit();
	}else if($(src).val()!=-1&&$(src).val()!=-2 && $(src).val()!=0){			
		if(ids.length<1){
			$(".msgBoxDIV").show();
			setTimeout("($(\".msgBoxDIV\").hide())",4000);
			
			$('#addtotag').val('-1');
			$('#addtotag1').val('-1');			
		}else{
			$('.box').each(function(i){
				if($(this).attr('checked')){
					$('.box1:eq('+i+')').attr('checked',true);
					$('.box2:eq('+i+')').attr('checked',true);
					$('.box3:eq('+i+')').attr('checked',true);
					$('.box4:eq('+i+')').attr('checked',true);
				}
			});
			$('#tagid').val($(src).val());
			$('#tagtype').val(1);
			$('#editreciveform').attr('action','/tag/doAddETag.html');
			$('#editreciveform').submit();
		}
	}else{//如果没有选中，出现提示信息
		if($(src).val()!=-1&&$(src).val()!=-2 && $(src).val()!=0){
			$(".msgBoxDIV").show();
			setTimeout("($(\".msgBoxDIV\").hide())",4000);
			return false;
		}		
	}
}

function deleteetag(id){
	if(id!=null&&id!=''&&id!=undefined){
		$('#tagid').val(id);
		$('#editreciveform').attr('action','/tag/deleteETag.html');
		$('#editreciveform').submit();
	}
}

function changemainmail(id,tag,boxtype){
	$.ajax({
		type:'post',
		url:'/email/EditMainMail.html',
		data:{id:id,ismain:tag,boxtype:boxtype},
		dataType: 'html',
		async: false,
		success:function(data){
    		if(data==1){
    			// 刷新页面
    			$('#pageForm').submit();
		    	return true;
		    }else{
		    	alert('星际邮件标记失败');
    			return false;
		    }
		}
	});
	return false;
}

function leftListClick1(url,orby,sortorder)
{	
	$('#orby').val(orby);
	$('#sortorder').val(sortorder);
	$('#editreciveform').attr('action',url);
	$("#editreciveform").submit();
}