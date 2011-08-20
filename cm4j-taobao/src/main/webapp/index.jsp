<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>商家小管家 - 创建定向优惠活动</title>
<meta name="keywords" content="keywords" />
<meta name="description" content="description" />
<meta name="author" content="yang.hao" />
<link rel="icon" href="/imgs/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/imgs/favicon.ico" type="image/x-icon" />

<%@include file="/commons/comm_inc_top.jsp" %>

</head>
<body>
	<div id="bg">
		<div id="main">

			<!-- 顶部 -->
			<%@include file="/commons/top.jsp"%>
			<!-- 左侧 -->
			<%@include file="/commons/left.jsp"%>

			<div id="right">

				<!-- 当前位置和广告位 -->
				<jsp:include page="/commons/position_ad.jsp?current_position=创建定向优惠活动" />

				<!-- 正文 -->
				<div id="text" class="contenttext">
					<p>
						session:${sessionScope.userSession.top_session }<br /> 
						id:${userSession.visitor_id }<br /> 
						nick:${userSession.visitor_nick }<br /> 
						role:${userSession.visitor_role }<br /> 
					</p>

				</div>
				<!-- 正文结束 -->

				<%@ include file="/commons/bottom_ad.jsp" %>

			</div>
			<div class="clear"></div>
		</div>

		<!-- 底部 -->
		<%@include file="/commons/footer.jsp"%>
	</div>

	<!-- 公共代码引用 -->
	<%@include file="/commons/comm_inc_bottom.jsp" %>
	
	<style>
		/* css for timepicker */
		.ui-timepicker-div .ui-widget-header{ margin-bottom: 8px; }
		.ui-timepicker-div dl{ text-align: left; }
		.ui-timepicker-div dl dt{ height: 25px; }
		.ui-timepicker-div dl dd{ margin: -25px 0 10px 65px; }
		.ui-timepicker-div td { font-size: 90%; }
	</style>
	
	<!-- 时间插件 -->
	<script src="/app/jquery-ui-timepicker-addon.js"></script>
	
	<!-- jquery分页 -->
	<script src="/app/jquery-pagination.js"></script>
	<link href="/app/css/jquery-pagination.css" rel="stylesheet" type="text/css"/>
	
	</body>
</html>