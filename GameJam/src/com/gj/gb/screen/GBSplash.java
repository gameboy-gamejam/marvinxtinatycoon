package com.gj.gb.screen;

import com.gj.gb.R;
import com.gj.gb.R.layout;

import android.app.Activity;
import android.os.Bundle;

public class GBSplash extends Activity {
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_splash);
        if(getIntent().getBooleanExtra("ExitMe", false)){
            finish();
        }

    }
}
