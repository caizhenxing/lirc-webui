package org.xmlprocess.LircClient.common;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlprocess.LircClient.dto.RemotesDTO;
import org.xmlprocess.LircClient.http.HttpHandler;
import org.xmlprocess.LircClient.xml.XMLHandler;

import android.util.Log;

public class RemoteHandler {

	private String url_S;

	private HttpHandler httpH = new HttpHandler();
	private XMLHandler xmlH = new XMLHandler();

	public RemotesDTO getRemotesXML() throws Exception {
		// Get the connection
		Log.w(getClass().getSimpleName(), "RemotesDTO getRemotesXML url = "+getXMLUrl_S());
		InputStream is = null;
		getUrl_S();
		RemotesDTO retRem = null;
		try {
			if (url_S != null && url_S.length() > 0) {
				Log.w(getClass().getSimpleName(), "RemotesDTO getRemotesXML url_S = "+url_S);
				is = httpH.retrieveStream(getXMLUrl_S());
			}
			if (is != null) {
				Document doc = xmlH.parse(is);
				Element root = doc.getDocumentElement();
				retRem = new RemotesDTO(root);
			}

		} catch (Exception e) {
			Log.w(getClass().getSimpleName(), "Error in getRemotesXML url = "
					+ url_S, e);
			// throw e;
		}
		return retRem;
	}

	public void getCommandAction(String uuid) {

		String urlS = getMainUrl_S(uuid);
		// Log.w(getClass().getSimpleName(),
		// "getCommandAction called for uuid urlS = " + urlS);
		String s = httpH.retrieve(urlS);
		// Log.w(getClass().getSimpleName(),
		// "getCommandAction called for uuid = " + uuid +" retval = "+s);
	}

	public String getXMLUrl_S() {

		StringBuilder sb = new StringBuilder();
		sb.append(getUrl_S());
		sb.append(CommonStatics.DEFAULT_SUFF_XML);
		return sb.toString();

	}

	public String getMainUrl_S() {
		StringBuilder sb = new StringBuilder();
		sb.append(getUrl_S());
		sb.append(CommonStatics.DEFAULT_SUFF_MAIN);
		return sb.toString();

	}

	// http://localhost:8080/LircServer/main.jsp?uuid=355ff637-fd08-41db-98ca-424ecdd412fc
	public String getMainUrl_S(String uuid) {
		StringBuilder sb = new StringBuilder();
		sb.append(getUrl_S());
		sb.append(CommonStatics.DEFAULT_SUFF_MAIN);
		sb.append("?uuid=");
		sb.append(uuid);
		return sb.toString();

	}

	public String getUrl_S() {
		if (url_S == null) {
			// Log.w(getClass().getSimpleName(), "rh getUrl_S currentURL= " +
			// StaticRemotesCache.currentURL);
			url_S = StaticRemotesCache.currentURL;
		}
		return url_S;
	}

	public void setUrl_S(String url_S) {
		this.url_S = url_S;
	}

}
