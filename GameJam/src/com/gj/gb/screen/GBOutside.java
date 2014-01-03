package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gj.gb.R;

public class GBOutside extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_outside);
	
		initButtons();
	}
	
	private OnClickListener buttonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			
			switch (id) {
			case R.id.buttonTown:
				toTown();
				break;
			}
		}
	};

	private void initButtons() {
		findViewById(R.id.buttonTown).setOnClickListener(buttonListener);
	}

	protected void toTown() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Do you want to return to town?");
		startActivityForResult(intent, 100);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 100 && resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
}
