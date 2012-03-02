/**
 * 弹出框
 * 
 * @param html
 * @returns
 */
function dialog_error(htmlMsg){
	return dialog("错误提醒",htmlMsg);
}
function dialog(title,htmlMsg) {
	if (htmlMsg == undefined){
		htmlMsg = "未知消息异常，如有需要，请联系管理员！";
	}
	return $('<div></div>')
	.html(htmlMsg)
	.dialog({
		title: title,
		autoOpen : false,
		modal : true,
		buttons : [{
			text : "关闭",
			click : function() { 
				$(this).dialog("close").dialog("destroy").empty().remove();
			}
		}]
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
	if(-1 == json.code) {
		dialog('身份失效提醒',"用户未登陆或身份过期，请点击<a href='" + json.objInfo + "'>这里登陆</a>");
		return false;
	} else if(0 == json.code) {
		dialog_error(json.message);
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
 * @returns 可以带返回值[success方法的返回]，但此时必须是同步调用[async = false]
 */
function _ajax(json_params){
	var result = undefined;
	$.ajax({
		url: json_params.url + "?is_json=true",
		async : json_params.async == undefined ? true : json_params.async,
		dataType : json_params.dataType == undefined ? "json" : json_params.dataType,
		data:json_params.data,
		success: function(json){
			if (checkJson(json)){
				result = json_params.success(json);
			} 
		},
		error: function(error){
			dialog_error ("未知异常：" + error);
			return ;
		},
	});
	return result;
}

/* =======================utils====================== */
/**
 * 判断字符串是否为空
 * 
 * @returns Boolean
 */
function isNotBlank(element) {
	if (element == undefined){
		return false;
	} else if ($.trim(element) == ''){
		return false;
	}
	return true;
}

/* =======================prototype function====================== */
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
 * string to array,以,分隔，去重复
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
/**
 * 将日期转换为字符串格式：yy-mm-dd HH:mm:ss
 */
Date.prototype.format = function (){
	return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate() + " " 
	+ this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds(); 
}