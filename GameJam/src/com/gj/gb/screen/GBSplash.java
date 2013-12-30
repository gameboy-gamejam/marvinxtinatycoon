package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gj.gb.R;
import com.gj.gb.popup.GBDishListPopup;

public class GBSplash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_title);
		
		//Save Ingredient TEMP
		SharedPreferences mIngredientCountPref = getSharedPreferences("IngredientCounts",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = mIngredientCountPref.edit();
		editor.putInt("ingredient_0", 41);
		editor.putInt("ingredient_1", 40);
		editor.putInt("ingredient_2", 39);
		editor.putInt("ingredient_3", 38);
		editor.putInt("ingredient_4", 37);
		editor.putInt("ingredient_5", 36);
		editor.putInt("ingredient_6", 35);
		editor.putInt("ingredient_7", 34);
		editor.putInt("ingredient_8", 33);
		editor.putInt("ingredient_9", 32);
		editor.putInt("ingredient_10", 31);
		editor.commit();
		
//		startActivity(new Intent(this, GBKitchen.class));
//		startActivity(new Intent(this, GBIngredientListPopup.class));
		startActivity(new Intent(this, GBDishListPopup.class));
	}
}
