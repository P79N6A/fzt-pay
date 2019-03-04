<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="decorator" content="mainframe" />
<title>角色授权管理</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/plugins/iCheck/square/blue.css"></link>
<link rel="stylesheet" href="<%=request.getContextPath()%>/zTree/css/zTreeStyle/zTreeStyle.css"></link>
<script type="text/javascript" src="<%=request.getContextPath()%>/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>

<style>
	<!--
		body{overflow: hidden}
		.content-wrapper{background-color:#fff}
		.modal-content{box-shadow:none;}
		.modal-footer{}
	-->
</style>
</head>
<body>
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-label="Close" onclick="javascript:parent.modalFrame('hide')">
				<span aria-hidden="true">×</span>
			</button>
			<h4 class="modal-title">为&nbsp;<b>${roleId}</b>&nbsp;角色授权</h4>
		</div>
	  <form id="addForm" action="#" class="form-horizontal">
	       <input type="hidden" id="roleId" value="${roleId}"/>
		   <div class="modal-body">
     		<div class="checkbox icheck">
     		      <!-- <label class="control-label col-md-3"></label> -->
							<div class="col-md-9">
								 <input name="powerids" type="hidden">
								 <div id="powermenudiv">
									<ul id="powersTree" class="ztree"  ></ul>
								</div>
						   </div>
			</div>
		</div>
	 </form>
		<div></div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default pull-left" id="button_hide" onclick="javascript:parent.modalFrame('hide')"
				data-dismiss="modal">关闭</button>
			<button type="button" class="btn btn-primary" onclick="saveUserRole()">保存</button>
		</div>
	</div>
	<!-- /.modal-content -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/plugins/iCheck/icheck.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/zTree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
	
	<script type="text/javascript">
	var path = "<%=request.getContextPath()%>";
	//保存角色授权信息
	function saveUserRole(){
		var roleId = $("#roleId").val();
		var powerids = $("#addForm input[name='powerids']").val(); 
		//alert(powerids);
		$.ajax({
			url:path + "/role/saveAuthorize.html",
			data : {
				roleId : roleId,
				powerids:powerids
			},
			datetype : "json",
			success : function(msg) {
				parent.modalFrame('hide');
				setTimeout(function(){
					if(msg>0){
						alert("授权成功!")
						//ymPrompt.alert({title:'信息提示',message:"角色授权成功！"});
						return ;
						}else{
						alert("授权失败!")
						//ymPrompt.alert({title:'信息提示',message:"角色授权失败！"});
						     }
				}, 200);
				
			}
		});
	}
	//alert(JSON.stringify(zNodes));
	</script>
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
</html>