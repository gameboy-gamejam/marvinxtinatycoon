package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBGameData.GBDayState;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.Utils;

public class GBOutside extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_outside);
	
		initButtons();
		updateData();
	}
	
	private void updateData() {
		GBGameData data = GBDataManager.getGameData();
		
		((TextView) findViewById(R.id.textGold)).setText(Utils.formatNum(data.getCurrentGold(), "#,###,###"));
		((TextView) findViewById(R.id.textRatings)).setText(String.valueOf(data.getCurrentRating()));
		((TextView) findViewById(R.id.textDay)).setText(Utils.formatDate(data.getCurrentDay(), data.getCurrentMonth(), data.getCurrentYear()));
		((ImageView) findViewById(R.id.imageDayState)).setImageResource(formatDayState(data.getDayState()));
	}

	private int formatDayState(GBDayState dayState) {
		if (dayState == GBDayState.AFTERNOON) {
			return R.drawable.daystate_1;
		} else if (dayState == GBDayState.EVENING) {
			return R.drawable.daystate_2;
		}
		return R.drawable.daystate_0;
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

	@Override
	public void onBackPressed() {
		toTown();
	}
}
