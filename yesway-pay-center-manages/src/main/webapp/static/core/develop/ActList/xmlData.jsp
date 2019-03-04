<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%response.setContentType("text/xml;charset=UTF-8");%>
<%@ page import="org.apache.struts2.dispatcher.Dispatcher"%>
<%@ page import="com.opensymphony.xwork2.config.entities.ActionConfig"%>
<%@ page import="java.lang.reflect.*"%>
<?xml version="1.0" encoding="UTF-8" ?>
<tree id="0">
<%
 Map allActionConfigs = Dispatcher.getInstance().getConfigurationManager().getConfiguration()
				.getRuntimeConfiguration().getActionConfigs();
		Collection namespaces = allActionConfigs.keySet();
		for (Iterator iterator = namespaces.iterator(); iterator.hasNext();) {
			 //表空间
			String namespace = (String) iterator.next();
%>			 
 <item id="namespace_<%=namespace.hashCode() %>" text="<%=namespace%>">
<%	Map actionsOfNamespace = (Map) allActionConfigs.get(namespace);
			Collection co2 = actionsOfNamespace.keySet();
			for (Iterator iterator2 = co2.iterator(); iterator2.hasNext();) {
					String actionName = (String) iterator2.next();
					ActionConfig actionConfig = (ActionConfig) actionsOfNamespace.get(actionName);
					String actionClassName=actionConfig.getClassName();
					System.out.println(actionClassName);
					String className = actionConfig.getClassName();
					%>
			 <item id="action_<%=actionName%>" text="<%=actionName%>" child="0" open="1">		
					<%
						
%>					
	  </item>
<%}  %>	
			 </item>
<%}%>
</tree>