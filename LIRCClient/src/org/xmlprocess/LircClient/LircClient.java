package org.xmlprocess.LircClient;

import org.xmlprocess.LircClient.common.StaticRemotesCache;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/*
 * dialog uses a relative layout
 * then within that a table layout
 */

// TO Tabs
// TODO Look up how to use long button push for open in new tab & popups e.g "hold this".
public class LircClient extends Activity {

	// TODO shove the root (Key URL) into the object cache & the most recent
	// (Key Static String)
	// TODO If no URL then settings
	// TODO See how settings work

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Log.w(getClass().getSimpleName(), "LircClient onCreate called StaticRemotesCache.getTestIntent()= "+StaticRemotesCache.getTestIntent());
		// Just a launcher for the std activity
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getTestIntent());
		this.startActivity(i);

	}

}