<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="java.util.Date,java.text.SimpleDateFormat,util.CreateUUID"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	String daydate = df.format(new Date()); // new Date()为获取当前系统时间
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mail/css/gray/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/edit/css/default/default.css" />
<link rel="stylesheet" href="/edit/js/plugins/code/prettify.css" />
<link href="/mail/css/tree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/compatible.js"></script>
<script type="text/javascript" src="/mail/js/tree2.js"></script>
<script type="text/javascript" src="/mail/js/write.js"></script>
<script type="text/javascript" src="/mail/js/ajaxupload.js"></script>
<script type="text/javascript" src="/mail/js/fujian.js"></script>
<script charset="utf-8" src="/edit/js/kindeditor.js"></script>
<script charset="utf-8" src="/edit/js/lang/zh_CN.js"></script>
<script charset="utf-8" src="/edit/js/plugins/code/prettify.js"></script>


<script language="javascript" src="/mail/js/request.js"></script>



<script>
KindEditor.ready(function(K) {
	var editor1 = K.create('textarea[name="email.content"]', {
		cssPath : '/edit/js/plugins/code/prettify.css',
		uploadJson : "/edit/page/upload.jsp?url=/${sessionScope.user.id}/",
		fileManagerJson : "/edit/page/file_manager.jsp?url=/${sessionScope.user.id}/",
		allowFileManager : true,
		afterCreate : function() {
			var self = this;
			document.getElementById("sendmail").onclick=function(){
				self.sync();
				sendmail();
			};
			document.getElementById("sendmail1").onclick=function(){
				self.sync();
				sendmail();
			};
			document.getElementById("dingshiBtn").onclick=function(){
				self.sync();
				dingshiClick();
			};
			document.getElementById("dingshiBtn1").onclick=function(){
				self.sync();
				dingshiClick();
			};
			document.getElementById("savecg").onclick=function(){
				self.sync();
				savecg();
			};
			document.getElementById("savecg1").onclick=function(){
				self.sync();
				savecg();
			};
		}
	});
	prettyPrint();
});
$(function(){
	if($('#emailid').val().length>0&&'${email.state}'==3){
		alert('已保存到草稿箱');
	}
});
function dsSendMailFun()
{
	var d = new Date();
	var year = Number(d.getFullYear());
	var month = Number(d.getMonth() + 1);
	var day = Number(d.getDate());
	var hours = Number(d.getHours());
	var minutes = Number(d.getMinutes());
	
	var mailyear = Number($("#maildate").val().split("-")[0]);
	var mailmonty = Number($("#maildate").val().split("-")[1]);
	var mailday = Number($("#maildate").val().split("-")[2]);
	var mailhour = Number($("#mailhour").val());
	var mailminute = Number($("#mailminute").val());
	
	if(mailyear < year || (mailyear == year && mailmonty < month) || 
		(mailyear == year && mailmonty == month && mailday < day) || 
		(mailyear == year && mailmonty == month && mailday == day && mailhour < hours) || 
		(mailyear == year && mailmonty == month && mailday == day && mailhour == hours && mailminute <= minutes)){
		alert("您设置的定时时间已经过期");
		return false;
	}else if(mailyear == year && mailmonty == month && mailday == day && mailhour == hours && (mailminute - minutes) <= 5){
		alert("您设置的定时时间距离现在的时间太近了");
		return false;
	}else{
		$("#mailtimes").val("0 " + mailminute + " " + mailhour + " " + mailday + " " + mailmonty + " ? " + mailyear);
		$("#dstime").val(mailyear+"-"+mailmonty+"-"+mailday+" "+mailhour+":"+mailminute+":00");
		$('#state').val(3);
		$('#type').val(6);
		$('#sendmailform').attr('action','/email/dsSendMail.html');
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
	}
}
function dingshiClick()
{
	var recivemail = $("#recivemail").val();
	var copymail = $("#copymail").val();
	var bccmail = $("#bccmail").val();
	var mailtitle = $("#mailtitle").val();
	var content = $("#content").val();
	if(recivemail == "" && copymail == "" && bccmail == ""){
		$("#msgBoxDIV").show();
		setTimeout("($(\"#msgBoxDIV\").hide())",4000);
		return false;
	}else{
		var flag1 = false ;
		if($('#mailtitle').val().length<1||$('#content').val().length<1){
			$(".titlecheck").show();			
			if(tag){
				flag = true;
			}else{
				return false;
			}
		}else{
			flag1 = true;
		}
		if(flag1){
			$('.createlabel').show();
		}
	}
}

</script>
<title>单位邮箱用户登录</title>
</head>
<body>
<input type="hidden" id="domainhost" value="${sessionScope.host}">
<div class="msgBoxDIV" id="msgBoxDIV">
	<span class="errmsg">请选择收件人</span>
</div>



<!--没有标题弹出的选择对话框-->
	<div class="titlecheck">
		<div class="createlabel_head" style="cursor:move;">		
			<span>发送邮件</span>
		</div>
		<div class="createlabel_body">
			<div style="margin:8px 0 0;font-weight:bold;">	
			<dir class="icon_info_b" style="margin: -7px 12px 8px 0px;"></dir>				
				没有填写邮件主题或正文，确认发送？<br><br>
			</div>
		</div>		
		<div class="createlabel_bottom"> 
			<input type="button" class="btn_blue" value="确定" onclick="sendmail(true);" />
			<input type="button" class="btn_gray" value="取消" onclick="$('.titlecheck').hide()" />
		</div>
	</div>


<div class="main">
	<div class="content">
		<div class="createlabel">
			<div class="createlabel_head" style="cursor:move;">
				<span>定时发送</span>
				<a class="createlabel_close" href="javascript:;" onclick="$('.createlabel').hide()" title="关闭"></a>
			</div>
			<form action="/email/dsSendMail.html" method="post" id="dsSendMailForm">
			<div class="createlabel_body" style="height:56px;">
				<p>选择定时发送的时间</p>
				<div style="margin:8px 0 0;">
					<input type="text" class="createlabel_input" name="maildate" value="<%=daydate %>" id="maildate" style="float:left;width:150px;" readonly="readonly" onfocus="c.showMoreDay=true;c.show(this);" />
					<label>日期</label>
					<select name="mailhour" id="mailhour" class="createlabel_select">
						<%
						String h = "";
						for(int i=0; i<24; i++){
							if(i<10){
								h = "0" + i;
							}else{
								h = "" + i;
							}
						%>
						<option value="<%=i %>"><%=h %></option>
						<%} %>
					</select>
					<label>时</label>
					<select name="mailminute" id="mailminute" class="createlabel_select">
					<%
						String m = "";
						for(int j=0; j<60; j+=5){
							if(j<10){
								m = "0" + j;
							}else{
								m = "" + j;
							}
						%>
						<option value="<%=j %>"><%=m %></option>
						<%} %>
					</select>
					<label>分</label>
				</div>
			</div>
			</form>
			<div class="createlabel_bottom">
				<input type="button" class="btn_blue" value="确定" onclick="dsSendMailFun()"/>
				<input type="button" class="btn_gray" value="取消" onclick="$('.createlabel').hide()"/>
			</div>
		</div>
		<div class="button">
			<span><input type="button" value="" name="sendBtn" class="sendBtn" id="sendmail"/></span>
			<span><input type="button" value="" name="dingshiBtn" class="dingshiBtn" id="dingshiBtn"/></span>
			<span><input type="button" value="" name="cuncgBtn" class="cuncgBtn" id="savecg" /></span>
			<span><input type="button" value="" name="closeBtn" class="closeBtn" onclick="location.href='/main.html'" /></span>
		</div>
		<div class="mail mailBG">
			<form action="/email/ReSendMail.html" method="post" id="sendmailform" enctype="multipart/form-data">
			<input type="hidden" name="email.id" id="emailid" value="${email.id}">
			<input type="hidden" name="email.type" id="type">
			<input type="hidden" name="email.ismain" id="ismain" value="2">
			<input type="hidden" name="email.state" id="state">
			<input type="hidden" name="mailtimes" value="" id="mailtimes" />
			<input type="hidden" name="dstime" value="" id="dstime" />
			<ul class="theme">
				<li>
					<label class="title"><font color="#7c7c7c">发件人</font></label>
					<label class="input">${sessionScope.user.name}<font color="#2a586f">&lt;${sessionScope.user.username}@${sessionScope.host}&gt;</font></label>
					<input type="hidden" name="email.fromname" value="${sessionScope.user.name}" id="fromname">
					<input type="hidden" name="email.frommail" value="<${sessionScope.user.username}@${sessionScope.host}>" id="frommail">
					<input type="hidden" value="${sessionScope.user.username}" id="frommail1">
				</li>
				<li>
					<label class="title"><font color="#7c7c7c">收件人</font></label>
					<c:if test="${not empty showstr}">
						<label class="input"><input type="text" name="email.toname" id="recivemail" class="input1" value="${showstr}" onkeydown="if(event.keyCode==8||event.which==8||event.charCode==8){delc(1);}" onkeyup="if(event.keyCode==8||event.which==8||event.charCode==8){delc(4);}"/></label>
					</c:if>
					<c:if test="${empty showstr}">
						<label class="input"><input type="text" name="email.toname" id="recivemail" class="input1" value="${email.toname}" onkeydown="if(event.keyCode==8||event.which==8||event.charCode==8){delc(1);}" onkeyup="if(event.keyCode==8||event.which==8||event.charCode==8){delc(4);}"/></label>
					</c:if>
					<input type="hidden" name="eaddr" value="${eaddr}" id="" >
				</li>
				<c:if test="${empty email.copyto}">
					<li style="display:none;" id="chaosong">
						<label class="title"><font color="#2a586f">抄&nbsp;&nbsp;送</font></label>
						<label class="input"><input type="text" name="email.copyto" id="copymail" value="${email.copyto}" class="input1" onkeydown="if(event.keyCode==8||event.which==8||event.charCode==8){delc(2);}" onkeyup="if(event.keyCode==8||event.which==8||event.charCode==8){delc(5);}"/></label>
					</li>
				</c:if>
				<c:if test="${not empty email.copyto}">
					<li id="chaosong">
						<label class="title"><font color="#2a586f">抄&nbsp;&nbsp;送</font></label>
						<label class="input"><input type="text" name="email.copyto" id="copymail" value="${email.copyto}" class="input1" onkeydown="if(event.keyCode==8||event.which==8||event.charCode==8){delc(2);}" onkeyup="if(event.keyCode==8||event.which==8||event.charCode==8){delc(5);}"/></label>
					</li>
				</c:if>
				<c:if test="${not empty email.bcc}">
					<li id="misong">
						<label class="title"><font color="#2a586f">密&nbsp;&nbsp;送</font></label>
						<label class="input"><input type="text" name="email.bcc" id="bccmail" value="${email.bcc}" class="input1" onkeydown="if(event.keyCode==8||event.which==8||event.charCode==8){delc(3);}" onkeyup="if(event.keyCode==8||event.which==8||event.charCode==8){delc(6);}"/></label>
					</li>
				</c:if>
				<c:if test="${empty email.bcc}">
					<li style="display:none;" id="misong">
						<label class="title"><font color="#2a586f">密&nbsp;&nbsp;送</font></label>
						<label class="input"><input type="text" name="email.bcc" id="bccmail" value="${email.bcc}" class="input1" onkeydown="if(event.keyCode==8||event.which==8||event.charCode==8){delc(3);}" onkeyup="if(event.keyCode==8||event.which==8||event.charCode==8){delc(6);}"/></label>
					</li>
				</c:if>
				<li>
					<label class="title">&nbsp;</label>
					<label class="input">
						<c:if test="${empty email.copyto}">
							<a id="addcs">添加抄送</a><a style="display: none;" id="delcs">删除抄送</a>
						</c:if>
						<c:if test="${not empty email.copyto}">
							<a style="display: none;" id="addcs">添加抄送</a><a id="delcs">删除抄送</a>
						</c:if>
						-
						<c:if test="${empty email.bcc}">
							<a id="addms">添加密送</a><a id="delms" style="display: none;">删除密送</a>
						</c:if>
						<c:if test="${not empty email.bcc}">
							<a id="addms" style="display: none;">添加密送</a><a id="delms">删除密送</a>
						</c:if>
					</label>
				</li>
				<li>
					<label class="title">主&nbsp;&nbsp;题</label>
					<label class="input"><input type="text" name="email.title" value="${email.title}" id="mailtitle" class="input1" /></label>
				</li>
				<!--  
				<li>
					<label class="title">&nbsp;</label>
					<label class="input">
						<c:if test="${empty email.file}">
							<a id="addfj" onclick="$('#attach').click();"><img src="/mail/images/gray/ico_fujian.jpg"/> 添加附件</a>
							<a style="display: none;" id="delfj" onclick="delfile();">删除附件</a>
						</c:if>
						<c:if test="${not empty email.file}">
							<a style="display: none;" id="addfj" onclick="$('#attach').click();"><img src="/mail/images/gray/ico_fujian.jpg"/> 添加附件</a>
							<a id="delfj" onclick="delfile();">${email.filename} 删除附件</a>
						</c:if>
						<input type="file" name="file" onchange="checkfile(this,30720);" id="attach" style="display: block;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;width:1px;" size="1">
						<input type="hidden" name="email.file" value="${email.file}" id="file" >
						<input type="hidden" name="email.filename" value="${email.filename}" id="filename" >
					</label>
				</li>
				-->
				
				
				<li>
					<label class="title">&nbsp;</label>
					<label class="input" style="position:relative;">
						<span id="filespan" style="width:80px;height:28px;position:absolute;z-index:99;">
							<input type="file" name="file" id="attach" 
							style="display:block;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;position:absolute;width:80px;height:28px;" size="1">
						</span>
						<a id="addfj" style="background:url('/mail/images/gray/ico_fujian.jpg') no-repeat center left;padding-left: 15px;">添加附件</a>
						<input type="hidden" name="email.file" value="${email.file}" id="file" >
						<input type="hidden" name="email.filename" value="${email.filename}" id="filename" >
					</label>
				</li>
				
				
				<input type="hidden" name="filelistsize" id="filelistsize" value="${filelistsize }"/>
				<c:if test="${not empty email.file}">
				<li id="fujianFileShow" style="display:block;height:auto;line-height:22px;padding-left:50px;">
					<s:iterator value="#request.filelist" id="fl">
						<%String uuid = CreateUUID.getuuid(); %>
						<p id="<%=uuid %>">${fl.filename}&nbsp;&nbsp;
							<a href="javascript:;" style="color:red;" 
								onclick="deletec('${fl.filename}','${fl.file}','filename','file','fujianFileShow','<%=uuid %>')">[删除]
							</a>
						</p>
					</s:iterator>
				</li>
				</c:if>
				<c:if test="${empty email.file}">
				<li id="fujianFileShow" style="display:none;height:auto;line-height:22px;padding-left:50px;">
					
				</li>
				</c:if>
				<li>
					<label class="title">正&nbsp;&nbsp;文</label>
					<label class="input" style="padding:2px 0;">
						<textarea id="content" name="email.content">${email.content}</textarea>
					</label>
				</li>
			</ul>
			</form>
			<ul class="lxrList">
				<li><b>通讯录</b></li>
				<li class="input">
					<input type="text" name="" id="mailkey" />
					<input type="button" value="" id="getmails" style="width:18px;">
				</li>
				<li class="title">邮箱通讯录</li>
				<li>
					<ul id="lxrinfo" style="height:318px;">
						<li class="listTitle"><label class="zhankai" style="display: none;"></label><label class="shousuo" style="display: none;"></label>个人地址本</li>
						<li class="list">
							<ul>
								<li onclick="showlxr('all','alllxrimg');" style="cursor: pointer;" class="mailgroup">
										<img id="alllxrimg" class="lxrimg" alt="" src="/adm/images/tree/nolines_plus.gif" />
									<a>全部联系人</a>
								</li>
								<%int tag = 0; %>
								<s:iterator value="#request.contactList" id="cal">
									<c:if test="${cal.addr!=sessionScope.user.username}">
										<%tag++;%>
										<li style="padding-left:18px;display:none;" class="lxr mailgroupsmall" lang="lxr_all" id="lxr_${cal.addr}"><c:if test="${fn:length(cal.name)>7}">${fn:substring(cal.name, 0, 7)}...</c:if><c:if test="${fn:length(cal.name)<=7}">${cal.name}</c:if></li>
									</c:if>
								</s:iterator>
								<% if(tag==0){%>
									<li style="padding-left:18px;display:none;" class="lxr mailgroupsmall" lang="lxr_all">(暂无联系人)</li>
								<% }%>
								<c:if test="${not empty ugrouplist}">
									<s:iterator value="#request.ugrouplist" id="uglss">
										<li onclick="showlxr('${uglss.id}','lxrimg${uglss.id}');" style="cursor: pointer;" class="mailgroup">
											<img id="lxrimg${uglss.id}" class="lxrimg" alt="" src="/adm/images/tree/nolines_plus.gif" />
											<a><c:if test="${fn:length(uglss.name)>7}">${fn:substring(uglss.name, 0, 7)}...</c:if><c:if test="${fn:length(uglss.name)<=7}">${uglss.name}</c:if></a>
										</li>
										<%int flag = 0; %>
										<s:iterator value="#request.conglist" id="conl">
											<c:if test="${conl.ugid eq uglss.id}">
												<s:iterator value="#request.contactList" id="cal1">
													<c:if test="${cal1.id eq conl.cid}">
														<c:if test="${cal1.addr!=sessionScope.user.username}">
															<%flag++;%>
															<li style="padding-left:18px;display:none;" lang="lxr_${uglss.id}" class="lxr mailgroupsmall" id="lxr_${cal1.addr}"><c:if test="${fn:length(cal1.name)>7}">${fn:substring(cal1.name, 0, 7)}...</c:if><c:if test="${fn:length(cal1.name)<=7}">${cal1.name}</c:if></li>
														</c:if>
													</c:if>
												</s:iterator>
											</c:if>
										</s:iterator>
										<% if(flag==0){%>
											<li style="padding-left:18px;display:none;" class="lxr mailgroupsmall" lang="lxr_${uglss.id}">(暂无联系人)</li>
										<% }%>
									</s:iterator>
								</c:if>
							</ul>
						</li>
						<li class="listTitle"><label class="zhankai" style="display: none;"></label><label class="shousuo" style="display: none;"></label>单位地址本</li>
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
								 			d.add('${unl.id}','${unl.pid}',''+lenname+'','','','','','','');
										</script>
										<s:iterator value="#request.userlist" id="userl">
											<c:if test="${userl.unid eq unl.id}">
												<c:if test="${userl.username!=sessionScope.user.username}">
													<script type="text/javascript">
														var lenn1 = '${fn:length(userl.name)}';
														var lenname1 = '${userl.name}';
														i--;
														if(lenn1>7){
															lenname1 = '${fn:substring(userl.name, 0, 7)}...';
														}
														d.add(i,'${unl.id}','<a class="lxr" id="lxr_${userl.username}@${sessionScope.host}">'+lenname1+'</a>','','','','','','');
													</script>
												</c:if>
											</c:if>
										</s:iterator>
									</s:iterator>
									<script type="text/javascript">
										document.write(d);
										$('.dTreeNode:eq(0)').hide();
									</script>
								</li>
								<li style="height:auto;" class="mailgroup">
									<img id="mailgroupimg" onclick="showgm();" alt="" src="/adm/images/tree/nolines_plus.gif" />
									<a onclick="showgm();" >邮件群组</a>
								</li>
								<s:if test="#request.grouplist.size eq 0">
									<li style="height:auto;" class="mailgroupsmall">
										<a style="display:none;" class="groupmail">
											(暂无邮件群组)
										</a>
									</li>
								</s:if>
								<s:else>
								<s:iterator value="#request.grouplist" id="grl">
									<li style="height:auto;" class="mailgroupsmall">
										<a style="display:none;" class="groupmail" id="g_${grl.id}" onclick="showsubuser('${grl.id}','${grl.name}');"><c:if test="${fn:length(grl.name)>7}">${fn:substring(grl.name, 0, 7)}...</c:if><c:if test="${fn:length(grl.name)<=7}">${grl.name}</c:if></a>
									</li>
								</s:iterator>
								</s:else>
							</ul>
						</li>
					</ul>
					<ul id="lxrinfo1" style="display: none;"></ul>
				</li>
			</ul>
			<span class="ico">
				<input type="button" title="隐藏通讯录" value="" onclick="closeLxrList()" />
			</span>
		</div>
		<div class="button">
			<span><input type="button" value="" name="sendBtn" class="sendBtn" id="sendmail1" /></span>
			<span><input type="button" value="" name="dingshiBtn" class="dingshiBtn" id="dingshiBtn1"/></span>
			<span><input type="button" value="" name="cuncgBtn" class="cuncgBtn" id="savecg1"/></span>
			<span><input type="button" value="" name="closeBtn" class="closeBtn" onclick="location.href='/main.html'"/></span>
		</div>
	</div>
</div>
</body>
</html>