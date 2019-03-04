<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>二级菜单管理</title>
</head>
<body>
	<section class="content-header">
		<h1>
			二级菜单信息<small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>二级菜单信息管理</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box  box-default">
			<div class="box-header">
			<form method="post" action="action=<%=request.getContextPath()%>/menu/fundsubmenu">
				<input type="hidden" name="pageNumber" value="1">
				<input  type="hidden" name="pageSize" value="10"> 
				<button type="button" class="btn btn-default" id="add">添加</button>
				<div class="box-tools">
				</div>
			</form>
			</div>
			<div class="box-body">
				<table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
					<!--<caption>基本的表格布局</caption>  -->
					<input type="hidden" value="${parentId}" name="menuId" id="menuId">
					<thead>
						<tr>
							<th>二级菜单编号</th>
							<th>二级菜单名称</th>
							<th>菜单路径</th>
							<th>父节点</th>
							<th>菜单启用状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="menu" items="${pager.datas}" varStatus="status">
							<tr>
								<td><span>${menu.menuId}</span></td>
								<td>${menu.menuName}<input type="hidden" value="${menu.menuId}" name="menuId" /></td>
								<td><span>${menu.parentMenuUrl}</span></td>
								<td><span>${menu.parentId}</span></td>
								<td>
									<c:if test="${menu.status eq '0'}">
										<span style="color: gray;">可用</span>
									</c:if>
									<c:if test="${menu.status eq '1'}">
										<span style="color: green;">禁用</span>
									</c:if>
									 </td>
								<td><a href="javascript:void(0)"  onclick='updatemenu(this)' style="cursor: pointer;">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
								    <a href="javascript:void(0)" onclick='deletemenu(this)' style="cursor: pointer;">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
	if(pageSize<1)pageSize=10;
	var changePage = function(pageIndex){
		$("input[name='pageNumber']").val(pageIndex);
		$("input[name='pageSize']").val(pageSize);
		$("form:first").submit();
	};
	$(document).ready(function() {
		
			initPager("pager", pageNumber, totalPages, function(pageclickednumber){
				changePage(pageclickednumber);
	 			<%-- window.location.href="<%=request.getContextPath()%>/menu/fundsubmenu?pageNumber=" + pageclickednumber + "&pageSize=" +pageSize; --%>
	 		}, {});
		    $("#add").bind("click", function() {
		    	var menuId = $("input[name='menuId']").val(); 
		    	window.location.href=path+"/menu/submenuAdd?menuId="+ menuId;
             });
	});
	
		function deletemenu(nowTr){
			confirmBox("确定删除?", function(nowTr){
				var menuId= $(nowTr).parent().parent().find("input[name=menuId]").val();
				$.ajax({
					 url:path+"/menu/deletemenu",
					 data: {menuId:menuId},
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
		function updatemenu(nowTr){
			var menuId= $(nowTr).parent().parent().find("input[name=menuId]").val();
			window.location.href=path+"/menu/submenuUpdate?menuId="+ menuId;
		};
     
	</script>
</body>
</html>