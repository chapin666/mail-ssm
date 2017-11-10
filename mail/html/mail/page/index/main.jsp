<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib  prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/mail/css/gray/main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.div1{
		float: right;
		display: inline;					
		width: 250px;
		position:absolute;
		right:-28px;top: 60px; 
		border-style:groove;
		border-width:1pt; 
		border-color: green;
		border-height: 1px;" 
	}
	
</style>
</head>
<body>
	<div class="main">
		<div align="left" style="height: 350px;">
		<div class="content" style="margin:2%;overflow: hidden;">
			<h1 style="font-size: 16px;">您好，<span style="color:#2A586F;font-size: 16px;">${sessionScope.user.name}</span></h1>
		</div>
		<div style="margin-top: 15px;margin-left: 25px;">
			<label class="ico1" style="margin: -2px 4px 0 0;"></label>
			<span style="margin-top: 10px;">邮件：${num2}<a href="/email/getMail.html?state=0">封未读邮件</a></span>			
		</div>
		<h1 style="font-size: 16px;">
		<div class="content" style="overflow: hidden;margin-left: 45px; font-weight: bold;font-size: 35px;">
			<a href="/email/getMail.html">收件箱(${num4})</a>
			<a href="/email/News.html?state=0">单位公告(${num1})</a>
			<a href="/email/getMainMail.html?state=0">星标邮件(${num3})</a>
		</div>
		
		</h1>
		</div>
		<div align="right"  style="position:absolute;right:60px;top: 20px; width: 245px;height: 200px;">
			<div>				
				<div align="left">	
					<%--天气预报<iframe src="http://www.7stk.com/1/6/sina.htm" frameborder="0" width="330" height="37" 
					marginheight="0" marginwidth="0" scrolling="no"></iframe> 				
				--%></div>
			</div>
			
			<div class="div1" align="left">
					<div style="background: #CCCCCC;">&nbsp;</div>
					<div style="background: #CCCCCC;height: 6px;">
						<div style="background: #CCCCCC;margin-left: 25px; "><h1>我的信息</h1></div>
					</div>
					<div style="background: #CCCCCC;">&nbsp;</div>
				<c:if test="${message != null}">
					<div style="margin-top: 10px;margin-left: 15px;color: red;">${message}</div>
				</c:if>				
				<c:if test="${message == null}">
					<div style="margin-top: 10px;margin-left: 15px;">上次登录时间：${time}</div>
					<div style="margin-top: 10px;margin-left: 15px;">上次 登录 IP：${ip}</div>
				</c:if>				
				<div>&nbsp;</div>
				<div>&nbsp;</div>
				<div>&nbsp;</div>
				<div>&nbsp;</div>
				<div>&nbsp;</div>
				<div>&nbsp;</div>
				<div>&nbsp;</div>				
			</div>
						
		</div>
		
		<div></div>
	</div>
	
</body>
</html>