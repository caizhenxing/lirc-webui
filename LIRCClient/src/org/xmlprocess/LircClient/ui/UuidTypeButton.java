package org.xmlprocess.LircClient.ui;

import org.xmlprocess.LircClient.common.CommonStatics;
import org.xmlprocess.LircClient.common.RemoteHandler;
import org.xmlprocess.LircClient.common.StaticRemotesCache;
import org.xmlprocess.LircClient.dto.RemotesDTO;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UuidTypeButton extends Button {

	private String uuid;
	private String type;
	private String label;
	private RemotesDTO rdto;

	public UuidTypeButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public UuidTypeButton(Context context, RemotesDTO rdto1) {
		super(context);
		
		//this.setHeight(100);
		int factor = 5;
		int scale = CommonStatics.SIZE * factor;
		int vert = scale * 5;
		//int horiz = CommonStatics.SIZE;
		this.setPadding(scale, vert, scale, vert);
		
		/*this.getTextScaleX();
		this.getTextSize();*/
		
		//Log.w(getClass().getSimpleName(),"this.getTextSize() = "+this.getTextSize() +" this.getTextScaleX() = "+this.getTextScaleX() +" CommonStatics.SIZE = "+CommonStatics.SIZE);
		
		if(CommonStatics.SIZE > 0){
			//Log.w(getClass().getSimpleName(),"this.getTextSize()1 ="+this.getTextSize());
			int si = CommonStatics.SIZE * factor;
			float fs = this.getTextSize() + si;
			//this.setTextScaleX(si);
			this.setTextSize(fs);
			//Log.w(getClass().getSimpleName(),"this.getTextSize()2 ="+this.getTextSize());
		}
		
		//this.setT
		
		this.setLabel(rdto1.getLabel());
		this.setText(rdto1.getLabel());
		this.setUuid(rdto1.getUuid());
		this.setType(rdto1.getType());
		this.setRdto(rdto1);


		this.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Log.w(getClass().getSimpleName(),
				// "Button clicked= v = "+v.getId() +" v st "+v.toString());
				UuidTypeButton uub = (UuidTypeButton) v;
				// Log.w(getClass().getSimpleName(),
				// "Button clicked uub uuid ="+uub.getUuid()
				// +" uub type =  "+uub.getType());
				if (uub.getType().equalsIgnoreCase(CommonStatics.E_COMMAND)
						|| uub.getType().equals(CommonStatics.E_MACRO)) {
					sendCommand();
				} else {
					// Navigation button action
					StaticRemotesCache.setCurrentRemote(uub.getRdto());
					uub.setIntent(uub.getContext());
				}

			}
		});
	}

	public void setIntent(Context context) {
		Intent i = new Intent();
		i.setClassName("org.xmlprocess.LircClient",
				StaticRemotesCache.getRemoteIntent());
		context.startActivity(i);

	}

	public void sendCommand() {		
		//do in a thread
		/*RemoteHandler rh = new RemoteHandler();
		rh.getCommandAction(this.getUuid());*/
		new CommandTask().execute(this.getUuid());

	}
	
	private class CommandTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			RemoteHandler rh = new RemoteHandler();
			rh.getCommandAction(params[0]);
			return null;
		}


		
		
	}


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public RemotesDTO getRdto() {
		return rdto;
	}

	public void setRdto(RemotesDTO rdto) {
		this.rdto = rdto;
	}

}
