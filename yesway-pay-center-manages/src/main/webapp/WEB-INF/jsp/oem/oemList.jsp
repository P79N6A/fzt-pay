<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>OEM厂商管理</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			OEM厂商<small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>OEM厂商管理</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box  box-default">
			<div class="box-header">
				<button type="button" class="btn btn-default" id="add">添加</button>
				<div class="box-tools"></div>
			</div>
			<div class="box-body">
				<table id="example" class="table table-striped table-bordered"
					cellspacing="0" width="100%">
					<input type="hidden" value="${message}" id="message">
					<!--<caption>基本的表格布局</caption>  -->
					<thead>
						<tr>
							<th>OEM编号</th>
							<th>OEM名称</th>
							<th>userId名字</th>
							<th>被叫小号</th>
							<th>订单回调地址</th>
							<th>signKey</th>
							<th>欢迎语</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="oem" items="${oemList}" varStatus="status" >
							<tr>
								<td><span>${oem.oemId}</span></td>
								<td>${oem.oemName} </td>
								<td>${oem.userIdName} </td>
								<td>${oem.called} </td>
								<td>${oem.callbackUrl} </td>
								<td>${oem.signKey} </td>
								<td><span>${oem.welcomeContent}</span></td>
								<td><a href="javascript:void(0)"  onclick="updateOem('${oem.oemId}')" style="cursor: pointer;">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
								    <a href="javascript:void(0)" onclick="deleteOem('${oem.oemId}');" style="cursor: pointer;">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
								    <a href="javascript:void(0)" onclick="conciergeSettings('${oem.oemId}');" style="cursor: pointer;">礼宾</a>&nbsp;&nbsp;&nbsp;&nbsp;
								    <a href="javascript:void(0)" onclick="messageTemplate('${oem.oemId}');" style="cursor: pointer;">通知</a>&nbsp;&nbsp;&nbsp;&nbsp;
								    </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
					<nav id="pager"></nav>
			</div>
		</div>

	</section>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/jquery/pager/jquery.pager.bootstrap.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/jquery/pager/pager.js"></script>
	<script>
	var path = "<%=request.getContextPath()%>";

	$(document).ready(function() {
		//操作成功提示
		  var message=  $('#message').val();
		  if(message!=null&&message!=''){
			  alert(message);
		  }
		    $("#add").bind("click", function() {
				window.location.href="<%=request.getContextPath()%>/oem/addOrUpdateOem.html";
             });
	});
	
		function deleteOem(oemId){
			confirmBox("确定删除?", function(){
				$.ajax({
					 url:path+"/oem/deleteOem",
					 data: {"oemId":oemId},
		             dataType: "json",
					 success:function(msg){
							if(msg ==true){
								alert("删除成功");
								location.reload();
							}else{
								alert("删除失败");
							}
					}
				});
			}, null, {
				cancleText : "再想想", 
				okText :  "删除",
			});
		};
		function updateOem(oemId){
			window.location.href=path+"/oem/addOrUpdateOem.html?oemId="+ oemId;
		};
		function conciergeSettings(oemId){
			window.location.href=path+"/oem/conciergeSettings.html?oemId="+ oemId;
		};
		var messageTemplate = function(oemId){
			window.location.href=path+"/oem/defaultTemplate.html?oemId="+ oemId;
		};
 
	</script>
</body>
</html>