var height = $(window).height();
var width = $(window).width();
var label_left = 0;

$(function(){
	
	//设置主页面的宽度
	label_left = (width - 440)/2+80;
	$(".createlabel").css("left", label_left);
	
	$("#mlistdiv").hover(
		function(){
			$('#mlistdiv').show();
		},
		function(){
			$('#mlistdiv').hide();
		}
	);
	
	$("#unitids").click(function(){
		if($(this).attr("checked")){
			$(".unitidclass").attr("checked", true);
		}else{
			$(".unitidclass").attr("checked", false);
		}
	});
	
});

$(window).resize(function(){
	height = $(window).height();
	width = $(window).width();
	
	//设置主页面的宽度
	label_left = (width - 440)/2+80;
	$(".createlabel").css("left", label_left);
	
});

function deleteUnit(id)
{
	if(window.confirm('确定删除吗？')){
		location.href = '/unit/deleteUnit.mail?id='+id;
	}
}

function showRename()
{
	$("#unitrenametitle").html('单位部门重命名');
	$("#unitname").val($("#dyunitname").val());
	$("#unitid").val($("#dyunitid").val());
	$("#updateUnitForm").attr("action","/unit/doEditUnit.mail");
	$(".createlabel").hide();
	$("#unitrename").show();
}

function showCreate()
{
	$("#unitrenametitle").html('新建单位部门');
	$("#unitname").val('');
	$("#unitid").val($("#dyunitid").val());
	$("#updateUnitForm").attr("action","/unit/doAddUnit.mail");
	$(".createlabel").hide();
	$("#unitrename").show();
}

function updateUnitAction()
{
	var unitname = $("#unitname").val();
	if(unitname == ""){
		alert("部门名称不能为空");
		return false;
	}else if(unitname.length>40){
		alert("部门名称太长");
		return false;
	}else{
		if(unitname.indexOf('<')>-1||unitname.indexOf('>')>-1||unitname.indexOf('@')>-1){
			alert("部门名称不能包含特殊字符");
			return false;
		}else{
			if($("#unitrenametitle").html() == '单位部门重命名'){
				if(unitname != $("#dyunitname").val()){
					$.ajax({
						type:'post',
						url:'/unit/checkNameExist.mail',
						data:{name:unitname},
						dataType: 'html',
						async: false,
						success:function(data){
						    if(data==1){
								alert("此部门已存在");
								return false;
						    }else{
						    	$("#updateUnitForm").submit();
						    }
						}
					});
				}else{
					alert("部门名称未做修改");
					return false;
				}
			}else{
				$.ajax({
					type:'post',
					url:'/unit/checkNameExist.mail',
					data:{name:unitname},
					dataType: 'html',
					async: false,
					success:function(data){
					    if(data==1){
							alert("此部门已存在");
							return false;
					    }else{
					    	$("#updateUnitForm").submit();
					    }
					}
				});
			}
		}
	}
}

function ischeck()
{
	var isunit = false;
	$(".unitidclass").each(function(i){
		if($(this).attr("checked")){
			isunit = true;
			return false;
		}
	});
	
	if(!isunit){
		//alert("请至少选择一项");
		$(".msgBoxDIV").show();
		setTimeout("($(\".msgBoxDIV\").hide())",4000);
		return false;
	}else{
		return true;
	}
}

function moveUnit()
{
	if(!ischeck()){
		return false;
	}else{
		$(".createlabel").hide();
		$("#moveunitlabel").show();
	}
}

function moveUnitAction()
{
	var userunid = $("#parent_id").val();
	$("#userunidform").val(userunid);
	$("#checkunitform").attr("action","/unit/editUnitId.mail");
	$("#checkunitform").submit();
}

function moveGroups()
{
	if(!ischeck()){
		return false;
	}else{
		$(".createlabel").hide();
		$("#movegroupslabel").show();
	}
}

function moveGroupsAction()
{
	var groupsids = $("#groupsids").val();
	$("#userunidform").val(groupsids);
	$("#checkunitform").attr("action","/unit/editGroups.mail");
	$("#checkunitform").submit();
}


function updateState(n)
{
	if(!ischeck()){
		return false;
	}else{
		$("#userunidform").val(n);
		$("#checkunitform").attr("action","/unit/editState.mail");
		$("#checkunitform").submit();
	}
}

function deleteUser()
{
	if(!ischeck()){
		return false;
	}else{
		if(window.confirm('删除用户会删除该用户对应的所有邮件数据，确定删除吗？')){
			$("#checkunitform").attr("action","/unit/deleteUsers.mail");
			$("#checkunitform").submit();
		}
	}
}


function showChild() {
	
   var obj=event.srcElement;
   var s1=$(obj).nextAll("select");
   
   s1.each(function(i){
	   $(this).remove();
   });
  
   var value1=$(obj).val();
   	   
   if(value1==-1){
	   if($(obj).prev().val()==undefined){
		   $('#parent_id').val(-1);
	   }else{
		   $('#parent_id').val($(obj).prev().val());
	   }
   }else{
	   $('#parent_id').val(value1);
   }
   
   $.ajax({
	   	url:'/unit_ajax/getUnitByJson.mail',
		cache :false,
		data : {pid :value1},
		type :'Post',
		dataType :'json',
		timeout :20000,
		error : function() {
			alert("出错啦");
		},
		success : function(json) {
			var len = json.length;
			if (len != 0) {
				$("<select onchange='showChild()' class='inputname'></select>").appendTo("#layer");
				$("<option value='-1'>---请选择---</option>").appendTo("#layer>select:last");
				$.each(json, function(i){
					$("<option value="+json[i].id+">&nbsp;"+json[i].name+ "</option>").appendTo("#layer>select:last");
				});
			}
		}
	});
}
