/*
 * 消息提示框组件
 * @author wanglh
 * @data 2013-09-11
 * @param(int) top 弹窗距顶部的位置 可以留空，默认为页面中间
 * @param(int) width 提示框的宽度 默认值为400
 * @param(int) height 提示框的高度 默认值为200
 * @param(String) title 提示框的title
 * @param(String) content 提示框显示的内容
 * @param(bool) closable 弹出框是否可关闭
 * @param(String) icon 提示类型：info/error/warning/question/success
 * @param(Function) ok_fn 确定按钮点击触发的方法（不用加弹窗关闭）
 * @example $.messager({
			top:100,
			width:400,
			height:200,
			title:"title name",
			content:"content",
			closable:false,
			icon:"success",
			cancelButton:false,
			ok_fn:function(){
				alert("ok");
			}
		});
 */

// 创建一个闭包    
(function($){
	// 插件的定义
	$.messager = function(options){
		//debug(this);
		//构建迭代之前主要选项元素
		var opts = $.extend({}, $.messager.defaults, options);
		openDialog(opts);
		initDialog(opts);
	};
	function openDialog(opts){
		var _icon = '',
			_html = '';
		switch(opts["icon"]){
			case "prompt":
				_icon='<div class="msgrPrompt"></div>';
				break;
			case "warning":
				_icon='<div class="msgrWarning"></div>';
				break;
			case "confirm":
				_icon='<div class="msgrConfirm"></div>';
				break;
			case "error":
				_icon='<div class="msgrError"></div>';
				break;
		}
		//组合html
		_html += '<div name="messagerDialog"><div class="msgr">';
		_html +=    _icon;
		_html += '	<div class="msgrTxt">';
		_html += '		<div class="msgrTxtCon">' + opts["content"] + '</div>';
		_html += '	</div>';
		_html += '</div>';
		//生成按钮
		if(opts["cancelButton"]){
			_html += '<div class="msgrOper"><a href="javascript:;" class="msgrBtn2" name="messagerOK">确定<s class="sl"></s><s class="sr"></s></a><a href="javascript:;" class="msgrBtn1" name="messagerCancel">取消<s class="sl"></s><s class="sr"></s></a></div>';
		}else{
			_html += '<div class="msgrOper"><a href="javascript:;" class="msgrBtn1" name="messagerOK">确定<s class="sl"></s><s class="sr"></s></a></div>'
		}
		_html += '</div>';
		$("body").append(_html);
	}

	function initDialog(opts){
		$("div[name='messagerDialog']").dialog({
			top:opts["top"],
			width:opts["width"]||380,
			height:opts["height"],
			title:opts["title"],
			closable:opts["closable"],
			modal:true,
			onClose:function(){
				$("div[name='messagerDialog']").parent().next().next().remove();
				$("div[name='messagerDialog']").parent().next().remove();
				$("div[name='messagerDialog']").parent().remove();
			}
		}).data().window.mask.append('<iframe width="100%" height="100%" frameborder="0" scrolling="no" style="opacity:0;filter:alpha(opacity=0);"></iframe>');
		$("a[name='messagerOK']").die("click");
		$("a[name='messagerOK']").live({
			click:function(){
				opts["ok_fn"]();
				$(this).parent().parent().parent().parent().parent().next().next().remove();
				$(this).parent().parent().parent().parent().parent().next().remove();
				$(this).parent().parent().parent().parent().parent().remove();
				
			}
		});
		$("a[name='messagerCancel']").die("click");
		$("a[name='messagerCancel']").live({
			click:function(){
				$("div[name='messagerDialog']").dialog("close");
			}
		});
	}
	//插件的defaults
	$.messager.defaults = {
		icon:"prompt",//默认的样式
		title:"提示",//标题
		content:"My content",
		closable:true,
		cancelButton:true,
		ok_fn:function(){
			//alert("ok");
		}//OK按钮的事件
	};
// 闭包结束
})(jQuery);