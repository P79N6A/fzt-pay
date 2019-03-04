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
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			OEM厂商管理 <small>OEM厂商添加/修改</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>OEM厂商</a></li>
			<li><a href="#"><i class="fa"></i>OEM厂商</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box box-solid box-default">
			<div class="box-header">OEM厂商添加/修改</div>
			<div class="box-body">
				<form class="form-horizontal"  id="oemForm" >
					<div class="form-group">
						<label for="oemId" class="col-sm-2 control-label"><font color="red">*</font>OEM ID</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="oemId" name="oemId" placeholder="请输入OEM ID" value="${oem.oemId}"
								<c:if test="${oem.oemId !=null }">readonly="readonly"</c:if>
							>
						</div>
					</div>
					<div class="form-group">
						<label for="oemName" class="col-sm-2 control-label"><font color="red">*</font>OEM名称</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="oemName" name="oemName" placeholder="请输入OEM名称" value="${oem.oemName}">
						</div>
					</div>
					<div class="form-group">
						<label for="oemId" class="col-sm-2 control-label"><font color="red">*</font>userId含义</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="userIdName" name="userIdName" placeholder="请输入userIdName" value="${oem.userIdName}">
						</div>
					</div>
					<div class="form-group">
						<label for="called" class="col-sm-2 control-label"> 呼叫小号</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="called" name="called" placeholder="请输入呼叫小号" value="${oem.called}">
						</div>
					</div>
					<div class="form-group">
						<label for="callbackUrl" class="col-sm-2 control-label"> 回调地址</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="callbackUrl" name="callbackUrl" placeholder="请输入回调地址" value="${oem.callbackUrl}">
						</div>
					</div>
					<div class="form-group">
						<label for="signKey" class="col-sm-2 control-label"><font color="red">*</font>加密秘钥</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="signKey" name="signKey" placeholder="请输入加密秘钥" value="${oem.signKey}">
						</div>
					</div>
					<div class="form-group">
						<label for="welcomeContent" class="col-sm-2 control-label"> 欢迎语</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="welcomeContent" name="welcomeContent" placeholder="请输入欢迎语" value="${oem.welcomeContent}">
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
	var path="<%=request.getContextPath()%>";
	$(function(){
		var validateRules = {
			rules : {
				oemId : {
					required : true,
					maxlength:50
				},oemName : {
					required : true,
					maxlength:50
				},userIdName:{
					required :true,
					maxlength:50
				},called:{
					required :false,
					maxlength:50
				},callbackUrl:{
					required :false,
					maxlength:50
				},signKey:{
					required :true,
					maxlength:32
				},welcomeContent:{
					required:false,
					maxlength:100
				}
			}
		};
		/* // 新增操作，增加远程验证
		validateRules.oemName = {
			required : true,
			remote: {
			    url: "../oem/nameexist",     //后台处理程序
			    type: "post",               //数据发送方式
			    dataType: "json",           //接受数据格式   
			    data: {                     //要传递的数据
			    	oemName: function() {
			            return $("#oemName").val();
			        }
			    }
			}
		}; */
		// 新增操作，增加远程验证
		var isAdd = ${oem==null};
		if(isAdd)
		validateRules.rules.oemId = {
			required : true,
			remote: {
			    url: "../oem/idexist",     //后台处理程序
			    type: "post",               //数据发送方式
			    dataType: "json",           //接受数据格式   
			    data: {                     //要传递的数据
			    	oemId : function(){
			        	return $("#oemId").val();
			        }
			    }
			}
		};
		$("#oemForm").validate({
			rules : validateRules.rules,
			submitHandler: function(form){      
			      //$(form).ajaxSubmit();    
			      var oem={'oemId':$("#oemId").val(),
		  		  			'oemName':$("#oemName").val(),
				  			'userIdName':$("#userIdName").val(),
				  			'called':$("#called").val(),
				  			'signKey':$("#signKey").val(),
				  			'welcomeContent':$("#welcomeContent").val(),
				  			'callbackUrl':$("#callbackUrl").val()
			  				};
			      oem = JSON.stringify(oem); 
			      $.ajax({
			    	  url: path+"/oem/save",
			    	  type:'post',
			    	  contentType: 'application/json' ,
			    	  data:oem,
			    	  success:function(data){
		    			  alert(data)
			    		  if(data=="保存成功"){
			    			  window.location.href=path+"/oem/oemList.html";
			    		  }
			    	  }
			      })
			   } ,
			messages:{
				oemId:{
					required :"OEM ID不能为空",
					remote:"OEM ID已经存在，请修改"
				},oemName:{
					required :"OEM名称不能为空",
				},userIdName : {
					required :"userId含义系统不能为空",
				},signKey :{
					required : "加密秘钥不能为空",
				}
			}
		});
	});
	</script>
</body>

</html>