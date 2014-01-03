package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.gridview.IngredientMarketGridViewAdapter;
import com.gj.gb.model.GBGameData;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.Utils;

public class GBMarket extends Activity {

	private GBGameData data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_market);
		
		data = GBDataManager.getGameData();
		
		initButtons();
		updateData();
	}
	
	private OnClickListener buttonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			
			switch (id) {
			case R.id.buttonDone:
				confirmExit();
				break;
			case R.id.buttonBuy:
				break;
			case R.id.buttonSell:
				updateGridView();
				break;
			}
		}
	};

	private void initButtons() {
		findViewById(R.id.buttonDone).setOnClickListener(buttonListener);
		findViewById(R.id.buttonBuy).setOnClickListener(buttonListener);
		findViewById(R.id.buttonSell).setOnClickListener(buttonListener);
	}

	protected void confirmExit() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Are you finished buying/selling ingredients?");
		startActivityForResult(intent, 100);
	}

	protected void updateGridView() {
		GridView grid = (GridView) findViewById(R.id.gridList);
		grid.setAdapter(new IngredientMarketGridViewAdapter(this, data.getIngredients()));
	}

	private void updateData() {
		GBGameData data = GBDataManager.getGameData();
		
		((TextView) findViewById(R.id.textCurrentGold)).setText(Utils.formatNum(data.getCurrentGold(), "#,###,###"));
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
		confirmExit();
	}

}
