
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URI"%><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%
	response.setHeader("pragma","no-cache");
	response.setHeader("cache-control","no-cache");
	response.setHeader("expires","0");
	response.setHeader("Content-Type","application/octet-stream");
	String filename=request.getParameter("name");
	if(filename==null||filename.length()==0){
		filename="导出.xls";
	}
	filename=URLEncoder.encode(filename,"UTF-8");
	response.setHeader("content-Disposition","attachment;filename=\""+filename+"\"");
	
%>${param.content}<%
//System.out.println(request.getParameter("content"));
%>