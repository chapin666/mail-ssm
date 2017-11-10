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
<script type="text/javascript" src="/mail/js/mailinfo.js"></script>
<script type="text/javascript" src="/mail/js/move.js"></script>
<title>单位邮箱-详细页</title>

</head>
<body>

<div class="main">
	<div class="content">

		<div class="button">
			<span><input type="button" value="" name="backBtn" class="backBtn" onclick="history.back();"/></span>
			<span>
				<input type="button" value="" name="deleteBtn" class="deleteBtn" 
				onclick="location.href='/user/delTagNameMail.html?mailid=${mailid}&types=${types}&tagname=${tagname}&id=${draftsid}';return false;"/>
			</span>			
		</div>
		
		<div class="mail infomail">
			<ul class="mailinfo" style="padding:0px;height:auto;padding-bottom:10px;">
				<li class="ptitle">
					<p class="title"><b>${email.title}</b></p>
				</li>
				<c:if test="${requestScope.ftype!=0}">
					<li>
						<p>发件人：<label>${email.fromname}</label>${email.frommail}</p>
					</li>
				</c:if>
				<li>
					<p>时　间：${email.time}</p>
				</li>
				<c:if test="${not empty email.toname}">
					<li style="height:auto;" id="emailtoname">
						<p style="height:auto;">
							收件人：<font color="#333">${fn:replace(email.toname,"<","&lt;")}</font>
						</p>
					</li>
				</c:if>
				<c:if test="${not empty email.copyto}">
					<li style="height:auto;" id="emailcopyto">
						<p style="height:auto;">抄　送：<font color="#333">${fn:replace(email.copyto,"<","&lt;")}</font></p>
					</li>
				</c:if>
				
				<c:if test="${not empty email.bcc}">
					<li style="height:auto;" id="emailcopyto">
						<p style="height:auto;">密　送：<font color="#333">${fn:replace(email.bcc,"<","&lt;")}</font></p>
					</li>
				</c:if>
				
				<c:if test="${not empty filelist}">
					<li style="height:auto;">
						<p>
							<a><span>附　件：</span><span class="ico3"></span></a>
							<s:iterator value="#request.filelist" id="fl">
								<a onclick="$('#files').val('${fl.file}');$('#filenames').val('${fl.filename}');$('#downloadform').submit();">&nbsp;&nbsp;${fl.filename}</a>
							</s:iterator>
						</p>
					</li>
					<form action="/email/download.html" method="post" id="downloadform">
						<input type="hidden" name="files" id="files">
						<input type="hidden" name="filenames" id="filenames">
					</form>
				</c:if>
			</ul>
			<div class="mailcontent">
				<div style="padding:0px 15px;">${email.content}</div>
			</div>
		</div>
		<div class="button">
			<span><input type="button" value="" name="backBtn" class="backBtn" onclick="history.back();"/></span>
			<span>
				<input type="button" value="" name="deleteBtn" class="deleteBtn" 
				onclick="location.href='/user/delTagNameMail.html?mailid=${mailid}&types=${types}&tagname=${tagname}&id=${draftsid}';return false;"/>
			</span>			
		</div>
	</div>
</div>

</body>
</html>