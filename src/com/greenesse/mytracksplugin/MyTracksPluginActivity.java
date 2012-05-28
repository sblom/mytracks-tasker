package com.greenesse.mytracksplugin;

import com.google.android.apps.mytracks.services.ITrackRecordingService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class MyTracksPluginActivity extends Activity {
	private ITrackRecordingService mytracksService;
	private Intent myTracksServiceIntent;
	
	private ServiceConnection connection = new ServiceConnection() {		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i("MyTracksPlugin", "Service disconnected.");
			startAndBindMyTracksService();
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i("MyTracksPlugin", "Service connected.");
			synchronized (this) {
				mytracksService = ITrackRecordingService.Stub.asInterface(service);
			}		
		}
	};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	myTracksServiceIntent = new Intent();
    	myTracksServiceIntent.setComponent(new ComponentName(
    			getString(R.string.mytracks_service_package),
                getString(R.string.mytracks_service_class)));
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
		startAndBindMyTracksService();
    }

	private void startAndBindMyTracksService() {
		startService(myTracksServiceIntent);
        if (!bindService(myTracksServiceIntent, connection, 0)) {
        	Log.e("MyTracksPlugin", "Couldn't bind to service.");
        }
	}

    @Override
    protected void onStop() {
		unbindService(connection);
		super.onStop();
    }
    
    public void start(View view) {
    	try {
	    	if (!mytracksService.isRecording()) {
	    		mytracksService.startNewTrack();
	    	}
    	} catch (RemoteException e) {
    		Log.e("MyTracksPlugin", "RemoteException: " + e.getMessage());
    	}
    }
    
    public void stop(View view) {
    	try {
	    	if (mytracksService.isRecording()) {
	    		mytracksService.endCurrentTrack();
	    	}
    	} catch (RemoteException e) {
    		Log.e("MyTracksPlugin", "RemoteException: " + e.getMessage());
    	}
    }
}