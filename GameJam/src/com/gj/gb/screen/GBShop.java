package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gj.gb.R;
import com.gj.gb.widget.GBShopSurface;

public class GBShop extends Activity {

	public static boolean returnToMain = false;
	
	protected GBShopSurface surface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_shop);
		surface = (GBShopSurface) findViewById(R.id.gBShopSurface1);
		
		findViewById(R.id.buttonMenu).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GBShop.this, GBInGameMenu.class);
				intent.putExtra("from", "shop");
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		surface.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		/* CHEAT! */
		if (returnToMain) {
			returnToMain = false;
			finish();
		}
		
		surface.resume();
	}
	
	@Override
	protected void onDestroy() {
		surface.destroy();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(GBShop.this, GBInGameMenu.class);
		intent.putExtra("from", "shop");
		startActivity(intent);
	}
}
