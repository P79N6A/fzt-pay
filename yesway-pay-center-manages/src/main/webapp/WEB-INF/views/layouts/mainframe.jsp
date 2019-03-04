<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><sitemesh:title /></title>
<%@ include file="/WEB-INF/views/include/commoncss.jsp"%>
<%@ include file="/WEB-INF/views/include/commonjs.jsp"%>
<%@ include file="/WEB-INF/views/include/jquery-validation.jsp"%>
<script type="text/javascript">
	window.oldalert = parent.oldalert;
	window.alert = parent.alert;
	window.confirmBox = parent.confirmBox;
	window.modalFrame = parent.modalFrame;
</script>
<sitemesh:head />
</head>
<body class="hold-transition skin-blue" style="">
	<div class="content-wrapper" style="margin: 0px">
		<sitemesh:body />
	</div>
	<!-- ./wrapper -->
	<%@ include file="/WEB-INF/views/include/popover.jsp"%>
</body>
</html>
