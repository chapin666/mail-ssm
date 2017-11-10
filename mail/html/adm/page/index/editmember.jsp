<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/member.css" rel="stylesheet" type="text/css" />
<link href="/adm/css/tree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/tree.js"></script>
<script type="text/javascript" src="/adm/js/compatible.js"></script>
<script type="text/javascript" src="/adm/js/member.js"></script>
<script type="text/javascript">
	$(function(){
		$("#mlistdiv").hover(
			function(){
				$('#mlistdiv').show();
			},
			function(){
				$('#mlistdiv').hide();
			}
		);
	});
</script>
<script type="text/javascript">
	$(function(){
		var last = 0 ;
		
		if('${user.unid}'!=''){
			getTreeByID('${user.unid}','layer'); 
				 			  
		}
		
		function getTree(id,pid,temp){
			$.ajax({
				url:'<%=request.getContextPath()%>/unit_ajax/getUnitByJson.mail',
				cache :false,
				data : {
					pid :pid
				},
				type :'Post',
				dataType :'json',
				timeout :20000,
				error : function() {
					alert("出错啦");
				},
				success : function(json) {
					var len = json.length;
					if (len != 0) {
						var a= new Array();  
						last+=1;
						if('1'==pid){        	
							a.push("<select class='inputname' onchange='showChild();'>");
							a.push("<option value='-1'>---请选择---</option>");
							a.push("<option value='1' selected >顶级类别</option>");
							a.push("</select>");
						}
						a.push("<select class='inputname' onchange='showChild();'>");
						$.each(json, function(i) {		   
							var treeid=json[i].id;
							var name=json[i].name;
							if(id==treeid){
								a.push("<option value='");
								a.push(json[i].id);
								a.push("'selected>");
								a.push(json[i].name);
								a.push("</option>");                		
							}else{
								a.push("<option value='");
								a.push(json[i].id);
								a.push("'>");
								a.push(json[i].name);
								a.push("</option>");		   	              		                	                   		
							} 
						});					
						a.push("</select>");

						if(last==1){
						 	$('#layer').append(a.join(""));
						}else{
							$('#layer > select:first').before(a.join(""));
						}
     					if('1'!=pid){
     						getTreeByID(pid,temp);  
     					}
					}					                	     
				}
			});
		}
		
		function getTreeByID(id,temp){
			$.ajax({
				type:'GET',
				url: '<%=request.getContextPath()%>/unit/getUnitPidById.mail',
				data:{id:id},
				dataType: 'html',
				success:function(data){					
					var pid=data;
					getTree(id,pid,temp);					
				}
			});
   	 	}
	});
</script>
</head>
<body>
<div class="main">
	<div class="content">
		<div class="left structManage">
			<div class="c_nav">
				<a class="on bd_r" href="#">
					<span class="icon_diff icon_party"></span>组织架构
				</a>
				<a class="" href="/groups/list.mail">
					<span class="icon_diff icon_group"></span>邮件群组
				</a>
			</div>
			<div class="partytree">
				<script type="text/javascript">
					d = new dTree('d');
				</script>
			 	<s:iterator value="#request.unitlist" id="unl">
					<script type="text/javascript">
						d.add('${id}','${pid}','${name}','/unit/getUserByUnit.mail?id=${id}','','');
					</script>
				</s:iterator>
				<div class="dtree" align="left">
					<script type="text/javascript">
						document.write(d);
					</script>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="con_box">
				<div class="con_head" style="border-bottom:1px dashed #D4D4D4;margin-bottom:10px;height:30px;">
					<div class="rightleft" style="margin:0px;">
						<h2 id="structPath" class="title">编辑成员</h2>
					</div>
				</div>
				<div class="con_body">
					<div class="addbodylist">
						<form action="/user/doEditAdmUser.mail" method="post" id="edituserform">
						<input type="hidden" name="user.id" value="${user.id}"/>
						<ul> 
							<li>
								<span class="span1">成员名称：</span>
								<span><input type="text" name="user.name" id="name" value="${user.name}" class="inputname" /></span>
								<span class="msg" id="msg1"></span>
							</li>
							<li>
								<span class="span1">用 户 名：</span>
								<span>${user.username}@${user.domainname}</span>
							</li>
							<li>
								<span class="span1">用户密码：</span>
								<span><input type="password" name="user.pass" id="pass" value="${user.pass}" class="inputname" /></span>
								<span class="msg" id="msg3"></span>
							</li>
							<li>
								<span class="span1">确认密码：</span>
								<span><input type="password" name="repass" id="password" value="${user.pass}" class="inputname" /></span>
								<span class="msg" id="msg4"></span>
							</li>
							<li>
								<span class="span1">成员部门：</span>
								<span>
									<span id="layer">
									</span>
									<input type="hidden" name="user.unid" id="parent_id" value="${user.unid}">
								</span>
								<span class="msg" id="msg5"></span>
							</li>
							<li>
								<span class="span1">成员电话：</span>
								<span><input type="text" name="user.phone" id="phone" value="${user.phone}" class="inputname" /></span>
								<span class="msg" id="msg6"></span>
							</li>
							<li>
								<span class="span1">成员性别：</span>
								<c:if test="${user.sex eq 1}">
									<span style="margin:7px 5px 0 0;"><input type="radio" name="user.sex" value="1" class="sex" checked></span>
									<span>男　</span>
									<span style="margin:7px 5px 0 0;"><input type="radio" name="user.sex" value="2" class="sex" ></span>
									<span>女</span>
								</c:if>
								<c:if test="${user.sex eq 2}">
									<span style="margin:7px 5px 0 0;"><input type="radio" name="user.sex" value="1" class="sex"></span>
									<span>男　</span>
									<span style="margin:7px 5px 0 0;"><input type="radio" name="user.sex" value="2" class="sex" checked></span>
									<span>女</span>
								</c:if>
							</li>
							<li>
								<span class="span1">成员状态：</span>
								<c:if test="${user.state eq 1}">
									<span style="margin:7px 5px 0 0;"><input type="radio" name="user.state" value="1" class="state" checked></span>
									<span>启用　</span>
									<span style="margin:7px 5px 0 0;"><input type="radio" name="user.state" value="2" class="state" ></span>
									<span>禁用</span>
								</c:if>
								<c:if test="${user.state eq 2}">
									<span style="margin:7px 5px 0 0;"><input type="radio" name="user.state" value="1" class="state"></span>
									<span>启用　</span>
									<span style="margin:7px 5px 0 0;"><input type="radio" name="user.state" value="2" class="state" checked></span>
									<span>禁用</span>
								</c:if>
							</li>
						</ul>
						<p>
							<input type="button" id="updateSubAdminBtn" onclick="updateUser()" class="buttonsub" value="确定" />
						</p>
						</form>
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