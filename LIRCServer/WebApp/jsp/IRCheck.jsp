<%@ page import="org.xmlprocess.lircServer.pageHelpers.MainPageHelper"%>
<html>
<head>

<%
MainPageHelper mph = new MainPageHelper();
mph.process(getServletConfig().getServletContext(),request);
%>


<title>Settings</title>
<link type="text/css" rel="stylesheet" href="css/doc.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script language="javascript" type="text/javascript"
	src="./jscripts/lircServer.js"></script>
</head>
<body>

<h2 class="docTitle">IRCheck</h2>


<p></p>
<hr />
<p>
<% String Message = request.getParameter("Error_Message");
if(Message == null || Message.length() == 0){
	Message = "Error Message goes here";
}
if(Message != null && Message.length() > 0){%>
<span class="error">
<%=Message %>
</span>
<%} %>
</p>


</body>

</html>
