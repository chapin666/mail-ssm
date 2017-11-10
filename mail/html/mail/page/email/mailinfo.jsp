<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mail/css/gray/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
<script type="text/javascript" src="/mail/js/mailinfo.js"></script>
<script type="text/javascript" src="/mail/js/move.js"></script>
<title>单位邮箱-详细页</title>

</head>
<body>

<form action="/email/ReWrite.html" method="post" id="rewriteform">
	<input type="hidden" name="email.id" value="${email.id}" id="emailid"/>
	<input type="hidden" name="email.fromname" value="${email.fromname}" />
	<input type="hidden" name="email.frommail" value="${email.frommail}" />
	<input type="hidden" name="email.time" value="${email.time}" />
	<input type="hidden" name="email.toname" value="${email.tomail}" id="tomail" />
	<input type="hidden" name="email.toname" value="${email.toname}"  />
	<input type="hidden" name="email.copyto" value="${email.copyto}" />
	<input type="hidden" name="email.bcc" value="${email.bcc}" />
	<input type="hidden" name="email.file" value="${email.file}" />
	<input type="hidden" name="email.filename" value="${email.filename}" />
	<input type="hidden" name="email.title" value="${title1}" />
	<input type="hidden" name="email.content" value="${content1}" />
	<input type="hidden" name="type" id="typeid" />
</form>
<!--撤回邮件时的对话框-->	
<div class="DoRecall">
	<div class="createlabel_head" style="cursor:move;">
		<span>撤回邮件</span>
	</div>
	<form>
		<div id="recalltext_div_1" class="recalltext">
			<dir class="icon_info_b"></dir>
			<div style="font-weight: bold;font-size: 14px;"> 确定撤回此邮件吗？</div>
			<div style="font-size: 12px;">如果撤回成功，对方将只能看到邮件的主题，并得到已被撤回的提示。</div>
			<div style="color: #A0A0A0;font-size: 12px;">
			详细说明：
			<ol style="list-style:decimal;padding:0;margin:0 0 0 2em;*zoom:expression(function(el){el.style.zoom=1;})(this);">
			<li style="list-style: decimal inside none;">仅尝试撤回发往内部的邮件，不支持从其他邮箱撤回。</li>
			<li style="list-style: decimal inside none;">如果对方已经阅读，将不予撤回。</li>
			<li style="list-style: decimal inside none;">撤回结果将通过系统邮件通知您。 </li>
			</ol>
			</div>
		</div>
		
		<div id="recalltext_div_2" class="recalltext" style="display:none;">
			<dir class="icon_ok_b"></dir>
			<div style="font-weight: bold;font-size: 14px;">撤回操作已完成。</div>
				<div style="width:97%;margin:0auto;height:94px;*height:94px;overflow:auto;overflow-x:hidden;cursor: pointer;">
				<table class="list_2" cellspacing="0" cellpadding="2" style="width:100%;border:0px solid #fff;">
					<tbody>
					<tr>
						<td style="width:270px; height:30px;border: 1px solid #CCCCCC !important;">${email.toname}
						</td>
						<td>
							<span id="recallresult" style="color: #CC0000;"></span>
						</td>
					</tr>
					</tbody>
				</table>
				</div>
		</div>
	
	</form>
	<div class="createlabel_bottom">
		<input type="button" id="btn_blue" class="btn_blue" value="确定" onclick="recall();" />
		<input type="button" id="btn_ok" class="btn_blue" value="确定" style="display:none" onclick="RecallClose();" />
		<input type="button" id="btn_gray" class="btn_gray" value="取消" onclick="$('.DoRecall').hide()" />
	</div>
</div>

<!--举报邮件时弹出的选择对话框-->
<div class="reportmail">
	<div class="createlabel_head" style="cursor:move;">
		<span>举报垃圾邮件</span>
	</div>
	<form action="/email/ReportMail.html" method="post" id="report">
	<input type="hidden" name="email.frommail" value="${email.frommail}" />
	<input type="hidden" name="email.id" value="${email.id}" id="emailid"/>
	
		<div class="createlabel_body" style="cursor: auto;">
			<div style="margin:8px 0 0;font-weight:bold; ">
				<input type="checkbox" name="checkbox1" value="checkbox" style="cursor: pointer;">
				将该发件人加入黑名单
				<br /><br />
				<input type="checkbox" name="checkbox2" value="checkbox" style="cursor: pointer;"> 
				将发件人历史来信全部移到垃圾箱 
				<br />
			</div>
		</div>
	</form>
	<div class="createlabel_bottom">
		<input type="button" class="btn_blue" value="确定" onclick="reportmail();" />
		<input type="button" class="btn_gray" value="取消" onclick="$('.reportmail').hide()" />
	</div>
</div>

<!--彻底删除弹出的选择对话框-->
<div class="cddelete">
	<div class="createlabel_head" style="cursor:move;">		
	<span>删除确认</span>
</div>
<div class="createlabel_body">
	<div style="margin:8px 0 0;font-weight:bold; "><br/>	
	<dir class="icon_info_b" style="margin: -7px 12px 8px 0px;"></dir>				
		彻底删除后邮件将不可恢复，确定要删除？<br/><br/>
	</div>
</div>		
<div class="createlabel_bottom"> 
	<input type="button" class="btn_blue" value="确定" onclick="moveinfo(6);" />
	<input type="button" class="btn_gray" value="取消" onclick="$('.cddelete').hide()" />
	</div>
</div>

<div class="main">
	<div class="content">

			<div class="button">
			<span><input type="button" value="" name="backBtn" class="backBtn" onclick="history.back();"/></span>
			<c:if test="${requestScope.ftype!=10}">
			<span class="jgx">&nbsp;</span>
			<!-- 
			<c:if test="${email.frommail==requestScope.username and requestScope.ftype!=3}">
				<span><input type="button" value="" name="getbackBtn" class="getbackBtn" onclick="getback();"/></span>
			</c:if>
			-->
			<c:if test="${requestScope.ftype==3}">
				<span><input type="button" value="" name="rewriteBtn" class="rewriteBtn" onclick="rewrite(3);"/></span>
			</c:if>
			<c:if test="${email.frommail!=requestScope.username and email.frommail!=requestScope.adminname}">
				<span><input type="button" value="" name="huifuBtn" class="huifuBtn" onclick="rewrite(1);"/></span>
			</c:if>
			<span><input type="button" value="" name="zfBtn" class="zfBtn" onclick="rewrite(2);"/></span>
			<c:if test="${email.frommail!=requestScope.adminname and requestScope.ftype!=6 and requestScope.ftype!=0 and requestScope.ftype!=4}">
				<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="moveinfo(1);"/></span>
				<span><input type="button" value="" name="cddeleteBtn" class="cddeleteBtn" onclick="moveinfo(2);"/></span>
			</c:if>
			<c:if test="${email.frommail==requestScope.adminname or requestScope.ftype==0 or requestScope.ftype==4}">
				<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="moveinfo(2);"/></span>
			</c:if>
			<!-- 撤回 -->
			<c:if test="${requestScope.ftype==2}">
				<span><input type="button" value="" name="getbackBtn" class="getbackBtn" onclick="DoRecall();"/></span>
			</c:if>
			<!-- 举报 -->
			<c:if test="${requestScope.ftype==1}">							
				<span><input type="button" value="" name="reportBtn" class="reportBtn" onclick="report();"/></span>
			</c:if>
			
			
			
			<c:if test="${requestScope.ftype eq 1}">
			<span>
				<select id="addtotag" onchange="addetag(this);">
					<option value="-1">标记为...</option>
					<option value="" disabled>--------------</option>
					<option value="-3">已读邮件</option>

					<option value="-4">未读邮件</option>
					<c:if test="${not empty taglist}">
					<option value="" disabled>--------------</option>

					</c:if>
					<s:iterator value="#request.taglist" id="tlist">
						<option value="${tlist.id}">　${tlist.name}</option>
					</s:iterator>
					<option value="" disabled>--------------</option>
					<!-- <option value="-2" onclick="$('.createlabel').show()">新建标签</option> -->
					<option value="-2">新建标签</option>
				</select>
			</span>
			</c:if>
			</c:if>
			<!--
			<c:if test="${email.frommail!=requestScope.adminname}">
			<span>
				<select name="" id="">
					<option value="">移动到...</option>
					<option value="4" onclick="moverecive(4);">　发件箱</option>
					<option value="5" onclick="moverecive(5);">　草稿箱</option>
					<option value="6" onclick="moverecive(6);">　垃圾箱</option>
				</select>
			</span>
			</c:if>
			-->
		</div>
		<div class="tiaozhuan">
			跳转到第
			<input type="text" class="ipt1" />
			页
			<input type="button" class="qdBtn" />
		</div>
		<div class="createlabel">
			<div class="createlabel_head" style="cursor:move;">
				<span>新建标签</span>
				<a class="createlabel_close" href="javascript:;" onclick="addetag(0);" title="关闭"></a>
			</div>
			<form action="/tag/doAddTag6.html" method="post" id="addtagform">
			<div class="createlabel_body">
				<p>请您输入标签名称</p>
				<div style="margin:8px 0 0;">
					<input type="text" class="createlabel_input" name="tag.name" id="tagname"/>
					<input type="hidden" name="tag.userid" value="${sessionScope.user.id}" id="userid" />
					<input type="hidden" name="eid" value="${email.id}" id="emailids" />
					<input type="hidden" name="ftype" value="${requestScope.ftype}">
					<span class="tagsp1" id="tagsp1" style="color: #FF0000;"></span>
				</div>
			</div>
			</form>
			<div class="createlabel_bottom">
				<input type="button" class="btn_blue" value="确定" onclick="addtag();"/>
				<input type="button" class="btn_gray" value="取消" onclick="addetag(0);"/>
			</div>
		</div>
		<div class="mail infomail">
			<ul class="mailinfo" style="padding:0px;height:auto;padding-bottom:10px;">
				<li class="ptitle">
					<p class="title"><b>${email.title}</b></p>
				</li>
				<c:if test="${requestScope.ftype!=0}">
					<li>
						<p>发件人：<font color="#5FA207"><b>${email.fromname}${email.frommail}</b></font></p>
					</li>
				</c:if>
				<li>
					<p>时　间：${email.time}</p>
				</li>

				<li style="height:auto;" id="emailtoname">
					<p style="height:auto;">
						收件人：<font color="#333">${fn:replace(email.toname,"<","&lt;")}</font>
					</p>
					<span style="float:right;"><a href="javascript:;" onclick="javascript:this.href=self.location" target="_blank">新窗口打开</a> </span>
				</li>

				<c:if test="${not empty email.copyto}">
					<li style="height:auto;" id="emailcopyto">
						<p style="height:auto;">抄　送：<font color="#333">${fn:replace(email.copyto,"<","&lt;")}</font></p>
					</li>
				</c:if>
				
				<c:if test="${not empty email.bcc}">
					<li style="height:auto;" id="emailcopyto">
						<p style="height:auto;">密　送：<font color="#333">${fn:replace(email.bcc,"<","&lt;")}</font></p>
					</li>
				</c:if>
				
				<c:if test="${not empty filelist}">
					<li style="height:auto;">
						<p>
							<a><span>附　件：</span><span class="ico3"></span></a>
							<s:iterator value="#request.filelist" id="fl">
								<a onclick="$('#files').val('${fl.file}');$('#filenames').val('${fl.filename}');$('#downloadform').submit();">&nbsp;&nbsp;${fl.filename}</a>
							</s:iterator>
						</p>
					</li>
					<form action="/email/download.html" method="post" id="downloadform">
						<input type="hidden" name="files" id="files">
						<input type="hidden" name="filenames" id="filenames">
					</form>
				</c:if>
				<!-- 发送状态 -->
				<c:if test="${requestScope.ftype==2}">
					<li>
						<p>发送状态：<font color="#000000"><c:if test="${email.state2 eq 0}"> 正在投递...</c:if><c:if test="${email.state2 eq 1}"> 投递成功</c:if><c:if test="${email.state2 eq 2}"> 投递失败</c:if></font></p>
					</li>
				</c:if>
			</ul>
			<div class="mailcontent">
				<div style="padding:0px 15px;">${email.content}</div>
			</div>
		</div>
		<div class="button">
			<span><input type="button" value="" name="backBtn" class="backBtn" onclick="history.back();"/></span>
			<c:if test="${requestScope.ftype!=10}">
			<span class="jgx">&nbsp;</span>
			<!--
			<c:if test="${email.frommail==requestScope.username and requestScope.ftype!=3}">
				<span><input type="button" value="" name="getbackBtn" class="getbackBtn" onclick="getback();"/></span>
			</c:if>
			-->
			<c:if test="${requestScope.ftype==3}">
				<span><input type="button" value="" name="rewriteBtn" class="rewriteBtn" onclick="rewrite(3);"/></span>
			</c:if>
			<c:if test="${email.frommail!=requestScope.username and email.frommail!=requestScope.adminname}">
				<span><input type="button" value="" name="huifuBtn" class="huifuBtn" onclick="rewrite(1);"/></span>
			</c:if>
			<span><input type="button" value="" name="zfBtn" class="zfBtn" onclick="rewrite(2);"/></span>
			<c:if test="${email.frommail!=requestScope.adminname and requestScope.ftype!=6 and requestScope.ftype!=0 and requestScope.ftype!=4}">
				<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="moveinfo(1);"/></span>
				<span><input type="button" value="" name="cddeleteBtn" class="cddeleteBtn" onclick="moveinfo(2);"/></span>
			</c:if>
			<c:if test="${email.frommail==requestScope.adminname or requestScope.ftype==0 or requestScope.ftype==4}">
				<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="moveinfo(2);"/></span>
			</c:if>
			
			<!-- 撤回 -->
			<c:if test="${requestScope.ftype==2}">
				<span><input type="button" value="" name="getbackBtn" class="getbackBtn" onclick="moverecive(3);"/></span>
			</c:if>
			
			<!-- 举报 -->
			<c:if test="${requestScope.ftype==1}">							
				<span><input type="button" value="" name="reportBtn" class="reportBtn" onclick="report();"/></span>
			</c:if>
			
			<c:if test="${requestScope.ftype eq 1}">
			<span>
				<select id="addtotag1" onchange="addetag(this);">
					<option value="-1">标记为...</option>
					<option value="" disabled>--------------</option>
					<option value="-3">已读邮件</option>
					<option value="-4">未读邮件</option>
					<c:if test="${not empty taglist}">
					<option value="" disabled>--------------</option>
					</c:if>
					
					<s:iterator value="#request.taglist" id="tlist">
						<option value="${tlist.id}">　${tlist.name}</option>
					</s:iterator>
					<option value="" disabled>--------------</option>
					<option value="-2" onclick="$('.createlabel').show()">新建标签</option>
				</select>
			</span>
			</c:if>
			</c:if>
			<!--
			<c:if test="${email.frommail!=requestScope.adminname}">
			<span>
				<select name="" id="">
					<option value="">移动到...</option>
					<option value="4" onclick="moverecive(4);">　发件箱</option>
					<option value="5" onclick="moverecive(5);">　草稿箱</option>
					<option value="6" onclick="moverecive(6);">　垃圾箱</option>
				</select>
			</span>
			</c:if>
			-->
		</div>
	</div>
</div>
<form method="post" id="editinfoform">
	<input type="hidden" name="ftype" value="${ftype}">
	<input type="hidden" name="edittype" id="edittype">
	<input type="hidden" name="tid" id="tagid">
	<input type="hidden" name="eid" value="${email.id}">
	<input type="hidden" name="ttype" id="tagtype">
	<input type="checkbox" style="display:none;" name="id" class="box" lang="${email.see}" value="${email.id}" checked />
	<input type="checkbox" name="types" class="box1" value="${email.type}" checked style="display:none;" />
	<input type="checkbox" name="states" class="box2" value="${email.state}" checked style="display:none;" />
	<input type="checkbox" name="files" class="box3" value="${email.file}" checked style="display:none;" />
	<input type="checkbox" name="frommails" class="box4" value="${email.frommail}" checked style="display:none;" />
	<input type="checkbox" name="mailids" class="box5" value="${email.mailid}" checked style="display:none;" />
	<input type="text" name="reportname" value="${requestScope.username}">	
</form>
</body>
</html>