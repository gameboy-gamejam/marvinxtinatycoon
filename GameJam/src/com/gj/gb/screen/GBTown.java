package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

public class GBTown extends Activity {
  
	protected GBGameData data;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_town);

        int id = getIntent().getIntExtra("button_id", R.id.buttonNewGame);
        initData(id);
        initButtons();
    }

	private void initButtons() {
		findViewById(R.id.buttonMenu).setOnClickListener(buttonListener);
	}
	
	private OnClickListener buttonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.buttonMenu:
				startActivity(new Intent(GBTown.this, GBInGameMenu.class));
				break;
			}
		}
	};
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(GBTown.this, GBInGameMenu.class));
	}

	private void initData(int id) {
		if (id == R.id.buttonNewGame) {
			GBDataManager.createData();
			GBDataManager.saveData();
			updateData();
		} else if (id == R.id.buttonContinue) {
			// do nothing
			GBDataManager.loadData();
			updateData();
		} else {
			// fatal error
		}
	}

	private void updateData() {
		data = GBDataManager.getGameData();
		
		((TextView) findViewById(R.id.textGold)).setText(Utils.formatNum(data.getCurrentGold(), "#,###,###"));
		((TextView) findViewById(R.id.textRatings)).setText(String.valueOf(data.getCurrentRating()));
		((TextView) findViewById(R.id.textDay)).setText(Utils.formatDate(data.getCurrentDay(), data.getCurrentMonth(), data.getCurrentYear()));
		((ImageView) findViewById(R.id.imageDayState)).setBackgroundColor(formatDayState(data.getDayState()));
	}

	private int formatDayState(GBDayState dayState) {
		if (dayState == GBDayState.MORNING) {
			return Color.BLUE;
		} else if (dayState == GBDayState.AFTERNOON) {
			return Color.MAGENTA;
		} else if (dayState == GBDayState.EVENING) {
			return Color.DKGRAY;
		}
		return 0;
	}
}
