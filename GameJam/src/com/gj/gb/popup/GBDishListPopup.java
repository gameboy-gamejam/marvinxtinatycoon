package com.gj.gb.popup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBRecipeFactory;
import com.gj.gb.gameview.GBDishListAdapter;
import com.gj.gb.model.GBRecipe;

public class GBDishListPopup extends Activity {
	private GridView gridView;
	private List<GBRecipe> mDishList = new ArrayList<GBRecipe>();
	private GBDishListAdapter mDishListAdapter;
	
	private static final int REQUEST_CODE_INGREDIENT = 10001;
	private static final int RESULT_CODE_INGREDIENT = 20001;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_dish_list);

		//initialize
		init();

		//generate dishes
		generateDishes();
		
		mDishListAdapter.setRecipeList(mDishList);
		mDishListAdapter.setInfoView(
				(TextView)findViewById(R.id.name),
				(TextView)findViewById(R.id.description),
				(TextView)findViewById(R.id.price));
		gridView.setAdapter(mDishListAdapter);
	}
	
	private void init(){
		mDishListAdapter = new GBDishListAdapter(this);
		gridView = (GridView) findViewById(R.id.dishlist);
		findViewById(R.id.btn_close).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		findViewById(R.id.btn_prepare).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDishListAdapter.getDishId() == -1) return;
				Intent intent = new Intent(GBDishListPopup.this, GBIngredientListPopup.class);
				intent.putExtra("DishId", mDishListAdapter.getDishId());
				startActivityForResult(intent, REQUEST_CODE_INGREDIENT);
			}
		});
	}
	
	private void generateDishes(){
		for(int i = 0;i < 3; i++){
			mDishList.add(GBRecipeFactory.getRecipeById(i));
		}	
		for(int i = 0;i < 3; i++){
			mDishList.add(GBRecipeFactory.getRecipeById(i));
		}	
		for(int i = 0;i < 3; i++){
			mDishList.add(GBRecipeFactory.getRecipeById(i));
		}	
	}
}
