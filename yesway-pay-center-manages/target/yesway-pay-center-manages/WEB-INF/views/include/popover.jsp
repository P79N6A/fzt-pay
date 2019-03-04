<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$("[data-toggle='popover']").popover({
			trigger:(function(item){
				return $(item).attr("data-trigger");//!_trigger || _trigger == "" ? "hover" : _trigger;
			})(this),
			delay:{
				show: 500, 
				hide: 30
			},
			animation:true
		}).unbind("mouseout").bind("mouseout", function(){
			$(this).popover("hide");
		});
	});
</script>	
<style>
<!--
	/* 截断样式 */
	span.text{display:inline-block;overflow: hidden;/* width: 7rem; */text-overflow: ellipsis;white-space: nowrap;}
-->
</style>


<!-- 
详见：http://www.runoob.com/bootstrap/bootstrap-popover-plugin.html
DEMO：
<span class="text" data-toggle='popover' data-trigger="click|hover|focus|manual" data-content="这是弹出框的内容 " title="这是弹出框的标题" style="width:7rem;">text</span> 
 -->
