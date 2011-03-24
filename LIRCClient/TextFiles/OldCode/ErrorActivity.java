package org.xmlprocess.LircClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ErrorActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.w(getClass().getSimpleName(), "LircClient RemoteActivity onCreate called");
        
        try{
        	TableLayout tl=new TableLayout(this);
    		tl.setShrinkAllColumns(true);
    		tl.setStretchAllColumns(true);
    		TableRow tr1=new TableRow(this);
    		Button settingsB = new Button(this);

        	
            setContentView(tl);
            }
            catch(Exception e){
            	Log.w("LircClient Ex", e); 	
            }
            //Log.w(getClass().getSimpleName(), "LircClient called for URL "); 
        
    }

}
