<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
</head>
<body>
<div class="main">
	<div class="content" style="height: 550px;">
		<!-- ////// -->
		<div class="left structManage" style="height: 550px;">
			<ul class="sidetd">
				<li>
					<a href="/mailspams/mailspam.mail" target="main" class="selected">更新特征库<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailfilter2.mail">域名黑名单<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailspamlog.mail" target="main">投递失败日志<span class="arrow_right"></span></a>
				</li>
				<!--
				<li>
					<a href="/mailspams/mailKeyWordRule.mail">关键字规则<span class="arrow_right"></span></a>
				</li>
				-->
				<!-- 
				<li>
					<a href="/mailspams/mailspamglobal.mail" target="main">全局设置<span class="arrow_right"></span></a>
				</li>
				-->
				
			</ul>
		</div>
		<div class="right">
			<div class="contentright">
				<h1>特征库</h1>
				<div class="logoright" style="height:auto;">
				
				<table  width="748" border="0" cellpadding="0" cellspacing="0">
				       <tr bgcolor="#E7F4FD" height="26" align="center">
				            <th style="border:1px solid #D9D9D9;" width="30%">主机</th>
				            <th style="border:1px solid #D9D9D9;" width="40%">最后更新时间</th>
				            <th style="border:1px solid #D9D9D9;"  width="20%">&nbsp;</th>
				       </tr>
				       <s:iterator value="traitlibs">				       
				          <tr align="center">
				            <td style="border:1px solid #D9D9D9;" width="30%"><s:property value="hostaddress"/></td>
				            <td style="border:1px solid #D9D9D9;" width="40%"><s:property value="date"/></td>
				            <td style="border:1px solid #D9D9D9;"  width="20%">
				            	<img src="/adm/images/index/liveupdate_ico.gif"/>
				            	
				            	<%-- <a href="/mailspams/delTraitlibs.mail?id=${id }">
				            	<img src="/adm/images/index/delete.gif"/></a> --%>
				            	
				            </td>
				          </tr>
				       </s:iterator>
				       
				</table>
				
		          <c:if test="${not empty traitlibs}">
				    <div class="page">
				    	<span style="float:left;margin-left:20px;">共<font color="red">${count}</font>条</span>
					    <span style="float:right;margin-right:20px;">
						<font color="red">${pageNow}</font>/${pageNumber} 页 
						<c:if test="${pageNumber>1}">
							<a href="/mailspams/mailtraitlib.mail?i=${pageNow-1}">上一页 </a>&nbsp; <a href="/mailspams/mailtraitlib.mail?i=${pageNow+1}">下一页</a>
						</c:if>
				    	</span>	
				    </div>
				   </c:if>
				</div>
				<p>&nbsp;</p>
				<h4>使用说明</h4>
				    <p>&nbsp;</p>
			     	<p style="color: #686868;line-height: 20px;">系统在运行中会自动更新邮件特征规则库；</p>
                    <p style="color: #686868;line-height: 20px;">管理员也可通过当前页面手动更新现有邮件特征规则库；</p>
                    <p style="color: #686868;line-height: 20px;"> 更新可使系统拥有最新特征规则库，过滤最近发现的垃圾邮件。</p>
			</div>
		</div>
	</div>
	<div class="footer">
		<p>© 2013 Danwei Mail. All Rights Reserved</p>
	</div>
</div>
</body>
</html>