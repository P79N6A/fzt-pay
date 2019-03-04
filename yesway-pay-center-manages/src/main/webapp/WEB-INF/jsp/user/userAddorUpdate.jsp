
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title></title>
<meta name="decorator" content="mainframe" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/plugins/datepicker/skin/WdatePicker.css">
<!--[if lt IE 9]>
		<script src="js/html5.js"></script>	
	<![endif]-->
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			帐户管理 <small>帐户修改</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>帐户列表</a></li>
			<li><a href="#"><i class="fa"></i>帐户修改</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box box-solid box-default">
			<div class="box-header">帐户列表</div>
			<div class="box-body">
				<form class="form-horizontal" role="form" id="userForm"
					action="<%=path%>/user/save">
					<c:if test="${not empty mgtUser.userId}">
					<div class="form-group"  hidden="true">
						<label for="userId" class="col-sm-2 control-label">userId</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" disabled
								value="${mgtUser.userId}"> <input type="hidden"
								class="form-control" name="userId" value="${mgtUser.userId}">
						</div>
					</div>
					</c:if>
					<div class="form-group">
						<label for="loginName" class="col-sm-2 control-label"><font color="red">*</font>登陆名</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="loginName"
								name="loginName" placeholder="请输入登陆名称"
								value="${mgtUser.loginName}">
						</div>
					</div>
					<c:if test="${empty mgtUser.userId}">
						<div class="form-group">
							<label for="password" class="col-sm-2 control-label"><font color="red">*</font>密码</label>
							<div class="input-group col-sm-5">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="请输入密码" value="${mgtUser.password}">
							</div>
						</div>
						<div class="form-group">
							<label for="passwordConfirm" class="col-sm-2 control-label"><font color="red">*</font>密码确认</label>
							<div class="input-group col-sm-5">
								<input type="password" class="form-control" id="passwordConfirm" 
									name="passwordConfirm" placeholder="请再次输入密码" value="${mgtUser.password}">
							</div>
						</div>
					</c:if>
					<div class="form-group">
						<label for="firstName" class="col-sm-2 control-label"><font color="red">*</font>姓</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="lastName"
								name="lastName" placeholder="请输入姓"
								value="${mgtUser.lastName}">
						</div>
					</div>
					<div class="form-group">
						<label for="lastName" class="col-sm-2 control-label"><font color="red">*</font>名</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="firstName"
								name="firstName" placeholder="请输入名"
								value="${mgtUser.firstName}" />
						</div>
					</div>
					
					<div class="form-group">
						<label for="title" class="col-sm-2 control-label">头衔</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id=title
								name="title" placeholder=""
								value="${mgtUser.title}" />
						</div>
					</div>
					<div class="form-group">
						<label for="gender" class="col-sm-2 control-label">性别</label>
						<div class="input-group col-sm-5">
							<select class="form-control selectpicker show-tick" name="gender">
								<option value="0"
									<c:if test="${mgtUser.gender eq 0}" >selected = "selected"</c:if>>男</option>
								<option value="1"
									<c:if test="${mgtUser.gender eq 1}" >selected = "selected"</c:if>>女</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="phoneNumber" class="col-sm-2 control-label">电话</label>
						<div class="input-group col-sm-5">
								 <input class="form-control" id="phoneNumber" maxlength="200"  validType="mobile"
								name="phoneNumber" value="${mgtUser.phoneNumber}" />
						</div>
					</div>
					<div class="form-group">
						<label for="email" class="col-sm-2 control-label">电子邮箱</label>
						<div class="input-group col-sm-5">
								 <input class="form-control" id="email" maxlength="200"
								name="email" value="${mgtUser.email}" />
						</div>
					</div>
					<div class="form-group">
						<label for="appId" class="col-sm-2 control-label">支付宝应用ID</label>
						<div class="input-group col-sm-5">
								 <input class="form-control" id="appId" maxlength="200"
								name="appId" value="${mgtUser.appId}" />
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-primary">提交</button>
							<input type="button" class="btn btn-default" id="back"
								name="back" onclick="javascript:window.history.go(-1);"
								value="返回" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<%=request.getContextPath()%>/static/plugins/datepicker/WdatePicker.js"></script>
	<script>
		$("#userForm").validate({
			rules : {
				loginName : {
					required : true
				},password : {
					required : true
				},
				passwordConfirm : {
					required : true,
					equalTo :'#password'
				},
				firstName : {
					required : true
				},
				lastName : {
					required : true
				}/* ,
				phoneNumber : {
					//required : true,
					isPhone : true
				}*/
			}
		});
	</script>
</body>

</html>