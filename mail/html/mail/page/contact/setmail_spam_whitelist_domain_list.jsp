<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/mail/css/gray/setmail.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.bodylist {
	width: 100%;
	height: auto;
	line-height: 28px;
	float: left;
    font-size: 12px;
    font-weight: normal;
    text-align: left;
}
.bodylist ul {
	width: 100%;
	height: 100%;
	list-style: none;
}	
.bodylist li {
	width: 100%;
	height: 28px;
	padding: 6px 0;
	list-style: none;
	float: left;
	border-bottom: 1px solid #E1E1E1;
}	
.bodylist li:hover {
    background: #DDEEFF;
    text-decoration: none;
}
.bodylist input, .bodylist span {
	float: left;
}	
.bodylist input{
	width: 12px;
	height: 12px;
	margin: 8px 12px 0 24px;	
}	
.bodylist span {
    color: #1E5494;
    font-size: 12px;
    height: 28px;
    line-height: 28px;
    text-align: left;
    cursor: pointer;
}
.bodylist span.listspan1 {
	width: 600px;
	margin-left: 20px;
	color: #000;
}
.bodylist span.listspan5 {
	width: 300px;
	margin-left: 20px;
	color: #000;
}
.bodylist span.listspan2 {
	width: 200px;
	color: #000;
}	
.bodylist span.listspan3 {
	width: 108px;
	color: #000;
}
.bodylist span.listspan4 {
	width: 80px;
	color: #000;
}
.bodylist span.listspan4 a {
	color: #1E5494;
	text-decoration: none;
	margin-right: 8px;
}
.bodylist span.listspan4 a:hover {
	color: #1E5494;
	text-decoration: underline;
	margin-right: 8px;
}
.bodylist span label {
	background: url("/adm/images/index/member_ico.png") no-repeat 0 -32px;	
	height: 16px;
    width: 16px;
    float: left;
    margin: 7px 3px 0 0;
}
.bodylist ul li.title {
	width: 100%;
	height: 28px;
	line-height: 28px;
	float: left;
	background: #F1F1F1;
    border-bottom: 1px solid #D6D6D6;
    border-top: 1px solid #D6D6D6;
    font-size: 12px;
    font-weight: normal;
    text-align: left;
    padding: 0px;
}
.bodylist ul li.title span {
	color: #000;
}
</style>
<script type="text/javascript">
function tobl(){
	self.location.href = "/user/spam_blacklist.html";	
}
function subFormBl(){
	var email = document.getElementById("txtbl").value;
	var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(!reg.test(email)){
		alert("地址有误!");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div class="layout">
		<div style="font-size:14px;border-bottom-width: 2px;border-bottom: 2px solid #EAEAEA;padding-bottom: 5px;">
			<b>设置邮件域名白名单 </b>| <a href="javascript:tobl();"  style="text-decoration: none;"  target="spam_blacklist"> 返回"反垃圾"设置 </a>
		</div>
		<div style="min-height: 50px;padding-left: 60px;padding-top: 10px; border-bottom-width: 2px;border-bottom: 2px solid #EAEAEA;">
			<form action="./spam_whitelist_domain_list.html?m=add"  method="post" onsubmit="return subFormBl();">
				<input id="txtbl" name="txtbl" size="30" height="25px" type="text"  >&nbsp;
				<input type="submit" class="btn_gray" value=" 添加到白名单 "  >
			</form>
		</div>
		
		<div style="margin-top: 10px; "  class="bodylist">
		<ul>
			<li class="title">
				<span class="listspan1">域名地址</span>
				<span class="listspan3">操作</span>
			</li>
				
			
			<c:forEach var="bl" items="${blacklist}" >					   
			    <li>	    
				<span class="listspan1">${bl.reportuser}</span>
				<span class="listspan3">
					<a href="./spam_whitelist_domain_list.html?m=del&txtbl=${bl.reportuser}">删除</a></span>
			    </li>
			</c:forEach>
		</ul>
		<c:if test="${not empty blacklist}">
		 <div class="page">
			<span style="float:left;margin-left:20px;">共<font color="red">${count}</font>条</span>
			<span style="float:right;margin-right:20px;">
				<font color="red">${pageNow}</font>/${pageNumber} 页 
				<c:if test="${pageNow>1}">
					<a href="./spam_whitelist_domain_list.html?i=${pageNow-1}">上一页 </a>&nbsp; 
				</c:if>
				<c:if test="${pageNumber>pageNow}">
					<a href="./spam_whitelist_domain_list.html?i=${pageNow+1}">下一页</a>
				</c:if>
			</span>	
		  </div>
		 </c:if>
		</div>
					
		
</div>
</body>
</html>