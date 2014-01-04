package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBGameData.GBDayState;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.Utils;

public class GBTown extends Activity {
	
	public static boolean returnToMain = false;
  
	protected GBGameData data;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_town);

        int id = getIntent().getIntExtra("button_id", R.id.buttonNewGame);
        initData(id);
        initButtons();
    }

	private void initButtons() {
		findViewById(R.id.buttonMenu).setOnClickListener(buttonListener);
		findViewById(R.id.buttonOutside).setOnClickListener(buttonListener);
		findViewById(R.id.buttonMarket).setOnClickListener(buttonListener);
		findViewById(R.id.buttonBulletin).setOnClickListener(buttonListener);
		findViewById(R.id.buttonShop).setOnClickListener(buttonListener);
		findViewById(R.id.buttonMayor).setOnClickListener(buttonListener);
		findViewById(R.id.buttonGuild).setOnClickListener(buttonListener);
	}
	
	private OnClickListener buttonListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.buttonMenu:
				startActivity(new Intent(GBTown.this, GBInGameMenu.class));
				break;
			case R.id.buttonOutside:
				toOutside();
				break;
			case R.id.buttonMarket:
				toMarket();
				break;
			case R.id.buttonShop:
				toShop();
				break;
			case R.id.buttonMayor:
				toMayor();
				break;
			case R.id.buttonGuild:
				toGuild();
				break;
			case R.id.buttonBulletin:
				break;
			}
		}
	};
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(GBTown.this, GBInGameMenu.class));
	}
	
	protected void toGuild() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Sorry, the guild isn't open. Come back again some other time.");
		intent.putExtra("one_button", true);
		intent.putExtra("btn_1", "OK");
		startActivity(intent);
	}

	protected void toMayor() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Sorry, the mayor doesn't want to be disturbed right now.");
		intent.putExtra("one_button", true);
		intent.putExtra("btn_1", "OK");
		startActivity(intent);
	}

	protected void toMarket() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Do you want to visit the market to buy or sell ingredients?");
		startActivityForResult(intent, 100);
	}

	protected void toOutside() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Do you want to go outside town and hunt for ingredients?");
		startActivityForResult(intent, 101);
	}

	protected void toShop() {
		Intent intent = new Intent(this, GBPopConfirm.class);
		intent.putExtra("message", "Do you want to open your shop?");
		startActivityForResult(intent, 102);
	}

	private void initData(int id) {
		if (id == R.id.buttonNewGame) {
			Toast.makeText(this, "New Game", Toast.LENGTH_SHORT).show();
			GBDataManager.createData();
			data = GBDataManager.getGameData();
			updateData();
		} else if (id == R.id.buttonContinue) {
			Toast.makeText(this, "Continue", Toast.LENGTH_SHORT).show();
			// do nothing
			GBDataManager.loadData();
			data = GBDataManager.getGameData();
			updateData();
		} else {
			Toast.makeText(this, "FATAL!", Toast.LENGTH_SHORT).show();
			// fatal error
		}
	}

	private void updateData() {
		((TextView) findViewById(R.id.textGold)).setText(Utils.formatNum(data.getCurrentGold(), "#,###,###"));
		((TextView) findViewById(R.id.textRatings)).setText(String.valueOf(data.getCurrentRating()));
		((TextView) findViewById(R.id.textDay)).setText(Utils.formatDate(data.getCurrentDay(), data.getCurrentMonth(), data.getCurrentYear()));
		((ImageView) findViewById(R.id.imageDayState)).setImageResource(formatDayState(data.getDayState()));
	}

	private int formatDayState(GBDayState dayState) {
		if (dayState == GBDayState.AFTERNOON) {
			return R.drawable.daystate_1;
		} else if (dayState == GBDayState.EVENING) {
			return R.drawable.daystate_2;
		}
		return R.drawable.daystate_0;
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		/* CHEAT! */
		if (returnToMain) {
			GBTown.returnToMain = false;
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 100 && resultCode == RESULT_OK) {
			startActivityForResult(new Intent(this, GBMarket.class), 201);
		} else if (requestCode == 101 && resultCode == RESULT_OK) {
			startActivityForResult(new Intent(this, GBOutside.class), 202);
		} else if (requestCode == 102 && resultCode == RESULT_OK) {
			startActivityForResult(new Intent(this, GBShop.class), 203);
		}
		
		if (requestCode == 201 || requestCode == 202 || requestCode == 203) {
			this.data.update();
			updateData();
		}
	}
}