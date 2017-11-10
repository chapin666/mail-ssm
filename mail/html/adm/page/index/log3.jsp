<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/log.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
<script type="text/javascript">
	$(function(){
		
	});
</script>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="left structManage">
			<ul class="sidetd">
				<li>
					<a href="/log/log!toList1.mail">邮件搜索<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/log/log!toList2.mail">登录记录<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/log/log!list3.mail" class="selected">操作记录<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/log/log!list4.mail">发件统计<span class="arrow_right"></span></a>
				</li>
			</ul>
		</div>
		<div class="right">
			<div class="con_box">
				<div class="con_head">
					<div class="rightleft">
						<h2 id="structPath" class="title">操作记录</h2>
					</div>
				</div>
				<form action="/log/log!list3.mail" method="post" id="pageForm">
					<input type="hidden" name="pagenow" id="pagenow" value="1">
				</form>
				<div class="con_body">
					<c:if test="${searchValue eq 1}">	
						<div class="bodylist">
							<ul>
								<li class="title">
									<span class="listspan51"><a >操作时间</a></span>
									<span class="listspan22"><a >操作类型</a></span>
									<span class="listspan2"><a >操作人员</a></span>
									<span class="listspan2">相关数据</span>
								</li>
								<c:if test="${empty logList}">
								<li><p style="text-align:center;">没有相关记录</p></li>
								</c:if>
								<c:if test="${not empty logList}">
								<s:iterator value="#request.logList" id="loglist">
								<li>
									<span class="listspan51">${loglist.time}</span>
									<span class="listspan22">${loglist.types}</span>
									<span class="listspan2">${loglist.username}</span>
									<span class="listspan2">${loglist.odata}</span>
								</li>
								</s:iterator>
								</c:if>
							</ul>
						</div>
						<c:if test="${not empty logList}">
						<div class="page">
							<span style="float:left;margin-left:20px;">共<font color="red">${totalsize}</font>条</span>
							<span style="float:right;margin-right:20px;">
								<font color="red">${pagenow}</font>/${pagenum} 页 
								<c:if test="${pagenum>1}">
									${pagebar}
								</c:if>
							</span>	
						</div>
						</c:if>
					</c:if>
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