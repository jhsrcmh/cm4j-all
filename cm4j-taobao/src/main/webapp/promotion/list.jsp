<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				<jsp:include page="/commons/position_ad.jsp?current_position=定向优惠活动" />

				<!-- 正文 -->
				<div id="text" class="contenttext">
				
				<table id="promotion_query" style="width: 95%;">
					<tr style="font-weight: bold;" align="center">
						<td colspan="8">单个商品定向优惠活动查询 [淘宝系统实时查询]</td>
					</tr>
					<tr style="font-weight: bold;" align="center">
						<td colspan="8">
							商品ID：<input type="text" id="num_iid" />
							<select id="promotion_status">
								<option selected="selected">全部</option>
								<option value="ACTIVE">启用</option>
								<option	value="UNACTIVE">禁用</option>
							</select>
							<input id="promotion_submit" type="button" value="提交"/>
						</td>
					</tr>
					<tr style="font-weight: bold;" align="center">
						<td>活动ID</td>
						<td>名称</td>
						<td>类型</td>
						<td>优惠数量</td>
						<td>开始时间</td>
						<td>结束时间</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
					<tr id="not_found_ploys" style="font-weight: bold;" align="center">
						<td colspan="8">请输入商品ID进行查看</td>
					</tr>
				</table>
				<table style="width: 95%;">
					<tr style="font-weight: bold;" align="center">
						<td colspan="8">定向优惠活动展示 [小管家存储活动]</td>
					</tr>
					<tr style="font-weight: bold;" align="center">
						<td>活动ID</td>
						<td>名称</td>
						<td>类型</td>
						<td>优惠数量</td>
						<td>开始时间</td>
						<td>结束时间</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
					<c:forEach var="ploy" items="${result }">
					<tr align="center">
						<td>${ploy.promotionId }</td>
						<td>${ploy.promotionTitle }</td>
						<td>
							${ploy.discountType eq 'DISCOUNT' ? "打折" : "优惠"}
						</td>
						<td>${ploy.discountValue }${ploy.discountType eq 'DISCOUNT' ? "折" : "元"}
						</td>
						<td>${ploy.startDate }</td>
						<td>${ploy.endDate }</td>
						<td>${ploy.status == "ACTIVE" ? "启用" : "禁用" }</td>
						<td><a href="#" sid="unactive">禁用</a></td>
					</tr>
					</c:forEach>
				</table>
				
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
	
	<script type="text/javascript">
		$(document).ready(function(){
			// 查询按钮事件
			$("#promotion_submit").click(function(){
				var num_iid = $("#num_iid").val();
				if (num_iid == '' || num_iid.match(/^\d+$/) == null){
					dialog_error ("请正确输入商品ID再进行查询");
					return ;
				}
				
				// ajax请求
				_ajax({
					url: "/secure/promotion/get",
					data:{
						num_iid : num_iid,
						status : $("#promotion_status").val(),
					},
					success: function(json){
						$("tr[name='show_ploys']").remove();
						
						if (json.total_results == 0){
							$("#not_found_ploys")
								.html('<td colspan="8">对不起，未查询到此商品活动</td>')
								.show();
						} else {
							var html = [];
							$(json.promotions).each(function(index, element){
								var promotion_html = [];
								promotion_html.push('<tr name="show_ploys" style="font-weight: bold;" align="center">');
								promotion_html.push('<td>#1</td>');
								promotion_html.push('<td>#2</td>');
								promotion_html.push('<td>#3</td>');
								promotion_html.push('<td>#4</td>');
								promotion_html.push('<td>#5</td>');
								promotion_html.push('<td>#6</td>');
								promotion_html.push('<td>#7</td>');
								promotion_html.push('<td><a href="#" sid="unactive">禁用</a></td>');
								promotion_html.push('</tr>');
								
								html.push(
									promotion_html.join("")
									.replace("#1",element.promotionId)
									.replace("#2",element.promotionTitle)
									.replace("#3",element.discountType == "DISCOUNT" ? "打折" : "优惠")
									.replace("#4",element.discountValue)
									.replace("#5",new Date(element.startDate).format())
									.replace("#6",new Date(element.endDate).format())
									.replace("#7",element.status == "ACTIVE" ? "启用" : "禁用")		
								);
							});
							$("#not_found_ploys").hide();
							$("#promotion_query").append(html.join(""));
							
							unactive_event();
						}
					},
				});
			});
			
			// 禁用活动按钮事件
			unactive_event ();
			function unactive_event(){
				$("a[sid='unactive']").click(function(){
					var td_line = $(this).parent().parent().children();
					var promotionId = $(td_line[0]).html();
					var promotionTitle = $(td_line[1]).html();
					var status_container = $(td_line[6]);
					
					dialog ("禁用活动确认","是否禁用活动[" + promotionTitle + "] ?")
					.dialog({
						buttons : [{
							text : "确认",
							click : function() {
								// 关闭前一个弹出框
								$(this).dialog("close").dialog("destroy");
								
								// ajax请求
								_ajax({url: "/secure/promotion/unactive",
									data:{
										promotion_id : promotionId,
									},
									success: function(json){
										dialog ("操作成功","[" + promotionTitle + "]" + "活动禁用成功");
										status_container.html("禁用");
									}
								});
							}
						},{
							text : "取消",
							click : function() { $(this).dialog("close");}
						}]
					});
				});
			}
			
		});
	</script>
</body>
</html>