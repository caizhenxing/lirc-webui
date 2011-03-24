package org.xmlprocess.LircClient;

import java.util.Iterator;

import org.xmlprocess.LircClient.common.StaticRemotesCache;
import org.xmlprocess.LircClient.dto.RemotesDTO;
import org.xmlprocess.LircClient.ui.RemotesView;
import org.xmlprocess.LircClient.ui.SettingsPreference;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.KeyEvent;

import org.xmlprocess.LircClient.R;

import settingsHandler.SettingsDTO;
import settingsHandler.SettingsHandler;

public class SettingsActivity extends PreferenceActivity  {
	
	/*     public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        } else return super.onKeyDown(keyCode, event);
    } 
	 * 
	 * 
	 */
	
	//Editor editor;
	//SharedPreferences settings;
	SettingsHandler sh;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Log.w(getClass().getSimpleName(), "LircClient SettingsActivity onCreate called");
        sh = new SettingsHandler(this);
        sh.getSettings();
       
        setPreferenceScreen(createPreferenceHierarchy());
       

    }
    
    
    private PreferenceScreen createPreferenceHierarchy() {

    	PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
    	
    	PreferenceCategory dialogBasedPrefCat = new PreferenceCategory(this);
        dialogBasedPrefCat.setTitle("LIRC Settings");
        root.addPreference(dialogBasedPrefCat);

        /*Custom Dialog box
         * Set:
         * Label, URL, current tick box . Just tick tick box vs click in text fields to edit them
         * Remove old if label changes?
         */
        // Edit text preference        

        	for (final Iterator<SettingsDTO> it1 = sh.getSettingsList().iterator(); it1
    		.hasNext();) {
        		SettingsDTO sd = it1.next();
        		//Log.w(getClass().getSimpleName(),"create prefs sd = "+sd.id);
        		/*EditTextPreference editTextPref = new EditTextPreference(this);
                editTextPref.setDialogTitle("Add Edit LIRC Server");
                
                editTextPref.setTitle(sd.getLabel());
                editTextPref.setSummary(sd.getUrl());
                editTextPref.setKey(sd.getLabel());
                editTextPref.setText(sd.getUrl());*/
        		SettingsPreference sp = new SettingsPreference(this,sh);
        		sp.setSd(sd);
                dialogBasedPrefCat.addPreference(sp);
        	}
        	
        
        /*EditTextPreference editTextPref = new EditTextPreference(this);
        editTextPref.setDialogTitle("editTextPref.setDialogTitle BOB");
        editTextPref.setKey("edittext_preference");
        editTextPref.setTitle("editTextPref.setTitle BOB");
        editTextPref.setSummary("editTextPref.setSummary BOB");
        dialogBasedPrefCat.addPreference(editTextPref);*/

        // List preference
        /*ListPreference listPref = new ListPreference(this);
       // listPref.setEntries(R.array.entries_list_preference);
       // listPref.setEntryValues(R.array.entryvalues_list_preference);
        listPref.setDialogTitle("listPref.setDialogTitle BOB");
        listPref.setKey("list_preference");
        listPref.setTitle("listPref.setTitle BOB");
        listPref.setSummary("listPref.setSummary BOB");
        dialogBasedPrefCat.addPreference(listPref);*/

        // Launch preferences
        //PreferenceCategory launchPrefCat = new PreferenceCategory(this);
        //launchPrefCat.setTitle(R.string.launch_preferences);
       // root.addPreference(launchPrefCat);

    	
    	
    	return root;

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // Do Stuff
        	Log.w(getClass().getSimpleName(), "LircClient onKeyDown Menu key pressed "); 

        	Intent i = new Intent();
            i.setClassName("org.xmlprocess.LircClient", StaticRemotesCache.getRemoteIntent());
            this.startActivity(i);
        	
        	return true;
        } else {
            return false;
        } 
    }
}
