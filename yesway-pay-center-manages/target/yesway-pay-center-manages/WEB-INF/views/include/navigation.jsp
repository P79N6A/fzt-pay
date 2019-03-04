<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>
</head>
<body class="treebg">

<aside class="main-sidebar">
	<section class="sidebar">
		<ul class="sidebar-menu">
			<li class="header">订单管理系统</li>
			
			<c:forEach var="item" items="${userProfile.moduleList}" varStatus="status" begin="0" step="1">
			         <c:if test="${item.type=='1' && item.parentId=='0' && item.subMenu==0 }">
			              <li>
				               <a href="#" onclick="openTab('Manager','','<%=request.getContextPath()%>${item.parentMenuUrl }')" >
					              <i class="fa fa-users"></i> <span>${item.menuName }</span>
			    	           </a>
			               </li> 
			           </c:if> 
			           <c:if test="${item.type=='1' && item.parentId=='0' && item.subMenu!=0 }">
			                <li class="treeview">
				                <a href="#" >
					              <i class="fa fa-cloud-upload"></i>
					               <span>${item.menuName }</span>
					              <i class="fa fa-angle-left pull-right"></i>
				                </a>
				                <ul class="treeview-menu" style="display: none;">				                
			              		<c:forEach var="it" items="${userProfile.moduleList}" varStatus="status" begin="0" step="1">				            
				         		  <c:if test="${ it.parentId == item.menuId}"> 
				                   <li>
				                      <a href="#" onclick="openTab('Manager','','<%=request.getContextPath()%>${it.parentMenuUrl }')" >
					                      <i class="fa fa-users"></i> <span>${it.menuName }</span>
			    	                  </a>
			                        </li> 
                                 </c:if>
				                </c:forEach>
				           </ul>
				           </li> 
			           </c:if> 
			     
			     </c:forEach>
<!-- 			start -->
		
<!-- 		stop 	 -->
		</ul>
	</section>
</aside>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zTree/js/jquery.ztree.core-3.5.min.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
	
<script type="text/javascript">
		
</script>
</body>