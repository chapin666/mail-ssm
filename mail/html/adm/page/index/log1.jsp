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
<script type="text/javascript" src="/mail/js/date.js"></script>
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
					<a href="/log/log!toList1.mail" class="selected">邮件搜索<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/log/log!toList2.mail">登录记录<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/log/log!list3.mail">操作记录<span class="arrow_right"></span></a>
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
						<h2 id="structPath" class="title">邮件搜索</h2>
					</div>
				</div>
				<div class="search">
					<form action="/log/log!list1.mail" method="post" name="searchForm" id="pageForm">
						<span>帐号：</span>
						<span>主题：</span>
						<span>类型：</span>
						<span>开始日期：</span>
						<span>结束日期：</span>
						<span class="ay"> </span>
						<input type="hidden" name="pagenow" id="pagenow" value="1">
						<input type="text" name="ftmail" id="ftmail" value="${ftmail}" />
						<input type="text" name="title" id="title" value="${title}" />
						<select name="types" id="types">
							<option value="">全部</option>
							<option value="2">收信</option>
							<option value="3">发信</option>
						</select>
						
						<script type="text/javascript">$('#types').val('${searchValue}');</script>
						
						<input type="text" name="times" id="times" value="${times }" onfocus="c.showMoreDay=true;c.show(this);"/>
						<input type="text" name="timex" id="timex" value="${timex }" onfocus="c.showMoreDay=true;c.show(this);"/>
						<input type="submit" name="searchBtn" id="searchBtn" value="查询" class="btn" />
					</form>
				</div>
				<div class="con_body">
					<c:if test="${searchValue eq 1 || searchValue eq 2 || searchValue eq 3}">
						<div class="bodylist">
							<ul>
								<li class="title">
									<span class="listspan11" style="text-align: center;">类型</span>
									<span class="listspan2" style="text-align: center;">主题</span>
									<span class="listspan32" style="text-align: center;">发件人</span>
									<span class="listspan32" style="text-align: center;">收件人</span>
									<span class="listspan32" style="text-align: center;">时间</span>
									<span class="listspan33" style="text-align: center;">状态</span>
								</li>								
								<c:if test="${empty logList}">
									<li><p style="text-align:center;">没有相关记录</p></li>
								</c:if>
							
								<c:if test="${searchValue eq 1 || searchValue eq 2 || searchValue eq 3}">	
													
									<c:if test="${not empty logList}">								
										<s:iterator value="#request.logList" id="email">
																						
											<li>
												<c:if test="${searchValue eq 2}">
													<span class="listspan11" style="text-align: center;">收信</span>
												</c:if>
												<c:if test="${searchValue eq 3}">
													<span class="listspan11" style="text-align: center;">发信</span>
												</c:if>
												<c:if test="${searchValue eq 1}">
													<c:if test="${email.type eq 1}">
														<span class="listspan11" style="text-align: center;">收信</span>
													</c:if>
													<c:if test="${email.type eq 2}">
														<span class="listspan11" style="text-align: center;">发信</span>
													</c:if>
												</c:if>
												<span class="listspan2" style="text-align: center;" title="${email.subject}">
													<c:if test="${fn:length(email.subject)>30}">${fn:substring(email.subject, 0, 30)}...</c:if>
													<c:if test="${fn:length(email.subject)<=30}">${email.subject}</c:if>												
												</span>
												<span class="listspan32" style="text-align: center;" title="${email.frommail}">															
													${email.fromname}	
												</span>
												<span class="listspan32" style="text-align: center;" title="${email.tomail}">
													${email.toname}
												</span>
												<span class="listspan32" style="text-align: center;">${email.time}</span>
												<span class="listspan33" style="text-align: center;">${email.state}</span>
											</li>
										</s:iterator>
									</c:if>
								</c:if>
								
								
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
							</ul>
						</div>
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