<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.yesway.bmw.manage.utils.PropUtils" %>
<!-- footer.jsp -->
<footer class="main-footer" style="position:fixed;bottom:-5px;width:100%;padding-right:250px">
	<!-- To the right -->
	<div class="pull-right" style="position:fixed;right:50px">
<%-- 		<b>Version</b>:&nbsp;<%=PropUtils.get("system_version") %> --%>
		<b>Version</b>:&nbsp;1.0
	</div>
	<!-- Default to the left -->
	<strong>Copyright &copy; <span id="footerYear"></span>&nbsp;&nbsp;<a href="http://www.yesway.cn" target="yesway">YESWAY</a>.
	</strong> All rights reserved.
	<script>
		document.getElementById("footerYear").innerHTML = new Date().getFullYear();
	</script>
</footer>
<!-- footer.jsp end -->