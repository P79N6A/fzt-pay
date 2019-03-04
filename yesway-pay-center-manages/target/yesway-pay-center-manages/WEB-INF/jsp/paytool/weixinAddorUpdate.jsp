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
		               微信信息管理 <small>微信信息修改</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>微信信息列表</a></li>
			<li><a href="#"><i class="fa"></i>微信信息修改</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box box-solid box-default">
			<div class="box-header">微信信息列表</div>
			<div class="box-body">
				<form class="form-horizontal" role="form" id="roleForm" action="<%=request.getContextPath()%>/payweixin/save">
				<div class="margin-b-10">
				<input type="hidden" class="form-control" id="payConfigId" name="payConfigId" value="${pay.payConfigId}">
	                	<label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>oemId编号</label>
						<div class="btn-group">
							<input type="text" class="form-control" id="oemId" name="oemId" placeholder="请输入oemId" value="${pay.oemId}"
							>
						</div>
						 <span style="display:inline-block;width:150px;text-align:right;"> mchId编号</span> 
						 <div class="btn-group">
						   <input type="text" id="mchId" name="mchId"  value="${pay.mchId}"  class="form-control" placeholder="请输入mchId" style="width: 150px; display: inline;"> 
						 </div>
						 <span style="display:inline-block;width:150px;text-align:right;"> 商户名称</span> 
						 <div class="btn-group">
						   <input type="text" id="mchName" name="mchName"  value="${pay.mchName}"  class="form-control" placeholder="请输入商户名称" style="width: 150px; display: inline;"> 
						 </div>
					</div>
					<div class="form-group">
					</div>
                   <div class="form-group">
					   <label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>appId</label>
	                   <div class="btn-group">
						   <input type="text" id="appId" name="appId"  value="${pay.appId}"  class="form-control" placeholder="请输入appId" style="width: 150px; display: inline;"> 
						 </div>
						 <span style="display:inline-block;width:222px;text-align:right;">商户秘钥</span> 
						  <div class="btn-group">
						   <input type="text" id="aesKey" name="aesKey"  class="form-control" value="${pay.aesKey}"  placeholder="请输入aes秘钥" style="width: 150px; display: inline;"> 
						 </div>
						  <span style="display:inline-block;width:222px;text-align:right;">客户端证书密码</span> 
						  <div class="btn-group">
						   <input type="text" id="notifyClientPWD" name="notifyClientPWD"  class="form-control" value="${pay.notifyClientPWD}"  placeholder="回调客户端证书提取密码" style="width: 150px; display: inline;"> 
						 </div>
					</div>
					<div class="form-group">
					   <label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>微信商家号</label>
	                   <div class="btn-group">
						   <input type="text" id="weixinMchId" name="weixinMchId"  value="${pay.weixinMchId}"  class="form-control" placeholder="商家微信开通的商家号weixinMchId  " style="width: 150px; display: inline;"> 
						 </div>
						 <span style="display:inline-block;width:222px;text-align:right;">微信商户秘钥</span> 
						  <div class="btn-group">
						   <input type="text" id="weixinApiSecret" name="weixinApiSecret"  class="form-control" value="${pay.weixinApiSecret}"  placeholder="微信商户秘钥weixinApiSecret" style="width: 150px; display: inline;"> 
						 </div>
						  <span style="display:inline-block;width:222px;text-align:right;">证书名称</span> 
						  <div class="btn-group">
						   <input type="text" id="credentialAdress" name="credentialAdress"  class="form-control" value="${pay.credentialAdress}"  placeholder="请输入证书名称" style="width: 150px; display: inline;"> 
						 </div>
					</div>
					<div class="form-group">
					   <label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>客户端证书路径</label>
	                   <div class="btn-group">
						   <input type="text" id="notifyClient" name="notifyClient"  value="${pay.notifyClient}"  class="form-control" placeholder="请输入回调客户端证书路径" style="width: 150px; display: inline;"> 
						 </div>
						 <span style="display:inline-block;width:222px;text-align:right;">服务端证书路径</span> 
						  <div class="btn-group">
						   <input type="text" id="notifyServer" name="notifyServer"  class="form-control" value="${pay.notifyServer}"  placeholder="请输入回调服务端证书路径" style="width: 150px; display: inline;"> 
						 </div>
						  <span style="display:inline-block;width:222px;text-align:right;">服务端证书密码</span> 
						  <div class="btn-group">
						   <input type="text" id="notifyServerPWD" name="notifyServerPWD"  class="form-control" value="${pay.notifyServerPWD}"  placeholder="请输入回调客户端服务端密码" style="width: 150px; display: inline;"> 
						 </div>
					</div>
					
					<div class="form-group">
						<label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>回调地址</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="thirdNotifyUrl" name="thirdNotifyUrl" placeholder="微信/支付宝回调配置地址" value="${pay.thirdNotifyUrl}"
							>
						</div>
					</div>
					<div class="form-group">
						<label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>第三方回调地址</label>
						<div class="input-group col-sm-5">
							<input type="text" class="form-control" id="notityUrl" name="notityUrl" placeholder="yesway回调第三方配置地址" value="${pay.notityUrl}"
							>
						</div>
					</div>
					<div class="form-group">
					   <label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>yesway公钥</label>
	                   <div class="input-group col-sm-5">
	                       <textarea class="form-control" id="yeswayPublicKey" maxlength="1000" name="yeswayPublicKey" rows="3"  placeholder="yesway加密公钥">${pay.yeswayPublicKey}</textarea>
						 </div>
					</div>
					<div class="form-group">
					   <label for="roleId" class="col-sm-2 control-label"><font color="red">*</font>yesway私钥</label>
	                    <div class="input-group col-sm-5">
						 <textarea class="form-control" id="yeswayPrivateKey"  name="yeswayPrivateKey" rows="3"  placeholder="yesway加密私钥"> ${pay.yeswayPrivateKey}</textarea> 
						 </div>
					</div>
					<%-- <div class="form-group">
						<label for="lastname" class="col-sm-2 control-label"><font color="red">*</font>支付宝公钥</label>
						<div class="input-group col-sm-5">
						 	 <textarea class="form-control" id="alipayPublicKey"  name="alipayPublicKey" rows="3" placeholder="支付宝公钥">${pay.alipayPublicKey}</textarea>
						</div>
						<div class="form-group">
					    </div>
						<label for="lastname" class="col-sm-2 control-label"><font color="red">*</font>商户私钥</label>
						<div class="input-group col-sm-5">
						 	 <textarea class="form-control" id="yeswayAlipayPrivateKey"  name="yeswayAlipayPrivateKey" rows="3" placeholder="商户私钥">${pay.yeswayAlipayPrivateKey}</textarea>
						</div>
					</div> --%>
					
					<div class="form-group"> 
							<label class="control-label col-md-3"></label>
							<div class="col-md-9">
								
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