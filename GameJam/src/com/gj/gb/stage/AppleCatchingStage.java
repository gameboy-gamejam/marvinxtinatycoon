package com.gj.gb.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBIngredient.IngredientCategory;
import com.gj.gb.popup.GBGameTipPopUp;
import com.gj.gb.popup.GBMiniGameRewardPopop;
import com.gj.gb.stage.actors.Apple;
import com.gj.gb.stage.actors.Basket;
import com.gj.gb.stage.actors.Tree;
import com.gj.gb.stage.common.Stage;
import com.gj.gb.util.GBDataManager;

public class AppleCatchingStage extends Stage implements SensorEventListener{
	
	private static final int MAX_TIME_FRUIT_FALL_FINISH = 10000;
	private static final int MAX_APPLE_PER_SCREEN = 2;
	
	private SurfaceHolder mSurfaceHolder;
    private Handler mDirector;
    private Runnable mScript;
    
    private Resources res;
    private boolean mIsScriptRunning;
	private boolean mIsInitializedSensor;
	private long mRecordedSeedTime;
	private long mEstimatedTimeFinish;
	private SensorManager mSensorManager;
	private OnTouchListener mOnTouchListener;
	
	private Random mSeeder;
	
	private boolean mIsFruitFalling;
	private boolean mIsSurfaceReady = false;
	
	private int mPointsEarned = 0;
	
	private Basket basket;
	private Tree tree;
	private List<Apple> apples;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSeeder = new Random(System.currentTimeMillis());
		apples = new ArrayList<Apple>();
		mIsShowReadyInstruction = true;
		prepareScript();
		prepareFloorDirectors();
		prepareStage();
	}
	
	private void reset(){
	    tree.reset();
	    initDrawTree();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(mIsShowReadyInstruction){
			showReadyInstruction();
			mIsShowReadyInstruction = false;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == GBMiniGameRewardPopop.REQUEST_CODE_REWARD || requestCode == REQUEST_CODE_GAME_START){
    		if(resultCode == RESULT_OK && mIsSurfaceReady){
    			GBGameData gbData = GBDataManager.getGameData();
    			if(gbData.getStamina() > 0){
    				gbData.useStamina();
    				reset();
    			} else {
    				showNotEnoughStaminaPopup();
    			}
    		} else {
    			finish();
    		}
		} else if(requestCode == REQUEST_CODE_NOT_ENOUGH_STAMINA){
			if(resultCode == RESULT_OK){
				finish();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void prepareScript(){
		mScript = new Runnable() {
			
			@Override
			public void run() {
				if(mIsScriptRunning){
					if(!tree.isShaking()){
						mIsFruitFalling = true;
						if(!mIsInitializedSensor){
							registerSensorListener();
							mIsInitializedSensor = true;
						}
					}
					if(mSurfaceHolder.getSurface().isValid()){
                    	long inGameCurrentTime = System.currentTimeMillis();
                        Canvas canvas = mSurfaceHolder.lockCanvas();
                        synchronized (mSurfaceHolder) {
                            if(canvas != null) {
                                tree.drawMe(canvas, res);
                                if(!tree.isShaking()){
                                	if(!mIsInitializedSensor){
                                		registerSensorListener();
                                		mIsInitializedSensor = true;
                                	}
	                                basket.drawMe(canvas);
	                                int seed = mSeeder.nextInt(100);
	                                if(apples.size() < MAX_APPLE_PER_SCREEN && seed%6 == 0 
	                                		&& inGameCurrentTime <= mEstimatedTimeFinish){
	                                    apples.add(new Apple(mSeeder.nextInt(553)+50, 50, 500));
	                                }
	                                List<Apple> spoiledApple = new ArrayList<Apple>();
	                                for(Apple apple: apples){
	                                	if(apple.isHitBasket(basket)){
	                                		mPointsEarned+=20;
	                                		spoiledApple.add(apple);
	                                	} else if(apple.isHitGround()){
	                                		spoiledApple.add(apple);
	                                	}
	                                	apple.drawMe(canvas, res);
	                                }
	                                apples.removeAll(spoiledApple);
	                                if(apples.size() == 0 && inGameCurrentTime > mEstimatedTimeFinish){
	                                    mIsFruitFalling = false;
	                                	mIsScriptRunning = false;
	                                	mDirector.removeCallbacks(mScript);
	                                	mSensorManager.unregisterListener(AppleCatchingStage.this);
	                                	mIsInitializedSensor = false;
	                                	if(tree.getTapIDx() >= Tree.MAX_NUMBER_TREE_TAP){
	                                		showPointsAndReward();
	                                	} else {
	                                	    tree.drawMe(canvas, res);
	                                	}
	                                }
                                }
                                mSurfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                    mDirector.postDelayed(mScript, FRAMEDELAY);
				}
			}
		};
	}
	
	private void registerSensorListener(){
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		        SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	private void prepareFloorDirectors(){
		mOnTouchListener = new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
                if(action == MotionEvent.ACTION_DOWN && !mIsFruitFalling &&
						!tree.isShaking()){
					playGame();
				}
				return true;
			}
		};
		SurfaceView surfaceView = (SurfaceView)findViewById(R.id.gameCanvas);
        surfaceView.setOnTouchListener(mOnTouchListener);
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mDirector = new Handler();
	}
	
	private void prepareStage(){
	    mIsFruitFalling = false;
	    res = getResources();
		basket = new Basket(150, 350, res);
		basket.setWallBorder(100, 553);
		tree = new Tree(50, 50, 553, 593);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		//Do nothing
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		mIsSurfaceReady = true;
	}
	
	private void initDrawTree(){
		if(mSurfaceHolder.getSurface().isValid()){
            Canvas canvas = mSurfaceHolder.lockCanvas();
            synchronized (mSurfaceHolder) {
                if(canvas != null) {
                    tree.drawMe(canvas, res);
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mIsScriptRunning = false;
		mDirector.removeCallbacks(mScript);
	}

	@Override
	protected void playGame() {
		tree.setShaking(true);
		tree.addTap();
		mRecordedSeedTime = System.currentTimeMillis();
		mEstimatedTimeFinish = mRecordedSeedTime + MAX_TIME_FRUIT_FALL_FINISH;
		mIsScriptRunning = true;
		mDirector.postDelayed(mScript, FRAMEDELAY);
	}

	@Override
	protected void resumeGame() {
		//Do nothing
	}

	@Override
	protected void endGame() {
		//Do nothing
	}

	@Override
	protected void showReadyInstruction() {
		Intent intent = new Intent(this, GBGameTipPopUp.class);
		intent.putExtra(GBGameTipPopUp.KEY_EXTRA_TIP, res.getString(R.string.tip_fruit));
		startActivityForResult(intent, REQUEST_CODE_GAME_START);
		mIsShowReadyInstruction = false;
	}

	@Override
	protected void showInGameMenu() {
		//Do nothing
	}

	@Override
    protected void showPointsAndReward() {
    	Intent intent = new Intent(AppleCatchingStage.this, GBMiniGameRewardPopop.class);
    	intent.putExtra(GBMiniGameRewardPopop.KEY_EXTRA_POINTS, mPointsEarned);
    	intent.putExtra(GBMiniGameRewardPopop.KEY_EXTRA_CATEGORY, IngredientCategory.FRUIT);
    	startActivityForResult(intent, GBMiniGameRewardPopop.REQUEST_CODE_REWARD);
    	mPointsEarned = 0;
    }

	@Override
	protected void releaseResources() {
		if(mSurfaceHolder != null){
            mSurfaceHolder = null;
        }
        if(mDirector != null){
            mDirector.removeCallbacks(mScript);
            mDirector = null;
        }
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//Do nothing
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			float[] values = event.values;
			basket.setTilt(values[1]);//x
		}
	}

}
