<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>微信信息管理</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			微信信息<small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>微信信息管理</a></li>
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
				<table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
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
								<td><a href="javascript:void(0)"  onclick='updaterole(this)' style="cursor: pointer;">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
<!-- 								    <a href="javascript:void(0)" onclick='deleterole(this)' style="cursor: pointer;">删除</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
								    <a href="javascript:void(0)" onclick='detailInfo(this)' style="cursor: pointer;">详情</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
	 			window.location.href="<%=request.getContextPath()%>/payweixin/weixin?pageNumber=" + pageclickednumber + "&pageSize=" +pageSize;
	 		}, {});
		    $("#add").bind("click", function() {
				window.location.href="<%=request.getContextPath()%>/payweixin/addorUpdate";
             });
	});
	
		function deleterole(nowTr){
			confirmBox("确定删除?", function(nowTr){
				var payConfigId= $(nowTr).parent().parent().find("input[name=payConfigId]").val();
				$.ajax({
					 url:path+"/payweixin/deletepay",
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
			window.location.href=path+"/payweixin/addorUpdate?payConfigId="+ payConfigId;
		};
      //详情操作
      function detailInfo(nowTr){
    	  var payConfigId= $(nowTr).parent().parent().find("input[name=payConfigId]").val();
    	  window.location.href=path+"/payweixin/detailInfo.html?payConfigId="+payConfigId;
    	 /*  var url = path+"/role/authorizerole.html";
    	  alert(roleId)
    	  var title = "用户角色授权";
    	  window.open(url,roleId, title,'width=580,height=290,left=385,top=200,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=yes');
    	  */
    	/*   modalFrame(path + "/role/authorizerole.html?payConfigId="+payConfigId, {
				width:"400px",
				height:"400",
				overflow:"show"
			}); */
      }
      //overflow:"hidden"
	</script>
</body>
</html>