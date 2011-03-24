package org.xmlprocess.LircClient.ui;

import org.xmlprocess.LircClient.common.StaticRemotesCache;

import settingsHandler.SettingsDTO;
import settingsHandler.SettingsHandler;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
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

public class SettingsPreference extends Preference {
	

	
	SettingsDTO sd;
	boolean editB = false;
	//View setView =null;
	SettingsHandler sh ;
	EditText urlEdit;
	
	ViewSwitcher switcher;

	public SettingsPreference(Context context) {
		super(context);
	}
	
	public SettingsPreference(Context context,SettingsHandler sh) {
		super(context);
		this.sh = sh;
	}

	public SettingsPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SettingsPreference(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		//How to refresh an existing view?;
		return getSwitcherView();
		
		/* Create a list of select single item
		 * Button New
		 * URL [x]
		 * 
		 * When change [x] save
		 * When click on text change to editable when click outside 
		 * set back to text and save
		 * 
		 */
		

	}
	
	public View getSwitcherView(){
		
		if(switcher == null){
		switcher = new ViewSwitcher(getContext());
		switcher.addView(getSetView(false));
		switcher.addView(getSetView(true));
		//switcher.
		}
		
		return switcher;
	}
	
	public View getSetView(boolean editable){
		//Log.w(getClass().getSimpleName(),"getSetView called editB = "+editB);
		TableLayout tl=new TableLayout(getContext());
		tl.setShrinkAllColumns(true);
		//tl.setColumnShrinkable(1, true);
		tl.setStretchAllColumns(true);
		
		if(editable){
			tl = getEditableView(tl);
		}
		else{
			tl = getStdView(tl);
		}
		
		ScrollView sv = new ScrollView(getContext());
		sv.setVerticalScrollBarEnabled(true);
		sv.setHorizontalScrollBarEnabled(true);
		
		sv.addView(tl);
		
		//setView = sv;
		
        return sv;		
		
	}
	
	
	public EditText getUrlEdit() {
		if(urlEdit == null){
			urlEdit = new EditText(getContext());
			urlEdit.setTextSize(12);
			urlEdit.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
		}
		return urlEdit;
	}

	public void setUrlEdit(EditText urlEdit) {
		this.urlEdit = urlEdit;
	}
	
	public TableLayout getEditableView(TableLayout tl){

		//Log.w(getClass().getSimpleName(),"getEditableView called editB = "+editB);
		TableRow tr1=new TableRow(getContext());
		
		//Add the default tickbox;
		TableLayout tickTL=new TableLayout(getContext());
		TableRow tickTR=new TableRow(getContext());
		//getUrlEdit();
		getUrlEdit().setText(sd.getUrl());
		tickTR.addView(getUrlEdit());

		
		tickTR.addView(getCB());
		tickTL.addView(tickTR);
		tr1.addView(tickTL);
		
		TableRow tr3=new TableRow(getContext());
		TableLayout butTL=new TableLayout(getContext());
		butTL.setShrinkAllColumns(true);
		//tl.setColumnShrinkable(1, true);
		butTL.setStretchAllColumns(true);
		TableRow butTR=new TableRow(getContext());
		butTR.addView(getSaveButton());
		butTR.addView(getNewButton());
		butTR.addView(getCancelButton());

		int numsd = sh.getSettingsList().size();
		//Log.w(getClass().getSimpleName(),"numsd = "+numsd);
		if(numsd > 1){
		butTR.addView(getDelButton());
		}
		
		butTL.addView(butTR);
		tr3.addView(butTL);
		tl.addView(tr3);
		
		
		tl.addView(tr1);

        return tl;		
		
	}
	
	public TableLayout getStdView(TableLayout tl){

		TableRow tr1=new TableRow(getContext());
		
		//Add the default tickbox;
		//TableLayout tickTL=new TableLayout(getContext());
		//TableRow tickTR=new TableRow(getContext());
		TextView labelView = new TextView(getContext());
		labelView.setText(sd.getUrl());
		
		labelView.setClickable(true);
		labelView.setOnClickListener(new OnClickListener()	{
		    public void onClick(View v) {
		        // Perform action on clicks
		    	//Log.w(getClass().getSimpleName(),"labelViewClicked ");
		    	editB = true;
		    	//switcher.showNext();
		    	switchView();
		    }
		});
		tr1.addView(labelView);
		tr1.addView(getCB());
		//tickTL.addView(tickTR);
		//tr1.addView(tickTL);
		tl.addView(tr1);

        return tl;	
		
	}

	public SettingsDTO getSd() {
		return sd;
	}

	public void setSd(SettingsDTO sd) {
		this.sd = sd;
	}
	
	public CheckBox getCB(){
		CheckBox cb = new CheckBox(getContext());
		cb.setChecked(sd.isDefaultSD());
		cb.setClickable(!cb.isChecked());
		
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener()	{
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					Log.w(getClass().getSimpleName(),"cbClicked cb state = "+arg1);
					if(arg1){
						if(editB){
							sd.setDefaultSD(arg1);
							sh.setCurrentSDO(sd);
						}
						else{
							sd.setDefaultSD(arg1);
							sh.setCurrentSDO(sd);
							sh.addPref(sd);
							refreshView();
							
						}
					}
				}
			});
		return cb;
	}
	
	public void switchView(){
		//Log.w(getClass().getSimpleName(),"switchView called editB = "+editB);
		//check edit wrt next previous
		switcher.showNext();
	}
	
	public Button getSaveButton(){
		Button saveB = new Button(getContext());
		saveB.setText("Save");
		saveB.setOnClickListener(new OnClickListener()	{
		    public void onClick(View v) {
		        // Perform action on clicks
		    	//Log.w(getClass().getSimpleName(),"getSaveButton clicked");
		    	///editB = false;
		    	//switcher.showNext();
		    	//switchView();
		    	
		    	String ue_s = getUrlEdit().getText().toString();
		    	//Log.w(getClass().getSimpleName(),"getSaveButton ue_s = "+ue_s);
		    	sd.setUrl(ue_s);
		    	//Log.w(getClass().getSimpleName(),"getSaveButton sd id = = "+sd.id);
		    	sh.addPref(sd);
		    	
		    	editB = false;
		    	refreshView();
		    	//switchView();
		    
		    }
		});
		return saveB;
	}
	
	public Button getCancelButton(){
		Button cancelB = new Button(getContext());
		cancelB.setText("Cancel");
		cancelB.setOnClickListener(new OnClickListener()	{
		    public void onClick(View v) {
		        // Perform action on clicks
		    	//Log.w(getClass().getSimpleName(),"getCancelButton clicked");
		    	editB = false;
		    	//switcher.showNext();
		    	switchView();
		    	

		    }
		});
	
		return cancelB;
	}

	public Button getNewButton(){
		Button newB = new Button(getContext());
		newB.setText("New");
		newB.setOnClickListener(new OnClickListener()	{
		    public void onClick(View v) {
		        // Perform action on clicks
		    	Log.w(getClass().getSimpleName(),"getNewButton clicked");
		    	///editB = false;
		    	//switcher.showNext();
		    	//switchView();
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
	
	public Button getDelButton(){
		Button delB = new Button(getContext());
		delB.setText("Delete");
		delB.setOnClickListener(new OnClickListener()	{
		    public void onClick(View v) {
		        // Perform action on clicks
		    	//Log.w(getClass().getSimpleName(),"getDelButton clicked");
		    	editB = false;
		    	sh.remPref(sd.getId());
		    	editB = false;
		    	refreshView();
		    	

		    }
		});
		return delB;
	}
	
	public void refreshView(){
		Intent i = new Intent();
	    i.setClassName("org.xmlprocess.LircClient", StaticRemotesCache.getSettingsIntent());
	    
	    this.getContext().startActivity(i);
	    
	}

}
