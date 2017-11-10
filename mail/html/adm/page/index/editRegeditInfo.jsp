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
	function submintRegedit(){
		var authorcode = $("#authorcode").val();		
		if(authorcode.length>200){
			$("#code").html('<label></label>&nbsp;注册码无效');
			return false;
		}else{
			$("#regeditInfo").submit();
			return true;
		}
	}
	function resets(){
		$('#authorcode').val('');
	}
	$(function(){		
		$("#resetBtn").hover(
			function(){
				$(this).removeClass("resetBtn").addClass("resetBtnOn");
			},
			function(){
				$(this).removeClass("resetBtnOn").addClass("resetBtn");
			}
		);
	});	
</script>
<style type="text/css">
.resetBtn {
	width: 55px;
	height: 25px;
	float: left;
	border: none;
	margin: 3px 5px 6px 5px;
	cursor: pointer;
}	
.resetBtnOn {
	width: 55px;
	height: 25px;
	float: left;
	border: none;
	margin: 3px 5px 6px 5px;
	cursor: pointer;
}	
.loginBtn {
	width: 55px;
	height: 25px;
	float: left;
	border: none;
	margin: 3px 45px 0 180px;
	cursor: pointer;
}	
.loginBtnOn {
	width: 55px;
	height: 25px;
	float: left;
	border: none;
	margin: 3px 45px 0 180px;
	cursor: pointer;
}

</style>
</head>
<body>
<div class="main">
	<div class="content" style="width: 960px;">
	  <div class="left structManage" >
			<ul class="sidetd">
				<li>
					<a href="/unit/toUnit.mail">单位信息<span class="arrow_right"></span></a>
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
					<a href="/unit/regeditInfo.mail" class="selected">注册信息<span class="arrow_right"></span></a>
				</li>
			</ul>
		</div>
	
		<div class="content" style="width: 960px;">
			<div class="margin-right: 100px;">
				<form action="/unit/updateRegeditInfo.mail" id="regeditInfo">
				<table width="798;" height="500" border="0" style="" cellpadding="0" cellspacing="0">	
					<tr>
					  <td width="13"></td>
					  <td width="798" height="55"  align="left"  style="border: 0;"><h1> 产品注册信息</h1></td>
					  <td width="280" align="center" style="background-color: #315CA1;">
					  	<div style="border: 0px;width: 120px;height: 13px;color: #FFFFFF;">：： 友情提示 ：：</div>
					  </td>
					</tr>
					<tr>
						<td></td>
						<td height="197" align="left">
							<table width="494" height="200" border="1" cellpadding="0" cellspacing="0" bordercolor="#F2F2F2">
								
								<tr>
									<td height="35" colspan="2" align="center" bgcolor="#F2F2F2">产品注册信息</td>
								</tr>
								<tr>
									<td height="24" align="center" bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;产品版本号:</td>
									<td bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;${softversion}</td>				
								</tr>
								<tr>
									<td height="24" align="center" bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;产品序列号:</td>
									<td bordercolor="#F6F6F6" bgcolor="#FFFFFF">
										<input type="text" name="authorcode" id="authorcode" value="${authorcode}" style="width: 200px;height: 15px;">
								    	<span class="sp3" id="code" style="color: red;"></span>
								    </td>			
								</tr>
								<tr>
									<td height="24" align="center" bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;最大用户数:</td>
									<td bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;${maxuser}</td>				
								</tr>
								<tr>
									<td height="23" align="center" bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;机器码:</td>
									<td bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;${machinecode}</td>				
								</tr>
								<tr>
									<td height="23" align="center" bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;软件到期时间:</td>
									<td bordercolor="#F6F6F6" bgcolor="#FFFFFF">&nbsp;${softenddate}</td>				
								</tr>
								<tr>
									<td height="23" colspan="2" align="center" bordercolor="#F6F6F6" bgcolor="#FFFFFF">
										<input type="button" name="loginBtn" id="loginBtn" class="loginBtn" value="提交" onclick="submintRegedit();">
										<input type="button" name="resetBtn" id="resetBtn" class="resetBtn" value="重置" onclick="resets();">
								  </td>
								</tr>
				  		  </table>
					  </td>
					  <td rowspan="2" align="left" valign="top" bgcolor="#DAEEEF" style="background-color: #F9FDFD;">
						  <p style="margin-top: 5px;"> &nbsp;&nbsp;&nbsp;&nbsp;您正式购买本产品后将会有一个正式的序列号，序列号和硬件绑定。</p>	
					  	  <p style="margin-top: 5px;"> &nbsp;&nbsp;&nbsp;&nbsp;如果您更换了硬件设备，请及时联系厂商获取新的序列号。 </p>
					  </td>
				    </tr>
					<tr align="center">
					  <td height="239" >&nbsp;</td>
		            </tr>
		            
				 </table>	
				 </form>
			</div>
	  </div>
	  
	</div>
	<div class="footer">
		<p>© 2013 Danwei Mail. All Rights Reserved</p>
	</div>
</div>
</body>
</html>






