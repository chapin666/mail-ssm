<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/mailfilter.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		
	});	
	
$(document).ready(function(){           
    $(".addblacklist").click(function(){   
    if($("#address").val().length>0)             
    $('#rewriteform').submit();                                  
   });       
   
   $("#deleteAll").click(function(){           
    $('#deleteAllform').submit();                                  
   });     
   
    $("#deletes").click(function(){           
    $('#deletefrom').submit();                                  
   });   
   
   $('.check-all').click(function(){ 
        if($(".check-all").attr("checked")==true)
        {
         $('.check-opt').attr('checked','checked');
        }
        else
        {
         $('.check-opt').removeAttr('checked');
         }
   });
   
 });  
           
      


 
	
</script>

<style type="text/css">

.logoright
{
  background-color: #D9E3EC;
}

.logoright ul
{
   list-style-type:none;
   height:30px;
   font-size:13px;
}


.logoright li{
		text-align:center;
		float:left;
		padding:8px 15px 8px 15px;
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
</style>

</head>
<body>
<div class="main" >
	<div class="content" style="height: 550px;">
		<div class="left structManage" style="height: 550px;">
		
		    <ul class="sidetd">
				<li>
					<a href="/mailspams/mailspam.mail" target="main">更新特征库<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailfilter1.mail" class="selected">域名黑名单<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/mailspams/mailspamlog.mail" target="main">投递失败日志<span class="arrow_right"></span></a>
				</li>
				<!-- 
				<li>
					<a href="#" target="main">关键字规则<span class="arrow_right"></span></a>
				</li>
				 -->
				<!-- 
				<li>
					<a href="/mailspams/mailspamglobal.mail" target="main">全局设置<span class="arrow_right"></span></a>
				</li>
				 -->
			</ul>
		
		</div>
		<div style="margin: 0px 20px 200px 180px;">
			<div class="con_box">
				<div class="con_head">
					<div class="rightleft" style="margin:0px;">
						<h2 id="structPath" class="title">黑名单</h2>
						<p class="t_info">如果您不希望收到某人的邮件，您可以把他的邮件地址加入到黑名单。</p>
						<p class="t_info">例如：zhangsan@example.com 或 example.com</p>
					</div>
					
				</div>
				
				<div class="logoright" style="height:auto;margin-top: 60px;">
				<ul>
				    <li>
					<a href="#" class="selected">黑名单<span class="arrow_right"></span></a>
				    </li>  
				    <li>
					<a href="/mailspams/mailfilter1.mail">白名单<span class="arrow_right"></span></a>
				    </li>
				   
			    </ul>		  
	    	    </div>
				
				<div class="search" style="height: 30px;">
				    <form id="rewriteform" action="/mailspams/Addblacklist.mail" method="post">
					<input type="text" name="address" id="address" value="" />
					<span class="addblacklist"><label></label>添加黑名单</span>
					<span id="deletes">删除</span>
					</form>
					
					<form id="deleteAllform" action="/mailspams/deleteAll2.mail?tag=1" method="post">
					 <span id="deleteAll">清空黑名单</span>
					</form>
					
				</div>
				<div class="con_body">
					<div class="bodylist">
						<ul>
							<li class="title">
								<span class="listspan1"><input type="checkbox" class="check-all"/>地址</span>
								<span class="listspan3">操作</span>
							</li>
							
								<!-- 批量删除 -->
							<form action="/mailspams/deletes2.mail" id="deletefrom" method="post">
							<s:iterator value="spamlists">					   
							    <li>	    
								<span class="listspan1"><input type="checkbox" name="ids"  value="<s:property value="id"/>" class="check-opt"/><s:property value="address"/></span>
								<span class="listspan3"><a href="/mailspams/deletespamlist2.mail?id=<s:property value="id"/>">删除</a></span>
							    </li>
							</s:iterator>
							</form>
							<!-- 
							
							<li><p style="text-align:center;">没有相关记录</p></li>
							<li>
								<span class="listspan1">example.com</span>
								<span class="listspan3"><a href="">删除</a></span>
							</li>
							<li>
								<span class="listspan1">zhangsan@example.com</span>
								<span class="listspan3"><a href="">删除</a></span>
							</li>
							 -->
						</ul>
						 <c:if test="${not empty spamlists}">
						 <div class="page">
							<span style="float:left;margin-left:20px;">共<font color="red">${count}</font>条</span>
							<span style="float:right;margin-right:20px;">
								<font color="red">${pageNow}</font>/${pageNumber} 页 
								<c:if test="${pageNumber>1}">
									<a href="/mailspams/mailfilter2.mail?i=${pageNow-1}">上一页 </a>&nbsp; <a href="/mailspams/mailfilter2.mail?i=${pageNow+1}">下一页</a>
								</c:if>
							</span>	
						  </div>
						 </c:if>
					</div>
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