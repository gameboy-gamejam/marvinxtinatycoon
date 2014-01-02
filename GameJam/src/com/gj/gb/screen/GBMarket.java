package com.gj.gb.screen;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class GBMarket extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_market);
		
		initData();
		initButtons();
	}
	
	private OnClickListener buttonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			
			switch (id) {
			case R.id.buttonDone:
				finish();
				break;
			case R.id.buttonBuy:
			case R.id.buttonSell:
			}
		}
	};

	private void initButtons() {
		findViewById(R.id.buttonDone).setOnClickListener(buttonListener);
		findViewById(R.id.buttonBuy).setOnClickListener(buttonListener);
		findViewById(R.id.buttonSell).setOnClickListener(buttonListener);
	}

	private void initData() {
		GBGameData data = GBDataManager.getGameData();
		
		((TextView) findViewById(R.id.textCurrentGold)).setText(Utils.formatNum(data.getCurrentGold(), "#,###,###"));
	}

}
