package com.gj.gb.screen;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gj.gb.R;
import com.gj.gb.factory.GBCustomerFactory;
import com.gj.gb.logic.GBEconomics;
import com.gj.gb.model.GBCounter;
import com.gj.gb.model.GBCustomer;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBRecipe;
import com.gj.gb.util.GBDataManager;
import com.gj.gb.util.Utils;

public class GBShop extends Activity implements Runnable, Handler.Callback {

	public static boolean returnToMain = false;

	protected Thread thread = null;
	protected Handler handler = null;

	protected Animation fadeInAnim, fadeOutAnim;

	protected boolean enableBack = false;
	protected boolean isRunning = false;

	protected List<GBCustomer> customers;
	protected List<GBRecipe> recipes;
	protected GBGameData data;
	
	protected List<Integer> onCounter;
	protected List<Integer> onQueue;
	
	protected GBCounter counter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scene_shop_alt);

		initButton();
		initAnimations();
		initData();

		(handler = new Handler(this)).sendEmptyMessage(1000);
	}

	private void initData() {
		data = GBDataManager.getGameData();
		
		customers = GBCustomerFactory.getCustomerList(40 + data.getLevel(), GBEconomics.getDayCustomerCount(data.getCurrentRating()));
		for (int i=0; i<customers.size(); i++) {
			GBCustomer customer = customers.get(i);
			Log.w("test", "Customer Info: " + i + " Arrive Time: " + customer.getArriveTime() + ", Decide Time: " + customer.getDecideTime());
		}
		recipes = data.getRecipes();
		
		onCounter = new ArrayList<Integer>();
		onQueue = new ArrayList<Integer>();
		
		counter = new GBCounter(this);
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
						Intent intent = Utils.getIntent(GBShop.this, GBKitchen.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.buttonMenu).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GBShop.this, GBInGameMenu.class);
				intent.putExtra("from", "shop");
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		/* CHEAT! */
		if (returnToMain) {
			returnToMain = false;
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		if (enableBack) {
			Intent intent = new Intent(GBShop.this, GBInGameMenu.class);
			intent.putExtra("from", "shop");
			startActivity(intent);
		}
	}

	@Override
	public void run() {
		ProgressBar timer = (ProgressBar) findViewById(R.id.progressGameTime);

		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		
		while (isRunning) {
			long elapsedTime = System.currentTimeMillis() - currentTime;
			currentTime += elapsedTime;

			int timeProgress = timer.getProgress();
			updateGame(60 - (timeProgress/1000), elapsedTime);
			
			timeProgress -= elapsedTime;
			
			timer.setProgress(timeProgress);
			
			if (timeProgress <= 0) {
				stopGame();
			}
		}
	}

	private void updateGame(int time, long elapse) {
		int n = customers.size();

		for (int i=0; i<n; i++) {
			GBCustomer customer = customers.get(i);
			
			customer.update(time);
			
			if (customer.hasArrived()) {
				boolean counter = onCounter.contains(customer.getId());
				boolean queue = onQueue.contains(customer.getId());
				
				if (customer.isDisappointed()) {
					if (counter) {
						onCounter.remove(Integer.valueOf(customer.getId()));
					} else {
						continue;
					}
				}
				
				// kapag yung customer ay wala sa counter
				if (!counter) {
					// kapag may free sa counter
					if (onCounter.size() < 3) {
						addToCounter(customer);
						if (queue) {
							onQueue.remove(Integer.valueOf(customer.getId()));
							runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									if (onQueue.size() <= 0) {
										((TextView) findViewById(R.id.textPendingCustomer)).setVisibility(View.INVISIBLE);
									} else {
										((TextView) findViewById(R.id.textPendingCustomer)).setText(onQueue.size() + " customer(s) are waiting...");
									}
								}
							});
						}
					} else {
						// kapag wala sa queue
						if (!queue) {
							addToQueue(customer);
						}
					}
				}
			}

			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					counter.check();	
				}
			});
		}
	}

	private void addToQueue(final GBCustomer customer) {
		onQueue.add(customer.getId());
		Log.w("test", "Customer " + customer.getId() + " is waiting for the counter to be cleared.");
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				((TextView) findViewById(R.id.textPendingCustomer)).setText(onQueue.size() + " customer(s) are waiting...");
				((TextView) findViewById(R.id.textPendingCustomer)).setVisibility(View.VISIBLE);
			}
		});
	}

	private void addToCounter(final GBCustomer customer) {
		onCounter.add(customer.getId());
		Log.w("test", "Customer " + customer.getId() + " is on the counter.");
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				counter.addCustomer(customer);	
			}
		});
	}

	private void stopGame() {
		isRunning = false;
		handler.sendEmptyMessage(1005);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		while (true) {
			try {
				thread.join();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		thread = null;
	}

	@Override
	public boolean handleMessage(Message msg) {
		int what = msg.what;

		if (what == 1000) {
			TextView text1 = (TextView) findViewById(R.id.textReady);
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
					handler.sendEmptyMessageDelayed(1001, 500);
				}
			});
			text1.startAnimation(fadeInAnim);
		} else if (what == 1001) {
			TextView text1 = (TextView) findViewById(R.id.textReady);
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
					handler.sendEmptyMessage(1002);
				}
			});
			text1.startAnimation(fadeOutAnim);
		} else if (what == 1002) {
			TextView text2 = (TextView) findViewById(R.id.textGo);
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
					handler.sendEmptyMessageDelayed(1003, 500);
				}
			});
			text2.startAnimation(fadeInAnim);
		} else if (what == 1003) {
			TextView text2 = (TextView) findViewById(R.id.textGo);
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
					handler.sendEmptyMessage(1004);
				}
			});
			text2.startAnimation(fadeOutAnim);
		} else if (what == 1004) {
			fadeOutAnim.setAnimationListener(null);

			findViewById(R.id.filter1).setVisibility(View.GONE);
			findViewById(R.id.filter2).setVisibility(View.GONE);

			findViewById(R.id.buttonMenu).setEnabled(true);
			findViewById(R.id.buttonKitchen).setEnabled(true);

			enableBack = true;

			startGame();
		} else if (what == 1005) {
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
				}
			});
			text3.startAnimation(fadeInAnim);
		}

		return true;
	}

	private void startGame() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

}
