<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'MyJsp.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
			<link href="<%=basePath%>core/dhtmlx/menu/menuTab.css"
			rel="stylesheet" type="text/css" />
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<body>
		<TABLE border=0 cellSpacing=0 width="100%">
			<TBODY>
				<TR>
					<TD class=menu_table_info onclick=window.event.cancelBubble=true;
						bgColor=#000000 height=24 vAlign=bottom width=20>
						操作
					</TD>
					<TD height=24 vAlign=bottom width=120>
						<TABLE border=0 cellSpacing=0 width="100%">
							<!-- Extend Item : P_suan -->
							<TBODY>
								<TR id=P_suan onmouseup=window.event.cancelBubble=true;
									class=menu_tr_out onmouseover='P_OnMouseOver("suan","rbpm")'
									onmouseout='P_OnMouseOut("suan","rbpm")'
									onclick=window.event.cancelBubble=true;>
									<TD class=menu_tr noWrap>
										<FONT style="FONT-SIZE: 18px" face=Wingdings>0</FONT> 添加合计列
									</TD>
									<TD style="FONT-FAMILY: webdings">
										4
									</TD>
								</TR>
								<!-- ITEM : I_sperator -->
								<TR onmouseup=window.event.cancelBubble=true; class=menu_tr_out
									onclick=window.event.cancelBubble=true;>
									<TD height=1 colSpan=2>
										<HR class=menu_hr_sperator>
									</TD>
								</TR>
								<!-- Extend Item : P_sd -->
								<TR id=P_sd onmouseup=window.event.cancelBubble=true;
									class=menu_tr_out onmouseover='P_OnMouseOver("sd","rbpm")'
									onmouseout='P_OnMouseOut("sd","rbpm")'
									onclick=window.event.cancelBubble=true;>
									<TD class=menu_tr noWrap>
										<FONT style="FONT-SIZE: 18px" face=Wingdings>0</FONT> 添加合计列
									</TD>
									<TD style="FONT-FAMILY: webdings">
										4
									</TD>
								</TR>
								<!-- Insert A Extend Menu or Item On Here For E_rbpm -->
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>

	</body>
</html>
