<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>角色信息管理</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			支付宝信息<small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>支付宝信息管理</a></li>
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
			 <input type="hidden" value="${message}" id="message" />
				<table id="example" class="table table-striped table-bordered"
					cellspacing="0" width="100%">
					<%-- <input type="hidden" value="${message}" id="message"> --%>
					<!--<caption>基本的表格布局</caption>  -->
					<thead>
						<tr>
							<th>oem编号</th>
							<th>mch编号</th>
							<th>商家名称</th>
							<th>商户appId</th>
							<th>回调地址</th>
							<th>yesway加密密钥</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="pay" items="${pager.datas}" varStatus="status">
							<tr>
								<td><span>${pay.oemId}</span></td>
								<td>${pay.mchId}<input type="hidden" value="${pay.payConfigId}" name="payConfigId" /></td>
								<td>${pay.mchName} </td>
								<td>${pay.appId} </td>
								<td><span>${pay.notityUrl}</span></td>
								<td><span>${pay.aesKey}</span></td>
								<td><a href="javascript:void(0)"  onclick='updaterole(this)' style="cursor: pointer;">修改</a>
<!-- 								    <a href="javascript:void(0)" onclick='deleterole(this)' style="cursor: pointer;">删除</a> -->
								    <a href="javascript:void(0)" onclick='detailInfo(this)' style="cursor: pointer;">详情</a>
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
	var totalPages = ${pager.totalPages};	
	var pageNumber = ${pager.pageNumber};	
	var pageSize = ${pager.pageSize};
	$(document).ready(function() {
		//操作成功提示
		  var message=  $('#message').val();
		  if(message!=null&&message!=''){
			  alert(message);
		  }
			initPager("pager", pageNumber, totalPages, function(pageclickednumber){
	 			window.location.href="<%=request.getContextPath()%>/payalipay/alipay?pageNumber=" + pageclickednumber + "&pageSize=" +pageSize;
	 		}, {});
		    $("#add").bind("click", function() {
				window.location.href="<%=request.getContextPath()%>/payalipay/addorUpdate";
             });
	});
	
		function deleterole(nowTr){
			confirmBox("确定删除?", function(nowTr){
				var payConfigId= $(nowTr).parent().parent().find("input[name=payConfigId]").val();
				$.ajax({
					 url:path+"/payalipay/deletepay",
					 data: {payConfigId:payConfigId},
		             dataType: "json",
					 success:function(msg){
							if(msg > 0){
								alert("删除成功");
								 $(nowTr).parent().parent().remove();
							}else{
								alert("删除失败");
							}
					}
				});
			}, nowTr, {
				cancleText : "再想想", 
				okText :  "删除",
			});
		};
		//修改
		function updaterole(nowTr){
			var payConfigId= $(nowTr).parent().parent().find("input[name=payConfigId]").val();
			window.location.href=path+"/payalipay/addorUpdate?payConfigId="+ payConfigId;
		};
      //详情
      function detailInfo(nowTr){
    	  var payConfigId= $(nowTr).parent().parent().find("input[name=payConfigId]").val();
    	  window.location.href=path+"/payalipay/detailInfo.html?payConfigId="+payConfigId;
    	 /*  modalFrame(path + "/payalipay/detailInfo.html?payConfigId="+payConfigId, {
				width:"400px",
				height:"400",
				overflow:"show"
			}); */
      }
      //overflow:"hidden"
	</script>
</body>
</html>