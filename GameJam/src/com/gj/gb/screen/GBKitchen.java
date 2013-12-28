package com.gj.gb.screen;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.gj.gb.R;
import com.gj.gb.gameview.GBDishModel;
import com.gj.gb.gameview.GBKitchenAdapter;

public class GBKitchen extends Activity {
	private GridView gridView;
	private ArrayList<GBDishModel> gridArray = new ArrayList<GBDishModel>();
	private GBKitchenAdapter customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_dishlist);

		//initialize
		init();

		//generate dishes
		generateDishes();
		
		customGridAdapter = new GBKitchenAdapter(this, R.layout.part_gridview,
				gridArray);
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
		// set dummy grid view item
		Bitmap homeIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_launcher);
		Bitmap userIcon = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ic_launcher);
		gridArray.add(new GBDishModel(homeIcon, "Home"));
		gridArray.add(new GBDishModel(userIcon, "User"));
		gridArray.add(new GBDishModel(homeIcon, "Home"));
		gridArray.add(new GBDishModel(userIcon, "User"));
		gridArray.add(new GBDishModel(homeIcon, "Home"));
		gridArray.add(new GBDishModel(userIcon, "User"));
		gridArray.add(new GBDishModel(homeIcon, "Home"));
		gridArray.add(new GBDishModel(userIcon, "User"));
		gridArray.add(new GBDishModel(userIcon, "User"));
		gridArray.add(new GBDishModel(homeIcon, "Home"));
		gridArray.add(new GBDishModel(userIcon, "User"));
		gridArray.add(new GBDishModel(homeIcon, "Home"));
		gridArray.add(new GBDishModel(userIcon, "User"));
		
	}
}
