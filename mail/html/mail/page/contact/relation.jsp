<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mail/css/gray/main.css" rel="stylesheet" type="text/css" />
<link href="/mail/css/gray/page.css" rel="stylesheet" type="text/css" />
<link href="/mail/css/tree.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/mail/js/jquery.js"></script>
<script type="text/javascript" src="/mail/js/page.js"></script>
<script type="text/javascript" src="/mail/js/tree2.js"></script>
<script type="text/javascript" src="/mail/js/relation.js"></script>
<title>单位邮箱-联系人</title>

</head>
<body>
	<form action="/contact/getContactList.html" method="post" id="pageForm">
	  	<input type="hidden" name="pagenow" id="pagenow" value="1">
		<input type="hidden" name="ugid" value="${ugid}">
		<input type="hidden" name="name" value="${name}">
	</form>
	
	<form action="/units/getUserByUnits.html" method="post" id="pageForm1">
	  	<input type="hidden" name="pagenow1" id="pagenow1" value="1">
	  	<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="name" value="${name}">
	</form>
	
	<form action="/group/getUserByGroup.html" method="post" id="pageForm2">
	  	<input type="hidden" name="pagenow2" id="pagenow2" value="1">
	  	<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="name" value="${name}">
	</form>
	
	
<!--删除弹出的选择对话框-->
<div class="cddelete" id="deletecontact">
	<div class="createlabel_head" style="cursor:move;">		
		<span>删除确认</span>
	</div>
	<div class="createlabel_body">
		<div style="margin:8px 0 0;font-weight:bold; "><br/>	
		<dir class="icon_info_b" style="margin: -7px 12px 8px 0px;"></dir>				
			彻底删除后邮件将不可恢复，确定要删除？<br/><br/>
		</div>
	</div>		
	<div class="createlabel_bottom"> 
		<input type="button" class="btn_blue" value="确定" onclick="deletecontact(true);" />
		<input type="button" class="btn_gray" value="取消" onclick="$('.cddelete').hide()" />
	</div>
</div>

<!--删除组弹出的选择对话框-->
<div class="cddelete" id="deleteugroup">
	<div class="createlabel_head" style="cursor:move;">		
		<span>删除确认</span>
	</div>
	<div class="createlabel_body">
		<div style="margin:8px 0 0;font-weight:bold; "><br/>	
		<dir class="icon_info_b" style="margin: -7px 12px 8px 0px;"></dir>				
			彻底删除后组将不可恢复，确定要删除？<br/><br/>
		</div>
	</div>		
	<div class="createlabel_bottom"> 
		<input type="button" class="btn_blue" value="确定" onclick="deleteugroup('${ugid}',true);" />
		<input type="button" class="btn_gray" value="取消" onclick="$('.cddelete').hide()" />
	</div>
</div>

<div class="msgBoxDIV" id="msgNameDIV">
	<span class="errmsg">请选择联系人</span>
</div>	
<div class="msgBoxDIV" id="msgsysDIV">
	<span class="errmsg">系统忙，请稍后再试</span>
</div>
<div class="main">
	<div class="content">
		<div class="button">
			<c:if test="${empty uname and empty gname}">
				<span><input type="button" value="" name="xjBtn" class="xjBtn" onclick="$('.createlabelmember').show()" /></span>
				<c:if test="${not empty ugid}">
					<span><input type="button" value="" name="bjzBtn" class="bjzBtn" onclick="$('.editlabel').show()"/></span>
				</c:if>
				<c:if test="${empty ugid}">
					<span><input type="button" value="" name="bjzBtn" class="bjzBtnOn" disabled="disabled"/></span>
				</c:if>
				<span class="jgx"></span>
			</c:if>
			<span><input type="button" value="" name="xxBtn" class="xxBtn" onclick="gotowrite();"/></span>
			<c:if test="${empty uname and empty gname}">
				<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="deletecontact();" /></span>
				<c:if test="${not empty ugid}">
					<span><input type="button" value="" name="deletegroupBtn" class="deletegroupBtn" onclick="deleteugroup('${ugid}');" /></span>
				</c:if>
				<c:if test="${empty ugid}">
					<span><input type="button" value="" name="deletegroupBtn" class="deletegroupBtnOn" disabled="disabled"/></span>
				</c:if>
				<span>
					<select id="addtogroup" onchange="addcong(this);">
						<option value="-1">添加到组...</option>
						<c:if test="${not empty ugrouplist}">
							<s:iterator value="#request.ugrouplist" id="ugl">
								<option value="${ugl.id}">　${ugl.name}</option>
							</s:iterator>
						</c:if>
						<option style="color:#ccc" disabled value="-2">-----------</option>
						<option value="-2">　新建组...</option>
					</select>
				</span>
			</c:if>
		</div>
		<div class="createlabel">
			<div class="createlabel_head" style="cursor:move;">
				<span>新建组</span>
				<a class="createlabel_close" href="javascript:;" onclick="addcong(0)" title="关闭"></a>
			</div>
			<form action="/ugroup/doAddUGroup.html" method="post" id="addugroupform">
				<div class="createlabel_body">
					<p>请您输入组名称</p>
					<div style="margin:8px 0 0;">
						<input type="text" class="createlabel_input" value="" name="ugroup.name" id="ugname"/>
						<input type="hidden" value="${sessionScope.user.id}" name="ugroup.userid" id="userid" />
						<span class="sp3" id="msg3" style="color: red;"></span>
					</div>
				</div>
				<div class="createlabel_bottom">
					<input type="button" class="btn_blue" value="确定" onclick="addugroup();"/>
					<input type="button" class="btn_gray" value="取消" onclick="addcong(0)" />
				</div>
			</form>
		</div>
		<div class="editlabel">
			<div class="editlabel_head" style="cursor:move;">
				<span>编辑组</span>
				<a class="editlabel_close" href="javascript:;" onclick="$('.editlabel').hide()" title="关闭"></a>
			</div>
			<form action="/ugroup/doEditUGroup.html" method="post" id="editugroupform">
				<div class="editlabel_body">
					<p>组名称</p>
					<div style="margin:8px 0 0;">
						<input type="text" class="editlabel_input" value="${name}" name="ugroup.name" id="ugname1"/>
						<input type="hidden" value="${name}" id="ugname2"/>
						<input type="hidden" value="${ugid}" name="ugroup.id" id="ugid1" />
						<span class="sp3" id="msg4" style="color: red;"></span>
					</div>
				</div>
				<div class="editlabel_bottom">
					<input type="button" class="btn_blue" value="确定" onclick="editugroup();"/>
					<input type="button" class="btn_gray" value="取消" onclick="$('.editlabel').hide()" />
				</div>
			</form>
		</div>
		<div class="createlabelmember">
			<div class="createlabel_head" style="cursor:move;">
				<span>新建联系人</span>
				<a class="createlabel_close" href="javascript:;" onclick="$('.createlabelmember').hide()" title="关闭"></a>
			</div>
			<form action="/contact/doAddContact.html" method="post" id="addcform">
				<div class="createlabel_body">
					<input type="hidden" name="contact.userid" value="${sessionScope.user.id}" >
					<p>姓名：<input type="text" class="createlabel_input memberinput" value="" name="contact.name" id="name" style="width: 190px;"/>
						<span class="sp3" id="msg1" style="color: red;"></span>
					</p>
					<p>邮件：<input type="text" class="createlabel_input memberinput" value="" name="contact.addr" id="addr" style="width: 190px;"/>
						<span class="sp3" id="msg2" style="color: red;"></span>
					</p>
					<div class="createugrouplist">
					<p style="float:left">分组：</p>
						<ul>
							<s:iterator value="#request.ugrouplist" id="uglss">
								<li><input type="checkbox" name="ugid" value="${uglss.id}" /><label>${uglss.name}</label></li>
							</s:iterator>
						</ul>
					</div>
				</div>
				<div class="createlabel_bottom">
					<input type="button" class="btn_blue" value="确定" onclick="addcontact();"/>
					<input type="button" class="btn_gray" value="取消" onclick="$('.createlabelmember').hide()" />
				</div>
			</form>
		</div>
		<div class="mail" style="height:430px;">
			<ul class="relation">
				<li>
					<b>
						<c:if test="${not empty ugid}">${name}</c:if>
						<c:if test="${not empty uname}">${uname}</c:if>
						<c:if test="${not empty gname}">${gname}</c:if>
						<c:if test="${empty ugid and empty uname and empty gname}">全部联系人</c:if>
					</b>
				</li>
				<li class="title">
					<span class="span1"><input type="checkbox" name="" onclick="allsel(this.checked);" /></span>
					<span class="span2">姓名</span>
					<span class="span3">Email地址</span>
					<span class="span4">分组</span>
				</li>
				<c:if test="${not empty contactList}">
					<form action="/contact/doAddCong.html" method="post" id="addcongform">
						<input type="hidden" name="cgid" id="cgid" value="${cgid}">
						<input type="hidden" name="cname" id="cname" value="${cname}">
						<s:iterator value="#request.contactList" id="ctl">
							<c:if test="${empty uname and empty gname}">
								<li>
									<span class="span7"><input type="checkbox" name="cid" value="${ctl.id}" lang="${ctl.name}" src="${ctl.addr}" class="box" /></span>
									<span class="span5">${ctl.name}</span>
									<span class="span8">${ctl.addr}</span>
									<span class="span6">${ctl.gnames}</span>
								</li>
							</c:if>
							<c:if test="${not empty uname}">
								<li>
									<span class="span7"><input type="checkbox" name="cid" lang="${ctl.name}" src="${ctl.addr}" class="box" /></span>
									<span class="span5">${ctl.name}</span>
									<span class="span8">${ctl.addr}</span>
									<span class="span6">单位成员/${uname}</span>
								</li>
							</c:if>
							<c:if test="${not empty gname}">
								<li>
									<span class="span7"><input type="checkbox" name="cid" lang="${ctl.name}" src="${ctl.addr}" class="box" /></span>
									<span class="span5">${ctl.name}</span>
									<span class="span8">${ctl.addr}</span>
									<span class="span6">邮件群组/${gname}</span>
									<input type="hidden" value="${gname}" id="gname"/>
								</li>
							</c:if>
						</s:iterator>
					</form>
				</c:if>
				<c:if test="${empty contactList}">
					<li style="padding:15px 0px 0px 15px;">
						暂无联系人！
					</li>
				</c:if>
			<c:if test="${not empty contactList}">
			<div class="page">
					<c:if test="${pagenum>1}">
						${pagebar}
					</c:if>
			</div>
			</c:if>
				<li>
					<!--<div class="page">${pagebar}</div>-->
				</li>
			</ul>
			<ul class="lxrList" style="margin-top:12px;">
				<li class="title" style="border-top:none;">邮箱通讯录</li>
				<li>
					<ul>
						<li class="listTitle"><label class="zhankai" style="display: none;"></label><label class="shousuo" style="display: none;"></label>个人地址本</li>
						<li class="list">
							<ul>
								<li><a href="/contact/getContactList.html">全部联系人</a></li>
								<c:if test="${not empty ugrouplist}">
									<s:iterator value="#request.ugrouplist" id="uglss">
										<li><a href="/contact/getContactList.html?ugid=${uglss.id}&name=${uglss.name}"><c:if test="${fn:length(uglss.name)>7}">${fn:substring(uglss.name, 0, 7)}...</c:if><c:if test="${fn:length(uglss.name)<=7}">${uglss.name}</c:if></a></li>
									</s:iterator>
								</c:if>
								<c:if test="${empty ugrouplist}">
									<li><a>(暂无联系人)</a></li>
								</c:if>
							</ul>
						</li>
						<li class="listTitle"><label class="zhankai" style="display: none;"></label><label class="shousuo" style="display: none;"></label>单位地址本</li>
						<li class="list">
							<ul>
								<li style="height:auto;">
									<script type="text/javascript">
										d = new dTree('d');
										d.add('0','-1','','','','');
									</script>
								 	<s:iterator value="#request.unitlist" id="unl">
								 		<c:if test="${empty unitid}">
								 			<script type="text/javascript">
									 			var lenn = '${fn:length(unl.name)}';
												var lenname = '${unl.name}';
												if(lenn>7){
													lenname = '${fn:substring(unl.name, 0, 7)}...';
												}
									 			d.add('${unl.id}','${unl.pid}',''+lenname+'','/units/getUserByUnits.html?id=${unl.id}','','','','','');
											</script>
								 		</c:if>
								 		<c:if test="${not empty unitid}">
									 		<c:if test="${unitid eq unl.id}">
												<script type="text/javascript">
													var lenn1 = '${fn:length(unl.name)}';
													var lenname1 = '${unl.name}';
													if(lenn1>7){
														lenname1 = '${fn:substring(unl.name, 0, 7)}...';
													}
										 			d.add('${unl.id}','${unl.pid}',''+lenname1+'','/units/getUserByUnits.html?id=${unl.id}','','','','','true');
												</script>
											</c:if>
											<c:if test="${unitid > unl.id or unitid < unl.id}">
												<script type="text/javascript">
													var lenn2 = '${fn:length(unl.name)}';
													var lenname2 = '${unl.name}';
													if(lenn2>7){
														lenname2 = '${fn:substring(unl.name, 0, 7)}...';
													}
										 			d.add('${unl.id}','${unl.pid}',''+lenname2+'','/units/getUserByUnits.html?id=${unl.id}','','','','','');
												</script>
											</c:if>
										</c:if>
									</s:iterator>
									<script type="text/javascript">
										document.write(d);
										$('.dTreeNode:eq(0)').hide();
									</script>
								</li>
								<li style="height:auto;" class="mailgroup">
									<img id="mailgroupimg" onclick="showgm();" alt="" src="/adm/images/tree/nolines_plus.gif">
									<a onclick="showgm();" >邮件群组</a>
								</li>
								<s:if test="#request.grouplist.size eq 0">
									<li style="height:auto;" class="mailgroupsmall">
										<a style="display:none;" class="groupmail">(暂无邮件群组)</a>
									</li>
								</s:if>
								<s:else>
								<s:iterator value="#request.grouplist" id="grl">
									<li style="height:auto;" class="mailgroupsmall">
										<a style="display:none;" class="groupmail" href="/group/getUserByGroup.html?id=${grl.id}"><c:if test="${fn:length(grl.name)>7}">${fn:substring(grl.name, 0, 7)}...</c:if><c:if test="${fn:length(grl.name)<=7}">${grl.name}</c:if></a>
									</li>
								</s:iterator>
								</s:else>
							</ul>
						</li>
						<!-- 
						<li class="listTitle"><label class="zhankai" style="display: none;"></label><label class="shousuo"></label>工具箱</li>
						<li class="list">
							<ul>
								<li>导入联系人</li>
								<li>导出个人地址本</li>
								<li>导出企业地址本</li>
							</ul>
						</li>
						-->
					</ul>
				</li>
			</ul>
		</div>
		<div class="button">
			<c:if test="${empty uname and empty gname}">
				<span><input type="button" value="" name="xjBtn" class="xjBtn" onclick="$('.createlabelmember').show();"/></span>
				<c:if test="${not empty ugid}">
					<span><input type="button" value="" name="bjzBtn" class="bjzBtn" onclick="$('.editlabel').show()"/></span>
				</c:if>
				<c:if test="${empty ugid}">
					<span><input type="button" value="" name="bjzBtn" class="bjzBtnOn" disabled="disabled"/></span>
				</c:if>
				<span class="jgx"></span>
			</c:if>
			<span><input type="button" value="" name="xxBtn" class="xxBtn" onclick="gotowrite();"/></span>
			<c:if test="${empty uname and empty gname}">
				<span><input type="button" value="" name="deleteBtn" class="deleteBtn" onclick="deletecontact();"/></span>
				<c:if test="${not empty ugid}">
					<span><input type="button" value="" name="deletegroupBtn" class="deletegroupBtn" onclick="deleteugroup('${ugid}');" /></span>
				</c:if>
				<c:if test="${empty ugid}">
					<span><input type="button" value="" name="deletegroupBtn" class="deletegroupBtnOn" disabled="disabled"/></span>
				</c:if>
				<span>
					<select id="addtogroup1" onchange="addcong(this);">
						<option value="-1">添加到组...</option>
						<c:if test="${not empty ugrouplist}">
							<s:iterator value="#request.ugrouplist" id="ugls">
								<option value="${ugls.id}">　${ugls.name}</option>
							</s:iterator>
						</c:if>
						<option style="color:#ccc" disabled value="-2">-----------</option>
						<option value="-2" onclick="$('.createlabel').show()">　新建组...</option>
					</select>
				</span>
			</c:if>
		</div>
	</div>
</div>
</body>
</html>