package org.xmlprocess.LircClient.ui;

import settingsHandler.SettingsHandler;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SettingsButton extends Button {

	int type = 0;
	static String saveS = "Save";
	static String newS = "New";
	static String delS = "Delete";
	String sdID;
	SettingsHandler sh;
	
	// How to get set text with a button action?

	public SettingsButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SettingsButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public SettingsButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public SettingsButton(Context context, int type) {
		super(context);
		this.type = type;
		
		switch (type)
        {
        case 0 :
        {
        	this.setText(saveS);
        	break;
        }
        case 1 :
        {
        	this.setText(newS);
        	break;
        }
        case 2 :
        {
        	this.setText(delS);
        	break;
        }		
        }
		
		this.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Log.w(getClass().getSimpleName(), "Button clicked= v = "+v.getId() +" v st "+v.toString());
            	SettingsButton sb = (SettingsButton)v;
            	Log.w(getClass().getSimpleName(), "Button clicked= sb= "+sb.getText() +" sb type =  "+sb.getType());
        		switch (sb.getType())
                {
                case 0 :
                {
                	Log.w(getClass().getSimpleName(), "Button clicked should SAVE id "+sdID);
                	break;
                }
                case 1 :
                {
                	Log.w(getClass().getSimpleName(), "Button clicked should NEW id "+sdID);
                	break;
                }
                case 2 :
                {
                	Log.w(getClass().getSimpleName(), "Button clicked should DELETE id "+sdID);
                	break;
                }		
                }
            	
		
            }
		}); 
		
		

	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSdID() {
		return sdID;
	}

	public void setSdID(String sdID) {
		this.sdID = sdID;
	}

	public SettingsHandler getSh() {
		return sh;
	}

	public void setSh(SettingsHandler sh) {
		this.sh = sh;
	}

}
