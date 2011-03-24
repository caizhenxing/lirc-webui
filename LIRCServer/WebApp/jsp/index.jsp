<%@ page import="org.xmlprocess.lircServer.pageHelpers.MainPageHelper"%>
<html>
<head>

<%

MainPageHelper mph = new MainPageHelper();
mph.process(getServletConfig().getServletContext(),request);

if(!mph.getScon().getCc().getConDTO().testIRpath[4] || !mph.getScon().getCc().getConDTO().testXMLpath[4]){	
%>
<jsp:forward page="settings.jsp" />
<%}	
if(!mph.getScon().getXmlcc().isIrOK()){
	//System.out.println("IR not OK forwarding to IRCheck.jsp");
	%>
<jsp:forward page="IRCheck.jsp" />
<%} else{ %>
<jsp:forward page="main.jsp" />
<%} %>
