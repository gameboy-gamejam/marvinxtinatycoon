package com.gj.gb.popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.screen.GBMarket;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.ImageCache;

public class GBMarketPopConfirm extends Activity {

	private int maxQty = 0;
	private int ingredientId = -1;
	private int quantity = 1;
	private int price = 0;
	private int type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_market);
		
		maxQty = getIntent().getIntExtra("max_qty", 0);
		ingredientId = getIntent().getIntExtra("ingredient_id", -1);
		type = getIntent().getIntExtra("type", GBMarket.TYPE_BUY);
		
		findViewById(R.id.buttonClose).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		findViewById(R.id.buttonBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		GBIngredient ingredient = GBIngredientsFactory.getIngredientById(ingredientId);
		if (ingredient != null) {
			price = GBEconomics.recomputePrice(ingredient.getPrice());
			((TextView) findViewById(R.id.textName)).setText(ingredient.getName());
			((TextView) findViewById(R.id.textPrice)).setText(price + "G");
		}
		
		updateQuantity(1);
		findViewById(R.id.buttonSpinner).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String[] items = new String[maxQty];
				
				for (int i=0; i<maxQty; i++) {
					items[i] = String.valueOf(i+1);
				}
				
				AlertDialog.Builder builder = new AlertDialog.Builder(GBMarketPopConfirm.this);
				builder.setItems(items, new AlertDialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						updateQuantity(which+1);
					}
				});
				builder.create().show();
			}
		});
		
		if (type == GBMarket.TYPE_SELL) {
			((Button) findViewById(R.id.buttonBuy)).setText("Confirm Sell");
		}
		
		findViewById(R.id.buttonBuy).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int totalAmount = quantity * price;
				GBGameData data = GBDataManager.getGameData();
				if (type == GBMarket.TYPE_BUY) {
					data.setCurrentGold(data.getCurrentGold() - totalAmount);
					data.updateIngredient(ingredientId, quantity);
				} else {
					data.setCurrentGold(data.getCurrentGold() + totalAmount);
					if (quantity == maxQty) {
						data.removeIngredient(ingredientId);
					} else {
						data.updateIngredient(ingredientId, -1 * quantity);
					}
				}
				setResult(RESULT_OK);
				finish();
			}
		});
		
		((ImageView) findViewById(R.id.imageIcon)).setImageBitmap(ImageCache.getBitmap(this, "ingredient_" + (ingredientId+1)));
	}

	private void updateQuantity(int i) {
		quantity = i;
		((TextView) findViewById(R.id.buttonSpinner)).setText(String.valueOf(quantity));
		((TextView) findViewById(R.id.textTotalAmount)).setText((quantity*price) + "G");
	}
}
