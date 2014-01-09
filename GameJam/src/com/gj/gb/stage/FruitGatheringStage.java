package com.gj.gb.stage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.gj.gb.R;
import com.gj.gb.model.GBGameData;
import com.gj.gb.model.GBIngredient.IngredientCategory;
import com.gj.gb.popup.GBGameTipPopUp;
import com.gj.gb.popup.GBMiniGameRewardPopop;
import com.gj.gb.stage.actors.Tree;
import com.gj.gb.stage.common.Stage;
import com.gj.gb.util.GBDataManager;

public class FruitGatheringStage extends Stage{
	
	private SurfaceHolder mSurfaceHolder;
    private Handler mDirector;
    private Runnable mScript;
    
    private boolean mIsScriptRunning;
    private Resources res;
    private Tree tree;
    
    private int mPointsEarned = 0;
    private long mRecordTimeStarted = -1;
    private boolean mIsSurfaceReady = false;
    
    private Paint white;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.stage);
    	mIsShowReadyInstruction = true;
    	prepareScript();
    	prepareFloorDirectors();
    	prepareStage();
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		if(mIsShowReadyInstruction){
			showReadyInstruction();
		}
	}
    
    @Override
    protected void onPause() {
    	boolean isScreenOn = ((PowerManager) this.getApplicationContext()
				.getSystemService(Context.POWER_SERVICE)).isScreenOn();
		if (!isScreenOn) {
			mIsScriptRunning = false;
			mDirector.removeCallbacks(mScript);
		}
    	super.onPause();
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == GBMiniGameRewardPopop.REQUEST_CODE_REWARD || requestCode == REQUEST_CODE_GAME_START){
    		if(resultCode == RESULT_OK && mIsSurfaceReady){
    			GBGameData gbData = GBDataManager.getGameData();
    			if(gbData.getStamina() > 0){
    				gbData.useStamina();
    				playGame();
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
    
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		mIsSurfaceReady = true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		//Do nothing
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mIsScriptRunning = false;
		mDirector.removeCallbacks(mScript);
	}

	@Override
	protected void playGame() {
		mRecordTimeStarted = System.currentTimeMillis();
		mIsScriptRunning = true;
		mPointsEarned = 0;
		//tree.setTimeTreeLived(mRecordTimeStarted);
		tree.reset();
		mDirector.postDelayed(mScript, 70);
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
	protected void releaseResources() {
		if(mSurfaceHolder != null){
            mSurfaceHolder = null;
        }
        if(mDirector != null){
            mDirector.removeCallbacks(mScript);
            mDirector = null;
        }
	}

	//methods for pop ups
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
    	Intent intent = new Intent(FruitGatheringStage.this, GBMiniGameRewardPopop.class);
    	intent.putExtra(GBMiniGameRewardPopop.KEY_EXTRA_POINTS, mPointsEarned);
    	intent.putExtra(GBMiniGameRewardPopop.KEY_EXTRA_CATEGORY, IngredientCategory.FRUIT);
    	startActivityForResult(intent, GBMiniGameRewardPopop.REQUEST_CODE_REWARD); 
    }
    
    //other methods
    private void prepareScript(){
        mScript = new Runnable() {
            @Override
            public void run() {
                if(mIsScriptRunning) {
                	/*if(tree.getState() == Tree.STATE_DIED){
                		mIsScriptRunning = false;
                	}*/
                    if(mSurfaceHolder.getSurface().isValid()){
                    	long inGameCurrentTime = System.currentTimeMillis();
                        Canvas canvas = mSurfaceHolder.lockCanvas();
                        synchronized (mSurfaceHolder) {
                            if(canvas != null) {
                            	canvas.drawColor(0, Mode.CLEAR);//eraser
                            	canvas.drawRect(0, 0, 1000, 700, white);
                                //tree.drawMe(canvas, res, inGameCurrentTime);
                                mSurfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                    mDirector.postDelayed(mScript, 70);
                } else {
                	showPointsAndReward();
                	mDirector.removeCallbacks(mScript);
                }
            }
        };
    }
    
    private void prepareFloorDirectors(){
        mOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
            	int pointerIdx  = event.getActionIndex();
                int maskedAction= event.getActionMasked();
                switch (maskedAction) {
	                case MotionEvent.ACTION_DOWN:
	                case MotionEvent.ACTION_POINTER_DOWN:
	                	float touchPosX = event.getX(pointerIdx);
	                	float touchPosY = event.getY(pointerIdx);
	                    if(tree.isHit(touchPosX, touchPosY)){
	                    	tree.setShaking(true);
	                    	/*if(tree.getState() == Tree.STATE_YOUNG){
	                    		tree.abuseHealth();
	                    	} else if(tree.getState() == Tree.STATE_BEARING){
	                    		mPointsEarned+=15;
	                    	} else if(tree.getState() == Tree.STATE_OLD){
	                    		mPointsEarned+=5;
	                    	}*/
	                    }
	                	break;
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

    private void prepareStage() {
    	mIsScriptRunning = false;
    	res = getResources();
    	tree = new Tree(200, 50, 803, 593);
    	white = new Paint(); 
    	white.setStyle(Paint.Style.FILL);
    	white.setColor(Color.WHITE);
    }
}
