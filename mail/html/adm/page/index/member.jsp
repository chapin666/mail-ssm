<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/member.css" rel="stylesheet" type="text/css" />
<link href="/adm/css/tree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/tree.js"></script>
<script type="text/javascript" src="/adm/js/compatible.js"></script>
<script type="text/javascript" src="/adm/js/unit.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
</head>
<body>
	<c:if test="${disable == 2}">
		<form action="/unit/getUserByUnit.mail" method="post" id="pageForm">
		  	<input type="hidden" name="pagenow" id="pagenow" value="${pagenow}">
			<input type="hidden" name="name" value="${name}">
			<input type="hidden" name="disable" value="${disable}">		
		</form>
	</c:if>
	<c:if test="${disable == 0}">
		<form action="/unit/getUserByUnit.mail" method="post" id="pageForm">
	  	<input type="hidden" name="pagenow" id="pagenow" value="${pagenow}">
		<input type="hidden" name="name" value="${name}">
	</form>
	</c:if>
<div class="msgBoxDIV">
	<span class="errmsg">未选中任何记录</span>
</div>
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
		<div class="createlabel" id="unitrename">
			<div class="createlabel_head" style="cursor:move;">
				<span id="unitrenametitle">单位部门重命名</span>
				<a class="createlabel_close" href="javascript:;" onclick="$('#unitrename').hide()" title="关闭"></a>
			</div>
			<form action="/unit/doEditUnit.mail" method="post" id="updateUnitForm">
			<div class="createlabel_body">
				<p>请您输入部门名称</p>
				<div style="margin:8px 0 0;">
					<input type="text" class="createlabel_input" name="unitname" id="unitname" value="${unit.name }" />
					<input type="hidden" name="unitid" id="unitid" value="${unit.id }" />
				</div>
			</div>
			</form>
			<div class="createlabel_bottom">
				<input type="button" class="btn_blue" value="确定" onclick="updateUnitAction();"/>
				<input type="button" class="btn_gray" value="取消" onclick="$('#unitrename').hide()"/>
			</div>
		</div>
		<div class="createlabel" id="moveunitlabel">
			<div class="createlabel_head" style="cursor:move;">
				<span>移动到部门</span>
				<a class="createlabel_close" href="javascript:;" onclick="$('#moveunitlabel').hide()" title="关闭"></a>
			</div>
			<form action="" method="post" id="moveunitlaForm">
			<div class="createlabel_body" style="height:76px;padding:20px 30px;">
				<p>请您选择部门</p>
				<div style="margin:8px 0 0;">
					<span id="layer">
						<select id="parentid" onchange="showChild();" class="inputname1">
							<option value="-1">---请选择---</option>
							<option value="1">--我的单位--</option>
						</select>
					</span>
					<input type="hidden" name="userunid" id="parent_id" value="-1">
				</div>
			</div>
			</form>
			<div class="createlabel_bottom">
				<input type="button" class="btn_blue" value="确定" onclick="moveUnitAction();"/>
				<input type="button" class="btn_gray" value="取消" onclick="$('#moveunitlabel').hide()"/>
			</div>
		</div>
		<div class="createlabel" id="movegroupslabel">
			<div class="createlabel_head" style="cursor:move;">
				<span>添加到邮件群组</span>
				<a class="createlabel_close" href="javascript:;" onclick="$('#movegroupslabel').hide()" title="关闭"></a>
			</div>
			<form action="" method="post" id="movegroupsForm">
			<div class="createlabel_body" style="height:76px;padding:20px 30px;">
				<p>请您选择邮件群组</p>
				<div style="margin:8px 0 0;">
					<span>
					<select id="groupsids" class="inputname1">
						<option value="">---请选择---</option>
						<s:iterator value="#request.listgroups" id="gpid">
							<option value="${id }">${name }</option>
						</s:iterator>
					</select>
					</span>
				</div>
			</div>
			</form>
			<div class="createlabel_bottom">
				<input type="button" class="btn_blue" value="确定" onclick="moveGroupsAction();"/>
				<input type="button" class="btn_gray" value="取消" onclick="$('#movegroupslabel').hide()"/>
			</div>
		</div>
		<div class="right">
			<div class="con_box">
				<div class="con_head">
					<div class="rightleft">
						<input type="hidden" name="dyunitname" id="dyunitname" value="${unit.name }" />
						<input type="hidden" name="dyunitid" id="dyunitid" value="${unit.id }" />
						<c:if test="${ empty unitlist }"><h2 id="structPath" class="title">暂无单位信息</h2></c:if>
						<c:if test="${ not empty unitlist }">
							<h2 id="structPath" class="title">${unit.name }</h2>
							<div id="structNode" class="t_tool">
								<a class="btn" onclick="$('#mlistdiv').show();">
									<span class="ico_edit" style="display:none;"></span>
									<span class="arrow"></span>
								</a>
							</div>
							<div class="m_list" id="mlistdiv">
								<ul>
									<li onclick="showRename()"><a>重命名</a></li>
									<li onclick="showCreate()"><a>新建部门</a></li>
									<c:if test="${unit.id != 1}">
										<li onclick="deleteUnit('${unit.id }')"><a>删除</a></li>
									</c:if>
								</ul>
							</div>
						</c:if>
					</div>
					<div class="tool_box">
						<div class="searchcon">
							<form action="/unit/getUserByUnit.mail" method="post" id="serachUnitForm">
								<input class="txt" type="text" name="searchname" value="${searchname }" onclick="if(this.value=='搜索成员'){this.value='';}" onblur="if(this.value==''){this.value='搜索成员';}" />
								<input type="hidden" name="id" value="${unit.id }" />
								<input class="btnSearch" id="serachUnitBtn" type="submit" value="" />
							</form>
						</div>
						<a id="btnAddMbr" class="btn_add" href="/user/addAdmUser.mail">
							<span class="ico_add"></span> 新增成员
						</a>
						<!--<a id="btnImportMbr" class="btn_add" href="#">批量导入</a>-->
					</div>
					<c:if test="${disable == 0}">
						<p class="t_info">成员
							<c:if test="${ empty userlist }">0</c:if>
							<c:if test="${ not empty userlist }">${totalsize}</c:if>个
							<c:if test="${stopState > 0}">,<a href="/unit/getUserByUnit.mail?id=${unit.id}&disable=2"><font color="#0033FF">禁用${stopState}个</font></a></c:if>
						</p>
					</c:if>
					
					<c:if test="${disable == 2}">
						<p class="t_info">							
							<c:if test="${stopState > 0}">禁用成员${stopState}个</c:if>
						</p>
					</c:if>
					
				</div>
				<div class="con_body">
					<c:if test="${ not empty unitlist }">
						<div class="button">
							<input type="checkbox" name="ids" value="" id="unitids" />
							<span class="btnspan" onclick="moveUnit()">移动到部门<label></label></span>
							<span class="btnspan" onclick="moveGroups()">添加到邮件群组<label></label></span>
							<span class="btnspan" onclick="updateState(2)">禁用</span>
							<span class="btnspan" onclick="updateState(1)">启用</span>
							<span class="btnspan" onclick="deleteUser()">删除</span>
						</div>
						<div class="bodylist">
							<form action="" method="post" id="checkunitform">
							<input type="hidden" id="userunidform" name="unid" value="" />
							<input type="hidden" name="pagenow"  value="${pagenow}">
							<ul>
								<c:if test="${ empty userlist }">
									<li>部门内暂无人员信息</li>
								</c:if>
								<c:if test="${ not empty userlist }">
									<c:if test="${disable == 0}">
										<s:iterator value="#request.userlist" id="lt2" status="st2">
											<c:if test="${state == 2 }">
												<li>
													<input type="checkbox" name="unitids" value="${id }" class="unitidclass" style="color: #A0A0A0;"/>
													<span class="listspan1" onclick="location.href='/user/editAdmUser.mail?id=${id}'" style="color: #A0A0A0;"><label class="label2"></label>${name}</span>
													<span class="listspan2" onclick="location.href='/user/editAdmUser.mail?id=${id}'" style="color: #A0A0A0;">${username}@${domainname}</span>
													<span class="listspan3" onclick="location.href='/user/editAdmUser.mail?id=${id}'" style="color: #A0A0A0;">${unitname}</span>
												</li>
											</c:if>
											
											<c:if test="${state == 1 }">
												<li>
													<input type="checkbox" name="unitids" value="${id }" class="unitidclass" />
													<span class="listspan1" onclick="location.href='/user/editAdmUser.mail?id=${id}'"><label></label>${name}</span>
													<span class="listspan2" onclick="location.href='/user/editAdmUser.mail?id=${id}'">${username}@${domainname}</span>
													<span class="listspan3" onclick="location.href='/user/editAdmUser.mail?id=${id}'">${unitname}</span>
												</li>
											</c:if>
										
										</s:iterator>								
									</c:if>								
								
									<c:if test="${disable == 2}">
										<s:iterator value="#request.userlist" id="lt2" status="st2">
											<c:if test="${state == 2 }">
												<li>
													<input type="checkbox" name="unitids" value="${id }" class="unitidclass" style="color: #A0A0A0;"/>
													<span class="listspan1" onclick="location.href='/user/editAdmUser.mail?id=${id}'" style="color: #A0A0A0;"><label class="label2"></label>${name}</span>
													<span class="listspan2" onclick="location.href='/user/editAdmUser.mail?id=${id}'" style="color: #A0A0A0;">${username}@${domainname}</span>
													<span class="listspan3" onclick="location.href='/user/editAdmUser.mail?id=${id}'" style="color: #A0A0A0;">${unitname}</span>
												</li>
											</c:if>										
										</s:iterator>								
									</c:if>				
								
								</c:if>
							</ul>
							</form>
						</div>
						<c:if test="${not empty userlist}">
						<div class="page">
							<span style="float:left;margin-left:20px;">共<font color="red">${totalsize}</font>条</span>
							<span style="float:right;margin-right:20px;">
								<font color="red">${pagenow}</font>/${pagenum} 页 
								<c:if test="${pagenum>1}">
									${pagebar} &nbsp;&nbsp;跳转到&nbsp;<input id="jumptopage" size="5" />
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