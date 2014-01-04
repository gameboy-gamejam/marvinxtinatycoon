package com.gj.gb.stage;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.gj.gb.R;
import com.gj.gb.stage.actors.Tree;
import com.gj.gb.stage.common.Stage;

public class FruitGatheringStage extends Stage{
	
	private SurfaceHolder mSurfaceHolder;
    private Handler mDirector;
    private Runnable mScript;
    
    private boolean mIsScriptRunning;
    private Resources res;
    private Tree tree;
    
    private int mPointsEarned = 0;
    private long mRecordTimeStarted = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.stage_carrot);
    	prepareScript();
    	prepareFloorDirectors();
    	prepareStage();
    }
    
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		playGame();
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		//Do nothing
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		mIsScriptRunning = false;		
	}

	@Override
	protected void playGame() {
		mRecordTimeStarted = System.currentTimeMillis();
		mIsScriptRunning = true;
		tree.setTimeTreeLived(mRecordTimeStarted);
		mDirector.postDelayed(mScript, 70);
	}

	@Override
	protected void resumeGame() {
		mIsScriptRunning = true;
	}

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

	//methods for pop ups
    @Override
    protected void showReadyInstruction() {
        // TODO show pop up
        
    }

    @Override
    protected void showInGameMenu() {
        //Do nothing
    }

    @Override
    protected void showPointsAndReward() {
        // TODO show pop up
        
    }
    
    //other methods
    private void prepareScript(){
        mScript = new Runnable() {
            @Override
            public void run() {
                if(mIsScriptRunning) {
                	if(tree.getState() == Tree.STATE_DIED){
                		mIsScriptRunning = false;
                	}
                    if(mSurfaceHolder.getSurface().isValid()){
                    	long inGameCurrentTime = System.currentTimeMillis();
                        Canvas canvas = mSurfaceHolder.lockCanvas();
                        synchronized (mSurfaceHolder) {
                            if(canvas != null) {
                            	canvas.drawColor(R.color.black);
                                tree.drawMe(canvas, res, inGameCurrentTime);
                                mSurfaceHolder.unlockCanvasAndPost(canvas);
                            }
                        }
                    }
                    mDirector.postDelayed(mScript, 70);
                } else {
                	showPointsAndReward();
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
	                    	if(tree.getState() == Tree.STATE_YOUNG){
	                    		tree.abuseHealth();
	                    	} else if(tree.getState() == Tree.STATE_BEARING){
	                    		mPointsEarned+=3;
	                    	} else if(tree.getState() == Tree.STATE_OLD){
	                    		mPointsEarned++;
	                    	}
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
    	mDirector = new Handler();
    	mIsScriptRunning = false;
    	res = getResources();
    	tree = new Tree(50, 50, 653, 593);
    }
}
