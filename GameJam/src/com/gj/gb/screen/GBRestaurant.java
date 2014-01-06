package com.gj.gb.screen;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBNewCustomerFactory;
import com.gj.gb.gridview.ShopDishGridViewAdapter;
import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBNewCustomer;
import com.gj.gb.model.GBNewCustomer.GBCustomerState;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.screen.shop.GBQueueManager;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.ImageCache;
import com.gj.gb.util.Utils;

public class GBRestaurant extends Activity implements Runnable,
		Handler.Callback, SurfaceHolder.Callback {

	public static final int HANDLE_MESSAGE_START_SHOP = 500;
	public static final int HANDLE_MESSAGE_SHOP_READY = 501;
	public static final int HANDLE_MESSAGE_SHOP_READY_OUT = 502;
	public static final int HANDLE_MESSAGE_SHOP_GO = 503;
	public static final int HANDLE_MESSAGE_SHOP_OPEN = 504;
	public static final int HANDLE_MESSAGE_SHOP_CLOSE = 505;

	public static final int REQUEST_CLOSE = 1000;
	public static final int REQUEST_MENU = 1001;
	
	public static boolean returnToMain = false;

	private Thread thread = null;
	private Handler handler = null;
	private boolean isRunning = false;

	protected Animation fadeInAnim, fadeOutAnim;
	private boolean enableBack = false;
	private boolean isSuspended = false;

	private GBGameData gameData;
	private List<GBNewCustomer> customerList;
	private GBQueueManager queueManager;

	private int goldEarned = 0;
	private int experienceEarned = 0;
	private int ratingsEarned = 0;
	private int customerServed = 0;
	private int totalCustomer = 0;
	
	private SurfaceView shopSurface;
	private SurfaceHolder shopSurfaceHolder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_shop_alt);

		gameData = GBDataManager.getGameData();

		initAnimations();
		initViews();

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

	Bitmap bitmap = null;
	
	/* Thread */
	@Override
	public void run() {
		isRunning = true;

		initializeData();
		ProgressBar timer = (ProgressBar) findViewById(R.id.progressGameTime);

		long startTime = System.currentTimeMillis();
		long currentTime = startTime;

		bitmap = ImageCache.getBitmap(this, "ic_launcher");
		
		while (isRunning) {
			final long elapsedTime = System.currentTimeMillis() - currentTime;
			currentTime += elapsedTime;

			if (!isSuspended) {
				int timeProgress = timer.getProgress();

				updateData(elapsedTime);
				
				try {
					Canvas canvas = shopSurfaceHolder.lockCanvas();
					render(canvas);
					shopSurfaceHolder.unlockCanvasAndPost(canvas);
				} catch (IllegalArgumentException e) {
					
				}

				timeProgress -= elapsedTime;

				timer.setProgress(timeProgress);

				if (timeProgress <= 0) {
					stopGame();
				}
			}
		}
	}

	int x=0, y=0;
	
	private void render(Canvas canvas) {
		if (canvas != null) {
			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		}
	}

	private void stopGame() {
		isRunning = false;
		handler.sendEmptyMessage(HANDLE_MESSAGE_SHOP_CLOSE);
	}

	private void updateData(long elapsedTime) {
		int n = customerList.size();
		for (int i = 0; i < n; i++) {
			GBNewCustomer customer = customerList.get(i);
			customer.update(elapsedTime);

			if (customer.getState() == GBCustomerState.ARRIVED) {
				queueManager.addNewCustomer(customer);
			} else if (customer.getState() == GBCustomerState.RAGE_QUIT) {
				queueManager.removeCustomer(customer);
			} else if (customer.getState() == GBCustomerState.SERVED) {
				customerServed++;
				queueManager.removeCustomer(customer);
			}
		}

		List<GBNewCustomer> left = queueManager.getLeft();
		while (left.size() > 0) {
			customerList.remove(left.remove(0));
		}

		queueManager.update(elapsedTime);
	}

	private void initializeData() {
		customerList = GBNewCustomerFactory.getRandomCustomers(10);
		queueManager = new GBQueueManager(this);

		totalCustomer = customerList.size();
	}

	@Override
	protected void onResume() {
		super.onResume();

		isSuspended = false;

		/* CHEAT! */
		if (returnToMain) {
			GBRestaurant.returnToMain = false;
			finish();
		}

		refreshDishList();
	}

	private void refreshDishList() {
		GridView list = (GridView) findViewById(R.id.listReadyDish);
		list.setAdapter(new ShopDishGridViewAdapter(this, gameData
				.getReadyDish()));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				List<GBRecipe> ready = gameData.getReadyDish();
				GBRecipe recipe = ready.get(position);
				synchronized (queueManager) {
					if(queueManager.serve(recipe)) {
						goldEarned += GBEconomics.getRecipePrice(recipe);
						ready.remove(position);
						refreshDishList();
						((TextView) findViewById(R.id.textGoldEarn)).setText(goldEarned + "G");
					}
				}
			}
		});
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
					handler.sendEmptyMessageDelayed(HANDLE_MESSAGE_SHOP_READY,
							500);
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
	private void initViews() {
		shopSurface = (SurfaceView) findViewById(R.id.surfaceCanvas);
		shopSurface.setZOrderOnTop(true);
		shopSurfaceHolder = shopSurface.getHolder();
		shopSurfaceHolder.addCallback(this);
		shopSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
		
		findViewById(R.id.buttonKitchen).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = Utils.getIntent(GBRestaurant.this,
								GBKitchen.class);
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
		intent.putExtra("gold_earned", goldEarned);
		intent.putExtra("total_customer", totalCustomer);
		intent.putExtra("customer_served", customerServed);
		intent.putExtra("experience_gained", experienceEarned);
		intent.putExtra("ratings_earned", ratingsEarned);
		startActivityForResult(intent, REQUEST_CLOSE);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.w("test", "Changed!");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.w("test", "Created!");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.w("test", "Destroyed!");
	}
}
