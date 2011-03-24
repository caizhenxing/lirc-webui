package org.xmlprocess.lircServer.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.xmlprocess.lircServer.common.CommonStatics;
import org.xmlprocess.lircServer.common.ObjectCache;
import org.xmlprocess.lircServer.dto.ConfigDTO;
import org.xmlprocess.lircServer.io.GetFileURL;

public class ConfigController {

	private static final Logger log = Logger.getLogger(ConfigController.class
			.getName());

	GetFileURL gfu = new GetFileURL();
	
	//ConfigDTO conDTO;
	
	
	/*Properties props;

	public String lircDir;
	public String irSend;
	public String irSendPath;
	public String xmlDir;
	public String xmlFile;
	public String xmlPath;
	public Hashtable<String, ArrayList<String>> errsHT = new Hashtable<String, ArrayList<String>>();

	public boolean[] testIRpath;
	public boolean[] testXMLpath;
	public boolean[] testXMLDir;*/

	public static void main(String[] args) {
		// Test
		ConfigController cc = new ConfigController();
		String propsFN = "D:/Data/ATestXML/test/Lirc/test.props";
		try {
			cc.setupProps(propsFN);
			//cc.debugvals();
			// log.info("Check Props ="+cc.checkProps());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public void setupProps(String propsFN) throws Exception {
		//Properties props = new Properties();

		boolean[] test = gfu.testfile(propsFN);
		if (!test[0]) {
			// Props doesn't exist so create
			//props = new Properties();
			getConDTO().addReqProps(propsFN);
		} else {
			if (!test[1]) {
				String err = "Can't read " + propsFN;
				log.severe(err);
				throw new Exception(err);
			}
			if (!test[2]) {
				String err = "Can't write " + propsFN;
				log.severe(err);
				throw new Exception(err);
			}
			if (test[3]) {
				String err = propsFN + " Is a directory please set to a file";
				log.severe(err);
				throw new Exception(err);
			}

			File propsFile = new File(propsFN);
			FileInputStream fis = new FileInputStream(propsFile);
			getConDTO().getProps().load(fis);
			//props.load(fis);
			fis.close();
			getConDTO().addReqProps(propsFN);
		}

		checkProps();
		setConDTO(getConDTO());

	}

	public void checkProps() {
		testFiles();
		/*if (getErrsHT().size() > 0) {
			for (final Enumeration<String> e = getErrsHT().keys(); e
					.hasMoreElements();) {
				final String key = e.nextElement();
				ArrayList<String> errArr = getErrsHT().get(key);
				log.info("Errors found in Key : " + key);
				for (final Iterator<String> it1 = errArr.iterator(); it1
						.hasNext();) {
					log.info(it1.next());
				}
			}
		}*/

	}

	/*	public void addReqProps(String propsFN) throws Exception {
		// First add the absolute path to the props file
		File propsFile = new File(propsFN);
		String propsAbsFN = propsFile.getAbsolutePath();
		log.info("propsAbsFN = " + propsAbsFN);
		getConDTO().getProps().setProperty(CommonStatics.CONFIG_PROPS, propsAbsFN);
		getConDTO().getIrSendPath();
		getConDTO().getXmlPath();

		getConDTO().writeProps();
	}

	public String getProp(String key, String defval) {

		if (getConDTO().getProps().getProperty(key) == null
				|| getConDTO().getProps().getProperty(key).length() == 0) {
			getConDTO().getProps().setProperty(key, defval);
		}
		return getConDTO().getProps().getProperty(key);
	}

	public void writeProps() throws Exception {
		//ObjectCache.put(CommonStatics.CONFIG_PROPS, props);
		String fn = getConDTO().getProps().getProperty(CommonStatics.CONFIG_PROPS);
		getConDTO().getProps().store(new FileOutputStream(fn), null);
	} */

	public boolean testFiles() {

		boolean isErr = false;
		testIRpath();
		testXMLpath();
		testXMLDir();

		return isErr;
	}

/*	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
*/
/*	public String getLircDir() {
		lircDir = winSafeURL(getProp(CommonStatics.LIRC_DIR, ""));
		props.setProperty(CommonStatics.LIRC_DIR, this.lircDir);
		return lircDir;
	}

	public void setLircDir(String lircDir) {
		this.lircDir = winSafeURL(lircDir);
		props.setProperty(CommonStatics.LIRC_DIR, this.lircDir);
		getIrSendPath();
	}

	public String getIrSend() {
		irSend = getProp(CommonStatics.IRSEND, CommonStatics.IRSEND);
		return irSend;
	}

	public void setIrSend(String irSend) {
		this.irSend = irSend;
		props.setProperty(CommonStatics.IRSEND, this.irSend);
		getIrSendPath();
	}

	public String getIrSendPath() {
		String irp = "/" + getIrSend();
		if (getLircDir().length() > 0) {
			setIrSendPath(lircDir + irp);
		} else
			setIrSendPath("");
		return irSendPath;
	}

	public void setIrSendPath(String irSendPath) {
		this.irSendPath = winSafeURL(irSendPath);
		props.setProperty(CommonStatics.IRSEND_PATH, this.irSendPath);
	}

	public String getXmlDir() {
		xmlDir = winSafeURL(getProp(CommonStatics.XML_DIR, ""));
		props.setProperty(CommonStatics.XML_DIR, this.xmlDir);
		//log.info(CommonStatics.XML_DIR + " = " + xmlDir);
		
		return xmlDir;
	}

	public void setXmlDir(String xmlDir) {
		
		this.xmlDir = winSafeURL(xmlDir);
		props.setProperty(CommonStatics.XML_DIR, this.xmlDir);
		getXmlPath();
	}

	public String getXmlFile() {

		xmlFile = getProp(CommonStatics.XML_FILE, "");
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
		props.setProperty(CommonStatics.XML_FILE, this.xmlFile);
		getXmlPath();
	}

	public String getXmlPath() {

		if (getXmlDir().length() > 0 && getXmlFile().length() > 0) {
			String xmlp = "/" + getXmlFile();
			setXmlPath(xmlDir + xmlp);
		} else
			setXmlPath("");
		return xmlPath;
	}
*/
	public boolean[] testIRpath() {
		boolean[] test = new boolean[5];
		
		test[4] = false;
		ArrayList<String> errs = new ArrayList<String>();
		if (getConDTO().irSendPath != null && getConDTO().irSendPath.length() > 0) {
			test = gfu.testfile(getConDTO().irSendPath);
			if (!test[0]) {
				errs.add("File doesn't exist");
			}
			else {
			if (!test[1]) {
				errs.add("Can't read File ");
			}
			// if(!test[2]) {
			// errs.add("Can't write "_;
			// }
			if (test[3]) {
				errs.add("Is a directory please set to a file");
			}
			}
			/*if (errs.size() > 0) {
				errs.add("irSendPath = " + irSendPath);
			}*/

		} else {
			errs.add("irSendPath not set");
		}

		if (errs.size() > 0) {

			String key = CommonStatics.IRSEND_PATH;
			getConDTO().getErrsHT().put(key, errs);
		}
		// File OK
		if (errs.size() == 0) {
			test[4] = true;
		}
		getConDTO().setTestIRpath(test);
		return test;
	}

	public boolean[] testXMLpath() {
		boolean[] test = new boolean[5];
		// Test[5] used to see if OK
		test[4] = false;
		ArrayList<String> errs = new ArrayList<String>();
		if (getConDTO().xmlPath != null && getConDTO().xmlPath.length() > 0) {
			test = gfu.testfile(getConDTO().xmlPath);
			if (!test[0]) {
				errs.add("File doesn't exist");
			}
			else {
			if (!test[1]) {
				errs.add("Can't read File");
			}
			if (!test[2]) {
				errs.add("Can't write File");
			}
			if (test[3]) {
				errs.add("Is a directory please set to a file");
			}
			}
			/*if (errs.size() > 0) {
				errs.add("xmlPath = " + xmlPath);
			}*/
		} else {
			errs.add("xmlPath not set");
		}

		if (errs.size() > 0) {
			String key = CommonStatics.XML_PATH;
			getConDTO().getErrsHT().put(key, errs);
			testXMLDir();
		}
		if (errs.size() == 0) {
			test[4] = true;
		}
		getConDTO().setTestXMLpath(test);
		return test;
	}
	
	public boolean[] testXMLDir() {
		boolean[] test = new boolean[5];
		// Test[5] used to see if OK
		test[4] = false;
		ArrayList<String> errs = new ArrayList<String>();
		if (getConDTO().xmlDir != null && getConDTO().xmlDir.length() > 0) {
			test = gfu.testfile(getConDTO().xmlDir);
			if (!test[0]) {
				errs.add("File doesn't exist");
			}
			else {
			if (!test[1]) {
				errs.add("Can't read File");
			}
			if (!test[2]) {
				errs.add("Can't write File");
			}
			if (!test[3]) {
				errs.add("Is not a directory");
			}
			}
			/*if (errs.size() > 0) {
				errs.add("xmlDir = " + xmlDir);
			}*/
		} else {
			errs.add("xmlDir not set");
		}

		if (errs.size() > 0) {
			String key = CommonStatics.XML_DIR;
			getConDTO().getErrsHT().put(key, errs);
		}
		if (errs.size() == 0) {
			test[4] = true;
		}
		
		getConDTO().setTestXMLDir(test);
		return test;
	}

/*	public void setXmlPath(String xmlPath) {
		this.xmlPath = winSafeURL(xmlPath);
		props.setProperty(CommonStatics.XML_PATH, this.xmlPath);
	}

	public Hashtable<String, ArrayList<String>> getErrsHT() {
		return errsHT;
	}

	public void setErrsHT(Hashtable<String, ArrayList<String>> errsHT) {
		this.errsHT = errsHT;
	}

	public boolean[] getTestIRpath() {
		return testIRpath;
	}

	public void setTestIRpath(boolean[] testIRpath) {
		this.testIRpath = testIRpath;
	}

	public boolean[] getTestXMLpath() {
		return testXMLpath;
	}

	public void setTestXMLpath(boolean[] testXMLpath) {
		this.testXMLpath = testXMLpath;
	}

	public boolean[] getTestXMLDir() {
		return testXMLDir;
	}

	public void setTestXMLDir(boolean[] testXMLDir) {
		this.testXMLDir = testXMLDir;
	}
	
	public static String winSafeURL(String filepath) {
		//System.out.println("WinSafeURL filepath = " + filepath);
		String WinSafePath = "";
		if(filepath != null){
		WinSafePath = filepath.replace('\\', '/');
	}
		return WinSafePath;
	} */

	public ConfigDTO getConDTO() {	
		if(ObjectCache.get(CommonStatics.CONF_KEY) == null) {
			//CONF_KEY
			setConDTO(new ConfigDTO());
		}
		return (ConfigDTO)ObjectCache.get(CommonStatics.CONF_KEY);
	}

	public void setConDTO(ConfigDTO conDTO) {
		ObjectCache.put(CommonStatics.CONF_KEY, conDTO);
		//this.conDTO = conDTO;
	}

	
	
}
