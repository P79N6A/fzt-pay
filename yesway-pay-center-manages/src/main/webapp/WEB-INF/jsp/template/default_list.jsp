<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>默认模板管理</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			默认模板<small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>默认模板管理</a></li>
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
					<thead>
						<tr>
							<th>模板编号</th>
							<th>模块名称</th>
							<th>模板名称</th>
							<th>模板内容</th>
							<th>模板语言</th>
							<th>添加时间</th>
							<th>更新时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${templateList}" var="template"  varStatus="status">
							<tr>
								<td>${template.templateCode}</td>
								<td>${template.moduleName}</td>
								<td>${template.templateName}</td>
								<td><%-- ${template.content} --%><a href="javascript:void(0)">查看</a>
								</td>
								<td>${template.language}</td>
								<td><fmt:formatDate value="${template.addTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
								<td><fmt:formatDate value="${template.updateTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
								<td><a href="javascript:void(0)"  onclick='updateTemplate(this,"${template.templateId}")' style="cursor: pointer;">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
								    <a href="javascript:void(0)" onclick='deleteTemplate(this,"${template.templateId}");' style="cursor: pointer;">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
 			window.location.href="<%=request.getContextPath()%>/message/template/default.html?pageNumber=" + pageclickednumber + "&pageSize=" +pageSize;
 		}, {});
	    $("#add").bind("click", function() {
			window.location.href="<%=request.getContextPath()%>/message/template/addorUpdate";
        });
	});
	
	function deleteTemplate(nowTr,templateId){
		confirmBox("确定删除?", function(nowTr){
			$.ajax({
				 url:path+"/message/template/delete",
				 data: {templateId:templateId},
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
	function updateTemplate(nowTr,templateId){
		window.location.href=path+"/message/template/addorUpdate?templateId="+ templateId;
	};
 
	</script>
</body>
</html>