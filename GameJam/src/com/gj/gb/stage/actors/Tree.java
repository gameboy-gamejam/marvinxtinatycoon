package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gj.gb.R;
import com.gj.gb.stage.common.StageHelper;

public class Tree {
	
	public static final int MAX_NUMBER_TREE_TAP = 5;
	
	private static final int MAX_SHAKE_TREE = 3;
	
	private float mPosX;
    private float mPosY;
    private float mPosXRightBorder;
    private float mPosYBottomBorder;
    private int mWidth;
    private int mHeight;
    
    private Bitmap mCurrentSkin;
    private boolean mIsShaking;
    private int mShakingIdx;
    private int mTapIdx;

	public Tree(float posX, float posY, float posXRightBorder, float posYBottomBorder) {
        mPosX = posX;
        mPosY = posY;
        mPosXRightBorder = posXRightBorder;
        mPosYBottomBorder = posYBottomBorder;
        mHeight = (int) (mPosYBottomBorder - mPosY);
        mWidth = (int) (mPosXRightBorder - mPosX);
        reset();
    }
    
    public void drawMe(Canvas canvas, Resources res, long inGameCurrentTime){
    	if(mCurrentSkin == null){
        	mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.tree_died);
            Bitmap.createScaledBitmap(mCurrentSkin, mWidth, mHeight, false);
        }
    	if(mIsShaking){
    		if(mShakingIdx < MAX_SHAKE_TREE){
    			mShakingIdx++;
    		} else {
    			mIsShaking = false;
    			mShakingIdx = 0;
    		}
    		if(mShakingIdx%2 != 0){
    			canvas.drawBitmap(mCurrentSkin, mPosX+7, mPosY, null);
    		}
    	}
    	canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
    }
	
	public boolean isHit(float touchPosX, float touchPosY){
        return StageHelper.isWithinBorders(mPosX, mPosXRightBorder, mPosY, 
                                           mPosYBottomBorder, touchPosX, touchPosY);
    }
	
	public void reset(){
		mIsShaking = false;
		mShakingIdx = 0;
		mTapIdx = 0;
	}
	
	public void setShaking(boolean isShaking) {
		if(!mIsShaking) {
			mIsShaking = isShaking;
		}
	}
	
	public boolean isShaking(){
		return mIsShaking;
	}
	
	public int getTapIDx(){
		return mTapIdx;
	}
	
	public void addTap(){
		return mTapIdx++;
	}
}
