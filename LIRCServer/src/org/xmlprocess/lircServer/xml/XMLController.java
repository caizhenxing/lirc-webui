package org.xmlprocess.lircServer.xml;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlprocess.lircServer.common.CommonStatics;
import org.xmlprocess.lircServer.common.ObjectCache;
import org.xmlprocess.lircServer.dto.CommandContainerDTO;
import org.xmlprocess.lircServer.dto.ConfigDTO;
import org.xmlprocess.lircServer.dto.XmlDTO;
import org.xmlprocess.lircServer.io.LocalExec;

public class XMLController {
	
	private static final Logger log = Logger.getLogger(XMLController.class.getName());
	
	/*boolean irOK;
	public String currentRemote = "";
	public String webAppRootPath;
	public String settingsXSLTPath;
	public String mainXSLTPath;
	public String currentURL;*/
	
	ArrayList<String> lines = new ArrayList<String>();

	LocalExec le = new LocalExec();
	
	
	public void init(){
		/*if(isIrOK()){
			log.info("irOK = "+isIrOK());
		if(getConDTO().getTestXMLDir()[4]){	
		}	
		}*/
		
	}
	
	public Document createNewXML() throws Exception{
		
		Document doc = XMLUtil.getEmptyDocument();
		Element Root  = doc.createElement(CommonStatics.E_REMOTES);
		//getUUIDElem(Root);

		doc.appendChild(Root);
		
		getXmlDTO().setCurrentRemote("");
		le.setCommands(getIRCmd_List());
		ArrayList<String> remotes = le.runCMDs();
		for (final Iterator<String> it1 = remotes.iterator(); it1.hasNext();) {
			String remString = it1.next();
			String remote = split(remString,1);
			
			Element rem = doc.createElement(CommonStatics.E_REMOTE);
			rem.setAttribute(CommonStatics.A_NAME, remote);
			//getUUIDElem(rem);
			Element group = doc.createElement(CommonStatics.E_GROUP);
			group.setAttribute(CommonStatics.A_NAME, "default");
			group.setAttribute(CommonStatics.A_COLS, "4");
			//getUUIDElem(group);
			rem.appendChild(group);
			
			log.info("remote = "+remote);
			getXmlDTO().setCurrentRemote(remote);
			le.setCommands(getIRCmd_List());
			ArrayList<String> keys = le.runCMDs();
			for (final Iterator<String> it2 = keys.iterator(); it2.hasNext();) {
				String keyS = split(it2.next(),2);
				log.info("keyS = "+keyS);
				Element keyE = doc.createElement(CommonStatics.E_COMMAND);
				keyE.setAttribute(CommonStatics.A_NAME, keyS);
				keyE.setAttribute(CommonStatics.A_LABEL, keyS);
				//getUUIDElem(keyE);
				group.appendChild(keyE);				
			}
		Root.appendChild(rem);
		}
		log.info("Doc = \n"+XMLUtil.writeXMLToString(doc));
		//getConDTO().setXmlFile(getXmlFN());
			//setXmlPath(getConDTO().getXmlDir()+"/"+getXmlFN());
		doc = addUuidAttrDoc(doc, CommonStatics.A_UUID,false);
			writeDoc(doc);

	return doc;	
			
	}
	

	
/*	public Element getUUIDElem(Element elem){
		String uuidVal = elem.getAttribute(CommonStatics.A_UUID);
		if(uuidVal == null || uuidVal.length() == 0){
			UUID uuid = UUID.randomUUID();
			elem.setAttribute(CommonStatics.A_UUID, uuid.toString());
		}
		
		return elem;
	}
*/	
	public static Document addUuidAttrDoc(Document Doc, String Attname, boolean reset) {

		try {
			// System.out.println("addUuidAttrDoc(Document Doc, String Attname )
			// called");
			Element root = Doc.getDocumentElement();
			// System.out.println("addUuidAttrDoc Root = "+root.getNodeName());
			root = addUuidAttrElems(root, Attname,reset);
			// System.out.println("addUuidAttrDoc Root2 = "+root.getNodeName());

			// System.out.println("Document post adding uuid =
			// "+writeXMLToString(Doc));
		} catch (Exception E) {
			log.severe("Exception in addUuidAttrDoc(Document Doc, String Attname ) Ex = "+ E);
		}
		return Doc;
	}
	
	
	public static Element addUuidAttrElems(Element elem, String Attname, boolean reset) {
		// System.out.println("addUuidAttrElems(Element elem, String Attname
		// called ");
		elem = addUuidAttrElem(elem, Attname,reset);
		// System.out.println("elem = addUuidAttrElem(elem, Attname );");
		NodeList pl = elem.getChildNodes();
		int index = pl.getLength();
		// System.out.println("addUuidAttrElems index = "+index);
		int i = 0;
		while (i < index) {
			// System.out.println("addUuidAttrElems i = "+i);
			Node DomNode = pl.item(i);
			if ((DomNode.getNodeType()) == org.w3c.dom.Node.ELEMENT_NODE) {
				Element el = (Element) DomNode;
				el = addUuidAttrElems(el, Attname,reset);

			}
			i++;

		}
		return elem;
	}

	public static Element addUuidAttrElem(Element elem, String Attname, boolean reset) {
		// System.out.println("addUuidAttrElem(Element elem, String Attname )
		// called");
		// If att exists
		if(reset){
			elem.setAttribute(Attname, "");
		}
		if (elem.getAttribute(Attname).equals("")) {
			// Get a UUID & add it to the element as an id attrib
			elem.setAttribute(Attname, UUID.randomUUID().toString());
		}
		// if the attrib doesn't exist
		if (elem.getAttribute(Attname) == null) {
			// Get a UUID & add it to the element as an id attrib
			elem.setAttribute(Attname, UUID.randomUUID().toString());
		}
		return elem;
	}
	
	
	
	public String split(String line, int pos){
		String rval = line.split(" ")[pos];
		return rval.trim();
	}

/*	public String getIrsendPath() {
		return irsendPath;
	}

	public void setIrsendPath(String irsendPath) {
		this.irsendPath = irsendPath;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String getXmlDir() {
		return xmlDir;
	}

	public void setXmlDir(String xmlDir) {
		this.xmlDir = xmlDir;
	} */

	public boolean isIrOK() {
		
		/*getXmlDTO().setIrOK(false);
		String irsp = getConDTO().getIrSendPath();
		if(irsp != null && irsp.length() > 0){
			getXmlDTO().setCurrentRemote("");
			//currentRemote ="";
			le.setCommands(getListCmd());
			lines = le.runCMDs();
			le.printlines(lines);
			//test IR eg check string contain irsend?
			log.info("isIrOK lines size = "+lines.size());
			if(lines.size() > 0) {
				getXmlDTO().setIrOK(true);
			}	
		}
		return getXmlDTO().isIrOK();*/
		
		return true;
	}

	/*public void setIrOK(boolean irOK) {
		this.irOK = irOK;
	}*/
	
	private ArrayList<String> runCmd(String[] cmds){
		//ArrayList<String> resultsAr = new ArrayList<String>();
		le.logCmds(cmds);
		le.setCommands(cmds);
		ArrayList<String> resultsAr = le.runCMDs();
		le.printlines(resultsAr);
		
		return resultsAr;
	}

	public LocalExec getLe() {
		return le;
	}

	public void setLe(LocalExec le) {
		this.le = le;
	}

	public String[] getIRCmd_List() {
		//return new String[]{getConDTO().getIrSendPath(),CommonStatics.IRCMD_LIST,getXmlDTO().getCurrentRemote(),""};
		return getExecCmd("",CommonStatics.IRCMD_LIST);
	}
	
	public String[] getIRCmd_SendOnce(String name) {
		//irsend("SEND_ONCE",$remote,$_GET['key']);
		return getExecCmd(name,CommonStatics.IRCMD_SEND_ONCE);	
	}
	
	public String[] getIRCmd_SendStart(String name) {
		//irsend("SEND_ONCE",$remote,$_GET['key']);
		return getExecCmd(name,CommonStatics.IRCMD_SEND_START);	
	}
	public String[] getIRCmd_SendStop(String name) {
		//irsend("SEND_ONCE",$remote,$_GET['key']);
		return getExecCmd(name,CommonStatics.IRCMD_SEND_STOP);	
	}
	public String[] getExecCmd(String name, String cmd) {
		String currRem = getXmlDTO().getCurrentRemote();
		
		//String quote ="\"";
		
		//log.info("currRem = "+currRem);
		//irsend("SEND_ONCE",$remote,$_GET['key']);
		return new String[]{getConDTO().getIrSendPath(),cmd,currRem,name};
	}



	public Document getDoc() throws Exception{
		
		//log.info("getDoc() called");
		Document doc = (Document)ObjectCache.get(CommonStatics.DOC_KEY);
		if(doc == null) {
			//log.info("getDoc isXmlPathOK"+getConDTO().testXMLpath[4]);
			if(getConDTO().testXMLpath[4]){
				//load the XML
				doc = loadDoc();
			}
			else{
				//create new XML
				//log.info("create new XML");
				doc = createNewXML();
				ObjectCache.put(CommonStatics.DOC_KEY, doc);
				
			}
			
		}
		
		
		return doc;
	}

	public void setDoc(Document doc) throws Exception{
		ObjectCache.put(CommonStatics.DOC_KEY, doc);
		writeDoc(doc);
	}

	public ArrayList<String> getLines() {
		return lines;
	}

	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
	
	public String getHTML(String uuid) {
		String html = "Render xml using XSLT";
		//log.info("getHTML uuid = "+uuid);
		
		Properties props = new Properties();
		props.put(CommonStatics.XSLT_CURRENT_URL,getXmlDTO().getCurrentURL());
		if(uuid != null && uuid.length() > 0) {
			//log.info("getHTML adding uuid = "+uuid);
			props.put(CommonStatics.A_UUID,uuid);
		}
		html = XMLUtil.processXslt(getXML(), getXmlDTO().getFullPath(getXmlDTO().getMainXSLTPath()),props);
		
		return html;
	}
	
	public String getXML(){
		String xml = "";
		try {
			xml = XMLUtil.writeXMLToString(getDoc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
	
	public Document loadDoc() throws Exception {
		//log.info("loadDoc xmlPath = "+getConDTO().getXmlPath());
		Document doc = XMLUtil.getDocument(getConDTO().getXmlPath());
		
		int origL = XMLUtil.writeXMLToString(doc).length();
		
		doc = addUuidAttrDoc(doc, CommonStatics.A_UUID,false);
		int newL = XMLUtil.writeXMLToString(doc).length();
		if(origL != newL) {
		//log.info("Lengths not the same writing new doc");	
		writeDoc(doc);
		}
		return doc;
	}
	
	public void writeDoc(Document doc) throws Exception {
		//log.info("writeDoc xmlPath = "+getConDTO().getXmlPath());
			XMLUtil.writeXMLToFile(doc, getConDTO().getXmlPath());
		
	}
	
	public Document getSettingsXML() throws Exception {
		
		ConfigDTO conDTO = getConDTO();
		
		Document errsD = XMLUtil.getEmptyDocument();
		Element Root  = errsD.createElement(CommonStatics.E_SETTINGS);
		errsD.appendChild(Root);
		
		//IRPath
		Element e_irp = errsD.createElement(CommonStatics.IRSEND_PATH);
		e_irp.setAttribute(CommonStatics.A_IS_OK, Boolean.toString(conDTO.getTestIRpath()[4]));
		e_irp.setAttribute(CommonStatics.LIRC_DIR, conDTO.getLircDir());
		e_irp.setAttribute(CommonStatics.IRSEND, conDTO.getIrSend());
		e_irp.setAttribute(CommonStatics.IRSEND_PATH, conDTO.getIrSendPath());
		
		if(!conDTO.getTestIRpath()[4]){
			addErrTxt(errsD, e_irp,CommonStatics.IRSEND_PATH,conDTO);
		}
		
		
		
		Root.appendChild(e_irp);
		//XML DIR
		Element e_xmld = errsD.createElement(CommonStatics.XML_DIR);
		e_xmld.setAttribute(CommonStatics.A_IS_OK, Boolean.toString(conDTO.getTestXMLDir()[4]));
		e_xmld.setAttribute(CommonStatics.XML_DIR, conDTO.getXmlDir());
		if(!conDTO.getTestXMLDir()[4]){
			addErrTxt(errsD, e_xmld,CommonStatics.XML_DIR,conDTO);
		}
		
		
		Root.appendChild(e_xmld);
		//XMLPath
		Element e_xmlp = errsD.createElement(CommonStatics.XML_PATH);
		e_xmlp.setAttribute(CommonStatics.A_IS_OK, Boolean.toString(conDTO.getTestXMLpath()[4]));
		e_xmlp.setAttribute(CommonStatics.XML_FILE, conDTO.getXmlFile());
		e_xmlp.setAttribute(CommonStatics.XML_PATH, conDTO.getXmlPath());
		if(!conDTO.getTestXMLpath()[4]){
			addErrTxt(errsD, e_xmlp,CommonStatics.XML_PATH,conDTO);
		}

		Root.appendChild(e_xmlp);
		
		return errsD;
	}
	
	public void addErrTxt(Document d, Element parent, String Key,ConfigDTO conDTO) {
		Element e_irp_Errs = d.createElement(CommonStatics.E_ERRORS);
		parent.appendChild(e_irp_Errs);
		//String key = CommonStatics.IRSEND_PATH;
		ArrayList<String> errArr = conDTO.getErrsHT().get(Key);
		//log.info("errArr "+errArr.size());
		for (final Iterator<String> it1 = errArr.iterator(); it1
		.hasNext();) {
			Element e_irp_Err = d.createElement(CommonStatics.E_ERROR);
			e_irp_Errs.appendChild(e_irp_Err);	
			e_irp_Err.setAttribute(CommonStatics.A_ERR_TXT, it1.next());	
		}
		
	}
	
	public String  getSettingsXML_S() {
		
		String xml = "Should be XML";
		
		try {
			xml = XMLUtil.writeXMLToString(getSettingsXML());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return xml;
	}
	
	public String  getSettingsHTML_S() {
		String html = "Should be rendered into HTML";
		Properties props = new Properties();
		props.put(CommonStatics.XSLT_CURRENT_URL,getXmlDTO().getCurrentURL());
		html = XMLUtil.processXslt(getSettingsXML_S(), getXmlDTO().getFullPath(getXmlDTO().getSettingsXSLTPath()),props);
		return html;
	}
	
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
	
	public XmlDTO getXmlDTO() {
		if(ObjectCache.get(CommonStatics.XD_KEY) == null) {
			//CONF_KEY
			setXmlDTO(new XmlDTO());
		}
		return (XmlDTO)ObjectCache.get(CommonStatics.XD_KEY);
	}
	
	public void setXmlDTO(XmlDTO xmld) {
		ObjectCache.put(CommonStatics.XD_KEY, xmld);
	}
	
	public String getXMLAction(String uuid) {
		//String retVal = "Some text";
		log.info("XMLCC getXMLAction uuid = "+uuid);
			return evalUUID(uuid);
		
	}
	
	public String runCCD(String uuid){
		StringBuilder sb = new StringBuilder();
		if(GetCMDs_HT().containsKey(uuid)){
			ArrayList<CommandContainerDTO> ccdArr=GetCMDs_HT().get(uuid);
			for ( Iterator<CommandContainerDTO> ccdIt = ccdArr.iterator(); ccdIt.hasNext(); ) {
			//.iterator()
			sb.append(runCCDTO(ccdIt.next()));
			
		}
			
		}
		return sb.toString();
		
	}
	
	public String evalUUID(String uuid) {
		
		if(GetCMDs_HT().containsKey(uuid)){
			return runCCD(uuid);
		}
		
		else{
		ArrayList<CommandContainerDTO> ccdAr= new ArrayList<CommandContainerDTO>();	
		String nodeName ="";
		Element el = null;
		try {
			el = (Element)XMLUtil.getNodeByLocator(uuid,getDoc());
			nodeName = el.getLocalName();
			/*log.info("nodeName= "+nodeName);
			log.info(XMLUtil.convertToStringLeaveCDATA(el));*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String name = el.getAttribute(CommonStatics.A_NAME);
		//log.info("EvalUUID name = "+name);
		
		if(nodeName.equals(CommonStatics.E_COMMAND)) {
			//log.info("EvalUUID proc CMD ");
			setRemoteFromNode(el);
			CommandContainerDTO ccd = processCommand(el);
			ccdAr.add(ccd);
		}
		
		if(nodeName.equals(CommonStatics.E_MACRO)) {
			//log.info("EvalUUID proc MACRO ");
			setRemoteFromNode(el);
			NodeList INodes = el.getChildNodes();
			for (int y = 0; y < INodes.getLength(); y++) {
				Node node = INodes.item(y);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element childE = (Element) node;
					if(childE.getLocalName().equals(CommonStatics.E_COMMAND)) {
						CommandContainerDTO ccd = processCommand(childE);
						ccdAr.add(ccd);
					}
				}
			}
		}
		if(ccdAr.size() > 0){
		GetCMDs_HT().put(uuid, ccdAr);
		}
		return runCCD(uuid);
		}
		
	}
	
	public CommandContainerDTO processCommand(Element cmdEl) {
		
		CommandContainerDTO ccd = new CommandContainerDTO();
		String name = cmdEl.getAttribute(CommonStatics.A_NAME);
		String num = cmdEl.getAttribute(CommonStatics.A_NUM);
		String cmd = cmdEl.getAttribute(CommonStatics.A_CMD);
		/*log.info("processCommand name ="+name);
		log.info("processCommand num ="+num);
		log.info("processCommand cmd ="+cmd);*/
		
		if(cmd != null && cmd.length() > 0) {
		if(!cmd.equalsIgnoreCase(CommonStatics.IRCMD_SEND_START) && !cmd.equalsIgnoreCase(CommonStatics.IRCMD_SEND_STOP)) {
			log.severe("A Command must be either: \n"+CommonStatics.IRCMD_SEND_START +"\n or \n"+CommonStatics.IRCMD_SEND_STOP +" Currently = \n"+cmd);
			log.severe("Setting to default (SEND_ONCE)");
			cmd ="";
		}
		
		if(cmd.equalsIgnoreCase(CommonStatics.IRCMD_SEND_START)) {
			// SEND CMD
			log.info("Sending cmd "+CommonStatics.IRCMD_SEND_START);
			String[] cmds = getIRCmd_SendStart(name);
			ccd.setCmds(cmds);
			return ccd;
		}
		if(cmd.equalsIgnoreCase(CommonStatics.IRCMD_SEND_STOP)) {
			// SEND CMD
			log.info("Sending cmd "+CommonStatics.IRCMD_SEND_STOP);
			String[] cmds = getIRCmd_SendStop(name);
			ccd.setCmds(cmds);
			return ccd;
		}
		
		}
		
		if(num != null && num.length() > 0) {
			//Test to see if it's a number
			try {
			int i = Integer.parseInt(num);
			String[] cmds = getIRCmd_SendOnce(name);
			ccd.setCmds(cmds);
			ccd.setNumTimes(i);
			return ccd;

			}catch(NumberFormatException nfe) {
				log.severe("num is Not A Number currently = "+num);
				num = "";
			}
		}
		
		//else do the std 
		log.info("No num nor CMD so doing the std Sending cmd "+CommonStatics.IRCMD_SEND_ONCE);
		String[] cmds = getIRCmd_SendOnce(name);
		ccd.setCmds(cmds);
		//runCmd(cmds);
		
		
		return ccd;
	}
	

	public void printStringArray (String[] arr) {
		for (int y = 0; y < arr.length; y++) {
			log.info("printStringArray y = "+y +" val = "+arr[y]);	
		}
	}
	
	private void setRemoteFromNode(Node n) {
		String currRemote = "";
		Node Parent = null;
		if (n != null) {
			while (n.getParentNode() != null) {
				Parent = n.getParentNode();
				if(Parent.getLocalName().equalsIgnoreCase(CommonStatics.E_REMOTE)) {
				Element remoteEl = (Element)Parent;
				
				String name = remoteEl.getAttribute(CommonStatics.A_NAME);
				
				getXmlDTO().setCurrentRemote(name);
				
				String uuid = remoteEl.getAttribute(CommonStatics.A_UUID);
				
				log.info("Remote found name = "+name + " UUID = "+uuid);
				return;
				}
				n = Parent;
			}
		}
		
	}
	
	
	public Hashtable<String,ArrayList<CommandContainerDTO>> GetCMDs_HT(){
		//Hashtable ht = null;
		//String is UUID
		
		if(ObjectCache.get(CommonStatics.CMDS_HT_KEY) != null){
			Hashtable<String,ArrayList<CommandContainerDTO>> ht = (Hashtable<String,ArrayList<CommandContainerDTO>>)ObjectCache.get(CommonStatics.CMDS_HT_KEY);
			return ht;
		}
		else{
			Hashtable<String,ArrayList<CommandContainerDTO>> ht = new Hashtable<String,ArrayList<CommandContainerDTO>>();
			ObjectCache.put(CommonStatics.CMDS_HT_KEY, ht);
			return ht;
			
		}
	}
	
	public void resetAllUUID() throws Exception{

		ObjectCache.remove(CommonStatics.CMDS_HT_KEY);
		setDoc(addUuidAttrDoc(getDoc(), CommonStatics.A_UUID,true));
	}
	
	public String runCCDTO(CommandContainerDTO ccd){
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < ccd.getNumTimes(); y++) {
		// SEND CMD
		//log.info("Sending cmd "+CommonStatics.IRCMD_SEND_ONCE +" "+i +" many times currently sending "+y);	
		//printStringArray(ccd.getCmds());	
		
		sb.append(runCmd(ccd.getCmds()));
	}
		return sb.toString();
	}

}
