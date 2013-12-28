package com.gj.gb.screen;

import java.util.ArrayList;

import com.gj.gb.R;
import com.gj.gb.gameview.GBDishModel;
import com.gj.gb.gameview.GBKitchenAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;

public class GBKitchen extends Activity {
	GridView gridView;
	ArrayList<GBDishModel> gridArray = new ArrayList<GBDishModel>();
	GBKitchenAdapter customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_dishlist);

		// set grid view item
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
		gridView = (GridView) findViewById(R.id.dishlist);
		customGridAdapter = new GBKitchenAdapter(this, R.layout.part_gridview,
				gridArray);
		gridView.setAdapter(customGridAdapter);
	}
}
