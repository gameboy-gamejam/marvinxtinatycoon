package com.gj.gb.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.util.GBDataManager;

public class GBDaySummaryPop extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_summary);

		findViewById(R.id.buttonNextDay).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						onBackPressed();
					}
				});

		GBGameData data = GBDataManager.getGameData();
		int gold = data.getDayTotalGold();
		float exp = data.getDayTotalExperience();
		int ratings = data.getDayTotalRatings();

		((TextView) findViewById(R.id.textDayLabel)).setText("DAY " + (data.getTotalDay()+1));
		((TextView) findViewById(R.id.textGold)).setText(String.valueOf(gold));
		((TextView) findViewById(R.id.textExperience)).setText(String
				.valueOf(exp));
		((TextView) findViewById(R.id.textRating)).setText(String
				.valueOf(ratings));
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}
}