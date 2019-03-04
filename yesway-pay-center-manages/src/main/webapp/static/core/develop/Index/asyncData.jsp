<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	response.setContentType("text/xml;charset=UTF-8");
	System.out.println("asyncData.jsp");
	Enumeration em = request.getParameterNames();
	while (em.hasMoreElements()) {
		String s = (String) em.nextElement();
		System.out.println(s + ":" + request.getParameter(s));
	}
	String id = request.getParameter("id");
	if (id == null)
		return;
	if (id.equals("root")) {
%>

<?xml version="1.0" encoding="UTF-8" ?>
<tree id="root">
	<item id="1123" text="用户列表111111111" child="1"  select="yes">

	</item>
	<item id="gen_11231" text="1用户列表111111111" child="1"  select="yes">

	</item>
	<item id="gen_11232" text="2用户列表111111111" child="1"  select="yes">

	</item>
</tree>
<%
	}
%>
<%
	if (id.equals("1123")||id.equals("11232")) {
%>
<?xml version="1.0" encoding="UTF-8" ?>
<tree id="1123" child="1"> 
	<item id="1123" text="用户列表1" child="0" /> 
</tree>

<%
	}
%>
<%
	if (id.startsWith("gen_")) {
		System.out.println("------------auto gen id------------");
%>
<?xml version="1.0" encoding="UTF-8" ?>
<tree id="<%=id%>" child="1"> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
	<item id="<%="gen_"+(new java.util.Random().nextInt()) %>" text="auto" child="1" /> 
</tree>
<%
	}
%>