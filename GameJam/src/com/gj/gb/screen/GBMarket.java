package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.gridview.IngredientMarketGridViewAdapter;
import com.gj.gb.gridview.IngredientMarketGridViewAdapter2;
import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBGameData;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.Utils;

public class GBMarket extends Activity {

	public static final int TYPE_BUY = 1;
	public static final int TYPE_SELL = 2;
	
	private GBGameData data;
	
	private int type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_market);
		
		data = GBDataManager.getGameData();
		
		initButtons();
		updateData();
		updateBuyGridView();
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
				updateBuyGridView();
				break;
			case R.id.buttonSell:
				updateSellGridView();
				break;
			}
		}
	};

	private void initButtons() {
		findViewById(R.id.buttonDone).setOnClickListener(buttonListener);
		findViewById(R.id.buttonBuy).setOnClickListener(buttonListener);
		findViewById(R.id.buttonSell).setOnClickListener(buttonListener);
	}

	protected void updateBuyGridView() {
		type = TYPE_BUY;
		GridView grid = (GridView) findViewById(R.id.gridList);
		grid.setAdapter(new IngredientMarketGridViewAdapter2(this, GBEconomics.getMarketList()));
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				int iid = GBEconomics.getMarketList().get(position);
				int max = calculateMaxQuantity(iid);
				
				if (max == 0) {
					Intent intent = new Intent(GBMarket.this, GBPopConfirm.class);
					intent.putExtra("message", "You do not have enough gold to purchase this ingredient.");
					intent.putExtra("one_button", true);
					intent.putExtra("btn_1", "OK");
					startActivityForResult(intent, 500);
				} else {
					Intent intent = new Intent(GBMarket.this, GBMarketPopConfirm.class);
					intent.putExtra("ingredient_id", iid);
					intent.putExtra("max_qty", max);
					intent.putExtra("type", TYPE_BUY);
					startActivityForResult(intent, 1000);
				}
			}
		});
	}

	protected int calculateMaxQuantity(int id) {
		int normalPrice = GBIngredientsFactory.getIngredientById(id).getPrice();
		int price = GBEconomics.recomputePrice(normalPrice);
		int gold = data.getCurrentGold();
		
		int quantity = 0;
		for (int i=0; /* infinite ;A; watdapak*/; i++) {
			int temp = i * price;
			if (temp <= gold) {
				quantity++;
			} else {
				quantity-=1;
				break;
			}
		}
		
		return quantity;
	}

	protected void confirmExit() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Are you finished buying/selling ingredients?");
		startActivityForResult(intent, 100);
	}

	protected void updateSellGridView() {
		type = TYPE_SELL;
		GridView grid = (GridView) findViewById(R.id.gridList);
		grid.setAdapter(new IngredientMarketGridViewAdapter(this, data.getIngredients()));
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(GBMarket.this, GBMarketPopConfirm.class);
				intent.putExtra("ingredient_id", data.getIngredients().get(position).getId());
				intent.putExtra("max_qty", data.getIngredients().get(position).getQuantity());
				intent.putExtra("type", TYPE_SELL);
				startActivityForResult(intent, 1000);
			}
		});
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
		} else if (requestCode == 1000 && resultCode == RESULT_OK) {
			updateData();
			if (type == TYPE_SELL) {
				updateSellGridView();
			}
		}
	}

	@Override
	public void onBackPressed() {
		confirmExit();
	}

}
