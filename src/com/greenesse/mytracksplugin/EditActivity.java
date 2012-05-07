package com.greenesse.mytracksplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    	RadioButton startRecording = (RadioButton)findViewById(R.id.startRecording);
    	String action = startRecording.isChecked() ? "start" : "stop";
    	
    	Bundle extra = new Bundle();
    	extra.putString("action", action);

    	Intent resultIntent = new Intent();
    	resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, startRecording.isChecked() ? "Start recording" : "Stop recording");
    	resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE, extra);
    	setResult(RESULT_OK, resultIntent);

    	finish();
    }
    
    public void cancel(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
}
