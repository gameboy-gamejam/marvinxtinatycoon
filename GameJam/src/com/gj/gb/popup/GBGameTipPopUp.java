package com.gj.gb.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;

public class GBGameTipPopUp extends Activity{
	
	public static final String KEY_EXTRA_TIP = "tip";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_game_tip);
		
		String message = getIntent().getStringExtra(KEY_EXTRA_TIP);
		
		((TextView) findViewById(R.id.tip)).setText(message);
		
		findViewById(R.id.play).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}
		});
	}
}
