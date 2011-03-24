package org.xmlprocess.lircServer.io;

import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlprocess.lircServer.xml.XMLUtil;

public class BuildDocFromLirc {
	
	private static final Logger log = Logger.getLogger(BuildDocFromLirc.class
            .getName());
	
	
	public String XMLDir;
	public static String baseFN="XMLLirc";
	public static String xmlFE=".xml";
	public Document doc;
	
	/**
	 * Init - XML Dir
	 * lircPath etc
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public void init() {
		//long endTime = System.currentTimeMillis();
		doc = XMLUtil.getEmptyDocument();
		getRemotes();
		
	}
	public void getRemotes() {
		
		String[] remotes = {"Sky","DVD"};
		
		
		
	}
	
	public void getKeys(Element remote) {
		
	}
	
	

}
