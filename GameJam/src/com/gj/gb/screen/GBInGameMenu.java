package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gj.gb.R;

public class GBInGameMenu extends Activity {

	protected int selectedId = -1;
	
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
		findViewById(R.id.buttonMyInfo).setOnClickListener(buttonListener);
		findViewById(R.id.buttonRecipe).setOnClickListener(buttonListener);
		findViewById(R.id.buttonCustomer).setOnClickListener(buttonListener);
		findViewById(R.id.buttonSystem).setOnClickListener(buttonListener);
		findViewById(R.id.buttonIngredients).setOnClickListener(buttonListener);
	}

	private OnClickListener buttonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			
			if (id == R.id.buttonClose) {
				finish();
				return;
			}

			if (selectedId == id) {
				switch (id) {
				case R.id.buttonMyInfo:
					break;
				case R.id.buttonRecipe:
					break;
				case R.id.buttonCustomer:
					break;
				case R.id.buttonSystem:
					startActivity(new Intent(GBInGameMenu.this, GBSystemMenu.class));
					finish();
					break;
				case R.id.buttonIngredients:
					break;
				}
			} else {
				selectedId = id;
				setTextLabel(id);
			}
		}
	};

	private void setTextLabel(int id) {
		TextView text = (TextView) findViewById(R.id.textMenuName);
		
		switch (id) {
		case R.id.buttonMyInfo:
			text.setText("My Info");
			break;
		case R.id.buttonRecipe:
			text.setText("Recipe List");
			break;
		case R.id.buttonCustomer:
			text.setText("Customer List");
			break;
		case R.id.buttonSystem:
			text.setText("System");
			break;
		case R.id.buttonIngredients:
			text.setText("Ingredients List");
			break;
		}
	}
}
