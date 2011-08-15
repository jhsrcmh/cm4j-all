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
				<jsp:include page="/commons/position_ad.jsp?current_position=添加活动" />

				<!-- 正文 -->
				<div id="text" class="contenttext">
					<p>
						<form action="/secure/promotion/add" method="post">
							<input type="hidden" name="items" id="items"/>
							
							<b>Step1:选择促销商品：</b><br />
							<!-- 测试分页 -->
							<div id="Searchresult" class="productFlow" align="center"></div>
							<div class="clear"></div>
							<div id="Pagination"></div>
							<div class="clear"></div>
							
							<br /><b>Step2:设定标题和描述：</b><br />
							活动名称：<input type="text" name="promotionTitle"/>(最多5个字符)<br />
							活动描述：<input name="promotionDesc" style="width: 400px;"/>(最多30个字符)<br />
							
							<br /><b>Step3:优惠类型和额度：</b><br />
							优惠类型：<input type="radio" name="discountType" value="DISCOUNT" checked="checked"/> 打折
									<input type="radio" name="discountType" value="PRICE"/> 特价<br />
									
							优惠额度：<select name="discountValue" style="width: 80px;">
										<c:forEach begin="1" end="99" var="result">
											<option value="${result/10 }">&nbsp;&nbsp;${result/10 }折</option>
										</c:forEach>
									</select>
							<span id="discountSpan"><input type="text" name="discountValue"/>(精确到小数点后2位，不得超过商品原价) <br />
							
							首件打折：<input type="radio" name="decreaseNum" checked="checked" value="0"/> 否(全部商品打折)
									<input type="radio" name="decreaseNum" value="1"/> 是(仅首件打折)
									</span><br />
							<!-- 显示例子 -->
							<span id="discountShowSpan"></span>
						
							<br /><b>Step4:设定活动开始和结束时间：</b><br />
							活动开始时间：<input type="text" name="startDate" readonly="readonly"/> <br />
							活动结束时间：<input type="text" name="endDate" readonly="readonly"/> <br />
							客户分类：
							<select name="tagId" style="width: 80px;">
								<option value="1" selected="selected">所有用户</option>
							</select> (未进行客户分类?点这里指定)
							<br />
							
							<br /><input type="button" id="formSubmit" value="提交"/> <input type="reset" value="重置"/>
							
							<!-- 测试显示商品
							<div class="productFlow" align="center">
								<ul>
									<li><img src="/imgs/address.gif" /></li>
									<li>商品名</li>
									<li>100元</li>
								</ul>
								<ul>
									<li><img src="/imgs/address.gif" /></li>
									<li>商品名</li>
									<li>100元</li>
								</ul>
							</div> -->
							
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
			// 页面初始化
			page_init();
			
		 	// 初始化分页
			var page_size = 1;
			initPagination();
			function initPagination() {
				// 显示第一页
				var total_results = showItems(page_size,1);
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
				showItems(page_size,page_index + 1);
            }
			
			// 查询在售商品
			function showItems (page_size,page_no){
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
							showAllSelectedItems(page_no);
						}
					},
					error: function(error){
						alert('error:' + error);
					},
				});
				return total_results;
			}
			
			/**
			 * 商品标签点击事件绑定
			 * 选择的商品都绑定在#items对象上，按page_{no}为键存放
			 * @param page_no 当前页
			 */ 
			function bindClickEvent(page_no){
				$("#Searchresult ul").click(function(){
					if ($(this).attr("class") != 'productSelect'){
						// 点击事件，添加边框，记录数据
						$(this).addClass("productSelect");
						
						var array = $("#items").data("page_" + page_no);
						if (array == undefined){
							array = [];
						}
						array.push($(this).attr("item_id"));
						$("#items").data("page_" + page_no, $.unique(array));
					} else {
						// 点击事件，去除边框，删除数据
						$(this).removeClass("productSelect");
						
						var array = $("#items").data("page_" + page_no);
						if (array != undefined){
							array.deleElement($(this).attr("item_id")).deleElement("");
						}
						$("#items").data("page_" + page_no, array);
					}
				});
			}
			
			/**
			 * 选中当前页所有已绑定数据的标签(模拟click)
			 * @param page_no 当前页
			 */ 
			function showAllSelectedItems (page_no){
				var array = $("#items").data("page_" + page_no);
				if (array != undefined){
					$(array).each(function(index,element){
						// css("border","1px solid");
						$("#Searchresult ul[item_id='"+ element +"']").click();
					});
				}
			}
			
			/**
			 * 设置所有选中商品数据
			 * @param total_page_size 总页数
			 */ 
			function setAllSelectedItems (total_page_size){
				var result = [];
				for (var i=0; i < total_page_size; i++) {
					var page_array = $("#items").data("page_" + (i + 1));
					if (page_array != undefined){
						result = $.merge(result,page_array);
					}
				};
				$("#items").val($.unique(result).toString());
			}
		}); 
		
		
		/**
		 * 页面初始化
		 */ 
		function page_init (){
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
			
			// 默认选择第一项,隐藏首件打折选项
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
			$("input[name='startDate']").datetimepicker({
				dateFormat: 'yy-mm-dd',
				minDate: '+0',
			});
			$("input[name='endDate']").datetimepicker({
				dateFormat: 'yy-mm-dd',
				minDate: '+0',
			});
		}
	</script>
</body>
</html>