package com.gj.gb.screen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gj.gb.R;

public class GBInGameMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_game_menu);
		
		initButtons();
		animateButtons();
	}

	private void animateButtons() {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		Animation shakeRev = AnimationUtils.loadAnimation(this, R.anim.shake_rev);
		
		findViewById(R.id.buttonMyInfo).startAnimation(shake);
		findViewById(R.id.buttonRecipe).startAnimation(shake);
		findViewById(R.id.buttonCustomer).startAnimation(shake);
		findViewById(R.id.buttonSystem).startAnimation(shakeRev);
		findViewById(R.id.buttonIngredients).startAnimation(shakeRev);
	}

	private void initButtons() {
		findViewById(R.id.buttonClose).setOnClickListener(buttonListener);
	}

	private OnClickListener buttonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			
			switch (id) {
			case R.id.buttonClose:
				finish();
				break;
			}
		}
	};
}
