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
<script type="text/javascript" src="/mail/js/savemail.js"></script>
<script type="text/javascript" src="/mail/js/move.js"></script>
<title>单位邮箱-标签</title>
<style type="text/css">
	li:HOVER {
		padding: 6px 5px 6px 15px;
		color: #2A586F;
		cursor: pointer;
		background: #f1f1f1;
	}	
</style>
</head>
<body>

<form action="/email/fromMail.html" method="post" id="pageForm">
  	<input type="hidden" name="pagenow" id="pagenow" value="1">
	<input type="hidden" name="name" value="${name}">
</form>
<div class="main">

	<div class="content" style="height: 526px;">
	
		<p class="title">
			<b><c:if test="${tagname eq 'null'}">标签(共${totalsize}封)</c:if></b>
			<b><c:if test="${tagname ne 'null' }">${tagname}</c:if></b> 
			
			<a href="/user/setmail.html" style="float:right">
			<label class="ico_manage" style="margin-top: 7px;"></label>
			管理"标签"
			</a>
		</p>

		<div class="button">
			
		</div>
		<div class="mail" >
			<ul class="relation" style="padding:0px;">
				<li class="getmailbt">
					<span class="span21" style="border-right:#c5c6c8 1px solid"><label class="ico4"></label></span>
					<span class="span2" style="border-right:#c5c6c8 1px solid">发件人</span>
					<span class="span9" style="border-right:#c5c6c8 1px solid">主题</span>
					<span class="span50" style="border-right:#c5c6c8 0px solid">时间</span>
					<span class="span1" style="border-right:#c5c6c8 0px solid"></span>
				</li>
				
				<s:iterator value="#request.getTagMailRecvList" id="recv"></s:iterator>
				<form action="" method="post" id="">
									
					<!-- 收件箱标记邮件 -->
					<s:iterator value="#request.getTagMailRecvList" status="st">
					<li>
						<span class="span22" style="border-right:#c5c6c8 0px solid; cursor: pointer;"
						onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=${type}&tagmail=1&tagmailid=${mailid}&tagtypes=${type}&tagname=${tagname}';return false;">						
							<c:if test="${state eq 0}"><label class="ico1"></label></c:if>
							<c:if test="${state eq 1 or state eq 3 or state eq 4}"><label class="ico2"></label></c:if>
						</span>
						<span class="span5" style="border-right:#c5c6c8 0px solid; cursor: pointer;" 
						onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=${type}&tagmail=1&tagmailid=${mailid}&tagtypes=${type}&tagname=${tagname}';return false;">
							<c:if test="${recvFrommailList[st.index] != null}">${recvFrommailList[st.index]}</c:if>	
							<c:if test="${recvFrommailList[st.index] == null}">(收件人未填写)</c:if>
						</span>
						<span class="span6" style="border-right:#c5c6c8 0px solid; cursor: pointer;"
						onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=${type}&tagmail=1&tagmailid=${mailid}&tagtypes=${type}&tagname=${tagname}';return false;">
							<c:if test="${not empty subject}">${subject}</c:if>
							<c:if test="${empty subject}">(无主题)</c:if>
						</span>
						<span class="span50" style="border-right:#c5c6c8 0px solid; cursor: pointer;"
						onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=${type}&tagmail=1&tagmailid=${mailid}&tagtypes=${type}&tagname=${tagname}';return false;">
							${time}
						</span>
						<span class="span7" style="border-right:#c5c6c8 0px solid; cursor: pointer;">
							<!-- <a href="/user/delTagNameMail.html?mailid=${mailid}&types=${type}&tagname=${tagname}">删除</a> -->
						</span>
					</li>
					</s:iterator>
					
					<!-- 发件箱标记邮件 -->
					<s:iterator value="#request.getTagMailSendList" status="st">
						<li>
							<span class="span22" style="border-right:#c5c6c8 0px solid"
							onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=${type}&tagmail=1&tagmailid=${mailid}&tagtypes=${type}&tagname=${tagname}';return false;">
								<c:if test="${state eq 0}"><label class="ico1"></label></c:if>
								<c:if test="${state eq 1 or state eq 3 or state eq 4}"><label class="ico2"></label></c:if>
							</span>
							<span class="span5" style="border-right:#c5c6c8 0px solid"
							onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=${type}&tagmail=1&tagmailid=${mailid}&tagtypes=${type}&tagname=${tagname}';return false;">
								<c:if test="${sendFrommailList[st.index] != null}">${sendFrommailList[st.index]}</c:if>	
								<c:if test="${sendFrommailList[st.index] == null}">(收件人未填写)</c:if>							
							</span>
							<span class="span6" style="border-right:#c5c6c8 0px solid"
							onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=${type}&tagmail=1&tagmailid=${mailid}&tagtypes=${type}&tagname=${tagname}';return false;">
								<c:if test="${not empty subject}">${subject}</c:if>
								<c:if test="${empty subject}">(无主题)</c:if>							
							</span>
							<span class="span50" style="border-right:#c5c6c8 0px solid"
							onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=${type}&tagmail=1&tagmailid=${mailid}&tagtypes=${type}&tagname=${tagname}';return false;">
								${time}
							</span>
							<span class="span7" style="border-right:#c5c6c8 0px solid">
								<!-- <a href="/user/delTagNameMail.html?mailid=${mailid}&types=${type}&tagname=${tagname}">删除</a> -->
							</span>
						</li>
					</s:iterator>
					<!-- 草稿箱标记邮件 -->
					<s:iterator value="#request.getTagMailDraftsList" status="st">
						<li>
							<span class="span22" style="border-right:#c5c6c8 0px solid"
							onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=0&tagmail=1&draftsid=${id}&tagtypes=${type}&tagname=${tagname}';return false;">
								<c:if test="${see eq 2}"><label class="ico1"></label></c:if>
								<c:if test="${see eq 1}"><label class="ico2"></label></c:if>
							</span>
							<span class="span5" style="border-right:#c5c6c8 0px solid"
							onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=0&tagmail=1&draftsid=${id}&tagtypes=${type}&tagname=${tagname}';return false;">
								<c:if test="${draftsFrommailList[st.index] != null}">${draftsFrommailList[st.index]}</c:if>
								<c:if test="${draftsFrommailList[st.index] == null}">(收件人未填写)</c:if>
							</span>
							<span class="span6" style="border-right:#c5c6c8 0px solid"
							onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=0&tagmail=1&draftsid=${id}&tagtypes=${type}&tagname=${tagname}';return false;">
								<c:if test="${not empty subject}">${subject}</c:if>
								<c:if test="${empty subject}">(无主题)</c:if>
							</span>
							<span class="span50" style="border-right:#c5c6c8 0px solid"
							onclick="location.href='/email/MailInfo.html?id=${id}&mailid=${mailid}&ftype=0&tagmail=1&draftsid=${id}&tagtypes=${type}&tagname=${tagname}';return false;">
								${time}
							</span>
							<span class="span7" style="border-right:#c5c6c8 0px solid">
								<!-- <a href="/user/delTagNameMail.html?id=${id}&types=${type}&tagname=${tagname}">删除</a> -->
							</span>
						</li>
					</s:iterator>
				</form>				
			</ul>
		</div>
		<div class="button"></div>
	</div>
</div>
</body>
</html>