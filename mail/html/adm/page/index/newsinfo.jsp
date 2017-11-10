<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/newsletter.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
</head>
<body>
	<div class="main">
		<div class="content">
			<div class="pathsize">
				<ul>
					<li class="home">
						<a href="#">单位公告</a>
					</li>
				</ul>
			</div>
			<div class="notice_newstyle">
				<div class="new_title_box">
					<h1 class="new_title">${notice.title}</h1>
					<div class="newbtnright">
						<a class="btnNormal" onclick="location.href='/emails/getNews.mail'" style="padding:4px 10px;">
							<span class="ico_home"></span> 返回
						</a>
					</div>
				</div>
				<div class="contents">
					<table class="notice_table">
						<tbody>
							<tr>
								<td>${notice.content}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="footer">
			<p>© 2013 Danwei Mail. All Rights Reserved</p>
		</div>
	</div>
</body>
</html>