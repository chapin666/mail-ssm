<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<style type="text/css">


.logoright ul
{
   list-style-type:none;
   height:30px;
   font-size:13px;
}


.logoright li{
		text-align:center;
		float:left;
		padding:3px 15px 4px 15px;
	}
.logoright li:hover{
		background:#aac8ee;
		color:#FFF;
	}
.logoright a{
		color:#000;
		text-decoration:none;
	}
.logoright a:hover{
		color:#FFF;
		text-decoration:none;	
	}


.jbsz,.cwkz,.ipplkz,.mailplkz,.glsz,.gjsz
{
   height: 200px;
   padding-left: 20px;
   
}

</style>


<script language="javascript" type="text/javascript">
	$(document).ready(function(){
$(".logoright ul li").click(function(){
      if($(this).text()=='基本设置')
      {
         $(".jbsz").show();
         $(".cwkz").hide();
         $(".ipplkz").hide();
         $(".mailplkz").hide();
         $(".glsz").hide();
         $(".gjsz").hide();
      }
       if($(this).text()=='出错次数控制')
      {
         $(".jbsz").hide();
         $(".cwkz").show();
         $(".ipplkz").hide();
         $(".mailplkz").hide();
         $(".glsz").hide();
         $(".gjsz").hide();
      }
      
       if($(this).text()=='IP连接频率控制')
      {
         $(".jbsz").hide();
         $(".cwkz").hide();
         $(".ipplkz").show();
         $(".mailplkz").hide();
         $(".glsz").hide();
         $(".gjsz").hide();
      }
       if($(this).text()=='用户发送邮件频率设置')
      {
         $(".jbsz").hide();
         $(".cwkz").hide();
         $(".ipplkz").hide();
         $(".mailplkz").show();
         $(".glsz").hide();
         $(".gjsz").hide();
      }
       if($(this).text()=='过滤分数阈值设置')
      {
         $(".jbsz").hide();
         $(".cwkz").hide();
         $(".ipplkz").hide();
         $(".mailplkz").hide();
         $(".glsz").show();
         $(".gjsz").hide();
      }
     if($(this).text()=='高级设置')
      {
         $(".jbsz").hide();
         $(".cwkz").hide();
         $(".ipplkz").hide();
         $(".mailplkz").hide();
         $(".glsz").hide();
         $(".gjsz").show();
      }
      
});
});
</script>

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
					<a href="/mailspams/mailspamlog.mail" target="main">投递失败日志<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailKeyWordRule.mail">关键字规则<span class="arrow_right"></span></a>
				</li>
				<!-- 
				<li>
					<a href="/mailspams/mailspamglobal.mail" class="selected">全局设置<span class="arrow_right"></span></a>
				</li>
				-->
			</ul>
		</div>
		<div class="right">
			<div class="contentright">

				<div class="logoright" style="height:auto;">
				<h1>全局设置</h1>
				<ul>
				   <li><a href="#">基本设置</a></li>
				   <li><a href="#">出错次数控制</a></li>
				   <li><a href="#">IP连接频率控制</a></li>
				   <li><a href="#">用户发送邮件频率设置</a></li>
				   <li><a href="#">过滤分数阈值设置</a></li>
				   <li><a href="#">高级设置</a></li>	   
				</ul>		  
	    	    </div>

			</div>
			 <div class="jbsz">
				  
			        <table style="padding-top: 20px;">
			           <tr>
			               <td>收件人个数限制(来自外站):</td>
			               <td><input type="text" name="number"/></td>
			           </tr>
			           
			           <tr>
			               <td>发信人非本站用户时发送邮件大小上限:</td>
			               <td><input type="text" name="big"/> KB （-1表示无限制）</td>
			           </tr>
			           
			           <tr>
			               <td>当Mail From命令的内容为空时:</td>
			               <td><select name="select"><option value="1">接受</option><option value="0">拒绝</option></select></td>
			           </tr>
			       </table>
				
			  </div>
			  
			  <!-- 错误次数控制 -->
			  <div class="cwkz" style="display: none;">
				  
			        <table style="padding-top: 20px;">
			           <tr>
			               <td>每个连接SMTP认证失败次数上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           
			           <tr>
			               <td>每个连接可以接受的命令次数上限:</td>
			               <td><input type="text" name=""/> KB （-1表示无限制）</td>
			           </tr>
			           
			           <tr>
			               <td>每个连接可以接受的命令错误次数上限:</td>
			               <td><input type="text" name=""/> KB （-1表示无限制）</td>
			           </tr>
			       </table>
				
			  </div>
			  
			   <!-- ip连接频率控制-->
			  <div class="ipplkz" style="display: none;">
			       <table style="padding-top: 20px;">
			           <tr>
			               <td>IP同时连接数上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           
			           <tr>
			               <td>IP每15分钟连接数上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           
			           <tr>
			               <td>IP每小时SMTP认证失败次数上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           <tr>
			               <td>IP每小时MAIL命令用户不存在比例上限(单位%):</td>
			               <td><input type="text" name=""/></td>
			           </tr>
			            <tr>
			               <td>IP每小时RCPT命令用户不存在比例上限(单位%):</td>
			               <td><input type="text" name=""/></td>
			           </tr>
			            <tr>
			               <td>IP当天连接数上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			       </table>
			  </div>
			  
			  
			    <!--用户发送邮件频率控制-->
			  <div class="mailplkz" style="display: none;">
			       <table style="padding-top: 20px;">
			           <tr>
			               <td>用户每15分钟发出邮件的总数上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           
			           <tr>
			               <td>用户当天发出邮件的总数上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           
			           <tr>
			               <td>用户每15分钟发出邮件中收信人总人次上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           <tr>
			               <td>用户当天发出邮件中收信人总人次上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           
			       </table>
			  </div>
			  
			   <!--过滤分数阈值设置-->
			   <div class="glsz" style="display: none;">
			       <table style="padding-top: 20px;">
			           <tr>
			               <td>灰名单过滤分值(默认7,使用灰名单技术阻隔邮件):</td>
			               <td><input type="text" name=""/></td>
			           </tr>
			           
			           <tr>
			               <td>标记分值(默认100,邮件主题加上标记前缀):</td>
			               <td><input type="text" name=""/></td>
			           </tr>
			           
			           <tr>
			               <td>隔离分值(默认10,把邮件保存到用户的垃圾邮件文件夹):</td>
			               <td><input type="text" name=""/></td>
			           </tr>
			           <tr>
			               <td>阻断分值(默认100,直接reject此邮件):</td>
			               <td><input type="text" name=""/></td>
			           </tr>
			           <tr>
			               <td>是否过滤本站发出的邮件:</td>
			               <td><select name=""><option value="0">否</option><option value="1">是</option></select></td>
			           </tr>
			       </table>
			  </div>
			
			 <!--高级设置-->
			   <div class="gjsz" style="display: none;">
			       <table style="padding-top: 20px;">
			           <tr>  
			               <td><input type="checkbox"  name="" value="radiobutton" checked>是否允许ip挂起 </td>
			               <td></td>
			           </tr>
			           
			           <tr>
			               <td><input type="checkbox" name="" value="radiobutton" checked>使用关键字过滤</td>
			               <td></td>
			           </tr>
			           
			           <tr>
			               <td><input type="checkbox" name="" value="radiobutton" checked>使用Spam Fingerprint过滤 </td>
			               <td></td>
			           </tr>
			           <tr>
			               <td><input type="checkbox" name="" value="radiobutton" checked>使用SPF查询过滤 </td>
			               <td></td>
			           </tr>
			           <tr>
			               <td>每个连接的发送邮件数上限:</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			           <tr>
			               <td>Fingerprint计数器过滤阈值(默认10,-1表示不使用):</td>
			               <td><input type="text" name=""/>（-1表示无限制）</td>
			           </tr>
			             <tr>
			               <td>mail from发信人必须等于auth认证用户:</td>
			               <td><select name=""><option value="0">否</option><option value="1">是</option></select></td>
			           </tr>
			           
			           <tr>
			               <td>信头中的from发信人必须等于mail from发信人:</td>
			               <td><select name=""><option value="0">否</option><option value="1">是</option></select>(如果选择是，会影响自动转发和直接转发功能)</td>
			           </tr>
			           
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