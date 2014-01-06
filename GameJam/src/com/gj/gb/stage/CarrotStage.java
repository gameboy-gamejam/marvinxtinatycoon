package com.gj.gb.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.gj.gb.R;
import com.gj.gb.model.GBIngredient.IngredientCategory;
import com.gj.gb.popup.GBGameTipPopUp;
import com.gj.gb.popup.GBMiniGameRewardPopop;
import com.gj.gb.stage.actors.Carrot;
import com.gj.gb.stage.common.Stage;
import com.gj.gb.stage.common.StageHelper;

public class CarrotStage extends Stage {

    private static final int CARROT_NUMBER_OF_SOIL = 9;
    private static final int POINTS_EARNED_PER_CARROT = 15;
	private static final int DEFAULT_CARROT_GAME_TIMER_MS = 40000;
	private static final int DEFAULT_SWIPE_DISTANCE_PX = 100;
	private static final int INITIAL_HEIGHT_RED_TIMER = 482;
	
	private SurfaceHolder mSurfaceHolder;
	private Handler mDirector;
    private Runnable mScript;
    private Random mSeeder;
    //timer
    private Paint mTimerBackground;
    private Paint mTimerLabelBackground;
    private Paint mTimerLabel;
    private Paint mTimer;
	
	//defines the border line of the crops
	private float mLeftBorderCanvasPos;
	private float mRightBorderCanvasPos;
	private float mTopBorderCanvasPos;
	private float mBottomBorderCanvasPos;
	
	private List<Carrot> carrots;
	private SparseArray<Carrot> touchPoints;//TODO may mas maganda pa kaya kesa sa sparse
		
	private long mRecordTimeStarted = -1;
	private long mEstimateTimeFinish;
	private boolean mIsScriptRunning;
	private boolean mIsSurfaceReady = false;
	private Resources res;
	
	private int mPointsEarned = 0;
	
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
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == GBMiniGameRewardPopop.REQUEST_CODE_REWARD || requestCode == REQUEST_CODE_GAME_START){
    		if(resultCode == RESULT_OK && mIsSurfaceReady){
    			//showReadyInstruction();
    			playGame();
    		} else {
    			finish();
    		}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mIsScriptRunning = false;
	}
	
	@Override
	protected void playGame() {
		mRecordTimeStarted = System.currentTimeMillis();
		mEstimateTimeFinish = mRecordTimeStarted + DEFAULT_CARROT_GAME_TIMER_MS;
		mIsScriptRunning = true;
		mPointsEarned = 0;
		touchPoints = new SparseArray<Carrot>();
		for(Carrot carrot: carrots){
			carrot.reset();
		}
		mDirector.postDelayed(mScript, FRAMEDELAY);
	}
	
	@Override
    protected void resumeGame() {
        mIsScriptRunning = true;   
    }
	
	
	//methods called when game has ended
	@Override
	protected void endGame() {
	    mIsScriptRunning = false;
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

	
	//methods called for pop ups
	@Override
	protected void showReadyInstruction() {
		Intent intent = new Intent(this, GBGameTipPopUp.class);
		intent.putExtra(GBGameTipPopUp.KEY_EXTRA_TIP, res.getString(R.string.tip_carrot));
		startActivityForResult(intent, REQUEST_CODE_GAME_START);
		mIsShowReadyInstruction = false;
	}

	@Override
	protected void showInGameMenu() {
		//Do nothing
	}

	@Override
    protected void showPointsAndReward() {
    	Intent intent = new Intent(CarrotStage.this, GBMiniGameRewardPopop.class);
    	intent.putExtra(GBMiniGameRewardPopop.KEY_EXTRA_POINTS, mPointsEarned);
    	intent.putExtra(GBMiniGameRewardPopop.KEY_EXTRA_CATEGORY, IngredientCategory.CROPS);
    	startActivityForResult(intent, GBMiniGameRewardPopop.REQUEST_CODE_REWARD); 
    }
	
	
	//methods for surface view
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mIsSurfaceReady = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
		//Do nothing
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mIsScriptRunning = false;	
	}
	
	//other methods
	private void prepareScript(){
        mScript = new Runnable() {
            @Override
            public void run() {
            	long currentTime = System.currentTimeMillis();
                if(mEstimateTimeFinish < currentTime) {
                    mIsScriptRunning = false;
                }
                if(mIsScriptRunning) {
                    if(mSurfaceHolder.getSurface().isValid()){
                        Canvas canvas = mSurfaceHolder.lockCanvas();
                        synchronized (mSurfaceHolder) {
                            if(canvas != null) {
                                float timerRate = (float) ((currentTime - mRecordTimeStarted)/(DEFAULT_CARROT_GAME_TIMER_MS*1.0));
                                float currentHeightTimer = INITIAL_HEIGHT_RED_TIMER*timerRate;
                                canvas.drawRect(653, 50, 900, 100, mTimerLabelBackground);
                                canvas.drawText(res.getString(R.string.score)+" "+String.valueOf(mPointsEarned), 703, 80, mTimerLabel);
                                canvas.drawRect(703, 89, 853, 593, mTimerBackground);
                                canvas.drawRect(713, 99+currentHeightTimer, 843, 581, mTimer);
                                int oddChecker = mSeeder.nextInt(50);
                                if(oddChecker%2 != 0){//true if seeding
                                    int soil = mSeeder.nextInt(CARROT_NUMBER_OF_SOIL);
                                    Carrot carrot = carrots.get(soil);
                                    if(carrot.getState() == Carrot.STATE_EMPTY){
                                        carrot.setState(Carrot.STATE_SEEDED);
                                        carrot.setTimeSeeded(currentTime);
                                    }
                                }
                                for(Carrot carrot: carrots){
                                    carrot.drawMe(canvas, res, currentTime);
                                }
                                mSurfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                    mDirector.postDelayed(mScript, FRAMEDELAY);
                } else {
                	showPointsAndReward();
                	mDirector.removeCallbacks(mScript);
                }
            }
        };
    }
    	
	private void prepareFloorDirectors(){
		mDirector = new Handler();
        mOnTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int pointerIdx  = event.getActionIndex();
                int pointerId   = event.getPointerId(pointerIdx);
                int maskedAction= event.getActionMasked();
                
                switch (maskedAction) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        float touchPosX = event.getX(pointerIdx);
                        float touchPosY = event.getY(pointerIdx);
                        if(StageHelper.isWithinBorders(mLeftBorderCanvasPos, mRightBorderCanvasPos, 
                                mTopBorderCanvasPos, mBottomBorderCanvasPos, touchPosX, touchPosY)){
                            for(Carrot carrot: carrots){
                                if(carrot.isHit(touchPosX, touchPosY)){
                                    carrot.setState(Carrot.STATE_SELECTED);
                                    carrot.setRecordedTouchPtY(touchPosY);
                                    touchPoints.put(pointerId, carrot);
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Carrot carrotOnMove = touchPoints.get(pointerId);
                        if(carrotOnMove != null) {
                            float recordedPtY = carrotOnMove.getRecordedTouchPtY();
                            float distance = recordedPtY - event.getY(pointerIdx);
                            if(distance >= DEFAULT_SWIPE_DISTANCE_PX/* || carrotOnMove.getState() == Carrot.STATE_HARVESTED*/) {
                                mPointsEarned += POINTS_EARNED_PER_CARROT;
                                carrotOnMove.setState(Carrot.STATE_HARVESTED);
                                carrotOnMove.setRecordedTouchPtY(-1);
                                touchPoints.remove(pointerId);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        Carrot carrotUp = touchPoints.get(pointerId);
                        if(carrotUp != null){
                            carrotUp.setState(Carrot.STATE_UNSELECTED);
                            touchPoints.remove(pointerId);
                        }
                        return false;
                }
                return true;
            }
        };
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.gameCanvas);
        surfaceView.setOnTouchListener(mOnTouchListener);
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSeeder = new Random(System.currentTimeMillis());
    }
    
    private void prepareStage(){
        //stage
        res = getResources();
        mLeftBorderCanvasPos = 50;
        mRightBorderCanvasPos = 653;
        mTopBorderCanvasPos = 50;
        mBottomBorderCanvasPos = 593;
        mTimerBackground = new Paint();
        mTimerBackground.setColor(res.getColor(R.color.white));
        mTimerBackground.setStyle(Style.FILL);
        mTimerLabelBackground = new Paint();
        mTimerLabelBackground.setColor(res.getColor(R.color.black));
        mTimerLabelBackground.setStyle(Style.FILL);
        mTimerLabel = new Paint();
        mTimerLabel.setColor(res.getColor(R.color.yellow_mild));
        mTimerLabel.setTextSize(30);
        mTimer = new Paint();
        mTimer.setColor(res.getColor(R.color.red));
        mTimer.setStyle(Style.FILL);
        //actors
        carrots = new ArrayList<Carrot>();
        Carrot carrot = new Carrot(50, 50, 251 , 231);
        carrots.add(carrot);
        carrot = new Carrot(50, 231, 251 , 412);
        carrots.add(carrot);
        carrot = new Carrot(50, 412, 251 , 593);
        carrots.add(carrot);
        carrot = new Carrot(251, 50, 452 , 231);
        carrots.add(carrot);
        carrot = new Carrot(251, 231, 452 , 412);
        carrots.add(carrot);
        carrot = new Carrot(251, 412, 452 , 593);
        carrots.add(carrot);
        carrot = new Carrot(452, 50, 653 , 231);
        carrots.add(carrot);
        carrot = new Carrot(452, 231, 653 , 412);
        carrots.add(carrot);
        carrot = new Carrot(452, 412, 653 , 593);
        carrots.add(carrot);
        
    }
}
