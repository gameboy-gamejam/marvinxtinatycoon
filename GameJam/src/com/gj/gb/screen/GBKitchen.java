package com.gj.gb.screen;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.gj.gb.R;
import com.gj.gb.factory.GBRecipeFactory;
import com.gj.gb.gameview.GBKitchenAdapter;
import com.gj.gb.model.GBRecipe;

public class GBKitchen extends Activity {
	private GridView gridView;
	private List<GBRecipe> gridArray = new ArrayList<GBRecipe>();
	private GBKitchenAdapter customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_dishlist);

		//initialize
		init();

		//generate dishes
		generateDishes();
		
		customGridAdapter = new GBKitchenAdapter(this);
		customGridAdapter.setRecipeList(gridArray);
		gridView.setAdapter(customGridAdapter);
	}
	
	private void init(){
		gridView = (GridView) findViewById(R.id.dishlist);
		findViewById(R.id.btn_close).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void generateDishes(){
		for(int i = 0;i < 3; i++){
			gridArray.add(GBRecipeFactory.getRecipeById(i));
		}		
	}
}
