var height = $(window).height();
var width = $(window).width();
var label_left = 0;

$(function(){
	//设置主页面的宽度
	label_left = (width - 440)/2+80;
	$(".createlabel").css("left", label_left);
	
});

$(window).resize(function(){
	height = $(window).height();
	width = $(window).width();
	
	//设置主页面的宽度
	label_left = (width - 440)/2+80;
	$(".createlabel").css("left", label_left);
	
});

function updateHost()
{
	$(".createlabel").show();
}

function updateHostAction()
{
	var host = $("#host").val();
	var yhost = $("#yhost").val();
	if(host == ""){
		alert("请填写你的域名信息！");
		return false;
	}
	if(host == yhost && yhost != ""){
		alert("域名信息未做任何修改！");
		return false;
	}
	$("#updateHostForm").submit();
}