<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mail/css/gray/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
<script type="text/javascript" src="/mail/js/rubmail.js"></script>
<script type="text/javascript" src="/mail/js/move.js"></script>
<title>单位邮箱-垃圾箱</title>

</head>
<body>

<div class="msgBoxDIV">
<span class="errmsg">未选中任何邮件</span>
</div>

<!--彻底删除弹出的选择对话框-->
	<div class="cddelete">
		<div class="createlabel_head" style="cursor:move;">		
			<span>删除确认</span>
		</div>
		<div class="createlabel_body">
			<div style="margin:8px 0 0;font-weight:bold; "><br/>	
			<dir class="icon_info_b" style="margin: -7px 12px 8px 0px;"></dir>				
				彻底删除后邮件将不可恢复，确定要删除？<br/><br/>
			</div>
		</div>		
		<div class="createlabel_bottom"> 
			<input type="button" class="btn_blue" value="确定" onclick="movedel(6);" />
			<input type="button" class="btn_gray" value="取消" onclick="$('.cddelete').hide()" />
		</div>
	</div>


<form action="/email/noRubMail.html" method="post" id="noRubMail">
	<input type="hidden" name="ids" value="ids">
</form>

<form action="/email/getRubMail.html" method="post" id="pageForm">
	<input type="hidden" name="pagenow" id="pagenow" value="${pagenow}">
	<input type="hidden" name="name" value="${name}">
</form>
<div class="main">
	<div class="content">
		<p class="title"><b>垃圾箱</b>(共${totalsize}封)</p>
		<div class="button">
			<!-- 彻底删除 -->
			<span><input type="button" value="" name="cddeleteBtn" class="cddeleteBtn" onclick="movedel(2);"/></span>
			<!-- 这不是垃圾 -->
			<span><input type="button" value="" name="bsljBtn" class="bsljBtn" onclick="noRubMail();"/></span>
			<span>
				<select id="addtotag" onchange="addetag(this);">
					<option value="-1">标记为...</option>
					<!--<option value="" disabled>--------------</option> 
					<option value="-3">已读邮件</option>
					<option value="-4">未读邮件</option>
					<option value="" disabled>--------------</option> -->
					<c:if test="${not empty taglist}">
						<option value="" disabled>--------------</option>
					</c:if>
					<s:iterator value="#request.taglist" id="tlist">
						<option value="${tlist.id}">　${tlist.name}</option>
					</s:iterator>
					<option value="" disabled>--------------</option>
					<option value="-2">新建标签</option>
				</select>
			</span>
			
			<!-- 分页 -->
			<c:if test="${not empty emaillist}">
			<span class="page">
				${pagenow}/${pagenum} 页
				<c:if test="${pagenum>1}">
					${pagebar}
				</c:if>
				<a href="javascript:void(0);" class="tiaozhuanBtn" onclick="tiaozhuanShow(1)">跳转</a>
			</span>
			</c:if>
		</div>
		<div class="tiaozhuan">
			跳转到第
			<input type="text" class="ipt1" name="jumpnum" id="jumpnum" onkeydown="if((event||window.event).keyCode&&(event||window.event).keyCode==13){$('#qdBtn').click();}"/>
			页
			<input type="button" class="qdBtn" name="qdBtn" id="qdBtn" onclick="jump();" />
		</div>
		<div class="createlabel">
			<div class="createlabel_head" style="cursor:move;">
				<span>新建标签</span>
				<a class="createlabel_close" href="javascript:;" onclick="addetag(0);" title="关闭"></a>
			</div>
			<form action="/tag/doAddTag.html" method="post" id="addtagform">
			<div class="createlabel_body">
				<p>请您输入标签名称</p>
				<div style="margin:8px 0 0;">
					<input type="text" class="createlabel_input" name="tag.name" id="tagname"/>
					<input type="hidden" name="tag.userid" value="${sessionScope.user.id}" id="userid" />
					<span class="tagsp1" id="tagsp1" style="color: #FF0000;"></span>
				</div>
			</div>
			</form>
			<div class="createlabel_bottom">
				<input type="button" class="btn_blue" value="确定" onclick="addtag();"/>
				<input type="button" class="btn_gray" value="取消" onclick="addetag(0);"/>
			</div>
		</div>
		<div class="mail">
			<ul class="relation" style="padding:0px;">
				<li class="getmailbt">
					<span class="span1"><input type="checkbox" onclick="selall(this.checked);" id="cbs"/></span>
					<span class="span21"><label class="ico4"></label></span>
					<span class="span2">发件人</span>
					<span class="span9">主题</span>
					<span class="span50">时间↓</span>
				</li>
				
				<form action="/email/editRubMail.html" method="post" id="editRubMail">
					<input type="hidden" name="pagenow" id="pagenow" value="${pagenow}">
				
					<input type="hidden" name="edittype" id="edittype">
					<input type="hidden" name="tid" id="tagid">
					<s:iterator value="#request.emaillist" id="eml">
						<li>
							<span class="span7">
								<input type="checkbox" name="id" class="box" lang="${eml.see}" value="${eml.id}" />
								<input type="checkbox" name="types" class="box1" value="${eml.type}" style="display:none;" />
								<input type="checkbox" name="states" class="box2" value="${eml.state}" style="display:none;" />
								<input type="checkbox" name="files" class="box3" value="${eml.file}" style="display:none;" />
								<input type="checkbox" name="frommails" class="box4" value="${eml.frommail}" style="display:none;" />
								<input type="checkbox" name="tomails" class="box5" value="${eml.tomail}" style="display:none;" />
							</span>
							<a>
								<span class="span22" onclick="location.href='/email/MailInfo.html?mailid=${eml.mailid}&id=${eml.id}&ftype=4'"><label class="ico2"></label><c:if test="${not empty eml.file}"><label class="ico3"></label></c:if></span>
								<span class="span5" onclick="location.href='/email/MailInfo.html?mailid=${eml.mailid}&id=${eml.id}&ftype=4'">${eml.frommail}</span>
								<span class="span6"><font onclick="location.href='/email/MailInfo.html?mailid=${eml.mailid}&id=${eml.id}&ftype=4'">
								<c:if test="${fn:length(eml.title)>35}">${fn:substring(eml.title, 0, 35)}...</c:if><c:if test="${fn:length(eml.title)<=35}">${eml.title}</c:if>
								</font>
									<c:if test="${not empty etaglist}">
										
										<c:if test="${sessionScope.user.username eq eml.frommail}">	
										<input type="hidden" name="ttype" id="tagtype" value="1" />										
											<s:iterator value="#request.etaglist" id="etl">
												<c:if test="${etl.eid eq eml.id and etl.type eq 1}">&nbsp;<label>${etl.name}<font onclick="deleteetag('${etl.id}');return false;">×</font></label></c:if>
											</s:iterator>
										</c:if>
										<c:if test="${sessionScope.user.username != eml.frommail}">
										<input type="hidden" name="ttype" id="tagtype" value="1" />
											<s:iterator value="#request.etaglist" id="etl">
												<c:if test="${etl.eid eq eml.id and etl.type eq 1}">&nbsp;<label>${etl.name}<font onclick="deleteetag('${etl.id}');return false;">×</font></label></c:if>
											</s:iterator>
										</c:if>
									</c:if>
								</span>
								<span class="span50">${eml.time}<c:if test="${eml.ismain eq 1}"><label class="main1" onmousedown="changemainmail('${eml.id}',0,'${eml.mailid}',1);return false;"></label></c:if><c:if test="${eml.ismain!=1}"><label class="main2" onmousedown="changemainmail('${eml.id}',1,'${eml.mailid}',1);return false;"></label></c:if></span>
							</a>
						</li>
					</s:iterator>
				</form>
				<li class="bottom">
					<label>选择：</label>
					<a onclick="selall(true);">全部</a>
					-
					<a onclick="selall(false);">无</a>
				</li>
			</ul>
		</div>
		<div class="button">
			<!-- 彻底删除 -->
			<span><input type="button" value="" name="cddeleteBtn" class="cddeleteBtn" onclick="movedel(2);"/></span>
			<!-- 这不是垃圾 -->
			<span><input type="button" value="" name="bsljBtn" class="bsljBtn" onclick="noRubMail();"/></span>
			
			<span>
				<select id="addtotag1" onchange="addetag(this);">
					<option value="-1">标记为...</option> 
					<!--<option value="" disabled>--------------</option>
					<option value="-3">已读邮件</option>
					<option value="-4">未读邮件</option>
					<option value="" disabled>--------------</option> -->
					<c:if test="${not empty taglist}">
						<option value="" disabled>--------------</option>
					</c:if>
					<s:iterator value="#request.taglist" id="tlist">
						<option value="${tlist.id}">　${tlist.name}</option>
					</s:iterator>
					<option value="" disabled>--------------</option>
					<option value="-2" onclick="$('.createlabel').show()">新建标签</option>
				</select>
			</span>
			
			<!-- 分页 -->
			<c:if test="${not empty emaillist}">
			<span class="page">
				${pagenow}/${pagenum} 页
				<c:if test="${pagenum>1}">
					${pagebar}
				</c:if>
				<a href="javascript:void(0);" class="tiaozhuanBtn" onclick="tiaozhuanShow(event)">跳转</a>
			</span>
			</c:if>
			
		</div>
	</div>
</div>
</body>
</html>