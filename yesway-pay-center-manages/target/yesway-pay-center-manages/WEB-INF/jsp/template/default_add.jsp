<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>添加模板</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			模板管理 <small>添加模板</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>模板管理 </a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box  box-default">
		<!-- Main content -->	
		<section class="content">
			<div class="box box-solid box-default">
				<div class="box-header">添加模板</div>
				<div class="box-body">
					<form class="form-horizontal" id="templateForm">			
						<div class="form-group">
							<label for="templateCode" class="col-sm-3 control-label"><font color="red">*</font>模板编号</label>
							<div class="input-group col-sm-5">
								<input type="text" class="form-control" id="templateCode"
									name="templateCode" placeholder="请输入模板编号"
									value="">
							</div>
						</div>					
						<div class="form-group">
							<label for="moduleName" class="col-sm-3 control-label"><font color="red">*</font>模块名称</label>
							<div class="input-group col-sm-5">
								<input type="text" class="form-control" id="moduleName"
									name="moduleName" placeholder="请输入模块名称"
									value="" />
							</div>
						</div>
						<div class="form-group">
							<label for="templateName" class="col-sm-3 control-label"><font color="red">*</font>模板名称</label>
							<div class="input-group col-sm-5">
								<input type="text" class="form-control" id="templateName"
									name="templateName" placeholder="请输入模板名称"
									value="" />
							</div>
						</div>
						<div class="col-md-6">
                    		<div class="box box-info">
		                		<div class="box-header with-border">
		                 			<h3 class="box-title">中文模板</h3>
								</div><!-- /.box-header -->
								<div class="box-body">
									<div class="form-group">
				                    	<label class="col-sm-3 control-label" >通知标题(邮件)</label>
				                    	<div class="col-xs-7">
				                    		<input class="form-control" id="title_zh" name="title_zh" placeholder="请输入邮件标题">
				                    	</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label" >消息内容</label>
										<div class="col-xs-7">
											<textarea rows="3" cols="100" class="form-control" id="content_zh" name="content_zh" placeholder="请输入模板内容"></textarea>
										</div>
									</div>
								</div><!-- /.box-body -->
							</div>
						</div>
						<div class="col-md-6">
							<div class="box box-info">
			                	<div class="box-header with-border">
			                  		<h3 class="box-title">英文模板</h3>
			                	</div><!-- /.box-header -->
								<div class="box-body">
									<div class="form-group">
				                    	<label class="col-sm-3 control-label" >通知标题(邮件)</label>
				                    	<div class="col-xs-7">
				                    		<input class="form-control" id="title_en" name="title_en" placeholder="请输入邮件标题"></textarea>
				                    	</div>
				                    </div>
				                    <div class="form-group">
				                    	<label class="col-sm-3 control-label" >消息内容</label>
				                    	<div class="col-xs-7">
				                    		<textarea rows="3" cols="100" class="form-control" id="content_en" name="content_en" placeholder="请输入模板内容"></textarea>
				                    	</div>
				                    </div>
								</div><!-- /.box-body -->
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
		</div>

	</section>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/jquery/pager/jquery.pager.bootstrap.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/jquery/pager/pager.js"></script>
	<script>
	var path = "<%=request.getContextPath()%>";
	$(function(){
		var rules = {
				templateCode : {
					required : true,
					remote: "<%=request.getContextPath()%>/message/template/templateCodeExist",
					maxlength:50,
					
				},
				moduleName : {
					required : true,
					maxlength:50
				},
				templateName : {
					required : true,
					maxlength:50
				},
				title_zh : {
					required : true,
					maxlength:50
				},
				title_en : {
					required : true,
					maxlength:50
				},
				content_zh : {
					required : true,
					maxlength:10240
				},
				content_en : {
					required : true,
					maxlength:10240
				}
			};

		$("#templateForm").validate({
			rules : rules,
			submitHandler: function(form){
				var template = {
					'templateCode':$('#templateCode').val(),
					'moduleName':$('#moduleName').val(),
					'templateName':$('#templateName').val(),
					'content_zh':$('#content_zh').val(),
					'title_zh':$('#title_zh').val(),
					'content_en':$('#content_en').val(),
					'title_en':$('#title_en').val(),
				}
				
				console.log(template)
				$.ajax({
		    	  url: path+"/message/template/add",
		    	  type:'post',
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
					required : "模板编号不能为空",
					remote: "模板号已经存在，请修改"
				},
				moduleName : {
					required : "模块名称不能为空",
				},
				templateName : {
					required : "模板名称不能为空",
				},
				title_zh : {
					required : "请输入中文标题",
				},
				title_en : {
					required : "请输入英文标题",
				},
				content_zh : {
					required : "请输入中文内容",
				},
				content_en : {
					required : "请输入英文内容",
				}
			}
		});
	});
 
	</script>
</body>
</html>