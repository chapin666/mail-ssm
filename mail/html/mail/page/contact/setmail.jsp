<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/mail/css/gray/main.css" rel="stylesheet" type="text/css" />
<link href="/mail/css/gray/setmail.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
<script type="text/javascript" src="/mail/js/move.js"></script>
<script type="text/javascript" src="/mail/js/setmail.js"></script>

</head>
<body>	
	<input type="hidden" id="signstate" value="${signstate}">
	<input type="hidden" id="sName" value="${sName}">
	<input type="hidden" id="signContent" value="${signContent}">
	<input type="hidden" id="signid" value="${signid}">
	
	<form action="/user/setmail.html" method="post" id="pageForm">
		<input type="hidden" name="pagenow" id="pagenow" value="1"> 
		<input type="hidden" name="name" value="${name}">
	</form>
	

	
	<div class="createlabel" id="tags">
		<div class="createlabel_head" style="cursor:move;">
			<span>新建标签</span> 
			<a class="createlabel_close" href="javascript:;" onclick="$('#tags').hide()" title="关闭"></a>
		</div>
		<form action="/user/addTag.html" method="post" id="addtagform">
			<div class="createlabel_body">
				<p>请您输入标签名称</p>
				<div style="margin:8px 0 0;">
					<input type="text" class="createlabel_input" name="tag.name" id="tagname" value="" />
					<input type="hidden" name="tag.userid" value="${sessionScope.user.id}" id="userid" /> 
					<input type="hidden" name="id" id="id" value="null" /> 
					<input type="hidden" name="edittag" id="edittag"/> 
					<span class="tagsp1" id="tagsp1" style="color: #FF0000;"></span>
				</div>
			</div>
		</form>
		<div class="createlabel_bottom">
			<input type="button" class="btn_blue" value="确定" onclick="addtag();" />
			<input type="button" class="btn_gray" value="取消" onclick="$('#tags').hide()" />
		</div>
	</div>

	<div id="txt_title">邮件设置</div>
	<div class="box">
		<div class="tagMenu">
			<ul class="menu">
				<li>标签</li>
				<li>账户</li>
				<li>反垃圾</li>
			</ul>
		</div>
		<!-- 标签 -->
		<div class="content">
			<div class="layout">
				<div>
					<table id="settingtable">
						<tr id="tableTitle" align="center">
							<td>标签名</td>
							<td>未读邮件</td>
							<td>总邮件</td>
							<td>操作</td>
						</tr>
						<s:iterator value="tagList" status="st">
							<tr class="tr2" align="center">
								<td align="left"><font class="btn_name">${name}</font></td>
								<td>${listonreadtag[st.index]}</td>
								<td>${listsumtag[st.index]}</td>								
								<td>
									<a onclick="edittag('${id}','${name}');">改名</a>&nbsp;&nbsp;									
									<a href="/user/deleteTag.html?id=${id}">删除</a>
								</td>								
							</tr>
						</s:iterator>
					</table>
					<!-- 分页 -->
					<c:if test="${not empty tagList}">
						<span class="page" style="margin-left: 1000px;">
							${pagenow}/${pagenum} 页 <c:if test="${pagenum>1}">
								${pagebar}
							</c:if> </span>
					</c:if>
					<!-- 新建标签 -->
					<div style="margin-top: 20px;margin-left: 10px;">
						<a class="btn_new" onclick="newTag();">新建标签</a>
					</div>
				</div>
					<div class="d2-1">
				</div>
			</div>
			<!-- 账户 -->
			<div class="layout">
				<div>
					<div style="margin-top: 10px;">
						<div style="font-weight: bold;margin-top: 40px;">个人信息</div>
						<div style="border:1px; border-style:solid; margin-bottom:20px; border-color:#d8dee5;"></div>
						<span>账户：${sessionScope.user.username}@${sessionScope.host}</span>
					</div>
					
					<div style="font-weight: bold;margin-top: 40px;">个性签名</div>
					<div style="border: 1px; border-style: solid;margin-bottom: 20px;border-color: #d8dee5;"></div>
					<div>
						<span>使用个性签名：
							<select name="" id="signselect" style="width:150px;height: 20px;border: 1px solid #CCCCCC;">
								<option value="-1">不使用</option>
								<s:iterator value="#request.signlist">
									<option value="${id}">${name}</option>																	
								</s:iterator>
							</select>
													
						</span><br><br>
						<span style="margin-left:90px;"><a onclick="showSignName();">添加个性签名</a></span>
					</div>
					
					<div class="createlabel" id="sign"  style=" height: 294px;border: 1px solid #CCCCCC;border-radius: 5px 5px 5px 5px;">
						<div class="createlabel_head" style="cursor:move;">
							<span>添加个性签名</span> 
							<a class="createlabel_close" href="javascript:;" onclick="$('#sign').hide()" title="关闭"></a>
						</div>
						<form action="/user/modifySign.html" method="post" id="modifySign">
							<div class="createlabel_body" style="height: 162px;overflow: hidden;">
								<input type="text" value="签名1" id="signName" name="signName" style="height: 18px;width:200px;border: 1px solid #CCCCCC;">
								<span class="tagsp1" id="signsp1" style="color: #FF0000;"></span>
								<br><br>
								<textarea rows="9" cols="60" name="content" id="content" style="border: 1px solid #CCCCCC;resize: none;"></textarea>
								<span class="tagsp1" id="contentsp1" style="color: #FF0000;"></span>
								<input type="hidden" name="state" value="0" id="state">
								<input type="hidden" name="id" id="eid2">
							</div>
						</form>
						<div class="createlabel_bottom">
							<input type="button" class="btn_blue" value="确定" onclick="addsign();" />

							<input type="button" class="btn_gray" value="取消" onclick="$('#sign').hide();" />
						</div>
					</div>
					<div id="editsign" style="display: none;margin-top: 10px;margin-left: 90px;">						
						
						<textarea rows="7" cols="50" id="econtent" name="econtent" style="border: 1px solid #CCCCCC;resize: none;">${content}</textarea>
						<input type="hidden" name="eid" id="eid">
						<input type="hidden" name="eSignName" id="eSignName">							
											
						<div style="margin-top: 0px;background-color:#EAEAEA;width: 317px;height: 30px;border: 1px solid #CCCCCC;">
							<input type="button" class="btn_blue" value="编辑" id="btn_blue" style="margin-top: 3px;"/>
							<input type="button" class="btn_gray" value="删除" id="btn_gray"/>
						</div>
					</div>
					<div>&nbsp;</div>
					<div>&nbsp;</div>
					<div class="d2-1"></div>
				</div>
			</div>
			<!-- 反垃圾 -->
			<div class="layout">
			
				<iframe id="spam_blacklist" name="spam_blacklist" width="99%" height="80%" frameborder="0" 
				 scrolling="yes"	src="./spam_blacklist.html"></iframe>

				
			</div>
		</div>
	</div>
</body>
</html>