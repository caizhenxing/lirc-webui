package org.xmlprocess.LircClient.common;

public class CommonStatics {
	
	/** The name used to locate the props file*/
	public static String CONFIG_PROPS = "mainProps";
	/** The name used to locate the props file*/
	public static String DEF_CONFIG_XML = "config.xml";
	
	/** The name used to locate the settings XSLT File*/
	public static String SETTINGS_XSLT = "settingsXSLT";
	/** The name used to locate the settings XSLT File*/
	public static String MAIN_XSLT = "mainXSLT";
	
	public static String xml = ".xml";
	
	public static String DOC_KEY = "DOC_XML";
	public static String CONF_KEY = "CONFIG_DTO";
	public static String XD_KEY = "XML_DTO";
	
	public static String CMDS_HT_KEY = "CMDS_HT";
	
	
	/** The directory where the lirc executables are*/
	public static String LIRC_DIR = "lircDir";
	
	/** The Name of the IRSend exec*/
	public static String IRSEND = "irsend";
	
	/** The Name of the IRSend path*/
	public static String IRSEND_PATH = "irsendPath";
	
	/** The Name of the IRSend path*/
	public static String XML_DIR = "xmlDir";
	
	/** The Name of the IRSend path*/
	public static String XML_FILE = "xmlFile";
	/** The Name of the IRSend path*/
	public static String XML_PATH = "xmlPath";
	/** Create XML*/
	public static String CREATE_XML = "createXML";
	/** Reset XML*/
	public static String RESET_XML = "resetXML";
	
	
	
	//XML Element names
	/** XML Element names remotes */
	public static String E_REMOTES = "remotes";
	/** XML Element names remote */
	public static String E_REMOTE = "remote";
	/** XML Element names group */
	public static String E_GROUP = "group";
	/** XML Element names command */
	public static String E_COMMAND= "command";
	/** XML Element names macro */
	public static String E_MACRO = "macro";
	
	//XML Element names
	/** The Name of the Errors path*/
	public static String E_SETTINGS = "settings";	
	/** The Name of the Errors path*/
	public static String E_ERRORS = "errors";
	/** The Name of the Errors path*/
	public static String E_ERRORS_TEXT = "errorsText";
	/** The Name of the Errors path*/
	public static String E_ERROR = "error";
	
	//Attributes
	/** The Name Attribute*/
	public static String A_NAME = "name";
	/** The Label Attribute*/
	public static String A_LABEL = "label";
	/** The Name of the IRSend path*/
	public static String A_COLS = "cols";
	/** The Name of the IRSend path*/
	public static String A_ORDER = "order";
	/** The Name of the IRSend path*/
	public static String A_UUID = "uuid";
	/** Number of times event should be called*/
	public static String A_NUM = "num";
	/** If on a command then can be SEND_START or SEND_STOP.*/
	public static String A_CMD = "cmd";
	
	/** The Name of the IRSend path*/
	public static String A_IS_OK = "isOK";
	
	/** The Name of the IRSend path*/
	public static String A_ERR_TXT = "errorText";
	
	/** The Name of the IRSend path*/
	public static String TRUE = "true";
	/** The Name of the IRSend path*/
	public static String FALSE = "false";

	
	/** IRSEND CMDS **/
	/**SEND ONCE*/
	public static String IRCMD_SEND_ONCE = "SEND_ONCE";
	/**SEND ONCE*/
	public static String IRCMD_SEND_START = "SEND_START";
	/**SEND ONCE*/
	public static String IRCMD_SEND_STOP = "SEND_STOP";
	/**SEND ONCE*/
	public static String IRCMD_LIST = "LIST";
	
	
	/**Attrib values **/
	/**BOOKMARK*/
	public static String A_VAL_BOOKMARK = "bookmark";
	
	/** XSLT Params **/
	
	public static String XSLT_CURRENT_URL = "current.url";
	
	/** Settings Name **/
	public static String SETTINGS_NAME = "LircClient";
	/** Settings Name **/
	public static String SETTINGS_CURRENT = "cur_set";
	/** Default URL **/
	public static String DEFAULT_URLS = "http://10.0.2.2:8080/LircServer";
	/** Default URL **/
	public static String DEFAULT_URL_NAME ="DefaultURL";
	
	/** Default XML suffix **/
	public static String DEFAULT_SUFF_XML ="/xml";
	/** Default main suffix  **/
	public static String DEFAULT_SUFF_MAIN ="/xmlhttp.jsp";
	
	public static int SIZE = 0;
	
	
}
