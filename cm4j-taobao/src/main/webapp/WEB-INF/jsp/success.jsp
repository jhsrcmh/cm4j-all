<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>成功消息</title>
</head>

<body>
	<div>
		<h2>成功消息显示：</h2>
		session:${userSession.top_session }<br /> 
		id:${userSession.visitor_id }<br /> 
		nick:${userSession.visitor_nick }<br /> 
		role:${userSession.visitor_role }<br /> 
		
		<a href="/safe/user_login_out">退出系统</a>
		
		<input type="button" value="立即关闭"
			onclick="window.opener=null;window.close();" style="cursor: pointer" />
	</div>
	</div>
</body>
</html>
