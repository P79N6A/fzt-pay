<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	window.oldalert = window.alert;
	window.alert = function(content) {
		$("#windowAlertContent").html(content + "");
		$("#_windowAlertBox").modal('show');
		//$("#...").modal('hide');
	};
	$(function() {
		/* $("#windowAlertContent").html("test");
		$("#_windowAlertBox").modal('show'); */
	});
</script>
<style>
<!--
.modal-content {
	border-radius: 6px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, .5)
}
-->
</style>
<div id="_windowAlertBox" class="modal fade bs-example-modal-sm in"
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
			<div id="windowAlertContent" class="modal-body"></div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
