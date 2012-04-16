package com.greenesse.mytracksplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    	Intent resultIntent = new Intent();
    	resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_STRING_BLURB, "Test blurb");
    	resultIntent.putExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE, new Bundle());
    	setResult(RESULT_OK, resultIntent);
    	finish();
    }
    
    public void cancel(View view) {
    	setResult(RESULT_CANCELED);
    	finish();
    }
}
