package com.greenesse.mytracksplugin;

import com.google.android.apps.mytracks.services.ITrackRecordingService;

import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class BroadcastExecutiveService extends IntentService {
	public BroadcastExecutiveService(String name) {
		super(name);
	}
	
	public BroadcastExecutiveService() {
		// TODO: WTF should this be?
		super("BroadcaseExecutiveService");
	}

	private ITrackRecordingService mytracksService;
	private Intent myTracksServiceIntent;

	private Intent pending;

	private ServiceConnection connection = new ServiceConnection() {		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i("MyTracksPlugin", "Service disconnected.");
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i("MyTracksPlugin", "Service connected.");
			synchronized (this) {
				mytracksService = ITrackRecordingService.Stub.asInterface(service);
				if (pending != null) {
					onHandleIntent(pending);
					pending = null;
				}
			}		
		}
	};
	
	@Override
	public void onCreate() {
		super.onCreate();

    	myTracksServiceIntent = new Intent();
    	myTracksServiceIntent.setComponent(new ComponentName(
    			getString(R.string.mytracks_service_package),
                getString(R.string.mytracks_service_class)));

    	startService(myTracksServiceIntent);
        if (!bindService(myTracksServiceIntent, connection, 0)) {
        	Log.e("MyTracksPlugin", "Couldn't bind to service.");
        }
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		synchronized (this) {
			if (mytracksService == null) {
				pending = intent;
				return;
			}
		}
		if ("start".equals(intent.getAction())) {
			start();
		}
		else if ("stop".equals(intent.getAction())) {
			stop();
		}
	}
	
	@Override
	public void onDestroy() {
		unbindService(connection);
		super.onDestroy();
	}
	
    public void start() {
    	try {
	    	if (!mytracksService.isRecording()) {
	    		mytracksService.startNewTrack();
	    	}
    	} catch (RemoteException e) {
    		Log.e("MyTracksPlugin", "RemoteException: " + e.getMessage());
    	}
    }
    
    public void stop() {
    	try {
	    	if (mytracksService.isRecording()) {
	    		mytracksService.endCurrentTrack();
	    	}
    	} catch (RemoteException e) {
    		Log.e("MyTracksPlugin", "RemoteException: " + e.getMessage());
    	}
    }
}
