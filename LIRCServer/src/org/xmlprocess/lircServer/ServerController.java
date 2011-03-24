package org.xmlprocess.lircServer;

import java.util.logging.Logger;

import org.xmlprocess.lircServer.config.ConfigController;
import org.xmlprocess.lircServer.xml.XMLController;

public class ServerController {
	
	private static final Logger log = Logger.getLogger(ServerController.class.getName());
	ConfigController cc = new ConfigController();
	XMLController xmlcc = new XMLController();
	
	/*public boolean irpathOK;
	public boolean xmlPathOK;
	public boolean xmlDirOK;*/
	
	public String uuid;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String propsFN = "/home/adamf/LircTest/test.props";
		ServerController sc = new ServerController();
		sc.init(propsFN);
	}
	
	
	public void init(String propsFN) {
		initProps(propsFN);
		checkFiles();
		exec();
	}
	
	public void initProps(String propsFN) {
		try {
			cc.setupProps(propsFN);
			//cc.debugvals();
			cc.checkProps();
			//log.info("Check Props ="+cc.checkProps());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void checkFiles() {
		//IROK 
		//irpathOK = cc.getTestIRpath()[4];
		//xmlPathOK = cc.getTestXMLpath()[4];
		//xmlDirOK = cc.getTestXMLDir()[4];
		
		/*log.info("irpathOK "+irpathOK);
		log.info("xmlPathOK "+xmlPathOK);
		log.info("xmlDirOK "+xmlDirOK);*/
		
		//log.info(xmlcc.getSettingsXML_S(cc));
		
		
	}
	
	public String getSettingsXML() {
		return xmlcc.getSettingsXML_S();
	}
	
	public String getSettingsHTML() {
		return xmlcc.getSettingsHTML_S();
	}
	
	public String getMainXML() {
		return xmlcc.getXML();
	}
	public String getMainHTML() {
		//log.info("getMainHTML");
		return xmlcc.getHTML(getUuid());
	}
	
	public String getActionXML() {
		return xmlcc.getXMLAction(getUuid());
	}
	
	
	public void exec() {
		
		xmlcc.init();
		/*if(irpathOK) {
			xmlcc.setIrsendPath(cc.irSendPath);
			if(!xmlPathOK) {
				if(xmlDirOK) {
					xmlcc.setXmlDir(cc.xmlDir);
					if(cc.xmlFile == null || cc.xmlFile.length() == 0){
						xmlcc.setXmlFN(cc.xmlFile);
						cc.setXmlFile(xmlcc.getXmlFN());
						try {
							cc.writeProps();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

						
					log.info("irpathOK and xmlDirOK but no xml file Should create the XML File");
				xmlcc.init();
				}
			} 
			
			
		}*/
		
		
	}


	public ConfigController getCc() {
		return cc;
	}


	public void setCc(ConfigController cc) {
		this.cc = cc;
	}


	public XMLController getXmlcc() {
		return xmlcc;
	}


	public void setXmlcc(XMLController xmlcc) {
		this.xmlcc = xmlcc;
	}


/*	public boolean isIrpathOK() {
		return irpathOK;
	}


	public void setIrpathOK(boolean irpathOK) {
		this.irpathOK = irpathOK;
	}


	public boolean isXmlPathOK() {
		return xmlPathOK;
	}


	public void setXmlPathOK(boolean xmlPathOK) {
		this.xmlPathOK = xmlPathOK;
	}


	public boolean isXmlDirOK() {
		return xmlDirOK;
	}


	public void setXmlDirOK(boolean xmlDirOK) {
		this.xmlDirOK = xmlDirOK;
	} */


	public String getUuid() {
		if(uuid == null) {
			uuid = "";
		}
		//log.info("getUuid uuid = "+uuid);
		return uuid;
	}


	public void setUuid(String uuid) {
		
		this.uuid = uuid;
		//log.info("setUuid uuid = "+uuid);
	}

}
