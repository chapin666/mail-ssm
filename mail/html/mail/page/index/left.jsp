<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/mail/css/gray/left.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/left.js"></script>
<script type="text/javascript" src="/mail/js/move.js"></script>
<style type="text/css">
	.center ul li .mailgroup:HOVER {
	padding: 6px 5px 6px 15px;
	color: #2A586F;
	cursor: pointer;
	background: #f1f1f1;
}
</style>
<script type="text/javascript">
function showlxr(id,imgid){
	$('.lxrimg').attr('src','/adm/images/tree/nolines_plus.gif');
	$('li[lang]').each(function(i){
		if($(this).attr('lang')!='lxr_'+id){
			$(this).css('display','none');
		}
	})
	$('li[lang]').each(function(i){
		if($(this).attr('lang')=='lxr_'+id){
			if($(this).css('display')=='none'){
				$(this).css('display','block');
				$('#'+imgid).attr('src','/adm/images/tree/nolines_minus.gif');
			}else{
				$(this).css('display','none');
				$('#'+imgid).attr('src','/adm/images/tree/nolines_plus.gif');
			}
		}
	})
}
</script>
</head>
<body>
	<form action="" id="clear"></form>
	<div class="main">
		<div class="head">
			<ul>
				<li>
					<img src="/mail/images/gray/left_top_write.jpg" class="img1" />
					<a onclick="leftListClick(100000,'/email/Write.html')">写信</a>
				</li>
				<li>
					<img src="/mail/images/gray/left_top_shou.jpg" class="img2" />
					<a onclick="leftListClick(0,'/email/getMail.html')">收信</a>
				</li>
				<li>
					<img src="/mail/images/gray/left_top_tong.jpg" class="img3" />
					<a onclick="leftListClick(100000,'/contact/getContactList.html')">通讯录</a>
				</li>
			</ul>
		</div>
		<div class="center">
			<ul id="leftList">
				<li onclick="leftListClick(0,'/email/getMail.html')">收件箱
					<c:if test="${getMailnum > 0 }">(${getMailnum })</c:if>
				</li>
				<li onclick="leftListClick(1,'/email/fromMail.html')">已发送</li>
				<li onclick="leftListClick(2,'/email/saveMail.html')">草稿箱
					<c:if test="${saveMailnum > 0 }">(${saveMailnum })</c:if>
				</li>
				<li onclick="leftListClick(3,'/email/delMail.html')">已删除
					<c:if test="${delMailnum > 0 }">
						<a style="float: right;" onclick="clearDel();" id="clearDel" target="main">[清空]</a>
					</c:if>					
				</li>
				<li onclick="leftListClick(4,'/email/getRubMail.html')">垃圾箱
					<c:if test="${getRubMailnum > 0 }">
						<a style="float: right;" onclick="clearRub();" id="clearRub" target="main">[清空]</a>
					</c:if>					
				</li>
				<li onclick="leftListClick(5,'/email/getMainMail.html')">星标邮件
					<c:if test="${getMainMailnum > 0 }">(${getMainMailnum })</c:if>
				 	<label class="main1" ></label>
				 </li>
				<li onclick="leftListClick(6,'/email/News.html')">单位公告
					<c:if test="${newsnum > 0 }">(${newsnum })</c:if>
				</li>
				
				
				<div style="margin: 5px 5px 0px;font: 1px/0px ;height:0px !important;border-top:#d8dee5 1px solid;"></div>
				
				<!--  <li onclick="leftListClick(7,'/user/setmail.html')">-->
					<li  onclick="leftListClick(7,'/user/gettagmail.html?tagname=null')" style="cursor: pointer;height: 16px;margin-left: -3px;margin-top: 2px;" class="mailgroup">
						<img id="alllxrimg" class="lxrimg"  alt="" src="/adm/images/tree/nolines_plus.gif"
						style="height: 18px;line-height: 24px;float: left;margin-top: 3px;" onclick="showlxr('all','alllxrimg');"/>
						<a style="height: 18px;line-height: 24px;text-decoration: none;float: left;">标签</a>
					</li>
						<%int tag = 7; %>
						<s:iterator value="#request.taglist" id="tlist">	
							<c:if test="${not empty taglist}">
								<%tag++;%>
								<li style="padding-left:18px;display:none;" class="lxr mailgroupsmall" lang="lxr_all" onclick="leftListClick(<%=tag%>,'/user/getTagNameMail.html?tagname=${tlist.name}')">
									&nbsp;&nbsp;${tlist.name}
								</li>
							</c:if>									
						</s:iterator>
						<% if(tag==7){%>
							<li style="padding-left:18px;display:none;" class="lxr mailgroupsmall" lang="lxr_all">&nbsp;&nbsp;(暂无标签)</li>
						<% }%>						
						<%--</li>--%>
			</ul>
		</div>
		<div class="foot"></div>
	</div>
</body>
</html>