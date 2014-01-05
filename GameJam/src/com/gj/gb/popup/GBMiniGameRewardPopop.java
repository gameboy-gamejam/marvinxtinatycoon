package com.gj.gb.popup;

import com.gj.gb.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class GBMiniGameRewardPopop extends Activity {
	
	public static final String KEY_EXTRA_POINTS = "points";
	public static final int REQUEST_CODE_REWARD = 13000;
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			
			switch (id) {
				case R.id.returnTown:
					setResult(RESULT_CANCELED);
					finish();
					break;
				case R.id.playAgain:
					setResult(RESULT_OK);
					finish();
					break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_minigame_reward);
		
		int points = getIntent().getExtras().getInt(KEY_EXTRA_POINTS);
		
		((TextView) findViewById(R.id.points)).setText(String.valueOf(points));
		findViewById(R.id.returnTown).setOnClickListener(mOnClickListener);
		findViewById(R.id.playAgain).setOnClickListener(mOnClickListener);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
