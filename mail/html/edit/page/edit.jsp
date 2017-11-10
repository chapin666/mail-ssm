<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8" />
	<title>edit</title>
	<link rel="stylesheet" href="/css/edit/default/default.css" />
	<link rel="stylesheet" href="/js/edit/plugins/code/prettify.css" />
	<script type="text/javascript" src="/js/jquery.js"></script>
	<script charset="utf-8" src="/js/edit/kindeditor.js"></script>
	<script charset="utf-8" src="/js/edit/lang/zh_CN.js"></script>
	<script charset="utf-8" src="/js/edit/plugins/code/prettify.js"></script>
	<script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content1"]', {
				cssPath : '/js/edit/plugins/code/prettify.css',
				uploadJson : '/page/edit/upload_json.jsp',
				fileManagerJson : '/page/edit/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					
					document.getElementById("subcontent").onclick=function(){
						self.sync();
						alert($('#content1').val());
						document.forms['example'].submit();
					};
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
			prettyPrint();
		});
	</script>
</head>
<body>
	<form name="example" method="post" action="edit.jsp" id="example">
		<textarea name="content1" id="content1" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;"></textarea>
		<br />
		<input type="button" id="subcontent" value="提交" />
	</form>
</body>
</html>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>