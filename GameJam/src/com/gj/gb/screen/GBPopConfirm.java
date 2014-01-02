package com.gj.gb.screen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;

public class GBPopConfirm extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_confirm);
		
		String message = getIntent().getStringExtra("message");
		boolean oneButton = getIntent().getBooleanExtra("one_button", false);
		
		String btnText1 = null, btnText2 = null;
		btnText1 = getIntent().getStringExtra("btn_1");
		if (oneButton) {
			((TextView) findViewById(R.id.buttonNegative)).setVisibility(View.GONE);
		} else {
			btnText2 = getIntent().getStringExtra("btn_2");
		}
		
		if (btnText1 != null)
			((TextView) findViewById(R.id.buttonPositive)).setText(btnText1);
		if (btnText2 != null)
			((TextView) findViewById(R.id.buttonNegative)).setText(btnText2);
		((TextView) findViewById(R.id.textMessage)).setText(message);
		

		((TextView) findViewById(R.id.buttonPositive)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});

		((TextView) findViewById(R.id.buttonNegative)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
