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
			   <c:if test="${item.type=='1' && item.parentId=='0' && item.subMenu=='0' }">
			       <li>
				    <a href="#" onclick="openTab('Manager','','<%=request.getContextPath()%>${item.parentMenuUrl }')" >
					<i class="fa fa-users"></i> <span>${item.menuName }</span>
			    	</a>
			      </li> 
			  </c:if>
			  <c:if test="${item.type=='1' && item.parentId=='0'}">
			      <c:forEach var="it" items="${userProfile.moduleList}" varStatus="status" begin="0" step="1">
			           <c:if test="${it.type == '2' && it.parentId == item.menuId}">
			              <li class="treeview">
				                <a href="#" >
					              <i class="fa fa-cloud-upload"></i>
					               <span>${item.menuName }</span>
					              <i class="fa fa-angle-left pull-right"></i>
				                </a>
				               <ul class="treeview-menu" style="display: none;">
				                 <li>
				                  <a href="#" onclick="openTab('Manager','','<%=request.getContextPath()%>${it.parentMenuUrl }')" >
					              <i class="fa fa-users"></i> <span>${it.menuName }</span>
			    	              </a>
			                     </li> 
				              </ul>
                         </li> 
                       </c:if>
			      </c:forEach>
			  </c:if>
			</c:forEach>
		</ul>
	</section>
</aside>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zTree/js/jquery.ztree.core-3.5.min.js"></script>
 <script type="text/javascript" src="<%=request.getContextPath()%>/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
	
<script type="text/javascript">
		
		var setting = {
			check: {
				enable: true,
				chkboxType: {"Y":"p", "N":"ps"}
			},
			view: {
				dblClickExpand: false
			},
			data: {
				key: {
					name:"TREENAME"
				}
				,simpleData: {
					enable: true,
					idKey: "TREEID",
					pIdKey: "TREEPID"
				}
			},
			callback: {
				beforeClick: beforeClick,
				onCheck: onCheck
			}
		};

		var zNodes =${functionTree };

		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("powersTree");
			zTree.checkNode(treeNode, !treeNode.checked, true, true);
			return false;
		}
		
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("powersTree"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].TREEID + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			 $("#addForm input[name='powerids']").val(v); 
		}  
		$(document).ready(function(){
			$.fn.zTree.init($("#powersTree"), setting, zNodes);
		});
		
</script>
</body>