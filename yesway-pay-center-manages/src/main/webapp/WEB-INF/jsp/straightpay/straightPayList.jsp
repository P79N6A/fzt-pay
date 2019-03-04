<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>二级商户审核信息管理</title>
	<link rel="stylesheet" href="${request.contextPath}/static/plugins/datepicker/skin/WdatePicker.css">
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			二级商户审核信息<small>列表显示</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i>二级商户信息管理</a></li>
			<!-- <li class="active">Here</li> -->
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="box  box-default">
			<div class="box-header">
				<button type="button" class="btn btn-default" id="add">添加商户</button>
				<div class="box-tools"></div>
			</div>
			<form class="form-inline" id="searchForm" action="<%=request.getContextPath()%>/aliStraightPay/straightPayList.html">
				<input type="hidden" name="pageNumber" value="${pager.pageNumber }"/>
				<input type="hidden" name="pageSize" value="${pager.pageSize }"/>
				<div class="form-group">
					<input type="text" class="form-control" id="nameLike" name="nameLike" placeholder="商户名称"/>
				</div>
				<div class="form-group">
					<label>创建时间：</label>
					<input type="text" id="start_time" name="beginTime" value=""  class="form-control" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_time\')}',el:this})"/>
					<label>至</label>
					<input type="text" id="end_time" name="endTime" value=""  class="form-control" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_time\')}',el:this})"/>
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary">查询</button>
				</div>
			</form>
			<div class="box-body">
				<table id="example" class="table table-striped table-bordered"
					cellspacing="0" width="100%">
					<%-- <input type="hidden" value="${message}" id="message"> --%>
					<!--<caption>基本的表格布局</caption>  -->
					<thead>
						<tr>
							<th>编号</th>
							<th>商户简称</th>
							<th>联系电话</th>
							<th>建立时间</th>
							<th>审核状态</th>
							<th>业务状态</th>
							<%--<th>操作</th>--%>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="merchantsAudit" items="${pager.datas}" varStatus="status">
							<tr>
								<td><span>${status.index+1}</span></td>
								<td>${merchantsAudit.merchants} </td>
								<td>${merchantsAudit.phone}</td>
								<td><fmt:formatDate value="${merchantsAudit.addTime}" pattern="yyyy-MM-dd " /></td>
								<td>
									<c:if test="${merchantsAudit.auditStatus eq '1'}">
										<span style="color: gray;">已保存</span>
									</c:if>
									<c:if test="${merchantsAudit.auditStatus eq '2'}">
										<span style="color: green;">审核中</span>
									</c:if>
									<c:if test="${merchantsAudit.auditStatus eq '3'}">
										<span style="color: green;">审核失败</span>
									</c:if>
									<c:if test="${merchantsAudit.auditStatus eq '4'}">
										<span style="color: green;">审核通过</span>
									</c:if>
								</td>
								<td>
									<c:if test="${merchantsAudit.businessStatus eq '1'}">
										<span style="color: gray;">正常</span>
									</c:if>
									<c:if test="${merchantsAudit.businessStatus eq '2'}">
										<span style="color: green;">停用</span>
									</c:if>
								</td>
								<%--<td>--%>
									<%--&lt;%&ndash;<a href="javascript:void(0)"  onclick="updaterole('${merchantsAudit.externalId}')" style="cursor: pointer;">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&ndash;%&gt;--%>
								    <%--&lt;%&ndash;<a href="javascript:void(0)" onclick='deleterole(this)' style="cursor: pointer;">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&ndash;%&gt;--%>
								    <%--&lt;%&ndash;<a href="javascript:void(0)" onclick='authorizeole(this)' style="cursor: pointer;">授权</a>&nbsp;&nbsp;&nbsp;&nbsp;&ndash;%&gt;--%>
								<%--</td>--%>
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
	<script src="<%=request.getContextPath()%>/static/core/My97DatePicker/WdatePicker.js"></script>
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
	 			window.location.href="<%=request.getContextPath()%>/aliStraightPay/straightPayList.html?pageNumber=" + pageclickednumber + "&pageSize=" +pageSize;
	 		}, {});
		    $("#add").bind("click", function() {
				window.location.href="<%=request.getContextPath()%>/aliStraightPay/add.html";
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
		function updaterole(obj){
			window.location.href=path+"/aliStraightPay/edit.html?externalId="+ obj;
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