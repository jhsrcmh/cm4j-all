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
				<jsp:include page="/commons/position_ad.jsp?current_position=分批橱窗推荐" />

				<!-- 正文 -->
				<div id="text" class="contenttext">
						<form action="/secure/promotion/add" method="post">
							<input type="hidden" name="numIids_group1"/>
							<input type="hidden" name="numIids_group2"/>
							
							<b>Step1:请选择分批橱窗推荐的商品(只显示在售商品)<br />
								<font color="red">注意：点击鼠标左键选择第一批(选中后为蓝色框)，点击鼠标右键选择第二批(选中后为橙色框)</font>：
							</b><br />
							<!-- 测试分页 -->
							<div id="Searchresult" class="productFlow" align="center"></div>
							<div class="clear"></div>
							<div id="Pagination"></div>
							<div class="clear"></div>
							<br />
							
							<b>Step2：选择分批橱窗推荐时间间隔</b><br />
							两批次轮换时间间隔：<select>
								<option value="8">8小时</option>
								<option value="16">16小时</option>
								<option value="24" selected="selected">1天</option>
								<option value="72">3天</option>
								<option value="168">7天</option>
							</select><br />
							
							<br /><input type="button" id="formSubmit" value="提交"/> <input type="reset" value="重置"/>
							
						</form>

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
	
	<script type="text/javascript">
		// 禁止右键
		document.oncontextmenu = function() {
			event.returnValue = false;
		}
		$(document).ready(function() {
			// 查询可设置的橱窗数量限制 
			_ajax({
				url: "/secure/shop/remainshowcase_get",
				success: function(json){
					$("#Pagination").data("total_showcase", json.allCount);
				}
			});
			
			// 初始化分页
			var page_size = 1;
			initPagination(page_size);
			function initPagination(page_size) {
				// 显示第一页
				var total_results = page_show(page_size,1);
				
				// 获取总页数并绑定
				var total_pages = total_results % page_size == 0 ? total_results / page_size : (Math.floor(total_results / page_size) + 1);
				$("#Pagination").data("total_pages", total_pages);
				
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
					url: "/secure/items/list_onsale",
					async : false, // 同步，因为要返回值
					data:{
						page_size : page_size,
						page_no : page_no,
					},
					success: function(json){
						if (checkJson(json)){
							$('#Searchresult').empty();
							$(json.items).each (function(index,item){
								var html = '<ul item_id="#1"><li><img src="#2" /></li><li>#3(#4元)</li></ul>';
								$('#Searchresult').append(html.replace("#1",item.numIid)
										.replace("#2",item.picUrl)
										.replace("#3",item.title)
										.replace("#4",item.price));
							});
							
							// call 绑定点击商品事件
							bindClickEvent();
							// call 选中当前页所有已绑定数据的标签(模拟click)
							showAllSelectedItems();
							
							return json.total_results;
						}
					},
				});
			}
			
			/**
			 * 商品标签点击事件绑定
			 * 选择的商品都绑定在#Pagination对象上，按numIids为键存放
			 * @param page_no 当前页
			 */ 
			function bindClickEvent(){
				$("#Searchresult ul").mousedown(function(e){
					var group = 1;
					if (1 == e.which){ // 左击
						bindClickEvent_group ($(this),1); // 第一组
					} else if (3 == e.which){ // 右击
						bindClickEvent_group ($(this),2); // 第二组 
					}
				});
			}
			
			function bindClickEvent_group (element,group){
				var class_group = ["productSelect","productSelect_second"];
				var index = group - 1;
				
				// 已被其他组选中
				if (isNotBlank(element.attr("class")) && $.inArray(element.attr("class"),class_group) != index){
					dialog_error("此商品已被其他选为其他组，请先退出其他组再选择");
					return;
				}
				
				// 选中本组
				if (element.attr("class") != class_group[index]){
					var array = getAllBindedItems(group);
					var total_showcase = $("#Pagination").data("total_showcase") == undefined
						? 0 :  $("#Pagination").data("total_showcase");
					
					if (array.length >= total_showcase){
						dialog_error("本批次最多选择total_showcase个商品");
						return;
					}
					// 添加边框样式
					setElementStyle (element,group,"add");
					
					// 绑定数据
					array.push(element.attr("item_id"));
					$("#Pagination").data("numIids_group" + group, $.unique(array));
				} else { // 取消选中本组
					// 去除边框样式
					setElementStyle (element,group,"remove");
					
					// 删除绑定数据
					$("#Pagination").data("numIids_group" + group, 
							$("#Pagination").data("numIids_group" + group)
							.deleElement(element.attr("item_id"))
							.deleElement("")
					);
				}
			}
			
			/**
			 * 对象的css样式操作
			 */
			function setElementStyle (element,group,operation){
				var class_group = ["productSelect","productSelect_second"];
				var index = group - 1;
				
				if ("add" == operation){
					element.addClass(class_group[index]);
				} else if ("remove" == operation){
					element.removeClass(class_group[index]);
				}
			}
			
			/**
			 * 选中当前页所有已绑定数据的标签(模拟click)
			 * @param total_pages 总页数
			 */ 
			function showAllSelectedItems(){
				var group1 = getAllBindedItems(1);
				var group2 = getAllBindedItems(2);
				$("#Searchresult ul").each(function(index,element){
					if ($.inArray($(this).attr("item_id"),group1) != -1){
						// 在第一组
						setElementStyle ($(this),1,"add");
					} else if ($.inArray($(this).attr("item_id"),group2) != -1){
						// 在第二组
						setElementStyle ($(this),2,"add");
					}
				});
			}
			
			/**
			 * 设置所有选中商品数据
			 * @param group 组别 1 2
			 */ 
			function getAllBindedItems(group){
				var result = $("#Pagination").data("numIids_group" + group);
				if (result == undefined){
					result = [];
				}
				return result;
			}
			
			/**
			 * 提交按钮
			 */ 
			$("#formSubmit").click(function(){
				alert("group1:" + getAllBindedItems(1) + "\ngroup2:" + getAllBindedItems(2));
			});
		}); 
		
	</script>
</body>
</html>