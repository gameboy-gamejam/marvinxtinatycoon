package com.gj.gb.screen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gj.gb.R;

public class GBMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_main);

		findViewById(R.id.buttonNewGame).setOnClickListener(listener);
		findViewById(R.id.buttonContinue).setOnClickListener(listener);
		findViewById(R.id.buttonScores).setOnClickListener(listener);
		findViewById(R.id.textTap).setOnClickListener(listener);
	}
	
	protected OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.buttonNewGame:
			case R.id.buttonContinue:
			case R.id.buttonScores:
			case R.id.textTap:
			}
		}
	};
}
