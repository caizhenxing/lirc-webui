package org.xmlprocess.LircClient;

import org.xmlprocess.LircClient.common.CommonStatics;
import org.xmlprocess.LircClient.common.StaticRemotesCache;
import org.xmlprocess.LircClient.dto.RemotesDTO;
import org.xmlprocess.LircClient.ui.RemotesView;

import settingsHandler.SettingsHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class RemoteActivity extends Activity {

	private SettingsHandler sh = new SettingsHandler(this);
	
	static String settings = "Settings";
	static String big = "Size +";
	static String small = "Size -";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Log.w(getClass().getSimpleName(),
		// "LircClient RemoteActivity onCreate called");

		try {
			RemotesView rv = new RemotesView();
			String curUrl = sh.getCurrentSDO().getUrl();
			// Log.w(getClass().getSimpleName(),
			// "LircClient called curUrl= "+curUrl);
			RemotesDTO rd = null;
			try {
				StaticRemotesCache.setCurrentURL(curUrl);
				rd = StaticRemotesCache.getCurrentRemote();
				setContentView(rv.getRemotesView(rd, this));
			} catch (Exception ex) {
				if (rd == null) {
					Log.w(getClass().getSimpleName(), "NO Server FOUND= "
							+ curUrl);
					showDialog(1);
				}
			}
		} catch (Exception e) {
			Log.w("LircClient Ex", e);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// remove existing
		int ms = menu.size();
		// Log.w(getClass().getSimpleName(),
		// "LircClient onPrepareOptionsMenu menu.size() = "+ms);
		menu.removeGroup(0);
		menu.removeGroup(1);
		menu.removeGroup(2);
		menu.add(0, 0, 0, big);
		if(CommonStatics.SIZE > 0){
		menu.add(1, 1, 1, small);
	}
		menu.add(2, 2, 2, settings);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Log.w(getClass().getSimpleName(),
		// "LircClient onOptionsItemSelected item = "+item);
		if (item.getGroupId() == 0 && item.getItemId() == 0
				&& item.getTitle().equals(big)) {
			//Log.w(getClass().getSimpleName(),"LircClient onOptionsItemSelected makeBig chosen");
			makeBig();
		}
		if (item.getGroupId() == 1 && item.getItemId() == 1
				&& item.getTitle().equals(small)) {
			//Log.w(getClass().getSimpleName(),"LircClient onOptionsItemSelected makeSmall chosen");
			makeSmall();
		}
		if (item.getGroupId() == 2 && item.getItemId() == 2
				&& item.getTitle().equals(settings)) {
			//Log.w(getClass().getSimpleName(),"LircClient onOptionsItemSelected Settings chosen");
			openSettings();
		}

		return true;
	}

	public void openSettings() {
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getSettingsIntent());
		this.startActivity(i);
	}
	
	public void makeBig(){
		CommonStatics.SIZE++;
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getRemoteIntent());
		this.startActivity(i);
	}
	
	public void makeSmall(){
		if(CommonStatics.SIZE > 0){
			CommonStatics.SIZE--;
			Intent i = new Intent();
			i.setClassName("org.xmlprocess.LircClient",
					StaticRemotesCache.getRemoteIntent());
			this.startActivity(i);
		}
	}


}
