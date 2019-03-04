<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>帐户管理</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/plugins/iCheck/square/blue.css"></link>
<style>
	<!--
		body{overflow: hidden}
		.content-wrapper{background-color:#fff}
		.modal-content{box-shadow:none;}
		.modal-footer{}
	-->
</style>
</head>
<body>
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-label="Close" onclick="javascript:parent.modalFrame('hide')">
				<span aria-hidden="true">×</span>
			</button>
			<h4 class="modal-title">为&nbsp;<b>${loginName}</b>&nbsp;分配角色</h4>
		</div>
		<div class="modal-body">
			<div>
				<!-- <input type="checkbox"  id="cboxchecked" />
		  		<label>全选</label> -->
			</div>
     		<div class="checkbox icheck">
	     		<c:forEach var="allRole" items="${allRoleList}">
	     			<c:if test="${allRole.roleName != 'admin' }">
			     		<input type="checkbox" name="roleId" value="${allRole.roleId }"
			     			<c:forEach var="checkRole" items="${checkRoleList}">
			     				<c:if test="${checkRole.roleId eq allRole.roleId}">checked="checked"</c:if>
			     			</c:forEach>/> 
			     		<c:out value="${allRole.roleName}"></c:out>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	     			</c:if>
				</c:forEach>
			</div>
			<input type="hidden" id="userId" value="${userId}"/>
			<input type="hidden" id="group" value="${group}"/>
		</div>
		<div></div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default pull-left" id="button_hide" onclick="javascript:parent.modalFrame('hide')"
				data-dismiss="modal">关闭</button>
			<button type="button" class="btn btn-primary" onclick="saveUserRole()">保存</button>
		</div>
	</div>
	<!-- /.modal-content -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/iCheck/icheck.min.js"></script>
	<script>
		var path = "<%=request.getContextPath()%>";

		$(function() {
			// iCheck（http://www.bootcss.com/p/icheck/#callbacks）
			$("input").iCheck({
				checkboxClass : 'icheckbox_square-blue',
				radioClass : 'iradio_square-blue',
			});
			// 联动
			$("#cboxchecked").on("ifClicked", function(){
				var bischecked = $('#cboxchecked').is(':checked');
				var fruit = $('input[name="ruleId"]');
				// “ifClick”事件中插件好像是先调用该回调后修改checkbox的状态的，所以下一行调用的操作是反的
				bischecked ? fruit.iCheck('uncheck') : fruit.iCheck('check');
				//console.log($("input[name='ruleId']").length + " / " + $("input[name='ruleId']:checked").length);
			});
			$("input[name='ruleId']").on("ifChanged", function(){
				// 用“ifClick”在判断时会出现问题，好像是插件先调用该回调，后修改checkbox的状态
				checkState(this);
			});
			checkState($("input[name='ruleId']"));
		});
		
		var checkState = function(){
			//console.log($("input[name='ruleId']").length + " / " + $("input[name='ruleId']:checked").length);
			if($("input[name='ruleId']").length == $("input[name='ruleId']:checked").length){
		    	$('#cboxchecked').iCheck('check');
			}else{
		    	$('#cboxchecked').iCheck('uncheck');
			}
		};
		
		function saveUserRole() {
			var roleList = "";
			var userId = $("#userId").val();
			var group = $("#group").val();
			$("input[name='roleId']:checked").each(function() {
				roleList += "," + $(this).val();
			});
			roleList = roleList.substring(1);
			$.ajax({
				url : path + "/user/saveRole",
				data : {
					roleList : roleList,
					group : group,
					userId : userId
				},
				datetype : "json",
				success : function(msg) {
					parent.modalFrame('hide');
					setTimeout(function(){
						alert(msg);
					}, 200);
				}
			});
		}
	</script>
</body>
</html>