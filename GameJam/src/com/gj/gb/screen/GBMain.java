package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gj.gb.R;
import com.gj.gb.util.GBDataManager;

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
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// enables the continue button if there is save file
		if (GBDataManager.hasSaveData()) {
			findViewById(R.id.buttonContinue).setEnabled(true);
		}
	}

	protected OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.buttonNewGame:
				if (GBDataManager.hasSaveData()) {
					// ask popup if want to clear data and start new game
				} else {
					toGameScreen(id);
				}
				break;
			case R.id.buttonContinue:
				break;
			case R.id.buttonScores:
			case R.id.textTap:
				findViewById(R.id.panelButtons).setVisibility(View.VISIBLE);
				findViewById(R.id.panelStart).setVisibility(View.INVISIBLE);
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		GBDataManager.cleanup();
	}

	protected void toGameScreen(int id) {
		Intent intent = new Intent(this, GBTown.class);
		intent.putExtra("button_id", id);
		startActivity(intent);
	}
}
