package com.gj.gb.stage.actors;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.gj.gb.R;
import com.gj.gb.stage.common.StageHelper;

public class Carrot {

	public static final int STATE_EMPTY		 	= 1000;
	public static final int STATE_SEEDED 		= 1001;
	public static final int STATE_SPROUT 		= 1002;
	public static final int STATE_RIPE 			= 1003;
	public static final int STATE_SPOILED 		= 1004;
	public static final int STATE_HARVESTED 	= 1005;
	public static final int STATE_SELECTED	 	= 1006;
	public static final int STATE_UNSELECTED 	= 1007;

	private int mState;
	private float mPosX;
	private float mPosY;
	private float mPosXRightBorder;
	private float mPosYBottomBorder;
	private Bitmap mCurrentSkin;
	
	private float mRecordedTouchPtY;//records user's activity for this carrot only

	// Down time in ms
	private long mSeedDownTime;
	private long mSproutDownTime;
	private long mRipeDownTime;
	private long mSpoiledDownTime;
	private boolean mHarvestDownTimeNoFade;
	private boolean mHarvestDownTimeHalfFade;
	private boolean mHarvestDownTimeFullFade;
	private static final long mDefaultDownTime = 500;

	public Carrot(float posX, float posY, float posXRightBorder, float posYBottomBorder) {
		mPosX = posX;
		mPosY = posY;
		mPosXRightBorder = posXRightBorder;
		mPosYBottomBorder = posYBottomBorder;
		mState = STATE_EMPTY;
	}

	public void drawMe(Canvas canvas, Resources res, long inGameCurrentTime) {
		if (mState == STATE_EMPTY && mCurrentSkin == null) {
			mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_empty);
		} else if (mState == STATE_SEEDED && inGameCurrentTime > mSeedDownTime) {
			mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_sprout);
			mState = STATE_SPROUT;
		} else if (mState == STATE_UNSELECTED || (mState == STATE_SPROUT && inGameCurrentTime > mSproutDownTime)) {
			mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_ripe);
			mState = STATE_RIPE;
		} else if (mState == STATE_RIPE && inGameCurrentTime > mRipeDownTime) {
			mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_spoiled);
			mState = STATE_SPOILED;
			if(inGameCurrentTime > mSpoiledDownTime){
				mSpoiledDownTime = inGameCurrentTime + mDefaultDownTime;
			}
		} else if (mState == STATE_SELECTED) {
			mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_selected);
		} else if (mState == STATE_SPOILED && inGameCurrentTime > mSpoiledDownTime) {
			mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_empty);
			mState = STATE_EMPTY;
		} else if (mState == STATE_HARVESTED) {
			if (!mHarvestDownTimeNoFade && !mHarvestDownTimeHalfFade && !mHarvestDownTimeFullFade) {
				mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_harvested);
				mHarvestDownTimeNoFade = true;
			} else if (mHarvestDownTimeNoFade) {
				mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_harvest_fade_half);
				mHarvestDownTimeHalfFade = true;
				mHarvestDownTimeNoFade = false;
			} else if (mHarvestDownTimeHalfFade) {
				mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_harvested_fade_full);
				mHarvestDownTimeFullFade = true;
				mHarvestDownTimeHalfFade = false;
			} else if (mHarvestDownTimeFullFade) {
				mCurrentSkin = BitmapFactory.decodeResource(res, R.drawable.carrot_empty);
				mHarvestDownTimeFullFade = false;
				mState = STATE_EMPTY;
			}
		}
		canvas.drawBitmap(mCurrentSkin, mPosX, mPosY, null);
	}
	
	public boolean isHit(float touchPosX, float touchPosY){
		if(mState == STATE_RIPE){
			return StageHelper.isWithinBorders(mPosX, mPosXRightBorder, mPosY, 
					mPosYBottomBorder, touchPosX, touchPosY);
		}
		return false;
	}

	private void initDownTime() {
		mSeedDownTime = 3000;
		mSproutDownTime = 3000;
		mRipeDownTime = 5000;
		mSpoiledDownTime = 3000;
		mHarvestDownTimeNoFade = false;
		mHarvestDownTimeHalfFade = false;
		mHarvestDownTimeFullFade = false;
	}

	public void setTimeSeeded(long timeSeeded) {
		initDownTime();
		mSeedDownTime += timeSeeded;
		mSproutDownTime += mSeedDownTime;
		mRipeDownTime += mSproutDownTime;
		mSpoiledDownTime += mRipeDownTime;
	}
	
	public void setState(int state) {
		mState = state;
	}

	public int getState() {
		return mState;
	}

	public float getRecordedTouchPtY() {
		return mRecordedTouchPtY;
	}

	public void setRecordedTouchPtY(float recordedTouchPtY) {
		mRecordedTouchPtY = recordedTouchPtY;
	}
}
