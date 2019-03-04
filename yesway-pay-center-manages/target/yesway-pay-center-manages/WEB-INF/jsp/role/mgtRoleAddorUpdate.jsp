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
<!--[if lt IE 9]>
		<script src="js/html5.js"></script>	
	<![endif]-->
<script type="text/javascript" src="../zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="../zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			角色信息管理 <small>角色信息修改</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>角色信息列表</a></li>
			<li><a href="#"><i class="fa"></i>角色信息修改</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box box-solid box-default">
			<div class="box-header">角色信息列表</div>
			<div class="box-body">
				<form class="form-horizontal" role="form" id="roleForm" action="<%=request.getContextPath()%>/role/save">
					<div class="form-group">
						<label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>编号</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="roleId" name="roleId" placeholder="请输入编号" value="${role.roleId}"
								<c:if test="${role.roleId !=null }">readonly="readonly"</c:if>
							>
						</div>
					</div>
					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label"><font color="red">*</font>角色名称</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="roleName" name="roleName"  placeholder="请输入角色名称"
								value="${role.roleName}" 
									<c:if test="${role.roleId !=null }">readonly="readonly"</c:if>
								>
						</div>
					</div>
					<div class="form-group">
						<label for="group" class="col-sm-2 control-label"><font color="red">*</font>所属系统</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="group"
								name="group"  placeholder="请输入系统名称"
								value="${role.group}">
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">角色状态</label>
						<div class="input-group col-sm-5">
							<select class="form-control selectpicker show-tick" name="status">
								<option value="0"
									<c:if test="${role.status eq 0}" >selected = "selected"</c:if>>可用</option>
								<option value="1"
									<c:if test="${role.status eq 1}" >selected = "selected"</c:if>>禁用</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">角色说明</label>
						<div class="input-group col-sm-5">
						 	 <textarea class="form-control" id="remark" maxlength="200"
								name="remark" rows="3" > ${role.remark}</textarea>
						</div>
					</div>
					
					<div class="form-group"> 
							<label class="control-label col-md-3">权限选择</label>
							<div class="col-md-9">
								 <input name="powerids" type="hidden">
								 <div id="powermenudiv">
									<ul id="powersTree" class="ztree"  ></ul>
								</div>
							</div>
						</div>
					
 					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="submit" class="btn btn-primary">提交</button>
							<input type="button" class="btn btn-default" id="back" name="back" onclick="javascript:window.history.go(-1);"
								value="返回" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</section>
	<script>
	$(function(){
		var validateRules = {
			rules : {
				roleId : {
					required : true
				},roleName : {
					required : true
				},group:{
					required :true
				}
			}
		};
		// 新增操作，增加远程验证
		validateRules.roleName = {
			required : true,
			remote: {
			    url: "../role/nametexist",     //后台处理程序
			    type: "post",               //数据发送方式
			    dataType: "json",           //接受数据格式   
			    data: {                     //要传递的数据
			    	roleName: function() {
			            return $("#roleName").val();
			        },
			        roleId : function(){
			        	return $("#roleId").val();
			        }
			    }
			}
		};
		// 新增操作，增加远程验证
		validateRules.roleId = {
			required : true,
			remote: {
			    url: "../role/idexist",     //后台处理程序
			    type: "post",               //数据发送方式
			    dataType: "json",           //接受数据格式   
			    data: {                     //要传递的数据
			    	roleName: function() {
			            return $("#roleName").val();
			        },roleId : function(){
			        	return $("#roleId").val();
			        }
			    }
			}
		};
		validateRules.group={
				required : true,	
		};
		$("#roleForm").validate({
			rules : validateRules,
			messages:{
				roleName:{
					required :"编号不能为空",
					remote:"该编号已经存在，请修改"
				},roleName:{
					required :"角色名称不能为空",
					remote:"该名称已经存在，请修改"
				},group : {
					required :"所属系统不能为空",
				}
			}
		});
	});
	</script>
</body>

</html>