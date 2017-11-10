<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="/adm/css/unit.css" rel="stylesheet" type="text/css" />
<link href="/adm/css/subadmin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/adm/js/domain.js"></script>
<script type="text/javascript">
	function addHost(){	
		if($('#host').val().length>0){
			$.ajax({
				type:'post',
				url:'/unit/checkHostNameExist.mail',
				data:{host:$("#host").val()},
				dataType: 'html',
				async: false,
				success:function(data){
		    		if(data==1){	    			
		    			$("#domainsp1").html('<label></label>域名已存在');
				    	return false;
				    }else{
				    	//$('#addtagform').attr('action','/tag/doAddTag3.html');
				    	$("#updateHostForm").submit();
		    			return true;
				    }
				}
			});
			return false;
		}else{
			alert('请输入标签名称');
			return false;
		}
	}
	
	function editHost(host,tag,id){		
		if(tag == 0){
			$('#host').val("");
			$('#edithost').val(tag);
		}else{
			$('#host').val(host);
			$('#edithost').val(tag);
			$('#domainid').val(id);
		}
		$('#updatedomain').show();
	}
	
	function delDomin(id){			
		$('#id').val(id);
		$('#deldomin').show();		
	}
	
	function delHost(){	
		var id = $("#id").val();
		var url = '/unit/delHost.mail?domainid='+id;		
		window.top.main.location.href = url;
	}
</script>

</head>
<body>

<!--彻底删除弹出的选择对话框-->
<div class="createlabel" id="deldomin" style="width: 300px;">
	<div class="createlabel_head" style="cursor:move;">		
		<span>删除确认</span>
		<a class="createlabel_close" href="javascript:;" onclick="$('.createlabel').hide()" title="关闭"></a>
	</div>
	<div class="createlabel_body">
		<div style="margin:8px 0 0;font-weight:bold; "><br/>	
		<dir class="icon_info_b" style="margin: -7px 0px 18px 0px;"></dir>				
			删除后域名将不可恢复，确定要删除？<br/><br/>
			<input type="hidden" name="domainid" id="id"/>
		</div>
	</div>		
	<div class="createlabel_bottom"> 
		<input type="button" class="btn_blue" value="确定" onclick="delHost();" />
		<input type="button" class="btn_gray" value="取消" onclick="$('.createlabel').hide()"/>
	</div>
</div>


<div class="main">
	<div class="content">
		<div class="createlabel" id="updatedomain">
			<div class="createlabel_head" style="cursor:move;">
				<span>修改域名设置</span>
				<a class="createlabel_close" href="javascript:;" onclick="$('.createlabel').hide()" title="关闭"></a>
			</div>
			<form action="/unit/updateHost.mail" method="post" id="updateHostForm">
			<div class="createlabel_body">
				<p>请您输入域名名称:</p>
				<div style="margin:8px 0 0;">
					<input type="text" class="createlabel_input" name="host" id="host" value="" style="width:366px;" />
					<input type="hidden" name="yhost" id="yhost" value="${sessionScope.host}" />
					<input type="hidden" name="edithost" id="edithost"/>
					<input type="hidden" name="domainid" id="domainid"/>					
					<span class="domainsp1" id="domainsp1" style="color: #FF0000;"></span>
				</div>
			</div>
			</form>
			<div class="createlabel_bottom">
				<input type="button" class="btn_blue" value="确定" onclick="addHost();"/>
				<input type="button" class="btn_gray" value="取消" onclick="$('.createlabel').hide()"/>
			</div>
		</div>
		<div class="left structManage" style="height: 750px;">
			<ul class="sidetd">
				<li>
					<a href="/unit/toUnit.mail">单位信息<span class="arrow_right"></span></a>
				</li>
				<li>
					<a href="/unit/toUpdateHost.mail" class="selected">域名管理<span class="arrow_right"></span></a>
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
		<div class="right" style="height: 735px;">
			<div class="contentright">
			
				<div class="con_head">
					<div class="rightleft">
						<h2 id="structPath" class="title">域名管理</h2>
					</div>
					<div class="tool_box">
						<a id="btnAddMbr" class="btn_add" onclick="editHost(0,0)" style="margin:0px;">
							<span class="ico_add"></span> 添加域名
						</a>
					</div>
				</div>
				<div class="logoright">
					<span class="s4">
						<label></label> 成员：${num2}，部门：${num1}，分组：${num3}
					</span>
				</div>
				<s:iterator value="#request.configList">
				<div class="logoright" style="height: 60px;margin-top: 20px;">				
					<c:if test="${empty configList}">
						<span class="s1 domain">暂未设置域名</span>					
					</c:if>	
					<span class="s1 domain">
						<!--<c:if test="${empty sessionScope.host}">暂未设置域名</c:if>-->
						<!-- <c:if test="${not empty sessionScope.host}">${sessionScope.host }</c:if> -->						
							${value}
					</span>	
					<span class="s5 domain3" onclick="delDomin('${id}');">删除</span>				
					<span class="s3 domain3" onclick="editHost('${value}',1,${id})"><label></label>修改</span>					
				</div>
				</s:iterator>
			</div>
		</div>
	</div>
	<div class="footer">
		<p>© 2013 Danwei Mail. All Rights Reserved</p>
	</div>
</div>
</body>
</html>