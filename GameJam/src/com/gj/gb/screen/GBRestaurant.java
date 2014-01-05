package com.gj.gb.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.util.Utils;

public class GBRestaurant extends Activity implements Runnable, Handler.Callback {

	public static final int HANDLE_MESSAGE_START_SHOP = 500;
	public static final int HANDLE_MESSAGE_SHOP_READY = 501;
	public static final int HANDLE_MESSAGE_SHOP_READY_OUT = 502;
	public static final int HANDLE_MESSAGE_SHOP_GO = 503;
	public static final int HANDLE_MESSAGE_SHOP_OPEN = 504;
	public static final int HANDLE_MESSAGE_SHOP_CLOSE = 505;
	
	public static final int REQUEST_CLOSE = 1000;
	public static final int REQUEST_MENU = 1001;
	
	private Thread thread = null;
	private Handler handler = null;
	private boolean isRunning = false;

	protected Animation fadeInAnim, fadeOutAnim;
	private boolean enableBack = false;
	private boolean isSuspended = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_shop_alt);
		
		initAnimations();
		initButton();
		
		handler = new Handler(this);
		handler.sendEmptyMessage(HANDLE_MESSAGE_START_SHOP);
	}

	@Override
	public void onBackPressed() {
		if (enableBack) {
			isSuspended = true;
			Intent intent = new Intent(GBRestaurant.this, GBInGameMenu.class);
			intent.putExtra("from", "shop");
			startActivityForResult(intent, 2000);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CLOSE && resultCode == RESULT_OK) {
			finish();
		} else if (requestCode == 2000) {
			isSuspended = false;
		}
	}

	/* Thread */
	@Override
	public void run() {
		isRunning = true;
		
		initializeData();
		
		while (isRunning) {
			if (!isSuspended) {
				updateData();
				updateView();
			}
		}
	}

	private void updateView() {
		
	}

	private void updateData() {
		
	}

	private void initializeData() {
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		isSuspended = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		isRunning = false;
		
		while (true) {
			try {
				thread.join();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		cleanup();
	}

	// linis linis din pag.may-time
	protected void cleanup() {
		
	}

	@Override
	public boolean handleMessage(Message msg) {
		int what = msg.what;
		
		TextView text1, text2;
		
		switch (what) {
			case HANDLE_MESSAGE_START_SHOP:
				text1 = (TextView) findViewById(R.id.textReady);
				text1.setVisibility(View.VISIBLE);
				fadeInAnim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						handler.sendEmptyMessageDelayed(HANDLE_MESSAGE_SHOP_READY, 500);
					}
				});
				text1.startAnimation(fadeInAnim);
				break;
			case HANDLE_MESSAGE_SHOP_READY:
				text1 = (TextView) findViewById(R.id.textReady);
				text1.setVisibility(View.VISIBLE);
				fadeInAnim.setAnimationListener(null);
				fadeOutAnim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						findViewById(R.id.textReady).setVisibility(View.INVISIBLE);
						handler.sendEmptyMessage(HANDLE_MESSAGE_SHOP_READY_OUT);
					}
				});
				text1.startAnimation(fadeOutAnim);
				break;
			case HANDLE_MESSAGE_SHOP_READY_OUT:
				text2 = (TextView) findViewById(R.id.textGo);
				text2.setVisibility(View.VISIBLE);
				fadeOutAnim.setAnimationListener(null);
				fadeInAnim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						handler.sendEmptyMessageDelayed(HANDLE_MESSAGE_SHOP_GO, 500);
					}
				});
				text2.startAnimation(fadeInAnim);
				break;
			case HANDLE_MESSAGE_SHOP_GO:
				text2 = (TextView) findViewById(R.id.textGo);
				text2.setVisibility(View.VISIBLE);
				fadeInAnim.setAnimationListener(null);
				fadeOutAnim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						findViewById(R.id.textGo).setVisibility(View.INVISIBLE);
						handler.sendEmptyMessage(HANDLE_MESSAGE_SHOP_OPEN);
					}
				});
				text2.startAnimation(fadeOutAnim);
				break;
			case HANDLE_MESSAGE_SHOP_OPEN:
				fadeOutAnim.setAnimationListener(null);

				findViewById(R.id.filter1).setVisibility(View.GONE);
				findViewById(R.id.filter2).setVisibility(View.GONE);

				findViewById(R.id.buttonMenu).setEnabled(true);
				findViewById(R.id.buttonKitchen).setEnabled(true);

				enableBack = true;

				startGame();
				break;
			case HANDLE_MESSAGE_SHOP_CLOSE:
				enableBack = false;
				findViewById(R.id.filter1).setVisibility(View.VISIBLE);
				findViewById(R.id.filter2).setVisibility(View.VISIBLE);

				TextView text3 = (TextView) findViewById(R.id.textClose);
				text3.setVisibility(View.VISIBLE);
				fadeInAnim.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						enableBack = true;
						toClosePopup();
					}
				});
				text3.startAnimation(fadeInAnim);
				findViewById(R.id.buttonMenu).setEnabled(false);
				findViewById(R.id.buttonKitchen).setEnabled(false);
				break;
		}
		
		return true;
	}

	private void initAnimations() {
		fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
	}

	/* initialize buttons */
	private void initButton() {
		findViewById(R.id.buttonKitchen).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = Utils.getIntent(GBRestaurant.this, GBKitchen.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.buttonMenu).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
	
	private void startGame() {
		thread = new Thread(this);
		thread.start();
	}

	protected void toClosePopup() {
		Intent intent = new Intent(this, GBShopPopClose.class);
//		intent.putExtra("gold_earned", totalAmountEarned);
//		intent.putExtra("total_customer", customers.size());
//		intent.putExtra("customer_served", totalCustomerServed);
//		intent.putExtra("experience_gained", experienceEarned);
//		intent.putExtra("ratings_earned", totalRatingsEarned);
		startActivityForResult(intent, REQUEST_CLOSE);
	}
}
