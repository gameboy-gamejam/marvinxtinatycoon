package com.gj.gb.screen;

import android.app.Activity;
import android.os.Bundle;

import com.gj.gb.R;

public class GBKitchen extends Activity {

	public static final int REQUEST_CODE_DISH = 10001;
	public static final int RESULT_CODE_DISH = 20001;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_kitchen);

	}

}
