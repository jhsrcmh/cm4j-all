<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>网站标题</title>
<meta name="keywords" content="keywords" />
<meta name="description" content="description" />
<meta name="author" content="yang.hao" />
<link rel="icon" href="imgs/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="imgs/favicon.ico" type="image/x-icon" />

</head>
<body>
	<div id="bg">
		<div id="main">

			<!-- 顶部 -->
			<%@include file="commons/top.jsp"%>
			<!-- 左侧 -->
			<%@include file="commons/left.jsp"%>

			<div id="right">

				<!-- 当前位置和广告位 -->
				<jsp:include page="commons/position_ad.jsp?current_position=关于我们" />

				<!-- 正文 -->
				<div id="text" style="width: 670px;" class="contenttext">
					<p>
						关于我们…… <br /> <br />关于我们…… <br />关于我们…… <br />关于我们…… <br />关于我们……
						<br />关于我们…… <br />关于我们…… <br />关于我们…… <br />关于我们…… <br />关于我们……
						<br />关于我们…… <br />关于我们…… <br />关于我们…… <br />关于我们…… <br />关于我们……
						<br />关于我们…… <br />关于我们…… <br /> abdc
					</p>

				</div>
				<!-- 正文结束 -->

				<%@ include file="commons/bottom_ad.jsp" %>

			</div>
			<div class="clear"></div>
		</div>

		<!-- 底部 -->
		<%@include file="commons/footer.jsp"%>
	</div>

	<!-- 公共代码引用 -->
	<%@include file="commons/comm_include.jsp"%>
</body>
</html>