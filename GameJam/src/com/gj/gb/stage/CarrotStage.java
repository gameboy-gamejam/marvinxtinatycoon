package com.gj.gb.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.gj.gb.R;
import com.gj.gb.stage.actors.Carrot;
import com.gj.gb.stage.common.Stage;
import com.gj.gb.stage.common.StageHelper;

public class CarrotStage extends Stage {

	private static final int DEFAULT_CARROT_GAME_TIMER_MS = 90000;
	private static final int CARROT_NUMBER_OF_SOIL = 9;
	private static final int DEFAULT_SWIPE_DISTANCE_PX = 100;
	private static final int FRAMEDELAY = 100;
	
	private SurfaceHolder mSurfaceHolder;
	
	//defines the border line of the crops
	private float mLeftBorderCanvasPos;
	private float mRightBorderCanvasPos;
	private float mTopBorderCanvasPos;
	private float mBottomBorderCanvasPos;
	
	private List<Carrot> carrots;
	private SparseArray<Carrot> touchPoints;//TODO may mas maganda pa kaya kesa sa sparse
	
	private long mRecordTimeStarted = -1;
	private long mEstimateTimeFinish;
	private Handler mDirector;
	private Runnable mScript;
	private Random mSeeder;
	private Resources res;
	private boolean mIsScriptRunning;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stage_carrot);
		
		mOnTouchListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				int pointerIdx 	= event.getActionIndex();
				int pointerId 	= event.getPointerId(pointerIdx);
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
							if(distance >= DEFAULT_SWIPE_DISTANCE_PX || carrotOnMove.getState() == Carrot.STATE_HARVESTED) {
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
		
		res = getResources();
		touchPoints = new SparseArray<Carrot>();
		mSeeder = new Random(System.currentTimeMillis());
		
		mScript = new Runnable() {
			@Override
			public void run() {
				if(mIsScriptRunning) {
					//TODO draw timer
					long currentTime = System.currentTimeMillis();
					if(mEstimateTimeFinish < currentTime) {
						mIsScriptRunning = false;
					}
					int oddChecker = mSeeder.nextInt(50);
					if(oddChecker%2 != 0){//true if seeding
						int soil = mSeeder.nextInt(CARROT_NUMBER_OF_SOIL);
						Carrot carrot = carrots.get(soil);
						if(carrot.getState() == Carrot.STATE_EMPTY){
							carrot.setState(Carrot.STATE_SEEDED);
							carrot.setTimeSeeded(currentTime);
						}
					}
					if(mSurfaceHolder.getSurface().isValid()){
						Canvas canvas = mSurfaceHolder.lockCanvas();
						synchronized (mSurfaceHolder) {
							if(canvas != null) {
								for(Carrot carrot: carrots){
									carrot.drawMe(canvas, res, currentTime);
								}
								mSurfaceHolder.unlockCanvasAndPost(canvas);
							}
						}
					}
					mDirector.postDelayed(mScript, FRAMEDELAY);
				} else {
					endGame();
				}
			}
		};
		mLeftBorderCanvasPos = 100;
		mRightBorderCanvasPos = 703;
		mTopBorderCanvasPos = 400;
		mBottomBorderCanvasPos = 943;
		carrots = new ArrayList<Carrot>();
		Carrot carrot = new Carrot(100, 400, 301 , 581);
		carrots.add(carrot);
		carrot = new Carrot(100, 581, 301 , 762);
		carrots.add(carrot);
		carrot = new Carrot(100, 762, 301 , 943);
		carrots.add(carrot);
		carrot = new Carrot(301, 400, 502 , 581);
		carrots.add(carrot);
		carrot = new Carrot(301, 581, 502 , 762);
		carrots.add(carrot);
		carrot = new Carrot(301, 762, 502 , 943);
		carrots.add(carrot);
		carrot = new Carrot(502, 400, 703 , 581);
		carrots.add(carrot);
		carrot = new Carrot(502, 581, 703 , 762);
		carrots.add(carrot);
		carrot = new Carrot(502, 762, 703 , 943);
		carrots.add(carrot);
		
		mSurfaceHolder = ((SurfaceView)findViewById(R.id.gameCanvas)).getHolder();
		mSurfaceHolder.addCallback(this);
	}

	@Override
	protected void playGame() {
		if(mRecordTimeStarted == -1) {
			mRecordTimeStarted = System.currentTimeMillis();
			mEstimateTimeFinish = mRecordTimeStarted + DEFAULT_CARROT_GAME_TIMER_MS; 
		}
		mIsScriptRunning = true;
		mDirector = new Handler();
		mDirector.postDelayed(mScript, FRAMEDELAY);
	}
	
	@Override
	protected void endGame() {
		showPointsAndReward();
		releaseResources();
	}

	@Override
	protected void showReadyInstruction() {
	}

	@Override
	protected void showInGameMenu() {
		mIsScriptRunning = false;
	}

	@Override
	protected void showPointsAndReward() {
	}

	@Override
	protected void releaseResources() {
		if(mSurfaceHolder != null){
			mSurfaceHolder = null;
		}
		mIsScriptRunning = false;
		if(mDirector != null){
			mDirector.removeCallbacks(mScript);
			mDirector = null;
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		playGame();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int frmt, int w, int h) {
		//do nothing
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mIsScriptRunning = false;	
	}
}
