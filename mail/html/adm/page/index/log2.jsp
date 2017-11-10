<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	String daydate = df.format(new Date()); // new Date()为获取当前系统时间
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/log.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
<script type="text/javascript" src="/mail/js/date.js"></script>
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
					<a href="/log/log!toList2.mail" class="selected">登录记录<span class="arrow_right"></span></a>
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
					<div class="rightleft" style="margin:0px;">
						<h2 id="structPath" class="title">登录记录</h2>
					</div>
					<p class="t_info">可查询管理员、成员最近一个月的记录</p>
				</div>
				<div class="searchtitle">
					<span class="on">按日期</span>
					<span onclick="location.href='/log/log!toList21.mail';">按帐号</span>
					<span onclick="location.href='/log/log!list22.mail';">管理员</span>
				</div>
				<div class="searchcontent" id="searchcontent0">
					<form action="/log/log!list2.mail" method="post" name="searchForm" id="pageForm">
					<input type="hidden" name="pagenow" id="pagenow" value="1">
					<p>
						<span>时间范围：</span>
						<input type="text" name="time" id="time"  value="${logger.time }" onfocus="c.showMoreDay=true;c.show(this);" class="input" />
						<label>&nbsp;&nbsp;至&nbsp;&nbsp;<%=daydate %>　&nbsp;</label>
						<label>
							<input type="submit" name="searchBtn" id="searchBtn" value="查询" class="btns" />
						</label>
						<input type="hidden" name="day" id="day" value="${logger.day }" />
					</p>
					<p>
						<label style="margin-left:60px;">快速查询：
							<a href="/log/log!list2.mail?day=7">7天</a>
							<a href="/log/log!list2.mail?day=30">30天</a>
							<a href="/log/log!list2.mail?day=180">180天</a>
						</label>
					</p>
					</form>
				</div>
				<div class="con_body">
					<c:if test="${searchValue eq 1}">	
						<div class="bodylist">
							<ul>
								<li class="title">
									<span class="listspan111">最后登录时间</span>
									<span class="listspan2">成员</span>
									<span class="listspan2">最后登录ip</span>
									<span class="listspan1">近期登陆详情</span>
								</li>
								<!-- 
								<li style="margin:0px;padding:0px;">
									<p style="padding-left:20px;background:#DDD;color:#333333;">今天</p>
								</li>
								-->
								<c:if test="${empty logList}">
								<li><p style="text-align:center;">没有相关记录</p></li>
								</c:if>
								<c:if test="${not empty logList}">
								<s:iterator value="#request.logList" id="loglist">
								<li>
									<span class="listspan111">${loglist.time}</span>
									<span class="listspan2">${loglist.username}</span>
									<span class="listspan2">${loglist.ips}</span>
									<span class="listspan1"><a href="/log/log!list21.mail?username=${loglist.username}">详情</a></span>
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