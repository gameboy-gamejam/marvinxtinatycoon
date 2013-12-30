package com.gj.gb.popup;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.factory.GBRecipeFactory;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBRecipe;

public class GBIngredientListPopup extends Activity {
	private int mDishId;
	private int mCount;
	GBRecipe mRecipe;

	private static int MAX_OVEN_CAPACITY = 5;

	private int[] mNameIdList = new int[] { R.id.name_ingredient_1,
			R.id.name_ingredient_2, R.id.name_ingredient_3 };
	private int[] mHolderIdList = new int[] { R.id.holder_ingredient_1,
			R.id.holder_ingredient_2, R.id.holder_ingredient_3 };
	private int[] mMaxIdList = new int[] { R.id.max_ingredient_1,
			R.id.max_ingredient_2, R.id.max_ingredient_3 };

	private int[] mCurrentIdList = new int[] { R.id.current_ingredient_1,
			R.id.current_ingredient_2, R.id.current_ingredient_3 };

	SharedPreferences mIngredientCountPref;
	List<Integer> mIngredientList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_ingredient_list);

		// get saved ingredient count
		mIngredientCountPref = getSharedPreferences("IngredientCounts",
				MODE_PRIVATE);

		// get intent
		Intent intent = getIntent();
		mDishId = intent.getIntExtra("DishId", -1);
		mCount = 0;
		GBRecipe mRecipe = GBRecipeFactory.getRecipeById(mDishId);
		mIngredientList = mRecipe.getIngredients();

		((TextView) findViewById(R.id.name)).setText(mRecipe.getName());
		((TextView) findViewById(R.id.max_value)).setText(MAX_OVEN_CAPACITY
				+ "");

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
		
		findViewById(R.id.btn_close).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		findViewById(R.id.btn_right).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				addCount();
			}
		});
		
		findViewById(R.id.btn_left).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				subCount();
			}
		});

	}

	public void addCount() {
		if (mCount >= MAX_OVEN_CAPACITY)
			return;
		mCount++;
		for (int i = 0; i < mIngredientList.size(); i++) {
			((TextView) findViewById(mCurrentIdList[i])).setText(mCount + "");
		}
		((TextView) findViewById(R.id.current_value)).setText(mCount + "");
	}
	
	public void subCount() {
		if (mCount <= 0)
			return;
		mCount--;
		for (int i = 0; i < mIngredientList.size(); i++) {
			((TextView) findViewById(mCurrentIdList[i])).setText(mCount + "");
		}
		((TextView) findViewById(R.id.current_value)).setText(mCount + "");
	}
}
