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
<style>
.test {
	overflow: hidden;
	margin-left: 1.15rem;
	width: 7rem;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			帐户管理 <small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>帐户管理</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box box-default">
			<div class="box-header">
				<button type="button" class="btn btn-default" id="add">添加</button>
				<div class="box-tools"></div>
			</div>
			<form role="form" id="searchForm"	action="<%=request.getContextPath()%>/user/userList.html">
				<input type="hidden" name="pageNumber" value="${pager.pageNumber }"/>
				<input type="hidden" name="pageSize" value="${pager.pageSize }"/>
			</form>
			<div class="box-body">
				<input type="hidden" value="${message}" id="message" />
				<table id="example" class="table table-striped table-bordered"
					cellspacing="0" width="100%">
					<!--<caption>基本的表格布局</caption>  -->
					<thead>
						<tr>
							<th hidden="true">编号</th>
							<th>登陆名</th>
							<th>姓名</th>
							<th>性别</th>
							<th>电话</th>
							<th hidden="true">所属系统</th>
							<th>头衔</th>
							<th>邮箱</th>
							<th>支付宝ID</th>
							<th>添加时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${pager.datas}"
							varStatus="status">
							<tr>
								<td hidden="true"><input type="hidden" value="${user.userId}" id="userId" name="userId" />${user.userId}</td>
								<td><input type="hidden" value="${user.loginName}" name="loginName">${user.loginName}</td>
								<td><b>${user.lastName}</b>${user.firstName}</td>
								<td><c:if test="${user.gender==1}">女</c:if> <c:if
										test="${user.gender==0}">男</c:if></td>
								<td>${user.phoneNumber}</td>
								<td hidden="true">${user.group}<input type="hidden" value="${user.group}" id="group" name="group"></td>
								<td>${user.title}</td>
								<td>${user.email}</td>
								<td>${user.appId}</td>
								<td><fmt:formatDate value="${user.addTime }"
										pattern="yyyy-MM-dd " /></td>
								<td><c:if test="${user.loginName!='admin'}">
									<!-- 该用户不是admin才可分配角色 -->
									<a href="javascript:void(0)" onclick='setRules(this);' style="cursor: pointer;">分配角色</a>
									<a href="javascript:void(0)" onclick='updatePassword(this)' style="cursor: pointer;">重置密码</a>
									</c:if>
									<c:if test="${user.loginName=='admin'}">
										<shiro:hasAnyRoles name="admin">
											<!-- 当前用户是admin时，只有超级管理员才可以重置密码 -->
											<a href="javascript:void(0)" onclick='updatePassword(this)' style="cursor: pointer;">重置密码</a>
										</shiro:hasAnyRoles>
									</c:if>
									<a href="javascript:void(0)" onclick='updateMgtUser(this)' style="cursor: pointer;">修改</a>
									<c:if test="${user.loginName!='admin'}">
									<a href="javascript:void(0)" onclick='deleteMgtUser(this);' style="cursor: pointer;">删除</a>
									</c:if>
									</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<nav id="pager"></nav>
			</div>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="setRuleBox" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Modal title</h4>
					</div>
					<div class="modal-body">...</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary">保存</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/static/plugins/jquery/pager/jquery.pager.bootstrap.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/static/plugins/jquery/pager/pager.js"></script>
	<script>
		var path = "<%=request.getContextPath()%>";
		var totalPages = ${pager.totalPages};	
		var pageNumber = ${pager.pageNumber};	
		var pageSize = ${pager.pageSize};
	
		function setRules(obj){
			var userId = $(obj).parent().parent().find("input[name=userId]").val();
			var loginName = $(obj).parent().parent().find("input[name=loginName]").val();
			var group = $(obj).parent().parent().find("input[name=group]").val();
			modalFrame(path + "/user/setroles.html?userId="+userId+"&loginName="+loginName+"&group"+group, {
				width:"500px",
				height:"225",
				overflow:"hidden"
			});
		}
	
		function updatePassword(obj){
			var userId = $(obj).parent().parent().find("input[name=userId]").val();
			modalFrame(path + "/user/resetPassword.html?userId=" + userId, {
				width:"500px",
				height:"255px",
				overflow:"hidden"
			});
		}

		$(document).ready(function() {
			//操作成功提示
			  var message=  $('#message').val();
			  if(message!=null&&message!=''){
				  alert(message);
			  }
		    $("#add").bind("click", function() {
				window.location.href=path + "/user/addorUpdate";
             });
		});
	
		function deleteMgtUser(nowTr){
			confirmBox("确定删除?", function(nowTr){
				var userId= $(nowTr).parent().parent().find("input[name=userId]").val();
				 $.ajax({
					 url : path + "/user/deleteMgtUser",
					 data : {userId : userId},
		             dataType : "json",
					 success: function(msg){
							if(msg > 0){
								alert("删除成功");
								 $(nowTr).parent().parent().remove();
							}else{
								alert("删除失败");
							};
					}
				});
			}, nowTr, {
				cancleText : "再想想", 
				okText :  "删除",
			});
		};
		function updateMgtUser(nowTr){
			var userId= $(nowTr).parent().parent().find("input[name=userId]").val();
			window.location.href=path + "/user/addorUpdate?userId="+ userId;
		};
 		initPager("pager", pageNumber, totalPages, function(pageclickednumber){
 			$("input[name='pageNumber']").val(pageclickednumber);
 			$("#searchForm").submit();
				}, {});
	</script>
</body>
</html>