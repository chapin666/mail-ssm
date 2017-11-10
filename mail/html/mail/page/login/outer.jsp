<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
  String name = request.getParameter("username");
  String pass = request.getParameter("password");
  System.out.println(name + "-" + pass);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mail/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<title>单位邮箱用户登录</title>

<style type="text/css">
body{ TEXT-ALIGN: center; }
#center{
MARGIN-RIGHT: auto;
MARGIN-LEFT: auto;
height:200px;
width:400px;
vertical-align:middle;
line-height:200px;
}
</style>

<script type="text/javascript">
	function initInfo(){
		if($("#username").val().length<1){
			$("#error").html("<font color='red'>邮箱用户名为空</font>");
		}else if($("#password").val().length<1){
			$("#error").html("<font color='red'>邮箱密码为空</font>");
		}else{
			
			$.ajax({
				type:'post',
				url:'/user/checkUsernameExist.html',
				data:{username:$("#username").val()},
				dataType: 'html',
				async: false,
				error:function(){
					$("#error").html("<font color='red'>数据库连接出错</font>");
				},
				success:function(data){
					if(data==1){
				    	$.ajax({
							type:'post',
							url:'/mail/user/checkUserInterface',
							data:{username:$("#username").val(),password:$("#password").val()},
							dataType: 'html',
							async: false,
							error:function(){
								$("#error").html("<font color='red'>数据库连接出错</font>");
							},
							success:function(data){
								if(data==2){
							    	$("#error").html("<font color='red'>用户已冻结</font>");
					    		}else if(data==1){
							    	location.href='/index.html';
							    }else{
							    	$("#error").html("<font color='red'>密码错误</font>");
							    }
							}
						});
				    }else{
				    	$("#error").html("<font color='red'>用户名不存在</font>");
				    }
				}
			});
		}
	}
</script>
</head>
<body onload="initInfo()">
	<form action="" method="post" name="loginForm" id="loginForm">
		<label><input type="hidden" name="domainhost" id="domainhost" value="cddx.gov.cn"></label>
		<label><input type="hidden" name="username" id="username" value="<%=name %>" /></label>
		<label><input type="hidden" name="password" id="password" value="<%=pass %>" /></label>
	</form>
	<div id="center"><label id="error"></label></div>
</body>
</html>