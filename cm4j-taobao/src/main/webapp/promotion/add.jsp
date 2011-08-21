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
					<form action="/secure/promotion/add" method="post">
						<input type="hidden" name="numIids"/>
						<input type="hidden" name="discountValue"/>
						
						<b>Step1:选择促销商品：</b><br />
						<!-- 测试分页 -->
						<div id="Searchresult" class="productFlow" align="center"></div>
						<div class="clear"></div>
						<div id="Pagination"></div>
						<div class="clear"></div>
						
						<br /><b>Step2:设定标题和描述：</b><br />
						活动名称：<input type="text" name="promotionTitle"/>([2-5]个字符)<br />
						活动描述：<input name="promotionDesc" style="width: 400px;"/>([2-30]个字符)<br />
						
						<br /><b>Step3:优惠类型和额度：</b><br />
						优惠类型：<input type="radio" name="discountType" value="DISCOUNT" checked="checked"/> 打折
								<input type="radio" name="discountType" value="PRICE"/> 特价<br />
								
						优惠额度：<select id="discountV" style="width: 80px;">
									<c:forEach begin="1" end="999" var="result">
										<option value="${result/100 }">&nbsp;&nbsp;${result/100 }折</option>
									</c:forEach>
								</select>
						<span id="discountSpan"><input type="text" id="priceV"/>(精确到小数点后2位，不得超过所选商品原价) <br />
						
						首件打折：<input type="radio" name="decreaseNum" checked="checked" value="0"/> 否(全部商品打折)
								<input type="radio" name="decreaseNum" value="1"/> 是(仅首件打折)
								</span><br />
						<!-- 显示例子 -->
						<span id="discountShowSpan"></span>
					
						<br /><b>Step4:设定活动开始和结束时间：</b><br />
						活动开始时间：<input type="text" name="startTime" readonly="readonly"/> <br />
						活动结束时间：<input type="text" name="endTime" readonly="readonly"/> <br />
						客户分类：
						<select name="tagId" style="width: 80px;">
							<option value="1" selected="selected">所有用户</option>
						</select> (未进行客户分类?点这里指定)
						<br />
						
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
		$(document).ready(function() {
			// 页面初始化
			page_init();
			
		 	// 初始化分页
			var page_size = 2;
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
				$("#Searchresult ul").click(function(){
					if ($(this).attr("class") != 'productSelect'){
						var array = getAllBindedItems();
						if (array.length >= 10){
							dialog_error("一次活动最多选择10个商品");
							return;
						}
						
						// 点击事件，绑定对象
						array.push($(this).attr("item_id"));
						$("#Pagination").data("numIids", $.unique(array));
						// 点击事件，添加边框
						$(this).addClass("productSelect");
					} else {
						// 点击事件，去除边框，删除数据
						$(this).removeClass("productSelect");
						
						$("#Pagination").data("numIids", 
								$("#Pagination").data("numIids")
								.deleElement($(this).attr("item_id"))
								.deleElement("")
						);
					}
				});
			}
			
			/**
			 * 选中当前页所有已绑定数据的标签(模拟click)
			 * @param total_pages 总页数
			 */ 
			function showAllSelectedItems(){
				var array = getAllBindedItems();
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
			function getAllBindedItems(){
				var result = $("#Pagination").data("numIids");
				if (result == undefined){
					result = [];
				}
				return result;
			}
			
			// 点击提交按钮校验
			$("#formSubmit").click(function(){
					// 设置所有选中商品数据
					var total_pages = $("#Pagination").data("total_pages", total_pages);
					var result = getAllBindedItems(total_pages);
					$("input[name='numIids']").val($.unique(result).toString());
					
					var items = $("input[name='numIids']").val();
					if (items == undefined || items == ''){
						dialog_error ("未选中任何商品，请选择参与活动的商品后提交");
						return ;
					}
					
					// 标题和描述
					if (!$(":input[name='promotionTitle']").val().lengthRange(2,5)){
						dialog_error ("活动名称长度限制为[2-5]个字符");
						return ;
					}
					if (!$(":input[name='promotionDesc']").val().lengthRange(2,30)){
						dialog_error ("活动描述长度限制为[2-30]个字符");
						return ;
					}
					
					if ($(":radio[name='discountType'][value='DISCOUNT']").attr("checked") == "checked"){
						if ($("discountV").val() == ''){
							dialog_error ("优惠折扣不允许为空");
							return ;	
						}
						$(":input[name='discountValue']").val($("#discountV").val());
					} else if ($(":radio[name='discountType'][value='PRICE']").attr("checked") == "checked"){
						if (!$("#priceV").val().match(/^(([1-9]\d+)|(\d))(\.\d{1,2})?$/)){
							dialog_error ("优惠额度必须为[精确到小数后2位]的数字");
							return ;
						}
						$(":input[name='discountValue']").val($("#priceV").val());
					}
					
					// 开始结束时间
					if ($(":input[name='startTime']").val() == '' ||$(":input[name='endTime']").val() == ''){
						dialog_error ("请选择活动开始和结束时间");
						return ;
					}
					
					$("form:first").submit();
				}
			);
		}); 
		
		
		/**
		 * 页面初始化
		 */ 
		function page_init (){
			// 打折-优惠选择
			$(":radio[name='discountType']").click(function(){
				if ($(this).val() == 'DISCOUNT'){
					$("#discountV").show();
					$("#discountSpan").hide();
				} else if ($(this).val() == 'PRICE'){
					$("#discountV").hide();
					$("#discountSpan").show();
				}
				$("#discountShowSpan").html('');
			});
			
			// 默认选择第一项,隐藏首件打折选项
			$(":radio[name='discountType']:first").click();
			
			// 显示价格提示
			var showPrice = 10000;
			var showContent = '&nbsp;&nbsp;&nbsp;&nbsp;例子：假设商品原价为' + showPrice + '元，则折后价为：';
			$("#discountV").change(function(){
				var title = '';
				title += showContent + showPrice + '元  * ' + $(this).val() + '折 = ' + (showPrice/10 * $(this).val()) + ' 元<br />';
				$("#discountShowSpan").html(title);
			});
			$("#priceV").change(function(){
				var price = (showPrice - $(this).val());
				var title = '';
				title  += showContent + (price < 0 ? '商品价格超出' + showPrice + '元原价' : showPrice + '元 - ' + $(this).val() + '元 = ' + price + '元') + '<br />';
				$("#discountShowSpan").html(title);
			});
			
			// 日期加载
			$("input[name='startTime']").datetimepicker({
				dateFormat: 'yy-mm-dd',
				minDate: 0,
			});
			$("input[name='endTime']").datetimepicker({
				dateFormat: 'yy-mm-dd',
				minDate: 0,
			});
		}
	</script>
</body>
</html>