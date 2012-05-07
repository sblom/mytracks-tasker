package com.greenesse.mytracksplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FireReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
        if (com.twofortyfouram.locale.Intent.ACTION_FIRE_SETTING.equals(intent.getAction()))
        {
        	Bundle localeSettings = intent.getExtras().getBundle(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);
        	
        	Intent serviceRequest = new Intent();

        	serviceRequest.setClass(context.getApplicationContext(), BroadcastExecutiveService.class);
    		serviceRequest.setAction(localeSettings.getString("action"));
    		
    		context.startService(serviceRequest);
        }
	}

}
