package settingsHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.xmlprocess.LircClient.common.CommonStatics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SettingsHandler {

	Activity act;
	SharedPreferences settings;

	public SettingsHandler(Activity act) {
		super();
		this.act = act;
	}

	public SettingsHandler() {
		super();
	}

	public SettingsDTO getCurrentSDO() {

		SettingsDTO currSet = new SettingsDTO();
		if (getSettings().contains(CommonStatics.SETTINGS_CURRENT)) {
			String keyCurr = getSettings().getString(
					CommonStatics.SETTINGS_CURRENT,
					CommonStatics.DEFAULT_URL_NAME);
			currSet = getPref(keyCurr);
		} else {
			Log.w(getClass().getSimpleName(), "NO CURRENT Settings!!!");
		}

		currSet.setDefaultSD(true);

		return currSet;
	}

	public void setCurrentSDO(SettingsDTO sdc) {
		addPref(CommonStatics.SETTINGS_CURRENT, sdc.getId());
	}

	public ArrayList<SettingsDTO> getSettingsList() {

		String defUrl = getCurrentSDO().getUrl();
		ArrayList<SettingsDTO> sdAr = new ArrayList<SettingsDTO>();
		Map<String, ?> settings = getSettings().getAll();
		for (final Iterator<String> it1 = settings.keySet().iterator(); it1
				.hasNext();) {
			final String id = it1.next();
			if (!id.equals(CommonStatics.SETTINGS_CURRENT)) {
				String val = "";
				if (settings.get(id) != null) {
					val = settings.get(id).toString();
				}
				SettingsDTO sd = new SettingsDTO();
				sd.setId(id);
				sd.setUrl(val);
				if (val.equals(defUrl)) {
					sd.setDefaultSD(true);
				}
				sdAr.add(sd);
			}
		}

		return sdAr;
	}

	public SettingsDTO getPref(String key) {
		SettingsDTO sd = new SettingsDTO();
		String valCurr = getSettings().getString(key, "");
		sd.setUrl(valCurr);
		return sd;
	}

	public void addPref(SettingsDTO sd) {
		addPref(sd.getId(), sd.getUrl());
		getSettingsList();
	}

	public void addPref(String id, String value) {
		Editor e = settings.edit();
		e.putString(id, value);
		e.commit();
		reloadSettings();
	}

	public void reloadSettings() {
		settings = null;
		getSettings();
	}

	public void remPref(String id) {
		Editor e = settings.edit();
		e.remove(id);
		e.commit();
		reloadSettings();
	}

	public SharedPreferences getSettings() {
		if (settings == null) {
			settings = act.getSharedPreferences(CommonStatics.SETTINGS_NAME,
					Context.MODE_PRIVATE);
		}

		if (settings.getAll().size() == 0
				|| !settings.contains(CommonStatics.SETTINGS_CURRENT)) {
			Editor e = settings.edit();
			SettingsDTO sd = new SettingsDTO();
			e.putString(sd.getId(), CommonStatics.DEFAULT_URLS);
			e.putString(CommonStatics.SETTINGS_CURRENT, sd.getId());
			e.commit();
		}

		return settings;
	}

	public void logPrefs() {
		if (settings != null) {
			Map<String, ?> settingsAll = settings.getAll();
			Log.w(getClass().getSimpleName(), "logPrefs Num settingsAll = "
					+ settingsAll.size());
			for (final Iterator<String> it1 = settingsAll.keySet().iterator(); it1
					.hasNext();) {
				final String id = it1.next();
				String val = settingsAll.get(id).toString();
				Log.w(getClass().getSimpleName(), "Key = " + id + " Value = "
						+ val);
			}
		}
	}

}
