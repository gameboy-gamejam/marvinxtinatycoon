package com.gj.gb.screen;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.gj.gb.R;
import com.gj.gb.logic.GBShopLogic;
import com.gj.gb.model.GBCustomer;
import com.gj.gb.screen.surface.GBShopSurfaceView;

public class GBShop extends Activity {

	protected GBShopSurfaceView surfaceView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scene_shop);
        List<GBCustomer> customers = GBShopLogic.getCustomers(5);
        int n = customers.size();
        for (int i=0; i<n; i++) {
        	Log.v("test", "Customer " + (i+1) + ": " + customers.get(i).getDecideTime());
        }
//        surfaceView = new GBShopSurfaceView(this);
//        ((ViewGroup) findViewById(R.id.mainPanel)).addView(surfaceView);
    }
}