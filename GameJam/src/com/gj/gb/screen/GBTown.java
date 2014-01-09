package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBGameData.GBDayState;
import com.gj.gb.popup.GBDaySummaryPop;
import com.gj.gb.popup.GBLevelUpPop;
import com.gj.gb.popup.GBPopConfirm;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.Utils;

public class GBTown extends Activity {
	
	public static boolean returnToMain = false;
  
	protected GBGameData data;
	public static boolean shopFlag;
	
	private int currentHouse = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scene_town_alt);
        shopFlag = false;
        int id = getIntent().getIntExtra("button_id", R.id.buttonNewGame);
        initData(id);
        initView();
    }

	private void initView() {
		findViewById(R.id.buttonLeft).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentHouse--;
				updateHouse();
			}
		});
		
		findViewById(R.id.buttonRight).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				currentHouse++;
				updateHouse();
			}
		});
		
		findViewById(R.id.buttonGo).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (currentHouse == 0) {
					toMarket();
				} else if (currentHouse == 1) {
					toShop();
				} else {
					toOutside();
				}
			}
		});
	}

	protected void updateHouse() {
		if (currentHouse == 0) {
			findViewById(R.id.buttonLeft).setEnabled(false);
		} else if (currentHouse == 2) {
			findViewById(R.id.buttonRight).setEnabled(false);
		} else {
			findViewById(R.id.buttonLeft).setEnabled(true);
			findViewById(R.id.buttonRight).setEnabled(true);
		}
		
		if (currentHouse == 0) {
			((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.market);
		} else if (currentHouse == 1) {
			((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.shop);
		} else {
			((ImageView) findViewById(R.id.imageView1)).setImageResource(R.drawable.outside);
		}
	}

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
			GBDataManager.createData();
			data = GBDataManager.getGameData();
			updateData();
		} else if (id == R.id.buttonContinue) {
			// do nothing
			GBDataManager.loadData();
			data = GBDataManager.getGameData();
			updateData();
		} else {
			// fatal error
		}
	}

	private void updateData() {
		data.refreshIngredients();
		
		((TextView) findViewById(R.id.textGold)).setText(Utils.formatNum(data.getCurrentGold(), "#,###,###"));
		((TextView) findViewById(R.id.textRating)).setText(String.valueOf(data.getCurrentRating()));
		((TextView) findViewById(R.id.textCustomers)).setText(String.valueOf(data.getTotalCustomers()));
		((TextView) findViewById(R.id.textLevel)).setText("LEVEL " + data.getLevel());
		((TextView) findViewById(R.id.textGourmetPoints)).setText(String.valueOf(data.getExperience()));
		((TextView) findViewById(R.id.textDay)).setText("DAY " + data.getTotalDay()+1);
//		((ImageView) findViewById(R.id.imageDayState)).setImageResource(formatDayState(data.getDayState()));
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
		//oh no its a cheat
		if(shopFlag){
			shopFlag = false;
			this.data.update();
			updateData();
		}

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
			startActivityForResult(new Intent(this, GBRestaurant.class), 203);
		}
		
		if (requestCode == 201 || requestCode == 202 || requestCode == 203) {
			this.data.clearMenu();
			if(this.data.update()) {
				// TODO: call day summary first
				toDaySummary();
			}
			updateData();
			this.data.recoverStamina(1);
			if (this.data.hasLevel()) {
				toLevelUp();
			}
		}
		
		if (requestCode == 300) {
			updateData();
		} else if (requestCode == 400) {
			this.data.updateDay();
			this.data.recoverStamina(5);
			GBEconomics.update();
			updateData();
		}
	}

	private void toDaySummary() {
		startActivityForResult(new Intent(this, GBDaySummaryPop.class), 400);
	}

	private void toLevelUp() {
		startActivityForResult(new Intent(this, GBLevelUpPop.class), 300);
	}
}