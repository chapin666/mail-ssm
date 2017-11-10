var height = $(window).height();
var width = $(window).width();
var right_height = 0;
var relation_width = 0;
var span_width = 0;
var mail_width = 0;

$(function(){
	
	if($('#gname').val()!=undefined&&$('#gname').val().length>0){
		$('.groupmail').show();
		$('#mailgroupimg').attr('src','/adm/images/tree/nolines_minus.gif');
	}
	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	//设置主页面的宽度
	relation_width = width - 220;
	if(relation_width < 680){
		relation_width = 680
	}
	$(".relation").css("width", relation_width);
	span_width = relation_width - 413;
	$(".relation li span.span6").css("width", span_width);
	$(".relation li span.span4").css("width", span_width);
	mail_width = relation_width + 190;
	$(".mail").css("width", mail_width);
	$(".button").css("width", mail_width);
	
});

$(window).resize(function(){
	height = $(window).height();
	width = $(window).width();
	
	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	//设置主页面的宽度
	relation_width = width - 220;
	if(relation_width < 680){
		relation_width = 680
	}
	$(".relation").css("width", relation_width);
	span_width = relation_width - 413;
	$(".relation li span.span6").css("width", span_width);
	$(".relation li span.span4").css("width", span_width);
	mail_width = relation_width + 190;
	$(".mail").css("width", mail_width);
	$(".button").css("width", mail_width);
	
});

function addugroup(){
	
	if($('#ugname').val().length>0){
		$.ajax({
			type:'post',
			url:'/ugroup/checkNameExist.html',
			data:{name:$("#ugname").val(),userid:$("#userid").val()},
			dataType: 'html',
			async: false,
			success:function(data){
	    		if(data==1){
	    			//alert('组名称已存在');
	    			$("#msg3").html('<label></label>组名称已存在');
			    	return false;
			    }else{
			    	$('#addugroupform').submit();
	    			return true;
			    }
			}
		});
		return false;
	}else{
		//alert('请输入联系组名称');
		$("#msg3").html('<label></label>');
		$("#msg3").html('<label></label>请输入联系组名称');
		return false;
	}
}

function deleteugroup(id,tag){
	$("#deleteugroup").show();
	if(tag){
		$.ajax({
			type:'post',
			url:'/ugroup/deleteUGroup.html',
			data:{id:id},
			dataType: 'html',
			async: false,
			success:function(data){
	    		if(data!=1){
			    	//alert('系统忙，请稍后再试');
	    			$("#msgsysDIV").show();
	    			setTimeout("($(\"#msgsysDIV\").hide())",4000);
	    			return false;
			    }else{
			    	location.href='/contact/getContactList.html';
			    }
			}
		});
		return false;
	}
}

function deleteugroup1(ugid,cid){
	if(window.confirm('确定要解除此联系组？')){
		$.ajax({
			type:'post',
			url:'/contact/deleteCong.html',
			data:{ugid:ugid,cid:cid},
			dataType: 'html',
			async: false,
			success:function(data){
	    		if(data!=1){
			    	alert('系统忙，请稍后再试');
	    			return false;
			    }else{
			    	location.href='/contact/getContactList.html?ugid='+$('#ugid1').val()+'&name='+$('#ugname2').val();
			    }
			}
		});
		return false;
	}
}

function editugroup(){
	if($('#ugname1').val().length>0){
		if($('#ugname1').val()==$('#ugname2').val()){
			$('.editlabel').hide();
			return true ;
		}else{
			$.ajax({
				type:'post',
				url:'/ugroup/checkNameExist.html',
				data:{name:$("#ugname1").val(),userid:$("#userid").val()},
				dataType: 'html',
				async: false,
				success:function(data){
		    		if(data==1){
		    			//alert('组名称已存在');
		    			$("#msg4").html('<label></label>组名称已存在');
				    	return false;
				    }else{
				    	$('#editugroupform').submit();
		    			return true;
				    }
				}
			});
			return false;
		}
	}else{
		//alert('请输入联系组名称');
		$("#msg4").html('<label></label>');
		$("#msg4").html('<label></label>请输入联系组名称');
		return false;
	}
}

function addcong(src){
	
	if($(src).val()==-2 || $(src).val()==0){
		$('.createlabel').show();
		if($(src).val()==0){
			$('.createlabel').hide();
			$('#addtogroup').val('-1');
			$('#addtogroup1').val('-1');
		}
	}else if($(src).val()!=-1&&$(src).val()!=-2 && $(src).val()!=0){
		var ids = getCheckedInt("box");
		if(ids.length<1){
			alert("请选择联系人");
			$('#addtogroup').val('-1');
			$('#addtogroup1').val('-1');
		}else{
			$('#cname').val(src.options[src.selectedIndex].text);
			$('#cgid').val($(src).val());
			$('#addcongform').submit();
		}
	}
}

function deletecontact(tag){
	var ids = getCheckedInt("box");	
	if(ids.length<1){
		//alert("请选择联系人");
		$("#msgNameDIV").show();
		setTimeout("($(\"#msgNameDIV\").hide())",4000);
	}else{
		//if(window.confirm('确定删除选中联系人？')){
		$("#deletecontact").show();		
		if(tag){
			$('#addcongform').attr('action','/contact/deleteContact.html');
			$('#addcongform').submit();
		}			
	}
}


function checkName(){
	if($('#name').val().length<1){
		//alert('请输入联系人姓名');
		$("#msg1").html('<label></label>请输入联系人姓名');
		return false;
	}else{
		$("#msg1").html('<labe></label>');
		return true;
	}
}

function checkAddr(){
	if($('#addr').val().length<1){
		//alert('请输入联系人邮箱地址');
		$("#msg2").html('<label></label>请输入联系人邮箱地址');
		return false;
	}else{
		var tag = false ;
		$.ajax({
			type:'post',
			url:'/contact/checkAddrExist.html',
			data:{addr:$("#addr").val()},
			dataType: 'html',
			async: false,
			success:function(data){
	    		if(data==1){
	    			//alert('联系人已存在');
	    			$("#msg2").html('<label></label>联系人已存在');
			    	tag = false;
			    }else{
			    	/*$.ajax({
						type:'post',
						url:'/contact/checkUserExist.html',
						data:{addr:$("#addr").val()},
						dataType: 'html',
						async: false,
						success:function(data){
				    		if(data==1){
						    	tag = true;
						    }else{
						    	alert('联系人邮箱地址不存在');
				    			tag = false;
						    }
						}
					});*/			    	
					tag = true;
			    }
			}
		});
		return tag;
	}
}

function addcontact(){
	var tag1 = checkName();
	if(tag1){
		var tag2 = checkAddr();
		if(tag2){
			$('#addcform').submit();
		}
	}
}

function gotowrite(){
	var ids = getCheckedInts("box");

	if(ids.length<1){
		location.href='/email/Write.html';
	}else{
		location.href='/email/Write.html?cids='+ids;
	}
}

function showgm(){
	if($('.groupmail').css('display')=='none'){
		$('.groupmail').show();
		$('#mailgroupimg').attr('src','/adm/images/tree/nolines_minus.gif');
	}else{
		$('.groupmail').hide();
		$('#mailgroupimg').attr('src','/adm/images/tree/nolines_plus.gif');
	}
}