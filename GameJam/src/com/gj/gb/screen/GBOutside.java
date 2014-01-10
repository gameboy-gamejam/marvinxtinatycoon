package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBGameData.GBDayState;
import com.gj.gb.popup.GBAcknowledgementPopup;
import com.gj.gb.popup.GBPopConfirm;
import com.gj.gb.stage.AppleCatchingStage;
import com.gj.gb.stage.CarrotStage;
import com.gj.gb.stage.FishStage;
import com.gj.gb.util.GBDataManager;

public class GBOutside extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_outside);

		initButtons();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateData();
	}

	private void updateData() {
		GBGameData data = GBDataManager.getGameData();

		((TextView) findViewById(R.id.textStamina)).setText(String.valueOf(data
				.getStamina()));
		updateDayState();
	}

	private void updateDayState() {
		GBDayState dayState = GBDataManager.getGameData().getDayState();
		if (dayState == GBDayState.AFTERNOON) {
			findViewById(R.id.bgSky).setBackgroundResource(R.color.sky_2);
			findViewById(R.id.bgFilter).setBackgroundResource(
					R.color.sky_2_clear);
			findViewById(R.id.bgFilter).setVisibility(View.VISIBLE);
		} else if (dayState == GBDayState.EVENING) {
			findViewById(R.id.bgSky).setBackgroundResource(R.color.sky_3);
			findViewById(R.id.bgFilter).setBackgroundResource(
					R.color.sky_3_clear);
			findViewById(R.id.bgFilter).setVisibility(View.VISIBLE);
		} else {
			findViewById(R.id.bgSky).setBackgroundResource(R.color.sky_1);
			findViewById(R.id.bgFilter).setVisibility(View.INVISIBLE);
		}
	}
	private OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			GBGameData gbData = GBDataManager.getGameData();
			switch (id) {
			case R.id.buttonTown:
				toTown();
				break;
			case R.id.buttonGame1:
				gbData.setExperience(gbData.getExperience() + 1);
				if (gbData.getStamina() > 0) {
					gbData.useStamina();
					startActivity(new Intent(GBOutside.this, CarrotStage.class));
				} else {
					showNotEnoughStaminaPopup();
				}
				break;
			case R.id.buttonGame2:
				gbData.setExperience(gbData.getExperience() + 1);
				if (gbData.getStamina() > 0) {
					gbData.useStamina();
					startActivity(new Intent(GBOutside.this, FishStage.class));
				} else {
					showNotEnoughStaminaPopup();
				}
				break;
			case R.id.buttonGame3:
				gbData.setExperience(gbData.getExperience() + 1);
				if (gbData.getStamina() > 0) {
					gbData.useStamina();
					startActivity(new Intent(GBOutside.this,
							AppleCatchingStage.class));
				} else {
					showNotEnoughStaminaPopup();
				}
				break;
			}
		}
	};

	private void showNotEnoughStaminaPopup() {
		Intent intent = new Intent(GBOutside.this, GBAcknowledgementPopup.class);
		intent.putExtra(GBAcknowledgementPopup.KEY_EXTRA_MESSAGE,
				getResources().getString(R.string.not_enough_stamina));
		startActivity(intent);
	}

	private void initButtons() {
		findViewById(R.id.buttonTown).setOnClickListener(buttonListener);
		findViewById(R.id.buttonGame1).setOnClickListener(buttonListener);
		findViewById(R.id.buttonGame2).setOnClickListener(buttonListener);
		findViewById(R.id.buttonGame3).setOnClickListener(buttonListener);
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

	@Override
	public void onBackPressed() {
		toTown();
	}
}
