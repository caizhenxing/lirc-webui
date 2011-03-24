package org.xmlprocess.LircClient;

import java.util.Iterator;

import org.xmlprocess.LircClient.common.StaticRemotesCache;
import org.xmlprocess.LircClient.ui.SettingsView;

import settingsHandler.SettingsDTO;
import settingsHandler.SettingsHandler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SettingsActivityTV extends Activity {

	SettingsHandler sh;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Log.w(getClass().getSimpleName(),
		// "LircClient SettingsActivityTV onCreate called");
		sh = new SettingsHandler(this);
		sh.getSettings();
		setContentView(getSettingsView());
	}

	public View getSettingsView() {

		TableLayout tl = new TableLayout(this);
		tl.setShrinkAllColumns(true);
		tl.setStretchAllColumns(true);

		TableRow trSetButt = new TableRow(this);
		trSetButt.addView(getTestButton());
		tl.addView(trSetButt);

		for (final Iterator<SettingsDTO> it1 = sh.getSettingsList().iterator(); it1
				.hasNext();) {
			SettingsDTO sd = it1.next();
			TableRow trSettings = new TableRow(this);
			SettingsView setv = new SettingsView(sd, sh, this);
			trSettings.addView(setv.getSettingView());
			tl.addView(trSettings);

		}

		ScrollView sv = new ScrollView(this);
		sv.setVerticalScrollBarEnabled(true);
		sv.setHorizontalScrollBarEnabled(true);

		sv.addView(tl);

		return sv;

	}

	public Button getTestButton() {
		Button settingsB = new Button(this);
		settingsB.setText("Reload");
		settingsB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				openTest();
			}
		});
		return settingsB;
	}

	public void openTest() {
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getTestIntent());
		this.startActivity(i);
	}

}
