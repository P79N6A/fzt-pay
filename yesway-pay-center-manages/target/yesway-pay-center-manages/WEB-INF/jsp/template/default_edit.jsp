<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta name="decorator" content="mainframe" />
<title>编辑模板</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			模板管理 <small>修改模板</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>编辑模板</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box box-solid box-default">
			<div class="box-header">修改模板</div>
			<div class="box-body">
				<form class="form-horizontal" id="templateForm">	
					<input type="hidden" name="templateId" id="templateId" value="${template.templateId}"/>				
					<div class="form-group">
						<label for="templateCode" class="col-sm-2 control-label"><font color="red">*</font>模板编号</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="templateCode"
								name="templateCode" placeholder="请输入模板编号" disabled
								value="${template.templateCode}">
						</div>
					</div>					
					
					<div class="form-group">
						<label for="moduleName" class="col-sm-2 control-label"><font color="red">*</font>模块名称</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="moduleName"
								name="moduleName" placeholder="请输入模块名称"
								value="${template.moduleName}" />
						</div>
					</div>
					
					<div class="form-group">
						<label for="templateName" class="col-sm-2 control-label"><font color="red">*</font>模板名称</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="templateName"
								name="templateName" placeholder="请输入模板名称"
								value="${template.templateName}" />
						</div>
					</div>
					<div class="form-group">
						<label for="templateName" class="col-sm-2 control-label"><font color="red">*</font>模板语言</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" disabled
								value="${template.language}" />
						</div>
					</div>
					<div class="form-group">
						<label for="templateName" class="col-sm-2 control-label"><font color="red">*</font>通知标题(邮件)</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="title"
								name="title" placeholder="请输入标题"
								value="${template.title}" />
						</div>
					</div>
					<div class="form-group">
						<label for="content" class="col-sm-2 control-label"><font color="red">*</font>模板内容</label>
						<div class="input-group col-sm-5">
							<textarea rows="3" cols="10" class="form-control" id="content"
								name="content" placeholder="请输入模板内容"><!-- ${template.content} --></textarea>
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
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/jquery/pager/jquery.pager.bootstrap.js"></script>
	<script>
	var path="<%=request.getContextPath()%>";
	$(function(){
		var validateRules = {
			rules : {
				templateCode : {
					required : true,
					maxlength:50
				},
				moduleName : {
					required : true,
					maxlength:50
				},
				templateName : {
					required : true,
					maxlength:50
				},
				title : {
					required : true,
					maxlength:50
				},
				content : {
					required : true,
					maxlength: 10240
				}
			}	
		}
		
		$("#templateForm").validate({
			rules : validateRules.rules,
			submitHandler: function(form){
				var template = {
					'templateId': $('#templateId').val(),
					'templateCode':$('#templateCode').val(),
					'moduleName':$('#moduleName').val(),
					'templateName':$('#templateName').val(),
					'title':$('#title').val(),
					'content':$('#content').val()
				}
				
// 				console.log(template)
				template = JSON.stringify(template); 
				$.ajax({
		    	  url: path+"/message/template/save",
		    	  type:'post',
		    	  contentType: 'application/json',
		    	  data: template,
		    	  success:function(data){
	    			  alert(data)
		    		  if(data=="修改成功" || data=="添加成功"){
		    			  window.location.href=path+"/message/template/default.html";
		    		  }
		    	  }
		        })
			},
			messages:{
				templateCode : {
					required : '模板编号不能为空',
				},
				moduleName : {
					required : '模块名称不能为空',
				},
				templateName : {
					required : '模板名称不能为空',
				},
				title : {
					required : '邮件标题不能为空',
				},
				content : {
					required : '模板内容不能为空',
				}
			}
		});
		var textareaVal = $("#content").val();
		if(textareaVal){
			textareaVal = textareaVal.replace(/<\!\-\-/g,"");
			textareaVal = textareaVal.replace(/\-\->/g,"");
			$("#content").val(textareaVal);
		}
	});
	</script>
</body>
</html>