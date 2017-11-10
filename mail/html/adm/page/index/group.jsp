<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/group.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/group.js"></script>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="left structManage">
			<div class="c_nav">
				<a class="" href="/unit/getUserByUnit.mail?id=1">
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
				<div class="con_head">
					<div class="rightleft">
						<c:if test="${ empty list }"><h2 id="structPath" class="title">暂无邮件群组</h2></c:if>
						<c:if test="${ not empty list }">
						<h2 id="structPath" class="title">${groups.name }</h2>
						<div id="structNode" class="t_tool">
							<a class="btn" onclick="$('#mlistdiv').show();">
								<span class="ico_edit" style="display:none;"></span>
								<span class="arrow"></span>
							</a>
						</div>
						<div class="m_list" id="mlistdiv">
							<ul>
								<li><a href="/groups/toUpdateGroup.mail?id=${groups.id }">编辑</a></li>
								<li><a href="javascript:if(window.confirm('确认删除吗？'))location.href='/groups/deleteGroups.mail?id=${groups.id }'">删除</a></li>
							</ul>
						</div>
						</c:if>
					</div>
					<div class="tool_box">
						<a id="btnAddMbr" class="btn_add" href="/groups/toAddGroup.mail?number=${number }">
							<span class="ico_add"></span> 新建群组
						</a>
					</div>
				</div>
				<div class="con_body">
					<c:if test="${ not empty list }">
						<div class="button">
							<input type="checkbox" name="ids" value="" id="memberids" />
							<span class="btnspan" onclick="ycGroupsFun()">移出群组</span>
						</div>
						<div class="bodylist">
							<form action="/groups/ycGroups.mail" name="ycGroups" id="ycGroups">
							<input type="hidden" name="id" value="${groups.id }"  />
							<ul>
								<c:if test="${ empty list2 }">
									<li>群组暂无单位人员信息</li>
								</c:if>
								<c:if test="${ not empty list2 }">
								<s:iterator value="#request.list2" id="lt2" status="st2">
								<li>
									<c:if test="${type eq 1 }">
										<input type="checkbox" name="unitsids" value="${id }" class="unitscla" />
										<span><label></label>${name }</span>
									</c:if>
									<c:if test="${type eq 2 }">
										<input type="checkbox" name="usersids" value="${id }" class="usersla" />
										<span class="listspan3"><label class="l1"></label>${name }</span>
										<span>${username }</span>
									</c:if>
								</li>
								</s:iterator>
								</c:if>
							</ul>
							</form>
						</div>
						<c:if test="${not empty list2}">
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