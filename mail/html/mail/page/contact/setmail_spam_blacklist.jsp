<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/mail/css/gray/setmail.css" rel="stylesheet" type="text/css" />
<style type="text/css">
a{
text-decoration: none;
}
a:HOVER {
text-decoration: underline;
}
p{
font-size: 13px;
padding-top: 10px;
}
</style>

</head>
<body>


	<!-- 反垃圾 -->
	<div class="layout">
		<div style="font-size:14px;border-bottom-width: 2px;border-bottom: 2px solid #EAEAEA;font-weight: bold;padding-bottom: 3px;">
			黑名单
		</div>
		<div style="min-height: 50px;padding-left: 60px;padding-top: 10px; padding-bottom: 10px;">
			<a href="./spam_blacklist_list.html" target="spam_blacklist"> 设置邮件地址黑名单 </a>
			<br/>
			<p>(如果您不希望收到某人的邮件，您可以把他的邮件地址加入到黑名单。)</p>
			<br/>
			<a href="./spam_blacklist_domain_list.html" target="spam_blacklist"> 设置域名黑名单 </a>
			<br/>
			<p>(例：在域名黑名单中设置"example.com"，拒收来自该站点的邮件。)</p>
		</div>
		
		<div style="font-size:14px;border-bottom-width: 2px;border-bottom: 2px solid #EAEAEA;font-weight: bold;padding-bottom: 3px;">
			白名单
		</div>
		<div style="min-height: 50px;padding-left: 60px;padding-top: 10px; padding-bottom: 10px;">
			<a href="./spam_whitelist_list.html" target="spam_blacklist"> 设置邮件地址白名单 </a>
			<br/>
			<p>(来自邮件地址白名单中联系人的邮件，仅受用户自定义规则影响，确保您能收到他的邮件。)</p>
			<br/>
			<a href="./spam_whitelist_domain_list.html" target="spam_blacklist"> 设置域名白名单 </a>
			<br/>
			<p>(例：在域名白名单中设置"126.com"，来自该站点的邮件仅受用户自定义规则影响，确保您收到"@126.com"发来的所有邮件。)</p>
		</div>
		
		<div class="d2-1"></div>
	</div>

</body>
</html>