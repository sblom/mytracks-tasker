package com.greenesse.mytracksplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class EditActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        
        Intent intent = getIntent();
    }
    
    @Override
    public void finish() {
    	super.finish();
    }
    
    public void save(View view) {
		String action = "";
		switch (view.getId())
		{
		case R.id.startButton:
			action = "start";
			break;
		case R.id.stopButton:
			action = "stop";
			break;
		default:
			Log.wtf("button handler", "Invalid button handled!");
			break;
		}
    	
		Bundle extra = new Bundle();
		extra.putString("action", action);

		Intent resultIntent = new Intent();
		resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, "start".equals(action) ? getString(R.string.start_recording_blurb) : getString(R.string.stop_recording_blurb));
		resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE, extra);
    	setResult(RESULT_OK, resultIntent);

    	finish();
    }
    
    public void cancel(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
}
