package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.gj.gb.R;

public class GBSplash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_title);
		// MAAWA KAYO DONT DELETE THIS UNLESS NA IMPLEMENT  NA YUNG NEW SAVE FUNCTIONALITIES HEHE
		//Save Ingredient TEMP
		SharedPreferences mIngredientCountPref = getSharedPreferences("IngredientCounts", MODE_PRIVATE);
		SharedPreferences.Editor editorIngredient = mIngredientCountPref.edit();
		//ingredients
		editorIngredient.putInt("ingredient_0", 41);
		editorIngredient.putInt("ingredient_1", 40);
		editorIngredient.putInt("ingredient_2", 39);
		editorIngredient.putInt("ingredient_3", 38);
		editorIngredient.putInt("ingredient_4", 37);
		editorIngredient.putInt("ingredient_5", 36);
		editorIngredient.putInt("ingredient_6", 35);
		editorIngredient.putInt("ingredient_7", 34);
		editorIngredient.putInt("ingredient_8", 33);
		editorIngredient.putInt("ingredient_9", 32);
		editorIngredient.putInt("ingredient_10", 4);
		editorIngredient.commit();
		
		SharedPreferences mDishCountPref = getSharedPreferences("DishCounts", MODE_PRIVATE);
		SharedPreferences.Editor editorDish = mDishCountPref.edit();
		//dish
		editorDish.putInt("dish_0", 0);
		editorDish.putInt("dish_1", 0);
		editorDish.putInt("dish_2", 0);
		editorDish.commit();
		
		startActivity(new Intent(this, GBKitchen.class));
//		startActivity(new Intent(this, GBIngredientListPopup.class));
//		startActivity(new Intent(this, GBDishListPopup.class));
	}
}
