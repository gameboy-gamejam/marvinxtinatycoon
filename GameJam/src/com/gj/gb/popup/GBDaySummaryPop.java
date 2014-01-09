package com.gj.gb.popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.screen.GBPopConfirm;
import com.gj.gb.util.GBDataManager;

public class GBDaySummaryPop extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_summary);
		
		findViewById(R.id.buttonNextDay).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		GBGameData data = GBDataManager.getGameData();
		int gold = data.getDayTotalGold();
		int exp = data.getDayTotalExperience();
		int ratings = data.getDayTotalRatings();
		
		((TextView) findViewById(R.id.textGold)).setText(String.valueOf(gold));		
		((TextView) findViewById(R.id.textExperience)).setText(String.valueOf(exp));		
		((TextView) findViewById(R.id.textRating)).setText(String.valueOf(ratings));
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Do you want to save the game?");
		startActivityForResult(intent, 100);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 100) {
			boolean save = resultCode == RESULT_OK;
			if (save) {
				saveData();
			} else {
				setResult(RESULT_OK);
				finish();
			}
		} else if (requestCode == 101) {
			setResult(RESULT_OK);
			finish();
		}
	}

	protected void saveData() {
		GBDataManager.saveData();
		
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Successfully saved");
		intent.putExtra("one_button", true);
		intent.putExtra("btn_1", "OK");
		startActivityForResult(intent, 101);
	}
}