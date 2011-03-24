package org.xmlprocess.lircServer.pageHelpers;

import java.util.Enumeration;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.xmlprocess.lircServer.ServerController;
import org.xmlprocess.lircServer.common.CommonStatics;



public class MainPageHelper {
	
	private static final Logger log = Logger.getLogger(MainPageHelper.class.getName());
	
	ServletContext sc;

	HttpServletRequest request;

	HttpSession session;
	
	ServerController scon = null;
	
	
	public void process(ServletContext sc1, HttpServletRequest request1) {

		init(sc1, request1);
		//log.error("process Session id 1 = "+session.getId());
		processInitParams();
		setValuesFromParam();
	}
	
	public void init(ServletContext sc1, HttpServletRequest request1) {
		request = request1;
		sc = sc1;
		//su.logParameters("IndexHelper line 78");
		session = request.getSession();

	}

	
	public void processInitParams() {
		if (scon == null) {
			//log.info("scon was null");
			scon = new ServerController();
			//for testing - move to web.xml
			//String propsFN = "/home/adamf/LircTest/test.props";
			//String propsFN = "D:/Data/AtestXML/test/Lirc/test.props";
			
			String webAppPath = sc.getRealPath("/");
			//log.info("webAppPath = "+webAppPath);
			scon.getXmlcc().getXmlDTO().setWebAppRootPath(webAppPath);
			
			String settingsXSLT = sc.getInitParameter(CommonStatics.SETTINGS_XSLT);
			if(settingsXSLT == null || settingsXSLT.length() == 0){
				settingsXSLT = "xslt/config2html.xslt";
			}
			scon.getXmlcc().getXmlDTO().setSettingsXSLTPath(settingsXSLT);
			
			String mainXSLT = sc.getInitParameter(CommonStatics.MAIN_XSLT);
			if(mainXSLT == null || mainXSLT.length() == 0){
				mainXSLT = "xslt/lircXml2html.xslt";
			}
			scon.getXmlcc().getXmlDTO().setMainXSLTPath(mainXSLT);
			
			scon.getXmlcc().getXmlDTO().setCurrentURL(request.getRequestURL().toString());
			
			String configPropsFN = sc.getInitParameter(CommonStatics.CONFIG_PROPS);
			String configFN_FP="";
			if(configPropsFN == null || configPropsFN.length() == 0){
				configFN_FP = "/home/adamf/LircTest/test.props";
			}
			else{configFN_FP = scon.getXmlcc().getXmlDTO().getFullPath(configPropsFN);}
			
			//System.out.println("configPropsFN = "+configPropsFN);
			//System.out.println("configFN_FP = "+configFN_FP);
			

			scon.init(configFN_FP);	
		}
		
		
		
	}
	
	
	public void setValuesFromParam() {
		
		//configPropsFN = request.getParameter(CommonStatics.CONFIG_PROPS);
		
		/*log.info("getRequestURI = "+request.getRequestURI());
		log.info("getQueryString() = "+request.getQueryString());
		log.info("getRequestURL().toString() = "+request.getRequestURL().toString());*/
		
		
		String lircDir = request.getParameter(CommonStatics.LIRC_DIR);
		String irSend = request.getParameter(CommonStatics.IRSEND);
		String xmlDir = request.getParameter(CommonStatics.XML_DIR);
		String xmlFile = request.getParameter(CommonStatics.XML_FILE);
		
		String createXmlFile = request.getParameter(CommonStatics.CREATE_XML);
		String resetXML= request.getParameter(CommonStatics.RESET_XML);
		
		String uuid = request.getParameter(CommonStatics.A_UUID);

		/*log.info("lircDir = "+lircDir);
		log.info("irSend = "+irSend);
		log.info("xmlDir = "+xmlDir);
		log.info("xmlFile = "+xmlFile);*/
		
		//log.info("createXmlFile = "+createXmlFile);
		
		//logAttribs();
		//logParameters();
		
		//log.info("uuid = "+uuid);
		
		boolean settingsChanged = false;
		
		if(lircDir != null) {
			scon.getCc().getConDTO().setLircDir(lircDir);
			settingsChanged = true;
		}
		if(irSend != null) {
			scon.getCc().getConDTO().setIrSend(irSend);
			settingsChanged = true;
		}
		if(xmlDir != null) {
			scon.getCc().getConDTO().setXmlDir(xmlDir);
			settingsChanged = true;
		}
		if(xmlFile != null) {
			scon.getCc().getConDTO().setXmlFile(xmlFile);
			settingsChanged = true;
		}
		
		if(settingsChanged) {
			try {
				scon.getCc().getConDTO().writeProps();
				scon.getCc().checkProps();
			} catch (Exception e) {
				log.severe("Error Writing Props");
			}
		}
		
		if(uuid != null) {
			//log.info("UUID = "+uuid);
			scon.setUuid(uuid);
		}
		
		if(createXmlFile != null && createXmlFile.equalsIgnoreCase("yes")) {
			try {
				//log.info("should be calling getDoc");
				getScon().getXmlcc().getDoc();
			} catch (Exception e) {
				log.severe("Error trying to get an XML File!!");
				e.printStackTrace();
			}
		}
		
		if(resetXML != null && resetXML.equalsIgnoreCase("yes")) {
			try {
				//log.info("should be calling getDoc");
				getScon().getXmlcc().resetAllUUID();
			} catch (Exception e) {
				log.severe("Error trying to reset UUID in XML File!!");
				e.printStackTrace();
			}
		}


	}

	public ServerController getScon() {
		return scon;
	}

	public void setScon(ServerController scon) {
		this.scon = scon;

	}
	
	public void logAttribs() {

		for (Enumeration AttribNames = sc.getAttributeNames(); AttribNames
				.hasMoreElements();) {
			String AttribName = (String) AttribNames.nextElement();
			log.info("logAttribs Attribname = " + AttribName);
		}

	}
	
	public void logParameters() {
			log.info("logParams called");
		// Hashtable result = new Hashtable();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				log.info("logParameters paramName = " + paramName
						+ " paramValue = " + paramValue);
				/*
				 * log.error("parseFormControls adding to hashtable paramName = " +
				 * paramName + " paramValue = " + paramValue);
				 */
				// result.put(paramName, paramValue);
			}
		}

	}


}



