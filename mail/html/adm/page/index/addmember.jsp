<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/member.css" rel="stylesheet" type="text/css" />
<link href="/adm/css/tree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/tree.js"></script>
<script type="text/javascript" src="/adm/js/compatible.js"></script>
<script type="text/javascript" src="/adm/js/member.js"></script>
<script type="text/javascript">
	$(function(){
		$("#mlistdiv").hover(
			function(){
				$('#mlistdiv').show();
			},
			function(){
				$('#mlistdiv').hide();
			}
		);
	});
</script>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="left structManage">
			<div class="c_nav">
				<a class="on bd_r" href="#">
					<span class="icon_diff icon_party"></span>组织架构
				</a>
				<a class="" href="/groups/list.mail">
					<span class="icon_diff icon_group"></span>邮件群组
				</a>
			</div>
			<div class="partytree">
				<script type="text/javascript">
					d = new dTree('d');
				</script>
			 	<s:iterator value="#request.unitlist" id="unl">
					<script type="text/javascript">
						d.add('${id}','${pid}','${name}','/unit/getUserByUnit.mail?id=${id}','','');
					</script>
				</s:iterator>
				<div class="dtree" align="left">
					<script type="text/javascript">
						document.write(d);
					</script>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="con_box">
				<div class="con_head" style="border-bottom:1px dashed #D4D4D4;margin-bottom:10px;height:30px;">
					<div class="rightleft" style="margin:0px;">
						<h2 id="structPath" class="title">新增成员</h2>
					</div>
				</div>
				<div class="con_body">
					<div class="addbodylist">
						<form action="/user/doAddAdmUser.mail" method="post" id="adduserform">
						<ul> 
							<li>
								<span class="span1">域名选择：</span>
								<span>
									<select id="checkDomain" class="inputname">
										<s:iterator value="#request.configList">									
											<option value="${value}">${value}</option>
										</s:iterator>
									</select>
								</span>
								<input type="hidden" id="domainhost" name="domain" value="" />								
							</li>
							<li>
								<span class="span1">成员名称：</span>
								<span><input type="text" name="user.name" id="name" value="" class="inputname" /></span>
								<span class="msg" id="msg1"></span>
							</li>
							<li>
								<span class="span1">用 户 名：</span>
								<span><input type="text" name="user.username" id="username" value="" class="inputname" /></span>
								<span class="msg" id="msg2"></span>
							</li>
							<li>
								<span class="span1">用户密码：</span>
								<span><input type="password" name="user.pass" id="pass" value="" class="inputname" /></span>
								<span class="msg" id="msg3"></span>
							</li>
							<li>
								<span class="span1">确认密码：</span>
								<span><input type="password" name="repass" id="password" value="" class="inputname" /></span>
								<span class="msg" id="msg4"></span>
							</li>
							<li>
								<span class="span1">成员部门：</span>
								<span>
									<span id="layer">
										<select id="parentid" onchange="showChild();" class="inputname1">
											<option value="-1">---请选择---</option>
											<option value="1">--我的单位--</option>
										</select>
									</span>
									<input type="hidden" name="user.unid" id="parent_id" value="-1">
								</span>
								<span class="msg" id="msg5"></span>
							</li>
							<li>
								<span class="span1">成员电话：</span>
								<span><input type="text" name="user.phone" id="phone" value="" class="inputname" /></span>
								<span class="msg" id="msg6"></span>
							</li>
							<li>
								<span class="span1">成员性别：</span>
								<span style="margin:7px 5px 0 0;"><input type="radio" name="user.sex" value="1" class="sex" checked></span>
								<span>男　</span>
								<span style="margin:7px 5px 0 0;"><input type="radio" name="user.sex" value="2" class="sex" ></span>
								<span>女</span>
							</li>
							<li>
								<span class="span1">成员状态：</span>
								<span style="margin:7px 5px 0 0;"><input type="radio" name="user.state" value="1" class="state" checked></span>
								<span>启用　</span>
								<span style="margin:7px 5px 0 0;"><input type="radio" name="user.state" value="2" class="state" ></span>
								<span>禁用</span>
							</li>
						</ul>
						<p>
							<input type="button" id="updateSubAdminBtn" onclick="addUser()" class="buttonsub" value="确定" />
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