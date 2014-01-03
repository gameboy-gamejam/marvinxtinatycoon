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
import com.gj.gb.gridview.IngredientGridViewAdapter;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.util.GBDataManager;

public class GBCommonGridList extends Activity {

	protected GBGameData data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		data = GBDataManager.getGameData();
		
		int type = getIntent().getIntExtra("type", -1);
		setContent(type);
		
		findViewById(R.id.buttonClose).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GBCommonGridList.this, GBInGameMenu.class));
				finish();
			}
		});
	}

	private void setContent(int type) {
		switch (type) {
		case R.id.buttonIngredients:
			setContentView(R.layout.scene_ingredients_grid);
			GridView grid = (GridView) findViewById(R.id.gridList);
			grid.setAdapter(new IngredientGridViewAdapter(this, data.getIngredients()));
			grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					updateIngredientInfo(position);
				}
			});
			updateIngredientInfo(0);
			break;
		case R.id.buttonRecipe:
			setContentView(R.layout.scene_recipe_grid);
			break;
		case R.id.buttonCustomer:
			setContentView(R.layout.scene_customer_grid);
			break;
		default:
			finish();
		}
	}

	protected void updateIngredientInfo(int position) {
		GBIngredient ingredient = data.getIngredients().get(position);
		
		((TextView) findViewById(R.id.textIngredientName)).setText(ingredient.getName());
		((TextView) findViewById(R.id.textIngredientPrice)).setText(""+ingredient.getPrice());
		((TextView) findViewById(R.id.textIngredientQty)).setText(ingredient.getQuantity()+"x");
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(GBCommonGridList.this, GBInGameMenu.class));
		finish();
	}
}
