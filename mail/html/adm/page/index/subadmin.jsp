<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/subadmin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
<script type="text/javascript">
	$(function(){
		
	});
</script>
</head>
<body>
<div class="main">
	<div class="content" style="min-height:780px;">
		<div class="left structManage" style="min-height:780px;">
			<ul class="sidetd">
				<li>
					<a href="/unit/toUnit.mail">单位信息<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/unit/toUpdateHost.mail">域名管理<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/member/toApdatePassword.mail">修改密码<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/member/getMemberList.mail" class="selected">分级管理员<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/unit/regeditInfo.mail">注册信息<span class="arrow_right"></span></a>
				</li>
			</ul>
		</div>
		<div class="right">
			<div class="con_box">
				<div class="con_head">
					<div class="rightleft">
						<h2 id="structPath" class="title">分级管理员</h2>
					</div>
					<div class="tool_box">
						<a id="btnAddMbr" class="btn_add" href="/emails/SendSubAdmin.mail" style="margin:0px;">
							<span class="ico_add"></span> 添加
						</a>
					</div>
				</div>
				<form action="/member/getMemberList.mail" method="post" name="searchForm" id="pageForm">
					<input type="hidden" name="pagenow" id="pagenow" value="1">
				</form>
				<div class="con_body">
					<div class="bodylist">
						<ul>
							<li class="title">
								<span class="listspan1" style="width: 100px;">姓名</span>
								<span class="listspan55" style="width: 100px;">用户名</span>
								<span class="listspan35" style="width: 320px;">权限</span>
								<span class="listspan45" style="width: 200px;">操作</span>
							</li>
							<c:if test="${empty memberlist}">
							<li><p style="text-align:center;">没有相关记录</p></li>
							</c:if>
							<c:if test="${not empty memberlist}">
							<s:iterator value="#request.memberlist" id="memberlist">
							<li>
								<span class="listspan1" style="width: 100px;">${name}</span>
								<span class="listspan55" style="width: 100px;">${username}</span>
								<span class="listspan35" style="width: 320px;">${type}</span>
								<span class="listspan45" style="width: 200px;">
									<a href="/member/editMember.mail?id=${id }">编辑</a>
									<a href="/member/editMemberPass.mail?id=${id }">重置密码</a>
									<a href="javascript:if(confirm('确实要删除吗？'))window.location='/member/deleteMember.mail?id=${id }'">删除</a>
								</span>
							</li>
							</s:iterator>
							</c:if>
						</ul>
					</div>
					<c:if test="${not empty memberlist}">
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
	<div class="footer">
		<p>© 2013 Danwei Mail. All Rights Reserved</p>
	</div>
</div>
</body>
</html>