<%@ page import="org.xmlprocess.lircServer.pageHelpers.MainPageHelper"%>

<%
MainPageHelper mph = new MainPageHelper();
mph.process(getServletConfig().getServletContext(),request);
%>

<%=mph.getScon().getMainHTML() %>
