<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/passowrd.js"></script>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="left structManage">
			<ul class="sidetd">
				<li>
					<a href="/unit/toUnit.mail">单位信息<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/unit/toUpdateHost.mail">域名管理<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/member/toApdatePassword.mail" class="selected">修改密码<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/member/getMemberList.mail">分级管理员<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/unit/regeditInfo.mail">注册信息<span class="arrow_right"></span></a>
				</li>
			</ul>
		</div>
		<div class="right">
			<div class="contentright">
				<h1>修改密码</h1>
				<div class="logoright" style="height:130px;border-bottom:1px dashed #DFDFDF;">
					<form action="/member/updatePassword.mail" method="post" id="updatePasswordForm">
						<p>
							<span class="sp1">旧密码</span>
							<span class="sp2"><input type="password" name="ypassword" id="ypassword" class="pwd1" /></span>
							<span class="sp3" id="msg1"></span>
						</p>
						<p>
							<span class="sp1">新密码</span>
							<span class="sp2"><input type="password" name="password" id="password" class="pwd1" /></span>
							<span class="sp3" id="msg2"></span>
						</p>
						<p>
							<span class="sp1">确认密码</span>
							<span class="sp2"><input type="password" name="repassword" id="repassword" class="pwd1" /></span>
							<span class="sp3" id="msg3"></span>
						</p>
						<p>
							<input type="button" name="updatePasswordBtn" id="updatePasswordBtn" onclick="updatePassword()" class="button" value="保存修改" />
						</p>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">
		<p>© 2013 Danwei Mail. All Rights Reserved</p>
	</div>
</div>
</body>
</html>