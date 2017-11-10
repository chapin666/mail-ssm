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
<script type="text/javascript" src="/mail/js/getmail.js"></script>
<script type="text/javascript" src="/mail/js/move.js"></script>
<style type="text/css">
.btn_gray {
display: inline-block;
height: 22px;
min-width: 24px;
line-height: 22px;
border: 1px solid #888;
}
</style>
<title>单位邮箱-收件箱</title>

</head>
<body>
	<form action="/email/getMail.html" method="post" id="pageForm">
	  	<input type="hidden" name="pagenow" id="pagenow" value="${pagenow}">
	  	<input type="hidden" name="issee" value="${issee}">
		<input type="hidden" name="name" value="${name}">
		<input type="hidden" name="orby"  value="${orby}">					
		<input type="hidden" name="sortorder"  value="${sortorder}">	
	</form>	
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
			<input type="button" class="btn_blue" value="确定" onclick="moverecive(6);" />
			<input type="button" class="btn_gray" value="取消" onclick="$('.cddelete').hide()" />
		</div>
	</div>


<div class="main">

<div class="msgBoxDIV">
	<span class="errmsg">未选中任何邮件</span>
</div>

	<div class="content">
		<p class="title"><b>收件箱</b>(共${totalsize}封)</p>
		<div class="button">
			<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="moverecive(3);"/></span>
			<span><input type="button" value="" name="cddeleteBtn" class="cddeleteBtn" onclick="moverecive(5);"/></span>
<!--			<span><input type="button" value="" name="zfBtn" class="zfBtn" /></span>-->
<!--			<span><input type="button" value="" name="qbbwydBtn" class="qbbwydBtn" onclick="moverecive(3);"/></span>-->

			<span>
				<select id="addtotag" onchange="addetag(this);">
					<option value="-1">标记为...</option>
					<option value="" disabled>--------------</option>
					<option value="-3">已读邮件</option>
					<option value="-4">未读邮件</option>
					<c:if test="${not empty taglist}">
					<option value="" disabled>--------------</option>
					</c:if>
					<s:iterator value="#request.taglist" id="tlist">
						<option value="${tlist.id}">${tlist.name}</option>
					</s:iterator>
					<option value="" disabled>--------------</option>
					<!-- <option value="-2" onclick="$('.createlabel').show()">新建标签</option> -->
					<option value="-2">新建标签</option>
				</select>
			</span>
			<span>
				<!--
				<select name="" id="">
					<option value="">移动到...</option>
					 <option value="4" onclick="moverecive(4);">　发件箱</option>
					<option value="5" onclick="moverecive(5);">　草稿箱</option>
					<option value="6" onclick="moverecive(6);">　垃圾箱</option>
				</select>
				-->
			</span>
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
				<a class="createlabel_close" href="javascript:;" onclick="addetag(0)" title="关闭"></a>
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
				<input type="button" class="btn_gray" value="取消" onclick="addetag(0)"/>
			</div>
		</div>
		<div class="mail">
			<ul class="relation" style="padding:0px;">
				<li class="getmailbt">
					<span class="span1"><input type="checkbox" onclick="selall(this.checked);" id="cbs"/></span>
					<span class="span21"><label class="ico4"></label></span>
					<span class="span2">					
						<c:if test="${orby == 'frommail' }">
							<c:if test="${sortorder == 'desc'}">
								<a onclick="leftListClick1('/email/getMail.html','frommail','asc')">发件人↓</a>
							</c:if>
							<c:if test="${sortorder == 'asc'}">
								<a onclick="leftListClick1('/email/getMail.html','frommail','desc')">发件人↑</a>
							</c:if>
						</c:if>
						<c:if test="${orby != 'frommail' }">
							<c:if test="${sortorder == 'desc'}">
								<a onclick="leftListClick1('/email/getMail.html','frommail','asc')">发件人</a>
							</c:if>
							<c:if test="${sortorder == 'asc'}">
								<a onclick="leftListClick1('/email/getMail.html','frommail','desc')">发件人</a>
							</c:if>
						</c:if>
					</span>
					
					<span class="span9">
					<c:if test="${orby == 'subject'}">
						<c:if test="${sortorder == 'desc'}">
							<a onclick="leftListClick1('/email/getMail.html','subject','asc')">主题↓</a>
						</c:if>
						<c:if test="${sortorder == 'asc'}">
							<a onclick="leftListClick1('/email/getMail.html','subject','desc')">主题↑</a>
						</c:if>
					</c:if>
					<c:if test="${orby != 'subject'}">
						<c:if test="${sortorder == 'desc'}">
							<a onclick="leftListClick1('/email/getMail.html','subject','asc')">主题</a>
						</c:if>
						<c:if test="${sortorder == 'asc'}">
							<a onclick="leftListClick1('/email/getMail.html','subject','desc')">主题</a>
						</c:if>
					</c:if>
					</span>
					<span class="span50">
						<c:if test="${orby == 'time'}">
							<c:if test="${sortorder == 'desc'}">					
								<a onclick="leftListClick1('/email/getMail.html','time','asc')">时间↓</a>
							</c:if>
							<c:if test="${sortorder == 'asc'}">					
								<a onclick="leftListClick1('/email/getMail.html','time','desc')">时间↑</a>
							</c:if>
						</c:if>
						
						<c:if test="${orby != 'time'}">
							<c:if test="${sortorder == 'desc'}">					
								<a onclick="leftListClick1('/email/getMail.html','time','asc')">时间</a>
							</c:if>
							<c:if test="${sortorder == 'asc'}">					
								<a onclick="leftListClick1('/email/getMail.html','time','desc')">时间</a>
							</c:if>
						</c:if>
						
					</span>
				</li>
				<form action="/email/EditReciveMail.html" method="post" id="editreciveform">
				
					<input type="hidden" name="pagenow" id="pagenow" value="${pagenow}">
				  	<input type="hidden" name="issee" value="${issee}">
					<input type="hidden" name="orbypage"  value="${orby}">					
					<input type="hidden" name="sortorderpage"  value="${sortorder}">	
				
				
				
					<input type="hidden" name="edittype" id="edittype">
					<input type="hidden" name="tid" id="tagid">
					<input type="hidden" name="ttype" id="tagtype">
					
					<input type="hidden" name="orby" id="orby">					
					<input type="hidden" name="sortorder" id="sortorder">
					
					<s:iterator value="#request.emaillist" id="eml">
						<li>
							<span class="span7">
								<input type="checkbox"  name="id" class="box" state="${eml.state}" lang="${eml.see}" value="${eml.id}" />
								<input type="checkbox" name="state" class="box1" value="${eml.state}" style="display:none;"/>
								<input type="checkbox" name="types" class="box2" value="${eml.type}" style="display:none;" />
								<input type="checkbox" name="files" class="box3" value="${eml.file}" style="display:none;" />
								<input type="checkbox" name="frommails" class="box4" value="${eml.frommail}" style="display:none;" />
							</span>
							<a <c:if test="${eml.state eq 0}">style="font-weight: bold;" </c:if>>								
								<span class="span22" onclick="location.href='/email/MailInfo.html?id=${eml.id}&mailid=${eml.mailid}&ftype=1';return false;">
								<c:if test="${eml.state eq 0}">
								<label class="ico1"></label>
								</c:if>
								<c:if test="${eml.state eq 1}">
									<label class="ico2"></label>
								</c:if>
								<c:if test="${not empty eml.file}">
									<label class="ico3"></label>
								</c:if>
								</span>
								<span class="span5" onclick="location.href='/email/MailInfo.html?id=${eml.id}&mailid=${eml.mailid}&ftype=1';return false;">${eml.fromname}</span>
								<span class="span6">
									<font onclick="location.href='/email/MailInfo.html?id=${eml.id}&mailid=${eml.mailid}&ftype=1';return false;">
									<c:if test="${fn:length(eml.subject)>35}">${fn:substring(eml.subject, 0, 35)}...</c:if><c:if test="${fn:length(eml.subject)<=35}">${eml.subject}</c:if>
									</font>
									<c:if test="${not empty etaglist}">
										<s:iterator value="#request.etaglist" id="etl">
											<c:if test="${etl.eid eq eml.id and etl.type eq 1}">&nbsp;
												<label style="float: right;">${etl.name}
													<font onclick="deleteetag('${etl.id}');return false;">×</font>
												</label>
											</c:if>
										</s:iterator>
									</c:if>
								</span>
								
							</a>
							<span class="span50">${eml.time}<c:if test="${eml.ismain eq 1}"><label class="main1" onmousedown="changemainmail('${eml.id}',0,1);return false;"></label></c:if><c:if test="${eml.ismain!=1}"><label class="main2" onmousedown="changemainmail('${eml.id}',1,1);return false;"></label></c:if></span>
						</li>
					</s:iterator>
				</form>
				<li class="bottom">
					<label>选择：</label>
					<a onclick="selall(true);">全部</a>
					-
					<a onclick="selall(false);">无</a>
					-
					<a onclick="selread(true);">已读</a>
					-
					<a onclick="selread(false);">未读</a>
				</li>
			</ul>
		</div>
		<div class="button">
			<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="moverecive(3);"/></span>
			<span><input type="button" value="" name="cddeleteBtn" class="cddeleteBtn" onclick="moverecive(5);"/></span>
			
			
			
			<!--<span><input type="button" value="" name="zfBtn" class="zfBtn" /></span>
			<span><input type="button" value="" name="qbbwydBtn" class="qbbwydBtn" onclick="moverecive(3);"/></span>-->
			<span>
				<select id="addtotag1" onchange="addetag(this);">
					<option value="-1">标记为...</option>
					<option value="" disabled>--------------</option>
					<option value="-3">已读邮件</option>
					<option value="-4">未读邮件</option>
					<c:if test="${not empty taglist}">
					<option value="" disabled>--------------</option>
					</c:if>
					<s:iterator value="#request.taglist" id="tlist">
						<option value="${tlist.id}">${tlist.name}</option>
					</s:iterator>
					<option value="" disabled>--------------</option>
					<option value="-2" onclick="$('.createlabel').show()">新建标签</option>
				</select>
			</span>
			<span>
				<!--
				<select name="" id="">
					<option value="">移动到...</option>
					<option value="4" onclick="moverecive(4);">　发件箱</option>
					<option value="5" onclick="moverecive(5);">　草稿箱</option>
					<option value="6" onclick="moverecive(6);">　垃圾箱</option>
				</select>
				-->
			</span>
			<c:if test="${not empty emaillist}">
			<span class="page">
				${pagenow}/${pagenum} 页
				<c:if test="${pagenum>1}">
					${pagebar}
				</c:if>
				<a class="tiaozhuanBtn" onclick="tiaozhuanShow(event)">跳转</a>
			</span>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>