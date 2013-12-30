package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gj.gb.R;
import com.gj.gb.model.GBStove;
import com.gj.gb.model.GBStove.OvenStatus;
import com.gj.gb.popup.GBDishListPopup;

public class GBKitchen extends Activity {

	public static final int REQUEST_CODE_DISH = 10001;
	public static final int RESULT_CODE_DISH = 20001;
	GBStove stove1, stove2, stove3, stove4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_kitchen);
		
		stove1 = new GBStove(this, R.id.progressBar1);
		stove2 = new GBStove(this, R.id.progressBar2);
		stove3 = new GBStove(this, R.id.progressBar3);
		stove4 = new GBStove(this, R.id.progressBar4);

		findViewById(R.id.btn_cook_1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(stove1.getStatus() == OvenStatus.VACANT){
					toDishList(1);
				}	else if(stove1.getStatus() == OvenStatus.COOKING){
					stove1.reset();
				} else if(stove1.getStatus() == OvenStatus.FINISHED){
					stove1.reset();
					addDishCount(stove1);
				}else if(stove1.getStatus() == OvenStatus.OVERCOOKED){
					stove1.reset();
				}	
			}
		});

		findViewById(R.id.btn_cook_2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(stove2.getStatus() == OvenStatus.VACANT){
					toDishList(2);
				}	else if(stove2.getStatus() == OvenStatus.COOKING){
					stove2.reset();
				} else if(stove2.getStatus() == OvenStatus.FINISHED){
					stove2.reset();
					addDishCount(stove2);
				}else if(stove2.getStatus() == OvenStatus.OVERCOOKED){
					stove2.reset();		
				}
			}
		});
		
		findViewById(R.id.btn_cook_3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(stove3.getStatus() == OvenStatus.VACANT){
					toDishList(3);
				}	else if(stove3.getStatus() == OvenStatus.COOKING){
					stove3.reset();
				} else if(stove3.getStatus() == OvenStatus.FINISHED){
					stove3.reset();
					addDishCount(stove3);
				}else if(stove3.getStatus() == OvenStatus.OVERCOOKED){
					stove3.reset();		
				}
			}
		});
		
		findViewById(R.id.btn_cook_4).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(stove4.getStatus() == OvenStatus.VACANT){
					toDishList(4);
				}	else if(stove4.getStatus() == OvenStatus.COOKING){
					stove4.reset();
				} else if(stove4.getStatus() == OvenStatus.FINISHED){
					stove4.reset();
				}
			}
		});

	}

	private void toDishList(int stove_no) {
		Intent intent = new Intent(this, GBDishListPopup.class);
		intent.putExtra("stove_no", stove_no);
		startActivityForResult(intent, REQUEST_CODE_DISH);
	}
	
	private void addDishCount(GBStove stove){
		SharedPreferences mDishCountPref = getSharedPreferences("DishCounts", MODE_PRIVATE);
		SharedPreferences.Editor editorDish = mDishCountPref.edit();
		editorDish.putInt("dish_"+stove.getDishId(), mDishCountPref.getInt("dish_"+stove.getDishId(), 0)+ stove.getCount());
		editorDish.commit();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_CODE_DISH:
			if (resultCode == RESULT_CODE_DISH) {
				data.getIntExtra("stove_no", -1);
				
				switch (data.getIntExtra("stove_no", -1)) {
				case 1:
					stove1.setDishId(data.getIntExtra("DishId", -1));
					stove1.setCount(data.getIntExtra("cook_count", -1));
					stove1.startCooking();
					break;
				case 2:
					stove2.setDishId(data.getIntExtra("DishId", -1));
					stove2.setCount(data.getIntExtra("cook_count", -1));
					stove2.startCooking();
					break;
				case 3:
					stove3.setDishId(data.getIntExtra("DishId", -1));
					stove3.setCount(data.getIntExtra("cook_count", -1));
					stove3.startCooking();
					break;
				case 4:
					stove4.setDishId(data.getIntExtra("DishId", -1));
					stove4.setCount(data.getIntExtra("cook_count", -1));
					stove4.startCooking();
					break;
				default:
					//do nothing
					break;
				}
			}
		}
	}
}
