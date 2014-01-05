package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gj.gb.R;
import com.gj.gb.stage.common.StageHelper;

public class Tree {
	
	public static final int STATE_YOUNG		= 4000;
	public static final int STATE_BEARING	= 4001;
	public static final int STATE_OLD		= 4002;
	public static final int STATE_DIED		= 4003;
	
	public static final int AGE_BEARING_FRUIT_MS	= 5000;
	public static final int AGE_OLD_MS				= 15000;
	public static final int AGE_DIED_MS				= 20000;
	
	private static final int MAX_SHAKE_TREE = 3;
	private static final int MAX_HEALTH = 5;
	
	private int health = 5;
	
	private int mState;
	private float mPosX;
    private float mPosY;
    private float mPosXRightBorder;
    private float mPosYBottomBorder;
    private int mWidth;
    private int mHeight;
    private Bitmap mCurrentSkin;
    private long mTimeTreeLived;
    private boolean mIsShaking;
    private int mShakingIdx;

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
    	long age = inGameCurrentTime - mTimeTreeLived;
    	if(mState == STATE_DIED || age >= AGE_DIED_MS){
        	mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_harvested_fade_full);
            Bitmap.createScaledBitmap(mCurrentSkin, mWidth, mHeight, false);
            mState = STATE_DIED;
        } else if (age < AGE_BEARING_FRUIT_MS) {
        	if(mState != STATE_YOUNG){
	            mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_sprout);
	            Bitmap.createScaledBitmap(mCurrentSkin, mWidth, mHeight, false);
	            mState = STATE_YOUNG;
        	}
        } else if(age < AGE_OLD_MS) {
        	if(mState == STATE_YOUNG) {
	        	mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_ripe);
	            Bitmap.createScaledBitmap(mCurrentSkin, mWidth, mHeight, false);
	            mState = STATE_BEARING;
        	}
        } else if(age < AGE_DIED_MS) {
        	if(mState == STATE_BEARING){
	        	mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_spoiled);
	            Bitmap.createScaledBitmap(mCurrentSkin, mWidth, mHeight, false);
	            mState = STATE_OLD;
        	}
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
    			return;
    		}
    	}
    	canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
    }
	
	public boolean isHit(float touchPosX, float touchPosY){
        return StageHelper.isWithinBorders(mPosX, mPosXRightBorder, mPosY, 
                                           mPosYBottomBorder, touchPosX, touchPosY);
    }
	
	public void abuseHealth(){
		health--;
		if(health == 0){
			mState = STATE_DIED;
		}
	}
	
	public void reset(){
		mState = -1;
		health = MAX_HEALTH;
		mIsShaking = false;
		mShakingIdx = 0;
	}
	
	public void setTimeTreeLived(long timeTreeLived) {
		mTimeTreeLived = timeTreeLived;
	}

	public int getState() {
		return mState;
	}
	
	public void setShaking(boolean isShaking) {
		if(!mIsShaking) {
			mIsShaking = isShaking;
		}
	}
}
