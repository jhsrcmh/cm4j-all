<%@ page language="java" pageEncoding="UTF-8"%> <!-- 公共代码引用 -->
<!-- jquery -->
<script type="text/javascript" src="/app/jquery-1.6.2.min.js"></script>
<!-- jquery ui -->
<script src="/app/jquery-ui-1.8.15.custom.min.js"></script>
<link href="/app/css/jquery-ui-south-street.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
	// 设定弹出框
	var $dialog = $('<div></div>')
	.dialog({
		autoOpen : false,
		modal : true,
		buttons : [{
			text : "关闭",
			click : function() { $(this).dialog("close");
			}
		}]
	});

	// 校验ajax请求
	function checkJson(json) {
		if(json.code == -1) {
			$dialog
			.html("用户未登陆或身份过期，请点击<a href='" + json.objInfo + "' target='_blank'>这里登陆</a>")
			.dialog({
			title: '身份失效提醒'
			})
			.dialog('open');
			return false;
		} else if(json.code == 0) {
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

	/**
	 * 为数组添加删除元素功能
	 * @return 返回操作后的数组
	 */
	Array.prototype.deleElement = function(element) {
		for(var i = this.length - 1; i >= 0; i--) {
			if($.trim(this[i]) == element) {
				this.splice(i, 1);
			}
		}
		this.reverse();
		return this;
	}
	/**
	 * string to array,以,分隔
	 * @return 返回数组，去空格
	 */
	String.prototype.str2array = function() {
		if(this == '') {
			return;
		}
		var array = this.split(",");
		array.deleElement('');
		return $.unique(array);
	}
</script>
<!-- js引用 -->
<script type="text/javascript" src="/app/png.js"></script>
<!-- 背景透明 -->
<script type="text/javascript">
	DD_belatedPNG.fix('#logo');

</script>