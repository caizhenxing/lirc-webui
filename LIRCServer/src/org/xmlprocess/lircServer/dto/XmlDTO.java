package org.xmlprocess.lircServer.dto;

import java.util.logging.Logger;

public class XmlDTO {
	
	private static final Logger log = Logger.getLogger(XmlDTO.class.getName());
	
	boolean irOK;
	//TODO DO I NEED CURRENT REMOTE? i.e. try with just settings.jsp or whatever
	//TODO  Find code for adding num td = num col
	public String currentRemote = "";
	public String webAppRootPath;
	public String settingsXSLTPath;
	public String mainXSLTPath;
	public String currentURL;
	
	public boolean isIrOK() {
		return irOK;
	}
	public void setIrOK(boolean irOK) {
		this.irOK = irOK;
	}
	public String getCurrentRemote() {
		return currentRemote;
	}
	public void setCurrentRemote(String currentRemote) {
		this.currentRemote = currentRemote;
	}
	public String getWebAppRootPath() {
		return webAppRootPath;
	}
	public void setWebAppRootPath(String webAppRootPath) {
		this.webAppRootPath = webAppRootPath;
	}
	public String getSettingsXSLTPath() {
		return settingsXSLTPath;
	}
	public void setSettingsXSLTPath(String settingsXSLTPath) {
		this.settingsXSLTPath = settingsXSLTPath;
	}
	public String getMainXSLTPath() {
		return mainXSLTPath;
	}
	public void setMainXSLTPath(String mainXSLTPath) {
		this.mainXSLTPath = mainXSLTPath;
	}
	public String getCurrentURL() {
		return currentURL;
	}
	public void setCurrentURL(String currentURL) {
		this.currentURL = currentURL;
	}
	
	public String getFullPath(String suffix) {
		return getWebAppRootPath()+suffix;
		
	}

}
