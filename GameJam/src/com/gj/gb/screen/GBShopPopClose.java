package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.Utils;

public class GBShopPopClose extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_shop);

		initData();

		findViewById(R.id.buttonContinue).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						onBackPressed();
					}
				});

		findViewById(R.id.buttonClose).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						onBackPressed();
					}
				});
	}

	private void initData() {
		Intent intent = getIntent();
		int gold = intent.getIntExtra("gold_earned", 0);
		int totalCustomer = intent.getIntExtra("total_customer", 0);
		int totalCustomerServed = intent.getIntExtra("customer_served", 0);
		int experience = intent.getIntExtra("experience_gained", 0);
		int ratings = intent.getIntExtra("ratings_earned", 0);
		String rating = "";
		if (ratings >= 0)
			rating = "+";
		rating += ratings;

		GBGameData data = GBDataManager.getGameData();
		data.setCurrentGold(data.getCurrentGold() + gold);
		data.setTotalCustomers(data.getTotalCustomers() + totalCustomer);
		data.setExperience(data.getExperience() + experience);
		data.setCurrentRating(data.getCurrentRating() + ratings);
		
		data.setDayTotalCustomer(data.getDayTotalCustomer()+totalCustomer);
		data.setDayTotalExperience(data.getDayTotalExperience()+experience);
		data.setDayTotalGold(data.getDayTotalGold()+gold);
		data.setDayTotalRatings(data.getDayTotalRatings()+ratings);

		((TextView) findViewById(R.id.textGold)).setText(Utils.formatNum(gold,
				"#,###,###"));
		((TextView) findViewById(R.id.textCustomer))
				.setText(totalCustomerServed + "/" + totalCustomer);
		((TextView) findViewById(R.id.textExperience))
				.setText("+" + experience);
		((TextView) findViewById(R.id.textRating)).setText(rating);
	}

	@Override
	public void onBackPressed() {
		// GBTown.shopFlag = true;
		Intent intent = new Intent(GBShopPopClose.this, GBTown.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);

		setResult(RESULT_OK);
		finish();
	}
}
