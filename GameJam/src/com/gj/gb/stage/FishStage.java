package com.gj.gb.stage;

import java.util.List;

import android.content.res.Resources;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.gj.gb.stage.actors.FishingRodBar;
import com.gj.gb.stage.actors.Pond;
import com.gj.gb.stage.common.Stage;

public class FishStage extends Stage {
    
    private SurfaceHolder mSurfaceHolder;
    private Handler mDirector;
    private Runnable mScript;

    //defines the border line of the ponds
    private float mLeftBorderCanvasPos;
    private float mRightBorderCanvasPos;
    private float mTopBorderCanvasPos;
    private float mBottomBorderCanvasPos;
    
    private List<Pond> ponds;
    private int mSelectedPondIdx;
    private FishingRodBar mFishingRod;
    
    private boolean mIsScriptRunning;
    private Resources res;
    
    private int mPointsEarned = 0;
    
    @Override
    protected void playGame() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void resumeGame() {
        // TODO Auto-generated method stub
        
    }
    
    //methods for SurfaceView
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        //Do nothing
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        
    }

    //methods for ending the game
    @Override
    protected void endGame() {
        Pond pond = ponds.get(mSelectedPondIdx);
        mPointsEarned = pond.getScore(mFishingRod.getScore());
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
        // TODO show pop up
    }

    @Override
    protected void showPointsAndReward() {
        // TODO show pop up
        
    }
}
