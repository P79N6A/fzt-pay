/**
 @author Yujianping
 @tel 13716506521
 @date 2013-5-30
 @param(int) width 提示框的宽度 默认值为auto
 @param(int) top 窗口距离顶部的位置 默认垂直居中
 @param(int) left 窗口距离左边的位置 默认水平居中
 @param(string) model 默认值为"tips",分别为"tips"(成功提示)、"message"(确认)、"window"(自定义内容)
 @param(string) tipsStatus 默认值为"success",分别为"success"(成功提示)、"error"(错误)
 @param(bool) multiple(model为message时有效) 默认值为false
 @param(string) position 默认值为"none",tl(上左)/tr(上右)/rt(右上)/rb(右下)/bl(下左)/br(下右)/lt(左上)/lb(左下)
 @param(string) content 提示框显示的内容(model为tips和message有效) 默认为：“恭喜您，操作已成功显示的正文内容”
 @param(bool) autoclose 是否自动关闭  默认为true
 @param(int) timeout(autoclose为true时有效) 显示时长 默认为3000,3000毫秒之后自动隐藏
 @example   使用示例
 @
 @    $(".test1").toolTip({
 @		width:150,
 @		model:"tips",
 @      tipsStatus:'success',
 @      poisition:"br",
 @      content:"恭喜您，操作已成功显示的正文内容",
 @		timeout:3000,
 @      top:窗口距离顶部的位置,
 @      left:窗口距离左边的位置
 @    });
 @    $(".test2").toolTip({
 @		width:150,
 @		model:"message",
 @      multiple:false,
 @      poisition:"br",
 @      content:"恭喜您，操作已成功显示的正文内容",
 @      top:窗口距离顶部的位置,
 @      left:窗口距离左边的位置
 @    });
 @    $(".test3").toolTip({
 @		width:150,
 @		model:"window",
 @      poisition:"br",
 @      autoclose:true
 @		timeout:3000,
 @      top:窗口距离顶部的位置,
 @      left:窗口距离左边的位置
 @    });
 **/
;(function($){
	
	// 定义数字变量
	var num = 100001;

	$.fn.extend({
		toolTip:function(data){
	
			// 默认变量以及定义相关变量
			var defaults = {
					width:"auto",
					top:null,
					left:null,
					model:"tips",
					tipsStatus:"success",
					multiple:false,
					position:"none",
					autoclose:true,
					timeout:3000,
					content:"恭喜您，操作已成功"
				},
				opts = $.extend(defaults,data || {}),
				model = opts["model"],
				tipsStatus = opts["tipsStatus"],
				multiple = opts["multiple"],
				position = opts["position"],
				_top = parseInt(opts["top"],10),
				_left = parseInt(opts["left"],10),
				nonum = /\D/,
				autoclose = opts["autoclose"],
				timeout = opts["timeout"],
				content = opts["content"],
				_html = '',
				_htmlCon = '',
				_htmlConWindow = '',
				_htmlPos = '',
				tipsStyle = null,tipsChildStyle = null,tipsTxt = null,
				width,top,left,
				_this = this;
			
			
			// 同一属性标签是否显示多个
			if(!multiple && !!$("div[name='message']") && model == "message"){
				$("div[name='message']").remove();
			}
			/* 暂时去掉此功能
			else if(!multiple && !!$("div[name='tips']") && model == "tips"){
				$("div[name='tips']").remove();
			}else if(!multiple && !!$("div[name='window']") && model == "window"){
				$("div[name='tips']").remove();
			}
			*/

			// 设置宽度
			if(opts["width"] == "auto"){
				width = "auto";
			}else{
				width = parseInt(opts["width"],10) -10;
			}
			
			// 设置tips类型
			if("tips" === model){
				
				switch(tipsStatus){
					
					case "success":
						tipsStyle = "T_success";
						tipsChildStyle = "T_successIcon";
						tipsTxt = "成功";
						break;
					case "error":
						tipsStyle = "T_error";
						tipsChildStyle = "T_errorIcon";
						tipsTxt = "失败";
						break;
				}
			}

			// 消息窗口模型
			switch(model){
				case "tips":
					_htmlCon = '<div class="' + tipsStyle + '"><span class="' + tipsChildStyle + '">' + tipsTxt + '</span><div class="T_successTxt">' + content + '</div></div>';
					break;
				case "message":
					_htmlCon = '<div class="T_msg"><h3 class="T_msgtitle">'+content+'</h3><div class="T_msgcontent">'+
								'<a href="javascript:;" class="T_msgBtn2" name="messageOK' + num + '">确定<s class="sl"></s><s class="sr"></s></a>'+
								'<a href="javascript:;" class="T_msgBtn1" name="messageCancel' + num + '">取消<s class="sl"></s><s class="sr"></s></a></div></div>';
					break;
				case "window":
					$(_this).show();	
					_htmlCon = $($(_this).clone().html($(_this).clone())).html();
					_htmlConWindow = _htmlCon;
					$(_this).remove();
					break;
			}
			// 设置小三角的位置
			switch(position){
				case "tl":
					_htmlPos = '<div class="T_arrow T_arrowtl"></div>';
					break;
				case "tr":
					_htmlPos = '<div class="T_arrow T_arrowtr"></div>';
					break;
				case "rt":
					_htmlPos = '<div class="T_arrow T_arrowrt"></div>';
					break;
				case "rb":
					_htmlPos = '<div class="T_arrow T_arrowrb"></div>';
					break;
				case "bl":
					_htmlPos = '<div class="T_arrow T_arrowbl"></div>';
					break;
				case "br":
					_htmlPos = '<div class="T_arrow T_arrowbr"></div>';
					break;
				case "lt":
					_htmlPos = '<div class="T_arrow T_arrowlt"></div>';
					break;
				case "lb":
					_htmlPos = '<div class="T_arrow T_arrowlb"></div>';
					break;
				case "none":
					_htmlPos = '';
					break;
			}

			// 组合dom内容并插入到body里
			_html = '<div class="T_layer" name="' + model +'"><div class="bg"><table cellspacing="0" cellpadding="0" border="0"><tbody><tr><td>' +
					'<div class="T_content" style="width:' + width + 'px;">' + _htmlCon + '</div>' +
					'</td></tr></tbody></table>' + _htmlPos + '</div></div>';
			$_html = $(_html).appendTo("body");
			
			// tips框在页面中的位置
			if(nonum.test(_top) && nonum.test(_left)){
				top = $(document).scrollTop() + ($(window).height() - $_html.height())/2; 
				left = $(document).scrollLeft() + ($(window).width() - $_html.width())/2;
			}else if(!nonum.test(_top) && nonum.test(_left)){
				top = _top;
				left = $(document).scrollLeft() + ($(window).width() - $_html.width())/2;
			}else if(nonum.test(_top) && !nonum.test(_left)){
				top = $(document).scrollTop() + ($(window).height() - $_html.height())/2;
				left = _left;
			}else{
				top = _top;
				left = _left;
			}
			
			// 设置提示框尺寸、宽高等样式并显示提示框
			$_html.css({
				"position":"absolute",
				"top":top,
				"left":left,
				"z-index":num
			});
			
			// 绑定事件确认按钮和取消按钮
			if(model === "message"){
				
				$("a[name='messageOK" + num + "']").live("click",{num:num},function(e){
					
					opts["ok_fn"]();
					$(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().remove();
				});
				
				$("a[name='messageCancel" + num + "']").live("click",{num:num},function(e){
					
					$(this).parent().parent().parent().parent().parent().parent().parent().parent().parent().remove();
				});
			}

			// 自增1
			num++;

			// 显示一定时间后删除 默认3000
			if(autoclose){
				$(_this).toolTipClose($_html,timeout,model,_htmlConWindow);
			}
		},
		// 显示一定时间后删除 默认3000
		toolTipClose:function($_html,timeout,model,_htmlConWindow){
			var closeTime = setTimeout(function(){
				if( ($.browser.msie&&($.browser.version == "8.0")) || $.browser.msie&&($.browser.version == "7.0") ){
					$_html.find(".T_content").children().fadeOut(400,function(){
						if(model === "window"){
							var $_htmlConWindow = $(_htmlConWindow).appendTo("body");
							$_htmlConWindow.hide();
						}
						
						$_html.remove();
						clearTimeout(closeTime);
					});
				}else{
					$_html.fadeOut(400,function(){
						if(model === "window"){
							var $_htmlConWindow = $(_htmlConWindow).appendTo("body");
							$_htmlConWindow.hide();
						}

						$_html.remove();
						clearTimeout(closeTime);
					});
				}
			},timeout);
		}
	});
})(jQuery);