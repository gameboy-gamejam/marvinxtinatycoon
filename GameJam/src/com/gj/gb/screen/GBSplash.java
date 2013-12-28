package com.gj.gb.screen;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;

import com.gj.gb.R;
import com.gj.gb.gameview.GBKitchenAdapter;
import com.gj.gb.gameview.GBDishModel;

public class GBSplash extends Activity {
	GridView gridView;
	ArrayList<GBDishModel> gridArray = new ArrayList<GBDishModel>();
	GBKitchenAdapter customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_title);
		
		startActivity(new Intent(this, GBKitchen.class));

	}
}
