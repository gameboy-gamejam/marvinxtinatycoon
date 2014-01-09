package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.gj.gb.R;
import com.gj.gb.util.GBDataManager;

public class GBSplash extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_town_alt);
		
		if(getIntent().getBooleanExtra("ExitMe", false)){
            finish();
        }
		
		Handler handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				initApp();
				startActivity(new Intent(GBSplash.this, GBMain.class));
				finish();
				return true;
			}
		});
		handler.sendEmptyMessageDelayed(0, 10000);
	}

	protected void initApp() {
		// TODO put load data here
		GBDataManager.init(this);
	}
}
