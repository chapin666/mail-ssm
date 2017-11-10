<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		var userlimt = '${sessionScope.member.type}';
		if(userlimt != null && userlimt != ''){
			var userlimts = userlimt.split(",");
			for(var i=0; i<userlimts.length; i++){
				var limt = userlimts[i];
				if(limt == '成员与群组'){
					var html = '<li>';
					html += '<a class="btn_addmember" href="/user/addAdmUser.mail">';
					html += '<span class="ico_addMember"></span>&nbsp;添加成员';
					html += '</a>';
					html += '</li>';
					html += '<li>';
					html += '<a class="btn_mailGroup" href="/groups/list.mail">';
					html += '<span class="ico_mailGroup"></span>&nbsp;邮件群组';
					html += '</a>';
					html += '</li>';
					$(".main_function").append(html);
				}else if(limt == '我的单位'){
					var html = '<li>';
					html += '<a class="btn_mailList" href="/unit/toUnit.mail">';
					html += '<span class="ico_mailList"></span>我的单位';
					html += '</a>';
					html += '</li>';
					html += '<li>';
					html += '<a class="btn_groupmail" href="/unit/toUpdateHost.mail">';
					html += '<span class="ico_crmAddr"></span>&nbsp;域名管理';
					html += '</a>';
					html += '</li>';
					$(".main_function").append(html);
				}else if(limt == '系统日志'){
					var html = '<li>';
					html += '<a class="btn_entDisk" href="/log/log!dsAdd.mail">';
					html += '<span class="ico_entDisk"></span>&nbsp;系统日志';
					html += '</a>';
					html += '</li>';
					$(".main_function").append(html);
				}else if(limt == '单位公告'){
					var html = '<li>';
					html += '<a class="btn_notice" href="/emails/getNews.mail">';
					html += '<span class="ico_internalNotice"></span>&nbsp;单位公告';
					html += '</a>';
					html += '</li>';
					$(".main_function").append(html);
				}
			}
		}
	});
</script>
</head>
<body>
	<div class="main">
		<div class="content">
			<div class="homepage">
				<div class="left">
					<h1 class="welcome">欢迎您，${sessionScope.member.username}</h1>
					<ul class="main_function"></ul>
				</div>
				<div class="right">
					<div class="inforbar">
						<!-- 
						<h4>域名</h4>
						<div class="d_list">
							<a class="graytext" href="#">
								${sessionScope.host}
								<span class="co_domain" title="已验证"></span>
							</a>
						</div>
						-->
					</div>
					<div class="inforbar">
						<h4>成员</h4>
						<div class="d_list cy">
							<a class="b_size" href="/unit/getUserByUnit.mail?id=1"><b class="num">${num2}</b> 个成员</a>
							<br>
							<a class="b_size" href="/unit/getUserByUnit.mail?id=1"><b class="num">${num1}</b> 个部门</a>
							<br>
							<a class="b_size" href="/groups/list.mail"><b class="num">${num3}</b> 个邮件组</a>
						</div>
					</div>
					<div class="inforbar">
						<h4>LOGO</h4>
						<div class="d_list">
							<img src="/adm/images/index/logo.png?t=<%=new Date() %>" alt="单位邮箱" width="196" height="37" border="0" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<p>中国核动力研究设计院 </p>
		</div>
	</div>
</body>
</html>