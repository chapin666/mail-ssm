<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/newsletter.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
<script type="text/javascript">
	$(function(){
		var j = 0 ;
		$('.notice_unsend').each(function(i){
			j++;
		});
		$('#sendstate').html(j);
	});
</script>
</head>
<body>
	<form action="/emails/getNews.mail" method="post" id="pageForm">
	  	<input type="hidden" name="pagenow" id="pagenow" value="1">
		<input type="hidden" name="name" value="${name}">
	</form>
	<div class="main">
		<div class="content" style="min-height: 650px;">
			<div class="pathsize">
				<ul>
					<li class="home">
						<a href="#">单位公告</a>
					</li>
				</ul>
			</div>
			<div class="notice_newstyle">
				<div class="new_title_box">
					<h1 class="new_title">单位公告</h1>
					<div class="info">(待发布公告<font id="sendstate">0</font>条)</div>
					<div class="newbtnright">
						<a class="btnNormal" href="/emails/SendNews.mail">
							<span class="btnNotice"></span> 发布公告
						</a>
					</div>
				</div>
				<div class="contents">
					<table class="notice_table">
						<tbody>
							<s:iterator value="#request.emaillist" id="eml">
								<c:if test="${eml.state eq 2}">
									<tr class="notice_unsend">
										<td width="33px" align="left" onclick="location.href='/emails/ReEditMail.mail?eid=${eml.id}'">
											<span class="ico_noti_pre"></span>
										</td>
										<td width="613px" align="left" onclick="location.href='/emails/ReEditMail.mail?eid=${eml.id}'">
											<div class="notice_t">
												<h2>${fn:substring(eml.title,0,20)}</h2>
												<span class="graytext">${fn:substring(eml.content,0,30)} </span>
											</div>
										</td>
										<td width="150px" align="left" onclick="location.href='/emails/ReEditMail.mail?eid=${eml.id}'">${eml.time}</td>
										<td width="104px" align="left">
											<a class="bt_recall" href="javascript:if(confirm('确实要删除吗?'))location.href='/emails/delNews.mail?id=${eml.id}'" title="删除"></a>
										</td>
									</tr>
								</c:if>
								<c:if test="${eml.state>2 or eml.state<2}">
									<tr class="notice_send">
										<td width="33px" align="left" onclick="location.href='/emails/NewsInfo.mail?id=${eml.id}'">
											<span class="ico_noti_pre"></span>
										</td>
										<td width="613px" align="left" onclick="location.href='/emails/NewsInfo.mail?id=${eml.id}'">
											<div class="notice_t">
												<h2 style="font-weight:lighter;">${fn:substring(eml.title,0,20)}</h2>
												<span class="graytext">${fn:substring(eml.content,0,30)} </span>
											</div>
										</td>
										<td width="150px" align="left" onclick="location.href='/emails/NewsInfo.mail?id=${eml.id}'">${eml.time}</td>
										<td width="104px" align="left">
											<a class="bt_recall" href="javascript:if(confirm('确实要删除吗?'))location.href='/emails/delNews.mail?id=${eml.id}'" title="删除"></a>
										</td>
									</tr>
								</c:if>
							</s:iterator>
						</tbody>
					</table>
					<c:if test="${not empty emaillist}">
						<span class="page">
							${pagenow}/${pagenum} 页
							<c:if test="${pagenum>1}">
								${pagebar}
							</c:if>
						</span>
					</c:if>
				</div>
			</div>
		</div>
		<div class="footer">
			<p>© 2013 Danwei Mail. All Rights Reserved</p>
		</div>
	</div>
</body>
</html>