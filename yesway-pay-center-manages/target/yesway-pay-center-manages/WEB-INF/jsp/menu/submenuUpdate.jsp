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
			菜单信息管理 <small>菜单信息修改</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>菜单信息列表</a></li>
			<li><a href="#"><i class="fa"></i>菜单信息修改</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box box-solid box-default">
			<div class="box-header">菜单信息列表</div>
			<div class="box-body">
				<form class="form-horizontal" role="form" id="menuForm" action="<%=request.getContextPath()%>/menu/updateSubMenu">
					<%-- <input type="hidden" value="${menu.parentId}" name="parentId" id="parentId"/> --%>
					<input type="hidden" value="${menu.menuId}" name="menuId" id="menuId"/>
					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label"><font color="red">*</font>二级菜单名称</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="menuName" name="menuName"  placeholder="请输入菜单名称" value="${menu.menuName}" >
						</div>
					</div>
					<div class="form-group">
						<label for="group" class="col-sm-2 control-label"><font color="red">*</font>二级菜单路径</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="parentMenuUrl"  name="parentMenuUrl"  placeholder="请输入菜单路径" value="${menu.parentMenuUrl}"	>
						</div>
					</div>
					<div class="form-group">
						<label for="group" class="col-sm-2 control-label"><font color="red">*</font>父菜单名称</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="parentId"  name="parentId"  placeholder="请输入父菜单名称" readonly="readonly" 	value="${submenu.menuName}">
						</div>
					</div>
					
					<div class="form-group">
						<label for="lastname" class="col-sm-2 control-label">菜单状态</label>
						<div class="input-group col-sm-5">
							<select class="form-control selectpicker show-tick" name="status">
								<option value="0"
									<c:if test="${menu.status eq 0}" >selected = "selected"</c:if>>可用</option>
								<option value="1"
									<c:if test="${menu.status eq 1}" >selected = "selected"</c:if>>禁用</option>
							</select>
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
				menuId : {
					required : true
				},menuName : {
					required : true
				}
			}
		};
		// 新增操作，增加远程验证
		/* validateRules.menuName = {
			required : true,
			remote: {
			    url: "../menu/nametexist",     //后台处理程序
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
		}; */
		// 新增操作，增加远程验证
		/* validateRules.roleId = {
			required : true,
			remote: {
			    url: "../menu/idexist",     //后台处理程序
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
		}; */
		/* validateRules.group={
				required : true,	
		}; */
		/* $("#menuForm").validate({
			rules : validateRules,
			messages:{
				menuName:{
					required :"编号不能为空",
					remote:"该编号已经存在，请修改"
				},menuName:{
					required :"角色名称不能为空",
					remote:"该名称已经存在，请修改"
				}
			}
		}); */
	});
	</script>
</body>

</html>