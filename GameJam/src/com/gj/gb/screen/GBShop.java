package com.gj.gb.screen;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.gj.gb.R;
import com.gj.gb.screen.surface.GBShopSurfaceView;

public class GBShop extends Activity {

	protected GBShopSurfaceView surfaceView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scene_shop);
        surfaceView = new GBShopSurfaceView(this);
        ((ViewGroup) findViewById(R.id.mainPanel)).addView(surfaceView);
    }
}
