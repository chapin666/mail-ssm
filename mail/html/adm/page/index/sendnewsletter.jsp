<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/newsletter.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/edit/css/default/default.css" />
<link href="/adm/css/tree2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/edit/js/plugins/code/prettify.css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/tree2.js"></script>
<script charset="utf-8" src="/edit/js/kindeditor.js"></script>
<script charset="utf-8" src="/edit/js/lang/zh_CN.js"></script>
<script charset="utf-8" src="/edit/js/plugins/code/prettify.js"></script>
<script>
KindEditor.ready(function(K) {
	var editor1 = K.create('textarea[name="notice.content"]', {
		cssPath : '/edit/js/plugins/code/prettify.css',
		uploadJson : "/edit/page/upload.jsp?url=/${sessionScope.member.id}/",
		fileManagerJson : "/edit/page/file_manager.jsp?url=/${sessionScope.member.id}/",
		allowFileManager : true,
		afterCreate : function() {
			var self = this;
			document.getElementById("sendmail").onclick=function(){
				self.sync();
				sendmail();
			};
			document.getElementById("savecg").onclick=function(){
				self.sync();
				savecg();
			};
		}
	});
	prettyPrint();
});
$(function(){
	$("#addcs").click(function(){
		$('#chaosong').show();
		$(this).hide();
		$("#delcs").show();
	})
	$("#addms").click(function(){
		$('#misong').show();
		$(this).hide();
		$("#delms").show();
	})
	$("#delcs").click(function(){
		$('#chaosong').hide();
		$(this).hide();
		$('#copymail').val('');
		$("#addcs").show();
	})
	$("#delms").click(function(){
		$('#misong').hide();
		$(this).hide();
		$('#bccmail').val('');
		$("#addms").show();
	})
	$('.lxr').click(function(){
		if($('#recivemail').val().indexOf($(this).attr('id').replace('lxr_',''))<0){
			$('#recivemail').val($('#recivemail').val()+$(this).html()+"<"+$(this).attr('id').replace('lxr_','')+'>;');
		}
		$('#recivemail').focus();
	});
});

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
		url:'/group/getUserByGroups.mail',
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
						var email = $('#recivemail').val();
						if(email.indexOf('<'+json.root[i].username+'@'+$('#domainhost').val()+'>')<0){
							str+=json.root[i].name+'<'+json.root[i].username+'@'+$('#domainhost').val()+'>;';
						}
						$('#recivemail').focus();
					}
				});
				$('#recivemail').val($('#recivemail').val()+str);
				$('#recivemail').focus();
			}					                	     
		}
	});
}

function getUnitMember(src){
	var str = '';
	if($(src).attr('checked')){
		$(src).parent().parent().next(".clip").find("input").attr("checked", true);
		$(src).parent().parent().next(".clip").find("input").attr("disabled", true);
		$(src).parent().parent().next().children().find(".lxr").each(function(i){
			if($('#recivemail').val().indexOf("<"+$(this).attr('id').replace('lxr_','')+">")<0){
				str+=$(this).html()+"<"+$(this).attr('id').replace('lxr_','')+'>;'
			}
		});
		$('#recivemail').val($('#recivemail').val()+str);
	}else{
		$(src).parent().parent().next(".clip").find("input").attr("checked", false);
		$(src).parent().parent().next(".clip").find("input").attr("disabled", false);
		$(src).parent().parent().next().children().find(".lxr").each(function(i){
			$('#recivemail').val($('#recivemail').val().replace($(this).html()+"<"+$(this).attr('id').replace('lxr_','')+">;",''));
		});
	}
	$('#recivemail').focus();
}

function savecg(){
	if($('#recivemail').val().length<1&&$('#mailtitle').val().length<1&&$('#content').val().length<1){
		alert('未发现改动，无需保存');
		return false;
	}else{
		$('#state').val(2);
		$('#sendmailform').submit();
	}
}

function sendmail(){
	if($('#recivemail').val().length<1){
		alert('请选择收件人');
		return false;	
	}else if($('#mailtitle').val().length<1){
		alert('请填写邮件主题');
		return false;
	}else if($('#content').val().length<1){
		alert('请填写邮件正文');
		return false;
	}else{
		$('#state').val(1);
		$('#sendmailform').submit();
	}
}

function delc(num){
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
	}
}
</script>
</head>
<body>
	<input type="hidden" id="domainhost" value="${sessionScope.host}">
	<div class="main">
		<div class="content" style="min-height: 620px;">
			<div class="pathsize">
				<ul>
					<li class="home">
						<a href="#">单位公告</a>
					</li>
				</ul>
			</div>
			<div class="notice_newstyle">
				<div class="new_title_box">
					<h1 class="new_title">发布新公告</h1>
					<div class="newbtnright">
						<a class="btnNormal" onclick="history.back();" style="padding:4px 10px;">
							<span class="ico_home"></span> 返回
						</a>
					</div>
				</div>
				<form action="/emails/SendAdmMail.mail" method="post" id="sendmailform">
				<div class="contents" style="min-height:434px;">
					<ul class="theme">
						<li>
							<input type="hidden" name="notice.id" id="noticeid" value="${notice.id}">
							<input type="hidden" name="notice.state" id="state">
							<input type="hidden" name="notice.author" value="${sessionScope.member.username}" id="author">
							<label class="title"><font color="#7c7c7c">公告范围</font></label>
							<label class="input"><input type="text" name="notice.tomail" id="recivemail" class="input1" readonly="readonly" value="${notice.tomail}" onkeydown="if(event.keyCode==8||event.which==8||event.charCode==8){delc(1);}" onkeyup="if(event.keyCode==8||event.which==8||event.charCode==8){delc(4);}"/></label>
						</li>
						<li>
							<label class="title">公告主题	</label>
							<label class="input"><input type="text" name="notice.title" id="mailtitle" value="${notice.title}" class="input1" /></label>
						</li>
						<!-- <
						li style="display: none;">
							<label class="title">&nbsp;</label>
							<label class="input">
								<c:if test="${empty email.file}">
									<a id="addfj" onclick="$('#attach').click();"><img src="/mail/images/gray/ico_fujian.jpg"/> 添加附件</a>
									<a style="display: none;" id="delfj" onclick="$('#delfj').html('删除附件');$('#delfj').hide();$('#addfj').show();">删除附件</a>
								</c:if>
								<c:if test="${not empty email.file}">
									<a style="display: none;" id="addfj" onclick="$('#attach').click();"><img src="/mail/images/gray/ico_fujian.jpg"/> 添加附件</a>
									<a id="delfj" onclick="$('#file').val('');$('#filename').val('');$('#delfj').html('删除附件');$('#delfj').hide();$('#addfj').show();">${email.filename} 删除附件</a>
								</c:if>
								<input type="file" name="file" onchange="checkfile(this,2048);" id="attach" style="display: none;">
								<input type="hidden" name="email.file" value="${email.file}" id="file" >
								<input type="hidden" name="email.filename" value="${email.filename}" id="filename" >
							</label>
						</li>
						-->
						<li>
							<label class="title">公告正文	</label>
							<label class="input" style="padding:2px 0;">
								<textarea id="content" name="notice.content">${notice.content}</textarea>
							</label>
						</li>
					</ul>
					<ul class="lxrList">
						<li class="title"><b>单位地址本</b></li>
						<li>
							<ul id="lxrinfo" style="height:368px;">
								<li class="list">
									<ul>
										<li style="height:auto;">
											<script type="text/javascript">
												d = new dTree('d');
												d.add('0','-1','','','','');
												var i = -2;
											</script>
										 	<s:iterator value="#request.unitlist" id="unl">
									 			<script type="text/javascript">
										 			var lenn = '${fn:length(unl.name)}';
													var lenname = '${unl.name}';
													if(lenn>7){
														lenname = '${fn:substring(unl.name, 0, 7)}...';
													}
										 			d.add('${unl.id}','${unl.pid}','<input type="checkbox" class="unitdel" value="${unl.id}" onclick="getUnitMember(this);"> '+lenname+'','#','','','','','');
												</script>
												<s:iterator value="#request.userlist" id="userl">
													<c:if test="${userl.unid eq unl.id}">
														<script type="text/javascript">
															var lenn1 = '${fn:length(userl.name)}';
															var lenname1 = '${userl.name}';
															i--;
															if(lenn1>7){
																lenname1 = '${fn:substring(userl.name, 0, 7)}...';
															}
															d.add(i,'${unl.id}','<a class="lxr" lang="unit_${unl.id}" id="lxr_${userl.username}@${userl.domainname}">'+lenname1+'</a>','','','','','','');
														</script>
													</c:if>
												</s:iterator>
											</s:iterator>
											<script type="text/javascript">
												document.write(d);
												$('.dTreeNode:eq(0)').hide();
											</script>
										</li>
										<c:if test="${not empty grouplist}">
											<li style="height:auto;" class="mailgroup">
												<img id="mailgroupimg" onclick="showgm();" alt="" src="/adm/images/tree/nolines_plus.gif" />
												<a onclick="showgm();" >邮件群组</a>
											</li>
											<s:if test="#request.grouplist.size eq 0">
												<li style="height:auto;" class="mailgroupsmall">
													<a style="display:none;" class="groupmail">(暂无邮件群组)</a>
												</li>
											</s:if>
											<s:else>
											<s:iterator value="#request.grouplist" id="grl">
												<li style="height:auto;" class="mailgroupsmall">
													<a style="display:none;" class="groupmail" id="g_${grl.id}" onclick="showsubuser('${grl.id}','${grl.name}');"><c:if test="${fn:length(grl.name)>7}">${fn:substring(grl.name, 0, 7)}...</c:if><c:if test="${fn:length(grl.name)<=7}">${grl.name}</c:if></a>
												</li>
											</s:iterator>
											</s:else>
										</c:if>
									</ul>
								</li>
							</ul>
							<ul id="lxrinfo1" style="display: none;"></ul>
						</li>
					</ul>
				</div>
				</form>
				<div class="submitColumn">
					<input class="btnSubmit" type="button" value="发送" name="sendbtn" id="sendmail"/>
					<input class="btnNormalt" type="button" value="存草稿" name="savebtn" id="savecg"/>
					<input class="btnNormalt" name="" type="button" value="取消" onclick="history.back();"/>
				</div>
			</div>
		</div>
		<div class="footer">
			<p>© 2013 Danwei Mail. All Rights Reserved</p>
		</div>
	</div>
</body>
</html>