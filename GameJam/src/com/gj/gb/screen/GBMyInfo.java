package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.util.GBDataManager;

public class GBMyInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_my_info);
		
		findViewById(R.id.buttonClose).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GBMyInfo.this, GBInGameMenu.class));
				finish();
			}
		});
		
		displayData();
	}

	private void displayData() {
		GBGameData data = GBDataManager.getGameData();
		((TextView) findViewById(R.id.textTotalDay)).setText(String.valueOf(data.getTotalDay()));
		
		ProgressBar levelBar = (ProgressBar) findViewById(R.id.progressLevel);
		ProgressBar expBar = (ProgressBar) findViewById(R.id.progressExp);
		ProgressBar staminaBar = (ProgressBar) findViewById(R.id.progressStamina);
		
		levelBar.setMax(100);
		levelBar.setProgress(data.getLevel());
		
		expBar.setMax(data.getNextLevel());
		expBar.setProgress(data.getExperience());
		
		staminaBar.setMax(10);
		staminaBar.setProgress(data.getStamina());
	}
}
