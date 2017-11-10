<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
	<link href="/adm/css/antiSpam.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/mail/js/jquery.js"></script>
	<script type="text/javascript" src="/mail/js/date.js"></script>
	<style type="text/css">
		input{		
			font-size: 16px;
			height: 16px;
			width: 150px;			
		}
	</style>
</head>
<body>

<div class="main">
	<div class="content" style="height: 550px;">		
		<div class="left structManage" style="height: 550px;">
			<ul class="sidetd">
				<li>
					<a href="/mailspams/mailspam.mail" target="main">更新特征库<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailfilter1.mail">域名黑名单<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailspamlog.mail" target="main" class="selected">投递失败日志<span class="arrow_right"></span></a>
				</li>
				<!-- 
				<li>
					<a href="/mailspams/mailKeyWordRule.mail">关键字规则<span class="arrow_right"></span></a>
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
			<div class="contentright">
				<h1>投递失败日志分析</h1>
				<div  align="right">
					<form action="/mailspams/selectTimeLog.mail" method="post"">	
			        <div>
			        	<span>时间范围：</span>
				       	<span><input type="text" name="startTime" id="times" value="${logger.times }" onfocus="c.showMoreDay=true;c.show(this);"/> --</span> 
				        <span><input type="text" name="endTime" id="times" value="${logger.times }" onfocus="c.showMoreDay=true;c.show(this);"/></span>
				        <span><input type="submit"  value="查询" class="button1" style="margin:0px 2px; height: 23px;" /></span>						
					</div>
					</form>
					<br>
				<table  width="748" border="0" cellpadding="0" cellspacing="0">
			       <tr bgcolor="#E7F4FD" height="26">
		              <th style="border:1px solid #D9D9D9;" width="10%">时间</th>
		              <th style="border:1px solid #D9D9D9;" width="15%">发件人</th>
		              <th style="border:1px solid #D9D9D9;"  width="15%">收件人</th>
		              <th style="border:1px solid #D9D9D9;" width="10%">投递次数</th>
		              <th style="border:1px solid #D9D9D9;" width="15%">邮件主题</th>
			       </tr>
				       
				       <s:iterator value="SendFaileLogs">
				          <tr align="center">
				              <td style="border:1px solid #D9D9D9;" width="10%"><s:property value="date"/></td>
				              <td style="border:1px solid #D9D9D9;" width="15%"><s:property value="frommail"/></td>
				              <td style="border:1px solid #D9D9D9;"  width="15%"><s:property value="tomail"/></td>
				              <td style="border:1px solid #D9D9D9;" width="10%"><s:property value="defercount"/></td>
				              <td style="border:1px solid #D9D9D9;" width="15%"><s:property value="mailtitle"/></td>
				          </tr> 
				       </s:iterator>
				       
				</table>
				<br>
		          <c:if test="${not empty SendFaileLogs}">
				    <div class="page">
				    	<span style="float:left;margin-left:20px;">共<font color="red">${count}</font>条</span>
					    <span style="float:right;margin-right:20px;">
						<font color="red">${pageNow}</font>/${pageNumber} 页 
						<c:if test="${pageNumber>1}">
							<a href="/mailspams/mailspamlog.mail?i=${pageNow-1}">上一页 </a>&nbsp; <a href="/mailspams/mailspamlog.mail?i=${pageNow+1}">下一页</a>
						</c:if>
				    	</span>	
				    </div>
				   </c:if>
				
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