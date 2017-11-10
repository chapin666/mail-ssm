<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/adm/css/newsletter.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<title>单位邮箱用户登录</title>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="success_content">
			<img align="middle" class="success_content_img" src="/mail/images/gray/ico_sendf.gif" />
			<div class="success_content_div">
				<b>您的公告已发布</b>
			</div>
			<!--
			<div class="settingtable">
				<p style="color:#7A8F99;margin-bottom:10px;">公告已发送给以下成员：</p>
				<p>收件人</p>
				<ul>
					<s:iterator value="#request.bcclist" id="rcl">
						<li>${rcl}</li>
					</s:iterator>
				</ul>
			</div>
			-->
			<div class="success_btn">
				<span onclick="location.href='/emails/getNews.mail'">返回公告列表</span>
				<a href="/emails/SendNews.mail">再写一封&gt;&gt;</a>
			</div>
		</div>
	</div>
</div>
</body>
</html>