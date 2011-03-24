package org.xmlprocess.LircClient;

import org.xmlprocess.LircClient.common.StaticRemotesCache;
import org.xmlprocess.LircClient.dto.RemotesDTO;

import settingsHandler.SettingsHandler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TestConnActivity extends Activity {

	private SettingsHandler sh = new SettingsHandler(this);
	String curUrl;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.w(getClass().getSimpleName(),"LircClient TestConnActivity onCreate called");

		try {
			curUrl = sh.getCurrentSDO().getUrl();
			Log.w(getClass().getSimpleName(), "LircClient called curUrl= "+curUrl);
			RemotesDTO rd = null;
			try {
				rd = StaticRemotesCache.setCurrentURL(curUrl);
				if (rd == null) {
					// display error page
					setContentView(getErrorView());
				} else {
					// go to RemoteActivity
					openRemote();
				}
			} catch (Exception ex) {
				if (rd == null) {
					Log.w(getClass().getSimpleName(), "NO Server FOUND= "
							+ curUrl);
					// showDialog(1);
				}
			}
		} catch (Exception e) {
			Log.w("LircClient Ex", e);
		}
		// Log.w(getClass().getSimpleName(), "LircClient called for URL ");

	}

	private View getErrorView() {

		TableLayout tl = new TableLayout(this);
		tl.setShrinkAllColumns(true);
		tl.setStretchAllColumns(true);

		TableRow tr1 = new TableRow(this);
		TextView labelView = new TextView(this);
		labelView.setText("The Server at :\n" + curUrl
				+ "\nhas taken too long to respond.");
		labelView.setBackgroundColor(Color.WHITE);
		labelView.setTextColor(Color.RED);
		labelView.setTextSize(18);
		tr1.addView(labelView);
		tl.addView(tr1);

		TableRow trSettings = new TableRow(this);
		trSettings.addView(getSettingsButton());
		tl.addView(trSettings);

		TableRow trCancel = new TableRow(this);
		trCancel.addView(getCancelButton());
		tl.addView(trCancel);

		ScrollView sv = new ScrollView(this);
		sv.setVerticalScrollBarEnabled(true);
		sv.setHorizontalScrollBarEnabled(true);
		sv.addView(tl);
		return sv;
	}

	public void openRemote() {
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getRemoteIntent());
		this.startActivity(i);
	}

	public void openSettings() {
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getSettingsIntent());
		this.startActivity(i);
	}

	public Button getCancelButton() {
		Button cancelB = new Button(this);
		cancelB.setText("Cancel");
		cancelB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				Log.w(getClass().getSimpleName(), "getCancelButton clicked");

			}
		});
		return cancelB;
	}

	public Button getSettingsButton() {
		Button settingsB = new Button(this);
		settingsB.setText("Open Settings");
		settingsB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				// Log.w(getClass().getSimpleName(),"getCancelButton clicked");
				openSettings();
			}
		});
		return settingsB;
	}

}
