<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
	<link href="/adm/css/antiSpam.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="/mail/js/jquery.js"></script>
	<script type="text/javascript" src="/mail/js/date.js"></script>
	<script type="text/javascript" src="/mail/js/page.js"></script>	
	<script type="text/javascript" src="/adm/js/tree.js"></script>
	<script type="text/javascript" src="/adm/js/compatible.js"></script>
	<script type="text/javascript" src="/adm/js/unit.js"></script>
		
	
	
	<script type="text/javascript">
		$(function(){
			$("#keyWordRuleids").click(function(){
				if($(this).attr("checked")){
					$(".unitidclass").attr("checked", true);
				}else{
					$(".unitidclass").attr("checked", false);
				}
			});
		});
		//修改关键字规则状态：0禁用，1启用
		function updateState(n)
		{
			if(!ischeck()){
				return false;
			}else{
				$("#state").val(n);
				//alert(n);
				$("#checkKeyWordRule").attr("action","/mailspams/updateState.mail");
				$("#checkKeyWordRule").submit();
			}
		}
	
		//删除
		function deleteUser()
		{
			if(!ischeck()){
				return false;
			}else{
				if(window.confirm('确定删除吗？')){
					$("#checkKeyWordRule").attr("action","/mailspams/delKeyWordRule.mail");
					$("#checkKeyWordRule").submit();
				}
			}
		}
		
		function check(){
			 if(document.getElementById("name").value != ""){
				 $("#sp1name").html('<label></label>');
			 }
			 if(document.getElementById("grade").value !=""){
				 $("#sp1grade").html('<label></label>');
			 }
			 if(document.getElementById("addresser").value !=""){
				 $("#sp1addresser").html('<label></label>');
			 }
			 if(document.getElementById("addressee").value !=""){
				 $("#sp1addressee").html('<label></label>');
			 }
			 if(document.getElementById("title").value!=""){
				 $("#sp1title").html('<label></label>');
			 }
			 if(document.getElementById("mainbody").value!=""){
				 $("#sp1mainbody").html('<label></label>');
			 }
			 if(document.getElementById("name").value=="") { 
				//alert("规则名不可以为空！");//提示     
				$("#sp1name").html('<label></label>规则名不可以为空');
			 	document.getElementById("name").focus();//焦点 
			 	return false;//返回false 
			 }else if(document.getElementById("grade").value==""){
				$("#sp1grade").html('<label></label>优先级不可以为空');
			 	document.getElementById("grade").focus();//焦点 
			 	return false;//返回false
			 }else if(document.getElementById("addresser").value==""){
				$("#sp1addresser").html('<label></label>收件人地址不可以为空');
			 	document.getElementById("addresser").focus();//焦点 
			 	return false;//返回false
			 }else if(document.getElementById("addressee").value==""){
				$("#sp1addressee").html('<label></label>收件人地址不可以为空');
			 	document.getElementById("addresser").focus();//焦点 
			 	return false;//返回false
			 }else if(document.getElementById("title").value==""){
				$("#sp1title").html('<label></label>发件人地址不可以为空');
			 	document.getElementById("title").focus();//焦点 
			 	return false;//返回false
			 }else if(document.getElementById("mainbody").value==""){
				$("#sp1mainbody").html('<label></label>发件人地址不可以为空');
			 	document.getElementById("title").focus();//焦点 
			 	return false;//返回false
			 }else{
				 return ture;
			 }
		}
		
	</script>
	
	<style type="text/css">
		tr,td { 
			width: 100px;
			height: 35px;
			font-size: 14px;
		}
		input{		
			font-size: 18px;
			height: 20px;
			width: 300px;			
		}
			
    </style>
</head>
<body>

<div class="main">
	<div class="content">
		<!-- ////// -->
		<div class="left structManage">
			<ul class="sidetd">
				<li>
					<a href="/mailspams/mailspam.mail" target="main">更新特征库<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailfilter1.mail">域名黑名单<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailspamlog.mail" target="main">投递失败日志<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailKeyWordRule.mail" class="selected">关键字规则<span class="arrow_right"></span></a>
				</li>
				<!-- 
				<li>
					<a href="/mailspams/mailspamglobal.mail">全局设置<span class="arrow_right"></span></a>
				</li>
				 -->
			</ul>
		</div>
		<div class="right">				
			<div class="bodylist">
				<h1>新建关键字规则</h1>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<hr>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
				<form action="/mailspams/addKeyWordRule.mail" method="post" id="checkKeyWordRule">
					<table border="0">
						<tr height="150">
							<td class="td1">规矩名称:</td>
							<td>
								<input type="text" name="name" id="name" height="30">								
							</td>
							<td><span class="sp1" id="sp1name" style="color: red;"></span></td>
						</tr>
						<tr>
							<td class="td1">优 先 级:</td>
							<td>
								<input type="text" name="grade" id="grade">
							</td>
							<td><span class="sp1" id="sp1grade" style="color: red;"></span></td>
						</tr>
						
						<tr>
							<td class="td1">收 件 人:</td>
							<td>
								<input type="text" name="addresser" id="addresser">
							</td>
							<td><span class="sp1" id="sp1addresser" style="color: red;"></span></td>
						</tr>
						<tr>
							<td class="td1">发 件 人:</td>
							<td>
								<input type="text" name="addressee" id="addressee">
							</td>
							<td><span class="sp1" id="sp1addressee" style="color: red;"></span></td>
						</tr>
						<tr>
							<td class="td1">标　　题:</td>
							<td>
								<input type="text" name="title" id="title">
							</td>
							<td><span class="sp1" id="sp1title" style="color: red;"></span></td>
						</tr>
						<tr>
							<td class="td1">正　　文:</td>
							<td>
								<input type="text" name="mainbody" id="mainbody">
							</td>
							<td><span class="sp1" id="sp1mainbody" style="color: red;"></span></td>
						</tr>
						<tr> 
							<td colspan="2" align="center">
								<input type="submit" name="submit" value="保存" class="button1" onclick="return check();">
								<input type="reset" name="reset" value="重置" class="button1">
							</td>
													
						</tr>
					</table>	
				</form>
			</div>
			
		</div>
	</div>
</div>
</body>
</html>