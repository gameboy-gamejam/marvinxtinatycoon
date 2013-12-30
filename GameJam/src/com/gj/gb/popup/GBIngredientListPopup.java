package com.gj.gb.popup;

import java.util.List;

import com.gj.gb.R;
import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.factory.GBRecipeFactory;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBRecipe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GBIngredientListPopup extends Activity {
	private int mDishId;
	GBRecipe mRecipe;

	private static int MAX_OVEN_CAPACITY = 5;

	private int[] mNameIdList = new int[] { R.id.name_ingredient_1,
			R.id.name_ingredient_2, R.id.name_ingredient_3 };
	private int[] mHolderIdList = new int[] { R.id.holder_ingredient_1,
			R.id.holder_ingredient_2, R.id.holder_ingredient_3 };
	private int[] mMaxIdList = new int[] { R.id.max_ingredient_1,
			R.id.max_ingredient_2, R.id.max_ingredient_3 };

	SharedPreferences mIngredientCountPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Integer> mIngredientList;
		setContentView(R.layout.popup_ingredient_list);

		//get saved ingredient count
		mIngredientCountPref = getSharedPreferences("IngredientCounts", MODE_PRIVATE);
		
		//get intent
		Intent intent = getIntent();
		mDishId = intent.getIntExtra("DishId", -1);
		GBRecipe mRecipe = GBRecipeFactory.getRecipeById(mDishId);
		mIngredientList = mRecipe.getIngredients();

		TextView dishName = (TextView) findViewById(R.id.name);

		TextView maxOvenCapacity = (TextView) findViewById(R.id.max_value);

		dishName.setText(mRecipe.getName());
		maxOvenCapacity.setText(MAX_OVEN_CAPACITY + "");

		// hide all the view first
		for (int i = 0; i < mHolderIdList.length; i++) {
			findViewById(mHolderIdList[i]).setVisibility(View.INVISIBLE);
		}

		for (int i = 0; i < mIngredientList.size(); i++) {
			// show only the views that are used
			findViewById(mHolderIdList[i]).setVisibility(View.VISIBLE);
			GBIngredient ingredient = GBIngredientsFactory
					.getIngredientById(mIngredientList.get(i));
			
			((TextView) findViewById(mNameIdList[i])).setText(ingredient
					.getName());

			((TextView) findViewById(mMaxIdList[i]))
					.setText(mIngredientCountPref.getInt("ingredient_"
							+ ingredient.getId(), -1)
							+ "");

		}

	}
}
