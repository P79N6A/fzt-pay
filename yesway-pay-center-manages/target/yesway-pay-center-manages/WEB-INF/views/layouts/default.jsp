<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<sitemesh:head />
</head>
<body class="hold-transition skin-blue sidebar-mini" style="overflow: hidden;">

	<div class="wrapper">
		<%@ include file="/WEB-INF/views/include/header.jsp"%>
		<%@ include file="/WEB-INF/views/include/navigation.jsp"%>
		<sitemesh:body />
		<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	</div>
	<%@ include file="/WEB-INF/views/include/windowconfirm.jsp"%>
	<%@ include file="/WEB-INF/views/include/windowalert.jsp"%>
	<%@ include file="/WEB-INF/views/include/windowmodal.jsp"%>
</body>
</html>
