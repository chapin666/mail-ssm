var fileType = "";
var imgFileUrl = "";
var barcount=0;
$(function(){
	uploadImg('attach','/fileUpload.html','file','filename','file','fujianFileShow');
	
});

var barInterval ;
function uploadImg(id,url,name,id1,id2,id3)
{
	new AjaxUpload( id, {
		action : url,
		name : name,
		fileType : fileType,
		single : false,
		data:{
			number:barcount
		},

		onSubmit : function(file, extension) {
			barcount=barcount+1;

			$("#"+id3+"").css("display","block");
			var mailheight = $(".mail").height();
			$(".mail").css("height", mailheight+22);
			
			var fileImg = "<span id='"+barcount+"' style='padding:6px 7px'>"+file+"&nbsp;</span>";
			fileImg+="<span style=''><div id='toolbar"+barcount+"' class='toolbar'> <strong id='bar"+barcount+"' class='bar' style='width:1%;'></strong></div></span>";
			$("#"+id3+"").append(fileImg);
			
			ToolBarStart(barcount);
			//仅允许一个上传
			this.disable();
		},
		onComplete : function(file, response) {
			if (response != null) {
				imgFileUrl = response.split("@");
			}
			var fileName = imgFileUrl[0];
			var fileUrl = imgFileUrl[1];
			var fileUri = imgFileUrl[2];
			var uuid = imgFileUrl[3];
			this.enable();
			
			if(uuid == null || fileUri ==null)
			{
				fileName = "error";
				fileUrl ="error";
				fileUri = "error";
				uuid = "error";
			}
			
			if (fileName != null) {
				var fileImg = "<span><a id='del"+barcount+"' href='javascript:;' style='color: #006699;' onclick='deletec(\""+fileName+"\",\""+fileUri+"\",\""+id1+"\",\""+id2+"\",\""+id3+"\",\""+barcount+"\")'>[删除]<br/></a></span>";
				$("#"+id3+"").append(fileImg);
		
				$("#fujianFileShow").find("#toolbar"+barcount).remove();
				
				$("#"+id2+"").val($("#"+id2+"").val()+"|"+fileName+"*"+fileUri);
					
				var filelistsize = $("#filelistsize").val();
				$("#filelistsize").val(Number(filelistsize)+1);
				
				if(fileName == "error")
				{
					$("#del"+barcount).html("[上传失败]<br/>");
				}else if($("#mailtitle").val().length==0)
				{
					// 主题为空时，添加附件完成后自动为添加主题
					$("#mailtitle").val(fileName);
				}
			}
		}
	});
}

function deletec(fileName,fileUri,id1,id2,id3,uuid) 
{
	$.ajax( {
		url :'/deleteUpFile.html',
		type :'POST',
		dataType :'HTML',
		data :"fileDir=" + fileUri,
		beforeSend : function(xmlhttprequest) {},
		success : function(data) {
			if (data == 1) {
				$("#"+id2+"").val($("#"+id2+"").val().replace("|"+fileName+"*"+fileUri, ""));
				$("#fujianFileShow").find("#"+uuid).remove();
				$("#fujianFileShow").find("#del"+uuid).remove();
				
				var len = $("#"+id3+"").find("span").size();
				if(len < 1){
					$("#"+id3+"").css("display","none");
				}

				var mailheight = $(".mail").height();
				$(".mail").css("height", mailheight-22);

				var filelistsize = $("#filelistsize").val();
				$("#filelistsize").val(Number(filelistsize)-1);
				
			} else {
				alert("删除失败");
			}
		},
		error : function() {
			alert("删除失败!");
		}
	});
}

function ToolBarStart(i) {
	document.getElementById("bar"+i).style.width = parseInt(document.getElementById("bar"+i).style.width) + 1 + "%";

	if (document.getElementById("bar"+i).style.width == "100%") {
		$("#fujianFileShow").find("#toolbar"+i).remove();
		return;
	}
	if (document.getElementById("bar"+i).style.width == "80%") {
		return;
	}
	
	setTimeout("ToolBarStart("+i+")", 80);
}