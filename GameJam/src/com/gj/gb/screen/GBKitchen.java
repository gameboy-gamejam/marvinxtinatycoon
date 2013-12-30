package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gj.gb.R;
import com.gj.gb.popup.GBDishListPopup;

public class GBKitchen extends Activity {

	public static final int REQUEST_CODE_DISH = 10001;
	public static final int RESULT_CODE_DISH = 20001;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_kitchen);

		findViewById(R.id.btn_cook_1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toDishList(1);
			}
		});
		
		findViewById(R.id.btn_cook_2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toDishList(2);
			}
		});

	}
	
	private void toDishList(int stove_no){
		Intent intent = new Intent(this, GBDishListPopup.class);
		intent.putExtra("stove_no", stove_no);
		startActivityForResult(intent, REQUEST_CODE_DISH);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_CODE_DISH:
			if (resultCode == RESULT_CODE_DISH) {
				//TODO
			}

		}
	}

}
