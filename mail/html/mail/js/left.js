
var height = $(window).height();

$(function(){
	//$(".clearDelMail").easydrag();
	//设置左边栏的高度
	var left_height = height - 117 - 14;
	$(".center").css("height", left_height);
	$(".center ul").css("height", left_height);
	
	
});

$(window).resize(function(){
	//设置左边栏的高度
	height = $(window).height();
	left_height = height - 117 - 14;
	$(".center").css("height", left_height);
	$(".center ul").css("height", left_height);
});

function leftListClick(n,url)
{
	$("#leftList li").removeClass("on");
	if(n <= 100000){
		$("#leftList li:eq("+n+")").addClass("on");
	}		
	window.top.main.location.href = url;
}

/**
function clearDelMail(){
	alert('clearDelMail');
	window.top.main.location.href = url;
}
function clearDel(){
	alert('qq');
	$('.clearDelMail').show();
}
*/
/**清空所有已删邮件*/
function clearDel(){
	var url = "";
	if(window.confirm('是否要清空此文件夹中的邮件？\n清空后邮件将无法恢复。')){
		url = '/email/clearDel.html';		
		$('#clear').attr('action',url);
		$('#clear').submit();//提交表单
		parent.window.frames["main"].location.reload();//从当前页面刷新框架中另一页面		
	}
	leftListClick(3,'/email/delMail.html');
}
/**清空垃圾箱所有邮件*/
function clearRub(){
	var url = "";
	if(window.confirm('是否要清空此文件夹中的邮件？\n清空后邮件将无法恢复。')){
		url = '/email/clearRub.html';
		$('#clear').attr('action',url);
		$('#clear').submit();//提交表单
		parent.window.frames["main"].location.reload();//从当前页面刷新框架中另一页面		
	}
	leftListClick(4,'/email/getRubMail.html');
}


