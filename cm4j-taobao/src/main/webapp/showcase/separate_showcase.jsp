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
						<form action="/secure/promotion/add" method="post">
							<input type="hidden" name="numIids"/>
							<input type="hidden" name="numIids_no2"/>
							
							<b>Step1:选择促销商品：</b><br />
							<!-- 测试分页 -->
							<div id="Searchresult" class="productFlow" align="center"></div>
							<div class="clear"></div>
							<div id="Pagination"></div>
							<div class="clear"></div>
							<br />
							
							<br /><input type="button" id="formSubmit" value="提交"/> <input type="reset" value="重置"/>
							
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
		$(document).ready(function() {
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
				var total_results = 0;
				$.ajax({
					url: "/secure/items/list_onsale",
					dataType :"json",
					async : false, // 同步，因为要返回值
					data:{
						page_size : page_size,
						page_no : page_no,
						is_json : true,
					},
					success: function(json){
						if (checkJson(json)){
							total_results = json.total_results;
							
							$('#Searchresult').empty();
							$(json.items).each (function(index,item){
								$('#Searchresult').append('<ul item_id=' + item.numIid + '><li><img width="100px" src="' + item.picUrl + '" /></li><li>' + item.title + '(' + item.price + '元)</li></ul>');
							});
							
							// call 绑定点击商品事件
							bindClickEvent(page_no);
							// call 选中当前页所有已绑定数据的标签(模拟click)
							showAllSelectedItems(total_results);
						}
					},
					error: function(error){
						dialog_error ("未知异常：" + error);
						return ;
					},
				});
				return total_results;
			}
			
			/**
			 * 商品标签点击事件绑定
			 * 选择的商品都绑定在#Pagination对象上，按page_{no}为键存放
			 * @param page_no 当前页
			 */ 
			function bindClickEvent(page_no){
				$("#Searchresult ul").mousedown(function(e){
					if (1 == e.which){
						// 左击
						if ($(this).attr("class") != 'productSelect'){
							// 设置所有选中商品数据
							var total_pages = $("#Pagination").data("total_pages", total_pages);
							if (getAllBindedItems(total_pages).length >= 10){
								dialog_error("一次活动最多选择10个商品");
								return;
							}
							
							// 点击事件，添加边框，记录数据
							$(this).addClass("productSelect");
							
							var array = $("#Pagination").data("page_" + page_no);
							if (array == undefined){
								array = [];
							}
							array.push($(this).attr("item_id"));
							$("#Pagination").data("page_" + page_no, $.unique(array));
						} else {
							// 点击事件，去除边框，删除数据
							$(this).removeClass("productSelect");
							
							var array = $("#Pagination").data("page_" + page_no);
							if (array != undefined){
								array.deleElement($(this).attr("item_id")).deleElement("");
							}
							$("#Pagination").data("page_" + page_no, array);
						}
					} else if (3 == e.which){
						// 右击
					}
				});
			}
			
			/**
			 * 选中当前页所有已绑定数据的标签(模拟click)
			 * @param total_pages 总页数
			 */ 
			function showAllSelectedItems(total_pages){
				var array = getAllBindedItems (total_pages)
				$("#Searchresult ul").each(function(index,element){
					if ($.inArray($(this).attr("item_id"),array) != -1){
						$(this).click();
					}
				});
			}
			
			/**
			 * 设置所有选中商品数据
			 * @param total_pages 总页数
			 */ 
			function getAllBindedItems (total_pages){
				var result = [];
				for (var i=0; i < total_pages; i++) {
					var page_array = $("#Pagination").data("page_" + (i + 1));
					if (page_array != undefined){
						result = $.merge(result,page_array);
					}
				};
				return result;
			}
		}); 
	</script>
</body>
</html>