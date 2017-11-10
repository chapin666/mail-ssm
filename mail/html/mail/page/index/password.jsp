<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/mail/css/gray/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/passowrd.js"></script>
</head>
<body>
<div class="msgBoxDIV">
	<span class="errmsg">密码修改成功，5秒后返回登录页面</span>
</div>
	<div class="main">
		<div class="content">
			<div class="contentright">
				<h1>修改密码</h1>
				<div class="logoright" style="height:130px;border-bottom:1px dashed #DFDFDF;">
					<form action="/user/editPass.html" method="post" id="updatePasswordForm">
						<p style="position: absolute;">
							<span class="sp1">旧密码</span>
							<span class="sp2"><input type="password" name="ypassword" id="ypassword" class="pwd1" /></span>
							<span class="sp3" id="msg1"></span>
						</p>
						<p style="position: absolute;margin-top: 40px;">
							<span class="sp1">新密码</span>
							<span class="sp2"><input type="password" name="password" id="password" class="pwd1" /></span>
							<span class="sp3" id="msg2"></span>
						</p>
						<p style="position: absolute;margin-top: 80px;">
							<span class="sp1">确认密码</span>
							<span class="sp2"><input type="password" name="repassword" id="repassword" class="pwd1" /></span>
							<span class="sp3" id="msg3"></span>
						</p>
						<p style="position: absolute;margin-top: 110px;">
							<input type="button" name="updatePasswordBtn" id="updatePasswordBtn" onclick="updatePassword()" class="buttonpwd" value="保存修改" />
						</p>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>