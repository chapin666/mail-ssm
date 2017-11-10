<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
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
		
		
		function newKey()
		{					
			self.location = "/adm/page/index/addMailKeyWordRule.jsp";
		}
		
		//修改关键字规则状态：0禁用，1启用
		function updateState(n)
		{
			if(!ischeck()){
				return false;
			}else{
				$("#state").val(n);
				$("#checkKeyWordRule").attr("action","/mailspams/updateState.mail");
				$("#checkKeyWordRule").submit();
			}
		}
	
		//删除
		function deleteKey()
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
		
	</script>


    <style type="text/css">
		
		table, td { 
			border:1px solid #ccc; 
			border-collapse:collapse;
		}
		h1 {
			border-bottom: 1px dashed #D4D4D4;
		    font-size: 20px;
		    line-height: 30px;
		    margin: 0 0 20px;
		    overflow: hidden;
		    padding-bottom: 15px;
		    font-weight: bold;
		    text-align: left !important;
		    width: 100%;
		    height: 30px;
		    float: left;
		}	
		
		.button1{
			font-family:Arial, Helvetica, sans-serif;
			background-color: #FCFBFB;
		    background-image: -moz-linear-gradient(center top , #FFFFFF, #E1E1E1);
		    border: 1px solid #D3D3D3;
		    border-radius: 2px 2px 2px 2px;
		    color: #000000;
		    font-size: 12px;
		    height: 26px;
		    line-height: 26px;
		    padding: 0 10px;
		    cursor: pointer;
		    display: inline-block;
		    text-align: center;
		    width: 50px;
		    margin-top: 5px;
		    
		}
		.button1:HOVER{
			font-family:Arial, Helvetica, sans-serif;
			background-color: #EBEBEB;
		    background-image: -moz-linear-gradient(center top , #FFFFFF, #E1E1E1);
		    border: 1px solid #D3D3D3;
		    border-radius: 2px 2px 2px 2px;
		    color: #000000;
		    font-size: 12px;
		    height: 26px;
		    line-height: 26px;
		    padding: 0 10px;
		    cursor: pointer;
		    display: inline-block;
		    text-align: center;
		    width: 50px;
		    margin-top: 5px;
		}
		
		.td1{
			width: 120px;
			text-align: left;
			font-size: 14px;
			color: #A0A0A0;
		}
    </style>



</head>
<body>
<div class="main">
	<div class="content" style="height: 550px;">
		<!-- ////// -->
		<div class="left structManage" style="height: 550px;">
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
				<!-- 
				<li>
					<a href="/mailspams/mailKeyWordRule.mail" class="selected">关键字规则<span class="arrow_right"></span></a>
				</li>
				-->
				<!-- 
				<li>
					<a href="/mailspams/mailspamglobal.mail">全局设置<span class="arrow_right"></span></a>
				</li>
				 -->
			</ul>
		</div>
		
		<div class="right">
		<div class="button " align="left">	
			<h1>关键字规则</h1>
			
			<div class="con_box" align="center" >				
				<div class="search" align="left">
					<form action="/mailspams/mailKeyMore.mail" method="post" name="searchForm" id="pageForm">	
						<input type="button"  onClick="newKey()" value="新建" class="button1"/>
				    		<input type="button"  onClick="updateState(0)" value="禁用" class="button1"/>
				    		<input type="button"  onClick="updateState(1)"value="启用" class="button1"/>
				    		<input type="button"  onClick="deleteKey()" value="删除" class="button1"/>
				    		&nbsp;&nbsp;&nbsp;&nbsp;											
					  	<span class="sname">
					  		匹配名称：<input class="td1" input type="text" name="name" id="name" style="height: 18px;"/>					  	
					  	</span>
						
						<span class="sname">
							最后修改时间：
							<script type="text/javascript">$('#types').val('${logger.types }');</script>							
						    <input class="td1" type="text" name="lastTime" id="lastTime" value="" onFocus="c.showMoreDay=true;c.show(this);" style="height: 18px;"/>						
						    <input type="submit" name="searchBtn" id="searchBtn" value="查询" class="button1" />						    
						</span>	
					  <p>&nbsp;</p>
					  <p>&nbsp;</p>
					</form>
				</div>
			</div>
				<hr>
				<p>&nbsp;</p>
				<p>&nbsp;</p>
			</div>
			<div class="bodylist">
				<form action="" method="post" id="checkKeyWordRule">
				<input type="hidden" name="state" id="state">
				<table width="748" border="0" cellpadding="0" cellspacing="0">
					<tr align="center">
						<td rowspan="2" bgcolor="#E4E4E4" >
							<input type="checkbox" name="ids" value="" id="keyWordRuleids" />
						</td>
						<td rowspan="2" bgcolor="#E4E4E4" >规矩名称</td>
						<td rowspan="2" bgcolor="#E4E4E4" >优先级</td>
						<td rowspan="2" bgcolor="#E4E4E4" >状态</td>
						<td height="26" colspan="4"  bgcolor="#E4E4E4">过滤类型</td>
						<td rowspan="2" bgcolor="#E4E4E4" >最后修改时间</td>
					</tr>
					<tr align="center"> 
						<td width="100" height="25" bgcolor="#E4E4E4">收件人</td>
						<td  width="100" bgcolor="#E4E4E4">发件人</td>
						<td  width="100" bgcolor="#E4E4E4">标题</td>
						<td width="100" bgcolor="#E4E4E4">正文</td>
					</tr>
					
					<s:iterator value="keyWordRule">					
						<tr align="center">
							<td  height="25" >
								<input type="checkbox" name="keyWordRuleids" value="${id }" class="unitidclass" />
							</td>
							<td>
								<s:property value="name"/>
							</td>
							<td>
								<s:property value="grade"/>
							</td>
							<td>
								 ${state==0?"禁止":"启用"}
							</td>
							<td>
								<s:property value="addresser"/>
							</td>
							<td>
								<s:property value="addressee"/>
							</td>
							<td>
								<s:property value="title"/>
							</td>
							<td>
								<s:property value="mainbody"/>
							</td>
							<td>
								<s:property value="lasttime"/>
							</td>
						</tr>
					</s:iterator>
					
				</table>
				</form>
			</div>
			
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			
		<c:if test="${not empty keyWordRule}">
		  <div class="page">
		    	<span style="float:left;margin-left:20px;">共<font color="red">${count}</font>条</span>
			    <span style="float:right;margin-right:20px;">
				<font color="red">${pageNow}</font>/${pageNumber} 页 
				<c:if test="${pageNumber>1}">
					<a href="/mailspams/mailKeyWordRule.mail?i=${pageNow-1}">上一页 </a>&nbsp; 
					<a href="/mailspams/mailKeyWordRule.mail?i=${pageNow+1}">下一页</a>
				</c:if>						
		    	</span>	
		    </div>			
		</c:if>
		
		</div>
	</div>
</div>


</body>
</html>