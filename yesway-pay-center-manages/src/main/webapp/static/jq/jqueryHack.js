(function($) {
	// ajax错误处理统一设置
//	$("body").ajaxError(function(e, xhr, settings, exception) {
//		if(settings.fileElementId!="uploadFile"){
//			var msg = "网络繁忙，请稍后重试。";
//			try {
//				var dataObj = eval("(" + xhr.responseText + ")");
//				if (dataObj&&dataObj.errorMsg) {
//					msg = dataObj.errorMsg;
//					if (!dataObj.isSysError) {
//						if($.messager){
//							$.messager({
//								title:"温馨提示",
//								content:msg,
//								icon:"error",
//								cancelButton:false,
//								ok_fn:function(){}
//							});
//						}else{
//							alert(msg);
//						}
//					}
//				}
//				if (dataObj&&dataObj.needLogin) {
//					//查找最上层面，将最上层页面跳转到登录页
//					window.parent.parent.parent.parent.parent.parent.parent.parent.parent.parent.parent.location.href = dataObj.loginPage;
//				}
//			}
//			finally {
//				/*if($.msgr){
//					$.msgr({
//						title:"温馨提示",
//						content:msg,
//						icon:"error",
//						cancelButton:false,
//						ok_fn:function(){}
//					});
//				}else{
//					alert(msg);
//				}*/
//			}
//		}
//	});
	$.ajaxSetup({
		timeout : 90000//统一网络请求超时时间
	});
	//支持jQuery的动态Hover事件
	$.fn.hoverDelay = function(options) {
		var defaults = {
			hoverDuring : 200,
			outDuring : 200,
			hoverEvent : $.noop,
			outEvent : $.noop
		};
		var sets = $.extend(defaults, options || {});
		return $(this).live("hover", function(event) {
			var that = this;
			if (event.type == "mouseenter") {
				clearTimeout(that.outTimer);
				that.hoverTimer = setTimeout(function() {
					sets.hoverEvent.apply(that);
				}, sets.hoverDuring);
			} else {
				clearTimeout(that.hoverTimer);
				that.outTimer = setTimeout(function() {
					sets.outEvent.apply(that);
				}, sets.outDuring);
			}
		});
	};
})(jQuery);