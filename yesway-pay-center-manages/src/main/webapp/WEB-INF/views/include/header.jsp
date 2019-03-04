<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- header.jsp -->
<header class="main-header">
	<!-- Logo -->
	<a href="" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini">
			<img src="<%=request.getContextPath()%>/static/images/logo_yesway.png" width="30px" height="30px" />
		</span>
		<!-- logo for regular state and mobile devices --> 
		<span class="logo-lg"> 
			<img src="<%=request.getContextPath()%>/static/images/logo_yesway.png" width="30px" height="30px" />
			<b>支付中心管理系统</b>
		</span>
	</a>
	<!-- Header Navbar -->
	<nav class="navbar navbar-static-top" role="navigation">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span>
		</a>
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown">
					<span class="glyphicon glyphicon-user"></span>
					<%-- <span class="hidden-xs">
						${user.loginName}
					</span> --%>
				</a>
				<ul class="dropdown-menu">
					<!-- User image -->
					<li class="user-header" style="height:auto;">
						<p>
							当前登录：<b>${user.lastName}</b>&nbsp;${user.firstName}
							<small>(${user.loginName})</small>
						</p>
					</li>
					<!-- Menu Body -->
				<!-- 	<li class="user-body">
					</li> -->
					<!-- Menu Footer-->
					<li class="user-footer">
						<div class="pull-left">
							<a href="#" class="btn btn-default btn-flat" onclick="javascript:updatePassword('${user.userId}')">
								<span class="fa fa-fw fa-gears"></span>&nbsp;&nbsp;重置密码
							</a>
						</div>
						<div class="pull-right">
						<div align="center">
							<a href="#" class="btn btn-default btn-flat" onclick="javascript:logout()">
								<i class="glyphicon glyphicon-log-in"></i>&nbsp;&nbsp;注销登录
							</a>
						</div>
					</li>
				</ul>
				</li>
				<!-- Control Sidebar Toggle Button -->
				<!-- <li><a href="javascript:;" onclick="javascript:logout();">
						<i class="glyphicon glyphicon-log-in"></i>
				</a></li> -->
			</ul>
		</div>
	</nav>
</header>
<!-- header.jsp END -->
<script>
	var path = "<%=request.getContextPath()%>";
	function logout() {
		confirmBox("确定注销么？", function() {
			window.location.href = path + "/logout";
		}, {}, {
			cancleText : "取消",
			okText : "注销",
		/* title : "提示消息" */
		});
	}
	
	function updatePassword(userId){
		modalFrame(path + "/user/resetPassword.html?userId=" + userId, {
			width:"500px",
			height:"255px",
			overflow:"hidden"
		});
	}

</script>