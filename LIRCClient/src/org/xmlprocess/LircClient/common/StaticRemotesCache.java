package org.xmlprocess.LircClient.common;

import org.xmlprocess.LircClient.dto.RemotesDTO;

import android.util.Log;

public class StaticRemotesCache {

	public static RemotesDTO currentRemote = null;
	public static String currentURL;
	// static String defaultURLS = "http://10.0.2.2:8080/LircServer/xml";
	static String remoteIntent = "org.xmlprocess.LircClient.RemoteActivity";
	static String settingsIntent = "org.xmlprocess.LircClient.SettingsActivityTV";
	static String testIntent = "org.xmlprocess.LircClient.TestConnActivity";

	public static String setupRemotes(String URL) throws Exception {
		// If empty all OK else message wrt what went wrong
		String retval = "";
		try {
			RemoteHandler rh = new RemoteHandler();
			rh.setUrl_S(URL);
			RemotesDTO rd = rh.getRemotesXML();
			if (rd == null) {
				throw new Exception("Could not get the remote");
			}
			ObjectCache.put(URL, rd);
		} catch (Exception ex) {
			retval = "StaticRemotesCache Error in setRemoteRoot url = " + URL
					+ " " + ex.getMessage();
			Log.w(retval, ex);
			throw ex;
		}
		return retval;
	}

	public static RemotesDTO getRemoteRoot() throws Exception {
		String URL = getCurrentURL();
		// If no URL set via settings try the default one.
		if (URL == null || URL.length() == 0) {
			setCurrentURL(CommonStatics.DEFAULT_URLS);
			// URL = CommonStatics.DEFAULT_URLS;
		}
		return getRemoteRoot(getCurrentURL());
	}

	public static RemotesDTO getRemoteRoot(String URL) throws Exception {

		RemotesDTO root = null;
		if (ObjectCache.get(URL) == null) {
			String msg = setupRemotes(URL);
			// Log.w("getRemoteRoot URL = "+URL +" msg = "+msg);
		}

		root = (RemotesDTO) ObjectCache.get(URL);
		return root;
	}

	public static RemotesDTO getCurrentRemote() throws Exception {
		if (currentRemote == null) {
			currentRemote = getRemoteRoot();
		}
		return currentRemote;
	}

	public static void setCurrentRemote(RemotesDTO currentRemote1) {

		// Log.w("setCurrentRemote ",
		// "setCurrentRemote called currentRemote1 type = "+currentRemote1.getType()
		// +" label = "+currentRemote1.getLabel());
		StaticRemotesCache.currentRemote = currentRemote1;
	}

	public static String getCurrentURL() {
		if (currentURL == null) {
			currentURL = "";
		}
		return currentURL;
	}

	public static RemotesDTO setCurrentURL(String currentURL) throws Exception {

		RemotesDTO rd = null;
		try {
			StaticRemotesCache.currentURL = currentURL;
			// make sure that the Remote is set.
			rd = getRemoteRoot();
		} catch (Exception e) {
			Log.w("StaticRemotesCache.setCurrentURL",
					"Error in getRemotesXML currentURL = " + currentURL, e);
			// throw e;
		}
		return rd;

	}

	public static String getRemoteIntent() {
		return remoteIntent;
	}

	public static String getSettingsIntent() {
		return settingsIntent;
	}

	public static String getTestIntent() {
		return testIntent;
	}

}
