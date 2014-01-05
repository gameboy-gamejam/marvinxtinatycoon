package com.gj.gb.popup;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBIngredientsFactory;
import com.gj.gb.factory.GBRecipeFactory;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBIngredient;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.GBDataManager;

public class GBIngredientListPopup extends Activity {
	private int mDishId;
	private int mCount;
	GBRecipe mRecipe;

	private static int MAX_OVEN_CAPACITY = 5;

	// no choice ako dito hahahah tyaka na lng to i optimize
	private int[] mNameIdList = new int[] { R.id.name_ingredient_1,
			R.id.name_ingredient_2, R.id.name_ingredient_3 };
	private int[] mHolderIdList = new int[] { R.id.holder_ingredient_1,
			R.id.holder_ingredient_2, R.id.holder_ingredient_3 };
	private int[] mMaxIdList = new int[] { R.id.max_ingredient_1,
			R.id.max_ingredient_2, R.id.max_ingredient_3 };
	private int[] mCurrentIdList = new int[] { R.id.current_ingredient_1,
			R.id.current_ingredient_2, R.id.current_ingredient_3 };

	List<Integer> mIngredientList;

	GBGameData data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_ingredient_list);

		// get intent
		Intent intent = getIntent();
		mDishId = intent.getIntExtra("DishId", -1);
		mCount = 0;
		GBRecipe mRecipe = GBRecipeFactory.getRecipeById(mDishId);
		mIngredientList = mRecipe.getIngredients();

		data = GBDataManager.getGameData();
		
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
					.setText(data.getIngredientQty(ingredient.getId())
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
		
		findViewById(R.id.btn_cook).setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				cook();
			}
		});
		

	}
	
	//para sa method na to...
	// chinecheck ko muna kung kailan pwede gumana yung cook button...
	// tapos yun na babawasan ko na agad yung ingredients .. astigggg
	//tapos kailangan kasi ng dishId at cookCount duhhh!!! para sa stove at sa sharedpref sa GB Kitchen thingy...
	public void cook(){
		for (int i = 0; i < mIngredientList.size(); i++) {
			GBIngredient ingredient = GBIngredientsFactory.getIngredientById(mIngredientList.get(i));
			int count = data.getIngredientQty(ingredient.getId());
			if(mCount > count || mCount == 0) return;
		}
		
		//deduct the ingredients from the shared pref
		for (int i = 0; i < mIngredientList.size(); i++) {
			GBIngredient ingredient = GBIngredientsFactory.getIngredientById(mIngredientList.get(i));
			data.updateIngredient(ingredient.getId(), -1 * mCount);
		}
		Intent intent = getIntent();
		intent.putExtra("DishId", mDishId);
		intent.putExtra("cook_count", mCount);
		setResult(GBDishListPopup.RESULT_CODE_INGREDIENT, intent);
		finish();
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
