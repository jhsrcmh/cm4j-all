<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>网站标题</title>
<meta name="keywords" content="keywords" />
<meta name="description" content="description" />
<meta name="author" content="yang.hao" />
<link rel="icon" href="/imgs/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/imgs/favicon.ico" type="image/x-icon" />

<script type="text/javascript" src="/app/jquery-1.6.2.min.js"></script>
<script src="/app/jquery-ui-1.8.15.custom.min.js"></script>
<link href="/app/css/jquery-ui-1.8.15.custom.css" rel="stylesheet" type="text/css"/>
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
				<jsp:include page="/commons/position_ad.jsp?current_position=添加活动" />

				<!-- 正文 -->
				<div id="text" class="contenttext">
					<p>
						<form action="/secure/promotion/add" method="post">
							<b>Step1:设定标题和描述：</b><br />
							活动名称：<input type="text" name="promotionTitle"/>(最多5个字符)<br />
							活动描述：<input name="promotionDesc" style="width: 400px;"/>(最多30个字符)<br />
							
							<br /><b>Step2:优惠类型和额度：</b><br />
							优惠类型：<input type="radio" name="discountType" value="DISCOUNT"/> 打折
									<input type="radio" name="discountType" value="PRICE"/> 特价<br />
									
							优惠额度：<select name="discountValue" style="width: 80px;">
										<c:forEach begin="1" end="99" var="result">
											<option value="${result/10 }">&nbsp;&nbsp;${result/10 }折</option>
										</c:forEach>
									</select>
							<span id="discountSpan"><input type="text" name="discountValue"/>(格式：0.01，不得超过商品原价) <br />
							
							首件打折：<input type="radio" name="decreaseNum" checked="checked" value="0"/> 否(全部打折)
									<input type="radio" name="decreaseNum" value="1"/> 是(仅首件打折)
									</span><br />
							<!-- 显示例子 -->
							<span id="discountShowSpan"></span>
						
							<br /><b>Step3:设定活动开始和结束时间：</b><br />
							活动开始时间：<input type="text" name="startDate"/> <br />
							活动结束时间：<input type="text" name="endDate"/> <br />
							客户分类：
							<select name="tagId" style="width: 80px;">
								<option value="1" selected="selected">所有用户</option>
							</select> (未进行客户分类?点这里指定)
							<br />
							
							<br /><input type="submit" id="formSubmit" value="提交"/>
						</form>
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
	<%@include file="/commons/comm_include.jsp"%>
	
	<script type="text/javascript">
		// 打折-优惠选择
		$(":radio[name='discountType']").click(function(){
			if ($(this).val() == 'DISCOUNT'){
				$("select[name='discountValue']").removeAttr('disable','');
				$("select[name='discountValue']").show();
				$("input[name='discountValue']").attr('disable','disable');
				$("#discountSpan").hide();
			} else if ($(this).val() == 'PRICE'){
				$("select[name='discountValue']").attr('disable','disable');
				$("select[name='discountValue']").hide();
				$("input[name='discountValue']").removeAttr('disable');
				$("#discountSpan").show();
			}
			$("#discountShowSpan").html('');
		});
		
		$(":radio[name='discountType']:first").click();
		
		// 显示价格提示
		var showPrice = 10000;
		var showContent = '&nbsp;&nbsp;&nbsp;&nbsp;例子：假设商品原价为' + 10000 + '元，则折后价为：';
		$("select[name='discountValue']").change(function(){
			var title = '';
			title += showContent + ' * ' + $(this).val() + '折 = ' + (showPrice/10 * $(this).val()) + ' 元<br />';
			$("#discountShowSpan").html(title);
		});
		$("input[name='discountValue']").change(function(){
			var price = (showPrice - $(this).val());
			var title = '';
			title  += showContent + (price < 0 ? '商品价格超出' + showPrice + '元原价' : showPrice + '元 - ' + $(this).val() + '元 = ' + price + '元') + '<br />';
			$("#discountShowSpan").html(title);
		});
		
		// 日期加载
		$(document).ready(function() {  
		    $("input[name='startDate']").datepicker({  
		    	dateFormat: 'yy-mm-dd @',
		         minDate: '+1',  
		         maxDate: '+10D'  
		    });  
		  }); 
		
	</script>
</body>
</html>