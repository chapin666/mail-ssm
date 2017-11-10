<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/subadmin.css" rel="stylesheet" type="text/css" />
<link href="/adm/css/tree2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/tree2.js"></script>
<script type="text/javascript" src="/adm/js/updatesubadminpassword.js"></script>
</head>
<body>
<div class="main">
	<div class="content" style="min-height:700px;">
		<div class="left structManage" style="min-height:700px;">
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
		<div class="right" style="min-height:680px;">
			<div class="con_box">
				<div class="con_head" style="border-bottom:1px dashed #D4D4D4;margin-bottom:10px;">
					<div class="rightleft" style="margin:0px;">
						<h2 id="structPath" class="title">分级管理员重置密码</h2>
					</div>
					<p class="t_info">指定某个成员为分级管理员，让其具有部分管理权限。</p>
				</div>
				<div class="con_body">
					<div class="addbodylist">
						<form action="/member/doEditMember.mail" method="post" name="addSubAdminForm" id="addSubAdminForm">
						<input type="hidden" name="member.id" id="id" value="${id }" />
						<ul> 
							<li>
								<span class="span1">用户密码：</span>
								<span><input type="password" name="member.pass" id="pass" value="" class="inputname" /></span>
								<span class="msg" id="msg3"></span>
							</li>
							<li>
								<span class="span1">确认密码：</span>
								<span><input type="password" name="member.password" id="password" value="" class="inputname" /></span>
								<span class="msg" id="msg4"></span>
							</li>
						</ul>
						<p>
							<input type="button" id="updateSubAdminBtn" onclick="updateSubAdmin()" class="buttonsub" value="确定" />
						</p>
						</form>
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