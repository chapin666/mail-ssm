<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/top.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		var userlimt = '${sessionScope.member.type}';
		if(userlimt != null && userlimt != ''){
			var userlimts = userlimt.split(",");
			for(var i=0; i<userlimts.length; i++){
				var limt = userlimts[i];
				if(limt == '成员与群组'){
					$(".wd").append('<li><a href="/unit/getUserByUnit.mail?id=1" target="main">成员与群组</a></li>');
				}else if(limt == '我的单位'){
					$(".wd").append('<li><a href="/unit/toUnit.mail" target="main">我的单位</a></li>');
				}else if(limt == '系统日志'){
					$(".wd").append('<li><a href="/log/log!dsAdd.mail" target="main">系统日志</a></li>');
					$(".wd").append('<li><a href="/mailspams/mailspam.mail" target="main">反垃圾管理</a></li>');
				}else if(limt == '单位公告'){
					$(".wd").append('<li><a href="/emails/getNews.mail" target="main">单位公告</a></li>');
				}else if(limt == '邮箱过滤'){
					$(".wd").append('<li><a href="/adm/page/index/mailfilter1.jsp" target="main">邮箱过滤</a></li>');
				}
				
			}
			
		}
		$("ul.wd li a").click(function(){
			$("ul.wd li a").removeClass("on");
			$(this).addClass("on");
		});
	});
</script>
</head>
<body>
	<div class="main">
		<div class="content">
			<div class="logo">
				<a href="/unit/toUnit.mail" class="selected"  target="main">
					<img src="/adm/images/index/logo.png?t=<%=new Date() %>" width="196" height="37" border="0" />
				</a>				
			</div>
			<div class="right">
				您好，
				<a href="/member/editMember.mail?username=${sessionScope.member.username}" target="main">
					${sessionScope.member.username}
				</a>
				|
				<a onclick="top.window.location='/adm/exit.mail';">退出</a>
			</div>
		</div>
		<div class="nav">
			<ul class="wd">
				<li><a class="on" href="/adm/main.mail" target="main">首页</a></li>
				
				
			</ul>
		</div>
	</div>
</body>
</html>