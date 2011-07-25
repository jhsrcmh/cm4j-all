<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>错误消息</title>
<link href="/style/css.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div>
		<h2>错误消息</h2>
		<c:if test="${param.error == 'loginTimeOut' }">用户登陆超时请登陆重试!</c:if>
		${msg }<br /> <input type="button" value="立即关闭"
			onclick="window.opener=null;window.close();" style="cursor: pointer" />
	</div>
	</div>
</body>
</html>
