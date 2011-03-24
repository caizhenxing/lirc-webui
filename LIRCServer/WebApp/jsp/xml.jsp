<%@ page import="org.xmlprocess.lircServer.pageHelpers.MainPageHelper"%>
<%
	String defEnc = "UTF-8";
	String sessionID = session.getId();
	MainPageHelper mph = new MainPageHelper();
	mph.process(getServletConfig().getServletContext(),request);
	String xmlString = mph.getScon().getXmlcc().getXML();
	//System.out.println("xmlString = "+xmlString);
	response.setContentType("text/xml");
	response.setCharacterEncoding(defEnc);
	response.getOutputStream().write(xmlString.getBytes(defEnc));
%>
