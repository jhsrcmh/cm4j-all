<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 公共代码引用 -->
<!-- jquery -->
<script type="text/javascript" src="/app/jquery-1.6.2.min.js"></script>
<!-- jquery ui -->
<script src="/app/jquery-ui-1.8.15.custom.min.js"></script>
<link href="/app/css/jquery-ui-south-street.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	// 设定弹出框
	var $dialog = $('<div></div>')
		.dialog({
			autoOpen: false,
			modal: true,
			buttons:[{
				text: "关闭",
				click: function() { $(this).dialog("close"); }
			}]
		});
	
	// 校验ajax请求
	function checkJson (json){
		if (json.code == -1){
			$dialog
			.html("用户未登陆或身份过期，请点击<a href='" + json.objInfo + "' target='_blank'>这里登陆</a>")
			.dialog({
				title: '身份失效提醒',
			})
			.dialog('open');
			return false;
		} else if (json.code == 0){
			$dialog
			.html(json.message)
			.dialog({
				title: '错误提醒',
			})
			.dialog('open');
			return false;
		}
		return true;
	}
	
	// 删除元素
	function deleteArray (array,element){
		for (var i=0;i< array.length;i++){
			if (element == array[i]){
				array = array.slice(i,1);
			}
		}
		return array;
	}
	
	alert(deleteArray([1,2,3,4],1));
	
	// string to array,以,分隔
	function str2array(str){
		if (str == undefined){
			return ;
		}
		var array = str.split(",");
		
	}
</script>		


<!-- js引用 -->
<script type="text/javascript" src="/app/png.js"></script>
<!-- 背景透明 -->
<script type="text/javascript">
	DD_belatedPNG.fix('#logo');
</script>