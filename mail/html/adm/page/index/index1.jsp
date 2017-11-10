<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="width:600px;margin-left:60px;float: left;">
	<div align="center">
		<span>您好，${sessionScope.member.username}</span>
		<a onclick="window.location='/adm/exit.mail';">退出</a>
	</div>
	<div align="center" style="margin-top:20px;">
		<a href="/adm/index.mail">首页</a>
		<a href="/adm/member.mail" style="padding-left: 30px;">成员与群组</a>
		<a href="" style="padding-left: 30px;">我的单位</a>
		<a href="" style="padding-left: 30px;" >系统日志</a>
		<a href="" style="padding-left: 30px;">邮箱过滤</a>
		<a href="" style="padding-left: 30px;">单位公告</a>
		
		
	</div>
	<div align="center" style="margin-top:20px;">
		<span>欢迎您，${sessionScope.member.username}</span>
	</div>
	<div align="center" style="margin-top:20px;">
		<a href="" style="">添加成员</a>
		<a href="" style="padding-left: 30px;">内部公告</a>
		<a href="" style="padding-left: 30px;">邮件群组</a>
	</div>
	<div align="center" style="margin-top:20px;">
		<a href="" style="">邮箱拦截</a>
		<a href="" style="padding-left: 30px;">系统日志</a>
		<a href="" style="padding-left: 30px;">域名管理</a>
	</div>
</div>
<div>
	<div align="center" style="margin-top:20px;">
		<span>域名</span><br>
		${sessionScope.host}
	</div>
	<div align="center" style="margin-top:20px;">
		<span>成员</span>
	</div>
	<div align="center" style="margin-top:20px;">
		<span>LOGO</span>
	</div>
</div>
</body>
</html>