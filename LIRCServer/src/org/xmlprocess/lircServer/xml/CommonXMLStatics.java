package org.xmlprocess.lircServer.xml;



public class CommonXMLStatics {
	
	/** The Default Encoding. Set to UTF-8. Possibly set in web.xml? */
	public static String DEFAULTENC = "UTF-8";
	
	/** Action parameter to set the XMLFilename */
	public static String ACTION_PARAM_XMLFILENAME = "action.xml.filename";

	/**
	 * The secret UUID attrib added to an XML doc in the absence of any other
	 * in order to guarantee a unique XPath
	 */
	public static String SECRETUUID = "uuid";
	//public static String SECRETUUID = "rumplestiltskin";
	/** Action parameter to set the Relative or http path */
	public static String ACTION_PARAM_NODELOCATE = "action.node.locator";
	

	
	//XPATH Statements
	
	//String rootXP = "//*[name()='"+BackingFileProps.INSTANCES +"']/@"+BackingFileProps.ROOT;
	/** Start Select Elem by name **/
	public static String XPATH_START_E_BY_NAME = "//*[name()='";
	/** End Select Elem by name **/
	public static String XPATH_END_SELECT = "']";
	/** Start Select All Attr by name **/
	public static String XPATH_START_ALL_ATT_BY_NAME = "//@*[name()='";
	/** Start Select an Attr by name **/
	public static String XPATH_START_ATT_BY_NAME = "/@*[name()='";
	
	/** Start Select by Attr val **/
	public static String XPATH_START_ATT_BY_VAL = "[@";
	/** Equals + singlequote **/
	public static String XPATH_EQUAL_SINGLEQUOT = "='";
	
	
	// Element Names
	
	
	
}
