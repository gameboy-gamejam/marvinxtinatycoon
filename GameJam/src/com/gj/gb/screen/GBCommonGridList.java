package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gj.gb.R;

public class GBCommonGridList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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

	@Override
	public void onBackPressed() {
		startActivity(new Intent(GBCommonGridList.this, GBInGameMenu.class));
		finish();
	}
}
