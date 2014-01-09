package com.gj.gb.popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.gj.gb.R;
import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.ImageCache;
import com.gj.gb.util.Utils;

public class GBLevelUpPop extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_levelup);
		
		findViewById(R.id.buttonClose).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
		
		findViewById(R.id.buttonOk).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
		
		initData();
	}

	private void initData() {
		ImageView image = (ImageView) findViewById(R.id.imagePrize);
		
		GBIngredient ingredient = GBIngredientsFactory.getIngredientById(Utils.RANDOM.nextInt(47));
		image.setImageBitmap(ImageCache.getBitmap(this, "ingredient_"+(ingredient.getId()+1)));
		
		GBDataManager.getGameData().updateIngredient(ingredient.getId(), 1);
	}
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		finish();
	}
}
