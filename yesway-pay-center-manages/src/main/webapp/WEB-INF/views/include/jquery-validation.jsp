<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
<!--
.tooltip-arrow {
	border-right-color: #f00 !important
}

.red-tooltip+.tooltip>.tooltip-inner {
	background-color: #f00 !important;
}
-->
</style>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/static/plugins/jquery/validation/jquery.validate.js"></script>
<script type="text/javascript">
	/**
	 * 通用验证设置
	 */
	jQuery.extend(jQuery.validator.messages, {
		required : "必选字段",
		remote : "请修正该字段",
		email : "请输入正确格式的电子邮件",
		url : "请输入合法的网址",
		date : "请输入合法的日期",
		dateISO : "请输入合法的日期 (ISO).",
		number : "请输入合法的数字",
		digits : "只能输入整数",
		creditcard : "请输入合法的信用卡号",
		equalTo : "请再次输入相同的值",
		accept : "请输入拥有合法后缀名的字符串",
		maxlength : jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
		minlength : jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
		rangelength : jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
		range : jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max : jQuery.validator.format("请输入一个最大为 {0} 的值"),
		min : jQuery.validator.format("请输入一个最小为 {0} 的值")
	});

	jQuery.validator.addMethod("isPhone", function(value,element) {     
	    var mobile = /^1[3|4|5|8|9]\d{9}$/;     
	    
//	    var tel = /^\d{3,4}-?\d{7,9}$/;     
	    return this.optional(element) || mobile.test(value);     
	    
	}, "请正确填写您的联系电话");  

	jQuery.validator.addMethod("isIdCardNo", function(value, element) {    
		if(!/^\d{17}[0-9X]$/.test(value)){
	          return false;
	     }
	     var Wi = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
	     var Ai = new Array('1','0','X','9','8','7','6','5','4','3','2');
	     var cardNoSum = 0;
	     for (var i=0; i<17; i++)cardNoSum+=value.charAt(i)*Wi[i];
	     var seq = cardNoSum%11;
	     if(Ai[seq] == value[17]){
	          return true;
	     } else {
	          return false;
	     }

	}, "请正确输入您的身份证号码");  
	
	jQuery.validator.setDefaults({
		errorPlacement : function(error, element) {
			//alert(error.html());
			var $element = $(element);
			var $parent = $element.parent();
			$parent.attr("data-toggle", "tooltip");
			$parent.attr("data-placement", "right");
			$parent.attr("data-trigger", "focus");
			$parent.attr("data-html", true);
			$parent.attr("title", error.html());
			$parent.tooltip("show");
			
			$parent.addClass("has-error");
			$parent.addClass("red-tooltip");
			$element.unbind("keyup").bind("keyup", function() {
				$parent.removeAttr("data-toggle");
				$parent.removeAttr("data-placement");
				$parent.removeAttr("data-trigger");
				$parent.removeAttr("data-html");
				$parent.removeAttr("data-title");
				
				$parent.removeClass("has-error");
				$parent.removeClass("red-tooltip");
				$(this).parent().tooltip("destroy");
				event.stopPropagation();
			});
		},
		submitHandler : function(form) {
			form.submit();
		}
	});
</script>