<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/adm/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/adm/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/login.js"></script>
<title>单位邮箱管理员登录</title>
</head>
<script language="JavaScript"> 
if (window != top) 
top.location.href = location.href; 

	function Center() {
		var h1 = document.documentElement.clientHeight;
		var w1 = document.documentElement.clientWidth;
		if (w1 > 1600) {
			document.getElementById("top").style.marginLeft = "-800px";
			document.getElementById("top").style.left = "50%";
			document.getElementById("top").style.width = "1600px";
		} else {
			document.getElementById("top").style.marginLeft = "0";
			document.getElementById("top").style.left = "0";
			document.getElementById("top").style.width = "100%";
		}
		if (h1 >= 600) {
			document.getElementById("top").style.position = "absolute";
			document.getElementById("top").style.top = "50%";
			document.getElementById("top").style.marginTop = "-310px";
		} else {
			document.getElementById("top").style.position = "relative";
			document.getElementById("top").style.top = "0";
			document.getElementById("top").style.marginTop = "0px";
		}
	}
</script>

<body onresize="Center()" onload="Center()" style="padding-top: 0px;">
	<div id="top" class="top"
		style="margin-left: -800px; left: 50%; width: 1600px; position: relative; top: 0px; margin-top: 0px;">
		<div class="head">
			<img src="/adm/images/index/logo.png?t=<%=new Date() %>" width="196"
				height="37" border="0" />
		</div>

		<div class="center">
			<div class="content">
				<div class="right">
					<div class="login">
						<form action="" method="post" name="loginForm" id="loginForm">
							<h1>邮件系统管理员登陆</h1>
							<ul>
								<li>
									<label>帐 号：</label> 
									<label>
										<input type="text"name="username" id="username" value="" />
									</label> 
								</li>

								<li><span id="error">请填写邮件系统管理员账号。</span></li>
								<li style="margin:15px 0px 15px;"><label>密 码：</label> <label><input
										type="password" name="password" id="password" value=""
										onkeydown="if((event||window.event).keyCode&&(event||window.event).keyCode==13){$('#loginBtn').click();}" />
								</label></li>
								<li style="height: 60px;"><input type="button" value=""
									name="loginBtn" id="loginBtn" class="loginBtn"
									onclick="login();" /> <input type="button" value=""
									name="resetBtn" id="resetBtn" class="resetBtn"
									onclick="resets();" /></li>
								<li class="ad">欢迎使用邮件系统</li>
							</ul>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div class="foot">
			<p>中国核动力研究设计院 </p><p>技术支持：中国核动力研究设计院信息中心</p>
		</div>
	</div>
</body>
</html>
</html>