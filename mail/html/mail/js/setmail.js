/*

var height = $(window).height();
var width = $(window).width();
var right_height = 0;
var relation_width = 0;
var span_width = 0;
var mail_width = 0;

$(function(){
	$(".cddelete").easydrag();
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
		$('#pageForm').attr('action','/email/delMail.html');
		$('#pageForm').submit();//提交表单
	}else{		
		document.getElementById("jumpnum").focus();//jumpnum获得焦点
		return false;
	}
}

function selall(tag){
	if(tag){
		$('#cbs').attr('checked',true);
		$('.box').attr('checked',true);
	}else{
		$('#cbs').attr('checked',false);
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

function movedel(tag){
	var ids = getCheckedInt('box');
	if(ids.length>0){
		$('.box').each(function(i){
			if($(this).attr('checked')){
				$('.box1:eq('+i+')').attr('checked',true);
				$('.box2:eq('+i+')').attr('checked',true);
				$('.box3:eq('+i+')').attr('checked',true);
				$('.box4:eq('+i+')').attr('checked',true);
				$('.box5:eq('+i+')').attr('checked',true);
			}
		});
		$('#edittype').val(tag);
		$('#editdelform').submit();
		
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
			    	$('#addtagform').attr('action','/tag/doAddTag3.html');
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
	if($(src).val()!=-1&&$(src).val()!=-2){
		var ids = getCheckedInt("box");
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
					$('.box5:eq('+i+')').attr('checked',true);
				}
			});
			$('#tagid').val($(src).val());
			$('#editdelform').attr('action','/tag/doAddETag3.html');
			$('#editdelform').submit();
		}
	}
}





function addtag1(){
	
	if($('#tagname').val().length>0){
		$.ajax({
			type:'post',
			url:'/tag/checkNameExist.html',
			data:{name:$("#tagname").val(),userid:$("#userid").val()},
			dataType: 'html',
			async: false,
			success:function(data){
	    		if(data==1){
	    			alert('标签名称已存在');
			    	return false;
			    }else{
			    	$('#addtagform').attr('action','/tag/doAddTag7.html');
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





function addetag1(src){
	if($(src).val()!=-1&&$(src).val()!=-2){
		var ids = getCheckedInt("box");
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
					$('.box5:eq('+i+')').attr('checked',true);
				}
			});
			$('#tagid').val($(src).val());
			$('#editdelform').attr('action','/tag/doAddETag7.html');
			$('#editdelform').submit();
		}
	}
}



function deleteetag1(id){
	if(id!=null&&id!=''&&id!=undefined){
		$('#tagid').val(id);
		$('#editdelform').attr('action','/tag/deleteETag7.html');
		$('#editdelform').submit();
	}
}
function deleteetag(id){
	if(id!=null&&id!=''&&id!=undefined){
		$('#tagid').val(id);
		$('#editdelform').attr('action','/tag/deleteETag3.html');
		$('#editdelform').submit();
	}
}


*/



parent.window.frames["left"].location.reload();//从当前页面刷新框架中另一页面

$(function(){
	
});

$(document).ready(function(){
   $("ul.menu li:first-child").addClass("current");
   $("div.content").find("div.layout:not(:first-child)").hide();
   $("div.content div.layout").attr("id", function(){
   		return idNumber("No")+ $("div.content div.layout").index(this);
   });
   $("ul.menu li").click(function(){
       var c = $("ul.menu li");
       var index = c.index(this);
       var p = idNumber("No");
        show(c,index,p);
    });
    
    function show(controlMenu,num,prefix){
        var content= prefix + num;
        $('#'+content).siblings().hide();
        $('#'+content).show();
        controlMenu.eq(num).addClass("current").siblings().removeClass("current");
    };
 
    function idNumber(prefix){
        var idNum = prefix;
        return idNum;
    };
    
    var signstate = $("#signstate").val();       
	if(signstate == 1){
		$(".menu li:last").click();
	}else{
		$(".menu li:first").click();
	}
 });

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

function newTag(){
	$('#tagname').val("");
	$('#edittag').val('0');
	$('#tags').show();
}



function edittag(id,name){
	$('#id').val(id);
	$('#tagname').val(name);
	$('#edittag').val('1');
	$('#tags').show();
}


function addsign(){	
	if($('#signName').val().length>0){
		if($('#signName').val().length>50){
			$("#signsp1").html('<label></label>签名过长');
			return false;
		}
		if($('#content').val().length>500){
			$("#contentsp1").html('<label></label>签名内容过长');
			return false;
		}
		
		$.ajax({
			type:'post',
			url:'/user/modifySign.html',
			data:{signName:$("#signName").val(),state:$("#state").val(),content:$("#content").val(),id:$("#eid2").val()},
			dataType :'json',
			async: false,
			timeout :20000,         //超时时间：20000
			error : function() {
				alert("出错啦");
			},
			success:function(json){
				if(json==null){
	    			$("#signsp1").html('<label></label>签名已存在');
			    	return false;
			    }else{
					var len = json.totalProperty;
					
					var strHtml="<option value='-1'>不使用</option>";
					$.each(json.root, function(i) {
						strHtml+=("<option value="+json.root[i].id+">&nbsp;"+json.root[i].name+ "</option>");
					});
					$("#signselect").html(strHtml);
					
			    	$('#sign').hide();
	    			return true;
			    }		   
			}
		});
		return false;
	}else{
		$("#signsp1").html('<label></label>请输入签名');
		return false;
	}	
}

$(function(){
	$("#signselect").click(function(){
		if($("#signselect").val()!=-1){				
			var defaultsign = $("#signselect option:selected").text();//选择项的文本
			$("#eid").val($("#signselect").val());
			$("#eSignName").val(defaultsign);
			$.ajax({
				type:'post',
				url:'/user/modifyDefaultSign.html',
				data:{id:$("#signselect").val(),defaultsign:defaultsign},
				dataType: 'html',
				async: false,
				success:function(data){
		    		if(data!=null){
		    			$("#econtent").val(data);	
				    }
				}
			});
			$("#editsign").show();			
		}
		if($("#signselect").val()==-1){
			$.ajax({
				type:'post',
				url:'/user/clearDefaultSign.html',
				data:{state:$("#signselect").val()},
				dataType: 'html',
				async: false,
				success:function(data){
		    		if(data!=null){
		    				
				    }
				}
			});
			$("#editsign").hide();
		}
	});
});

$(function(){
	$("#btn_blue").click(function(){
		var eid = $("#eid").val();
		var signName = $("#eSignName").val();
		var econtent = $("#econtent").val();
		var state = 1;
		$("#eid2").val(eid);
		$("#signName").val(signName);
		$("#content").val(econtent);
		$("#state").val(state);
		$('#sign').show();
	});
});

$(function(){
	$("#btn_gray").click(function(){
		var eid = $("#eid").val();		
		var url = '/user/delSign.html?id='+eid;		
		$('#modifySign').attr("action",url);		
		$('#modifySign').submit();
	});
});
 
$(function(){
	var signid = $("#signid").val();	
	if(parseInt(signid,10)>0){
		$("#signselect option[value= "+signid+"]").attr("selected", "selected");
		$("#econtent").val($("#signContent").val());
		$("#eid").val(signid);
		$("#eSignName").val($("#sName").val());		
		$("#editsign").show();	
	}
	if(parseInt(signid,10)<=0){
		$("#signselect option[value= -1]").attr("selected", "selected");
	}
	if($("#signselect").val()==-1){
		$("#editsign").hide();
	}
});


function showSignName(){
	var temp = "签名";
	var str = "";
	var arr = new Array();
	
	$(function(){			
		$("#signselect[@name=signselect] option").each(function(i){
			var str2 = $(this).text();
			arr[i] = str2;
			
		});
		//alert(arr.join(","));	//用逗号分隔			
		for(var i=1;i<100;i++){
			str = temp + i;			
			//alert(arr.toString().indexOf(str));
			if(arr.toString().indexOf(str) <= -1) {				
				$("#signName").val(str);
				break;
			}
		}
	});
	$("#sign").show();
}










