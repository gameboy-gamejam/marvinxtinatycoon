package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gj.gb.R;
import com.gj.gb.popup.GBPopConfirm;
import com.gj.gb.util.GBDataManager;

public class GBSystemMenu extends Activity {

	protected String from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_system_menu);

		from = getIntent().getStringExtra("from");

		if (from.equals("shop") || from.compareTo("shop") == 0) {
			findViewById(R.id.buttonSave).setEnabled(false);
		}

		initButtons();
	}

	private void initButtons() {
		findViewById(R.id.buttonClose).setOnClickListener(listener);
		findViewById(R.id.buttonContinue).setOnClickListener(listener);
		findViewById(R.id.buttonSave).setOnClickListener(listener);
		findViewById(R.id.buttonMain).setOnClickListener(listener);
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(GBSystemMenu.this, GBInGameMenu.class));
		finish();
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();

			switch (id) {
			case R.id.buttonClose:
				startActivity(new Intent(GBSystemMenu.this, GBInGameMenu.class));
				finish();
				break;
			case R.id.buttonContinue:
				finish();
				break;
			case R.id.buttonSave:
				saveData();
				break;
			case R.id.buttonMain:
				toMainMenu();
				break;
			}
		}
	};

	protected void saveData() {
		GBDataManager.saveData();

		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Successfully saved");
		intent.putExtra("one_button", true);
		intent.putExtra("btn_1", "OK");
		startActivityForResult(intent, 100);
	}

	protected void toMainMenu() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message",
				"Do you want to go to the main menu?\n(The data is not saved.)");
		startActivityForResult(intent, 101);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 101 && resultCode == RESULT_OK) {
			if (from.equals("town") || from.compareTo("town") == 0) {
				GBTown.returnToMain = true; /* CHEATERS */
			} else if (from.equals("shop") || from.compareTo("shop") == 0) {
				GBRestaurant.returnToMain = true;
				GBTown.returnToMain = true; /* CHEATERS */
			}
			finish();
		}
	}

}
