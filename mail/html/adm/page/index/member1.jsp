<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/tree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/adm/js/tree.js"></script>
</head>
<body>
<div style="width:800px;margin-left:60px;">
	<div align="center">
		<span>您好，${sessionScope.member.username}</span>
		<a onclick="window.location='/adm/exit.mail';">退出</a>
	</div>
	<div align="center" style="margin-top:20px;">
		<a href="/adm/index.mail">首页</a>
		<a href="/adm/member.mail" style="padding-left: 30px;">成员与群组</a>
		<a href="" style="padding-left: 30px;">我的单位</a>
		<a href="" style="padding-left: 30px;" >系统日志</a>
		<a href="" style="padding-left: 30px;">邮箱过滤</a>
		<a href="" style="padding-left: 30px;">单位公告</a>
	</div>
</div>
<div style="width:300px;margin-left:60px;float: left;">
	<div align="center" style="margin-top:20px;">
		<script type="text/javascript">
			d = new dTree('d');
		</script>
	 	<s:iterator value="#request.unitlist" id="unl">
			<script type="text/javascript">
				d.add('${id}','${pid}','${name}','/unit/getUserByUnit.mail?id=${id}','','main');
			</script>
		</s:iterator>
		<div class="dtree" align="left">
			<script type="text/javascript">
				document.write(d);
			</script>
		</div>
	</div>
</div>
<div>
	<div align="center" style="margin-top:20px;">
		<span>域名</span><br>
		${unit.name} 
		<select>
			<c:if test="${unit.id > 1}">
				<option id="delunit" value="${unit.id}">删除</option>
			</c:if>
			<option value="-2">重命名</option>
			<option value="-3">新建部门</option>
		</select>
	</div>
	<div align="center" style="margin-top:20px;">
		<span>成员</span>
	</div>
	<div align="center" style="margin-top:20px;">
		<span>LOGO</span>
	</div>
</div>
</body>
</html>