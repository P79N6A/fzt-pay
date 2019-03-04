/**
 @document
 @author:Yujianping
 @tel:13716506521
 @date:2013-6-25
 @param(int) width 提示框的宽度 默认值为380
 @param(int) height 提示框的高度 默认值为180
 @param(int) top 弹窗距顶部的位置 可以留空，默认为页面垂直居中
 @param(int) left 弹窗距顶部的位置 可以留空，默认为页面水居中
 @param(string) title 提示框的title 默认为：“提示”
 @param(string) content 提示框显示的内容 默认为：“您好，系统正在维护中，请稍候再试。”
 @param(string) icon 默认为prompt 提示类型：prompt(提示)/error(错误)/warning(警告)/confirm(确认)
 @param(function) ok_fn 确定按钮点击触发的方法（不用加弹窗关闭）
 @example 使用示例
 @        $.msgr({
 @			  width:380,
 @			  height:180,
 @			  top:500,
 @			  left:500,
 @			  title:"标题",
 @			  icon:"prompt",
 @			  content:"您好，系统正在维护中，请稍候再试。",
 @			  cancelButton:false
 @		 });
 **/
;(function($){
	
	// 定义变量
	var _num = 100;
	
	$.extend({
		msgr:function(options){
			
			var defaults= {
					title:"提示",
					icon:"prompt",
					content:'您好，系统正在维护中，请稍候再试。',
					cancelButton:true,
					ok_fn:function(){}
				},
				opts = $.extend(defaults,options || {}),
				title = opts["title"],
				content = opts['content'],
				iconType = opts["icon"],
				cancelBtn = opts["cancelButton"],
				_icon = '',
				_html = '',
				_this = this;

			// 确定图标类型
			switch(iconType){
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
				case "success":
					_icon='<div class="msgrSuccess"></div>';
					break;
				default:
					_icon='<div class="msgrPrompt"></div>';
			}

			// 组合html
			_html += '<div name="msgr' + _num + '"><div class="msgr">' + _icon + '<div class="msgrTxt"><div class="msgrTxtCon">' + content + '</div></div></div>';

			// 生成按钮
			if(cancelBtn){
				_html += '<div class="msgrOper"><a href="javascript:;" class="msgrBtn2" name="msgrOK' + _num + '">确定</a>' +
						 '<a href="javascript:;" class="msgrBtn1" name="msgrCancel' + _num + '">取消</a></div>';
			}else{
				_html += '<div class="msgrOper"><a href="javascript:;" class="msgrBtn2" name="msgrOK' + _num + '">确定</a></div>'
			}
			_html += '</div>';
			$("body").append(_html);
			
			// 生成弹窗
			$("div[name='msgr" + _num + "']").dlog({
				title:title,
				top:opts["top"],
				left:opts["left"],
				width:opts["width"],
				height:opts["height"],
				modal:opts["modal"],
				onClose:true
			});
			
			// 绑定确定
			
			$("a[name='msgrOK" + _num + "']").on("click",{_num:_num},function(e){
				
				opts["ok_fn"]();
				$("div[name='msgr" + e.data._num + "']")._dlogclose(true);
			});

			// 绑定取消事件
			if(cancelBtn){
				
				$("a[name='msgrCancel" + _num + "']").on("click",{_num:_num},function(e){
					
					$("div[name='msgr" + e.data._num + "']")._dlogclose(true);
				});
			}

			// num增加1
			_num++;
		}
	});
})(jQuery);