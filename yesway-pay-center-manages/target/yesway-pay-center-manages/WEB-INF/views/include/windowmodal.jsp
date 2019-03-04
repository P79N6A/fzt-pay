<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	/* settings = {
			width:"95%",
			height:"300px",
			overflow:"hidden"
	} */
	window.modalFrame = function(url, settings) {
		//隐藏
		if (url == "hide") {
			$("#_myModal").modal("hide");
			return false;
		}
		//默认配置
		var width = "95%";
		var height = "300px";
		var overflow = "hidden";
		if(settings){
			width = settings.width || width;
			height = settings.height || height;
			overflow = settings.overflow || overflow;
		}
		var $modal = $("#_myModal");
		// 设置宽高
		$modal.find(".modal-dialog").css("width", width).css("height", height);
		$modal.find("div.modal-content")
			.css("height", height)
			.css("width", width);
		$modal.find("iframe")
			.attr("src", url)
			.css("overflow", overflow)
			.css("height", $modal.find("div.modal-content").height() - 10 + "px")
			.css("width", $modal.find("div.modal-content").width() - 10 + "px");
		//显示模态框(http://v3.bootcss.com/javascript/#modals)
		$modal.modal({
			show : true,
			keyboard : true,
			backdrop : false
		});
	};
	//alert(typeof window.modalFrame);
</script>
<!-- /.content-wrapper -->
<div class="modal fade" id="_myModal" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content" style="width:100%;overflow: hidden;">
			<iframe style="border: 0px solid #fff; margin: 5px;width:95%;overflow: visible;">
				<div class="box">
				<div class="overlay">
	                 <i class="fa fa-refresh fa-spin"></i>
	            </div>
	            </div>
			</iframe>
		</div>
	</div>
</div>