<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/group.css" rel="stylesheet" type="text/css" />
<link href="/adm/css/tree2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/tree2.js"></script>
<script type="text/javascript" src="/adm/js/updategroup.js"></script>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="left structManage">
			<div class="c_nav">
				<a class="" href="/adm/member.mail">
					<span class="icon_diff icon_party"></span>组织架构
				</a>
				<a class="on bd_r" href="#">
					<span class="icon_diff icon_group"></span>邮件群组
				</a>
			</div>
			<div class="partytree">
				<div class="mailGroup_box">
					<div class="searchcon">
						<form action="/groups/list.mail" method="post" id="serachyjqzForm">
							<input class="t_search" type="text" name="name" value="${searchname }" onclick="if(this.value=='搜索邮件群组'){this.value='';}" onblur="if(this.value==''){this.value='搜索邮件群组';}" />
							<input class="btnSearch" id="serachyjqzBtn" type="submit" value="" />
						</form>
					</div>
				</div>
				<div id="mailGroupNav" class="mg_box">
					<ul>
						<c:if test="${ empty list }">
							<li>暂无邮件群组</li>
						</c:if>
						<c:if test="${ not empty list }">
							<% int i = 0; %>
							<s:iterator value="#request.list" id="lt" status="st">
								<%
								int number = Integer.parseInt((String)request.getAttribute("number"));
								if(i == number){
								%>
									<li class="on">
										<a class="item" title="${name }" href="/groups/list.mail?id=${id }&number=<s:property value="#st.index" />">${name }</a>
										<div class="btn_mg" title="相关操作">
											<span class="arrow" onclick="$('#mailGroupNavlistdiv<s:property value="#st.index" />').show();ligroup=<s:property value="#st.index" />;"></span>
										</div>
										<div class="m_list mailgroup" id="mailGroupNavlistdiv<s:property value="#st.index" />">
											<ul>
												<li onclick="location.href='/groups/toUpdateGroup.mail?id=${id }'"><a>编辑</a></li>
												<li onclick="if(window.confirm('确认删除吗？'))location.href='/groups/deleteGroups.mail?id=${id }'"><a>删除</a></li>
											</ul>
										</div>
									</li>
								<%}else{ %>
									<li>
										<a class="item" title="${name }" href="/groups/list.mail?id=${id }&number=<s:property value="#st.index" />">${name }</a>
										<div class="btn_mg" title="相关操作">
											<span class="arrow" onclick="$('#mailGroupNavlistdiv<s:property value="#st.index" />').show();ligroup=<s:property value="#st.index" />;"></span>
										</div>
										<div class="m_list mailgroup" id="mailGroupNavlistdiv<s:property value="#st.index" />">
											<ul>
												<li onclick="location.href='/groups/toUpdateGroup.mail?id=${id }'"><a>编辑</a></li>
												<li onclick="if(window.confirm('确认删除吗？'))location.href='/groups/deleteGroups.mail?id=${id }'"><a>删除</a></li>
											</ul>
										</div>
									</li>
								<%} i++; %>
							</s:iterator>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="con_box">
				<div class="con_head" style="border-bottom:1px dashed #D4D4D4;margin-bottom:10px;height:30px;">
					<div class="rightleft" style="margin:0px;">
						<h2 id="structPath" class="title">新建邮件群组</h2>
					</div>
				</div>
				<div class="con_body">
					<div class="addbodylist">
						<form action="/groups/doUpdateGroups.mail" method="post" name="addGroupsForm" id="addGroupsForm">
						<input type="hidden" name="groups.id" id="id" value="${groups.id }" />
						<ul> 
							<li>
								<span class="span1">组名：</span>
								<span><input type="text" name="groups.name" id="name" value="${groups.name }" class="inputname" /></span>
								<input type="hidden" name="groupsname" id="groupsname" value="${groups.name }" class="inputname" />
								<span class="msg" id="msg1"></span>
							</li>
							<li>
								<span class="span1">群发权限：</span>
								<span>
									<select name="groups.state" id="state" class="inputname">
										<option value="1">单位成员</option>
										<option value="2">组内成员</option>
									</select>
									<script type="text/javascript">$("#state").val('${groups.state }');</script>
								</span>
							</li>
							<li>
								<span class="span1">成员：</span>
								<span>(请选择此群组的成员)</span>
							</li>
							<li style="height:270px;">
								<span class="span1">&nbsp;</span>
								<div style="float:left;">
									<ul class="lxrList" style="height:248px;">
										<li class="title"><b>部门架构</b></li>
										<li>
											<ul id="lxrinfo" style="height:218px;">
												<li class="list">
													<ul id="typelist">
														<li style="height:auto;">
															<script type="text/javascript">
																d = new dTree('d');
																d.add('0','-1','','','','');
																var i = -2;
															</script>
														 	<s:iterator value="#request.unitlist" id="unl">
													 			<script type="text/javascript">
																	d.add('${unl.id}','${unl.pid}','<input type="checkbox" class="unitdel" value="${unl.id}" lang="${unl.name}" onclick="getUnitMember(this);"> ${unl.name}','#','','','','','');
																</script>
																<s:iterator value="#request.userlist" id="userl">
																	<c:if test="${userl.unid eq unl.id}">
																		<script type="text/javascript">
																			i--;
																			d.add(i,'${unl.id}','<a class="lxr" lang="unit_${unl.id}" id="lxr_${userl.username}@${sessionScope.host}" onclick="getUserMember(this,\'${userl.id}\');">${userl.name}</a>','','','','','','');
																		</script>
																	</c:if>
																</s:iterator>
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
									<input type="hidden" name="groupuser" id="groupuser" value="" />
									<input type="hidden" name="groupunit" id="groupunit" value="" />
									<ul class="lxrList" style="height:248px;">
										<li class="title"><b>选择的对象</b></li>
										<li>
											<ul id="selelctObj" style="height:218px;">
												<s:iterator value="#request.listGuss" id="listguss">
													<li class="obj1" lang="${id }">${name }</li>
												</s:iterator>
												<s:iterator value="#request.listGnss" id="listgnss">
													<li class="obj" lang="${id }">${name }</li>
												</s:iterator>
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