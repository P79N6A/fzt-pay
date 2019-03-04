<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>菜单信息管理</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			菜单信息<small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>菜单信息管理</a></li>
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
							<th>菜单编号</th>
							<th>菜单名称</th>
							<th>路径</th>
							<!-- <th>父菜单名称</th> -->
							<!-- <th>菜单等级</th> -->
							<th>是否有子菜单</th>
							<!-- <th>菜单内部排序</th> -->
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
								<%-- <td><span>${menu.parentId}</span></td> --%>
								<%-- <td><span>${menu.type}</span></td> --%>
								<td></span>
								    <c:if test="${menu.subMenu eq '1'}">
								       <span style="color: gray;">有</span>
								    </c:if>
								    <c:if test="${menu.subMenu eq '0'}">
								       <span style="color: gray;">无</span>
								    </c:if>
								</td>
								<%-- <td><span>${menu.sort}</span></td> --%>
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
								    <a href="javascript:void(0)" onclick='submenu(this)' style="cursor: pointer;">子菜单</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
	 			window.location.href="<%=request.getContextPath()%>/menu/menuList.html?pageNumber=" + pageclickednumber + "&pageSize=" +pageSize;
	 		}, {});
		    $("#add").bind("click", function() {
				window.location.href="<%=request.getContextPath()%>/menu/addorUpdate";
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
			window.location.href=path+"/menu/addorUpdate?menuId="+ menuId;
		};
      //查看进入子菜单管理
      function submenu(nowTr){
    	  var menuId= $(nowTr).parent().parent().find("input[name=menuId]").val();
    	  window.location.href=path+"/menu/fundsubmenu?menuId="+ menuId;
      }
	</script>
</body>
</html>