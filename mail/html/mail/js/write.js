var height = $(window).height();
var width = $(window).width();
var right_height = 0;
var mail_height = 0;
var textarea_height = 0;
var theme_width = 0;
var input_width = 0;
var focus_type = 0;
var lebel_left = 0;
var filelistsize = 0;

$(function(){
	$("#addcs").click(function(){
		$('#chaosong').show();
		$(this).hide();
		$("#delcs").show();
		var textareaheight = $(".ke-container-default").height();
		$(".theme li label.input textarea").css("height", textareaheight - 26);
		$(".ke-container-default").css("height", textareaheight - 26);
	})	
	
	$("#addms").click(function(){
		$('#misong').show();
		$(this).hide();
		$("#delms").show();
		var textareaheight = $(".ke-container-default").height();
		$(".theme li label.input textarea").css("height", textareaheight - 26);
		$(".ke-container-default").css("height", textareaheight - 26);
	})
	$("#delcs").click(function(){
		$('#chaosong').hide();
		$(this).hide();
		$('#copymail').val('');
		$("#addcs").show();
		var textareaheight = $(".ke-container-default").height();
		$(".theme li label.input textarea").css("height", textareaheight + 26);
		$(".ke-container-default").css("height", textareaheight + 26);
	})
	$("#delms").click(function(){
		$('#misong').hide();
		$(this).hide();
		$('#bccmail').val('');
		$("#addms").show();
		var textareaheight = $(".ke-container-default").height();
		$(".theme li label.input textarea").css("height", textareaheight + 26);
		$(".ke-container-default").css("height", textareaheight + 26);
	})
	$("#recivemail").focus(function(){
		focus_type = 0;
	});
	$("#copymail").focus(function(){
		focus_type = 1;
	});
	$("#bccmail").focus(function(){
		focus_type = 2;
	});
	$('#mailkey').keydown(function(event){
		// 响应回车
		if(event.keyCode ==13)
		{
			seachUser();
		}
	});
	// 发件人
	$('#recivemail').keydown(function(event){
		// 回车
		if(event.keyCode ==13)
		{
			autoadd($('#recivemail'));
		}else if(event.keyCode ==188){}//逗号
	});
	// 抄送
	$('#copymail').keydown(function(event){
		// 回车
		if(event.keyCode ==13)
		{
			autoadd($('#copymail'));
		}else if(event.keyCode ==188){}//逗号
	});
	// 密送
	$('#bccmail').keydown(function(event){
		// 回车
		if(event.keyCode ==13)
		{
			autoadd($('#bccmail'));
		}else if(event.keyCode ==188){}//逗号
	});

	
	$('.contactclick').click(function(){
		if(focus_type==0){
			if($('#recivemail').val().indexOf($(this).attr('id').replace('lxr_',''))<0){
				$('#recivemail').val($('#recivemail').val()+$(this).attr('value')+"<"+$(this).attr('id').replace('lxr_','')+'>;');
			}
			$('#recivemail').focus();
		}else if(focus_type==1){
			if($('#copymail').val().indexOf($(this).attr('id').replace('lxr_',''))<0){
				$('#copymail').val($('#copymail').val()+$(this).attr('value')+"<"+$(this).attr('id').replace('lxr_','')+'>;');
			}
			$('#copymail').focus();
		}else if(focus_type==2){
			if($('#bccmail').val().indexOf($(this).attr('id').replace('lxr_',''))<0){
				$('#bccmail').val($('#bccmail').val()+$(this).attr('value')+"<"+$(this).attr('id').replace('lxr_','')+'>;');
			}
			$('#bccmail').focus();
		}
	});
	
	//设置主页面的高度
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	lebel_left = (width - 440)/2;
	$(".createlabel").css("left", lebel_left);

	filelistsize = $("#filelistsize").val();
	mail_height = right_height - 48*2;
	if(mail_height < 460){
		mail_height = 460;
	}
	textarea_height = mail_height - 160;
	mail_height = mail_height + Number(filelistsize)*22
	$(".mail").css("height", mail_height);
	$("#content").css("height", textarea_height);

	//设置主页面的宽度
	if($(".lxrList").css("display") == "none"){
		theme_width = width - 260 + 180;
	}else{
		theme_width = width - 260;
	}
	$(".theme").css("width", theme_width);
	input_width = theme_width - 60;
	$(".theme li label.input input.input1").css("width", input_width);
	$(".theme li label.input textarea").css("width", input_width + 4);
	$(".ke-container-default").css("width", input_width + 4);
	
	$(".titlecheck").easydrag();//拖动事件
});

$(window).resize(function(){
	//设置右边栏的高度
	height = $(window).height();
	width = $(window).width();
	
	right_height = height - 24;
	$(".content").css("height", right_height);
	
	lebel_left = (width - 440)/2;
	$(".createlabel").css("left", lebel_left);
	
	filelistsize = $("#filelistsize").val();
	mail_height = right_height - 48*2;
	if(mail_height < 460){
		mail_height = 460;
	}
	textarea_height = mail_height - 160;
	mail_height = mail_height + Number(filelistsize)*22
	$(".mail").css("height", mail_height);
	$("#content").css("height", textarea_height);
	
	//设置主页面的宽度
	if($(".lxrList").css("display") == "none"){
		theme_width = width - 260 + 180;
	}else{
		theme_width = width - 260;
	}
	$(".theme").css("width", theme_width);
	input_width = theme_width - 60;
	$(".theme li label.input input.input1").css("width", input_width);
	$(".theme li label.input textarea").css("width", input_width + 4);
	$(".ke-container-default").css("width", input_width + 4);
	
});

function autoadd(widget) {
	var tomail=widget.val();
	var index1=tomail.lastIndexOf('@');
	var index2=tomail.lastIndexOf(';');

	if(tomail.length != index2+1){
		if(index1<=index2){
			tomail=tomail+"@"+$('#domainhost').val();
		}
		widget.val(tomail+";");
	}
}

// 通讯录里面搜索按钮是否点击的判断变量
var clickA=0;

function seachA()
{
	if(clickA ==0)
	{
		seachUser();
	}
	clickA=0;
}

function seachB()
{
	clickA=1;
}


function seachUser() {
	if($('#mailkey').val().length>0){
		$.ajax({
			url:'/user/getUserByKey.html',
			cache :false,
			data : {key:$('#mailkey').val()},
			type :'Post',
			dataType :'json',
			timeout :20000,
			error : function() {
				alert("出错啦");
			},
			success : function(json) {
				var len = 0;
				if(json!=null){
					len = json.totalProperty;
				}
				if (len != 0) {
					var str = "<li style='align:right;color:#006699' onclick='showmailinfo();'>清除搜索结果</li>";
					$.each(json.root, function(i) {		
						str+='<a onclick="settomail(this);" class="lm_saddr" value="'+json.root[i].name+'&lt;'+json.root[i].username+'&gt;'+'">';
						str+='<div class="lm_name"><b>'+json.root[i].name+'</b></div>';
						str+='<div class="lm_email">'+json.root[i].username+'</div></a>';
					});
					$('#lxrinfo1').html(str);
					$('#lxrinfo').hide();
					$('#lxrinfo1').show();
				}else{
					$('#lxrinfo1').html('<li style="align:right;color:#006699" onclick="showmailinfo();">清除搜索结果</li><li>没有查找到你要找的联系人！</li>');
					$('#lxrinfo').hide();
					$('#lxrinfo1').show();
				}				                	     
			}
		});
	}
}

function closeLxrList()
{
	$(".lxrList").hide();
	$(".ico").html('<input type="button" title="展开通讯录" class="on" value="" onclick="showLxrList()" />');
	$(window).resize();
}

function showLxrList()
{
	$(".lxrList").show();
	$(".ico").html('<input type="button" title="隐藏通讯录" value="" onclick="closeLxrList()" />');
	$(window).resize();
}

function login(){
	if($("#username").val().length<1){
		$("#error").html("<font color='red'>请输入邮箱用户名</font>");
		$("#username")[0].focus();
	}else if($("#password").val().length<1){
		$("#error").html("<font color='red'>请输入邮箱密码</font>");
		$("#password")[0].focus();
	}else{
		$.ajax({
			type:'post',
			url:'/user/checkUsernameExist.html',
			data:{username:$("#username").val()},
			dataType: 'html',
			async: false,
			success:function(data){
			    if(data==1){
			    	$.ajax({
						type:'post',
						url:'/user/checkPass.html',
						data:{username:$("#username").val(),password:$("#password").val()},
						dataType: 'html',
						async: false,
						success:function(data){
				    		if(data==2){
						    	$("#error").html("用户已冻结");
				    		}else if(data==1){
						    	location.href='/index.html';
						    }else{
						    	$("#error").html("密码错误");
								$("#password")[0].focus();
						    }
						}
					});
			    }else{
			    	$("#error").html("用户名不存在");
					$("#username")[0].focus();
			    }
			}
		});
		return false;
	}
}

function savecg(){
	if($('#recivemail').val().length<1&&$('#copymail').val().length<1&&$('#bccmail').val().length<1&&$('#mailtitle').val().length<1&&$('#attach').val().length<1&&$('#content').val().length<1){
		$("#msgsavecgNull").show();
		setTimeout("($(\"#msgsavecgNull\").hide())",4000);
		return false;
	}else{
		$('#state').val('0');
		$('#type').val('0');
		$('#sendmailform').attr("action", "/email/SendMail.html");
		if (document.all) {
			if($('#attach').val().length>0){
				$('#file').val($('#attach').val());
				var ff = $('#attach').val().split("\\");
				$('#filename').val(ff[ff.length-1]);
				$('#attach').remove();
				$('#sendmailform').submit();
			}else{
				$('#attach').remove();
				$('#sendmailform').submit();
			}
		}else{
			$('#sendmailform').submit();
		}
		
		$("#msgsavecg").show();
		setTimeout("($(\"#msgsavecg\").hide())",4000);
	}
}

function checkfile(file,filesize){
	if(file.value.length>0){
		var size = 0 ;
		if (document.all) {
            file.select();
            window.parent.document.body.focus();
			src = document.selection.createRange().text;
			$('#file').val(src);
			//alert(src);
			$.ajax({
				type:'post',
				url:"/UploadImgs.html",
				data:{src:src},
				dataType: 'html',
				async: false,
				success:function(data){
					size = data ;
				}
			});
        }else{
            size = Math.round(file.files[0].size/1024);
            if(size==0||size==undefined){
            	size = Math.round(file.files[0].fileSize/1024);
           	}
        }
        if(isNaN(size)||size<filesize){
			$('#addfj').hide();
			$('#delfj').html($(file).val()+" <a>删除附件</a>");
			$('#delfj').show();
		}else{
			alert('上传文件不能大于'+filesize+'KB');
			return false;
	    }
	}
}

function sendmail(tag){
	var flag = false ;
	if($('#recivemail').val().length<1&&$('#copymail').val().length<1&&$('#bccmail').val().length<1){
		//alert('请选择收件人');
		$("#msgBoxDIV").show();
		setTimeout("($(\"#msgBoxDIV\").hide())",4000);
		return false;	
	}else{
		if($('#mailtitle').val().length<1||$('#content').val().length<1){
			$(".titlecheck").show();			
			if(tag){
				flag = true;
			}else{
				return false;
			}
		}else{
			flag = true;
		}
		var tag = true ;
		var rrms ;
		var cpms ;
		var bcms ;
		if($('#recivemail').val().length>0){
			if($('#recivemail').val().substring($('#recivemail').val().length-1,$('#recivemail').val().length)==';'){
				var rrms = $('#recivemail').val().substring(0,$('#recivemail').val().length-1).split(";");
			}else{
				var rrms = $('#recivemail').val().split(";");
			}
		}
		if($('#copymail').val().length>0){
			if($('#copymail').val().substring($('#copymail').val().length-1,$('#copymail').val().length)==';'){
				var cpms = $('#copymail').val().substring(0,$('#copymail').val().length-1).split(";");
			}else{
				var cpms = $('#copymail').val().split(";");
			}
		}
		if($('#bccmail').val().length>0){
			if($('#bccmail').val().substring($('#bccmail').val().length-1,$('#bccmail').val().length)==';'){
				var bcms = $('#bccmail').val().substring(0,$('#bccmail').val().length-1).split(";");
			}else{
				var bcms = $('#bccmail').val().split(";");
			}
		}

		if(rrms!=null&&rrms!=''&&rrms!=undefined){
			for(var i=0;i<rrms.length;i++){
				if(rrms[i].indexOf('@')<0||rrms[i].indexOf('.')<0){
					alert('收件人格式填写不正确');
					tag = false ;
					return false;
				}
			}
		}
		if(cpms!=null&&cpms!=''&&cpms!=undefined){
			for(var i=0;i<cpms.length;i++){
				if(cpms[i].indexOf('@')<0||cpms[i].indexOf('.')<0){
					alert('抄送方格式填写不正确');
					tag = false ;
					return false;
				}
			}
		}
		if(bcms!=null&&bcms!=''&&bcms!=undefined){
			for(var i=0;i<bcms.length;i++){
				if(bcms[i].indexOf('@')<0||bcms[i].indexOf('.')<0){
					alert('密送方格式填写不正确');
					tag = false ;
					return false;
				}
			}
		}
		if(tag&&flag){
			$('#type').val(2);
			$('#state').val(1);
			if (document.all) {
				if($('#attach').val().length>0){
					$('#file').val($('#attach').val());
					var ff = $('#attach').val().split("\\");
					$('#filename').val(ff[ff.length-1]);
					$('#attach').remove();
					$('#sendmailform').submit();
				}else{
					$('#attach').remove();
					$('#sendmailform').submit();
				}
			}else{
				$('#sendmailform').submit();
			}
			
			$("#msgSending").show();
		}
	}
}

function delc(num){
	/*var contacts = '';
	var oTxt1 ;
	if(num==1){
		contacts = $('#recivemail').val();
		oTxt1 = document.getElementById("recivemail");
	}else if(num==2){
		contacts = $('#copymail').val();
		oTxt1 = document.getElementById("copymail");
	}else if(num==3){
		contacts = $('#bccmail').val();
		oTxt1 = document.getElementById("bccmail");
	}
	if(contacts!=''){
		var cursurPosition=-1;
		if(oTxt1.selectionStart){//非IE浏览器
			cursurPosition= oTxt1.selectionStart;
		}else if(document.selection==undefined){
		}else{//IE
			var range = document.selection.createRange();
			range.moveStart("character",-oTxt1.value.length);
			cursurPosition=range.text.length;
		}
		alert(cursurPosition);
	}
	if(num==1){
		$('#recivemail').attr('readonly',false);
	}else if(num==2){
		$('#copymail').attr('readonly',false);
	}else if(num==3){
		$('#bccmail').attr('readonly',false);
	}else if(num==4){
		$('#recivemail').attr('readonly',true);
	}else if(num==5){
		$('#copymail').attr('readonly',true);
	}else if(num==6){
		$('#bccmail').attr('readonly',true);
	}*/
}

function showlxr(id,imgid){
	$('.lxrimg').attr('src','/adm/images/tree/nolines_plus.gif');
	$('li[lang]').each(function(i){
		if($(this).attr('lang')!='lxr_'+id){
			$(this).css('display','none');
		}
	})
	$('li[lang]').each(function(i){
		if($(this).attr('lang')=='lxr_'+id){
			if($(this).css('display')=='none'){
				$(this).css('display','block');
				$('#'+imgid).attr('src','/adm/images/tree/nolines_minus.gif');
			}else{
				$(this).css('display','none');
				$('#'+imgid).attr('src','/adm/images/tree/nolines_plus.gif');
			}
		}
	})
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

function showsubuser(id,name){
	$.ajax({
		url:'/group/getUserByGroups.html',
		cache :false,
		data : {id:id},
		type :'Post',
		dataType :'json',
		timeout :20000,
		error : function() {
			alert("出错啦");
		},
		success : function(json) {
			var len = 0;
			if(json!=null){
				len = json.totalProperty;
			}
			if (len != 0) {
				var str = "";
				$.each(json.root, function(i) {
					if($('#frommail1').val()!=json.root[i].username){
						if(focus_type==0){
							var email = $('#recivemail').val();
							if(email.indexOf('<'+json.root[i].username+'@'+$('#domainhost').val()+'>')<0){
								str+=json.root[i].name+'<'+json.root[i].username+'@'+$('#domainhost').val()+'>;';
							}
							$('#recivemail').focus();
						}else if(focus_type==1){
							var email = $('#copymail').val();
							if(email.indexOf('<'+json.root[i].username+'@'+$('#domainhost').val()+'>')<0){
								str+=json.root[i].name+'<'+json.root[i].username+'@'+$('#domainhost').val()+'>;';
							}
							$('#copymail').focus();
						}else if(focus_type==2){
							var email = $('#bccmail').val();
							if(email.indexOf('<'+json.root[i].username+'@'+$('#domainhost').val()+'>')<0){
								str+=json.root[i].name+'<'+json.root[i].username+'@'+$('#domainhost').val()+'>;';
							}
							$('#bccmail').focus();
						}
					}
				});
				if(focus_type==0){
					$('#recivemail').val($('#recivemail').val()+str);
					$('#recivemail').focus();
				}else if(focus_type==1){
					$('#copymail').val($('#copymail').val()+str);
					$('#copymail').focus();
				}else if(focus_type==2){
					$('#bccmail').val($('#bccmail').val()+str);
					$('#bccmail').focus();
				}
			}					                	     
		}
	});
}

function settomail(src){
	if(focus_type==0){
		if($('#recivemail').val().indexOf($(src).attr('value'))<0){
			$('#recivemail').val($('#recivemail').val()+$(src).attr('value').replace('&lt;','<').replace('&gt;','>')+';');
		}
		$('#recivemail').focus();
	}else if(focus_type==1){
		if($('#copymail').val().indexOf($(src).attr('value'))<0){
			$('#copymail').val($('#copymail').val()+$(src).attr('value').replace('&lt;','<').replace('&gt;','>')+';');
		}
		$('#copymail').focus();
	}else if(focus_type==2){
		if($('#bccmail').val().indexOf($(src).attr('value'))<0){
			$('#bccmail').val($('#bccmail').val()+$(src).attr('value').replace('&lt;','<').replace('&gt;','>')+';');
		}
		$('#bccmail').focus();
	}
}

function showmailinfo(){
	$('#lxrinfo1').hide();
	$('#lxrinfo').show();
	$('#mailkey').val("");
}

function delfile(){
	$('#attach').remove();
	$('#filespan').html("<input type='file' name='file' onchange='checkfile(this,30720);' id='attach' style='display: block;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;'>");
	$('#filename').val('');
	$('#delfj').html('删除附件');
	$('#delfj').hide();
	$('#addfj').show();
}