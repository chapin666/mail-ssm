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
	<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
	<link href="/adm/css/antiSpam.css" rel="stylesheet" type="text/css">
	<link href="/adm/css/subadmin.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/mail/js/jquery.js"></script>
	<script type="text/javascript" src="/mail/js/date.js"></script>
	<script type="text/javascript" src="/mail/js/page.js"></script>
	<style type="text/css">
		input{		
			font-size: 16px;
			height: 16px;
			width: 150px;			
		}
	.input {
	float: left;
	width: 118px;
	marker-offset: 0;
	height: 20px;
    line-height: 24px;
    font-size: 12px;
    background: #FFFFFF;
    border-color: #C0C0C0 #BFBFBF #BFBFBF;
    border-right: 1px solid #BFBFBF;
    border-style: solid;
    border-width: 1px;
    font-weight: lighter;
    outline: medium none;
    padding: 0 0 0 2px;
}
	</style>
</head>
<body>

<div class="main">
	<div class="content" style="height: 800px;">		
		<div class="left structManage" style="height: 800px;">
			<ul class="sidetd">
				<li>
					<a href="/log/log!toList1.mail">邮件搜索<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/log/log!toList2.mail">登录记录<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/log/log!list3.mail">操作记录<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/log/log!list4.mail" class="selected">发件统计<span class="arrow_right"></span></a>
				</li>
			</ul>
		</div>
		<div class="right">
			<div class="contentright">
				<h1>发件统计</h1>
				<div  align="right">
					<form action="/log/log!list4.mail" method="post"">	
			        <div>
			        	<span style="float: left;margin:5px 2px;">时间：</span>
				       	<span style="float: left;">
				       		<input type="text" name="startTime" id="times" value="${startTime}" class="input" onfocus="c.showMoreDay=true;c.show(this);"/>
				       		<label  style="float: left;margin:5px 2px;">&nbsp;&nbsp;至&nbsp;&nbsp;<%=daydate %>　&nbsp;</label>
				       	</span> 
				        <span><input type="submit"  value="查询" class="button1" style="margin:-2px 2px;height: 25px;float: left;"/></span>	
					</div>
					</form>
					<form action="/log/log!list4.mail" method="post" id="pageForm">
						<input type="hidden" name="pagenow" id="pagenow" value="1">
						<input type="hidden" name="startTime" value="${startTime}">
					</form>
					<br>
				<div class="con_body">
					<div class="bodylist" style="margin-top: 20px;">
						<ul>
							<li class="title" style="height: 30px;padding-top: 5px;">								
								<span class="listspan35" style="width: 150px; text-align:center; ">账号</span>
								<span class="listspan55" style="width: 250px; text-align:center;">域名</span>
								<span class="listspan55" style="width: 180px; text-align:center;">发件量</span>
								<span class="listspan45" style="width: 180px; text-align:center;">每天发件详情</span>
							</li>
							<c:if test="${empty sendCountList}">
							<li><p style="text-align:center;">没有相关记录</p></li>
							</c:if>
							<c:if test="${not empty sendCountList}">
							<s:iterator value="#request.sendCountList" id="memberlist" status="st">
							<li>								
								<span class="listspan35" style="width: 150px; text-align:center;">${frommailList[st.index]}</span>
								<span class="listspan55" style="width: 250px; text-align:center;">${domainList[st.index]}</span>								
								<span class="listspan55" style="width: 180px; text-align:center;">${count}</span>
								<span class="listspan45" style="width: 180px; text-align:center;">
									<a href="/log/log!getSendName.mail?frommail=${frommail}">发件详情</a>
								</span>
							</li>
							</s:iterator>
							</c:if>
						</ul>
					</div>
					<c:if test="${not empty totalsize}">
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
				</div>
					
				
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