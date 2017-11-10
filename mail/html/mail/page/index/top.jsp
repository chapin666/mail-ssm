<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/mail/css/gray/top.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="main">
		<div class="logo">
			<img src="/mail/images/gray/logo.png?t=<%=new Date() %>" width="196" height="37" border="0" />
		</div>
		<div class="title">
			<p><nobr><b>${sessionScope.user.name}</b>&#60;${sessionScope.user.username}@${sessionScope.host}&#62;</nobr></p>
			<ul style="width: 300px;">
				<li><a onclick="top.window.location='/index.html';">邮箱首页</a></li>
				<li>|</li>
				<li><a href="/user/toEditPass.html" target="main">修改密码</a></li>
				<li>|</li>
				<li><a href="/user/setmail.html" target="main">邮箱设置</a></li>
				<li>|</li>
				<li><a onclick="top.window.location='/exit.html';">退出</a></li>
			</ul>
		</div>
		<div class="right">
			<div class="exit">
			
			</div>
			<!-- 
			<div class="search">
				<form action="" method="post" name="searchMailFrom" id="searchMailFrom">
					<input type="text" name="key" id="key" class="input1" />
					<input type="button" value="" name="keyBtn" id="keyBtn" class="keyBtn" />
				</form>
			</div>
			-->
		</div>
	</div>
</body>
</html>