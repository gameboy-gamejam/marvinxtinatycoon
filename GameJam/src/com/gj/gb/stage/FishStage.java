package com.gj.gb.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.gj.gb.R;
import com.gj.gb.model.GBIngredient.IngredientCategory;
import com.gj.gb.popup.GBGameTipPopUp;
import com.gj.gb.popup.GBMiniGameRewardPopop;
import com.gj.gb.stage.actors.Fisher;
import com.gj.gb.stage.actors.FishingRodBar;
import com.gj.gb.stage.actors.Pond;
import com.gj.gb.stage.common.Stage;
import com.gj.gb.stage.common.StageHelper;

public class FishStage extends Stage {
    
    private SurfaceHolder mSurfaceHolder;
    private Handler mDirector;
    private Runnable mScript;
    private Random mSeeder;

    //defines the border line of the ponds
    private float mLeftBorderCanvasPos;
    private float mRightBorderCanvasPos;
    private float mTopBorderCanvasPos;
    private float mBottomBorderCanvasPos;
    
    private List<Pond> ponds;
    private int mSelectedPondIdx;
    private FishingRodBar mFishingRod;
    private Fisher fisher;
    
    private boolean mIsScriptRunning;
    private boolean mIsSurfaceReady;
    private Resources res;
    
    private int mPointsEarned = 0;
    private Paint white; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.stage_carrot);
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
    	} else {
	    	mDirector.postDelayed(new Runnable() {//lipat ng onactivity result
				
				@Override
				public void run() {
					if(mSurfaceHolder.getSurface().isValid()){
			            Canvas canvas = mSurfaceHolder.lockCanvas();
			            synchronized (mSurfaceHolder) {
			                if(canvas != null) {
			                	canvas.drawColor(0, Mode.CLEAR);//eraser
			                	canvas.drawRect(0, 0, 1000, 700, white);
			                	fisher.drawMe(canvas);
			                    for(Pond pond: ponds) {
			                    	pond.drawMe(canvas, res);
			                    }
			                    //TODO drawFisher
			                    mSurfaceHolder.unlockCanvasAndPost(canvas);
			                }
			            }
			            mDirector.removeCallbacks(this);
			        } else {
			        	mDirector.postDelayed(this, FRAMEDELAY);
			        }	
				}
			}, FRAMEDELAY);
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == GBMiniGameRewardPopop.REQUEST_CODE_REWARD || requestCode == REQUEST_CODE_GAME_START){
    		if(resultCode == RESULT_OK && mIsSurfaceReady){
    			fisher.setState(Fisher.STATE_ON_STANDBY);
    			mSelectedPondIdx = -1;
    		} else {
    			finish();
    		}
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    protected void playGame() {
    	mFishingRod.adjustDifficulty(mSeeder.nextInt(5), FRAMEDELAY);
    	mFishingRod.setCatchBarPosX(mSeeder.nextInt((int) mFishingRod.getMovableDistanceWidth()));
    	mFishingRod.setIsLakeWithFish(mSeeder.nextBoolean());
    	mFishingRod.setRarityOfFish(mSeeder.nextInt(FishingRodBar.RARITY_OF_FISH_MAX_PT));
    	fisher.setState(Fisher.STATE_IS_FISHING);
    	mIsScriptRunning = true;
    	mDirector.postDelayed(mScript, FRAMEDELAY);
    }
    
    private void prepareScript(){
        mScript = new Runnable() {
            @Override
            public void run() {
                if(mIsScriptRunning) {
                    if(mSurfaceHolder.getSurface().isValid()){
                        Canvas canvas = mSurfaceHolder.lockCanvas();
                        synchronized (mSurfaceHolder) {
                            if(canvas != null) {
                            	canvas.drawPaint(white);
                                mFishingRod.drawMe(canvas);
                                fisher.drawMe(canvas);
                                for(Pond pond: ponds) {
                                	pond.drawMe(canvas, res);
                                }
                                //TODO drawFisher
                                mSurfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                    mDirector.postDelayed(mScript, FRAMEDELAY);
                }
            }
        };
    }
    
    private void prepareFloorDirectors(){
        mOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action  = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    	if(mIsSurfaceReady){
	                    	float touchPosX = event.getX();
	                        float touchPosY = event.getY();
	                    	if(StageHelper.isWithinBorders(mLeftBorderCanvasPos, mRightBorderCanvasPos, 
	                                mTopBorderCanvasPos, mBottomBorderCanvasPos, touchPosX, touchPosY)) {
	                    		for(Pond pond: ponds) {
	                    			if(pond.isHit(touchPosX, touchPosY)) {
	                    				mSelectedPondIdx = ponds.indexOf(pond);
	                    				playGame();
	                    			}
	                    		}
                    		}
                    	}
                    	return true;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                    	if(mSelectedPondIdx != -1){
	                    	endGame();
	                    	showPointsAndReward();
                    	}
                    	break;
                }
                return false;
            }
        };
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.gameCanvas);
        surfaceView.setOnTouchListener(mOnTouchListener);
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSeeder = new Random(System.currentTimeMillis());
        mDirector = new Handler();
    }

    private void prepareStage() {
    	mSelectedPondIdx = -1;
    	mIsSurfaceReady = false;
    	mIsScriptRunning = false;
    	res = getResources();
    	mFishingRod = new FishingRodBar(getResources());
    	ponds = new ArrayList<Pond>();
    	Pond pond = new Pond(251, 200, 431, 371);
    	pond.setFishRarity(0);
    	ponds.add(pond);
    	pond = new Pond(50, 381, 230, 552);
    	pond.setFishRarity(1);
    	ponds.add(pond);
    	pond = new Pond(452, 381, 632, 552);
    	pond.setFishRarity(2);
    	ponds.add(pond);
    	fisher = new Fisher(640, 164, 940, 552, res);
    	mLeftBorderCanvasPos	= 50;
    	mTopBorderCanvasPos 	= 200;
    	mRightBorderCanvasPos	= 632;
    	mBottomBorderCanvasPos	= 552;
    	white = new Paint(); 
    	white.setStyle(Paint.Style.FILL);
    	white.setColor(Color.WHITE); 
    }
    
    @Override
    protected void resumeGame() {
        //Do nothing
    }
    
    //methods for SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	mIsSurfaceReady = true;
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        //Do nothing
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	mIsScriptRunning = false;
        mIsSurfaceReady = false;
    }

    //methods for ending the game
    @Override
    protected void endGame() {//pede kaya dito yung up?
    	if(mSelectedPondIdx > -1) {
	    	mIsGameFinish = true;
	    	mIsScriptRunning = false;//TODO mag-isip ng cases na dapat nag-end yung game
	    	mDirector.removeCallbacks(mScript);
	    	mPointsEarned = 0;
	    	if(mFishingRod.isHit()) {
	    		Pond pond = ponds.get(mSelectedPondIdx);
		        mPointsEarned = pond.getScore(mFishingRod.getScore());
	    	}
    	}
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
		intent.putExtra(GBGameTipPopUp.KEY_EXTRA_TIP, res.getString(R.string.tip_fishing));
		startActivityForResult(intent, REQUEST_CODE_GAME_START);
		mIsShowReadyInstruction = false;
	}

    @Override
    protected void showInGameMenu() {
        //Do nothing
    }

    @Override
    protected void showPointsAndReward() {
    	Intent intent = new Intent(FishStage.this, GBMiniGameRewardPopop.class);
    	intent.putExtra(GBMiniGameRewardPopop.KEY_EXTRA_POINTS, mPointsEarned);
    	intent.putExtra(GBMiniGameRewardPopop.KEY_EXTRA_CATEGORY, IngredientCategory.FISH);
    	startActivityForResult(intent, GBMiniGameRewardPopop.REQUEST_CODE_REWARD); 
    }
}
