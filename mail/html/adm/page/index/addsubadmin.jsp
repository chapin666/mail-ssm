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
<script type="text/javascript" src="/adm/js/addsubadmin.js"></script>
</head>
<body>
<div class="main">
	<div class="content" style="min-height:860px;">
		<div class="left structManage" style="min-height:860px;">
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
						<h2 id="structPath" class="title">新增分级管理员</h2>
					</div>
					<p class="t_info">指定某个成员为分级管理员，让其具有部分管理权限。</p>
				</div>
				<div class="con_body">
					<div class="addbodylist">
						<form action="/member/doAddMember.mail" method="post" name="addSubAdminForm" id="addSubAdminForm">
						<ul> 
							<li>
								<span class="span1">分级管理员姓名：</span>
								<span><input type="text" name="member.name" id="name" value="" class="inputname" /></span>
								<span class="msg" id="msg1"></span>
							</li>
							<li>
								<span class="span1">用户名：</span>
								<span><input type="text" name="member.username" id="username" value="" class="inputname" /></span>
								<span class="msg" id="msg2"></span>
							</li>
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
							<li>
								<span class="span1">联系电话：</span>
								<span><input type="text" name="member.phone" id="phone" value="" class="inputname" /></span>
								<span class="msg" id="msg5"></span>
							</li>
							<li>
								<span class="span1">操作权限：</span>
								<span>(请勾选分级管理员可进行操作的功能)</span>
								<input type="hidden" name="member.type" id="type" value="" />
							</li>
							<li>
								<span class="span1">&nbsp;</span>
								<span>
									<input type="checkbox" name="" id="" value="成员与群组" class="boxname" />
									<label><font>成员与群组</font>(增加、修改及删除邮件成员与群组)</label>
								</span>
							</li>
							<li>
								<span class="span1">&nbsp;</span>
								<span>
									<input type="checkbox" name="" id="" value="我的单位" class="boxname" />
									<label><font>我的单位</font>(域名及LOGO和密码等信息的管理)</label>
								</span>
							</li>
							<li>
								<span class="span1">&nbsp;</span>
								<span>
									<input type="checkbox" name="" id="" value="系统日志" class="boxname" />
									<label><font>系统日志</font>(系统日志的查看)</label>
								</span>
							</li>
							<li>
								<span class="span1">&nbsp;</span>
								<span><a name="glfw"></a>
									<input type="checkbox" name="" id="" value="单位公告" class="boxname" />
									<label><font>单位公告</font>(发送内部信息)</label>
								</span>
							</li>
							<li>
								<span class="span1">管理范围：</span>
								<span>(请选择允许分级管理员管理的部门)</span>
							</li>
							<li style="height: 260px;">
								<span class="span1">&nbsp;</span>
								<div style="float:left;">
									<ul class="lxrList" style="height:248px;">
										<li class="title"><b>单位地址本</b></li>
										<li>
											<ul id="lxrinfo" style="height:218px;">
												<li class="list">
													<ul>
														<li style="height:auto;">
															<script type="text/javascript">
																d = new dTree('d');
																d.add('0','-1','','','','');
																var i = -2;
															</script>
														 	<s:iterator value="#request.unitlist" id="unl">
													 			<script type="text/javascript">
														 			var lenn = '${fn:length(unl.name)}';
																	var lenname = '${unl.name}';
																	if(lenn>7){
																		lenname = '${fn:substring(unl.name, 0, 7)}...';
																	}
														 			d.add('${unl.id}','${unl.pid}','<input type="checkbox" class="unitdel" value="${unl.id}" lang="${unl.name}" onclick="getUnitMember(this);"> '+lenname+'','#glfw','','','','','');
													 			</script>
															</s:iterator>
															<script type="text/javascript">
																document.write(d);
																$('.dTreeNode:eq(0)').hide();
															</script>
														</li>
													</ul>
												</li>
											</ul>
											<ul id="lxrinfo1" style="display: none;"></ul>
										</li>
									</ul>
									<span class="ico_home"></span>
									<input type="hidden" name="member.units" id="range" value="" />
									<ul class="lxrList" style="height:248px;">
										<li class="title"><b>选择的对象</b></li>
										<li>
											<ul id="selelctObj" style="height:218px;">
											</ul>
										</li>
									</ul>
								</div>
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