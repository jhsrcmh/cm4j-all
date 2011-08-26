<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>商家小管家 - 异步任务列表</title>
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
				<jsp:include page="/commons/position_ad.jsp?current_position=异步任务列表" />

				<!-- 正文 -->
				<div id="text" class="contenttext">
				
				<table id="Searchresult" class="table_show">
					<tr style="font-weight: bold;">
						<td colspan="8">异步任务查询列表</td>
					</tr>
					<tr class="c_bold">
						<td width="30px">ID</td>
						<td width="50px">任务类型</td>
						<td width="80px">子任务类型</td>
						<td width="80px">定时执行时间</td>
						<td width="110px">开始时间</td>
						<td width="110px">结束时间</td>
						<td width="40px">状态</td>
						<td>操作</td>
					</tr>
					<!-- 分页结果 -->
					<tr id="not_found" style="font-weight: bold;" align="center">
						<td colspan="8">未查询到相关数据！</td>
					</tr>
				</table>
				
				<table id="promotion_query" style="width: 95%;">
					<tr style="font-weight: bold;" align="center">
						<td colspan="8">
							<!-- 分页标签 -->
							<div id="Pagination" />
							<div class="clear" />
						</td>
					</tr>
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
	
	<!-- jquery分页 -->
	<script src="/app/jquery-pagination.js"></script>
	<link href="/app/css/jquery-pagination.css" rel="stylesheet" type="text/css"/>
	<script src="/app/data.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
		 	// 初始化分页
			var page_size = 2;
			initPagination(page_size);
			
			function initPagination(page_size) {
				// 显示第一页
				page_show(page_size,1);
				
				// 查询总页数
				var total_results = get_total_results();
				
	            // 初始化页码
	            $("#Pagination").pagination(total_results, {
	                callback: pageselectCallback,
	                items_per_page:page_size,
	                prev_text:'上一页',
	                next_text:'下一页',
	            });
	         }
			
			// 分页回调,page_index是从0开始的
			function pageselectCallback(page_index, jq){
				page_show(page_size,page_index + 1);
	        }
			
			// 查询在售商品
			function page_show (page_size,page_no){
				return _ajax({
					url: "/secure/async/list/cron",
					data:{
						page_size : page_size,
						page_no : page_no,
					},
					success: function(json){
						if (checkJson(json)){
							if (json.length > 0){
								// 除了前2行(标题和标题栏)，后面的tr都删了
								$('#Searchresult tr:eq(1) ~ tr').remove();
							}
							
							var html = ['<tr>',
										'<td>#1</td>',
										'<td>#2</td>',
										'<td>#3</td>',
										'<td>#4</td>',
										'<td>#5</td>',
										'<td>#6</td>',
										'<td>#7</td>',
										'<td><a href="#" sid="detail_log">执行记录</a> / <a href="#" sid="invalid_task">禁用</a></td>',
									'</tr>'];
							$(json).each (function(index,item){
								$('#Searchresult').append(
										html.join('')
										.replace("#1",item.taskId)
										.replace("#2",eval("async_task_type." +item.taskType))
										.replace("#3",eval("async_task_sub_type." +item.taskSubType))
										.replace("#4",item.taskCron)
										.replace("#5",new Date(item.startDate).format())
										.replace("#6",new Date(item.endDate).format())
										.replace("#7", eval("async_task_state." +item.state))
								);
							});
							
							// 绑定点击详细日志列表事件
							bind_click_events();
						}
					},
				});
			};
			
			// 绑定
			function bind_click_events(){
				// 禁用活动按钮事件
				$("a[sid='invalid_task']").click(function(){
					var td_line = $(this).parent().parent().children();
					var task_id = $(td_line[0]).html();
					
					_ajax({
						url: "/secure/async/invalid",
						data : {
							task_id : task_id,
						},
						success : function (json){
							if (checkJson(json)){
								if (json){
									dialog("禁用成功","恭喜你，禁用任务[" + task_id + "]成功！");
									return;
								} else {
									dialog_error("禁用任务[" + task_id + "]失败，请重试！");
									return;
								}
							}
						},
					});
				});
				
				// 点击详细日志列表事件
				$("a[sid='detail_log']").click(function(){
					var td_line = $(this).parent().parent().children();
					var task_id = $(td_line[0]).html();
					
					_ajax({
						url: "/secure/async/log/list",
						data : {
							page_size : 10, 
							page_no : 1,
							task_id : task_id,
						},
						success: function(json){
							if (checkJson(json)){
								if (json.length == 0){
									dialog_error("未查询到任务[" + task_id + "]的详细执行记录");
									return;
								}
								
								var html = $('<table class="table_show"><tr class="c_bold"><td>日志ID</td><td width="200px">相关任务数据</td>' 
										+ '<td>执行日期</td><td>执行状态</td></tr></table>');
								var tr = ['<tr>',
											'<td>#1</td>',
											'<td>#2</td>',
											'<td>#3</td>',
											'<td>#4</td>',
										'</tr>'];
								
								$(json).each (function(index,item){
									var line = tr.join('')
									.replace("#1",item.logId)
									.replace("#2",item.execInfo)
									.replace("#3",new Date(item.execDate).format())
									.replace("#4",eval("async_task_log_state." +item.state));
									
									html.append(line);
								});
								dialog ("任务[" + task_id + "]详细执行记录 [仅显示前10条]",html).dialog({
									minWidth : 500,
									minHeight : 300,
								});
							}
						}
					});
				});
			}
			
			
			// 查询异步任务总记录数
			function get_total_results(){
				return _ajax({
					url: "/secure/async/count/cron",
					async : false, // 同步，因为要返回值
					success: function(json){
						if (checkJson(json)){
							return json;
						}
					},
				});
			}
		}); 
	</script>
</body>
</html>