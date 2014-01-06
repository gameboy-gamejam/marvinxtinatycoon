package com.gj.gb.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;

public class GBAcknowledgementPopup extends Activity{
	
	public static final String KEY_EXTRA_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_ack);
		
		String message = getIntent().getStringExtra(KEY_EXTRA_MESSAGE);
		((TextView) findViewById(R.id.textMessage)).setText(message);
		
		findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}
