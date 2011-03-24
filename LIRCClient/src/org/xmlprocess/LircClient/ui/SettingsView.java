package org.xmlprocess.LircClient.ui;

import org.xmlprocess.LircClient.common.StaticRemotesCache;

import settingsHandler.SettingsDTO;
import settingsHandler.SettingsHandler;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class SettingsView {

	SettingsDTO sd;
	boolean editB = false;
	// View setView =null;
	SettingsHandler sh;
	EditText urlEdit;

	ViewSwitcher switcher;
	Context context;

	public SettingsView(SettingsDTO sd, SettingsHandler sh, Context context) {
		super();
		this.sd = sd;
		this.sh = sh;
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public View getSettingView(SettingsHandler sh, Context context) {
		this.context = context;
		return getSwitcherView();

	}

	public View getSettingView(SettingsDTO sd, SettingsHandler sh,
			Context context) {
		this.context = context;
		this.sd = sd;
		return getSwitcherView();

	}

	public View getSettingView() {
		return getSwitcherView();
	}

	public View getSwitcherView() {

		if (switcher == null) {
			switcher = new ViewSwitcher(getContext());
			switcher.addView(getSetView(false));
			switcher.addView(getSetView(true));
			// switcher.
		}

		return switcher;
	}

	public View getSetView(boolean editable) {
		// Log.w(getClass().getSimpleName(),"getSetView called editB = "+editB);
		TableLayout tl = new TableLayout(getContext());
		tl.setShrinkAllColumns(true);
		// tl.setColumnShrinkable(1, true);
		tl.setStretchAllColumns(true);

		if (editable) {
			tl = getEditableView(tl);
		} else {
			tl = getStdView(tl);
		}

		ScrollView sv = new ScrollView(getContext());
		sv.setVerticalScrollBarEnabled(true);
		sv.setHorizontalScrollBarEnabled(true);

		sv.addView(tl);
		return sv;

	}

	public EditText getUrlEdit() {
		if (urlEdit == null) {
			urlEdit = new EditText(getContext());
			urlEdit.setTextSize(12);
			urlEdit.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		}
		return urlEdit;
	}

	public void setUrlEdit(EditText urlEdit) {
		this.urlEdit = urlEdit;
	}

	public TableLayout getEditableView(TableLayout tl) {

		// Log.w(getClass().getSimpleName(),"getEditableView called editB = "+editB);
		TableRow tr1 = new TableRow(getContext());

		// Add the default tickbox;
		TableLayout tickTL = new TableLayout(getContext());
		TableRow tickTR = new TableRow(getContext());
		// getUrlEdit();
		getUrlEdit().setText(sd.getUrl());
		tickTR.addView(getUrlEdit());

		tickTR.addView(getCB());
		tickTL.addView(tickTR);
		tr1.addView(tickTL);

		TableRow tr3 = new TableRow(getContext());
		TableLayout butTL = new TableLayout(getContext());
		butTL.setShrinkAllColumns(true);
		// tl.setColumnShrinkable(1, true);
		butTL.setStretchAllColumns(true);
		TableRow butTR = new TableRow(getContext());
		butTR.addView(getSaveButton());
		butTR.addView(getNewButton());
		butTR.addView(getCancelButton());

		int numsd = sh.getSettingsList().size();
		// Log.w(getClass().getSimpleName(),"numsd = "+numsd);
		if (numsd > 1) {
			butTR.addView(getDelButton());
		}

		butTL.addView(butTR);
		tr3.addView(butTL);
		tl.addView(tr3);

		tl.addView(tr1);

		return tl;

	}

	public TableLayout getStdView(TableLayout tl) {

		TableRow tr1 = new TableRow(getContext());
		TextView labelView = new TextView(getContext());
		labelView.setText(sd.getUrl());

		labelView.setClickable(true);
		labelView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				editB = true;
				switchView();
			}
		});
		tr1.addView(labelView);
		tr1.addView(getCB());
		tl.addView(tr1);

		return tl;

	}

	public SettingsDTO getSd() {
		return sd;
	}

	public void setSd(SettingsDTO sd) {
		this.sd = sd;
	}

	public CheckBox getCB() {
		CheckBox cb = new CheckBox(getContext());
		cb.setChecked(sd.isDefaultSD());
		cb.setClickable(!cb.isChecked());

		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				Log.w(getClass().getSimpleName(), "cbClicked cb state = "
						+ arg1);
				if (arg1) {
					if (editB) {
						sd.setDefaultSD(arg1);
						sh.setCurrentSDO(sd);
					} else {
						sd.setDefaultSD(arg1);
						sh.setCurrentSDO(sd);
						sh.addPref(sd);
						testView();
					}
				}
			}
		});
		return cb;
	}

	public void switchView() {
		switcher.showNext();
	}

	public Button getSaveButton() {
		Button saveB = new Button(getContext());
		saveB.setText("Save");
		saveB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String ue_s = getUrlEdit().getText().toString();
				sd.setUrl(ue_s);
				sh.addPref(sd);
				editB = false;
				testView();
			}
		});
		return saveB;
	}

	public Button getCancelButton() {
		Button cancelB = new Button(getContext());
		cancelB.setText("Cancel");
		cancelB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				editB = false;
				switchView();
			}
		});

		return cancelB;
	}

	public Button getNewButton() {
		Button newB = new Button(getContext());
		newB.setText("New");
		newB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				SettingsDTO sdNew = new SettingsDTO();
				sdNew.getId();
				sdNew.setUrl("");
				sh.addPref(sdNew);
				editB = false;
				refreshView();
			}
		});
		return newB;
	}

	public Button getDelButton() {
		Button delB = new Button(getContext());
		delB.setText("Delete");
		delB.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				editB = false;
				sh.remPref(sd.getId());
				editB = false;
				refreshView();
			}
		});
		return delB;
	}

	public void refreshView() {
		Log.w(getClass().getSimpleName(), "refreshView called");
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getSettingsIntent());
		this.getContext().startActivity(i);
	}

	public void testView() {
		Log.w(getClass().getSimpleName(), "testView called");
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getTestIntent());
		this.getContext().startActivity(i);
	}

}
