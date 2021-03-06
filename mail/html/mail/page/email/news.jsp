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
<script type="text/javascript" src="/mail/js/news.js"></script>
<title>单位邮箱-单位公告</title>
</head>
<body>
<form action="/email/News.html" method="post" id="pageForm">
  	<input type="hidden" name="pagenow" id="pagenow" value="1">
  	<input type="hidden" name="issee" value="${issee}">
	<input type="hidden" name="name" value="${name}">
</form>
<div class="main">
	<div class="content">
		<p class="title"><b>单位公告</b>(共${totalsize}封)</p>
		<!-- <div class="button" style="display: none;"> -->
		<div class="button">
			<!--<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="movenews(1);"/></span>
			<span><input type="button" value="" name="cddeleteBtn" class="cddeleteBtn" onclick="moverecive(2);"/></span>
			<span><input type="button" value="" name="zfBtn" class="zfBtn" /></span>
			<span><input type="button" value="" name="qbbwydBtn" class="qbbwydBtn" onclick="moverecive(3);"/></span>-->
			<!--
			<span>
				<select id="addtotag" onchange="addetag(this);">
					<option value="-1">标记为...</option>
					<s:iterator value="#request.taglist" id="tlist">
						<option value="${tlist.id}">　${tlist.name}</option>
					</s:iterator>
					<option value="" disabled>--------------</option>
					<option value="-2" onclick="$('.createlabel').show()">　新建标签</option>
				</select>
			</span>
			-->
			<!--
			<span>
				<select name="" id="">
					<option value="">移动到...</option>
					<option value="4" onclick="moverecive(4);">　发件箱</option>
					<option value="5" onclick="moverecive(5);">　草稿箱</option>
					<option value="6" onclick="moverecive(6);">　垃圾箱</option>
				</select>
			</span>
			-->
		<c:if test="${not empty noticelist}">
			<span class="page">
				${pagenow}/${pagenum} 页
				<c:if test="${pagenum>1}">
					${pagebar}
				</c:if>
				<a href="javascript:void(0);" class="tiaozhuanBtn" onclick="tiaozhuanShow(1)">跳转</a>
			</span>
			</c:if>
		</div>
		<div class="tiaozhuan">
			跳转到第
			<input type="text" class="ipt1" name="jumpnum" id="jumpnum" onkeydown="if((event||window.event).keyCode&&(event||window.event).keyCode==13){$('#qdBtn').click();}"/>
			页
			<input type="button" class="qdBtn" name="qdBtn" id="qdBtn" onclick="jump();" />
		</div>
		<div class="createlabel">
			<div class="createlabel_head" style="cursor:move;">
				<span>新建标签</span>
				<a class="createlabel_close" href="javascript:;" onclick="$('.createlabel').hide()" title="关闭"></a>
			</div>
			<form action="/tag/doAddTag.html" method="post" id="addtagform">
			<div class="createlabel_body">
				<p>请您输入标签名称</p>
				<div style="margin:8px 0 0;">
					<input type="text" class="createlabel_input" name="tag.name" id="tagname"/>
					<input type="hidden" name="tag.userid" value="${sessionScope.user.id}" id="userid" />
				</div>
			</div>
			</form>
			<div class="createlabel_bottom">
				<input type="button" class="btn_blue" value="确定" onclick="addtag();"/>
				<input type="button" class="btn_gray" value="取消" onclick="$('.createlabel').hide()"/>
			</div>
		</div>
		<div class="mail">
			<ul class="relation" style="padding:0px;">
				<li class="getmailbt" style="border-top: #ccc 1px solid;">
					<span class="span1"><input type="checkbox" onclick="selall(this.checked);" id="cbs"/></span>
					<span class="span21"><label class="ico4"></label></span>
					<span class="span2">发件人</span>
					<span class="span9">主题</span>
					<span class="span50">时间↓</span>
				</li>
				<!--
				<li class="bt">
					<b>今天</b>(2封)
				</li>
				-->
				<form action="/email/EditNewsMail.html" method="post" id="editnewsform">
					<input type="hidden" name="edittype" id="edittype">
					<input type="hidden" name="tid" id="tagid">
					<input type="hidden" name="ttype" id="tagtype">
					<s:iterator value="#request.noticelist" id="eml">
						<li>
						
							<span class="span7">
								<input type="checkbox" name="id" state="${eml.state}" class="box" value="${eml.id}" />
								<input type="checkbox" name="states" class="box1" value="${eml.state}" style="display:none;" />
								<input type="checkbox" name="tomails" class="box4" value="${eml.tomail}" style="display:none;" />
							</span>
							<a>
								<span class="span22" onclick="location.href='/email/MailInfo.html?id=${eml.id}&ftype=10'">
								<c:if test="${eml.state eq 0}">
								<label class="ico1"></label>
								</c:if>
								<c:if test="${eml.state eq 1}">
									<label class="ico2"></label>
								</c:if>
								
								</span>
								<span class="span5" onclick="location.href='/email/MailInfo.html?id=${eml.id}&ftype=10'">${eml.author}</span>
								<span class="span6"><font onclick="location.href='/email/MailInfo.html?id=${eml.id}&ftype=10';return false;">
								<c:if test="${fn:length(eml.title)>35}">${fn:substring(eml.title, 0, 35)}...</c:if><c:if test="${fn:length(eml.title)<=35}">${eml.title}</c:if>
								</font>
								</span>
								<span class="span50">${eml.time}</span>
							</a>
						</li>
					</s:iterator>
				</form>
				<li class="bottom" style="border-bottom: #ccc 1px solid;">
					<label>选择：</label>
					<a onclick="selall(true);">全部</a>
					-
					<a onclick="selall(false);">无</a>
					-
					<a onclick="selread(true);">已读</a>
					-
					<a onclick="selread(false);">未读</a>
				</li>
			</ul>
		</div>
		<div class="button">
		<!--
		<div class="button" style="display: none;">
			<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="movenews(1);"/></span>
			<span><input type="button" value="" name="cddeleteBtn" class="cddeleteBtn" onclick="moverecive(2);"/></span>
			<span><input type="button" value="" name="zfBtn" class="zfBtn" /></span>
			<span><input type="button" value="" name="qbbwydBtn" class="qbbwydBtn" onclick="moverecive(3);"/></span>-->
			<!--
			<span>
				<select id="addtotag1" onchange="addetag(this);">
					<option value="-1">标记为...</option>
					<s:iterator value="#request.taglist" id="tlist">
						<option value="${tlist.id}">　${tlist.name}</option>
					</s:iterator>
					<option value="" disabled>--------------</option>
					<option value="-2" onclick="$('.createlabel').show()">　新建标签</option>
				</select>
			</span>
			-->
			<!--
			<span>
				<select name="" id="">
					<option value="">移动到...</option>
					<option value="4" onclick="moverecive(4);">　发件箱</option>
					<option value="5" onclick="moverecive(5);">　草稿箱</option>
					<option value="6" onclick="moverecive(6);">　垃圾箱</option>
				</select>
			</span>
			-->
			<c:if test="${not empty noticelist}">
			<span class="page">
				${pagenow}/${pagenum} 页
				<c:if test="${pagenum>1}">
					${pagebar}
				</c:if>
				<a href="javascript:void(0);" class="tiaozhuanBtn" onclick="tiaozhuanShow(event)">跳转</a>
			</span>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>