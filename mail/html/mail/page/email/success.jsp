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
<title>单位邮箱用户登录</title>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="success_content">
			<img align="middle" class="success_content_img" src="/mail/images/gray/ico_sendf.gif" />
			<div class="success_content_div">
				<c:if test="${empty dstime}">
					<b>您的邮件已发送</b>
				</c:if>
				<c:if test="${not empty dstime}">
					<b>您的邮件已保存到草稿箱，定时在 ${dstime} 发送</b>
				</c:if>
			</div>
			<div class="settingtable">
				<p style="color:#7A8F99;margin-bottom:10px;"><c:if test="${empty dstime}">邮件已发送给以下联系人：</c:if><c:if test="${not empty dstime}">邮件正准备发送给以下联系人：</c:if></p>
				<c:if test="${not empty recivelist}">
				<p>收件人</p>
				<ul>
					<s:iterator value="#request.recivelist" id="rcl">
						<li>${rcl}</li>
					</s:iterator>
				</ul>
				</c:if>
				<c:if test="${not empty copytolist}">
				<p>抄送方</p>
				<ul>
					<s:iterator value="#request.copytolist" id="cpl">
						<li>${cpl}</li>
					</s:iterator>
				</ul>
				</c:if>
				<c:if test="${not empty bcclist}">
				<p>密送方</p>
				<ul>
					<s:iterator value="#request.bcclist" id="bcl">
						<li>${bcl}</li>
					</s:iterator>
				</ul>
				</c:if>
			</div>
			<div class="success_btn">
				<span onclick="top.location.href='/index.html'">返回邮箱首页</span>
				<a href="/email/Write.html">再写一封&gt;&gt;</a>
			</div>
		</div>
	</div>
</div>
</body>
</html>