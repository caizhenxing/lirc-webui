<%@ page import="org.xmlprocess.lircServer.pageHelpers.MainPageHelper"%>

<%
MainPageHelper mph = new MainPageHelper();
System.out.println("xmlhttp called");
mph.process(getServletConfig().getServletContext(),request);
String retVal = mph.getScon().getActionXML();
System.out.println("xmlhttp retVal = "+retVal);
%>
