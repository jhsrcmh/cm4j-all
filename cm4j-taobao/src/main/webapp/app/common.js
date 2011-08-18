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

/**
 * 弹出框
 * 
 * @param html
 * @returns
 */
function dialog_error(htmlMsg){
	dialog("错误提醒",htmlMsg);
}
function dialog(title,htmlMsg) {
	$dialog
	.html(htmlMsg)
	.dialog({
	title: title
	})
	.dialog('open');
}

/**
 * 校验ajax请求
 * 
 * @param json
 * @returns {Boolean}
 */
function checkJson(json) {
	if(json.code == -1) {
		dialog('身份失效提醒',"用户未登陆或身份过期，请点击<a href='" + json.objInfo + "'>这里登陆</a>");
		return false;
	} else if(json.code == 0) {
		dialog(json.message);
		return false;
	}
	return true;
}

/**
 * 调用jquery的ajax请求，进行一次封装
 * 1.url添加参数
 * 2.默认提交格式为json
 * 3.封装error
 * 4.封装成功handler，调用者无需判断成功失败
 * 
 * @param url
 * @param data
 * @param handler
 * @returns
 */
function _ajax(json_params){
	$.ajax({
		url: json_params.url + "?is_json=true",
		dataType : json_params.dataType == undefined ? "json" : json_params.dataType,
		data:json_params.data,
		success: function(json){
			if (checkJson(json)){
				json_params.success(json);
			} 
		},
		error: function(error){
			dialog_error ("未知异常：" + error);
			return ;
		},
	});
}

/**
 * 为数组添加删除元素功能
 * 
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
 * 
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
/**
 * 判断字符长度
 */
String.prototype.lengthRange = function (min,max){
	if (this == undefined){
		return false;
	}
	if (this.length < min || this.length > max){
		return false;
	}
	return true;
}
