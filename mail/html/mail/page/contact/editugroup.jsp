<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mail/css/gray/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/relation.js"></script>
<title>单位邮箱-添加联系人</title>
</head>
<body>
<div class="main">
	<div class="content" style="padding:30px 0px 0px 50px;">
		<form action="/contact/doAddContact.html" method="post" id="addcform">
		<input type="hidden" name="contact.userid" value="${sessionScope.user.id}" >
		<p>姓名：<input type="text" name="contact.name" id="name"></p><br/>
		<p>电子邮件：<input type="text" name="contact.addr" id="addr"></p><br/>
		<c:if test="${not empty ugrouplist}">
			<p>联系人组：
				<s:iterator value="#request.ugrouplist">
					<input type="checkbox" name="ugid" value="${id}">&nbsp;${name}
				</s:iterator>
			</p>
		</c:if>
		<br/>
		<p>
		<input type="button" value="提交" onclick="addcontact();">&nbsp;&nbsp;
		<input type="button" value="取消" onclick="location.href='/contact/getContactList.html'">
		</p>
		</form>
	</div>
</div>
</body>
</html>