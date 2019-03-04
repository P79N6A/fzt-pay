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
			角色信息<small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>角色信息管理</a></li>
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
					<%-- <input type="hidden" value="${message}" id="message"> --%>
					<!--<caption>基本的表格布局</caption>  -->
					<thead>
						<tr>
							<th>角色编号</th>
							<th>角色名称</th>
							<th>所属系统</th>
							<th>角色启用状态</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="role" items="${pager.datas}" varStatus="status">
							<tr>
								<td><span>${role.roleId}</span></td>
								<td>${role.roleName}<input type="hidden" value="${role.roleId}" name="roleId" /></td>
								<td>${role.group} </td>
								<td>
									<c:if test="${role.status eq '0'}">
										<span style="color: gray;">可用</span>
									</c:if>
									<c:if test="${role.status eq '1'}">
										<span style="color: green;">禁用</span>
									</c:if>
									 </td>
								<td><span>${role.remark}</span></td>
								<td><a href="javascript:void(0)"  onclick='updaterole(this)' style="cursor: pointer;">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
								    <a href="javascript:void(0)" onclick='deleterole(this)' style="cursor: pointer;">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
								    <a href="javascript:void(0)" onclick='authorizeole(this)' style="cursor: pointer;">授权</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
	 			window.location.href="<%=request.getContextPath()%>/role/roleList.html?pageNumber=" + pageclickednumber + "&pageSize=" +pageSize;
	 		}, {});
		    $("#add").bind("click", function() {
				window.location.href="<%=request.getContextPath()%>/role/addorUpdate";
             });
	});
	
		function deleterole(nowTr){
			confirmBox("确定删除?", function(nowTr){
				var roleId= $(nowTr).parent().parent().find("input[name=roleId]").val();
				$.ajax({
					 url:path+"/role/deleterole",
					 data: {roleId:roleId},
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
			var roleId= $(nowTr).parent().parent().find("input[name=roleId]").val();
			window.location.href=path+"/role/addorUpdate?roleId="+ roleId;
		};
      //授权
      function authorizeole(nowTr){
    	  var roleId= $(nowTr).parent().parent().find("input[name=roleId]").val();
    	 /*  var url = path+"/role/authorizerole.html";
    	  alert(roleId)
    	  var title = "用户角色授权";
    	  window.open(url,roleId, title,'width=580,height=290,left=385,top=200,toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=auto,resizable=yes');
    	  */
    	  modalFrame(path + "/role/authorizerole.html?roleId="+roleId, {
				width:"400px",
				height:"400",
				overflow:"show"
			});
      }
      //overflow:"hidden"
	</script>
</body>
</html>