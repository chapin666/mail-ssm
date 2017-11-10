<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript">
	function logoUpdate(obj,idForm){
		var val = $(obj).val();
		if(val != ""){
			if(val.substring(val.length-4,val.length) == ".png"){
				if (document.all) {
					var url = '';
					if(idForm=='qtLogoUpdateFrom'){
						url = '/unit/updateQTLogo.mail' ;
					}else{
						url = '/unit/updateHTLogo.mail' ;
					}
		            obj.select();
		            debugger;
					var src = document.selection.createRange().text;
					$.ajax({
						type:'post',
						url:url,
						data:{src:src},
						dataType: 'html',
						async: false,
						success:function(data){
							location.href='/unit/toUnit.mail';
						}
					});
		        }else{
					$("#"+idForm).submit();
				}
			}else{
				alert("图片格式错误，请重新上传PNG格式图片");
				return false;
			}
		}
	}
</script>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="left structManage">
			<ul class="sidetd">
				<li>
					<a href="/unit/toUnit.mail" class="selected">单位信息<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/unit/toUpdateHost.mail">域名管理<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/member/toApdatePassword.mail">修改密码<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/member/getMemberList.mail">分级管理员<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/unit/regeditInfo.mail">注册信息<span class="arrow_right"></span></a>
				</li>
			</ul>
		</div>
		<div class="right">
			<div class="contentright">
				<h1>单位信息</h1>
				<div class="logoright" style="height:60px;">
					<span class="s1">前台邮箱LOGO</span>
					<span class="s2"><img src="/mail/images/gray/logo.png?t=<%=new Date() %>" width="196" height="37" border="0" /></span>
					<span class="s3" onclick="$('#qtlogo').click();"><label></label>修改</span>
					<span><font color="red">LOGO格式只能为PNG(196*37)</font></span>
					<form action="/unit/updateQTLogo.mail" method="post" id="qtLogoUpdateFrom" enctype="multipart/form-data">
						<input type="file" name="qtlogo" id="qtlogo" onchange="logoUpdate(this,'qtLogoUpdateFrom')" style="filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);opacity:0;width:0px;height:0px;" />
					</form>
				</div>
				<div class="logoright" style="height:60px;margin-top:20px;">
					<span class="s1">后台邮箱LOGO</span>
					<span class="s2"><img src="/adm/images/index/logo.png?t=<%=new Date() %>" width="196" height="37" border="0" /></span>
					<span class="s3" onclick="$('#htlogo').click();"><label></label>修改</span>
					<span><font color="red">LOGO格式只能为PNG(196*37)</font></span>
					<form action="/unit/updateHTLogo.mail" method="post" id="htLogoUpdateFrom" enctype="multipart/form-data">
						<input type="file" name="htlogo" id="htlogo" onchange="logoUpdate(this,'htLogoUpdateFrom')" style="filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);opacity:0;width:0px;height:0px;" />
					</form>
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