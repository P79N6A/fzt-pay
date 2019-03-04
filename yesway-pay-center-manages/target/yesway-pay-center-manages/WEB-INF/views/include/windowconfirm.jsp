<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	/**
	settings = {
		cancleText : "取消", 
		okText : "确定",
		title : "提示消息"
	}
	*/
	window.confirmBox = function(content, callback, callBackParams, settings) {
		var cancleText = "取消"; 
		var okText = "确定";
		var title = "提示消息";
		if(settings){
			if(settings.title)title = settings.title;
			if(settings.cancleText)cancleText = settings.cancleText;
			if(settings.okText)okText = settings.okText;
		}
		var $box = $("#_windowConfirmBox");
		
		$box.find("h4.modal-title").html(title);
		$box.find("div.modal-body").html(content);
		
		$box.find("button[name='cancel']")
			.html(cancleText)
			.unbind("click")
			.bind("click", function(){
				$box.modal('hide');
			});
		
		$box.find("button[name='ok']")
			.html(okText)
			.unbind("click").bind("click", function(){
				$box.modal('hide');
				callback(callBackParams);
			});
		$box.modal('show');
		//$("#...").modal('hide');
	};
	/* $(function() {
		confirmBox("显示alert好不好？", function(content){
			alert(content);
		}, "我是alert", {
			cancleText : "不好", 
			okText : "好",
			title : "测试confirm"
		});
	}); */
</script>
<style>
<!--
.modal-content {
	border-radius: 6px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, .5)
}
-->
</style>
<div id="_windowConfirmBox" class="modal fade bs-example-modal-sm in"
	tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="mySmallModalLabel">提示</h4>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" name="cancel">取消</button>
				<button type="button" class="btn btn-danger" name="ok">确定</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
